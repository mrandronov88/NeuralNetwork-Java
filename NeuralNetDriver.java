import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class NeuralNetDriver
{
	public static void main( String[] argv ) throws IOException
	{

		NeuralNet n1 = new NeuralNet( 4 );
		
		System.out.println("Reading *TRAINING DATA*\n..." );
		ArrayList<List<String>> trainingData = CSVReader.ReadData( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/data/mnist_train.csv" );
		System.out.println("COMPLETED - Reading *TRAINING DATA*" );
		
		System.out.println("Reading *TEST DATA*\n..." );
		ArrayList<List<String>> testData = CSVReader.ReadData( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/data/mnist_test.csv" );
		System.out.println("COMPLETED - Reading *TEST DATA*" );
		
		System.out.println( "Initializing Layers and Filling Weights and Biases." );
		NeuralNet.initializeLayers( n1, new int[]{ 10, 16, 16, 784 } );
		NeuralNet.fillWeightsAndBiases( n1, -10.0 );

		//ImageMaker.makeImage( testData.get( 1 ) );

		
		NeuralNet.test2( n1, testData );
		//~~~~~~~~~Training~~~~~~~~~
		System.out.println( "##########################\n~Training~\n##########################" );
		NeuralNet.training2( n1, trainingData );
		//~~~~~~~~~Testing~~~~~~~~~~

		NeuralNet.test2( n1, testData );
		
	}
}

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNet 
{
	double[][] layers;
	double[][][] weights;
	double[][] bias;
	
	NeuralNet( int numLayers )
	{
		layers = new double[numLayers][];
	}
	
	public static void initializeLayers( NeuralNet n1, int[] sizes )
	{
		for( int k = 0; k < sizes.length; k++ )
		{
			n1.layers[k] = new double[sizes[k]];
		}
	}
	
	public static void insertInputData( NeuralNet n1, List<String> Data )
	{
		for( int i = 0; i < n1.layers[0].length; i++ )
		{
			n1.layers[0][i] = (Double.parseDouble( Data.get(i+1) ) / 255.0 );
		}
	}


	public static void insertSampleInputData( NeuralNet n, List<String> Data )
	{
		for( int i = 0; i < n.layers[0].length; i++ )
		{
			n.layers[0][i] = (Double.parseDouble( Data.get(i) ) / 255.0 );
		}
	}
	
	public static void fillWeightsAndBiases( NeuralNet n1, double bias )
	{
		// Establish / Initialize Weights
		n1.weights = new double[ n1.layers.length - 1 ][][];
		for( int i = 0; i < n1.weights.length; i++ )
		{
			n1.weights[i] = new double[ n1.layers[i+1].length ][ n1.layers[i].length ];
		}
		
		// Fill weights with initially random values between -3.0 and 3.0
		Random rand = new Random();
		for( int i = 0; i < n1.weights.length; i++ )
		{
			for( int j = 0; j < n1.weights[i].length; j++ )
			{
				for( int k = 0; k < n1.weights[i][j].length; k++ )
				{
					//Random number between -3.0 and 3.0
					double randVal = rand.nextDouble( ) * 8 - 4;
					
					n1.weights[i][j][k] = randVal;
				}
			}
		}
		
		// Initialize biases
		n1.bias = new double[ n1.layers.length - 1 ][];
		for( int i = 0; i < n1.bias.length; i++ )
		{
			n1.bias[i] = new double[ n1.layers[i+1].length ];
		}
		
		// Fill biases with bias argument
		for( int i = 0; i < n1.bias.length; i++ )
		{
			for( int j = 0; j < n1.bias[i].length; j++ )
			{
				double randVal = rand.nextDouble( ) * 10 - 5;
				n1.bias[i][j] = randVal;
			}
		}
		
	}
	
	public void feedForward()
	{
		for( int i = 0; i < layers.length-1; i++ )
		{
			layers[i + 1] = sigmoidVec( addVec( dot( weights[i], layers[i] ), bias[i] ) );
		}
	}
	
	public static double[] dot( double[][] mat, double[] vector ) //*****
	{
		double[] result = new double[ mat.length ];		
		
		for( int i = 0; i < result.length; i++ )
		{
			double sum = 0;
			for( int j = 0; j < mat[i].length; j++ )
			{
				sum += mat[i][j] * vector[j];
			}
			
			result[i] = sum;
		}
		
		return result;
	}
	
	/*
	 * public static public void feedForward() { for( int i = 0; i <
	 * layers.length-1; i++ ) { layers[i + 1] = sigmoidVec( addVec( dot( weights[i],
	 * layers[i] ), bias[i] ) ); } }
	 */
	
	public static double[] addVec( double[] v1, double[] v2 ) //*****
	{
		double[] result = new double[ v1.length ];
		for( int i = 0; i < result.length; i++ )
		{
			result[ i ] = v1[i] + v2[i];
		}
		
		return result;
	}
	
	public static double[] sigmoidVec( double[] vector ) //*****
	{
		double[] result = new double[ vector.length ];
		for( int i = 0; i < vector.length; i++ )
		{
			result[i] = ( 1 / ( 1 + Math.exp( -vector[i] ) ) );
		}
		
		return result;
	}
	
	public static void test( NeuralNet n1, ArrayList<List<String>> testData )
	{
		int numCorrect = 0;
		int total = 0;
		double average = 0.0;
		
		double cost = 0.0;
		
		double[] results = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		//double[] layersResult = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		for( int i = 0; i < testData.size(); i++ )
		{
			total++;
			insertInputData(n1, testData.get(i) );
			n1.feedForward();
			if( greatestOfOutput( n1.layers[ n1.layers.length - 1 ] ) == Integer.parseInt( testData.get(i).get(0) ) )
			{
				results[ Integer.parseInt( testData.get(i).get(0) ) ]++;
				numCorrect++;
			}
			
			double[] y = new double[ n1.layers[ n1.layers.length - 1 ].length ];
			
			for( int l = 0; l < y.length; l++ )
			{
				if ( l == Integer.parseInt( testData.get( i ).get( 0 ) ) )
				{
					y[ l ] = 1.00;
				}
				else
				{
					y[ l ] = 0.00;
				}
			}
			
			cost += findCost( n1.layers[ n1.layers.length - 1 ], y );
			
			//layersResult = addVec( layersResult, n1.layers[ n1.layers.length - 1 ] );
		}
		average = (double) numCorrect / (double) total;
		
		cost = cost / total;
		
		/*System.out.println( "~~~~~~~~~Output~~~~~~~~");
		for( int i = 0; i < n1.layers[3].length; i++ )
		{
			System.out.println( i + " | " + (int)(n1.layers[3][i]*100) + "%" );
		}*/
		
		System.out.println( "|Initial Test|\n~~NumCorrect : " + numCorrect + "| Total :" + total + "| Average :" + (average*100.0) + "%\nCost :" + cost );
		
		/*
		for( int i = 0; i < results.length; i++ )
		{
			System.out.println( results[i] );
		}
		System.out.println( "~~~~~~~~~~" );
		for( int i = 0; i < layersResult.length; i++ )
		{
			System.out.println( layersResult[i] );
		}
		
		multiplyValueOnVec( layersResult, ( 1 / ( (double) testData.size() ) ) );
		
		System.out.println( "~~~~~~~~~~" + testData.size() );
		for( int i = 0; i < layersResult.length; i++ )
		{
			System.out.println( layersResult[i] );
		}
		*/

	}
	
	public static int greatestOfOutput( double[] output )
	{
		int greatest = 0;
		for( int i = 1; i < output.length; i++ )
		{
			if ( output[greatest] < output[i] )
			{
				greatest = i;
			}			
		}
		
		return greatest;
	}
	
	public static void training( NeuralNet n1, ArrayList<List<String>> trainingData )
	{
		int epochs = 8;
		int m = trainingData.size();
		int miniBatchSize = trainingData.size() / m;
		int dataCount = 0;
		double n = 0.5;

		double[][][] errorArray = new double[ miniBatchSize ][ n1.layers.length - 1 ][];
		double[][][] z = new double[ miniBatchSize ][ n1.layers.length - 1 ][];
		double[][][] a = new double[ miniBatchSize ][][];
		
		double originalCost = 0.0;

		for( int i = 0; i < miniBatchSize; i++ )
		{
			for( int j = 0; j < n1.layers.length - 1; j++ )
			{
				errorArray[ i ][ j ] = new double[ n1.layers[j].length ];
			}
		}
		
		
		for( int i = 0; i < epochs; i++ )
		{
			for( int j = 0; j < m; j++ )
			{
				dataCount = miniBatchSize * j;
				for( int k = 0; k < miniBatchSize; k++ )
				{
					insertInputData( n1, trainingData.get( k + dataCount ) ); // Insert Data
	
					double[] y = new double[ n1.layers[ n1.layers.length - 1 ].length ];
					
					for( int l = 0; l < y.length; l++ )
					{
						y[ l ] = 0.00;
					}					
					y[ Integer.parseInt( trainingData.get( k + dataCount ).get( 0 ) ) ] = 1.00;
					
					// 1.Feedforward
					n1.trainingFeedForward( z[k] );
					a[ k ] = n1.layers;
					
					originalCost += findCost( n1.layers[ n1.layers.length - 1 ], y );
	
					// 2. Output Error
					errorArray[ k ][ n1.layers.length - 2 ] = getOutputError( n1.layers, z[k], y );
					
					// 3. Backpropagate the Error
					backpropagate( n1.weights, z[k], errorArray[ k ] );
					
				}
				
				// 4. Adjust Weights and Biases accordingly
				adjustWeights( n, (double) miniBatchSize, errorArray, n1.weights, a );
				adjustBiases( n, (double) miniBatchSize, errorArray, n1.bias );
				
			}

			originalCost = originalCost / ( m * miniBatchSize ) ;
			System.out.println( "#" + i + "|Cost:|" + originalCost + "|" );
			originalCost = 0.0;

		}
	}
	
	public void trainingFeedForward( double[][] z )
	{
		for( int i = 0; i < layers.length-1; i++ )
		{
			z[i] = addVec( dot( weights[i], layers[i] ), bias[i] );
			layers[i + 1] = sigmoidVec( z[i] );
		}
	}
	
	public void backPropagateMethod( NeuralNet n1, double[][][] errorArray, double[][] z, double[] y, int k )
	{
		//errorArray[ k ][ n1.layers.length - 2 ] = getOutputError( n1.layers, z, y );
		
		// 3. Backpropagate the Error
		backpropagate( n1.weights, z, errorArray[ k ] );
	}

	/*
	 * public void trainingFeedForward( double[][] z, double cost ) { for( int i =
	 * 0; i < layers.length-1; i++ ) { layers[i + 1] = sigmoidVec( addVec( dot(
	 * weights[i], layers[i] ), bias[i] ) ); } }
	 */
	
	public static double[] getOutputError( double[][] activations, double[][] z, double[] y ) //*****
	{
		return vecHadamardProduct( subtractVec(activations[ activations.length-1 ], y), vecDerivativeSigmoid( z[ z.length-1 ] ) );
	}

	public static void backpropagate( double[][][] weights, double[][] z, double[][] errorArray )
	{
		for( int i = weights.length - 2; i >= 0; i-- )
		{

			//System.out.println( "|" + i + "||" +  errorArray[ i + 1 ][0] + "|" );
			
			double[] vdS = vecDerivativeSigmoid( z[ i ] );
			
			double[][] tW = transpose( weights[ i + 1 ] );
			
			double[] dt = dot( tW, errorArray[ i + 1 ] );

			errorArray[ i ] = vecHadamardProduct( dt, vdS );
		}
	}

	public static void adjustWeights( double n, double m, double[][][] errorArray, double[][][] weights, double[][][] a )
	{
		for ( int i = weights.length - 1; i >= 0; i-- )
		{
			double[][] weightGradient = calculateWeightGradient( i, errorArray, a );
			
			multiplyValueOnMat( weightGradient, n / m );
			
			weights[i] = subtractMat( weights[i], weightGradient );

			//System.out.println( "Adjusted Weights: Success" );
		}
	}

	public static void adjustBiases( double n, double m, double[][][] errorArray, double[][] biases )
	{
		for( int i = biases.length-1; i >= 0; i-- )
		{
			double[] biasGradient = calculateBiasGradient( i, errorArray, biases );

			multiplyValueOnVec( biasGradient, n / m );

			biases[ i ] = subtractVec( biases[ i ], biasGradient );

			//System.out.println( "Adjusted Biases: Success" );
		} 
	}
	
	public static double[][] calculateWeightGradient( int layerIndex, double[][][] errorArray, double[][][] a )
	{
		double[][] result = new double[ errorArray[ 0 ][ layerIndex ].length ][ a[ 0 ][ layerIndex ].length ];
		
		fillMatWithZeroes( result );

		for( int j = 0; j < errorArray.length; j++ )
		{
			result = addMat( result, multiplyVec( errorArray[j][ layerIndex ], a[ j ][ layerIndex ] ) );
		}
		
		return result;
	}
	
	public static double[] calculateBiasGradient( int layerIndex, double[][][] errorArray, double[][] biases )
	{
		double[] result = new double[ biases[ layerIndex ].length ];

		fillVecWithZeroes( result );

		for( int j = 0; j < errorArray.length; j++ )
		{
			result = addVecTraining( result, errorArray[j][ layerIndex ] );
		}
		
		return result;
	}
	
	public static double findCost( double[] a, double[] y )
	{
		double result = 0.0;
		for( int i = 0; i < a.length; i++ )
		{
			result += Math.pow( a[ i ] - y[ i ], 2 );
		}
		return result;
	}

	public static double[] subtractVec( double[] vec1, double[] vec2 ) //*****
	{
		double[] result = new double[ vec1.length ];
		for( int i = 0; i < result.length; i++ )
		{
			result[ i ] = vec1[ i ] - vec2[ i ];
		}
		return result;
	}

	public static double[] addVecTraining( double[] vec1, double[] vec2 ) //*****
	{
		double[] result = new double[ vec1.length ];
		for( int i = 0; i < result.length; i++ )
		{
			result[ i ] = vec1[ i ] + vec2[ i ];
		}
		return result;
	}

	public static double[] vecHadamardProduct( double[] vec1, double[] vec2 ) //*****
	{
		double[] result = new double[ vec1.length ];
		for( int i = 0; i < result.length; i++ )
		{
			result[ i ] = vec1[ i ] * vec2[ i ];
		}
		return result;
	}

	public static double[] vecDerivativeSigmoid( double[] z ) //*****
	{
		double[] result = new double[ z.length ];
		for( int i = 0; i < result.length; i++ )
		{
			result[ i ] = (Math.exp( -z[ i ] ) / ( Math.pow( (1 + Math.exp( -z[ i ] ) ), 2 ) ) );
		}
		return result;
	}

	public static double[][] transpose( double[][] mat ) //*****
	{
		double[][] result = new double[ mat[ 0 ].length ][ mat.length ];
		for( int i = 0; i < mat.length; i++ )
		{
			for( int j = 0; j < mat[ i ].length; j++ )
			{
				result[ j ][ i ] = mat[ i ][ j ];
				//System.out.println( "|" + j + "|" + i + "|" + result[j][i] );
			}
		}
		return result;
	}

	public static double[][] multiplyVec( double[] vec1, double[] vec2 ) //*****
	{
		double[][] result = new double[ vec1.length ][ vec2.length ];
		for( int i = 0; i < result.length; i++ )
		{
			for( int j = 0; j < result[ i ].length; j++ )
			{
				result[ i ][ j ] = vec1[ i ] * vec2[ j ];
			}
		}
		return result;
	}

	public static void fillVecWithZeroes( double[] vec ) //*****
	{
		for( int i = 0; i < vec.length; i++ )
		{
			vec[ i ] = 0.0;
		}
	}

	public static void fillMatWithZeroes( double[][] mat ) //*****
	{
		for( int i = 0; i < mat.length; i++ )
		{
			for( int j = 0; j < mat[ i ].length; j++ )
			{
				mat[ i ][ j ] = 0.0;
			}
		}
	}

	public static double[][] addMat( double[][] mat1, double[][] mat2 ) //*****
	{
		double[][] result = new double[ mat1.length ][ mat1[0].length ];
		for( int i = 0; i < result.length; i++ )
		{
			for( int j = 0; j < result[ i ].length; j++ )
			{
				result[ i ][ j ] = mat1[ i ][ j ] + mat2[ i ][ j ];
			}
		}
		return result;
	}

	public static double[][] subtractMat( double[][] mat1, double[][] mat2 ) //*****
	{
		double[][] result = new double[ mat1.length ][ mat1[0].length ];
		for( int i = 0; i < result.length; i++ )
		{
			for( int j = 0; j < result[ i ].length; j++ )
			{
				result[ i ][ j ] = mat1[ i ][ j ] - mat2[ i ][ j ];
			}
		}
		return result;
	}

	public static void multiplyValueOnMat( double[][] mat, double value ) //*****
	{
		for( int i = 0; i < mat.length; i++ )
		{
			for( int j = 0; j < mat[ i ].length; j++ )
			{
				mat[ i ][ j ] = mat[ i ][ j ] * value;
			}
		}
	}

	public static void multiplyValueOnVec( double[] vec, double value ) //*****
	{
		for( int i = 0; i < vec.length; i++ )
		{
			vec[ i ] = vec[ i ] * value;
		}
	}

	public static void printMat( double[][] mat )
	{
		System.out.println("~~~~~~~~~~");
		
		String x = "      ";
		
		for( int j = 0; j < mat[ 0 ].length; j++ )
		{
			x += j + " :| ";
		}
		System.out.println( x );
		
		for( int i = 0; i < mat.length; i++ )
		{
			x = i + " :| ";
			for( int j = 0; j < mat[ i ].length; j++ )
			{
				x += mat[ i ][ j ] + " | ";
			}
			System.out.println( x );
		}
		
		System.out.println("~~~~~~~~~~");
	}

	public static void printVec( double[] vec )
	{
		System.out.println("~~~~~~~~~~");
		for( int i = 0; i < vec.length; i++ )
		{
			System.out.println( " i:|" + i + "|" + vec[ i ] );
		}
		System.out.println("~~~~~~~~~~");
	}

	public static void saveNet( NeuralNet n, String saveName ) throws IOException
	{
		NetSaver.writeFile( n, saveName + ".txt" );
	}

	public static void loadNet( NeuralNet n, String fileName ) throws IOException
	{
		NetSaver.netLoader( n, fileName );
	}

	public static void makeImage( List<String> numberImage ) throws IOException
	{
		ImageMaker.makeImage( numberImage );
	}

	public static void trySample( NeuralNet n, String fileName ) throws IOException
	{
		List<String> sample = ImageMaker.getImageVals( fileName );

		insertSampleInputData( n, sample );

		int result = greatestOfOutput( n.layers[ n.layers.length - 1 ] );

		System.out.println( result );

	}
	
	public static void main( String[] args ) throws IOException
	{
		
		/*
		NeuralNet n1 = new NeuralNet( 4 );
		
		System.out.println("Reading *TRAINING DATA*\n..." );
		ArrayList<List<String>> trainingData = CSVReader.ReadData( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/data/mnist_train.csv" );
		System.out.println("COMPLETED - Reading *TRAINING DATA*" );
		*/

		System.out.println("Reading *TEST DATA*\n..." );
		ArrayList<List<String>> testData = CSVReader.ReadData( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/data/mnist_test.csv" );
		System.out.println("COMPLETED - Reading *TEST DATA*" );
		
		/*
		loadNet( n1, "net1-Save1.txt" );

		
		System.out.println( "Initializing Layers and Filling Weights and Biases." );
		initializeLayers( n1, new int[]{ 784, 16, 16, 10 } );
		fillWeightsAndBiases( n1, -10.0 );

		ImageMaker.makeImage( testData.get( 1 ) );

		
		test( n1, testData );
		//~~~~~~~~~Training~~~~~~~~~
		System.out.println( "##########################\n~Training~\n##########################" );
		training( n1, trainingData );
		//~~~~~~~~~Testing~~~~~~~~~~
		*/

		NeuralNet n = new NeuralNet( 4 );
		loadNet( n, "net1-Save1.txt" );
		test( n, testData );

		System.out.println( "####################################################" );
		trySample( n, "7.png" );

		//saveNet( n1, "net1" );
		
	}
}





/*
 
	public static void training( NeuralNet n1, ArrayList<List<String>> trainingData )
	{
		int epochs = 2;
		int m = trainingData.size();
		int miniBatchSize = trainingData.size() / m;
		int dataCount = 0;
		double n = 1.0;

		double[][][] errorArray = new double[ miniBatchSize ][ n1.layers.length - 1 ][];
		double[][][] z = new double[ miniBatchSize ][ n1.layers.length - 1 ][];
		double[][][] a = new double[ miniBatchSize ][][];
		
		double originalCost = 0.0;

		for( int i = 0; i < miniBatchSize; i++ )
		{
			for( int j = 0; j < n1.layers.length - 1; j++ )
			{
				errorArray[ i ][ j ] = new double[ n1.layers[j].length ];
			}
		}
		
		
		for( int i = 0; i < epochs; i++ )
		{
			for( int j = 0; j < m; j++ )
			{
				dataCount = miniBatchSize * j;
				for( int k = 0; k < miniBatchSize; k++ )
				{
					insertInputData( n1, trainingData.get( k + dataCount ) ); // Insert Data
	
					double[] y = new double[ n1.layers[ n1.layers.length - 1 ].length ];
					
					for( int l = 0; l < y.length; l++ )
					{
						y[ l ] = 0.00;
					}					
					y[ Integer.parseInt( trainingData.get( k + dataCount ).get( 0 ) ) ] = 1.00;
					
					// 1.Feedforward
					n1.trainingFeedForward( z[k] );
					a[ k ] = n1.layers;
					
					originalCost += findCost( n1.layers[ n1.layers.length - 1 ], y );
	
					// 2. Output Error
					errorArray[ k ][ n1.layers.length - 2 ] = getOutputError( n1.layers, z[k], y );
					
					// 3. Backpropagate the Error
					backpropagate( n1.weights, z[k], errorArray[ k ] );
				}
				
				// 4. Adjust Weights and Biases accordingly
				adjustWeights( n, (double) miniBatchSize, errorArray, n1.weights, a );
				adjustBiases( n, (double) miniBatchSize, errorArray, n1.bias );
			}
			
			originalCost = originalCost / ( m * miniBatchSize) ;
			System.out.println( "#" + i + "|Cost:|" + originalCost + "|" );

		}
	}
 
 
 
 */

		//CSVReader Data = new CSVReader();
		
		/*
		 * NeuralNet n2 = new NeuralNet( 4 );
		 * 
		 * n2.layers = new double[4][]; n2.layers[0] = new double[ 3 ]; n2.layers[1] =
		 * new double[ 2 ]; n2.layers[2] = new double[ 3 ]; n2.layers[3] = new double[ 3
		 * ];
		 * 
		 * n2.weights = new double[3][][]; n2.weights[0] = new double[2][3];
		 * n2.weights[1] = new double[3][2]; n2.weights[2] = new double[3][3];
		 * 
		 * n2.bias = new double[3][]; n2.bias[0] = new double[ 2 ]; n2.bias[1] = new
		 * double[ 3 ]; n2.bias[2] = new double[ 3 ];
		 * 
		 * fillWeightsAndBiases( n2, 1.0 );
		 * 
		 * n2.layers[0][0] = 0.56; n2.layers[0][1] = 0.71; n2.layers[0][2] = 0.35;
		 * 
		 * testTraining( n2, new double[]{ 1.0, 0.0, 0.0 } );
		 */
		
		/*
		 * double[][] test1 = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } }; double[][] test2
		 * = { { 1.0, 2.0 }, { 3.0, 4.0 } }; double[][] test3 = { { 5.0, 6.0 }, { 7.0,
		 * 8.0 } };
		 * 
		 * double[] vec1 = { 1.0, 2.0 }; double[] vec2 = { 3.0, 4.0 }; double[] vec3 = {
		 * 1.0, 2.0, 3.0 };
		 * 
		 * double[] result = sigmoidVec( vec1 );
		 * 
		 * printVec( vec1 ); printVec( result );
		 * 
		 * System.out.println( result[0] + "|" + result[1] );
		 */










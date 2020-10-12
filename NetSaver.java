import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NetSaver
{

	public static void writeFile( NeuralNet network, String fileName ) throws IOException
	{
		BufferedWriter writer = new BufferedWriter( new FileWriter( fileName ) );

		String specifications = "";
		writer.write( network.layers.length + "\nsp\n" );
		for( int i = 0; i < network.layers.length; i++ )
		{
			specifications += network.layers[i].length + "\n";
		}

		writer.write( specifications );
		writer.write( "\nm\n" );
		for( int i = 0; i < network.weights.length; i++ )
		{
			for( int j = 0; j < network.weights[ i ].length; j++ )
			{
				for( int k = 0; k < network.weights[ i ][ j ].length; k++ )
				{
					writer.write( network.weights[ i ][ j ][ k ] + "\n" );
				}
			}
		}

		writer.write( "b\n" );
		
		for( int i = 0; i < network.bias.length; i++ )
		{
			for( int j = 0; j < network.bias[ i ].length; j++ )
			{
				writer.write( network.bias[ i ][ j ] + "\n" );
			}
		}

		writer.close();
	}

	public static void netLoader( NeuralNet network, String FileName ) throws IOException
	{
		File file = new File( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/" + FileName );
		
		BufferedReader reader = new BufferedReader( new FileReader( file ) );
		
		String st = reader.readLine();
		
		// Process st into network
		int layerSize = Integer.parseInt( st );
		network.layers = new double[ layerSize ][];

		st = reader.readLine();
		for( int i = 0; i < layerSize; i++ )
		{
			st = reader.readLine();
			network.layers[i] = new double[ Integer.parseInt( st ) ];
		}
		
		st = reader.readLine();
		if ( !st.equals( "m" ) )
		{
			System.out.println("Error- File Syntax Error - no 'm'" );
			return;
		}

		System.out.println( "Success So far" );

		network.weights = new double[ network.layers.length - 1 ][][];
		for( int i = 0; i < network.weights.length; i++ )
		{
			network.weights[i] = new double[ network.layers[i+1].length ][ network.layers[i].length ];
		}

		network.bias = new double[ network.layers.length - 1 ][];
		for( int i = 0; i < network.bias.length; i++ )
		{
			network.bias[i] = new double[ network.layers[i+1].length ];
		}

		for( int i = 0; i < network.weights.length; i++ )
		{
			for( int j = 0; j < network.weights[ i ].length; j++ )
			{
				for( int k = 0; k < network.weights[ i ][ j ].length; k++ )
				{
					network.weights[ i ][ j ][ k ] = Double.parseDouble( st = reader.readLine() );
				}
			}
		}

		st = reader.readLine();
		if ( !st.equals( "b" ) )
		{
			System.out.println("Error- File Syntax Error - no 'm'" );
			return;
		}

		System.out.println( "Success So far" );

		for( int i = 0; i < network.bias.length; i++ )
		{
			for( int j = 0; j < network.bias[ i ].length; j++ )
			{
				network.bias[ i ][ j ] = Double.parseDouble( st = reader.readLine() );
			}
		}

		System.out.println( "Done" );

	}

	public static void main( String[] argv ) throws IOException
	{
		String str = "Hello";
		BufferedWriter writer = new BufferedWriter( new FileWriter( "test1.txt" ) );
		writer.write( str );
		writer.write( str );

		writer.close();
	}

}

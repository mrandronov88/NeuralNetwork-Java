import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;


public class ImageMaker
{

	public static void makeImage( List<String> numberImage ) throws IOException
	{

		String name = numberImage.get( 0 ) + "";

		BufferedImage img = new BufferedImage( 28, 28, BufferedImage.TYPE_INT_ARGB);
		File outputFile = new File( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/images/" + name + ".png" );

		int p = img.getRGB( 0, 0 );
		//System.out.println( "RGB value at ( 0,0 ):" + p );

		for( int i = 0; i < 28; i++ )
		{
			for( int j = 0; j < 28; j++ )
			{
				int brightnessVal = Integer.parseInt( numberImage.get( ( 28 * i ) + j + 1 ) );
				int pixel = ( 255 << 24 ) | ( brightnessVal << 16 ) | ( brightnessVal << 8 ) | brightnessVal;

				//System.out.println( brightnessVal );

				img.setRGB( j, i, pixel );
			}
		}

		p = img.getRGB( 0, 0 );
		//System.out.println( "RGB value at ( 0,0 ):" + p );
		//System.out.println( name );

		ImageIO.write( img, "png", outputFile );

	}

	public static List<String> getImageVals( String fileName ) throws IOException
	{
		String fileLocation =  "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/images/" + fileName;

		File file = new File( fileLocation );
		BufferedImage img = ImageIO.read( file );

		int[] pixelValues = new int[ img.getHeight() * img.getWidth() ];
		int[] r = new int[ pixelValues.length ];
		int[] g = new int[ pixelValues.length ];
		int[] b = new int[ pixelValues.length ];
		int[] result = new int[ pixelValues.length ];	
		List<String> finalResult = new ArrayList<String> ();
		
		// Get rgb values from image and insert into appropriate arrays
		for( int i = 0; i < img.getWidth(); i++ )
		{
			for( int j = 0; j < img.getHeight(); j++ )
			{
				int index = i * img.getWidth() + j;
				pixelValues[ index ] = img.getRGB( j, i );
				r[ index ] = ( pixelValues[ index ] >> 16 ) & 0x000000FF;
				g[ index ] = ( pixelValues[ index ] >> 8 ) & 0x000000FF; 
				b[ index ] = ( pixelValues[ index ] ) & 0x000000FF;
			}
		}
		
		// Finds the average of the r, g, and b values of each pixel,
		// and stores it to result. 
		// This is the grayscale value of each pixel.
		// Also, converts each item result into a String item in a List<String>
		for( int i = 0; i < pixelValues.length; i++ )
		{
			result[ i ] = ( r[ i ] + g[ i ] + b[ i ] ) / 3;
			finalResult.add( Integer.toString( result[ i ] ) );
			//System.out.println( "pixelValue |" + i + "| :" + pixelValues[ i ] );
			//System.out.println( "\tr :" + r[ i ] + "\tg :" + g[ i ] + "\tb :" + b[ i ] );
		}

		//System.out.println( "Success so far!" );
		
		return finalResult;

	}

	public static void makeImage2( List<String> numberImage, String name ) throws IOException
	{

		BufferedImage img = new BufferedImage( 28, 28, BufferedImage.TYPE_INT_ARGB);
		File outputFile = new File( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/images/" + name + ".png" );

		int p = img.getRGB( 0, 0 );
		//System.out.println( "RGB value at ( 0,0 ):" + p );

		for( int i = 0; i < 28; i++ )
		{
			for( int j = 0; j < 28; j++ )
			{
				int brightnessVal = Integer.parseInt( numberImage.get( ( 28 * i ) + j ) );
				int pixel = ( 255 << 24 ) | ( brightnessVal << 16 ) | ( brightnessVal << 8 ) | brightnessVal;

				//System.out.println( brightnessVal );

				img.setRGB( j, i, pixel );
			}
		}

		p = img.getRGB( 0, 0 );
		//System.out.println( "RGB value at ( 0,0 ):" + p );
		//System.out.println( name );

		ImageIO.write( img, "png", outputFile );

	}

	public static void main( String[] args ) throws IOException
	{

		List<String> test = getImageVals( "7.png" );
		makeImage2( test, "test" );

		/*
		BufferedImage img = new BufferedImage( 28, 28, BufferedImage.TYPE_INT_ARGB);
		File outputFile = new File( "/home/misha/Documents/Code/JavaProjects/NeuralNetwork/images/image.png" );
		
		int p = img.getRGB( 0,0 );

		System.out.println( "RGB value at ( 0,0 ):" + p );
		
		int brightnessVal = 0;

		p = ( 255 << 24 ) | ( brightnessVal << 16 ) | ( brightnessVal << 8 ) | brightnessVal;

		System.out.println( "RGB value at ( 0,0 ):" + p );
		
		for( int i = 0; i < 28; i++ )
		{
			for( int j = 0; j < 28; j++ )
			{
				img.setRGB( i, j, p );
			}
		}

		ImageIO.write( img, "png", outputFile );
		*/
	}
}

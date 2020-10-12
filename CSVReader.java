import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader
{
	
	ArrayList<List<String>> trainingData;
	ArrayList<List<String>> testData;
	
	CSVReader() throws IOException
	{
		trainingData = CSVReader.ReadData( "C:\\Users\\agnalin\\eclipse-workspace\\TestProject\\data\\mnist_train.csv" );
		
		testData = CSVReader.ReadData( "C:\\Users\\agnalin\\eclipse-workspace\\TestProject\\data\\mnist_test.csv" );
	}
	
	public static ArrayList<List<String>> ReadData(String filePath ) throws IOException
	{
		ArrayList<List<String>> records = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        records.add(Arrays.asList(values));
		    }
		    
/*		    line = br.readLine();
		    String[] values = line.split(",");
		    records.add(Arrays.asList(values));*/
		}
		
		return records;
	}
	
	public static void main( String[] args ) throws IOException
	{
		CSVReader CSVReader = new CSVReader();
		
		//ArrayList<List<String>> trainingData = CSVReader.ReadData( "C:\\Users\\agnalin\\eclipse-workspace\\TestProject\\data\\mnist_train.csv" );
		
		ArrayList<List<String>> testData = CSVReader.ReadData( "C:\\Users\\agnalin\\eclipse-workspace\\TestProject\\data\\mnist_test.csv" );
		
		System.out.println( Integer.parseInt( testData.get(0).get(0) ) + 1 );
		
		
	}

}


/*
List<List<String>> records = new ArrayList<>();
try (Scanner scanner = new Scanner(new File("book.csv"));) {
    while (scanner.hasNextLine()) {
        records.add(getRecordFromLine(scanner.nextLine()));
    }
}

Then we will parse the lines and store it into an array:
1
2
3
4
5
6
7
8
9
10
	
private List<String> getRecordFromLine(String line) {
    List<String> values = new ArrayList<String>();
    try (Scanner rowScanner = new Scanner(line)) {
        rowScanner.useDelimiter(COMMA_DELIMITER);
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next());
        }
    }
    return values;
}
 */

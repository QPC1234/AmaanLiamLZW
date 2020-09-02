import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LZWEncoder {
	private ArrayList<String> key; //index of a string is the code associated with it, ex: A is the 0th element, B is the 1st element, ...
	
	//constructor
	public LZWEncoder(){
		key = new ArrayList<String>();
		//add all the Ascii characters to the key to start with
		for (int i = 0; i < 256; i++) {
			key.add(((char) i);
		}
	}
	
	//main method that does the encoding from an input file to an output file
	public void encode(String inputFile, String outputFile) {
		PrintWriter pWriter = new PrintWriter(new FileReader (outputFile));
	}
}

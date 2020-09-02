import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.*;

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
	public void encode(String inputFile, String outputFile) throws IOException {
		PrintWriter pWriter = new PrintWriter(new File(outputFile));
		BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
		
		String tempKeyStart = "" + (char)(bReader.read()); //this is the piece of the string being read that is already in our key
		String tempKeyLast = "" + (char)(bReader.read()); //this is the last character of the string being read
		
		while(bReader.ready()) { //loops through the inputFile
			if(!key.contains(tempKeyStart + tempKeyLast)) { //checks if the read in string is already in the key
				pWriter.println(key.indexOf(tempKeyStart));
				key.add(tempKeyStart+tempKeyLast);
				tempKeyStart = tempKeyLast; //must do this for the stored string to reset
			}
			else {
				tempKeyStart = tempKeyStart + tempKeyLast; //if it is already in the key, we just grow the start until we find a series of characters not in the key
			}
			
			tempKeyLast = "" + (char)(bReader.read()); //adds to the string we are reading in
		}
	}
}

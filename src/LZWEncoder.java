import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LZWEncoder {
	private HashMap<EncodingWithFrequency, Integer> key; //hashmap where keys are the strings and values are the associated integers
	
	int maxSize = 32769;
	//constructor
	public LZWEncoder(){
		key = new HashMap<EncodingWithFrequency, Integer>();
		//add all the Ascii characters to the key to start with
		for (int i = 0; i < 256; i++) {
			key.put(new EncodingWithFrequency("" + (char) i, 0), i); // LUKAS WAS HERE, Quinn and Jake were here
		}
	}
	
	//main method that does the encoding from an input file to an output file
	public void encode(String inputFile, String outputFile) throws IOException {
		PrintWriter pWriter = new PrintWriter(outputFile);
		BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
		
		String tempKeyStart = "" + (char)(bReader.read()); //this is the piece of the string being read that is already in our key
		String tempKeyLast = "" + (char)(bReader.read()); //this is the last character of the string being read
		StringBuffer str = new StringBuffer();
		
		while(bReader.ready()) { //loops through the inputFile
			if(!key.containsKey(tempKeyStart + tempKeyLast)) { //checks if the read in string is already in the key
				String num = Integer.toBinaryString(key.get(tempKeyStart));
				while(num.length() < 12) {
					num = "0" + num;
				}
				str.append(num);
				while(str.length() > 8) {
					pWriter.print((char)Integer.parseInt(str.substring(0,8), 2));
					str.delete(0, 8);
				}
				
				if(key.size() < maxSize) {
					key.put(new EncodingWithFrequency(tempKeyStart+tempKeyLast, 0), key.size());
				}
				else {
					//do the while loop plan to find the smallest, remove the smallest and add in
				}
				tempKeyStart = tempKeyLast; //must do this for the stored string to reset
			}
			else {
				tempKeyStart = tempKeyStart + tempKeyLast; //if it is already in the key, we just grow the start until we find a series of characters not in the key
			}
			
			tempKeyLast = "" + (char)(bReader.read()); //adds to the string we are reading in
		}
		
		String num2 = Integer.toBinaryString(key.get(tempKeyStart)); // add the final strings
		String num3 = Integer.toBinaryString(key.get(tempKeyLast));
		while(num2.length() < 12) {
			num2 = "0" + num2;
		}
		str.append(num2);
		while(num3.length() < 12) {
			num3 = "0" + num3;
		}
		str.append(num3);
		
		while(str.length() > 8) { // convert binary to encoded character and add to .txt
			pWriter.print((char)Integer.parseInt(str.substring(0,8), 2));
			str.delete(0, 8);
		}
		
		int left = (8 - (str.length() % 8)) % 8; // add extra zeroes for padding
		if(left != 8) {
			for(int j = 0; j < left; j++) {
				str.append("0");
			}
		}
		for(int i = 0; i < str.length(); i = i + 8) { // add padded ending to .txt
			pWriter.print((char)Integer.parseInt(str.substring(i,i+8), 2));
		}
		pWriter.print((char)left); // add character for number of extra zeroes
		
		bReader.close();
		pWriter.close();
	}
}

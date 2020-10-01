import java.io.*;
import java.util.*;

public class LZWDecoder {
	private HashMap<Integer, String> encodingTable;
	final static int INITIAL_TABLE_SIZE = 128;
	final static int MAX_TABLE_SIZE = 32769;
	
	public LZWDecoder() {}
	
	public void decode(String inputFileName, String outputFileName) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader(inputFileName));
		encodingTable = new HashMap<Integer, String>();
		
		File file = new File(outputFileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintWriter pw = new PrintWriter(new File(outputFileName));
		
		PriorityQueue<String> pq = new PriorityQueue<String>(); //Makes a pq to store the least recent encoding
		//Fills table with the initial characters
		for(int a = 0; a < INITIAL_TABLE_SIZE; a++) {
			encodingTable.put(a, new String((char)a + ""));
			pq.add(new String((char)a + ""));
		}
		
		//First iteration, priming variables to loop
		int nextValue = INITIAL_TABLE_SIZE;
		int currentCode = br.read(); //the char being decoded, represents an encoded string
		String previousStr = ""; //represents the most recently printed set of chars
		String currentStr = ""; //represents the set of chars to be printed & added to the hashmap
		
		//Puts first combination (the first two letters) into the hashmap
		currentStr = (char) currentCode +"";
		currentCode = br.read();
		currentStr += (char) currentCode +"";
		encodingTable.put(nextValue, currentStr);
		pw.print(currentStr);
		pq.add(currentStr);
		
		//Resets strings to accept the next encoding
		previousStr = currentStr.substring(1);
		currentStr = "";
		nextValue++;
		
		while(br.ready()) {
			currentCode = br.read();
			if (encodingTable.containsKey(currentCode)) { //if current code is already in the hashmap, add previous characters + 1st letter of current to hashmap
				currentStr = encodingTable.get(currentCode);
				if (encodingTable.size() < MAX_TABLE_SIZE)
					encodingTable.put(nextValue, previousStr + currentStr.substring(0,1));
				else {
					encodingTable.remove(pq.poll());
					encodingTable.put(nextValue, previousStr + currentStr.substring(0,1));
				}	
			} 
			else { //otherwise, add the previous string + its first letter
				currentStr = previousStr + previousStr.substring(0,1);
				if (encodingTable.size() < MAX_TABLE_SIZE)
					encodingTable.put(nextValue, currentStr);
				else {
					encodingTable.remove(pq.poll());
					encodingTable.put(nextValue, currentStr);
				}
			}
			pw.print(currentStr);//print the current string
			
			//Checks to see if the encoding is already in the pq, if so, remove, add to back either way
			pq.remove(currentStr);
			pq.add(currentStr);
			
			//Resets strings to accept the next encoding
			previousStr = encodingTable.get(currentCode);
			currentStr = "";
			nextValue++;
		}
		pw.close();
		br.close();
	}
}

/**
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class LZWDecoder {
	private HashMap<Integer, String> key;
	
	int maxSize = 32769;
	
	public LZWDecoder(){
		key = new HashMap<Integer, String>();
		//add all the Ascii characters to the key to start with
		for (int i = 0; i < 256; i++) {
			key.put(i, "" + (char) i);
		}
	}
	
	//main method that does the decoding from an input file to an output file
	public void decode(String inputFile, String outputFile) throws IOException {
		PrintWriter pWriter = new PrintWriter(outputFile);
		BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
		
		StringBuffer binary = new StringBuffer();
		while(bReader.ready()) { //convert encoded characters to binary, add them to stringBuffer
			String add = Integer.toBinaryString(bReader.read());
			for(int i = 0; i < 8-add.length(); i++) {
				binary.append("0"); // add leading zeroes
			}
			binary.append(add); 
		}
		bReader.close();
		
		int remove = 8 + Integer.parseInt(binary.substring(binary.length()-8), 2);
		binary.delete(binary.length()-remove, binary.length()); // remove extra zeroes
		
		String last = "";
		for(int i = 0; i < binary.length(); i = i + 12) {
			int num = Integer.parseInt(binary.substring(i,i+12), 2); // convert binary to key codes
			
			if(!key.containsKey(num)) {
				key.put(key.size(), last + last.charAt(0)); // special case
			}
			else if(!last.equals("")) {
				key.put(key.size(), last + key.get(num).charAt(0)); // add new codes to key
			}
			
			pWriter.write(key.get(num)); // print strings
			last = key.get(num);
		}
		pWriter.close();
	}
}
*/
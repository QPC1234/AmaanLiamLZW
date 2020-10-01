import java.util.*;
import java.io.*;

public class LZWEncoder
{
	final static int INITIAL_TABLE_SIZE = 128;//creates a final variable because 128 is a "magic number" that appears multiple times
	public static HashMap<String,Character> init(HashMap<String,Character> table)
	{
		// fill the table with the standard ascii 1-128
		for(int a = 0; a < INITIAL_TABLE_SIZE; a++)
		{
			char current = (char)(a);
			// (pattern (string) + corresponding ascii (char))
			table.put(current+"",current);
		}
		return table;
	}

	public static void encode (String input, String outputFileName) 
	{
		// the table containing the pattern and corresponding ascii - "a" -> 'a'
		HashMap<String, Character> table = new HashMap<String, Character>();
		
		// fill the table with the standard ascii 1-128
		init(table);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(input));
			PriorityQueue<String> pq = new PriorityQueue<String>(); //Makes a pq to store the least recent encoding
			
			//prints directly to the outputFile instead of using a stringbuilder so it runs faster
			File file = new File(outputFileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			
			int current = br.read();

			// last char of previous pattern
			String prev = (char)current + "";

			// the next available ascii/table slot
			int num = INITIAL_TABLE_SIZE;

			while(current != -1)
			{
				current = br.read();
				// current portion of the text being scanned for new patterns
				String temp = prev + (char)current;

				// pattern not found
				if(!table.containsKey(temp))
				{
					// encode previous
					//prints to output file instead of appending
					
					pw.print(table.get(prev));
					pq.remove(table.get(prev));
					pq.add(table.get(prev));
					
					// max 256 bc the extended ascii table ends at 255, so we can't represent anything past 255
					//the larger the table the more it compresses, so we increased the max table size to a max of 2^15 (32768) (2^16 created some unreadable chars)
					// add to the table
					if(num < 32769) {
						table.put(temp, (char)num);
					}
					else {
						table.remove(pq.poll());
						table.put(temp, (char)num);
					}

					// increase the next available ascii/table slot
					num++;

					// reset bc we've already encoded the previous
					prev = "";
				}
				// add to or set the previous
				prev += (char)current;
			}
			br.close();
			pw.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
	}
}


/**
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
		PriorityQueue<EncodingWithFrequency> pq = new PriorityQueue<EncodingWithFrequency>(); //Makes a pq to store the least recent encoding
		
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
					EncodingWithFrequency temp = new EncodingWithFrequency(tempKeyStart+tempKeyLast, System.currentTimeMillis());
					key.put(temp, key.size());
					pq.remove(temp);
					pq.add(temp);
				}
				else {
					EncodingWithFrequency temp = pq.poll();
					key.remove(temp);
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
*/
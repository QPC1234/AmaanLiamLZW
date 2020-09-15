import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class LZWDecoder {
	private HashMap<Integer, String> key;
	
	int maxSize = 4096;
	
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
		
		StringBuffer str = new StringBuffer();
		
		while(bReader.ready()) {
		}
	}
}

import java.io.IOException;

public class TesterLZW {
	public static void main (String [] args) throws IOException {
		LZWEncoder encoder = new LZWEncoder ();
		LZWDecoder decoder = new LZWDecoder();
		encoder.encode ("big.txt", "output.txt");
		decoder.decode ("output.txt","decoded.txt");
	}
}

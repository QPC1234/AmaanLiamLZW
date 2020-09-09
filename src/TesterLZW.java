import java.io.IOException;

public class TesterLZW {
	public static void main (String [] args) throws IOException {
		LZWEncoder encoder = new LZWEncoder ();
		encoder.encode ("input.txt", "output.txt");
	}
}

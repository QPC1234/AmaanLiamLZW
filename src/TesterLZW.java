
public class TesterLZW {
	public static void main (String [] args) {
		LZWEncoder encoder = new LZWEncoder ();
		encoder.encode ("input.txt", "output.txt");
	}
}

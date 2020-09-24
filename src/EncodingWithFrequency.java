public class EncodingWithFrequency {
	public String encoding;
	public int frequency;
	
	public EncodingWithFrequency(String str, int freq) {
		encoding = str;
		frequency = freq;
	}
	
	private boolean equals(EncodingWithFrequency other) {
		if (other.frequency == this.frequency)
			return true;
		return false;
	}
}
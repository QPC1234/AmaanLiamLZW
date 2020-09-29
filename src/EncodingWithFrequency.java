public class EncodingWithFrequency {
	public String encoding;
	public double value;
	
	public EncodingWithFrequency(String str, double value) {
		encoding = str;
		this.value = value;
	}
	
	private boolean equals(EncodingWithFrequency other) {
		if (other.value == this.value)
			return true;
		return false;
	}
}
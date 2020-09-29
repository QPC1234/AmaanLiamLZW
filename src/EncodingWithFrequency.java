public class EncodingWithFrequency {
	public String encoding;
	public long value;
	
	public EncodingWithFrequency(String str, long value) {
		encoding = str;
		this.value = value;
	}
	
	private boolean equals(EncodingWithFrequency other) {
		if (other.value == this.value)
			return true;
		return false;
	}
}
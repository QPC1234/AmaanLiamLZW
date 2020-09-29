public class EncodingWithFrequency {
	public String encoding;
	public long value;
	
	public EncodingWithFrequency(String str, long value) {
		encoding = str;
		this.value = value;
	}
	
	private boolean equals(EncodingWithFrequency other) {
		if (other.encoding.equals(this.encoding))
			return true;
		return false;
	}
}
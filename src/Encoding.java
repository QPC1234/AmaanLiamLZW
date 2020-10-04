public class Encoding implements Comparable<Encoding>{
	public long timestamp;
	public String encoding;
	
	public Encoding (String encoding) {
		timestamp = System.currentTimeMillis();
		this.encoding = encoding;
	}
	
	public int compareTo(Encoding o) {
		return (int)((this.timestamp - o.timestamp) * 1000000);
	}
	
	public boolean equals(Encoding o) {
		if (this.encoding.equals(o.encoding)) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return encoding;
	}
}
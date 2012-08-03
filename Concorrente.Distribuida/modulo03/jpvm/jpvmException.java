package jpvm;




public
class jpvmException extends Throwable {
	private String val;

	public jpvmException() {
		val = "jpvm error: unknown error.";
	}

	public jpvmException(String str) {
		val = new String("jpvm error: "+str);
	}

	public String toString() {
		return new String(val);
	}
};

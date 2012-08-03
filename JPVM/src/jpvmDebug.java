



public
final class jpvmDebug {
	public static final boolean on     = false;
	public static final void note(String message) {
		if(on) {
			System.out.println("jpvmDebug: "+message);
			System.out.flush();
		}
	}
	public static final void error(String message) {
		System.err.println("jpvmDebug: "+message);
		System.err.flush();
		System.exit(1);
	}
};

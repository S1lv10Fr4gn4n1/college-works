package edu.org.common;

public class PluginNotFoundException extends Exception {

	private static final long	serialVersionUID	= 1L;

	public PluginNotFoundException() {
		super();
	}

	public PluginNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PluginNotFoundException(String message) {
		super(message);
	}

	public PluginNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}

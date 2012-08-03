package edu.org.common;

public class Cookie {
	public static String	AMSSessionId		= "AMSSessionId";
	public static String	AMSAuthenticationId	= "AMSAuthenticationId";
	private String			key;
	private String			value;
	private Long			duration			= 0L;

	public Cookie(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Cookie() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
}

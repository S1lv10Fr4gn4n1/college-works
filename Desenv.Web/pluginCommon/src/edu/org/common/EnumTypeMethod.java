package edu.org.common;


public enum EnumTypeMethod {
	POST("POST"),
	GET("GET");
	
	private String domain;
	
	private EnumTypeMethod(String domain) {
		this.domain = domain;
	}
	
	public String getDomain() {
		return this.domain;
	}
}

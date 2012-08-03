package edu.org.response;

import edu.org.common.AbstractResponse;


public class ResponseCSS extends AbstractResponse {
	private static final String CONTENT_TYPE = "text/css";
	
	@Override
	protected String getContentType() {
		return CONTENT_TYPE;
	}

}

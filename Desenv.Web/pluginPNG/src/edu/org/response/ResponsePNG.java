package edu.org.response;

import edu.org.common.AbstractResponse;


public final class ResponsePNG extends AbstractResponse {

	private static final String CONTENT_TYPE = "image/png";
	
	protected String getContentType() {
		return CONTENT_TYPE;
	}
}

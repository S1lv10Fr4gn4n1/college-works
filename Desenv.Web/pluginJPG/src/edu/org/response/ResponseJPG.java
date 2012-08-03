package edu.org.response;

import edu.org.common.AbstractResponse;


public final class ResponseJPG extends AbstractResponse {

	private static final String CONTENT_TYPE = "image/jpeg";
	
	protected String getContentType() {
		return CONTENT_TYPE;
	}
}

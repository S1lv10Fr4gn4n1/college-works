package edu.org.response;

import edu.org.common.AbstractResponse;

public class ResponseJS extends AbstractResponse {
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	@Override
	protected String getContentType() {
		return CONTENT_TYPE;
	}

}

package edu.org.response;

import edu.org.common.AbstractResponse;

public class ResponseMPEG extends AbstractResponse {
	private static final String CONTENT_TYPE = "video/mpeg";
	
	@Override
	protected String getContentType() {
		return CONTENT_TYPE;
	}

}

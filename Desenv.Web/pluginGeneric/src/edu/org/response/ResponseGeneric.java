package edu.org.response;

import edu.org.common.AbstractResponse;
import edu.org.server.utils.Mime;


public class ResponseGeneric extends AbstractResponse {
	private static final String CONTENT_TYPE = "text/html";

	protected synchronized String getContentType() {
		// no Resposta generica vai tentar encontrar um mime correspondente, 
		// caso nao encontrar ira devolver o default
		
		String extenseFile = this.getExtenseFile(this.path.toCharArray());
		String contentType = Mime.getValue(extenseFile);
		
		if (contentType != null && !contentType.trim().isEmpty()) {
			return contentType;
		}
		
		return CONTENT_TYPE;
	}
	
	
	private String getExtenseFile(char[] cs) {
		String tmpExt = "";
		for (int i = cs.length-1; i > 0; i--) {
			if (cs[i] == '.') {
				break;
			}

			tmpExt = cs[i] + tmpExt;
		}
		
		if (tmpExt.isEmpty()) {
			tmpExt = "all";
		}
		
		return tmpExt;
	}
}

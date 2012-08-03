package edu.org.application;

import java.io.OutputStream;

import edu.org.common.AbstractResponse;
import edu.org.common.PageNotFoundException;

public class ResponseREST extends AbstractResponse {
	
	private static final String CONTENT_TYPE = "text/plain"; 
	private String json;
	
	@Override
	public void write(OutputStream outputStream) throws PageNotFoundException {
		try {
			String jsonResult = this.getJson();
			makeHeader(jsonResult.getBytes().length);
			outputStream.write(this.strHeader.toString().getBytes());
			outputStream.write(jsonResult.getBytes());
		} catch (Exception e) {
			String nameFile = this.path.substring(1, this.path.length());
			throw new PageNotFoundException("Arquivo " + nameFile + " nao foi encontrado.", e);
		}
	}
	
	protected String getContentType() {
		return CONTENT_TYPE;
	}
	
	public String getJson() {
		return json;
	}
	
	public void setJson(String json) {
		this.json = json;
	}

}

package edu.org.common;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IRequest {
	public Map<String, String> getHeaderRequest() throws Exception;

	public EnumTypeMethod getMethod();

	public EnumContentType getContentType();

	public Map<String, String> getParameter();

	public String getValue(String paramenter);

	public int getCountFiles();

	public List<FileUpload> getFiles();

	void readInputStream(InputStream inputStream) throws Exception;

	void bindingDataRequest();

	String getPath();
	
	List<Cookie> getCookies();

	boolean isNull();
}

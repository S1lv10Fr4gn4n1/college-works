package edu.org.common;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IResponse {

	void write(OutputStream outputStream) throws PageNotFoundException;

	void init(String path, Map<String, String> parameters, Map<String, String> headerRequest, EnumTypeMethod typeMethod);

	void setFolderRoot(String folder);

	void setCookies(List<Cookie> cookies);
	
	Map<String, String> getHeaders();
}

package edu.org.common;

public class FileUpload {
	private String	name;
	private String	type;
	private byte[]	data;
	private String	contentType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}
}

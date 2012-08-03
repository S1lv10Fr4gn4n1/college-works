package edu.org.response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import edu.org.common.AbstractResponse;
import edu.org.common.PageNotFoundException;


public class ResponseLogin extends AbstractResponse {
	private static final String CONTENT_TYPE = "text/html";

	@Override
	public synchronized void write(OutputStream outputStream) throws PageNotFoundException {
		BufferedReader in;
		String pageLogin = "";
		try {
			in = new BufferedReader(new FileReader(this.folderRoot + "/authentication/login.html"));
			
			String str = "";
			
			while ((str = in.readLine()) != null) {
				pageLogin += str + "\n";
			}
			
			if (this.path == null || this.path.isEmpty()) {
				pageLogin = pageLogin.replace("###URL###", "");
			} else {
				pageLogin = pageLogin.replace("###URL###", this.path);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			makeHeader(pageLogin.getBytes().length);
			outputStream.write(this.strHeader.toString().getBytes());
			outputStream.write(pageLogin.getBytes());
		} catch (Exception e) {
			String nameFile = this.path.substring(1, this.path.length());
			throw new PageNotFoundException("Arquivo " + nameFile + " nao foi encontrado.", e);
		}
	}

	@Override
	protected String getContentType() {
		return CONTENT_TYPE;
	}

}

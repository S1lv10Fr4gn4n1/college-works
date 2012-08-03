package edu.org.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class AbstractResponse implements IResponse {
	protected static final String	CRLF		= "\r\n";
	protected final StringBuilder	strHeader	= new StringBuilder();
	protected Map<String, String>	parameters;
	protected Map<String, String>	headerRequest;
	protected String				path;
	protected EnumTypeMethod		typeMethod;
	protected String				folderRoot;
	private List<Cookie>			cookies		= new ArrayList<Cookie>();
	private Map<String, String>		headers = new HashMap<String, String>();

	protected void makeHeader(int fileLength) {
		this.writeHeader("HTTP/1.1 200 OK");
		this.writeHeader("Content-Length: " + fileLength);
		this.writeHeader("Date: " + new Date());
		this.writeHeader("Content-type: " + this.getContentType());
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		
		// coloca os cookies no headers
		for (Cookie c : cookies) {
			SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
			
			calendar.add(Calendar.SECOND, c.getDuration().intValue());
			this.writeHeader("Set-Cookie: " + c.getKey() + "=" + c.getValue() + "; expires=" + df.format(calendar.getTime()) + "; path=/; domain=localhost"); 
			
		}

		// coloca os cabecalhos adicionais
		Set<String> keySet = this.headers.keySet();
		for (String key : keySet) {
			String value = this.headers.get(key);
			this.writeHeader(key + ": " + value);
		}
		
		
		// eh necessario colocar um vazio para saber q acabou o header
		this.writeHeader("");
	}

	@Override
	public synchronized void write(OutputStream outputStream) throws PageNotFoundException {
		try {
			File file = new File(this.folderRoot + this.path);
			InputStream inputStream = null;

			inputStream = new FileInputStream(file);

			byte[] byteArray = new byte[inputStream.available()];
			inputStream.read(byteArray);
			makeHeader(byteArray.length);
			outputStream.write(this.strHeader.toString().getBytes());
			outputStream.write(byteArray);
		} catch (Exception e) {
			String nameFile = null;

			if (this.path != null && this.path.isEmpty()) {
				nameFile = this.path.substring(1, this.path.length());
			}

			throw new PageNotFoundException("Arquivo " + nameFile + " nao foi encontrado.", e);
		}
	}

	@Override
	public synchronized void init(String path, Map<String, String> parameters, Map<String, String> headerRequest, EnumTypeMethod typeMethod) {
		this.path = path;
		this.typeMethod = typeMethod;
		this.parameters = parameters;
		this.headerRequest = headerRequest;
	}

	protected void writeHeader(final String arg) {
		System.out.println(arg);
		this.strHeader.append(arg + AbstractResponse.CRLF);
	}

	@Override
	public synchronized void setFolderRoot(String folder) {
		this.folderRoot = folder;
	}

	protected abstract String getContentType();

	@Override
	public synchronized void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	@Override
	public synchronized Map<String, String> getHeaders() {
		return this.headers;
	}
}

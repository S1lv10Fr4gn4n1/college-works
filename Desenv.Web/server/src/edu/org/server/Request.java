package edu.org.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.org.common.Cookie;
import edu.org.common.EnumContentType;
import edu.org.common.EnumTypeMethod;
import edu.org.common.FileUpload;
import edu.org.common.IRequest;

public class Request implements IRequest {

	private Map<String, String>	headersRequest;
	private Map<String, String>	parameters;
	private List<FileUpload>	files;
	private StringBuilder		dataInputStream;
	private EnumTypeMethod		typeMethod;
	private EnumContentType		contentType;
	private String				protocol;
	private String				path;
	private byte[]				byteStream;

	@Override
	public synchronized void readInputStream(InputStream inputStream) throws Exception {
		List<Byte> listStream = new ArrayList<Byte>();

		this.dataInputStream = new StringBuilder();

		int sizeStream = inputStream.available();

		while (sizeStream > 0) {

			while (sizeStream > 0) {
				final int ready = inputStream.read();
				this.dataInputStream.append((char) (byte) ready);
				listStream.add((byte) ready);

				sizeStream--;
			}

			sizeStream = inputStream.available();
		}

		this.byteStream = new byte[listStream.size()];

		for (int i = 0; i < listStream.size(); i++) {
			this.byteStream[i] = listStream.get(i);
		}
	}

	@Override
	public synchronized void bindingDataRequest() {
		// inicializacao dos list e maps
		this.parameters = new HashMap<String, String>();
		this.headersRequest = new HashMap<String, String>();
		this.files = new ArrayList<FileUpload>();

		if (this.dataInputStream.toString().isEmpty()) {
			return;
		}

		// primeira quebra de linha, contem o metodo, paramentros(se metodo GET)
		// e path, protocolo http
		int initLine = 0;
		int untilNewLine = this.dataInputStream.indexOf("\n");
		String line = this.dataInputStream.substring(initLine, untilNewLine);
		String[] datas = line.split(" ");

		// define o tipo de metodo utilizado
		this.typeMethod = EnumTypeMethod.POST;
		if (datas[0].equals(EnumTypeMethod.GET.getDomain())) {
			this.typeMethod = EnumTypeMethod.GET;

			// pegar os paramentros passado pelo get
			this.dismountParameters(datas[1], null);
		}

		// caso seja POST o path esta no data[1]
		if (this.typeMethod == EnumTypeMethod.POST) {
			this.path = datas[1];
		}

		// define o protocolo usado
		this.protocol = datas[2];

		// define os headers do request
		while (untilNewLine != -1 || this.dataInputStream.length() < untilNewLine) {
			initLine = untilNewLine;
			untilNewLine = this.dataInputStream.indexOf("\n", ++untilNewLine);

			if (untilNewLine == -1 || this.dataInputStream.length() < untilNewLine) {
				continue;
			}

			line = this.dataInputStream.substring(initLine, untilNewLine).trim();

			// se for igual a zero, entao esta pulando uma linha e é o conteudo
			if (line.length() == 0) {
				break;
			}

			this.dismountHeaders(line);
		}

		// no caso se for POST, pode ser o os parametros
		if (this.typeMethod == EnumTypeMethod.POST) {
			line = this.dataInputStream.substring(initLine, this.dataInputStream.length()).trim();

			byte[] dataAux = new byte[this.byteStream.length];
			for (int i = initLine; i < this.dataInputStream.length(); i++) {
				dataAux[i] = this.byteStream[i];
			}

			byte[] lineByte = new byte[line.length()];
			for (int i = 0; i < lineByte.length; i++) {
//				lineByte[i] = dataAux[i + untilNewLine + 3]; //TODO rever estava causando erro
				lineByte[i] = dataAux[i + untilNewLine];
			}

			this.dismountParameters(line, lineByte);
		}
	}

	@Override
	public Map<String, String> getHeaderRequest() {
		return this.headersRequest;
	}

	@Override
	public EnumTypeMethod getMethod() {
		return this.typeMethod;
	}

	@Override
	public EnumContentType getContentType() {
		return this.contentType;
	}

	@Override
	public Map<String, String> getParameter() {
		return this.parameters;
	}

	@Override
	public String getValue(String parament) {
		return this.parameters.get(parament);
	}

	@Override
	public int getCountFiles() {
		return this.files.size();
	}

	@Override
	public List<FileUpload> getFiles() {
		return this.files;
	}

	/**
	 * Desmonta a string e coloca no Map parameters
	 * 
	 * @param parameters
	 */
	private void dismountParameters(String parameters, byte[] parameterByte) {
		if (this.typeMethod == EnumTypeMethod.GET) {
			int index = parameters.indexOf("?");

			// define o path, requisitado
			this.path = parameters.substring(0, index == -1 ? parameters.length() : index);

			if (index == -1) {
				return;
			}

			String aux = parameters.substring(++index);
			String[] arrayNameValue = aux.split("&");

			for (String nameValue : arrayNameValue) {
				String[] s = nameValue.split("=");
				this.parameters.put(s[0], s.length == 2 ? s[1] : null);
			}

		} else if (this.typeMethod == EnumTypeMethod.POST && this.headersRequest.get("Content-Type") != null && this.headersRequest.get("Content-Type").equals("multipart/form-data")) {
			String boundary = "--" + this.headersRequest.get("boundary");

			int initLine = 0;
			int untilNewLine = parameters.indexOf("\n");

			while (untilNewLine != -1 || parameters.length() < untilNewLine) {
				String line = parameters.substring(initLine, untilNewLine);

				if (line.indexOf(boundary) >= 0) {
					initLine = untilNewLine;
					untilNewLine = parameters.indexOf("\n", ++untilNewLine);
				}

				if (line.trim().length() == 0) {
					break;
				}

				line = parameters.substring(++initLine, untilNewLine);
				line = line.replace("Content-Disposition: form-data; name=", "");
				line = line.replaceAll("\n", "");
				line = line.replaceAll("\r", "");
				String name = line.replace("\"", "");

				int indexFileName = name.indexOf("filename=");

				if (indexFileName != -1 && parameterByte != null) {
					name = name.substring(indexFileName + 9, name.length());
					initLine = untilNewLine;

					untilNewLine = parameters.indexOf("\n", ++untilNewLine);

					String contentType = null;
					line = parameters.substring(++initLine, untilNewLine);

					// pegar o content-type do doc
					if (line.indexOf("Content-Type:") >= 0) {
						line = line.replace("Content-Type: ", "");
						contentType = line.substring(0, line.length());
						contentType = contentType.replace("\r", "");
						contentType = contentType.replace("\n", "");
					}

					int boundaryUntil = parameters.indexOf(boundary, untilNewLine);

//					int countTotal = boundaryUntil - untilNewLine + 4;

					byte[] data = new byte[parameterByte.length];
					for (int i = untilNewLine + 4; i < boundaryUntil-1; i++) { //TODO original untilNewLine + 1
						data[i - untilNewLine - 4] = parameterByte[i];
					}

//					byte[] data2 = new byte[countTotal];
//					for (int i = 0; i < data2.length; i++) {
//						data2[i] = data[i + untilNewLine + 1];
//					}

					untilNewLine = boundaryUntil;

					FileUpload file = new FileUpload();
					file.setName(name);
					file.setData(data);
					file.setContentType(contentType);
					
					this.files.add(file);
				}

				initLine = untilNewLine;
				untilNewLine = parameters.indexOf("\n", ++untilNewLine);

				if (untilNewLine == -1 || parameters.length() < untilNewLine) {
					continue;
				}

				line = parameters.substring(initLine, untilNewLine).trim();

				if (line.isEmpty()) {
					initLine = untilNewLine;
					untilNewLine = parameters.indexOf("\n", ++untilNewLine);
					line = parameters.substring(initLine, untilNewLine).trim();
				}

				this.parameters.put(name, line);
				initLine = untilNewLine;
				untilNewLine = parameters.indexOf("\n", ++untilNewLine);
			}

		} else if (this.typeMethod == EnumTypeMethod.POST) {
			String aux = parameters;
			String[] arrayNameValue = aux.split("&");

			for (String nameValue : arrayNameValue) {
				String[] s = nameValue.split("=");
				this.parameters.put(s[0], s.length == 2 ? s[1] : null);
			}

		}
	}

	/**
	 * Desmonta o header e coloca no Map headersRequest
	 * 
	 * @param header
	 */
	private void dismountHeaders(String header) {
		int index = header.indexOf(":");

		String key = header.substring(0, index);
		String value = header.substring(index + 1, header.length());

		// caso for esse cara abaixo, tem q tirar o boundary e da mesma linha e
		// colocar no map
		if (key.equalsIgnoreCase("Content-Type") && value.indexOf("multipart/form-data") >= 0) {
			String boundary = header.substring(header.indexOf("boundary"), header.length());

			String[] arrayBoundary = boundary.split("=");
			this.headersRequest.put(arrayBoundary[0], arrayBoundary[1]);

			int indexBoundary = header.indexOf("boundary");
			value = header.substring(++index, indexBoundary - 2).trim();
		}

		this.headersRequest.put(key, value);
	}

	public String getProtocol() {
		return this.protocol;
	}

	public String getPath() {
		return this.path;
	}

	@Override
	public List<Cookie> getCookies() {
		String strCookies = this.headersRequest.get("Cookie");

		if (strCookies == null || strCookies.trim().isEmpty()) {
			return null;
		}

		String[] arrayCookies = strCookies.split("; ");

		List<Cookie> cookies = new ArrayList<Cookie>();

		for (int i = 0; i < arrayCookies.length; i++) {
			String[] s = arrayCookies[i].split("=");

			Cookie c = new Cookie();
			c.setKey(s[0].trim());
			c.setValue(s[1].trim());
			cookies.add(c);
		}

		return cookies;
	}

	@Override
	public boolean isNull() {
		return this.byteStream.length == 0;
	}

}

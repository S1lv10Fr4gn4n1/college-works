package edu.org.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import edu.org.common.AbstractResponse;
import edu.org.common.PageNotFoundException;


public class ResponseDebug extends AbstractResponse {
	private static final String CONTENT_TYPE = "text/html";

	@Override
	protected String getContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public void write(OutputStream outputStream) throws PageNotFoundException {
		StringBuilder strBody = new StringBuilder();
		
		strBody.append("<html>").append("\n");
		strBody.append("	<head>").append("\n");
		strBody.append("		<title>Debugger</title>").append("\n");
		strBody.append("	</head>").append("\n");
		strBody.append("	<body>").append("\n");
		strBody.append("		<div align='center'><h1><b>Debugger</b></h1></div>").append("\n");
		strBody.append("		<br>").append("\n");

		strBody.append("		<br>").append("\n");
		strBody.append("		<b>Metodo</b>: " + this.typeMethod.getDomain()).append("\n");
		strBody.append("		<br>").append("\n");
		strBody.append("		<b>Path</b>: " + this.path).append("\n");
		strBody.append("		<br>").append("\n");
		strBody.append("		<br>").append("\n");
			
		strBody.append("		<b>Cabecalhos</b>").append("\n");
		strBody.append("		<table border='1'>").append("\n");
		
		Set<String> chaves = this.headerRequest.keySet();
		
		for (String chave: chaves) {  
			if(chave != null) {
				strBody.append("			<tr>").append("\n");
				strBody.append("				<td>").append("\n");
				strBody.append("					" + chave).append("\n");
				strBody.append("				</td>").append("\n");
				strBody.append("				<td>").append("\n");
				strBody.append("					" + this.headerRequest.get(chave)).append("\n");
				strBody.append("				</td>").append("\n");
				strBody.append("			</tr>").append("\n");
			}  
		}
		
		strBody.append("		</table>").append("\n");

		strBody.append("		<br>").append("\n");
		strBody.append("		<b>Parametros</b>").append("\n");
		strBody.append("		<table border='1'>").append("\n");
		
		chaves = this.parameters.keySet();
		
		for (String chave: chaves) {  
			if(chave != null) {
				strBody.append("			<tr>").append("\n");
				strBody.append("				<td>").append("\n");
				strBody.append("					" + chave).append("\n");
				strBody.append("				</td>").append("\n");
				strBody.append("				<td>").append("\n");
				strBody.append("					" + this.parameters.get(chave)).append("\n");
				strBody.append("				</td>").append("\n");
				strBody.append("			</tr>").append("\n");
			}
		}

		strBody.append("		</table>").append("\n");
		strBody.append("	</body>").append("\n");
		strBody.append("</html>").append("\n");

		try {
			this.makeHeader(strBody.toString().getBytes().length);
			outputStream.write(this.strHeader.toString().getBytes());
			outputStream.write(strBody.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

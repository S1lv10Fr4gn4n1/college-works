package edu.org.server;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import edu.org.application.RESTController;
import edu.org.application.ResponseREST;
import edu.org.common.Cookie;
import edu.org.common.IRequest;
import edu.org.common.IResponse;
import edu.org.common.PageNotFoundException;
import edu.org.common.PluginNotFoundException;
import edu.org.response.ResponseLogin;
import edu.org.server.utils.Config;
import edu.org.server.utils.PluginFactory;

public class ServerFlowControl {
	private SessionController			sessionController			= new SessionController();
	private AuthenticationController	authenticationController	= new AuthenticationController();
	private Socket						socket;

	public void execute() {
		int	port = Integer.valueOf(Config.getValue("port"));
		String folderRoot = Config.getValue("folderRoot");
		
		try {
			System.out.println("Iniciando Servidor");

			final ServerSocket serv = new ServerSocket(port);
			
			while (true) {
				System.out.println("Aguardando requisicoes");
				this.socket = serv.accept();
				
				OutputStream outputStream = null;

				try {
					outputStream = socket.getOutputStream();

					final IRequest request = new Request();
					request.readInputStream(this.socket.getInputStream());
					request.bindingDataRequest();
					
					if (request.isNull()) {
						continue;
					}

					// pega todos os cookies existentes na requisicao
					List<Cookie> cookies = request.getCookies();
					this.sessionController.setCookies(cookies);
					
					// verificar se existe sessao, se nao existir cria uma.
					SessionWeb session = this.sessionController.findSession();
					
					// cria um response default
					IResponse response = this.getResponse("");
					
					// verificar se esta autenticado
					String path = new String();
					path = request.getPath();
					
					if (!this.authenticationController.isAuthSession(session, request.getParameter(), path)) {
						response = this.authenticationController.loginPage();
					} 
					
					path = this.replaceEncode(request.getParameter().get("urlPosLogin"));
					
					if (!response.getClass().getName().equals(ResponseLogin.class.getName())) {
						// verifica se eh uma requisicao REST
						if (this.isRequestREST(request.getPath())) {
							response = new ResponseREST();
							RESTController rest = new RESTController(session);
							
							rest.doAction(request, response);
						} else {
							response = this.getResponse(request.getPath());
							// manda no cabecalho um refresh para colocar a url correta
//							if (response.getClass().getName().equals(ResponseGeneric.class.getName()) && (path != null)) {
//								response.getHeaders().put("refresh", "0;url=http://localhost:" + port + path);
//							}
						}
					}
					
					
					if ((request.getPath() != null && request.getPath().equals(path)) || path == null) {
						response.init(request.getPath(), request.getParameter(), request.getHeaderRequest(), request.getMethod());
						System.out.println(response.getClass().getName() + " " + request.getPath());
					} else {
						response.init(path, request.getParameter(), request.getHeaderRequest(), request.getMethod());
						System.out.println(response.getClass().getName() + " " + path);
					}
					
					response.setFolderRoot(folderRoot);
					response.setCookies(session.getCookies());
					try {
						response.write(outputStream);
					} catch (PageNotFoundException e) {
						e.printStackTrace();
						outputStream.write(this.getPageError(e.getMessage()).getBytes());
					}
				} catch (PluginNotFoundException e) {
					outputStream.write(this.getPluginError(e.getMessage()).getBytes());
				} finally {
					outputStream.close();
					System.out.println("Fim da requisicao");
					this.socket.close();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isRequestREST(String path) {
		if (path.indexOf("/app/_REST_") != -1) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private String replaceEncode(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}

		return URLDecoder.decode(path);
	}

	private synchronized IResponse getResponse(String path) throws PluginNotFoundException {
		path = path != null ? path : "";
		String tmpExt = this.getExtenseFile(path.toCharArray());

		return PluginFactory.getPlugin(tmpExt);
	}

	private synchronized String getExtenseFile(char[] cs) {
		String tmpExt = "";
		for (int i = cs.length - 1; i > 0; i--) {
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

	private String getPageError(String message) {
		String msgError = "<div align='center'><b><h1>404 =P</b></h1><br><br><h4>" + (message != null ? message : "") + "</h4></div";

		StringBuilder error = new StringBuilder();
		error.append("HTTP/1.1 404 ERROR FAIL").append("\r\n");
		error.append("Content-Length: " + msgError.getBytes().length).append("\r\n");
		;
		error.append("Date: " + new Date()).append("\r\n");
		error.append("Content-type: text/html").append("\r\n");
		error.append("\r\n");
		error.append(msgError);

		return error.toString();
	}

	private String getPluginError(String message) {
		String msgError = "<div align='center'><b><h1>Erro 909 =( <br><br></b></h1> <h4>" + (message != null ? message : "") + "</h4></div";

		StringBuilder error = new StringBuilder();
		error.append("HTTP/1.1 404 ERROR FAIL PLUGIN").append("\r\n");
		error.append("Content-Length: " + msgError.getBytes().length).append("\r\n");
		;
		error.append("Date: " + new Date()).append("\r\n");
		error.append("Content-type: text/html").append("\r\n");
		error.append("\r\n");
		error.append(msgError);

		return error.toString();
	}
}

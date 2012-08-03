package edu.org.server;

import java.util.List;
import java.util.Map;

import edu.org.common.Cookie;
import edu.org.common.IResponse;
import edu.org.common.PageNotFoundException;
import edu.org.common.PluginNotFoundException;
import edu.org.server.utils.Config;
import edu.org.server.utils.ManagerUser;
import edu.org.server.utils.PluginFactory;

public class AuthenticationController {

	public synchronized boolean isAuthSession(SessionWeb session, Map<String, String> parameters, String path) {
		Cookie cookieAuth = this.findCookieAuthentication(session.getCookies());
		
		// validar a autenticacao
		if (this.validateAuthentication(session, parameters, path)) {
			return true;
		}
		
		if (cookieAuth != null && cookieAuth.getValue().equals(session.getAuthenticationCookie().getValue())) {
			session.replaceCookieAuthentication();
			return true;
		}

		return false;
	}

	private synchronized boolean validateAuthentication(SessionWeb session, Map<String, String> parameters, String path) {
		if (path != null && (path.indexOf("/loginauthentication") >= 0)) {
			String user = parameters.get("login");
			String pass = parameters.get("pass");
			
			if (ManagerUser.findUser(user, pass)) {
				session.getCookies().add(session.getAuthenticationCookie());
				return true;
			}
			
			ManagerUser.persistUser("abacate", "aba", "123");
		}
		
		return false;
	}

	private Cookie findCookieAuthentication(List<Cookie> cookies) {
		if (cookies == null || cookies.size() == 0) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getKey().equals(Cookie.AMSAuthenticationId)) {
				return cookie;
			}
		}

		return null;
	}

	public synchronized IResponse loginPage() throws PageNotFoundException, PluginNotFoundException {
		return this.createLoginResponse();
	}
	
	private IResponse createLoginResponse() throws PluginNotFoundException {
		String folder = Config.getValue("folderLib");
		return PluginFactory.getPlugin(folder + "pluginGeneric.jar", "edu.org.response.ResponseLogin");
	}
}

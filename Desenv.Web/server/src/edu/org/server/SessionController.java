package edu.org.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.org.common.Cookie;

public class SessionController {
	private Map<String, SessionWeb>	mapSessions	= new HashMap<String, SessionWeb>();
	private List<Cookie>			cookies		= new ArrayList<Cookie>();

	public synchronized SessionWeb findSession() {
		// procura o cookie referente a sessao
		Cookie cookieSession = this.findCookieSession(this.cookies);

		Set<String> keySet = this.mapSessions.keySet();

		for (String key : keySet) {
			SessionWeb session = this.mapSessions.get(key);

			if (cookieSession != null && cookieSession.getValue().equals(session.getSessionCookie().getValue())) {
				session.setCookies(this.cookies);
				session.replaceCookieSession();
				return session;
			}
		}

		// cria a nova sessao
		SessionWeb newSession = new SessionWeb();
		this.cookies.add(newSession.getSessionCookie());
		newSession.setCookies(this.cookies);
		this.mapSessions.put(newSession.getSessionCookie().getValue(), newSession);

		return newSession;
	}

	private Cookie findCookieSession(List<Cookie> cookies) {
		if (cookies == null || cookies.size() == 0) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getKey().equals(Cookie.AMSSessionId)) {
				return cookie;
			}
		}

		return null;
	}

	public void setCookies(List<Cookie> cookies) {
		if (cookies != null) {
			this.cookies = cookies;
		} else {
			this.cookies = new ArrayList<Cookie>();
		}
	}

	public List<Cookie> getCookies() {
		return this.cookies;
	}
}

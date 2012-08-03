package edu.org.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.org.common.Cookie;
import edu.org.server.utils.Config;

public class SessionWeb {
	private Map<String, Object>	attributes	= new HashMap<String, Object>();
	private Cookie				sessionCookie;
	private Cookie				authenticationCookie;
	private List<Cookie>		cookies		= new ArrayList<Cookie>();

	public SessionWeb() {
		// cria os cookies relationados a sessao
		Long sessionTimeOut = Long.parseLong(Config.getValue("sessionTimeOut").trim());
		this.sessionCookie = new Cookie();
		this.sessionCookie.setKey(Cookie.AMSSessionId);
		this.sessionCookie.setValue(Cookie.AMSSessionId + this.generateId());
		this.sessionCookie.setDuration(sessionTimeOut);

		Long authenticationTimeOut = Long.parseLong(Config.getValue("authenticationTimeOut").trim());
		this.authenticationCookie = new Cookie();
		this.authenticationCookie.setKey(Cookie.AMSAuthenticationId);
		this.authenticationCookie.setValue(Cookie.AMSAuthenticationId + this.generateId());
		this.authenticationCookie.setDuration(authenticationTimeOut);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}

	public Cookie getSessionCookie() {
		return this.sessionCookie;
	}

	public void setSessionCookie(Cookie sessionCookie) {
		this.sessionCookie = sessionCookie;
	}

	public Cookie getAuthenticationCookie() {
		return authenticationCookie;
	}

	public void setAuthenticationCookie(Cookie authenticationCookie) {
		this.authenticationCookie = authenticationCookie;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public void updateDurationCookieSession() {
		Long sessionTimeOut = Long.parseLong(Config.getValue("sessionTimeOut"));
		this.sessionCookie.setDuration(sessionTimeOut);
	}

	public void updateDutationCookieAuthentication() {
		Long authenticationTimeOut = Long.parseLong(Config.getValue("authenticationTimeOut"));
		this.sessionCookie.setDuration(authenticationTimeOut);
	}

	private String generateId() {
		Random random = new Random();
		double id = (random.nextGaussian() * Math.PI);

		id = Math.random() + id;
		
//		MessageDigest digest = null;
//
//		try {
//			digest = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		digest.update(id.getBytes());

		return String.valueOf(id);

	}

	public void replaceCookieSession() {
		for(Cookie c: this.cookies) {
			if (c.getKey().equals(Cookie.AMSSessionId)) {
				this.cookies.remove(c);
				this.cookies.add(this.sessionCookie);
				return;
			}
		}
	}

	public void replaceCookieAuthentication() {
		for(Cookie c: this.cookies) {
			if (c.getKey().equals(Cookie.AMSAuthenticationId)) {
				this.cookies.remove(c);
				this.cookies.add(this.authenticationCookie);
				return;
			}
		}
	}
}

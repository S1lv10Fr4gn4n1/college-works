package edu.org.server.utils;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ManagerUser {
	
	public static boolean findUser(String user, String pass) {
		try {
			String folderRoot = Config.getValue("folderRoot");
			folderRoot += "/authentication/users.xml";
			
			DocumentBuilderFactory dbf;
			DocumentBuilder db;
			Document doc;

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(folderRoot);

			Element elem = doc.getDocumentElement();

			// pega todos os elementos da tabela do XML
			NodeList nodeTable = elem.getElementsByTagName("users");

			for (int i = 0; i < nodeTable.getLength(); i++) {
				Element tagTable = (Element) nodeTable.item(i);
				NodeList listNodeField = tagTable.getElementsByTagName("user");

				for (int j = 0; j < listNodeField.getLength() ; j++) {
					Element tagField = (Element) listNodeField.item(j);
					
					//tagField.getAttribute("name").toLowerCase();
					String loginXml = tagField.getAttribute("login");
					String passXml =  tagField.getAttribute("pass");
					
					if (user.equals(loginXml) && pass.equals(passXml)) {
						return true;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return false;
	}
	
	public static void persistUser(String name, String login, String pass) {
		try {
			String folderRoot = Config.getValue("folderRoot");
			folderRoot += "/authentication/users.xml";
			
			DocumentBuilderFactory dbf;
			DocumentBuilder db;
			Document doc;

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(folderRoot);

			Element element = doc.createElement("user");
			
			//cria o atributo id e gravado
			element.setAttribute("name", name);
			element.setAttribute("login", login);
			element.setAttribute("pass", pass);
			
			Node elementUsers = doc.getElementsByTagName("users").item(0);
			elementUsers.appendChild(element);

			// grava o xml alterado
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(folderRoot));
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

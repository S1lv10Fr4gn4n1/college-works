package memoryManager;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReadSchema {
	private Map<String, StructTable>	listStruct	= new LinkedHashMap<String, StructTable>();

	
	public Map<String, StructTable> getListStruct() {
		return this.listStruct;
	}

	public void loadSchema(String pathXml) {
		try {
			DocumentBuilderFactory dbf;
			DocumentBuilder db;
			Document doc;

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(pathXml);


			Element elem = doc.getDocumentElement();

			// pega todos os elementos da tabela do XML
			NodeList listNodeTable = elem.getElementsByTagName("Tabela");

			// percorre cada elemento tabela encontrado
			for (int i = 0; i < listNodeTable.getLength(); i++) {
				Element tagTable = (Element) listNodeTable.item(i);

				// pega os dados cadastrado para a tabela atual
				String nameTable = tagTable.getAttribute("Nome").toLowerCase();
				StructTable structTable = new StructTable(nameTable);
				
				NodeList nodeFields = tagTable.getElementsByTagName("Campos");
				
				Element tagFiels = (Element) nodeFields.item(0);
				
				NodeList listNodeField = tagFiels.getElementsByTagName("Campo");
				
				
				int initAddress = 0;
				
				// percorre cada elemento da tag campos encontrado
				for (int j = 0; j < listNodeField.getLength() ; j++) {
					Element tagField = (Element) listNodeField.item(j);
					
					Field field = new Field();
					field.setName(tagField.getAttribute("Nome").toLowerCase());

					field.setType(TypeField.valueOf(tagField.getAttribute("Tipo").toUpperCase()));
					
					field.setSize(field.getType().getSize());
					
					if (field.getType() == TypeField.STRING) {
						field.setSize(TypeField.STRING.getSize()*2);
//						field.setSize(TypeField.STRING.getSize());
						
						if (!tagField.getAttribute("Tamanho").isEmpty()) {
							int sizeString = Integer.parseInt(tagField.getAttribute("Tamanho"));
							field.setSize(sizeString * 2); //REVER
//							field.setSize(sizeString);
						}
					}
						
					field.setSearch(false);
					if (tagField.getAttribute("Pesquisar").equalsIgnoreCase("true")) 
						field.setSearch(true);
					
					
					field.setInit(initAddress);
					initAddress += field.getSize();
					
					structTable.addField(field);
				}

				this.listStruct.put(structTable.getNameStruct(), structTable);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

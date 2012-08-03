package memoryManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ImportDataText {
	private String	tableName;
	private BufferPool bufferPool;
	
	public void setBufferPool(BufferPool bufferPool) {
		this.bufferPool = bufferPool;
	}
	
	public void loadFile(String filePath) throws Exception {
		File file = new File(filePath);
		
		if (!file.exists()) {
			throw new Exception("Arquivo " + filePath + " não exite");
		}
		
		StructTable table = this.bufferPool.getListStructTable().get(this.tableName);

		if (table == null)
			throw new Exception("Problemas, jose!\nTabela " + this.tableName + " Nao existe");

		BufferedReader in = new BufferedReader(new FileReader(filePath));
		String str;
		
		// uma leitura, pois a primeira linha sao os nomes dos campos
		String[] nameFields = in.readLine().toLowerCase().split(";");
		
		while ((str = in.readLine()) != null) {
			this.loadDataFields(str, nameFields, table);
		}
	}

	private void loadDataFields(String strDataField, String[] strNameFields, StructTable table) throws Exception {
		String[] dataFields = strDataField.split(";");
		
		List<FieldValue> listFieldValue = new ArrayList<FieldValue>();
		String valueFieldSearch = "";
		
		int i = 0;
//		for (int i = 0; i < strNameFields.length; i++) {
		for (Field field : table.getListFields().values()) {
//			String nameField = strNameFields[i].trim().replace("\"", "");
			
//			Field field = table.getField(nameField);
			
//			if (field == null) {
//				throw new Exception("Campo " + nameField + " não consta na tabela " + tableName);
//				throw new Exception("Campo " + field.getName() + " não consta na tabela " + tableName);
//			}
			
			FieldValue fieldValue = new FieldValue(field);
			
			if (dataFields.length > i)
				fieldValue.setValue(dataFields[i]);
			
			if (fieldValue.getSearch())
				valueFieldSearch = fieldValue.getValue();
			
			listFieldValue.add(fieldValue);
			
			i++;
		}
		
		this.bufferPool.insert(table.getNameStruct(), valueFieldSearch, listFieldValue);

	}

	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}
	
}

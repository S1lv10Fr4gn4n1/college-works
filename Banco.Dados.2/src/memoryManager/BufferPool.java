package memoryManager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BufferPool {
	private ByteBuffer					bufferPool;

	private final static int			sizePage	= 1024*2;
	private final static int			countPage	= 4;

	private List<Page>					listPages	= new ArrayList<Page>();
	private Map<String, StructTable>	listTable	= new LinkedHashMap<String, StructTable>();

	public BufferPool() {
		// criar o buffer pool
		this.bufferPool = ByteBuffer.allocate(sizePage * countPage);

		// criar as paginas
		for (int i = 0; i < countPage; i++) {
			Page page = new Page();
			page.setInit_address(i * sizePage);

			this.listPages.add(page);
		}
	}

	public void loadSchema(String pathXml) {
		ReadSchema read = new ReadSchema();
		read.loadSchema(pathXml);

		this.listTable = read.getListStruct();
	}

	public Map<String, StructTable> getListStructTable() {
		return this.listTable;
	}

	public void insert(String tableName, String valueSearchField, List<FieldValue> fieldValue) throws Exception {
		tableName = tableName.toLowerCase();

		StructTable table = this.listTable.get(tableName);

		// verificar se tem alguma pagina com a table e que tenha espaco para se colocado
		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName) && !page.IsFull()) {
				page.incPin_count();
				this.insertRegisterInPage(page, table, valueSearchField, fieldValue);
				page.decPin_count();

				return;
			}
		}

		// procura por uma pagina vazia
		for (Page page : this.listPages) {
			if (page.getTable().isEmpty()) {
				page.incPin_count();
				this.insertRegisterInPage(page, table, valueSearchField, fieldValue);
				page.decPin_count();

				return;
			}
		}

		// se nao tiver pagina de conteudo de uma determinada tabela e nao tiver paginas
		// livre, causa excecao
		throw new Exception("Nao existem mais paginas livres!");
	}

	private void insertRegisterInPage(Page page, StructTable table, String valueSearchField, List<FieldValue> listFieldValue) throws Exception {
		// atualiza controle de informacoes da pagina

		// nova pagina
		if (page.getTable().isEmpty()) {
			page.setTable(table.getNameStruct());
			page.setPage_id(table.getNameStruct() + valueSearchField);

			// calcula quantos registro porem caber nessa pagina para essa tabela
			page.setMaxRegister(sizePage / table.getSumByteFromStruct());

			if (page.getMaxRegister() == 0) {
				page.decPin_count();
				throw new Exception("Tabela maior que o tamanho da pagina");
			}
		}

		page.addRegister();

		for (FieldValue f : listFieldValue) {
			// grava o valor do campo informado
			int initAddress = page.getInit_address();
			initAddress += page.getNextRegister() * table.getSumByteFromStruct();
			initAddress += f.getInit();

			byte[] data = ConversionByteArray.convertFieldToByteArray(f, f.getValue());

			for (int i = 0; i < f.getSize(); i++) {
				this.bufferPool.put(initAddress + i, data[i]);
			}
		}
	}

	public void insert(String tableName, String valueSearchField, String nameField, String valueField) throws Exception {
		tableName = tableName.toLowerCase();
		nameField = nameField.toLowerCase();

		StructTable table = this.listTable.get(tableName);

		Field field = table.getField(nameField);

		if (field == null) {
			throw new Exception("Field \"" + nameField + "\" nao foi encontrado");
		}

		// verificar se tem alguma pagina com a table e que tenha espaco para se colocado
		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName) && !page.IsFull()) {
				page.incPin_count();
				this.insertRegisterInPage(page, table, valueSearchField, field, valueField);
				page.decPin_count();

				return;
			}
		}

		// procura por uma pagina vazia
		for (Page page : this.listPages) {
			if (page.getTable().isEmpty()) {
				page.incPin_count();
				this.insertRegisterInPage(page, table, valueSearchField, field, valueField);
				page.decPin_count();

				return;
			}
		}

		// se nao tiver pagina de conteudo de uma determinada tabela e nao tiver paginas
		// livre, causa excecao
		throw new Exception("Nao existem mais paginas livres!");
	}

	private void insertRegisterInPage(Page page, StructTable table, String valueSearchField, Field field, String valueField) throws Exception {
		// atualiza controle de informacoes da pagina

		// nova pagina
		if (page.getTable().isEmpty()) {
			page.setTable(table.getNameStruct());
			page.setPage_id(table.getNameStruct() + valueSearchField);

			// calcula quantos registro porem caber nessa pagina para essa tabela
			page.setMaxRegister(sizePage / table.getSumByteFromStruct());

			if (page.getMaxRegister() == 0) {
				page.decPin_count();
				throw new Exception("Tabela maior que o tamanho da pagina");
			}
		}

		page.addRegister();

		//		this.listPages.add(page);

		// INSERINDO REGISTRO
		// grava o campo search
		Field searchField = table.getSearchField();

		int initAddress = page.getInit_address();
		initAddress += page.getNextRegister() * table.getSumByteFromStruct();
		initAddress += searchField.getInit();

		byte[] data = ConversionByteArray.convertFieldToByteArray(searchField, valueSearchField);

		for (int i = 0; i < searchField.getSize(); i++) {
			this.bufferPool.put(initAddress + i, data[i]);
		}

		// grava o valor do campo informado
		initAddress = page.getInit_address();
		initAddress += page.getNextRegister() * table.getSumByteFromStruct();
		initAddress += field.getInit();

		data = ConversionByteArray.convertFieldToByteArray(field, valueField);

		for (int i = 0; i < field.getSize(); i++) {
			this.bufferPool.put(initAddress + i, data[i]);
		}
	}

	public void update(String tableName, String valueSearchField, List<FieldValue> listFieldValue) throws Exception {
		tableName = tableName.toLowerCase();
		StructTable table = this.listTable.get(tableName);
		Field searchField = table.getSearchField();

		// encontra a pagina com a tabela
		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName)) {
				for (int i = 0; i < page.getMaxRegister(); i++) {
					int initAddress = page.getInit_address();
					initAddress += i * table.getSumByteFromStruct();
					initAddress += searchField.getInit();

					byte[] data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, searchField.getSize());

					if (ConversionByteArray.convertByArrayByType(searchField.getType(), data).equalsIgnoreCase(valueSearchField)) {
						page.incPin_count();
						this.updateRegisterInPage(page, table, valueSearchField, listFieldValue);
						page.decPin_count();

						return;
					}
				}
			}
		}
	}

	private void updateRegisterInPage(Page page, StructTable table, String valueSearchField, List<FieldValue> listFieldValue) throws Exception {
		// atualiza controle de informacoes da pagina
		page.setDirty(true);

		int initAddress = page.getInit_address();

		// limpar antes de colocar o novo registro
		ConversionByteArray.clearBuffeInField(this.bufferPool, initAddress, table.getSumByteFromStruct());

		for (FieldValue fieldValue : listFieldValue) {
			// pega o endereco aonde comeca a pagina
			initAddress = page.getInit_address();
			initAddress += fieldValue.getInit();

			// consiste o value com o tipo e converte
			byte[] data = ConversionByteArray.convertFieldToByteArray(fieldValue, fieldValue.getValue());

			for (int i = 0; i < fieldValue.getSize(); i++) {
				this.bufferPool.put(initAddress + i, data[i]);
			}
		}
	}

	public void update(String tableName, String valueSearchField, String nameField, String valueField) throws Exception {
		tableName = tableName.toLowerCase();
		nameField = nameField.toLowerCase();

		StructTable table = this.listTable.get(tableName);

		Field field = table.getField(nameField);
		Field searchField = table.getSearchField();

		if (field == null) {
			throw new Exception("Field " + nameField + " nao foi encontrado");
		}

		// encontra a pagina com a tabela
		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName)) {
				for (int i = 0; i < page.getMaxRegister(); i++) {
					int initAddress = page.getInit_address();
					initAddress += i * table.getSumByteFromStruct();
					initAddress += searchField.getInit();

					byte[] data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, searchField.getSize());

					if (ConversionByteArray.convertByArrayByType(searchField.getType(), data).equalsIgnoreCase(valueSearchField)) {
						page.incPin_count();
						this.updateRegisterInPage(page, table, valueSearchField, field, valueField);
						page.decPin_count();

						return;
					}
				}
			}
		}
	}

	private void updateRegisterInPage(Page page, StructTable table, String valueSearchField, Field field, String valueField) throws Exception {
		// atualiza controle de informacoes da pagina
		page.setDirty(true);

		// pega o endereco aonde comeca a pagina
		int initAddress = page.getInit_address();
		initAddress += field.getInit();

		// consiste o value com o tipo e converte
		byte[] data = ConversionByteArray.convertFieldToByteArray(field, valueField);

		// limpar antes de colocar o novo registro
		ConversionByteArray.clearBuffeInField(this.bufferPool, initAddress, field.getSize());

		for (int i = 0; i < field.getSize(); i++) {
			this.bufferPool.put(initAddress + i, data[i]);
		}
	}

	public void delete(String tableName, String valueSearchField) throws Exception {
		tableName = tableName.toLowerCase();

		StructTable table = this.listTable.get(tableName);
		Field searchField = table.getSearchField();

		for (Page page : this.listPages) {

			if (page.getTable().equalsIgnoreCase(tableName)) {
				for (int i = 0; i < page.getMaxRegister(); i++) {
					int initAddress = page.getInit_address();
					initAddress += i * table.getSumByteFromStruct();
					initAddress += searchField.getInit();

					byte[] data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, searchField.getSize());

					if (ConversionByteArray.convertByArrayByType(searchField.getType(), data).equalsIgnoreCase(valueSearchField)) {
						page.incPin_count();

						initAddress -= searchField.getInit();
						ConversionByteArray.clearBuffeInField(this.bufferPool, initAddress, table.getSumByteFromStruct());

						page.delRegister();
						page.decPin_count();

						return;
					}
				}
			}
		}
	}

	public List<FieldValue> showFieldValue(String tableName, String valueSearchField) throws Exception {
		tableName = tableName.toLowerCase();

		StructTable table = this.listTable.get(tableName);

		Field searchField = table.getSearchField();

		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName)) {
				for (int i = 0; i < page.getMaxRegister(); i++) {
					int initAddress = page.getInit_address();
					initAddress += i * table.getSumByteFromStruct();
					initAddress += searchField.getInit();

					byte[] data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, searchField.getSize());

					if (ConversionByteArray.convertByArrayByType(searchField.getType(), data).equalsIgnoreCase(valueSearchField)) {
						List<FieldValue> listFieldValue = new ArrayList<FieldValue>();

						initAddress -= searchField.getInit();

						Set<String> s = table.getListFields().keySet();

						for (Iterator<String> it = s.iterator(); it.hasNext();) {
							String value = it.next();
							Field field = table.getListFields().get(value);

							FieldValue fieldValue = new FieldValue(field);

							data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, field.getSize());
							String dataValue = ConversionByteArray.convertByArrayByType(field.getType(), data);

							initAddress += fieldValue.getSize();

							fieldValue.setValue(dataValue);
							listFieldValue.add(fieldValue);
						}

						return listFieldValue;
					}
				}
			}
		}
		return null;
	}

	public String showFieldValue(String tableName, String valueSearchField, String nameField) throws Exception {
		tableName = tableName.toLowerCase();
		nameField = nameField.toLowerCase();

		StructTable table = this.listTable.get(tableName);
		Field field = table.getField(nameField);

		if (field == null) {
			throw new Exception("Field " + nameField + " nao foi encontrado");
		}

		Field searchField = table.getSearchField();

		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName)) {
				for (int i = 0; i < page.getMaxRegister(); i++) {
					int initAddress = page.getInit_address();
					initAddress += i * table.getSumByteFromStruct();
					initAddress += searchField.getInit();

					byte[] data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, searchField.getSize());

					if (ConversionByteArray.convertByArrayByType(searchField.getType(), data).equalsIgnoreCase(valueSearchField)) {
						initAddress -= searchField.getInit();

						initAddress += field.getInit();
						data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, field.getSize());
						String result = ConversionByteArray.convertByArrayByType(field.getType(), data);

						return result;
					}
				}
			}
		}

		return "";
	}

	public boolean thereRegister(String tableName, String valueSearchField) {
		tableName = tableName.toLowerCase();

		StructTable table = this.listTable.get(tableName);
		Field searchField = table.getSearchField();

		for (Page page : this.listPages) {
			if (page.getTable().equalsIgnoreCase(tableName)) {
				for (int i = 0; i < page.getMaxRegister(); i++) {
					int initAddress = page.getInit_address();
					initAddress += i * table.getSumByteFromStruct();
					initAddress += searchField.getInit();

					byte[] data = ConversionByteArray.getByteArrayFromBuffer(this.bufferPool, initAddress, searchField.getSize());

					if (ConversionByteArray.convertByArrayByType(searchField.getType(), data).equalsIgnoreCase(valueSearchField)) {
						return true;
					}
				}
			}
		}

		return false;
	}

}

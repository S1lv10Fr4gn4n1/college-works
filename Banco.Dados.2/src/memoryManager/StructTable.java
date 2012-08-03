package memoryManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class StructTable {
	private String				nameStruct;
	private Map<String, Field>	listFields	= new LinkedHashMap<String, Field>();

	public StructTable(String nameStruct) {
		this.nameStruct = nameStruct;
	}

	public void addField(Field field) {
		this.listFields.put(field.getName(), field);
	}

	public int getSumByteFromStruct() {
		Set<String> s = this.listFields.keySet();

		int sumByteStruct = 0;

		for (Iterator<String> it = s.iterator(); it.hasNext();) {
			String value = it.next();
			Field field = this.listFields.get(value);

			sumByteStruct += field.getSize();
		}

		return sumByteStruct;
	}

	public void setNameStruct(String nameStruct) {
		this.nameStruct = nameStruct;
	}

	public String getNameStruct() {
		return this.nameStruct;
	}

	public Field getField(String nameField) {
		Collection<Field> values = this.listFields.values();
		for (final Field field : values) {
			if (field.getName().equalsIgnoreCase(nameField))
				return field;
		}
		
		return this.listFields.get(nameField);
	}
	
	public Field getField(final int fieldIndex) {
		final Field[] fields = this.listFields.values().toArray(new Field[this.listFields.size()]);
		return fields[fieldIndex];
	}
	
	public Map<String, Field> getListFields() {
		return this.listFields;
	}

	public Field getSearchField() {
		Field field = null;
		
		Set<String> s = this.listFields.keySet();

		for (Iterator<String> it = s.iterator(); it.hasNext();) {
			String value = it.next();
			field = this.listFields.get(value);
			
			if (field.getSearch())
				return field;
		}

		return null;
	}
}

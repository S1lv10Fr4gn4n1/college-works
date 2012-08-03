package memoryManager;

public class FieldValue extends Field {
	private String value;
	
	public FieldValue(Field field) {
		this.setInit(field.getInit());
		this.setName(field.getName());
		this.setSearch(field.getSearch());
		this.setType(field.getType());
		this.setSize(field.getSize());
		
		if (this.getType() == TypeField.STRING)
			this.setValue("");
		else
			this.setValue("0");
	}

	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}

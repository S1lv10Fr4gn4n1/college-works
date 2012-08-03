package memoryManager;

public enum TypeField {
	STRING("String", 10),

	INTEGER("Integer", 4),
	
	DATETIME("DateTime", 8),
	
	FLOAT("Float", 4),
	
	BOOL("Bool", 1),
	
	BYTE("Byte", 1);
	
    private final String description;
    private final int 	 size;

    private TypeField(final String description, final int size) {
    	this.description = description;
    	this.size 		 = size;
    }

    public String getDescription() {
		return this.description;
	}
    
    public int getSize() {
		return this.size;
	}
    
    public String toString() {
    	return this.getDescription();
    }
    
}

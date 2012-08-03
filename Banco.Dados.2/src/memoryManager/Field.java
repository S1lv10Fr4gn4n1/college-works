package memoryManager;

public class Field {
	private String		name;
	private int			size;
	private TypeField	type;
	private int 		init;
	private boolean 	search;
	
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setType(TypeField type) {
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public TypeField getType() {
		return this.type;
	}
	
	public boolean getSearch() {
		return this.search;
	}
	
	public void setSearch(boolean search) {
		this.search = search;
	}
	
	public int getInit() {
		return this.init;
	}
	
	public void setInit(int init) {
		this.init = init;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}

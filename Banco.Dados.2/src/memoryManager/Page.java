package memoryManager;

public class Page {
	private boolean	dirty;
	private int		pin_count;
	private String	page_id;
	private String	table;
	private int		init_address;
	private int 	totRegister;
	private int 	maxRegister;

	public Page() {
		this.dirty = false;
		this.pin_count = 0;
		this.page_id = "";
		this.table = "";
		this.init_address = 0;
		this.totRegister = 0;
		this.maxRegister = 0;
	}

	public int getInit_address() {
		return this.init_address;
	}

	public void setInit_address(int initAddress) {
		this.init_address = initAddress;
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public int getPin_count() {
		return this.pin_count;
	}

	public void incPin_count() {
		this.pin_count++;
	}
	
	public void decPin_count() {
		this.pin_count--;
	}

	public String getPage_id() {
		return this.page_id;
	}

	public void setPage_id(String pageId) {
		this.page_id = pageId;
	}

	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
	public boolean IsFull() {
		return this.maxRegister == this.totRegister;
	}
	
	public void setMaxRegister(int maxRegister) {
		this.maxRegister = maxRegister;
	}
	
	public int getMaxRegister() {
		return this.maxRegister;
	}
	
	public void addRegister() {
		this.totRegister++;
	}
	
	public void delRegister() {
		this.totRegister--;
	}

	public int getNextRegister() {
		return this.totRegister - 1;
	}

}

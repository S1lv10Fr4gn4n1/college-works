package listaduplacirc;

public class NoListaDupla {
	
	private int info;
	private NoListaDupla prox;
	private NoListaDupla ant;
	
	public NoListaDupla() {
		this.setAnt(null);
		this.setProx(null);
	}	
	
	public int getInfo() {
		return info;
	}
	
	public void setInfo(int info) {
		this.info = info;
	}
	
	public NoListaDupla getProx() {
		return prox;
	}
	
	public void setProx(NoListaDupla prox) {
		this.prox = prox;
	}
	
	public NoListaDupla getAnt() {
		return ant;
	}
	
	public void setAnt(NoListaDupla ant) {
		this.ant = ant;
	}
	
	public String toString() {
		return String.valueOf(this.getInfo());
	}
}

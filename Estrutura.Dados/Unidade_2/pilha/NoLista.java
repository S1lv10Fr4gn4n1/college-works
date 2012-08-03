package pilha;

public class NoLista {
	private float info;
	private NoLista prox;
	
	public NoLista() {
		this.setProx(null);
	}
	public float getInfo() {
		return info;
	}
	public void setInfo(float info) {
		this.info = info;
	}
	public NoLista getProx() {
		return prox;
	}
	public void setProx(NoLista prox) {
		this.prox = prox;
	}
	
	public String toString() {	
		return String.valueOf(this.getInfo());
	}
}

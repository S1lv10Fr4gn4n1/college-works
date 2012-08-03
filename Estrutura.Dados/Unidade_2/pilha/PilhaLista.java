package pilha;

public class PilhaLista implements Pilha{
	private NoLista prin;
	
	public PilhaLista() {
		this.prin = null;	}
	
	public void libera() {
		this.prin = null;		
	}
	
	public float pop() throws Exception {
		if (!this.vazia()) {
			float v = this.prin.getInfo();
			this.prin = this.prin.getProx();
			return v;
		} else {
			throw new Exception("Erro: pilha vazia");
		}
	}
	
	public void push(float v) throws Exception {
		NoLista novo = new NoLista();
		novo.setInfo(v);
		novo.setProx(prin);
		this.prin = novo;	
	}
	
	public float top() throws Exception {
		if (!this.vazia()) 
			return this.prin.getInfo();
		else throw new Exception("Erro: pilha vazia");		
	}
	
	public boolean vazia() {		
		return this.prin == null;
	}	
	
	public String toString() {
		String str = "";
		NoLista p = this.prin;
		
		while (p != null) {
			str += p.getInfo() + " ";
			p = p.getProx();
		}
		return str;
	}	
}

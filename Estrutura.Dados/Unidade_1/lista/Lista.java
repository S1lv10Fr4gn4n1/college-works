package lista;
public class Lista {	
	
	private NoLista prin;
	
	public Lista() {
		this.setPrin(null);
	}
	
	public void insere(int v) {
		NoLista novo = new NoLista();
		novo.setInfo(v);
		novo.setProx(prin);
		this.prin = novo;
	}
	
	public void imprime() {
		NoLista p = this.prin;
		
		while (p != null) {
			System.out.print(p.getInfo() + " ");
			p = p.getProx();
		}		
	}
	
	public String toString() {		
		return ("");
	}
	
	public boolean vazia() {
		if (this.prin == null)
			return true;
		else
			return false;
	}
	
	public NoLista busca(int v) {
		NoLista p = this.prin;
		
		while (p != null) {
			if (p.getInfo() == v)
				return p;	
			p = p.getProx();
		}
		return p;
	}
	
	public int comprimento() {
		int qtd = 0;
		NoLista p = new NoLista();
		for(p=this.prin; p!=null;p=p.getProx())
			qtd++;
		
		return qtd;
	}
	
	public NoLista ultimo() {
		NoLista p = this.prin;
		while (p.getProx() != null) {
			p = p.getProx();
		}		
					
		return p;
	}

	public NoLista getPrin() {
		return this.prin;
	}

	public void setPrin(NoLista prin) {
		this.prin = prin;
	}
	
	// PDF lab_02 novos metodos a para a lista
	
	public void retira(int v) {
		NoLista p = this.prin;
		NoLista ant = null;
		
		//procuta elemento e guarda anterior
		while ((p.getProx() != null) && (p.getInfo() != v)) {
			ant = p;
			p = p.getProx();
		}
		
		// nao acho elemento
		if (p == null) {
			return;
		}
		
		if (ant == null) {
			this.prin = p.getProx();
		} else {
			ant.setProx(p.getProx());
		}
	}
	
	public void libera() {
		this.prin = null;
	}
	
	public void insereOrdenado(int v) {
		NoLista ant = null;
		NoLista novo;
		NoLista p = this.prin;
		
		while ((p != null) && (p.getInfo() < v)) {
			ant = p;
			p = p.getProx();
		}
		
		novo = new NoLista();
		novo.setInfo(v);
		
		if (ant == null) {
			novo.setProx(this.prin);
			this.prin = novo;
		} else {
			novo.setProx(ant.getProx());
			ant.setProx(novo);
		}
	}
	
	public boolean igual(Lista l) {
		NoLista p1 = this.prin;
		NoLista p2 = l.prin;
		
		while ((p1 != null) && (p2!= null)) {
			if (p1.getInfo() != p2.getInfo()) 
				return false;
			
			p1 = p1.getProx();
			p2 = p2.getProx();
		}
		
		if (p1 == p2) {
			return true;
		} else {
			return false;
		}			
	}
	
	public void imprimeRecursivo() {
		this.imprimeRecursivoAux(this.prin);
	}
	
	private void imprimeRecursivoAux(NoLista l) {
		if (l != null) {
			System.out.print(l.getInfo() + " ");
			this.imprimeRecursivoAux(l.getProx());
		}
	}
	
	public void retiraRecursivo(int v){		
		this.prin = this.retiraRecursivoAux(this.prin, v);
	}
	
	private NoLista retiraRecursivoAux(NoLista l, int v) {
		if (l != null) {
			if (l.getInfo() == v) {
				l = l.getProx();
			} else {
				l.setProx(retiraRecursivoAux(l.getProx(), v));
			}
		} 
		return l;
	}
}

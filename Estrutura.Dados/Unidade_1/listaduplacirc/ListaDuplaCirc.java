package listaduplacirc;

public class ListaDuplaCirc {
	private NoListaDupla prin;

	public ListaDuplaCirc() {
		this.prin = null;
	}

	public void insere(int v) {
		NoListaDupla novo = new NoListaDupla();
		novo.setInfo(v);

		if (this.prin == null) {
			this.prin = novo;
			novo.setProx(novo);
			novo.setAnt(novo);
		} else {
			novo.setProx(this.prin);
			this.prin.getAnt().setProx(novo);
			novo.setAnt(this.prin.getAnt());
			this.prin.setAnt(novo);
			// this.prin.setProx(novo);
			this.prin = novo;
		}

	}

	public void insereFim(int v) {

		// se não existir nada nao lista, ele é o primeiro
		if (this.prin == null) {
			this.insere(v);
			return;
		}

		NoListaDupla novo = new NoListaDupla();
		novo.setInfo(v);

		novo.setAnt(this.prin.getAnt());
		novo.setProx(this.prin);
		this.prin.setAnt(novo);
		novo.getAnt().setProx(novo);
	}

	public void retira(int v) {
		NoListaDupla p = this.busca(v);

		if (p == null)
			return;

		if (p == this.prin) {
			this.prin.getProx().setAnt(this.prin.getAnt());
			this.prin.getAnt().setProx(this.prin.getProx());
			this.prin = this.prin.getProx();
		} else {
			p.getProx().setAnt(p.getAnt());
			p.getAnt().setProx(p.getProx());
		}

	}

	public String toString() {
		NoListaDupla p = this.prin;
		String listaStr = "";

		if (p != null) {
			do {
				listaStr += " " + p;
				p = p.getProx();
			} while (p != this.prin);
		}

		return listaStr;
	}

	public boolean vazia() {
		return this.prin == null;
	}

	public NoListaDupla busca(int v) {
		NoListaDupla p = this.prin;

		if (p == null)
			return null;

		do {
			if (p.getInfo() == v)
				return p;
			p = p.getProx();
		} while (p != this.prin);

		return null;
	}

	public NoListaDupla buscaIndice(int i) {
		int cont = 0;
		NoListaDupla p = this.prin;

		if (p == null)
			return null;

		do {
			if (cont++ == i)
				return p;
			p = p.getProx();
		} while (p != this.prin);

		return null;
	}

	public void libera() {
		this.prin = null;
	}

	public boolean igual(ListaDuplaCirc l) {
		NoListaDupla p = this.prin;
		NoListaDupla p1 = l.prin;

		do {
			if (p.getInfo() != p1.getInfo())
				return false;

			p  = p.getProx();
			p1 = p1.getProx();
		} while ((p != this.prin) && (p1 != l.prin));

		if (p.getInfo() == p1.getInfo())
			return true;
		else
			return false;
	}

	public ListaDuplaCirc merge(ListaDuplaCirc l) {
		NoListaDupla p  = this.prin;
		NoListaDupla p1 = l.prin;
		ListaDuplaCirc lista = new ListaDuplaCirc();

		if (p == null)
			return l;
		else if (p1 == null)
			return this;
		
		lista.insere(p.getInfo());			
		p = p.getProx();
		
		lista.insere(p1.getInfo());
		p1 = p1.getProx();
		
		do {
			if (p != this.prin) {
				lista.insere(p.getInfo());			
				p = p.getProx();
			}
			if (p1 != l.prin) {
				lista.insere(p1.getInfo());
				p1 = p1.getProx();
			}
		} while ((p != this.prin) || (p1 != l.prin));		
		
		return lista;
	}

	public ListaDuplaCirc separa(int l) {
		ListaDuplaCirc lista = new ListaDuplaCirc();		
		NoListaDupla p = this.busca(l);
		
		if (p == null)
			return lista;

		if (p.getProx() != this.prin) {
			lista.prin = p.getProx();			
			lista.prin.setAnt(this.prin.getAnt());
			lista.prin.getAnt().setProx(lista.prin);	
			this.prin.setAnt(p);
			p.setProx(this.prin);
		}
		
		return lista;		
	}
}

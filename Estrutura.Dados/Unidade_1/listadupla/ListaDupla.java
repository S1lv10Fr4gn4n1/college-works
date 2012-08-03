package listadupla;

public class ListaDupla {

	private NoListaDupla prin;

	public ListaDupla() {
		this.prin = null;
	}

	public void insere(int v) {
		NoListaDupla novo = new NoListaDupla();
		novo.setInfo(v);
		novo.setProx(this.prin);
		novo.setAnt(null);

		if (this.prin != null) {
			this.prin.setAnt(novo);
			this.prin = novo;
		} else {
			this.prin = novo;
		}
	}

	public void imprime() {
		NoListaDupla p = this.prin;

		while (p != null) {
			System.out.print(p.getInfo() + " ");
			p = p.getProx();
		}
	}

	public String toString() {
		return "";
	}

	public boolean vazia() {
		return this.prin == null;
	}

	public NoListaDupla busca(int v) {
		NoListaDupla p = this.prin;

		while (p != null) {
			if (p.getInfo() == v)
				return p;
			else
				p = p.getProx();
		}
		return null;
	}

	public NoListaDupla buscaIndice(int i) {
		int cont = 0;
		NoListaDupla p = this.prin;

		// verificar se a busca é apartir de zero ??
		while (p != null) {
			if (cont++ == i) {
				return p;
			} else {
				p = p.getProx();
			}
		}
		return null;
	}

	public void retira(int v) {
		NoListaDupla p = this.busca(v);

		if (p == null)
			return;

		// verifica se é o primeiro
		if (this.prin == p)
			this.prin = p.getProx();
		else
			p.getAnt().setProx(p.getProx());

		if (p.getProx() != null)
			p.getProx().setAnt(p.getAnt());
	}

	public void libera() {
		this.prin = null;
	}

	public boolean igual(ListaDupla l) {
		NoListaDupla p = this.prin;
		NoListaDupla p1 = l.prin;

		while ((p != null) && (p1 != null)) {
			if (p.getInfo() != p1.getInfo())
				return false;

			p = p.getProx();
			p1 = p1.getProx();
		}

		if (p == p1)
			return true;
		else
			return false;
	}

	public ListaDupla merge(ListaDupla l) {
		NoListaDupla p = this.prin;
		NoListaDupla p1 = l.prin;
		ListaDupla lista = new ListaDupla();
		boolean thisTaNaVez = true;

		if (p == null)
			return l;
		else if (p1 == null)
			return this;

		while ((p != null) || (p1 != null)) {
			if ((thisTaNaVez) && (p != null)) {
				lista.insere(p.getInfo());
				p = p.getProx();
			} else if (p1 != null) {
				lista.insere(p1.getInfo());
				p1 = p1.getProx();
			}
			thisTaNaVez = !thisTaNaVez;
		}

		return lista;
	}

	public ListaDupla separa(int n) {
		ListaDupla lista = new ListaDupla();
		NoListaDupla p = this.busca(n);		
		
		if ((p == null) || (p.getProx() == null))
			return lista;
		
		lista.prin = p.getProx();
		lista.prin.getAnt().setProx(null);
		
		return lista;	
	}
}

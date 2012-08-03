package arvoreBinariaBusca;

import arvoreBinaria.NoArvoreBinaria;

public class ArvoreBinariaBusca {
	private NoArvoreBinaria raiz; 
	
	public ArvoreBinariaBusca() {
		this.raiz = null;
	}
	
	public NoArvoreBinaria busca(int v) {
		return this.buscaAux(this.raiz, v);
	}
	
	private NoArvoreBinaria buscaAux(NoArvoreBinaria a, int v) {
		if (a == null) 
			return null;
		
		if (v < a.getInfo()) 
			return this.buscaAux(a.getEsq(), v);
		else if (v < a.getInfo())
			return this.buscaAux(a.getDir(), v);
		else
			return a;
	}
	
	public void insere(int v) {
 		 NoArvoreBinaria no = this.insereAux(this.raiz, v);
		if (this.raiz == null)
			this.raiz = no;
	}
	
	private NoArvoreBinaria insereAux(NoArvoreBinaria a, int v) {
		if (a == null) {
			a = new NoArvoreBinaria(v);
			a.setEsq(null);
			a.setDir(null);		
		} else	
			if (v < a.getInfo()) 
				a.setEsq(this.insereAux(a.getEsq(), v));
			else a.setDir(this.insereAux(a.getDir(), v));
		
		return a;
	}
	
	public void retira(int v) {
		this.retiraAux(this.raiz, v);
	}
	
	private  NoArvoreBinaria retiraAux(NoArvoreBinaria a, int v) {
		NoArvoreBinaria p = null;

		if (a == null)
			return null;
		else 
			if (v < a.getInfo())
				a.setEsq(retiraAux(a.getEsq(), v));
			else
				if (v > a.getInfo())
					a.setDir(this.retiraAux(a.getDir(), v));
				else
					if((a.getDir() == null) && (a.getEsq() == null))
						return null;
					else
						if (a.getEsq() == null)
							a = a.getDir();
						else
							if (a.getDir() == null)
								a = a.getEsq();
							else {
								p = a.getEsq();
								while (p.getDir() != null) 								
									p = p.getDir();
								
								a.setInfo(p.getInfo());
								p.setInfo(v);
								a.setEsq(retiraAux(a.getEsq(), v));
							}
		
		return a;
							

	}
	
	public String toString() {
		return this.imprimeCres(this.raiz);
	}
	
	private String imprimeCres(NoArvoreBinaria no) {
		String s = new String("");
		
		if (no != null) {
			s += this.imprimeCres(no.getEsq()) +" ";
			s += no.getInfo();
			s += this.imprimeCres(no.getDir()) +" ";
		}
		
		return s;
	}
	
	public String toStringDecrescente() {
		return this.imprimeDecres(this.raiz);
	}
	
	private String imprimeDecres(NoArvoreBinaria no) {
		String s = new String("");
		
		if (no != null) {
			s += this.imprimeDecres(no.getDir());
			s += no.getInfo();
			s += this.imprimeDecres(no.getEsq());
		}
		
		return s;
	}
	
	public int somaFolhas() {
		int nr = 0;
		return this.somaFolhasAux(this.raiz, nr);
	}
	
	private int somaFolhasAux(NoArvoreBinaria no, int nr) {
		if (no != null) {
			if ((no.getDir() == null) && (no.getEsq() == null))
				nr += no.getInfo();
			nr = somaFolhasAux(no.getEsq(), nr);
			nr = somaFolhasAux(no.getDir(), nr);
		}
		return nr;
	}
	
	public int maioresX(int x) {
		int qtd = 0;
		return this.maioresXAux(this.raiz, x, qtd);
	}
	
	private int maioresXAux(NoArvoreBinaria no, int x, int qtd) {
		if (no != null) {		
			if (no.getInfo() > x) 
				qtd ++;
			
			qtd = this.maioresXAux(no.getEsq(), x, qtd);				
			qtd = this.maioresXAux(no.getDir(), x, qtd);			
		}
		
		return qtd;
	}
	
	public String imprimeArvore() {
		return this.imprimePre(raiz);
	}
	
	private String imprimePre(NoArvoreBinaria no)  { 
		String s = new String("");
		s += "<";
		
		if (no != null) {
			s += no.getInfo();
			s += imprimePre(no.getEsq());
			s += imprimePre(no.getDir());
		}
		s += ">";
		
		return s;
	}
}

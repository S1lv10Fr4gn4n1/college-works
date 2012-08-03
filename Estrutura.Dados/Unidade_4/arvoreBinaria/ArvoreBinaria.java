package arvoreBinaria;

public class ArvoreBinaria {
	
	private NoArvoreBinaria raiz;
	
	public ArvoreBinaria() {
		this.raiz = null;
	}
	
	public NoArvoreBinaria insere(int v, NoArvoreBinaria esq, NoArvoreBinaria dir) {
		NoArvoreBinaria no = new NoArvoreBinaria(v, esq, dir);
		raiz = no;
		return no;
	}
	
	public boolean  vazia() {
		return raiz == null;
	}
	
	@Override
	public String toString() {	
		return imprimePre(raiz);//rever
	}

	private String imprimePre(NoArvoreBinaria no)  { //percorre raiz -> esq(esq) -> dir(dir)
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
	
//	private String imprimeSim(NoArvoreBinaria no)  { //percorre esq -> raiz -> dir(dir)
//		String s = new String("");
//		s += "<";
//		
//		if (no != null) {
//			s += imprimeSim(no.getEsq());
//			s += no.getInfo();			
//			s += imprimeSim(no.getDir());
//		}
//		s += ">";
//		
//		return s;
//	}

//	private String imprimePos(NoArvoreBinaria no)  { //percorre esq -> dir -> raiz
//		String s = new String("");
//		s += "<";
//		
//		if (no != null) {
//			s += imprimePos(no.getEsq());
//			s += imprimePos(no.getDir());
//			s += no.getInfo();						
//		}
//		s += ">";
//		
//		return s;
//	}
	
	public boolean pertence(int info) {
		return pertence(raiz, info);
	} 
	
	private boolean pertence(NoArvoreBinaria no, int info) {
		if (no == null)
			return false;
		return ((no.getInfo() == info) ||
				(pertence(no.getEsq(), info)) || 
				(pertence(no.getDir(), info)));
	}
	
	public int pares() {
		int n = 0;		
		return this.pares(raiz, n);
	}
	
	private int pares(NoArvoreBinaria no, int nr) {
		if (no != null) {
			if ((no.getInfo()%2) == 0)
				nr++;
			nr = pares(no.getEsq(), nr);
			nr = pares(no.getDir(), nr);
		}
		return nr;		
	}
	
	public int folhas() {
		int nr = 0;
		return folhas(raiz, nr);
	}
	
	private int folhas(NoArvoreBinaria no, int nr) {
		if (no != null) {
			if ((no.getDir() == null) || (no.getEsq() == null))
				nr++;
			nr = folhas(no.getEsq(), nr);
			nr = folhas(no.getDir(), nr);
		}
		return nr;
	}
	
	public boolean igual(ArvoreBinaria a) {
		return igualAux(this.raiz, a.raiz);
	}	
	
	private boolean igualAux(NoArvoreBinaria no1, NoArvoreBinaria no2){
		if (no1 == null && no2 == null)
			return true;
		
		if (no1 == null || no2 == null)
			return false;
		
		return ((no1.getInfo()==no2.getInfo())&&
				 igualAux(no1.getEsq(), no2.getEsq())&&
				 igualAux(no1.getDir(), no2.getDir()) );
		
	}
	
	public ArvoreBinaria copia() {
		ArvoreBinaria a = new ArvoreBinaria();		
		 
		copiaAux(this.raiz, a);
		
		return a;
	}
	
	private NoArvoreBinaria copiaAux(NoArvoreBinaria no, ArvoreBinaria a){			
		NoArvoreBinaria novo = null;
		
		if (no != null) {
			novo = new NoArvoreBinaria(no.getInfo());
			novo.setEsq(copiaAux(no.getEsq(), a));
			novo.setDir(copiaAux(no.getDir(), a));
			a.insere(novo.getInfo(), novo.getEsq(), novo.getDir());
		}
		return novo;		
	}
	
}




















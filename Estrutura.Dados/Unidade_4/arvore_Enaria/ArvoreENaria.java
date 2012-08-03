package arvore_Enaria;

public class ArvoreENaria {
	
	private NoArvoreENaria raiz;
	
	public ArvoreENaria() {
		 this.raiz = null;
	}
	
	public NoArvoreENaria criaNo(int info) {
		NoArvoreENaria novo = new NoArvoreENaria(info);
		this.raiz = novo;

		return novo;		
	}
	
	public void insereFilho(NoArvoreENaria noPai, NoArvoreENaria noFilho) {
		noFilho.setProx(noPai.getPrin());		
		noPai.setPrin(noFilho);	
						
		this.raiz = noPai;		
	}
		
	public String toString() {
		return this.imprime(this.raiz);
	}
	
	private String imprime(NoArvoreENaria no) {
		String s = new String("");
		
		s += "<";
		s += no.getInfo();
		
		NoArvoreENaria p = no.getPrin();
		
		while (p != null) {
			s += imprime(p);
			p = p.getProx();			
		}
		
		s += ">";
		
		return s;
	}
	
	public boolean pertence(int info) {		
		return this.pertenceAux(this.raiz, info);
	}
	
	private boolean pertenceAux(NoArvoreENaria no, int info) {		
		while (no != null) 
			return ((no.getInfo() == info) || (pertenceAux(no.getPrin(), info)) || (pertenceAux(no.getProx(), info)));		
				
		return false;	
	}
	
	public int altura() {
		return this.alturaAux(this.raiz);		
	}
	
	private int alturaAux(NoArvoreENaria no) {
		int max = -1;
		
		NoArvoreENaria p = no.getPrin();
		 while (p != null) {
			 int h = alturaAux(p);
			 
			 if (h > max)
				 max = h;
			 
			 p = p.getProx();
		 }		
		return max + 1;		
	}
	
	public int pares() {
		int n = 0;
		return this.paresAux(this.raiz, n);
	}
	
	private int paresAux(NoArvoreENaria no, int n) {
		NoArvoreENaria p = no.getPrin();
		
		while (p != null) {
			n = paresAux(p, n);
			if (p.getInfo()%2 == 0)
				n++;
			
			p = p.getProx();			
		}		
		return n;
	}
	
	public int folhas() {
		int n = 0;
		
		return this.folhasAux(this.raiz, n);
	}
	
	private int folhasAux(NoArvoreENaria no, int n) {
		NoArvoreENaria p = no.getPrin();
		
		while (p != null) {
			n = folhasAux(p, n);
			if (p.getPrin() == null)
				n++;
			
			p = p.getProx();			
		}		
		return n;
	}
	
	public boolean igual(ArvoreENaria a){		
		return igualAux(this.raiz, a.raiz);
	}
	
	private boolean igualAux(NoArvoreENaria n1, NoArvoreENaria n2) {		
		if ((n1 == null) && (n2 == null))
			return true;
		
		if ((n1 == null) || (n2 == null))
			return false;
		return (n1.getInfo() == n2.getInfo() && (igualAux(n1.getPrin(), n2.getPrin())) && (igualAux(n1.getProx(), n2.getProx())));
	}
	
	public ArvoreENaria copia() {
		ArvoreENaria a = new ArvoreENaria();
		
		copiaAux(this.raiz, a);
		
		return a;
	}
	
	private NoArvoreENaria copiaAux(NoArvoreENaria no, ArvoreENaria a) {	
		
		NoArvoreENaria novo = new NoArvoreENaria(no.getInfo());		
		NoArvoreENaria p = no.getPrin();		
		while (p != null) {
			a.insereFilhoFim(novo, copiaAux(p, a));
			p = p.getProx();
		}
		
		return novo;
		
	}
	
	public void insereFilhoFim(NoArvoreENaria noPai, NoArvoreENaria noFilho) {
		noFilho.setProx(null);	
		
		NoArvoreENaria p = noPai.getPrin();
		
		if (p == null)
			return;
		
		do {
			p = p.getProx();
		} while (p.getProx()!= null);
		
		if (noPai.getPrin() == null)
			noPai.setPrin(noFilho);
		
		p.setProx(noFilho);	
						
		this.raiz = noPai;		
	}
	
  /*NoArvoreENaria novo = new NoArvoreENaria(no.getInfo());		
	NoArvoreENaria p = no.getPrin();		
	while (p != null) {
		a.insereFilho(novo, copiaAux(p, a));
		p = p.getProx();
	}
	
	return novo;
	*/
}












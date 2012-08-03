package arvoreBinariaBusca;

public class ArvoreBinariaBuscaMain {
	public static void main(String[] args) {
		ArvoreBinariaBusca a = new ArvoreBinariaBusca();
		a.insere(6);
		a.insere(2);
		a.insere(1);
		a.insere(4);
		a.insere(3);
		a.insere(8);
		
		System.out.println(a);
		System.out.println(a.imprimeArvore());
		
		System.out.println(a.maioresX(6));
	}
}

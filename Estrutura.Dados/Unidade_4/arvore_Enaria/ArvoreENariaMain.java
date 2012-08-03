package arvore_Enaria;

public class ArvoreENariaMain {
	
	public static void main(String[] args) {
		ArvoreENaria a = new ArvoreENaria();
		NoArvoreENaria n1 = a.criaNo(1);
		NoArvoreENaria n2 = a.criaNo(2);
		NoArvoreENaria n3 = a.criaNo(3);
		NoArvoreENaria n4 = a.criaNo(4);
		NoArvoreENaria n5 = a.criaNo(5);
		NoArvoreENaria n6 = a.criaNo(6);
		NoArvoreENaria n7 = a.criaNo(7);
		NoArvoreENaria n8 = a.criaNo(8);
		NoArvoreENaria n9 = a.criaNo(9);
		NoArvoreENaria n10 = a.criaNo(10);
		
		a.insereFilho(n3, n4);		
		a.insereFilho(n2, n5);
		a.insereFilho(n2, n3);
		a.insereFilho(n9, n10);
		a.insereFilho(n7, n9);
		a.insereFilho(n7, n8);
		a.insereFilho(n1, n7);
		a.insereFilho(n1, n6);
		a.insereFilho(n1, n2);
		
		ArvoreENaria b = new ArvoreENaria();
		NoArvoreENaria m1 = b.criaNo(1);
		NoArvoreENaria m2 = b.criaNo(2);
		NoArvoreENaria m3 = b.criaNo(3);
		NoArvoreENaria m4 = b.criaNo(4);
		NoArvoreENaria m5 = b.criaNo(5);
		NoArvoreENaria m6 = b.criaNo(6);
		NoArvoreENaria m7 = b.criaNo(7);
		NoArvoreENaria m8 = b.criaNo(8);
		NoArvoreENaria m9 = b.criaNo(9);
		NoArvoreENaria m10 = b.criaNo(10);
		
		b.insereFilho(m3, m4);		
		b.insereFilho(m2, m5);
		b.insereFilho(m2, m3);
		b.insereFilho(m9, m10);
		b.insereFilho(m7, m9);
		b.insereFilho(m7, m8);
		b.insereFilho(m1, m7);
		b.insereFilho(m1, m6);
		b.insereFilho(m1, m2);
		
		
		
		System.out.println("Arvore E-niaria: "+a);
		
		System.out.println("Pertence: "+a.pertence(-1));
		
		System.out.println("Altura: "+a.altura());
		
		System.out.println("folhas: "+a.folhas());
		
		System.out.println("Pares: "+a.pares());	
		
		System.out.println("iguais: "+a.igual(b));
		
		ArvoreENaria c = a.copia();
		System.out.println("arv copiada: "+c);
		
	}
}

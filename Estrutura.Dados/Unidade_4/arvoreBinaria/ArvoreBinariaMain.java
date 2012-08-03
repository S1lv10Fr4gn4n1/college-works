package arvoreBinaria;


public class ArvoreBinariaMain {
	
	public static void main(String[] args) {
		ArvoreBinaria a = new ArvoreBinaria();
		NoArvoreBinaria esqA = new NoArvoreBinaria(2);
		NoArvoreBinaria dirA = new NoArvoreBinaria(3);
		
		a.insere(1, esqA, dirA);
				
//		ArvoreBinaria b = new ArvoreBinaria();
//		b.insere(1, b.insere(2, null, b.insere(4, null, null)) , b.insere(3, b.insere(5, null, null), b.insere(6, null, null)));
				
		System.out.println(a);
		
		ArvoreBinaria c = a.copia();
		
		System.out.println(c);
		
	}
}

package listadupla;

public class ListaDuplaMain {
	public static void main(String[] args) {
		ListaDupla lista = new ListaDupla();
		ListaDupla lista1 = new ListaDupla();
		ListaDupla teste = new ListaDupla();
		
		lista.insere(1);
		lista.insere(2);
		lista.insere(3);
		lista.insere(4);
		lista.insere(5);
		lista.insere(6);
		
		//teste.insere(10);
		//teste.insere(11);
		//teste.insere(12);
		//teste.insere(13);
		//teste.insere(14);
		
		
		//lista1.insere(9);
		//lista1.insere(10);
		
		System.out.print("Lista 1: ");
		lista.imprime();
		
		System.out.print("\nLista 2: ");
		teste.imprime();
		//System.out.print("\nLista 2: ");
		//lista1.imprime()
		
		
		lista1 = lista.merge(teste);
		System.out.println("\nimprime merge: ");
		lista1.imprime();
		
		//System.out.print("\nMerge: ");
		//ListaDupla teste = lista1.merge(lista);
		
		//teste.imprime();		
		//lista.libera();		
		//System.out.println(lista.vazia());
		
	}
}

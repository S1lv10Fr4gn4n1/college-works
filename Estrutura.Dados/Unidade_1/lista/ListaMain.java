package lista;

public class ListaMain {
	public NoLista item;
	public Lista lista;
	
	public static void main(String[] args) {
		Lista lista = new Lista();
		
		lista.insere(1);
		lista.insere(3);
		lista.insere(5);
		lista.insere(7);
		
		lista.imprime();
		
		System.out.print("\nBusca Valor: " + lista.busca(7));
		System.out.print("\nLista Vazia: " + lista.vazia());
		System.out.print("\nComprimento: " + lista.comprimento());
		System.out.print("\nUltimo: " + lista.ultimo());
		
		lista.retira(7);
		System.out.print("\nRetira 7\n");
		
		lista.imprime();
		
		lista.libera();
		System.out.print("\nLista Vazia: " + lista.vazia());
		
		System.out.print("\nInsere Ordenado\n");
		lista.insereOrdenado(3);
		lista.insereOrdenado(7);				
		lista.insereOrdenado(1);
		lista.insereOrdenado(5);
		
		lista.imprime();
		
		Lista lista2 = new Lista();
		
		lista2.insereOrdenado(3);
		lista2.insereOrdenado(7);				
		lista2.insereOrdenado(1);
		lista2.insereOrdenado(5);
		
		System.out.print("\nLista s�o iguais: " +lista.igual(lista2));
		
		System.out.print("\nImprime normal:  ");
		lista.imprime();
		System.out.print("\nImprime recursivo:  ");
		lista.imprimeRecursivo();			
	}	
}

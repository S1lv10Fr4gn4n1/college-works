package listaduplacirc;

public class ListaDuplaCircMain {

	public static void main(String[] args) {
		ListaDuplaCirc lista1 = new ListaDuplaCirc();
		
		lista1.insere(1);
		lista1.insere(2);
		lista1.insere(3);
		lista1.insere(4);
		lista1.insere(5);
		
		System.out.print("Lista1: " + lista1);
		
		System.out.println("\nRetira : " );
		lista1.retira(5);
		
		
		System.out.print("\nLista1: " + lista1);

		
	}
}

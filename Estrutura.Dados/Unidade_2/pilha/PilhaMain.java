package pilha;

public class PilhaMain {
	public static void main(String[] args) {	
		PilhaLista pilha = new PilhaLista();		
		
		try {
			pilha.push(1);
			pilha.push(2);
			pilha.push(3);
			pilha.push(4);
		
			System.out.println(pilha);
			
			
			//System.out.println("Top: " + pilha.top());
			
			//pilha.libera();
						
			System.out.println("pilha: "+pilha);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());		
		}
		
		
	}
}

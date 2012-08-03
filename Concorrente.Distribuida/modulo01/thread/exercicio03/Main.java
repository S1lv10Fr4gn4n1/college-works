package thread.exercicio03;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		Fila fila = new Fila();
		Consumidor cons = new Consumidor(fila);
		Produtor prod = new Produtor(fila);
		
		cons.start();
		prod.start();
		
		cons.join();
		prod.join();
		
		System.out.println("Total: " + fila.Total());
	}
}

package thread.exercicio03;

public class Consumidor extends Thread{
	private Fila fila;
	
	public Consumidor(Fila anfila) {
		this.fila = anfila;
	}
	
	public void run() {
		try {
			for (int i = 1; i <= 1000 && !isInterrupted(); i++) {
				fila.get();
				sleep(150);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package thread.exercicio03;

import java.util.Date;

public class Produtor extends Thread{
	private Fila fila;
	
	public Produtor(Fila anFila) {
		this.fila = anFila;
	}
	
	public void run() {
		try {
			for (int i = 1; i <= 1000 && !isInterrupted(); i++) {
				this.fila.put(new Date().toString());
				sleep(50);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

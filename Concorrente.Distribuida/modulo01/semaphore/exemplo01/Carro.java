package semaphore.exemplo01;

import java.util.concurrent.Semaphore;

public class Carro extends Thread {
	private static Semaphore estacionamento = new Semaphore(10, true);

	public void run() {
		try {
			estacionamento.acquire();
			
			System.out.println(getName() + " ocupou vaga.");
				sleep((long) (Math.random() * 10000));
			System.out.println(getName() + " liberou vaga.");
			
			estacionamento.release();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}

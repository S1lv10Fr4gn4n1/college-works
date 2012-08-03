package semaphore.exemplo02;

import java.util.concurrent.Semaphore;

public class Mesa {
	private static Semaphore talher = new Semaphore(5, true);
	
	public void comer(String filosofo) throws InterruptedException {
		System.out.println("faminto: "+ filosofo);
		talher.acquire(2);
		System.out.println("terminou de comer: "+ filosofo);
	}
	
	public void pensar(String filosofo) {
		System.out.println("comecando a pensando: "+ filosofo);
		talher.release(2);
		System.out.println("parando pensar: "+ filosofo);
	}
}

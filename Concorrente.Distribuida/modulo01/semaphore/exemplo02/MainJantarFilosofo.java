package semaphore.exemplo02;

public class MainJantarFilosofo {
	
	public static void main(String[] args) throws InterruptedException {
		Mesa mesa = new Mesa();
		
		Filosofo[] filosofos = new Filosofo[5];
		
		for (int i = 0; i < filosofos.length; i++) 
			filosofos[i] = new Filosofo(mesa);
		
		for (int i = 0; i < filosofos.length; i++) 
			filosofos[i].start();

		for (int i = 0; i < filosofos.length; i++) 
			filosofos[i].join();
	}
}

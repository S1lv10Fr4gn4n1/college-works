package semaphore.exemplo01;

public class MainEstacionamento {
	
	public static void main(String[] args) throws InterruptedException {
		Carro[] car = new Carro[51];
		
		for (int i = 1; i <= 50; i++) 
			car[i] = new Carro();
		
		for (int i = 1; i <= 50; i++) 
			car[i].start();
		
		for (int i = 1; i <= 50; i++) 
			car[i].join();		
	}
}

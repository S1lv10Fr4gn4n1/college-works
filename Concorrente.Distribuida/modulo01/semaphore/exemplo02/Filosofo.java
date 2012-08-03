package semaphore.exemplo02;

public class Filosofo extends Thread{
	
	private Mesa mesa;

	public Filosofo(Mesa anMesa) {
		this.mesa = anMesa;
	}
	
	public void run() {
		for (int i = 0; i < 20 && !interrupted(); i++) {
			
			try {
				this.mesa.comer(this.getName());
				sleep((long) (Math.random() * 10000) + 200);
				this.mesa.pensar(this.getName());
				sleep((long) (Math.random() * 10000) + 200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

package lock.exemplo2;

public class MyMailBox {
	
	public static void main(String[] args) throws InterruptedException {
		BufferCycle buffer = new BufferCycle();
		
		Producer prod1 = new Producer(buffer);
		Producer prod2 = new Producer(buffer);
		Producer prod3 = new Producer(buffer);

		Consumer cons1 = new Consumer(buffer);
		Consumer cons2 = new Consumer(buffer);
		Consumer cons3 = new Consumer(buffer);
		

		prod1.start();
		prod2.start();
		prod3.start();

		cons1.start();
		cons2.start();
		cons3.start();
		
		cons1.join();
		cons2.join();
		cons3.join();

		prod1.join();
		prod2.join();
		prod3.join();
	}
}

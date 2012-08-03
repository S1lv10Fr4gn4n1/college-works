package lock.exemplo1;

public class MyMailBox {
	
	public static void main(String[] args) throws InterruptedException {
		MailBox mailBox = new MailBox();
		
		Producer prod1 = new Producer(mailBox);
		//Producer prod2 = new Producer(mailBox);
		//Producer prod3 = new Producer(mailBox);

		Consumer cons1 = new Consumer(mailBox);
		//Consumer cons2 = new Consumer(mailBox);
		//Consumer cons3 = new Consumer(mailBox);
		

		prod1.start();
		//prod2.start();
		//prod3.start();

		cons1.start();
		//cons2.start();
		//cons3.start();
		
		//cons1.join();
		//cons2.join();
		//cons3.join();

		//prod1.join();
		//prod2.join();
		//prod3.join();
	}
}

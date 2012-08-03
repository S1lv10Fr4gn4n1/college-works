package lock.exemplo1;

public class Consumer extends Thread{
	
	private MailBox mailBox = null;
	
	public Consumer(MailBox anMailBox) {
		this.mailBox = anMailBox;
	}
	
	public void run() {
		try {	
			for(int i = 0; i < 100 && !isInterrupted(); i++) {
				String message = this.mailBox.retrieveMessage();
				System.out.println("consumer" + i + " : " + message);
				sleep(150);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

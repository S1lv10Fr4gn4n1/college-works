package lock.exemplo4;

public class Consumer extends Thread{
	
	private MailBox mailBox = null;
	
	public Consumer(MailBox anMailBox) {
		this.mailBox = anMailBox;
	}
	
	public void run() {
		try {	
			for(int i = 0; i < 100 && !isInterrupted(); i++) {
				this.mailBox.retrieveMessage();				
				sleep(150);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package lock.exemplo1;

public class Producer extends Thread{
	private MailBox mailBox = null;
	
	public Producer(MailBox anMailBox) {
		this.mailBox = anMailBox;
	}
	
	public void run() {
		try {
			for(int i = 0; i < 100 && !isInterrupted(); i++) {
				final String message = "message" + i;
				this.mailBox.storeMessage(message);
				System.out.println("producer" + i + " : " + message);
				sleep(150);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

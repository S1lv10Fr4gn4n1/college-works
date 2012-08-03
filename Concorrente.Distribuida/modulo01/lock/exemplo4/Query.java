package lock.exemplo4;

public class Query extends Thread{
	
	private MailBox mailBox = null;
	
	public Query(MailBox anMailBox) {
		this.mailBox = anMailBox;
	}
	
	public void run() {
		try {	
			for(int i = 0; i < 100 && !isInterrupted(); i++) {
				this.mailBox.queryMessage();
				sleep(150);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

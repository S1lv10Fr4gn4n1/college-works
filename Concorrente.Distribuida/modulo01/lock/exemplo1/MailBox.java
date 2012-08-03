package lock.exemplo1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MailBox {
	
	private String message = null;
	private Lock lock = new ReentrantLock();
	private Condition hasMessage = lock.newCondition();
	private Condition hasNotMessage = lock.newCondition();
	
	public void storeMessage(String anMessage) throws InterruptedException {
		this.lock.lock();
		
		try {
			while (this.message != null)
				this.hasMessage.await();
		
			this.message = anMessage;
			this.hasNotMessage.signal();
		} finally {
			this.lock.unlock();
		}
	}
	
	public String retrieveMessage() throws InterruptedException {
		this.lock.lock();
		
		try {
			while (this.message == null)
				this.hasNotMessage.await();
			
			String retrieveMessage = this.message;
			this.message = null;
			
			this.hasMessage.signal();
			
			return retrieveMessage;
		} finally {
			this.lock.unlock();
		}
		
	}
}

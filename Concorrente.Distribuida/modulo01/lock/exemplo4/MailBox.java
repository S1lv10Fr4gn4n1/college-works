package lock.exemplo4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MailBox {
	
	private String message = null;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Condition hasMessage = lock.writeLock().newCondition();
	private Condition hasNotMessage = lock.writeLock().newCondition();
	
	public void storeMessage(String anMessage) throws InterruptedException {
		this.lock.writeLock().lock();
		
		try {
			while (this.message != null)
				this.hasMessage.await();
		
			this.message = anMessage;
			System.out.println("producer" + ": " + this.message);

			this.hasNotMessage.signal();
		} finally {
			this.lock.writeLock().unlock();
		}
	}
	
	public String retrieveMessage() throws InterruptedException {
		this.lock.writeLock().lock();
		
		try {
			while (this.message == null)
				this.hasNotMessage.await();
			
			String retrieveMessage = this.message;
			System.out.println("consumer" + ": " + retrieveMessage);
			this.message = null;
			
			this.hasMessage.signal();
			
			return retrieveMessage;
		} finally {
			this.lock.writeLock().unlock();
		}		
	}
	
	public String queryMessage() throws InterruptedException {
		this.lock.readLock().lock();
		
		try {
			System.out.println("query" + ": " + this.message);
			return this.message;
		} finally {
			this.lock.readLock().unlock();
		}
	}
}

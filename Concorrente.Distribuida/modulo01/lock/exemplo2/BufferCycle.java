package lock.exemplo2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferCycle {
	private List<String> buffer = new ArrayList<String>(3);
	private Lock lock = new ReentrantLock();
	private Condition canRead = lock.newCondition();
	private Condition canWrite = lock.newCondition();
	
	public String read() throws InterruptedException {	
		this.lock.lock();
		
		try {
			while (this.buffer.size() == 0)
				this.canRead.await();
				
			String message = this.buffer.remove(0);
			this.canWrite.signal();
			
			return message;
		} finally {
			this.lock.unlock();
		}
	}
	
	public void write(String anValue) throws InterruptedException {
		this.lock.lock();
		
		try {
			while (this.buffer.size() == 3)
				this.canWrite.await();
			
			this.buffer.add(anValue);
			this.canRead.signal();
		} finally {
			this.lock.unlock();
		}
	}
	
}

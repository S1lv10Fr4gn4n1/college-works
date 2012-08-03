package lock.exemplo2;

import java.util.Random;

public class Consumer extends Thread{
	
	private BufferCycle buffer = null;
	
	public Consumer(BufferCycle anBuffer) {
		this.buffer = anBuffer;
	}
	
	public void run() {
		try {	
			for(int i = 0; i < 100 && !isInterrupted(); i++) {
				String message = this.buffer.read();
				System.out.println("read" + i + " : " + message);
				
				sleep(new Random().nextInt(350));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

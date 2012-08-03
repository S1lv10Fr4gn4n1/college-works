package lock.exemplo2;

import java.util.Random;

public class Producer extends Thread{
	private BufferCycle buffer = null;
	
	public Producer(BufferCycle anBuffer) {
		this.buffer = anBuffer;
	}
	
	public void run() {
		try {
			for(int i = 0; i < 100 && !isInterrupted(); i++) {
				final String message = "message" + i;
				this.buffer.write(message);
				System.out.println("write" + i + " : " + message);

				sleep(new Random().nextInt(100));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

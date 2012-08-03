package thread.exercicio01;

import java.util.Date;

public class ExerThead01 extends Thread {

	public void run() {
		try {
			for (int i = 1; i <= 1000; i++) {
				Date now = new Date();
				System.out.println(now + " abacate");
			
				sleep(1000);
			}
		} catch (InterruptedException e) {				
			e.printStackTrace();
		}
	}
}

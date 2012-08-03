package thread.exercicio02;

public class DepositThread extends Thread {
	private double amount;
	private BankAccount account;
	
	public DepositThread(BankAccount anAccount, double anAmount) {
		this.account = anAccount;
		this.amount = anAmount;
	}

	public void run() {

		try {
			for (int i = 1; i <= 50 && !isInterrupted(); i++) {
				account.deposit(amount);
				sleep(100);
			}
		} catch (InterruptedException exception) {
				
		}
	}
}

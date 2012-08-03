package thread.exercicio02;

public class WithdrawThread extends Thread {
	private double amount;
	private BankAccount account;

	public WithdrawThread(BankAccount anAccount, double anAmount) {
		this.amount = anAmount;
		this.account = anAccount;
	}

	public void run() {
		try {
			for (int i = 1; i <= 50 && !isInterrupted(); i++) {
				account.withdraw(amount);
				sleep(50);
			}
		} catch (InterruptedException exception) {
		}
	}
}

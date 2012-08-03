package thread.exercicio02;

public class BankAccount {
	private double balance;	
	
	public synchronized void deposit(double amount) {
		this.balance += amount;
		this.notifyAll();
		
		System.out.println("Depositou: " +amount + ", Balanço: " + this.balance);
	}
	
	public synchronized void withdraw(double amount) throws InterruptedException {
		while (this.balance < amount) {
			System.out.println("___CAIU NO WAIT");
			this.wait();
		}
		
		this.balance = this.balance - amount;
		System.out.println("Sacou: " +amount + ", Balanço: " + this.balance);
	}
	
	public double getBalance() {
		return this.balance;
	}

}

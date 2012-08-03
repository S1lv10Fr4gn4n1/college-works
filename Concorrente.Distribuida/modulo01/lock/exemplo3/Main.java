package lock.exemplo3;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		BankAccount banco = new BankAccount();
		DepositThread deposito = new DepositThread(banco, 100);
		WithdrawThread saque = new WithdrawThread(banco, 100);
		
		deposito.start();
		saque.start();
		
		deposito.join();
		saque.join();
		
		System.out.println("Saldo final: " + banco.getBalance());
		
	}
}

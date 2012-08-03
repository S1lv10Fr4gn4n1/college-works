package thread.exercicio02;

public class Main {
	
	public static void main(String[] args) {
		BankAccount banco = new BankAccount();
		DepositThread deposito = new DepositThread(banco, 100);
		WithdrawThread saque = new WithdrawThread(banco, 100);
		
		deposito.start();
		saque.start();
		
		System.out.println("Saldo final: " + banco.getBalance());
		
	}
}

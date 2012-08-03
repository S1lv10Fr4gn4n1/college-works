package lock.exemplo3;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {
	private double balance;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void deposit(double amount) {
		this.lock.writeLock().lock();
		
		try {
			this.balance += amount;
			System.out.println("Depositou: " +amount + ", Balanço: " + this.getBalance());
		} finally {
			this.lock.writeLock().unlock();
		}				
	}
	
	public void withdraw(double amount) throws InterruptedException {
		this.lock.writeLock().lock();
		
		try {
			this.balance = this.balance - amount;
			System.out.println("Sacou: " +amount + ", Balanço: " + this.getBalance());
		} finally {
			this.lock.writeLock().unlock();
		}
	}
	
	public double getBalance() {
		this.lock.readLock().lock();
		
		try {
			return this.balance;
		} finally {
			this.lock.readLock().unlock();
		}
	}

}

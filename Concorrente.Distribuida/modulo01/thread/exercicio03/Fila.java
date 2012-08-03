package thread.exercicio03;

import java.util.*;

public class Fila {
	private List<String> lista = new ArrayList<String>(); 
	
	public synchronized void put(String v) throws Exception {
		while (this.lista.size() == 20) {
			System.out.println("__Wait Put");
			this.wait();	
		}

		this.lista.add(v);
		
		if (this.lista.size() == 1)
			this.notifyAll();
		
		System.out.println("put: "+ v+ ", Total: "+ this.lista.size());
	}

	public synchronized String get() throws Exception {
		String v = "";
				
		while (this.lista.isEmpty()) {
			System.out.println("__Wait Get");
			this.wait();
		}
		
		if (this.lista.size() < 10)
			this.notifyAll();
		
		v = this.lista.remove(0);
		
		System.out.println("get: " + v + ", Total: " + this.lista.size());

		return v;
	}
	
	public int Total() {
		return this.lista.size();
	}
}

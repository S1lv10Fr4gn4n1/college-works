package hash;

public class hashMain {
	public static void main(String[] args) {
		TabelaHash h = new TabelaHash(10);
		
		h.set("silvio", 12, 4);
		h.set("paulo", 22, 10);
		h.set("maria", 1, 12);
		
		System.out.println(h);
		
		h.remove(1);
		
		System.out.println(h);
	}
}

package ordenacao;

public class executa {
	public static void main(String[] args) {
		Ordenacao orc = new Ordenacao();
		int[] vet = orc.criaVetorEmbaralhado(10);		
		
		imprime(vet);
		
		long t1 = System.currentTimeMillis();		
		
		orc.quickSort(vet, 0, 9);
		
		long t2 = System.currentTimeMillis();
				
		imprime(vet);
			
		System.out.println("Tempo: "+ (t2-t1));
	}
	
	public static void imprime(int[] v){
		StringBuffer s = new StringBuffer();
		for (int i=0; i<v.length; i++)
			s.append(v[i]).append(" ");		
		
		System.out.println(s.toString());
	}
}

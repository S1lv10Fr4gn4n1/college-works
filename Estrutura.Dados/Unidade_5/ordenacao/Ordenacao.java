package ordenacao;

import java.util.Random;

public class Ordenacao {
	
	public int[] bubbleSort(int[] v) {
		int i, j ;
		int n = v.length;		
	
		for (i=n-1; i>1; i--) {			
			for (j=0; j<i;j++) {
				if (v[j] > v[j + 1]) {
					int temp = v[j];
					v[j] = v[j + 1];
					v[j + 1] = temp;
				}
			}
		}		
		return v;
	}
	
	public void quickSort(int v[], int a, int b) {
		if (a>=b)
			return;
		
		int indicePivo = particiona(v, a, b);
		quickSort(v, a, indicePivo);
		quickSort(v, indicePivo + 1, b);		
	}	
	
	private int particiona(int[] v, int a, int b) {
		int x = v[a];
		
		while (a<b) {
			while (v[a]<x)
				a++;
			while (v[b]>x)
				b--;
			troca(v,a,b);			
		}
		return a;
	}
	
	private void troca(int v[], int a, int b) {
		int temp = v[a];
		v[a] = v[b];
		v[b] = temp;
	}
	
	
	// metodo para criar um vetor embaralhado
    public int[] criaVetorEmbaralhado(int n) { 
    	int[] xVet = new int[n]; 
    	
    	for (int i = 0; i < n ;i++){ 
    		xVet[i] = i+1; } 
    
    	final Random ramdom = new Random(); 
    	
    	for (int i = 0; i< n; i++){ 
    		int temp = xVet[i]; 
    		int pos = ramdom.nextInt(n); 
    		xVet[i] = xVet[pos]; 
    		xVet[pos] = temp; 
    	} return xVet; 
    }	           	

}

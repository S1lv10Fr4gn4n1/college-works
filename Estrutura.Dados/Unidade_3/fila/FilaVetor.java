package fila;

public class FilaVetor implements Fila {
	private int n; // numero de elementos NA fila
	private int ini; // posição do proximo elemento ser retirado da fila (inicio
	// da fila)
	private int tam; // tamanho total do vetor (cadeiras)
	private int[] vet;

	// fim = (ini + n) % tam ; posição onde será inserido o proximo elemento

	public FilaVetor(int tam) {
		this.vet = new int[tam];
		this.tam = tam;
		this.ini = 0;
		this.n = 0;
	}

	public void insere(int v) throws Exception {
		int fim;
		if (n == tam)
			throw new Exception("ERRO!, capacidade da fila estourou!");
		else {
			// insere elemento na proxima posição livre
			fim = (ini + n) % tam;
			this.vet[fim] = v;
			// incrementa o numero total de elemenos
			this.n++;
		}
	}

	public void libera() {
		ini = 0;
		n = 0;
	}

	public int retira() throws Exception {
		int v;

		if (n == 0)
			throw new Exception("ERRO!, fila esta vazia!");
		else {
			v = this.vet[ini];
			ini = this.vet[ini] % tam;
			n--;
		}
		return v;
	}

	public boolean vazia() {
		return this.n == 0;
	}

	public String toString() {
		String str = "";
		int fim = (ini + n) % tam;
		int i = ini;

		if (n == 0)
			return str;

		do {
			str += String.valueOf(vet[i]) + " ";
			i++;

			if (this.tam == i)
				i = 0;
		} while (i != fim);

		return str;
	}

	public FilaVetor contatena(FilaVetor f2) throws Exception {
		int tamanho = f2.tam + this.tam;
		FilaVetor novaFila = new FilaVetor(tamanho);

		int fim = (this.ini + this.n) % this.tam;
		int i = this.ini;

		do {
			novaFila.insere(this.vet[i]);
			i++;

			if (this.tam == i)
				i = 0;
		} while (i != fim);

		fim = (f2.ini + f2.n) % f2.tam;
		i = f2.ini;

		do {
			novaFila.insere(f2.vet[i]);
			i++;

			if (f2.tam == i)
				i = 0;
		} while (i != fim);

		return novaFila;
	}

	public FilaVetor merge(FilaVetor f2) throws Exception {
			int tamanho = f2.tam + this.tam;
			FilaVetor f3 = new FilaVetor(tamanho);

			int i1 = this.ini;
			int i2 = f2.ini;

			for (; i1 < (this.n+ini) && i2 < (f2.n + ini); i1++, i2++) {
				f3.insere(this.vet[i1]);
				f3.insere(f2.vet[i2]);
			}

			for (; i1 < (this.n+ini); i1++) {
				f3.insere(this.vet[i1]);
			}
			
			for (; i2 < (f2.n+ini); i2++) {
				f3.insere(f2.vet[i2]);
			}

			return f3;
	}

}

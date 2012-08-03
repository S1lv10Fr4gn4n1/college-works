package hash;

import lista.NoLista;

public class TabelaHash {
	private Aluno[] tabela;

	public TabelaHash(int N) {
		this.tabela = new Aluno[N]; // preferivel numero primo
	}

	private int hash(int k) {
		return (k % this.tabela.length);
	}

	public Aluno get(int k) {
		Aluno a = this.tabela[this.hash(k)];

		while (a != null) {
			if (a.getMatricula() == k)
				return a;

			a = a.getProx();
		}

		return null;
	}

	public void set(String nome, int matricula, float mediaGeral) {
		int h = this.hash(matricula);
		Aluno a = this.tabela[h];
		
		if (a != null) {
			while (a != null) {
				if (a.getMatricula() == matricula) {
					a.setNome(nome);
					a.setMediaGeral(mediaGeral);
					return;
				}
				a = a.getProx();
			}
		}
		
		a = new Aluno();
		a.setNome(nome);
		a.setMediaGeral(mediaGeral);
		a.setMatricula(matricula);
		a.setProx(this.tabela[h]);
		this.tabela[h] = a;
	}

	public void remove(int k) {		// rever nao esta funcionando
		Aluno atual = this.tabela[this.hash(k)];
		Aluno ant = null;
		
		//procuta elemento e guarda anterior
		while ((atual.getProx() != null) && (atual.getMatricula() != k)) {
			ant = atual;
			atual = atual.getProx();
		}
		
		// nao acho elemento
		if (atual == null) {
			return;
		}
		
		if (ant != null) {			
			ant.setProx(atual.getProx());
		} else {
			
		}

	}
	
	public int size() {
        int cont = 0;
        
        for (int i=0;i<this.tabela.length;i++) {
            Aluno a = this.tabela[i];
            while (a != null) {
                cont++;
                a = a.getProx();
            }
        }
        return cont;
    }

	public String toString() {
		String saida = "";
		
        for (int i = 0;i < this.tabela.length;i++) {
            Aluno a = this.tabela[i];
            while (a != null) {
                saida += "\n Aluno: "+a.getNome()+"\n";
                saida += "Matricula: "+a.getMatricula()+"\n";
                saida += "Media geral: "+a.getMediaGeral()+"\n";
                a = a.getProx();
            }
        }
        
        return saida;
	}

}

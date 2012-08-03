package fila;

public interface Fila {
	
	void insere(int v) throws Exception;
	
	int retira() throws Exception;
	
	boolean vazia();
	
	void libera();
}

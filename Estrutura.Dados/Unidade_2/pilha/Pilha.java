package pilha;

public interface Pilha {
	public void push(float v)throws Exception; 
	public float pop() throws Exception;
	public float top() throws Exception;
	public boolean vazia();
	public void libera();
}

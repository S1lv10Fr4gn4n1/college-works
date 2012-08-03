package arvore_Enaria;

public class NoArvoreENaria {
	private int info;
	private NoArvoreENaria prin;
	private NoArvoreENaria prox;
	
	public NoArvoreENaria(int info) {
		this.setInfo(info);
		this.setPrin(null);
		this.setProx(null);		
	}
		
	public int getInfo() {
		return info;
	}
	public void setInfo(int info) {
		this.info = info;
	}
	public NoArvoreENaria getPrin() {
		return prin;
	}
	public void setPrin(NoArvoreENaria prin) {
		this.prin = prin;
	}
	public NoArvoreENaria getProx() {
		return prox;
	}
	public void setProx(NoArvoreENaria prox) {
		this.prox = prox;
	}
}

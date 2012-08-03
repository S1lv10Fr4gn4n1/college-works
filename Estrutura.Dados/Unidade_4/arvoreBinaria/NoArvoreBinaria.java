package arvoreBinaria;

public class NoArvoreBinaria {
	private int info;
	private NoArvoreBinaria esq;
	private NoArvoreBinaria dir;
	
	public NoArvoreBinaria(int info) {
		this.setInfo(info);
		this.setEsq(null);
		this.setDir(null);
	}
	
	public NoArvoreBinaria(int info, NoArvoreBinaria esq, NoArvoreBinaria dir) {
		this.setInfo(info);
		this.setEsq(esq);
		this.setDir(dir);
	}
	
	@Override
	public String toString() {	
		return null;
	}

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
	}

	public NoArvoreBinaria getEsq() {
		return esq;
	}

	public void setEsq(NoArvoreBinaria esq) {
		this.esq = esq;
	}

	public NoArvoreBinaria getDir() {
		return dir;
	}

	public void setDir(NoArvoreBinaria dir) {
		this.dir = dir;
	}
}

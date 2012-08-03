package edu.org.records;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public class SymbolTable {
	
	private NoSymbolTable firt;
	
	public SymbolTable() {
		this.setPrin(null);
	}
	
	public NoSymbolTable getPrin() {
		return this.firt;
	}

	public void setPrin(NoSymbolTable prin) {
		this.firt = prin;
	}

	public void insertSymbol(String identifier, TypeIdentifier type, int address) {
		NoSymbolTable newSymbol = new NoSymbolTable();
		newSymbol.setSymbol(identifier, type, address);
		newSymbol.setProx(this.firt);
		
		this.firt = newSymbol;
	}
	
	public boolean isEmpty() {
		if (this.firt == null)
			return true;
		else
			return false;
	}
	
	public NoSymbolTable searchIdentifier(String identifier) {
		NoSymbolTable p = this.firt;
		
		while (p != null) {
			if (p.getSymbol().getIdentifier().equalsIgnoreCase(identifier))
				return p;	
		
			p = p.getProx();
		}
		
		return p;
	}
	
	public boolean checksExistenceAttribute(String identifier) {
		NoSymbolTable p = this.firt;
		
		while (p != null) {
			if (p.getSymbol().getIdentifier().equalsIgnoreCase(identifier))
				return true;	
		
			p = p.getProx();
		}
		
		return false;
	}
	
	public void libera() {
		this.firt = null;
	}
	

	public void imprimeRecursivo() {
		this.imprimeRecursivoAux(this.firt);
	}
	
	private void imprimeRecursivoAux(NoSymbolTable l) {
		if (l != null) {
			System.out.print(l.getSymbol() + " ");
			this.imprimeRecursivoAux(l.getProx());
		}
	}
}

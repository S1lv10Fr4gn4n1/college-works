package edu.org.records;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public class NoSymbolTable {
	private Symbol			symbol;
	private NoSymbolTable	prox;

	public NoSymbolTable() {
		this.setProx(null);
	}

	public void setSymbol(String identifier, TypeIdentifier type, int address) {
		this.symbol = new Symbol(identifier, type, address);
	}

	public Symbol getSymbol() {
		return this.symbol;
	}

	public void setProx(NoSymbolTable prox) {
		this.prox = prox;
	}

	public NoSymbolTable getProx() {
		return this.prox;
	}

	public String toString() {
		return String.valueOf(this.getSymbol());
	}
}

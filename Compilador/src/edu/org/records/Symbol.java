package edu.org.records;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public class Symbol {
	// nome do identificador
	private String			identifier;
	
	// tipo do identificador
	private TypeIdentifier	typeIdentifier;
	
	// endereco de memoria do identificador
	private int				address;

	public Symbol(String identifier, TypeIdentifier type, int address) {
		this.identifier = identifier;
		this.typeIdentifier = type;
		this.address = address;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public TypeIdentifier getTypeIdentifier() {
		return this.typeIdentifier;
	}

	public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
		this.typeIdentifier = typeIdentifier;
	}
	
	public int getNrTypeIdentifier() {
		return this.typeIdentifier.ordinal() + 1;
	}

	public int getAddress() {
		return this.address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

}

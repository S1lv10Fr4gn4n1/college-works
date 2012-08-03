package edu.org.records;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public class Instruction {
	private int		position;
	private String	instruction;
	private String	parameter;

	public Instruction(int position, String instruction, String parameter) {
		this.position = position;
		this.instruction = instruction;
		this.parameter = parameter;
	}
	
	public String toString() {
		return this.position + " " + this.instruction + " " + this.parameter;
	}

	public int getPosition() {
		return position;
	}

	public String getInstruction() {
		return instruction;
	}

	public String getParameter() {
		return parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}

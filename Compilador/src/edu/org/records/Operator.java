package edu.org.records;

/*
 * Desenvolvedores/Aluno:
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public enum Operator {

	ADD(OperatorKind.ARITIMETIC),

	SUB(OperatorKind.ARITIMETIC),

	MUL(OperatorKind.ARITIMETIC),

	DIV(OperatorKind.ARITIMETIC),

	/**
	 * >=
	 */
	BGE(OperatorKind.RELATIONAL),

	/**
	 * >
	 */
	BGR(OperatorKind.RELATIONAL),

	/**
	 * !=
	 */
	DIF(OperatorKind.RELATIONAL),

	/**
	 * ==
	 */
	EQL(OperatorKind.RELATIONAL),

	/**
	 * <=
	 */
	SME(OperatorKind.RELATIONAL),

	/**
	 * <
	 */
	SMR(OperatorKind.RELATIONAL),

	AND(OperatorKind.LOGIC),

	NOT(OperatorKind.LOGIC),

	OR(OperatorKind.LOGIC),
	
	ATT(OperatorKind.ATTRIBUTION), 
	
	ATTADD(OperatorKind.ATTRIBUTION),
	
	ATTSUB(OperatorKind.ATTRIBUTION),
	
	ATTMUL(OperatorKind.ATTRIBUTION);

	
	private final OperatorKind	operatorKind;

	private Operator(final OperatorKind operatorKind) {
		this.operatorKind = operatorKind;
	}

	public OperatorKind getOperatorKind() {
		return this.operatorKind;
	}

}

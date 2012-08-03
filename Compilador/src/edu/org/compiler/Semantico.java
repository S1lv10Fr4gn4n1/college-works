package edu.org.compiler;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JOptionPane;

import edu.org.records.Identifier;
import edu.org.records.Instruction;
import edu.org.records.NoSymbolTable;
import edu.org.records.Operator;
import edu.org.records.Symbol;
import edu.org.records.SymbolTable;
import edu.org.records.TypeIdentifier;

public class Semantico implements Constants {
	private int							address					= 1;
	private int							instructionPoint		= 1;

	private int							countSwitch				= -1;
	private int							countLoop				= -1;

	private Stack<Instruction>			stackJMFCase			= new Stack<Instruction>();
	private List<Stack<Instruction>>	stackJMPCase			= new ArrayList<Stack<Instruction>>();

	private Stack<Integer>				stackLoop				= new Stack<Integer>();
	private List<Stack<Instruction>>	stackBreak				= new ArrayList<Stack<Instruction>>();

	private Stack<String>				stackOperation			= new Stack<String>();

	private Stack<TypeIdentifier>		stackTypeIdentifiers	= new Stack<TypeIdentifier>();

	private SymbolTable					symbolTable				= new SymbolTable();
	private List<Identifier>			tableIdentifier			= new ArrayList<Identifier>();

	private Map<Integer, Instruction>	areaInstruction			= new LinkedHashMap<Integer, Instruction>();

	public void executeAction(int action, Token token) throws SemanticError {
		switch (action) {
			case 1:
				this.acaoSemantica01(action, token);
				break;

			case 2:
				this.acaoSemantica02(action, token);
				break;

			case 3:
				this.acaoSemantica03(action, token);
				break;

			case 4:
				this.acaoSemantica04(action, token);
				break;

			case 5:
				this.acaoSemantica05(action, token);
				break;

			case 6:
				this.acaoSemantica06(action, token);
				break;

			case 7:
				this.acaoSemantica07(action, token);
				break;

			case 8:
				this.acaoSemantica08(action, token);
				break;

			case 9:
				this.acaoSemantica09(action, token);
				break;

			case 10:
				this.acaoSemantica10(action, token);
				break;

			case 11:
				this.acaoSemantica11(action, token);
				break;

			case 12:
				this.acaoSemantica12(action, token);
				break;

			case 13:
				this.acaoSemantica13(action, token);
				break;

			case 14:
				this.acaoSemantica14(action, token);
				break;

			case 15:
				this.acaoSemantica15(action, token);
				break;

			case 16:
				this.acaoSemantica16(action, token);
				break;

			case 17:
				this.acaoSemantica17(action, token);
				break;

			case 18:
				this.acaoSemantica18(action, token);
				break;

			case 19:
				this.acaoSemantica19(action, token);
				break;

			case 20:
				this.acaoSemantica20(action, token);
				break;

			case 21:
				this.acaoSemantica21(action, token);
				break;

			case 22:
				this.acaoSemantica22(action, token);
				break;

			case 23:
				this.acaoSemantica23(action, token);
				break;

			case 24:
				this.acaoSemantica24(action, token);
				break;

			case 25:
				this.acaoSemantica25(action, token);
				break;

			case 26:
				this.acaoSemantica26(action, token);
				break;

			case 27:
				this.acaoSemantica27(action, token);
				break;

			case 28:
				this.acaoSemantica28(action, token);
				break;

			case 29:
				this.acaoSemantica29(action, token);
				break;

			case 30:
				this.acaoSemantica30(action, token);
				break;

			case 31:
				this.acaoSemantica31(action, token);
				break;

			case 32:
				this.acaoSemantica32(action, token);
				break;
		}
	}

	private void acaoSemantica01(int action, Token token) {
		this.setInstruction("STP", "0");
	}

	private void acaoSemantica02(int action, Token token) throws SemanticError {
		TypeIdentifier type = null;
		String instruction = "";

		if (token.getLexeme().equalsIgnoreCase("int")) {
			type = TypeIdentifier.INTEGER;
			instruction = "ALI";
		} else if (token.getLexeme().equalsIgnoreCase("float")) {
			type = TypeIdentifier.FLOAT;
			instruction = "ALR";
		} else if (token.getLexeme().equalsIgnoreCase("string")) {
			type = TypeIdentifier.STRING;
			instruction = "ALS";
		} else if (token.getLexeme().equalsIgnoreCase("boolean")) {
			type = TypeIdentifier.BOOLEAN;
			instruction = "ALB";
		}

		for (int i = 0; i < this.tableIdentifier.size(); i++) {
			Identifier id = this.tableIdentifier.get(i);

			if (this.symbolTable.checksExistenceAttribute(id.getName()))
				throw new SemanticError("identificador " + id.getName() + " já declarado", id.getPosition());

			this.symbolTable.insertSymbol(id.getName(), type, this.address);

			this.address++;
		}

		this.setInstruction(instruction, String.valueOf(this.tableIdentifier.size()));

		this.tableIdentifier.clear();
	}

	private void acaoSemantica03(int action, Token token) {
		// sem implementacao
	}

	private void acaoSemantica04(int action, Token token) throws SemanticError {
		Identifier id = new Identifier(token.getLexeme(), token.getPosition());
		this.tableIdentifier.add(id);
	}

	private void acaoSemantica05(int action, Token token) throws SemanticError {
		if (this.stackLoop.size() == 0) {
			throw new SemanticError("comando 'break' fora de contexto", token.getPosition());
		}

		Instruction instruction = this.setInstruction("JMP", "#break#");

		Stack<Instruction> stack = this.stackBreak.get(this.countLoop);
		stack.add(instruction);
	}

	private void acaoSemantica06(int action, Token token) {
		this.stackOperation.push(token.getLexeme());
	}

	private void acaoSemantica07(int action, Token token) throws SemanticError {
		if (this.tableIdentifier.size() == 0) {
			JOptionPane.showMessageDialog(null, "problemas...");
			return;
		}

		Identifier id = this.tableIdentifier.get(0);
		this.tableIdentifier.clear();

		NoSymbolTable noSymbol = this.symbolTable.searchIdentifier(id.getName());

		if (noSymbol == null)
			throw new SemanticError("identificador " + id.getName() + " não encontrado", token.getPosition());

		Symbol symbol = noSymbol.getSymbol();

		String operation = this.stackOperation.pop();

		if (operation.equals("=")) {
			this.checkSemanticError(Operator.ATT, token, symbol);
			this.setInstruction("STR", String.valueOf(symbol.getAddress()));
		} else if (operation.equals("+=")) {
			this.checkSemanticError(Operator.ATTADD, token, symbol);
			this.setInstruction("LDV", String.valueOf(symbol.getAddress()));
			this.setInstruction("ADD", "0");
			this.setInstruction("STR", String.valueOf(symbol.getAddress()));
		} else if (operation.equals("-=")) {
			this.checkSemanticError(Operator.ATTSUB, token, symbol);
			this.setInstruction("LDV", String.valueOf(symbol.getAddress()));
			this.setInstruction("SUB", "0");
			this.setInstruction("LDI", "-1");
			this.setInstruction("MUL", "0");
			this.setInstruction("STR", String.valueOf(symbol.getAddress()));
		} else if (operation.equals("*=")) {
			this.checkSemanticError(Operator.ATTMUL, token, symbol);
			this.setInstruction("LDV", String.valueOf(symbol.getAddress()));
			this.setInstruction("MUL", "0");
			this.setInstruction("STR", String.valueOf(symbol.getAddress()));
		}
	}

	private void acaoSemantica08(int action, Token token) throws SemanticError {
		for (int i = 0; i < this.tableIdentifier.size(); i++) {
			NoSymbolTable noSymbol = this.symbolTable.searchIdentifier(this.tableIdentifier.get(i).getName());

			if (noSymbol == null)
				throw new SemanticError("identificador " + token.getLexeme() + " não encontrado", token.getPosition());

			this.setInstruction("REA", String.valueOf(noSymbol.getSymbol().getNrTypeIdentifier()));
			this.setInstruction("STR", String.valueOf(noSymbol.getSymbol().getAddress()));
		}

		this.tableIdentifier.clear();
	}

	private void acaoSemantica09(int action, Token token) {
		this.setInstruction("WRT", "0");
		
		this.stackTypeIdentifiers.pop();
	}

	private void acaoSemantica10(int action, Token token) {
		this.swapCaseEnd();

		this.stackJMPCase.remove(this.countSwitch--);

		this.setInstruction("DEL", "0");
	}

	private void acaoSemantica11(int action, Token token) {
		this.setInstruction("CPY", "0");
	}

	private void acaoSemantica12(int action, Token token) throws SemanticError {
		this.checkSemanticError(token);
		
		this.setInstruction("EQL", "0");
		Instruction instruction = this.setInstruction("JMF", "#caseStart#");

		this.stackJMFCase.add(instruction);
	}

	private void acaoSemantica13(int action, Token token) {
		this.swapCaseStart(String.valueOf(this.instructionPoint + 1));

		Instruction instruction = this.setInstruction("JMP", "#caseEnd#");
		Stack<Instruction> stack = this.stackJMPCase.get(this.countSwitch);
		stack.add(instruction);
	}

	private void acaoSemantica14(int action, Token token) {
		this.stackLoop.push(this.instructionPoint);
		this.countLoop++;

		Stack<Instruction> stack = new Stack<Instruction>();
		this.stackBreak.add(stack);
	}

	private void acaoSemantica15(int action, Token token) {
		this.swapBreak();

		this.setInstruction("JMP", String.valueOf(this.stackLoop.pop()));

		this.stackBreak.remove(this.countLoop--);
	}

	private void acaoSemantica16(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.OR, token);
		this.setInstruction("OR", "0");
	}

	private void acaoSemantica17(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.AND, token);
		this.setInstruction("AND", "0");
	}

	private void acaoSemantica18(int action, Token token) {
		this.setInstruction("LDB", "T");

		this.stackTypeIdentifiers.push(TypeIdentifier.BOOLEAN);
	}

	private void acaoSemantica19(int action, Token token) {
		this.setInstruction("LDB", "F");

		this.stackTypeIdentifiers.push(TypeIdentifier.BOOLEAN);
	}

	private void acaoSemantica20(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.NOT, token);
		this.setInstruction("NOT", "0");
	}

	private void acaoSemantica21(int action, Token token) {
		this.stackOperation.push(token.getLexeme());
	}

	private void acaoSemantica22(int action, Token token) throws SemanticError {
		String operation = this.stackOperation.pop();

		if (operation.equals("==")) {
			this.checkSemanticError(Operator.EQL, token);
			this.setInstruction("EQL", "0");
		} else if (operation.equals("!=")) {
			this.checkSemanticError(Operator.DIF, token);
			this.setInstruction("DIF", "0");
		} else if (operation.equals("<")) {
			this.checkSemanticError(Operator.SMR, token);
			this.setInstruction("SMR", "0");
		} else if (operation.equals("<=")) {
			this.checkSemanticError(Operator.SME, token);
			this.setInstruction("SME", "0");
		} else if (operation.equals(">")) {
			this.checkSemanticError(Operator.BGR, token);
			this.setInstruction("BGR", "0");
		} else if (operation.equals(">=")) {
			this.checkSemanticError(Operator.BGE, token);
			this.setInstruction("BGE", "0");
		}
	}

	private void acaoSemantica23(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.ADD, token);
		this.setInstruction("ADD", "0");
	}

	private void acaoSemantica24(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.SUB, token);
		this.setInstruction("SUB", "0");
	}

	private void acaoSemantica25(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.MUL, token);
		this.setInstruction("MUL", "0");
	}

	private void acaoSemantica26(int action, Token token) throws SemanticError {
		this.checkSemanticError(Operator.DIV, token);
		this.setInstruction("DIV", "0");
	}

	private void acaoSemantica27(int action, Token token) throws SemanticError {
		NoSymbolTable noSymbol = this.symbolTable.searchIdentifier(token.getLexeme());

		if (noSymbol == null)
			throw new SemanticError("identificador " + token.getLexeme() + " não encontrado", token.getPosition());

		Symbol symbol = noSymbol.getSymbol();

		this.setInstruction("LDV", String.valueOf(symbol.getAddress()));

		this.stackTypeIdentifiers.push(symbol.getTypeIdentifier());
	}

	private void acaoSemantica28(int action, Token token) {
		this.setInstruction("LDI", token.getLexeme());

		this.stackTypeIdentifiers.push(TypeIdentifier.INTEGER);
	}

	private void acaoSemantica29(int action, Token token) {
		this.setInstruction("LDR", token.getLexeme());

		this.stackTypeIdentifiers.push(TypeIdentifier.FLOAT);
	}

	private void acaoSemantica30(int action, Token token) {
		String tokeString = token.getLexeme();
		tokeString = tokeString.replace("'", "\"");

		this.setInstruction("LDS", tokeString);

		this.stackTypeIdentifiers.push(TypeIdentifier.STRING);
	}

	private void acaoSemantica31(int action, Token token) {
		this.setInstruction("LDI", "-1");
		this.setInstruction("MUL", "0");
	}

	private void acaoSemantica32(int action, Token token) {
		this.countSwitch++;

		Stack<Instruction> stack = new Stack<Instruction>();
		this.stackJMPCase.add(stack);
	}

	public String getCodeObject() {
		StringBuilder sb = new StringBuilder();

		for (Instruction instruction : this.areaInstruction.values()) {
			sb.append(instruction);
			sb.append("\n");
		}

		return sb.toString();
	}

	private Instruction setInstruction(String command, String parameter) {
		Instruction instruction = new Instruction(this.instructionPoint, command, parameter);

		this.areaInstruction.put(instruction.getPosition(), instruction);
		this.instructionPoint++;

		return instruction;
	}

	private void swapCaseEnd() {
		for (Instruction instruction : this.stackJMPCase.get(this.countSwitch)) {
			instruction.setParameter(String.valueOf(this.instructionPoint));
		}
	}

	private void swapBreak() {
		for (Instruction instruction : this.stackBreak.get(this.countLoop)) {
			instruction.setParameter(String.valueOf(this.instructionPoint + 1));
		}
	}

	private void swapCaseStart(String parameter) {
		Instruction instruction = this.stackJMFCase.pop();
		instruction.setParameter(parameter);
	}

	private void checkSemanticError(Operator operation, Token token, Symbol symbol) throws SemanticError {
		if (this.stackTypeIdentifiers.size() == 0) {
			JOptionPane.showMessageDialog(null, "Problemas no check semantic error!");
			return;
		}

		TypeIdentifier typeId = this.stackTypeIdentifiers.pop();

		if (operation == Operator.ATT) {
			// se o valor da expressao for diferente do identificador
			if (typeId != symbol.getTypeIdentifier())
				// porem um float pode atribuir um integer
				if (!(typeId == TypeIdentifier.INTEGER && symbol.getTypeIdentifier() == TypeIdentifier.FLOAT)) {
					throw new SemanticError("tipos incompativeis:\n" + symbol.getIdentifier() + 
											" é tipo " + symbol.getTypeIdentifier() + ", poderem a expressao atribuida é do tipo " + typeId, token.getPosition());
				}
		// para atribuicoes com += ou -= ou *= somente pode ser para float e integer
		} else if (!this.isValidType(symbol.getTypeIdentifier(), typeId, TypeIdentifier.STRING, TypeIdentifier.BOOLEAN)) {
			throw new SemanticError("tipos incompativeis:\n" + symbol.getIdentifier() + " é tipo " + symbol.getTypeIdentifier() +
									", esse tipo de atribuição não é aceita.", token.getPosition());
			
		// se for float pode receber integer
		} else if (symbol.getTypeIdentifier() == TypeIdentifier.INTEGER && typeId == TypeIdentifier.FLOAT) {
			throw new SemanticError("tipos incompativeis:\n" + symbol.getIdentifier() + 
					" é tipo " + symbol.getTypeIdentifier() + ", poderem a expressao atribuida é do tipo " + typeId, token.getPosition());
		}
	}

	private void checkSemanticError(Operator operation, Token token) throws SemanticError {
		if (this.stackTypeIdentifiers.size() <= 1) {
			JOptionPane.showMessageDialog(null, "Problemas no check semantic error!");
			return;
		}

		TypeIdentifier t1 = this.stackTypeIdentifiers.pop();
		TypeIdentifier t2 = null;

		if (operation != Operator.NOT) {
			t2 = this.stackTypeIdentifiers.pop();
		}

		switch (operation.getOperatorKind()) {
			case ARITIMETIC:

				if (!(this.isValidType(t1, t2, TypeIdentifier.STRING, TypeIdentifier.BOOLEAN))) {
					throw new SemanticError("tipos incompativeis, " + t1 + " e " + t2, token.getPosition());
				}

				if ((t1 == TypeIdentifier.FLOAT) || (t2 == TypeIdentifier.FLOAT)) {
					this.stackTypeIdentifiers.push(TypeIdentifier.FLOAT);
				} else if ((t1 == TypeIdentifier.INTEGER) && (t2 == TypeIdentifier.INTEGER) && (operation == Operator.DIV)) {
					this.stackTypeIdentifiers.push(TypeIdentifier.FLOAT);
				} else if ((t1 == TypeIdentifier.INTEGER) && (t2 == TypeIdentifier.INTEGER)) {
					this.stackTypeIdentifiers.push(TypeIdentifier.INTEGER);
				}

				break;

			case LOGIC:
				if (!(this.isValidType(t1, t2, TypeIdentifier.STRING, TypeIdentifier.FLOAT, TypeIdentifier.INTEGER))) {
					throw new SemanticError("tipos incompativeis, " + t1 + " e " + t2, token.getPosition());
				}

				this.stackTypeIdentifiers.push(TypeIdentifier.BOOLEAN);

				break;

			case RELATIONAL:
				if (!(this.isValidType(t1, t2, TypeIdentifier.BOOLEAN))) {
					throw new SemanticError("tipos incompativeis, " + t1 + " e " + t2, token.getPosition());
				}

				if (t1 == TypeIdentifier.STRING && t2 == TypeIdentifier.STRING) {
					this.stackTypeIdentifiers.push(TypeIdentifier.BOOLEAN);
				} else if (!(this.isValidType(t1, t2, TypeIdentifier.STRING))) {
					throw new SemanticError("tipos incompativeis, " + t1 + " e " + t2, token.getPosition());
				} else {
					this.stackTypeIdentifiers.push(TypeIdentifier.BOOLEAN);
				}

				break;
		}

	}
	
	private void checkSemanticError(Token token) throws SemanticError {
		// verificacao semantica para o Switch Case
		
		if (this.stackTypeIdentifiers.size() <= 1) {
			JOptionPane.showMessageDialog(null, "Problemas, pilha de tipos insuficiente para verificar o Case");
			return;
		}
		
		TypeIdentifier typeCase   = this.stackTypeIdentifiers.pop();
		TypeIdentifier typeSwitch = this.stackTypeIdentifiers.pop();
		
		if (typeCase != typeSwitch) {
			if (this.isValidType(typeSwitch, null, TypeIdentifier.FLOAT, TypeIdentifier.INTEGER) || 
				this.isValidType(typeCase, null, TypeIdentifier.FLOAT, TypeIdentifier.INTEGER)) {
				throw new SemanticError("tipos incompativeis, " + typeCase + " e " + typeSwitch, token.getPosition());
			}
		}

		// se nao tiver erro volta empilhar o tipo da expressao do switch
		this.stackTypeIdentifiers.push(typeSwitch);
	}

	private boolean isValidType(TypeIdentifier type1, TypeIdentifier type2, TypeIdentifier... assertType) {
		if (assertType != null) {
			for (final TypeIdentifier typeIdentifier : assertType) {
				if (type1 == typeIdentifier || type2 == typeIdentifier) {
					return false;
				}
			}
		}
		return true;
	}

}

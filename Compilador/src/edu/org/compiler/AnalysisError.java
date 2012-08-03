package edu.org.compiler;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public class AnalysisError extends Exception {
	private static final long serialVersionUID = -2860425629417372101L;
	private int position;

    public AnalysisError(String msg, int position)
    {
        super(msg);
        this.position = position;
    }

    public AnalysisError(String msg)
    {
        super(msg);
        this.position = -1;
    }

    public int getPosition()
    {
        return position;
    }

    public String toString()
    {
        return super.toString() + ", @ "+position;
    }
}

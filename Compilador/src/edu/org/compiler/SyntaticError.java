package edu.org.compiler;

/*
 * Desenvolvedores/Aluno: 
 * 
 * Marcelo Ferreira da Silva 
 * Silvio Fragnani da Silva
 * 
 * Ano:2010/1
 */

public class SyntaticError extends AnalysisError
{
    /**
     * @since 19/05/2010
     */
    private static final long serialVersionUID = -9163669189868163260L;

    public SyntaticError(String msg, int position)
	 {
        super(msg, position);
    }

    public SyntaticError(String msg)
    {
        super(msg);
    }
}

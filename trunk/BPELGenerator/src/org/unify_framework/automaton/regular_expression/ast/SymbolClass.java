package org.unify_framework.automaton.regular_expression.ast;

import java.util.Collection;

public interface SymbolClass extends Atom{
	
	public Collection<Symbol> getSymbols();
	public boolean isNegated();

}

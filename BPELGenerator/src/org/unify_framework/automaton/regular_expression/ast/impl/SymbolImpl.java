package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;
import org.unify_framework.automaton.regular_expression.ast.Symbol;

public class SymbolImpl extends AtomImpl implements Symbol{
	String symbol;

	@Override
	public String getSymbol() {
		return this.symbol;
	}

	public SymbolImpl(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public void accept(RegExpTreeVisitor visitor) {
		visitor.visit(this);
	}
	
	
}

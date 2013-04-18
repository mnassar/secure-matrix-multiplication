package org.unify_framework.automaton.regular_expression.ast.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;
import org.unify_framework.automaton.regular_expression.ast.Symbol;
import org.unify_framework.automaton.regular_expression.ast.SymbolClass;

public class SymbolClassImpl extends AtomImpl implements SymbolClass{
	Collection<Symbol> symbols;
	boolean negated;
	
	
	@Override
	public Collection<Symbol> getSymbols() {
		return this.symbols;
	}

	@Override
	public boolean isNegated() {
		return this.negated;
	}

	public SymbolClassImpl(Symbol... symbols){
		this.symbols = new HashSet<Symbol>(Arrays.asList(symbols));
		this.negated = false;
	}
	
	public SymbolClassImpl(boolean negated, Symbol...symbols){
		this(symbols);
		this.negated = negated;
	}

	@Override
	public void accept(RegExpTreeVisitor visitor) {
		visitor.visit(this);
	}
}

package org.unify_framework.automaton.regular_expression.ast;

public interface Alternative extends RegExp{
	
	public RegExp getLhs();
	public RegExp getRhs();

}

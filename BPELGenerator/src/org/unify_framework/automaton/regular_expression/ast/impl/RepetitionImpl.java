package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.Repetition;

public abstract class RepetitionImpl extends RegExpImpl implements Repetition{
	RegExp lhs;

	@Override
	public RegExp getLhs() {
		return this.lhs;
	}

	public RepetitionImpl(RegExp lhs) {
		super();
		this.lhs = lhs;
	}	
	
	
}

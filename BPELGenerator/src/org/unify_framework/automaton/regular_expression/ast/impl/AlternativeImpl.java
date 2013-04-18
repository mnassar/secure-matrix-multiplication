package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.Alternative;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;

public class AlternativeImpl extends RegExpImpl implements Alternative{
	RegExp lhs;
	RegExp rhs;
	
	@Override
	public RegExp getLhs() {
		return this.lhs;
	}

	@Override
	public RegExp getRhs() {
		return this.rhs;
	}

	public AlternativeImpl(RegExp lhs, RegExp rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public void accept(RegExpTreeVisitor visitor) {
		visitor.visit(this);
	}
	
	
	
}

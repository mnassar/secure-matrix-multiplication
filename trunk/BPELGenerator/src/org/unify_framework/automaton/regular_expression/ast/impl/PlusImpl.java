package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.Plus;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;

public class PlusImpl extends RepetitionImpl implements Plus {

	public PlusImpl(RegExp lhs) {
		
		super(lhs);
	}

	@Override
	public void accept(RegExpTreeVisitor visitor) {
		
		visitor.visit(this);
	}
}

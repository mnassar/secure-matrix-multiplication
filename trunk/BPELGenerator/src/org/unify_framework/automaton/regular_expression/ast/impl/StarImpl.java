package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;
import org.unify_framework.automaton.regular_expression.ast.Star;

public class StarImpl extends RepetitionImpl implements Star{

	public StarImpl(RegExp lhs) {
		
		super(lhs);
	}

	@Override
	public void accept(RegExpTreeVisitor visitor) {
		
		visitor.visit(this);
	}
}

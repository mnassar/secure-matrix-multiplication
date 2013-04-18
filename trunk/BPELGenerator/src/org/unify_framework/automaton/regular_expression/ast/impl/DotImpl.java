package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.Dot;
import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;

public class DotImpl extends AtomImpl implements Dot {

	@Override
	public void accept(RegExpTreeVisitor visitor) {
		visitor.visit(this);

	}

}

package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;

public abstract class RegExpImpl implements RegExp{

	@Override
	public abstract void accept(RegExpTreeVisitor visitor);
	
	
	public final static RegExp DotStar(){
		return new StarImpl(new DotImpl());
	}
	
}

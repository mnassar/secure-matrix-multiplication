package org.unify_framework.automaton.regular_expression.ast;

import org.unify_framework.automaton.regular_expression.ast.impl.DotImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.StarImpl;


public interface RegExp {
	
	public void accept(RegExpTreeVisitor visitor);
	
	static public final RegExp DOT_STAR = new StarImpl(new DotImpl());
	
}

package org.unify_framework.automaton.constraint;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.impl.ConcatenationImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.SymbolImpl;

public class Before extends Predicate {
	Activity a,b;
	
	
	
	public Before(Activity a, Activity b) {
		super();
		this.a = a;
		this.b = b;
	}



	@Override
	public boolean eval(CompositeActivity activity) {
		// .*b.*a.*
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				RegExp.DOT_STAR,
				new SymbolImpl(this.b.getName()),
				RegExp.DOT_STAR,
				new SymbolImpl(this.a.getName()),
				RegExp.DOT_STAR
		);
		
		return new Excludes(reg).eval(activity);
		
	}

}

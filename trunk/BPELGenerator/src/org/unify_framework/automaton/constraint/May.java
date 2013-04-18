package org.unify_framework.automaton.constraint;

import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.impl.ConcatenationImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.SymbolImpl;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.CompositeActivity;

public class May extends Predicate {

	private Activity a, b;
	
	public May(Activity a, Activity b) {
		
		super();
		this.a = a;
		this.b = b;
	}
	
	@Override
	public boolean eval(CompositeActivity activity) {
		
		// .*a.*b.*
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				RegExp.DOT_STAR,
				new SymbolImpl(this.a.getName()),
				RegExp.DOT_STAR,
				new SymbolImpl(this.b.getName()),
				RegExp.DOT_STAR
		);
		return new Includes(reg).eval(activity);
	}
}

package org.unify_framework.automaton.constraint;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.impl.ConcatenationImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.StarImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.SymbolClassImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.SymbolImpl;

public class Between extends Predicate {

	Activity a;
	Activity b;
	Activity c;
	
	
	
	
	
	public Between(Activity a, Activity b, Activity c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}



	@Override
	public boolean eval(CompositeActivity activity) {
		//.*a[^c]*b.*
		
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				RegExp.DOT_STAR,
				new SymbolImpl(this.a.getName()),
				new StarImpl(
						new SymbolClassImpl(true, new SymbolImpl(this.c.getName()))
				),
				new SymbolImpl(this.b.getName()),
				RegExp.DOT_STAR
		);
		
		return new Excludes(reg).eval(activity);
	}

}

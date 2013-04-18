package org.unify_framework.automaton.constraint;

import org.unify_framework.automaton.Automaton;
import org.unify_framework.automaton.regular_expression.ast.RegExp;

public class Excludes extends RegExpPredicate {

	public Excludes(RegExp expression) {
		super(expression);
	}
	
	
	@Override
	public boolean eval(Automaton wf) {
		return !super.eval(wf); 
	}

}

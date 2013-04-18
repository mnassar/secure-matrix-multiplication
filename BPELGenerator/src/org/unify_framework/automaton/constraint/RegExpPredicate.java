package org.unify_framework.automaton.constraint;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.automaton.Automaton;
import org.unify_framework.automaton.constraint.test.TestConstraints;
import org.unify_framework.automaton.regular_expression.AutomatonConvertor;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.translator.Translator;

public class RegExpPredicate extends Predicate{

	private RegExp expression;
	
	public RegExp getExpression() {
		return this.expression;
	}

	public RegExpPredicate(RegExp expression) {
		this.expression = expression;
	}
	
	protected Automaton getAutomatonForExpression() {
		
		// FIXME
		return AutomatonConvertor.convert(this.expression).toNFA(true).toDFA().reduce().group();
	}
	
	protected  boolean eval(Automaton wf){
		Automaton automatonForExpression = getAutomatonForExpression();
		TestConstraints.printAutomaton("regexp.dot", automatonForExpression);
		TestConstraints.printAutomaton("wf.dot", wf);
		Automaton res = wf.intersect(automatonForExpression).reduce();
		TestConstraints.printAutomaton("automatonMatch.dot", res);
		return res.hasFinalState();
	}
	
	@Override
	public final boolean eval(CompositeActivity a) {
		Automaton wf = new Translator().translate(a);
		return eval(wf.toNFA(true).toDFA());
	}
	
}

package org.unify_framework.automaton.constraint;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.CompositeActivity;

public class Exclude extends Predicate {
	
	private Activity a,b,m;
	
	public Exclude(Activity a, Activity b, Activity m) {
		
		super();
		this.a = a;
		this.b = b;
		this.m = m;
	}
	
	@Override
	public boolean eval(CompositeActivity activity) {
		
		boolean may1 = new May(this.m, this.a).eval(activity);
		boolean may2 = new May(this.m, this.b).eval(activity);
		return !may1 && !may2;
	}
}

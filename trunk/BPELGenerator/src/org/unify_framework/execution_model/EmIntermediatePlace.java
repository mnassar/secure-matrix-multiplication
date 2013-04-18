package org.unify_framework.execution_model;

import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.execution_model.visitors.ElementVisitor;

public class EmIntermediatePlace extends EmPlace {
	
	private Transition transition;
	
	public EmIntermediatePlace(String name, Transition transition) {
		
		super(name);
		this.transition = transition;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public Transition getTransition() {
		
		return this.transition;
	}
}

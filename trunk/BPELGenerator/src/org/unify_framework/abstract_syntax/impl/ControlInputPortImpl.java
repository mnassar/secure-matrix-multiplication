package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

public class ControlInputPortImpl extends ControlPortImpl implements ControlInputPort {
	
	private Transition incomingTransition;
	
	public ControlInputPortImpl(String name, NodeImpl parent) {
		
		super(name, parent);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public Transition getIncomingTransition() {
		
		return this.incomingTransition;
	}

	@Override
	public void setIncomingTransition(Transition incomingTransition) {
		
		this.incomingTransition = incomingTransition;
	}
}

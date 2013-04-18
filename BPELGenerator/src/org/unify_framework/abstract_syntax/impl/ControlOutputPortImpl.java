package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

public class ControlOutputPortImpl extends ControlPortImpl implements ControlOutputPort {
	
	private Transition outgoingTransition;
	
	public ControlOutputPortImpl(String name, NodeImpl parent) {
		
		super(name, parent);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public Transition getOutgoingTransition() {
		
		return this.outgoingTransition;
	}

	@Override
	public void setOutgoingTransition(Transition outgoingTransition) {
		
		this.outgoingTransition = outgoingTransition;
	}
}

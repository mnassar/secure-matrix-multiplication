package org.unify_framework.abstract_syntax.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

public class TransitionImpl implements Transition {
	
	private ControlOutputPort source;
	private ControlInputPort destination;
	private boolean woven;
	
	public TransitionImpl(ControlOutputPort source, ControlInputPort destination) {
		
		this(source, destination, false);
	}
	
	public TransitionImpl(ControlOutputPort source, ControlInputPort destination, boolean woven) {
		
		this.source = source;
		this.destination = destination;
		setWoven(woven);
		
		source.setOutgoingTransition(this);
		destination.setIncomingTransition(this);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public TransitionImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		ControlOutputPortImpl copiedSource = (ControlOutputPortImpl) mapping.get(getSource());
		ControlInputPortImpl copiedDestination = (ControlInputPortImpl) mapping.get(getDestination());
		TransitionImpl copy = new TransitionImpl(copiedSource, copiedDestination);
		return copy;
	}

	@Override
	public ControlInputPort getDestination() {
		
		return this.destination;
	}
	
	public String getName() {
		
		return "NYI";
	}
	
	public String getQualifiedName() {
		
		return "NYI";
	}

	@Override
	public ControlOutputPort getSource() {
		
		return this.source;
	}

	@Override
	public Node getSourceNode() {
		
		if (this.source != null) {
			return this.source.getParent();
		}
		return null;
	}

	@Override
	public Node getDestinationNode() {
		
		if (this.destination != null) {
			return this.destination.getParent();
		}
		return null;
	}
	
	public boolean isWoven() {
		
		return this.woven;
	}

	@Override
	public void replaceDestination(ControlInputPort oldDestination, ControlInputPort newDestination) {
		
		oldDestination.setIncomingTransition(null);
		newDestination.setIncomingTransition(this);
		this.destination = newDestination;
	}

	@Override
	public void replaceSource(ControlOutputPort oldSource, ControlOutputPort newSource) {
		
		oldSource.setOutgoingTransition(null);
		newSource.setOutgoingTransition(this);
		this.source = newSource;
	}
	
	protected void setWoven(boolean woven) {
		
		this.woven = woven;
	}
	
	@Override
	public String toString() {
		
		return getSource().getQualifiedName() + " --> " + getDestination().getQualifiedName();
	}
}

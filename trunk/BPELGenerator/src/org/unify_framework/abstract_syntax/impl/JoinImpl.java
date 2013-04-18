package org.unify_framework.abstract_syntax.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Join;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Transition;

public abstract class JoinImpl extends ControlNodeImpl implements Join {
	
	public JoinImpl(String name, boolean woven) {
		
		super(name, woven);
		super.getNewControlOutputPort();
	}

	@Override
	public ControlOutputPort getControlOutputPort() {
		
		Iterator<ControlOutputPort> iterator = getControlOutputPorts().iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		throw new RuntimeException("This join does not have a control output port!");
	}
	
	@Override
	public abstract JoinImpl getCopy(Map<ControlPort, ControlPort> mapping);

	@Override
	public Node getDestinationNode() {
		
		return getControlOutputPort().getOutgoingTransition().getDestination().getParent();
	}
	
	@Override
	public ControlOutputPortImpl getNewControlOutputPort() {
		
		throw new RuntimeException("A join may only have one control output port!");
	}

	@Override
	public Transition getOutgoingTransition() {
		
		return getControlOutputPort().getOutgoingTransition();
	}

	@Override
	public List<Node> getSourceNodes() {
		
		List<Node> result = new LinkedList<Node>();
		for (ControlInputPort controlIn : getControlInputPorts()) {
			Transition incomingTransition = controlIn.getIncomingTransition();
			ControlOutputPort source = incomingTransition.getSource();
			Node sourceNode = source.getParent();
			result.add(sourceNode);
		}
		return result;
	}
}

package org.unify_framework.abstract_syntax.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Split;
import org.unify_framework.abstract_syntax.Transition;

public abstract class SplitImpl extends ControlNodeImpl implements Split {
	
	public SplitImpl(String name, boolean woven) {
		
		super(name, woven);
		super.getNewControlInputPort();
	}
	
	@Override
	public ControlInputPort getControlInputPort() {
		
		Iterator<ControlInputPort> iterator = getControlInputPorts().iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		throw new RuntimeException("This split does not have a control input port!");
	}
	
	@Override
	public abstract SplitImpl getCopy(Map<ControlPort, ControlPort> mapping);

	@Override
	public List<Node> getDestinationNodes() {
		
		List<Node> result = new LinkedList<Node>();
		for (ControlOutputPort controlOutputPort : getControlOutputPorts()) {
			Transition outgoingTransition = controlOutputPort.getOutgoingTransition();
			ControlInputPort destination = outgoingTransition.getDestination();
			Node destinationNode = destination.getParent();
			result.add(destinationNode);
		}
		return result;
	}

	@Override
	public Transition getIncomingTransition() {
		
		return getControlInputPort().getIncomingTransition();
	}
	
	@Override
	public ControlInputPortImpl getNewControlInputPort() {
		
		throw new RuntimeException("A split may only have one control input port!");
	}

	@Override
	public Node getSourceNode() {
		
		return getControlInputPort().getIncomingTransition().getSource().getParent();
	}
}

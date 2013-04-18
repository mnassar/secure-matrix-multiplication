package org.unify_framework.abstract_syntax.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Transition;

public abstract class ActivityImpl extends NodeImpl implements Activity {
	
	public ActivityImpl(String name, boolean woven) {
		
		super(name, woven);
		super.getNewControlInputPort();
		super.getNewControlOutputPort();
	}
	
	@Override
	public ControlInputPort getControlInputPort() {
		
		Iterator<ControlInputPort> iterator = getControlInputPorts().iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		throw new RuntimeException("This activity does not have a control input port!");
	}

	@Override
	public ControlOutputPort getControlOutputPort() {
		
		Iterator<ControlOutputPort> iterator = getControlOutputPorts().iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		throw new RuntimeException("This activity does not have a control output port!");
	}

	@Override
	public ActivityImpl getCopy() {
		
		Map<ControlPort, ControlPort> mapping = new HashMap<ControlPort, ControlPort>();
		return getCopy(mapping);
	}
	
	@Override
	public abstract ActivityImpl getCopy(Map<ControlPort, ControlPort> mapping);

	@Override
	public Transition getIncomingTransition() {
		
		return getControlInputPort().getIncomingTransition();
	}
	
	@Override
	public ControlInputPortImpl getNewControlInputPort() {
		
		throw new RuntimeException("An activity may only have one control input port!");
	}
	
	@Override
	public ControlOutputPortImpl getNewControlOutputPort() {
		
		throw new RuntimeException("An activity may only have one control output port!");
	}

	@Override
	public Transition getOutgoingTransition() {
		
		return getControlOutputPort().getOutgoingTransition();
	}
}

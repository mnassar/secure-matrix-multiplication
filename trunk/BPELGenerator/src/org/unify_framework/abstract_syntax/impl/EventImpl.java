package org.unify_framework.abstract_syntax.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Event;

public abstract class EventImpl extends NodeImpl implements Event {
	
	public EventImpl(String name, CompositeActivity parent) {
		
		super(name, parent);
	}
	
	@Override
	public abstract Event getCopy(Map<ControlPort, ControlPort> mapping);
}

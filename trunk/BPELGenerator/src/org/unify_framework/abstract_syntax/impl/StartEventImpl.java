package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

import java.util.Iterator;
import java.util.Map;

/**
 * Reference implementation of the Unify {@link StartEvent} interface.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: StartEventImpl.java 12947 2010-08-11 13:31:10Z njonchee $
 */
public class StartEventImpl extends EventImpl implements StartEvent {
	
	public StartEventImpl(String name) {
		
		this(name, null);
	}
	
	public StartEventImpl(String name, CompositeActivityImpl parent) {
		
		super(name, parent);
		getNewControlOutputPort();
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public ControlOutputPort getControlOutputPort() {
		
		Iterator<ControlOutputPort> iterator = getControlOutputPorts().iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		throw new RuntimeException("This start event does not have a control output port!");
	}
	
	@Override
	public StartEvent getCopy(Map<ControlPort, ControlPort> mapping) {
		
		StartEvent copy = new StartEventImpl(getName());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	@Override
	public Transition getOutgoingTransition() {
		
		return getControlOutputPort().getOutgoingTransition();
	}
}

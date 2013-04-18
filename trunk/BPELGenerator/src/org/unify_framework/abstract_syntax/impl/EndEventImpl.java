package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

import java.util.Iterator;
import java.util.Map;

/**
 * Reference implementation of the Unify {@link EndEvent} interface.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: EndEventImpl.java 12947 2010-08-11 13:31:10Z njonchee $
 */
public class EndEventImpl extends EventImpl implements EndEvent {
	
	public EndEventImpl(String name) {
		
		this(name, null);
	}
	
	public EndEventImpl(String name, CompositeActivity parent) {
		
		super(name, parent);
		getNewControlInputPort();
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
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
	public EndEvent getCopy(Map<ControlPort, ControlPort> mapping) {
		
		EndEvent copy = new EndEventImpl(getName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		return copy;
	}
	
	@Override
	public Transition getIncomingTransition() {
		
		return getControlInputPort().getIncomingTransition();
	}
}

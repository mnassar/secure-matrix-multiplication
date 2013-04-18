package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.XorJoinImpl;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelXorJoin extends XorJoinImpl {
	
	public BpelXorJoin(String name) {
		
		super(name);
	}
	
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelXorJoin getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelXorJoin copy = new BpelXorJoin(getName());
		for (ControlInputPort cip : getControlInputPorts()) {
			mapping.put(cip, copy.getNewControlInputPort());
		}
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

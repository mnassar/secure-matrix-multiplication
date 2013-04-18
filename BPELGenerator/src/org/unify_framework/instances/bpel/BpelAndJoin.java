package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;

public class BpelAndJoin extends AndJoinImpl {
	
	public BpelAndJoin(String name) {
		
		super(name);
	}
	
//	public void accept(ElementVisitor visitor) {
//		
//		visitor.visit(this);
//	}
	
	@Override
	public BpelAndJoin getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelAndJoin copy = new BpelAndJoin(getName());
		for (ControlInputPort cip : getControlInputPorts()) {
			mapping.put(cip, copy.getNewControlInputPort());
		}
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

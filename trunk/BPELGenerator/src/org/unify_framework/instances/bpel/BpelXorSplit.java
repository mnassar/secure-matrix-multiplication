package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.XorSplitImpl;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelXorSplit extends XorSplitImpl {
	
	public BpelXorSplit(String name) {
		
		super(name);
	}
	
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelXorSplit getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelXorSplit copy = new BpelXorSplit(getName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		for (ControlOutputPort cop : getControlOutputPorts()) {
			mapping.put(cop, copy.getNewControlOutputPort());
		}
		return copy;
	}
}

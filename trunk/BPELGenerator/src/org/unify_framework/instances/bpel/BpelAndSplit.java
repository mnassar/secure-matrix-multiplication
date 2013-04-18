package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelAndSplit extends AndSplitImpl {
	
	public BpelAndSplit(String name) {
		
		super(name);
	}
	
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelAndSplit getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelAndSplit copy = new BpelAndSplit(getName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		for (ControlOutputPort cop : getControlOutputPorts()) {
			mapping.put(cop, copy.getNewControlOutputPort());
		}
		return copy;
	}
}

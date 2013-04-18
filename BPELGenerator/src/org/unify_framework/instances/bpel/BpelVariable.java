package org.unify_framework.instances.bpel;

import org.unify_framework.abstract_syntax.data_perspective.impl.VariableImpl;
import org.unify_framework.instances.bpel.visitors.Element;

public abstract class BpelVariable extends VariableImpl implements Element {
	
	public BpelVariable(String name) {
		
		super(name);
	}
	
	public abstract BpelVariable getCopy();
}

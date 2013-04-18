package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.Element;

public abstract class BpelFrom implements Element {
	
	private BpelCopy parent;
	
	public abstract BpelFrom getCopy();

	public BpelCopy getParent() {
		
		return this.parent;
	}

	public void setParent(BpelCopy parent) {
		
		this.parent = parent;
	}
}

package org.unify_framework.instances.bpel.visitors;

public interface Element {
	
	public void accept(ElementVisitor visitor);
}

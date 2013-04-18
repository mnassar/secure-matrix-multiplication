package org.unify_framework.execution_model.visitors;

public interface Element {
	
	public void accept(ElementVisitor visitor);
}

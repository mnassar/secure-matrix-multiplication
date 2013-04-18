package org.unify_framework.concrete_syntax.visitors;

public interface Element {
	
	public void accept(ElementVisitor visitor);
}

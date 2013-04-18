package org.unify_framework.abstract_syntax.visitors;

public interface Element {
	
	public void accept(ElementVisitor visitor);
}

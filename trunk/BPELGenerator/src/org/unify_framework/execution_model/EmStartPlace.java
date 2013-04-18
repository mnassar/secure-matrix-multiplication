package org.unify_framework.execution_model;

import org.unify_framework.execution_model.visitors.ElementVisitor;

public class EmStartPlace extends EmPlace {
	
	public EmStartPlace(String name) {
		
		super(name);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

package org.unify_framework.execution_model;

import org.unify_framework.execution_model.visitors.ElementVisitor;

public class EmEndPlace extends EmPlace {
	
	public EmEndPlace(String name) {
		
		super(name);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

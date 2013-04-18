package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.ControlPort;

public abstract class ControlPortImpl implements ControlPort {
	
	private String name;
	private NodeImpl parent;
	
	public ControlPortImpl(String name, NodeImpl parent) {
		
		this.name = name;
		this.parent = parent;
	}

	@Override
	public String getCondition() {
		
		throw new RuntimeException("NYI");
	}

	@Override
	public String getName() {
		
		return this.name;
	}
	
	public NodeImpl getParent() {
		
		return this.parent;
	}

	@Override
	public String getQualifiedName() {
		
		if (this.parent == null) {
			return getName();
		} else {
			return this.parent.getQualifiedName() + "." + getName();
		}
	}
}

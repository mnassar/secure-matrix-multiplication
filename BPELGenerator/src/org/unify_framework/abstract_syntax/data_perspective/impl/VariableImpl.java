package org.unify_framework.abstract_syntax.data_perspective.impl;

import org.unify_framework.abstract_syntax.data_perspective.Variable;

public class VariableImpl implements Variable {
	
	private String name;
	
	public VariableImpl(String name) {
		
		this.name = name;
	}

	@Override
	public String getName() {
		
		return this.name;
	}
	
	public Variable getCopy() {
		
		return new VariableImpl(this.name);
	}
}

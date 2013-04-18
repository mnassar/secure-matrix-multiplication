package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.unify_framework.abstract_syntax.data_perspective.Scope;
import org.unify_framework.abstract_syntax.data_perspective.Variable;

public class ScopeImpl implements Scope {
	
	private Map<String, Variable> variables = new HashMap<String, Variable>();
	private Scope parent;

	@Override
	public void addVariable(String name) {
		
		this.variables.put(name, null);
	}

	@Override
	public void addVariable(String name, Variable variable) {
		
		this.variables.put(name, variable);
	}

	@Override
	public void addVariable(Variable variable) {
		
		this.variables.put(variable.getName(), variable);
	}

	@Override
	public Variable getVariable(String name) {
		
		return this.variables.get(name);
	}

	@Override
	public Collection<Variable> getVariables() {
		
		return this.variables.values();
	}

	@Override
	public Variable lookupVariable(String name) {
		
		Variable variable = this.getVariable(name);
		if (variable == null && this.parent != null) {
			return this.parent.lookupVariable(name);
		} else {
			return variable;
		}
	}
	
	@Override
	public void removeVariable(String name) {
		
		this.variables.remove(name);
	}

	@Override
	public void setParent(Scope parent) {
		
		this.parent = parent;
	}

	@Override
	public void setVariable(String name, Variable variable) {
		
		this.variables.put(name, variable);
	}

	@Override
	public void setVariable(Variable variable) {
		
		this.variables.put(variable.getName(), variable);
	}
}

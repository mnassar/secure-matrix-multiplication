package org.unify_framework.abstract_syntax.data_perspective;

import java.util.Collection;

public interface Scope {
	
	public void addVariable(String name);

	public void addVariable(String name, Variable variable);
	
	public void addVariable(Variable variable);
	
	/**
	 * Gets the value of the given variable.
	 * 
	 * <p>This is not the same as variable lookup, in that this method only
	 * looks for the variable in the current scope, and not in its
	 * ancestors.</p>
	 * 
	 * @param name The name of the variable
	 */
	public Variable getVariable(String name);
	
	public Collection<Variable> getVariables();
	
	public Variable lookupVariable(String name);
	
	public void removeVariable(String name);
	
	public void setParent(Scope parent);
	
	public void setVariable(String name, Variable variable);
	
	public void setVariable(Variable variable);
}

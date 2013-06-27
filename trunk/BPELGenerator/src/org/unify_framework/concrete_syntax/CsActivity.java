package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.visitors.Element;

public abstract class CsActivity implements Element {
	
	private String name;
	
	public CsActivity() {
		
		// Do nothing
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
}
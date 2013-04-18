package org.unify_framework.dsls.parental_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class Child extends User {
	
	private User parent;
	
	public Child(String name) {
		
		super(name);
	}
	
	public User getParent() {
		
		return parent;
	}
	
	public void setParent(User parent) {
		
		this.parent = parent;
	}
}

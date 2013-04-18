package org.unify_framework.dsls;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public abstract class Concern {
	
	private String name;
	
	public Concern() {
		
	}
	
	public Concern(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
}

package org.unify_framework.dsls.access_control;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: User.java 17082 2011-09-01 14:31:14Z njonchee $
 */
public class User {
	
	private String name;
	private String password;
	private Set<String> userRoles = new HashSet<String>();
	
	public User(String name) {
		
		this.name = name;
	}
	
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	public void addUserRole(String userRole) {
		
		this.userRoles.add(userRole);
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getPassword() {
		
		return this.password;
	}
	
	public Set<String> getUserRoles() {
		
		return this.userRoles;
	}
}

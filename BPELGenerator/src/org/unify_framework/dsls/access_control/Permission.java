package org.unify_framework.dsls.access_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: Permission.java 16989 2011-08-25 09:13:47Z njonchee $
 */
public abstract class Permission {
	
	private String activity;
	
	public Permission(String activity) {
		
		this.activity = activity;
	}
	
	public String getActivity() {
		
		return this.activity;
	}
	
	public abstract PermissionEnum getPermissionEnum();
}

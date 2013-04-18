package org.unify_framework.dsls.access_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AllowPermission.java 16990 2011-08-25 10:28:40Z njonchee $
 */
public class AllowPermission extends Permission {
	
	public AllowPermission(String activity) {
		
		super(activity);
	}
	
	@Override
	public PermissionEnum getPermissionEnum() {
		
		return PermissionEnum.ALLOW;
	}
}

package org.unify_framework.dsls.access_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: DenyPermission.java 16990 2011-08-25 10:28:40Z njonchee $
 */
public class DenyPermission extends Permission {
	
	private ActionEnum action; 
	
	public DenyPermission(String activity) {
		
		super(activity);
	}
	
	public void setAction(String action) {
		
		if (action.equals("RaiseError")) {
			this.action = ActionEnum.RAISE_ERROR;
		} else if (action.equals("Skip")) {
			this.action = ActionEnum.SKIP;
		} else {
			throw new RuntimeException("Invalid action '" + action + "'");
		}
	}
	
	public ActionEnum getActionEnum() {
		
		return this.action;
	}
	
	@Override
	public PermissionEnum getPermissionEnum() {
		
		return PermissionEnum.DENY;
	}
}

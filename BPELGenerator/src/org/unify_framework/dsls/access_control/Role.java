package org.unify_framework.dsls.access_control;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: Role.java 17082 2011-09-01 14:31:14Z njonchee $
 */
public class Role {
	
	private String name;
	private Set<Permission> permissions = new HashSet<Permission>();
	
	public Role(String name) {
		
		this.name = name;
	}
	
	public void addPermission(Permission permission) {
		
		this.permissions.add(permission);
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public Set<DenyPermission> getDenyPermissions() {
		
		Set<DenyPermission> result = new HashSet<DenyPermission>();
		for (Permission permission : this.permissions) {
			if (permission.getPermissionEnum() == PermissionEnum.DENY) {
				result.add((DenyPermission) permission);
			}
		}
		return result;
	}
	
	public Set<Permission> getPermissions() {
		
		return permissions;
	}
}

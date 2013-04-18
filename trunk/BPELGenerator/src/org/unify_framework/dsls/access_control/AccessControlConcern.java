package org.unify_framework.dsls.access_control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.dsls.Concern;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AccessControlConcern.java 18543 2011-11-18 17:44:50Z njonchee $
 */
public class AccessControlConcern extends Concern {
	
	private PermissionEnum defaultPermission;
	private ActionEnum defaultAction;
	private Map<String, Role> roles = new HashMap<String, Role>();
	private Set<String> defaultUserRoles = new HashSet<String>();
	private Map<String, User> users = new HashMap<String, User>();
	private Expression usernameVariable;
	
	// CONSTRUCTORS ////////////////////////////////////////////////////////////
	
	public AccessControlConcern() {
		
		super();
	}
	
	public AccessControlConcern(String name) {
		
		super(name);
	}
	
	// ACCESSORS ///////////////////////////////////////////////////////////////
	
	public PermissionEnum getDefaultPermission() {
		
		return this.defaultPermission;
	}
	
	public ActionEnum getDefaultAction() {
		
		return this.defaultAction;
	}
	
	public Map<String, Map<String, String>> getActivityUsernamePermissionMapping() {
		
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		for (User user : this.users.values()) {
			for (String userRole : user.getUserRoles()) {
				Role role = this.roles.get(userRole);
				for (Permission p : role.getPermissions()) {
					String activityName = p.getActivity();
					if (!result.containsKey(activityName)) {
						result.put(activityName, new HashMap<String, String>());
					}
					Map<String, String> nestedMap = result.get(activityName);
					switch (p.getPermissionEnum()) {
						case ALLOW:
							nestedMap.put(user.getName(), "Allow");
							break;
						case DENY:
							switch (((DenyPermission) p).getActionEnum()) {
							case SKIP:
								nestedMap.put(user.getName(), "DenyBySkipping");
								break;
							case RAISE_ERROR:
								nestedMap.put(user.getName(), "DenyByRaisingError");
								break;
							}
							break;
					}
				}
			}
		}
		return result;
	}
	
	public Set<String> getDenyActivityNames() {
		
		Set<String> result = new HashSet<String>(); // A Set does not contain duplicates!
		for (Role role : this.roles.values()) {
			for (DenyPermission p : role.getDenyPermissions()) {
				result.add(p.getActivity());
			}
		}
		return result;
	}
	
	public Set<String> getDenyRaiseErrorActivityNames() {
		
		Set<String> result = new HashSet<String>(); // A Set does not contain duplicates!
		for (Role role : this.roles.values()) {
			for (DenyPermission p : role.getDenyPermissions()) {
				if (p.getActionEnum() == ActionEnum.RAISE_ERROR) {
					result.add(p.getActivity());
				}
			}
		}
		return result;
	}
	
	public Set<String> getDenySkipActivityNames() {
		
		Set<String> result = new HashSet<String>(); // A Set does not contain duplicates!
		for (Role role : this.roles.values()) {
			for (DenyPermission p : role.getDenyPermissions()) {
				if (p.getActionEnum() == ActionEnum.SKIP) {
					result.add(p.getActivity());
				}
			}
		}
		return result;
	}
	
	public Expression getUsernameVariableExpression() {
		
		return usernameVariable;
	}
	
	// MUTATORS ////////////////////////////////////////////////////////////////
	
	public void setDefaultPermission(String defaultPermission) {
		
		if (defaultPermission.equals("Allow")) {
			this.defaultPermission = PermissionEnum.ALLOW;
		} else if (defaultPermission.equals("Deny")) {
			this.defaultPermission = PermissionEnum.DENY;
		} else {
			throw new RuntimeException("Invalid default permission '" + defaultPermission + "'");
		}
	}
	
	public void setDefaultAction(String defaultAction) {
		
		if (defaultAction.equals("RaiseError")) {
			this.defaultAction = ActionEnum.RAISE_ERROR;
		} else if (defaultAction.equals("Skip")) {
			this.defaultAction = ActionEnum.SKIP;
		} else {
			throw new RuntimeException("Invalid default action '" + defaultAction + "'");
		}
	}
	
	public void addRole(Role role) {
		
		this.roles.put(role.getName(), role);
	}
	
	public void addDefaultUserRole(String userRole) {
		
		this.defaultUserRoles.add(userRole);
	}
	
	public void addUser(User user) {
		
		this.users.put(user.getName(), user);
	}
	
	public void setUsernameVariableExpression(Expression usernameVariable) {
		
		this.usernameVariable = usernameVariable;
	}
}

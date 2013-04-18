package org.unify_framework.dsls.parental_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class DenyUsagePolicy extends UsagePolicy {
	
	public DenyUsagePolicy(int age, String activityName) {
		
		super(age, activityName);
	}
	
	public PolicyEnum getPolicyEnum() {
		
		return PolicyEnum.DENY_USAGE;
	}
}

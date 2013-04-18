package org.unify_framework.dsls.parental_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public abstract class UsagePolicy extends Policy {
	
	public UsagePolicy(int age, String activityName) {
		
		super(age, activityName);
	}
}

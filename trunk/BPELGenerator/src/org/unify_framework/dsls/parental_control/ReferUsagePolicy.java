package org.unify_framework.dsls.parental_control;

import org.unify_framework.abstract_syntax.data_perspective.Expression;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ReferUsagePolicy.java 18556 2011-11-21 11:41:25Z njonchee $
 */
public class ReferUsagePolicy extends UsagePolicy {
	
	private Expression usernameVariable;
	
	public ReferUsagePolicy(int age, String activityName, Expression usernameVariable) {
		
		super(age, activityName);
		this.usernameVariable = usernameVariable;
	}
	
	public Expression getUsernameVariable() {
		
		return usernameVariable;
	}
	
	@Override
	public PolicyEnum getPolicyEnum() {
		
		return PolicyEnum.REFER_USAGE;
	}
}

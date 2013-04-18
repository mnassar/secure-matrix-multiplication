package org.unify_framework.dsls.parental_control;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public abstract class Policy {
	
	private int age;
	private String activityName;
	
	public Policy(int age, String activityName) {
		
		this.age = age;
		this.activityName = activityName;
	}
	
	public int getAge() {
		
		return age;
	}
	
	public String getActivityName() {
		
		return activityName;
	}
	
	public abstract PolicyEnum getPolicyEnum();
}

package org.unify_framework.dsls.parental_control;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class FilteringPolicy extends Policy {
	
	private String resultVariable;
	private Map<String, String> excludes = new HashMap<String, String>();
	
	public FilteringPolicy(int age, String activityName, String resultVariable) {
		
		super(age, activityName);
		this.resultVariable = resultVariable;
	}
	
	public String getResultVariable() {
		
		return resultVariable;
	}
	
	public void addExclude(String property, String value) {
		
		excludes.put(property, value);
	}
	
	@Override
	public PolicyEnum getPolicyEnum() {
		
		return PolicyEnum.FILTER;
	}
}

package org.unify_framework.dsls.parental_control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.dsls.Concern;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ParentalControlConcern.java 18537 2011-11-18 12:58:01Z njonchee $
 */
public class ParentalControlConcern extends Concern {
	
	private List<Policy> policies = new ArrayList<Policy>();
	private List<Child> children = new ArrayList<Child>();
	private Expression ageVariableExpression;
	
	// ACCESSORS ///////////////////////////////////////////////////////////////
	
	public Collection<Policy> getPolicies() {
		
		return policies;
	}
	
	public Map<String, String> getChildParentMapping() {
		
		Map<String, String> result = new HashMap<String, String>();
		for (Child child : this.children) {
			result.put(child.getName(), child.getParent().getName());
		}
		return result;
	}
	
	public Expression getAgeVariableExpression() {
		
		return ageVariableExpression;
	}
	
	// MUTATORS ////////////////////////////////////////////////////////////////
	
	public void addPolicy(Policy policy) {
		
		policies.add(policy);
	}
	
	public void addChild(Child child) {
		
		children.add(child);
	}
	
	public void setAgeVariableExpression(Expression ageVariableExpression) {
		
		this.ageVariableExpression = ageVariableExpression;
	}
}

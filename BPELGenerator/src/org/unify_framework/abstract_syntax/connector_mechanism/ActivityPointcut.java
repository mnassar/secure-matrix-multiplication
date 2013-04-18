package org.unify_framework.abstract_syntax.connector_mechanism;

public class ActivityPointcut extends ActivityOrRegionPointcut {
	
	private String expression;
	
	public ActivityPointcut(String expression) {
		
		this.expression = expression;
	}
	
	public String getExpression() {
		
		return this.expression;
	}
	
	@Override
	public String toString() {
		
		return "executingactivity(\"" + this.expression + "\")";
	}
}

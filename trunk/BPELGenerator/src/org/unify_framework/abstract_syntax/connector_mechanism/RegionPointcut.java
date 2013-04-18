package org.unify_framework.abstract_syntax.connector_mechanism;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class RegionPointcut extends ActivityOrRegionPointcut {

	private String expression;
	
	public RegionPointcut(String expression) {
		
		this.expression = expression;
	}
	
	public String getExpression() {
		
		return this.expression;
	}
	
	@Override
	public String toString() {
		
		return "executingregion(\"" + this.expression + "\")";
	}
}

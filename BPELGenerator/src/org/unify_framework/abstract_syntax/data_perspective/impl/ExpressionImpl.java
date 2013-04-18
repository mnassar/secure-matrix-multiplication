package org.unify_framework.abstract_syntax.data_perspective.impl;

import org.unify_framework.abstract_syntax.data_perspective.Expression;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public abstract class ExpressionImpl implements Expression {
	
	private String expression;
	
	public ExpressionImpl(String expression) {
		
		this.expression = expression;
	}
	
	public String getExpression() {
		
		return expression;
	}
}

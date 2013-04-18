package org.unify_framework.abstract_syntax.data_perspective;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface Expression {
	
	String getExpression();
	
	Expression getCopy();
	
	/**
	 * Returns a new expression that checks whether the current expression is
	 * smaller than the given constant.
	 */
	Expression smallerThanConstant(int constant);
}

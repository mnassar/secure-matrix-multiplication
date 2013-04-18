package org.unify_framework.abstract_syntax.data_perspective;

import org.unify_framework.abstract_syntax.AtomicActivity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface CopyExpressionToExpressionActivity extends AtomicActivity {

	public Expression getSourceExpression();
	public Expression getTargetExpression();
	public void setSourceExpression(Expression source);
	public void setTargetExpression(Expression target);
}

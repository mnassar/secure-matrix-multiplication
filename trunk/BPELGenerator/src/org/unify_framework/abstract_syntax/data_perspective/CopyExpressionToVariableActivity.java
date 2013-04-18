package org.unify_framework.abstract_syntax.data_perspective;

import org.unify_framework.abstract_syntax.AtomicActivity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface CopyExpressionToVariableActivity extends AtomicActivity {

	public Expression getSourceExpression();
	public Variable getTargetVariable();
	public void setSourceExpression(Expression source);
	public void setTargetVariable(Variable target);
}

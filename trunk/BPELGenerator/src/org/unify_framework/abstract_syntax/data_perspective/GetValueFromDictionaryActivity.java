package org.unify_framework.abstract_syntax.data_perspective;

import org.unify_framework.abstract_syntax.AtomicActivity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface GetValueFromDictionaryActivity extends AtomicActivity {
	
	public String getSourceVariableName();
	public String getKey();
	public Expression getTargetExpression();
	public void setSourceVariableName(String name);
	public void setKey(String key);
	public void setTargetExpression(Expression target);
}

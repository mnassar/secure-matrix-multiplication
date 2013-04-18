package org.unify_framework.abstract_syntax.data_perspective;

import org.unify_framework.abstract_syntax.AtomicActivity;

// TODO Create abstraction for services and implement InvokeServiceActivity in terms of that abstraction

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface InvokeMonitoringServiceActivity extends AtomicActivity {
	
	String getMessage();
	void setMessage(String message);
	Expression getUsernameVariable();
	void setUsernameVariable(Expression usernameVariable);
}

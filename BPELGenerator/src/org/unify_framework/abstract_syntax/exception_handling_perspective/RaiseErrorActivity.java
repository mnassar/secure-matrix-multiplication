package org.unify_framework.abstract_syntax.exception_handling_perspective;

import org.unify_framework.abstract_syntax.AtomicActivity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface RaiseErrorActivity extends AtomicActivity {
	
	String getErrorName();
	void setErrorName(String name);
}

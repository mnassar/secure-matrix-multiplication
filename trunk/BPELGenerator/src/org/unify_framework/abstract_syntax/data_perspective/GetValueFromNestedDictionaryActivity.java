package org.unify_framework.abstract_syntax.data_perspective;

import org.unify_framework.abstract_syntax.AtomicActivity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: GetValueFromNestedDictionaryActivity.java 18440 2011-11-08 11:54:15Z njonchee $
 */
public interface GetValueFromNestedDictionaryActivity extends AtomicActivity {
	
	public String getSourceVariableName();
	public String getFirstKey();
	public String getSecondKey();
	public String getTargetVariableName();
	public void setSourceVariableName(String name);
	public void setFirstKey(String key);
	public void setSecondKey(String key);
	public void setTargetVariableName(String name);
}

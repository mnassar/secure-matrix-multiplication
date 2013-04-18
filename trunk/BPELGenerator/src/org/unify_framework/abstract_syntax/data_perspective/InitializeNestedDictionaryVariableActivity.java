package org.unify_framework.abstract_syntax.data_perspective;

import java.util.Map;

import org.unify_framework.abstract_syntax.AtomicActivity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public interface InitializeNestedDictionaryVariableActivity extends AtomicActivity {
	
	public Map<String, Map<String, String>> getDictionary();
	public Variable getVariable();
	public void setDictionary(Map<String, Map<String, String>> dictionary);
	public void setVariable(Variable variable);
}

package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.HashMap;
import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.InitializeNestedDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class InitializeNestedDictionaryVariableActivityImpl extends AtomicActivityImpl implements InitializeNestedDictionaryVariableActivity {
	
	private Map<String, Map<String, String>> dictionary;
	private Variable variable;

	public InitializeNestedDictionaryVariableActivityImpl(String name) {
		
		super(name);
	}
	
	public Map<String, Map<String, String>> getDictionary() {
		
		return dictionary;
	}
	
	public Variable getVariable() {
		
		return this.variable;
	}
	
	@Override
	public void setDictionary(Map<String, Map<String, String>> dictionary) {
		
		this.dictionary = dictionary;
	}
	
	@Override
	public InitializeNestedDictionaryVariableActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		InitializeNestedDictionaryVariableActivityImpl copy = new InitializeNestedDictionaryVariableActivityImpl(getName());
		copy.setDictionary(this.getDictionaryCopy());
		copy.setVariable(this.variable.getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	protected Map<String, Map<String, String>> getDictionaryCopy() {
		
		Map<String, Map<String, String>> dictionaryCopy = new HashMap<String, Map<String, String>>();
		for (String activityName : this.dictionary.keySet()) {
			Map<String, String> nestedDictionary = this.dictionary.get(activityName);
			Map<String, String> nestedDictionaryCopy = new HashMap<String, String>();
			for (String username : nestedDictionary.keySet()) {
				nestedDictionaryCopy.put(username, nestedDictionary.get(username));
			}
			dictionaryCopy.put(activityName, nestedDictionaryCopy);
		}
		return dictionaryCopy;
	}

	@Override
	public void setVariable(Variable variable) {
		
		this.variable = variable;
	}
}

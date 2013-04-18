package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.HashMap;
import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.InitializeDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class InitializeDictionaryVariableActivityImpl extends AtomicActivityImpl implements InitializeDictionaryVariableActivity {
	
	private Map<String, String> dictionary;
	private Variable variable;

	public InitializeDictionaryVariableActivityImpl(String name) {
		
		super(name);
	}
	
	public Map<String, String> getDictionary() {
		
		return dictionary;
	}
	
	public Variable getVariable() {
		
		return this.variable;
	}
	
	@Override
	public void setDictionary(Map<String, String> dictionary) {
		
		this.dictionary = dictionary;
	}
	
	@Override
	public InitializeDictionaryVariableActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		InitializeDictionaryVariableActivityImpl copy = new InitializeDictionaryVariableActivityImpl(getName());
		copy.setDictionary(this.getDictionaryCopy());
		copy.setVariable(this.variable.getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	protected Map<String, String> getDictionaryCopy() {
		
		Map<String, String> dictionaryCopy = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : dictionary.entrySet()) {
			dictionaryCopy.put(entry.getKey(), entry.getValue());
		}
		return dictionaryCopy;
	}

	@Override
	public void setVariable(Variable variable) {
		
		this.variable = variable;
	}
}

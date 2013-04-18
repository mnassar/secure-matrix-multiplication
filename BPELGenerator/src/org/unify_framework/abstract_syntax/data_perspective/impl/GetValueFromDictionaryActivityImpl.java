package org.unify_framework.abstract_syntax.data_perspective.impl;

import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromDictionaryActivity;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class GetValueFromDictionaryActivityImpl extends AtomicActivityImpl implements GetValueFromDictionaryActivity {
	
	private String sourceVariableName;
	private String key;
	private Expression target;
	
	public GetValueFromDictionaryActivityImpl(String name) {
		
		super(name);
	}
	
	@Override
	public String getSourceVariableName() {
		
		return sourceVariableName;
	}
	
	@Override
	public String getKey() {
		
		return key;
	}
	
	@Override
	public Expression getTargetExpression() {
		
		return target;
	}
	
	@Override
	public void setSourceVariableName(String name) {
		
		this.sourceVariableName = name;
	}
	
	@Override
	public void setKey(String key) {
		
		this.key = key;
	}
	
	@Override
	public void setTargetExpression(Expression target) {
		
		this.target = target;
	}
}

package org.unify_framework.abstract_syntax.data_perspective.impl;

import org.unify_framework.abstract_syntax.data_perspective.GetValueFromNestedDictionaryActivity;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: GetValueFromNestedDictionaryActivityImpl.java 18440 2011-11-08 11:54:15Z njonchee $
 */
public class GetValueFromNestedDictionaryActivityImpl extends AtomicActivityImpl implements GetValueFromNestedDictionaryActivity {
	
	private String sourceVariableName;
	private String firstKey;
	private String secondKey;
	private String targetVariable;
	
	public GetValueFromNestedDictionaryActivityImpl(String name) {
		
		super(name);
	}
	
	@Override
	public String getSourceVariableName() {
		
		return sourceVariableName;
	}
	
	@Override
	public String getFirstKey() {
		
		return firstKey;
	}
	
	@Override
	public String getSecondKey() {
		
		return secondKey;
	}
	
	@Override
	public String getTargetVariableName() {
		
		return targetVariable;
	}
	
	@Override
	public void setSourceVariableName(String name) {
		
		this.sourceVariableName = name;
	}
	
	@Override
	public void setFirstKey(String key) {
		
		this.firstKey = key;
	}
	
	@Override
	public void setSecondKey(String key) {
		
		this.secondKey = key;
	}
	
	@Override
	public void setTargetVariableName(String name) {
		
		this.targetVariable = name;
	}
}

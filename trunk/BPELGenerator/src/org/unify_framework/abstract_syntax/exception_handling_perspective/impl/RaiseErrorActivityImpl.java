package org.unify_framework.abstract_syntax.exception_handling_perspective.impl;

import org.unify_framework.abstract_syntax.exception_handling_perspective.RaiseErrorActivity;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class RaiseErrorActivityImpl extends AtomicActivityImpl implements RaiseErrorActivity {
	
	private String errorName;
	
	public RaiseErrorActivityImpl(String name) {
		
		super(name);
	}
	
	@Override
	public String getErrorName() {
		
		return errorName;
	}
	
	@Override
	public void setErrorName(String name) {
		
		this.errorName = name;
	}
}

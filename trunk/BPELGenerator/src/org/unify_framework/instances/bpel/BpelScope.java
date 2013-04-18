package org.unify_framework.instances.bpel;

import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.data_perspective.impl.ScopeImpl;

public class BpelScope extends ScopeImpl {
	
	/**
	 * Creates a (deep) copy of the BPEL scope.
	 * 
	 * <p>Callers are responsible for setting the copy's parent.</p>
	 */
	public BpelScope getCopy() {
		
		BpelScope copy = new BpelScope();
		for (Variable variable : getVariables()) {
			BpelVariable bpelVariable = (BpelVariable) variable;
			copy.addVariable(bpelVariable.getCopy());
		}
		return copy;
	}
}

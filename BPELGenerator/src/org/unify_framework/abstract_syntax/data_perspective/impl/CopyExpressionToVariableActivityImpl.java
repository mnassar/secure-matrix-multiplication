package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class CopyExpressionToVariableActivityImpl extends AtomicActivityImpl implements CopyExpressionToVariableActivity {
	
	private Expression source;
	private Variable target;
	
	// CONSTRUCTORS ////////////////////////////////////////////////////////////
	
	public CopyExpressionToVariableActivityImpl(String name) {
		
		super(name);
	}
	
	// ACCESSORS ///////////////////////////////////////////////////////////////
	
	@Override
	public Expression getSourceExpression() {
		
		return source;
	}

	@Override
	public Variable getTargetVariable() {
		
		return target;
	}
	
	// MUTATORS ////////////////////////////////////////////////////////////////
	
	@Override
	public void setSourceExpression(Expression source) {
		
		this.source = source;
	}
	
	@Override
	public void setTargetVariable(Variable target) {
		
		this.target = target;
	}
	
	// OPERATIONS //////////////////////////////////////////////////////////////
	
	@Override
	public CopyExpressionToVariableActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		CopyExpressionToVariableActivityImpl copy = new CopyExpressionToVariableActivityImpl(getName());
		copy.setSourceExpression(this.source.getCopy());
		copy.setTargetVariable(this.target.getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

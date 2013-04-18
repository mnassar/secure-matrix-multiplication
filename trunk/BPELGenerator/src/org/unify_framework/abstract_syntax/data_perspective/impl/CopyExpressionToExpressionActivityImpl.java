package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToExpressionActivity;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class CopyExpressionToExpressionActivityImpl extends AtomicActivityImpl implements CopyExpressionToExpressionActivity {
	
	private Expression source;
	private Expression target;
	
	// CONSTRUCTORS ////////////////////////////////////////////////////////////
	
	public CopyExpressionToExpressionActivityImpl(String name) {
		
		super(name);
	}
	
	// ACCESSORS ///////////////////////////////////////////////////////////////
	
	@Override
	public Expression getSourceExpression() {
		
		return source;
	}

	@Override
	public Expression getTargetExpression() {
		
		return target;
	}
	
	// MUTATORS ////////////////////////////////////////////////////////////////
	
	@Override
	public void setSourceExpression(Expression source) {
		
		this.source = source;
	}
	
	@Override
	public void setTargetExpression(Expression target) {
		
		this.target = target;
	}
	
	// OPERATIONS //////////////////////////////////////////////////////////////
	
	@Override
	public CopyExpressionToExpressionActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		CopyExpressionToExpressionActivityImpl copy = new CopyExpressionToExpressionActivityImpl(getName());
		copy.setSourceExpression(this.source.getCopy());
		copy.setTargetExpression(this.target.getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

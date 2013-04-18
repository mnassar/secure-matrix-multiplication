package org.unify_framework.abstract_syntax;

import org.unify_framework.abstract_syntax.data_perspective.Expression;

/**
 * @author Niels Joncheere
 * 
 * @model
 */
public interface XorSplit extends Split {
	
	public void addGuard(ControlOutputPort controlOutputPort, Expression guard);
	
	public XorJoin getCorrespondingXorJoin();
	
	public Transition getDefaultOutgoingTransition();
	
	public Expression getGuard(ControlOutputPort controlOutputPort);
	
	public void setCorrespondingXorJoin(XorJoin correspondingXorJoin);
}

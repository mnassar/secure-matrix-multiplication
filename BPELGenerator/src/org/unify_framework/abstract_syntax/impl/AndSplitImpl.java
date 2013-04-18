package org.unify_framework.abstract_syntax.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AndSplitImpl.java 18296 2011-10-25 12:57:29Z njonchee $
 */
public class AndSplitImpl extends SplitImpl implements AndSplit {
	
	/* PRIVATE FIELDS *********************************************************/

	private AndJoin correspondingAndJoin;
	private AndJoin unstructuredCorrespondingAndJoin;
	
	/* CONSTRUCTORS ***********************************************************/
	
	public AndSplitImpl(String name) {
		
		this(name, false);
	}
	
	public AndSplitImpl(String name, boolean woven) {
		
		super(name, woven);
	}
	
	/**
	 * Creates a new <code>AndSplitImpl</code> object, with the specified
	 * <code>AndJoinImpl</code> object as its corresponding AND-join.
	 * 
	 * @deprecated In order to avoid chicken/egg problems, create splits and
	 *		joins separately and set the 'corresponding' fields afterwards.
	 */
	@Deprecated
	public AndSplitImpl(String name, boolean woven, AndJoinImpl correspondingAndJoin) {
		
		this(name, woven);
		this.correspondingAndJoin = correspondingAndJoin;
		correspondingAndJoin.setCorrespondingAndSplit(this);
	}
	
	/* GETTERS ****************************************************************/
	
	@Override
	public AndJoin getCorrespondingAndJoin() {
		
		return this.correspondingAndJoin;
	}
	
	@Override
	public AndJoin getUnstructuredCorrespondingAndJoin() {
		
		return this.unstructuredCorrespondingAndJoin;
	}
	
	/* SETTERS ****************************************************************/

	@Override
	public void setCorrespondingAndJoin(AndJoin andJoin){
		
		this.correspondingAndJoin = andJoin;
	}
	
	@Override
	public void setUnstructuredCorrespondingAndJoin(AndJoin andJoin){
		
		this.unstructuredCorrespondingAndJoin = andJoin;
	}
	
	/* PUBLIC METHODS *********************************************************/
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public AndSplitImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		AndSplitImpl copy = new AndSplitImpl(getName(), getWoven());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		for (ControlOutputPort controlOutputPort : getControlOutputPorts()) {
			mapping.put(controlOutputPort, copy.getNewControlOutputPort());
		}
		return copy;
	}
}

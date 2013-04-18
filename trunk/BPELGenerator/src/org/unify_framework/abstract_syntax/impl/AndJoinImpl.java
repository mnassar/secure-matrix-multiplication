package org.unify_framework.abstract_syntax.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AndJoinImpl.java 18296 2011-10-25 12:57:29Z njonchee $
 */
public class AndJoinImpl extends JoinImpl implements AndJoin {
	
	/* PRIVATE FIELDS *********************************************************/

	private AndSplit correspondingAndSplit;
	private AndSplit unstructuredCorrespondingAndSplit;
	
	/* CONSTRUCTORS ***********************************************************/
	
	public AndJoinImpl(String name) {
		
		this(name, false);
	}
	
	public AndJoinImpl(String name, boolean woven) {
		
		super(name, woven);
	}
	
	/**
	 * Creates a new <code>AndJoinImpl</code> object, with the specified
	 * <code>AndSplitImpl</code> object as its corresponding AND-split.
	 * 
	 * @deprecated In order to avoid chicken/egg problems, create splits and
	 *		joins separately and set the 'corresponding' fields afterwards.</p>
	 */
	public AndJoinImpl(String name, boolean woven, AndSplitImpl correspondingAndSplit) {
		
		this(name, woven);
		this.correspondingAndSplit = correspondingAndSplit;
		correspondingAndSplit.setCorrespondingAndJoin(this);
	}
	
	/* GETTERS ****************************************************************/
	
	@Override
	public AndSplit getCorrespondingAndSplit() {
		
		return this.correspondingAndSplit;
	}
	
	@Override
	public AndSplit getUnstructuredCorrespondingAndSplit() {
		
		return this.unstructuredCorrespondingAndSplit;
	}
	
	/* SETTERS ****************************************************************/
	
	@Override
	public void setCorrespondingAndSplit(AndSplit andSplit) {
		
		this.correspondingAndSplit = andSplit;
	}
	
	@Override
	public void setUnstructuredCorrespondingAndSplit(AndSplit andSplit) {
		
		this.unstructuredCorrespondingAndSplit = andSplit;
	}
	
	/* PUBLIC METHODS *********************************************************/
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public AndJoinImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		AndJoinImpl copy = new AndJoinImpl(getName(), getWoven());
		for (ControlInputPort controlInputPort : getControlInputPorts()) {
			mapping.put(controlInputPort, copy.getNewControlInputPort());
		}
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

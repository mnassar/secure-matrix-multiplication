package org.unify_framework.abstract_syntax.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

import java.util.Map;

public class XorJoinImpl extends JoinImpl implements XorJoin {
	
	private static final Log log = LogFactory.getLog(XorJoinImpl.class);
	
	/* PRIVATE FIELDS *********************************************************/
	
	private XorSplit correspondingXorSplit;
	
	/* CONSTRUCTORS ***********************************************************/
	
	public XorJoinImpl(String name) {
		
		this(name, false);
	}
	
	public XorJoinImpl(String name, boolean woven) {
		
		super(name, woven);
	}
	
	/* GETTERS ****************************************************************/
	
	@Override
	public XorSplit getCorrespondingXorSplit() {
		
		if (this.correspondingXorSplit == null) {
			log.error("XOR-join '" + this.getQualifiedName() + "' does not have a correspinfing split!");
			throw new RuntimeException("This join does not have a corresponding split!");
		} else {
			return this.correspondingXorSplit;
		}
	}
	
	/* SETTERS ****************************************************************/

	@Override
	public void setCorrespondingXorSplit(XorSplit correspondingXorSplit){
		
		this.correspondingXorSplit = correspondingXorSplit;
	}
	
	/* PUBLIC METHODS *********************************************************/
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public XorJoinImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		XorJoinImpl copy = new XorJoinImpl(getName(), getWoven());
		for (ControlInputPort controlInputPort : getControlInputPorts()) {
			mapping.put(controlInputPort, copy.getNewControlInputPort());
		}
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

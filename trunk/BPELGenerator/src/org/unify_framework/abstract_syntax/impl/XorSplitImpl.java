package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XorSplitImpl extends SplitImpl implements XorSplit {
	
	private static final Log log = LogFactory.getLog(XorSplitImpl.class);
	
	/* PRIVATE FIELDS *********************************************************/
	
	private XorJoin correspondingXorJoin;
	private Map<ControlOutputPort, Expression> guards = new HashMap<ControlOutputPort, Expression>();
	
	/* CONSTRUCTORS ***********************************************************/
	
	public XorSplitImpl(String name) {
		
		this(name, false);
	}
	
	public XorSplitImpl(String name, boolean woven) {
		
		super(name, woven);
	}
	
	/* GETTERS ****************************************************************/
	
	@Override
	public XorJoin getCorrespondingXorJoin() {
		
		if (this.correspondingXorJoin == null) {
			throw new RuntimeException("The XOR-split '" + this.getName() + "' does not have a corresponding XOR-join!");
		} else {
			return this.correspondingXorJoin;
		}
	}
	
	/* SETTERS ****************************************************************/

	@Override
	public void setCorrespondingXorJoin(XorJoin correspondingXorJoin) {
		
		this.correspondingXorJoin = correspondingXorJoin;
	}
	
	/* PUBLIC METHODS *********************************************************/
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public void addGuard(ControlOutputPort controlOutputPort, Expression guard) {
		
		log.debug("Adding guard '" + guard + "' to control output port...");
		this.guards.put(controlOutputPort, guard);
	}
	
	@Override
	public XorSplitImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		XorSplitImpl copy = new XorSplitImpl(getName(), getWoven());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		for (ControlOutputPort controlOutputPort : getControlOutputPorts()) {
			mapping.put(controlOutputPort, copy.getNewControlOutputPort());
		}
		return copy;
	}
	
	@Override
	public Transition getDefaultOutgoingTransition() {
		
		Transition defaultOutgoingTransition = null;
		for (Transition transition : getOutgoingTransitions()) {
			if (getGuard(transition.getSource()) == null) {
				if (defaultOutgoingTransition == null) {
					defaultOutgoingTransition = transition;
				} else {
					throw new RuntimeException("This XOR-split has more than one default outgoing transition");
				}
			}
		}
		if (defaultOutgoingTransition != null) {
			return defaultOutgoingTransition;
		} else {
			throw new RuntimeException("This XOR-split has no default outgoing transition");
		}
	}
	
	@Override
	public Expression getGuard(ControlOutputPort controlOutputPort) {
		
		return this.guards.get(controlOutputPort);
	}
	
	public Map<ControlOutputPort, Expression> getGuards() {
		
		return guards;
	}
}

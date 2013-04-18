package org.unify_framework.abstract_syntax.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.unify_framework.abstract_syntax.ExecutionStatus;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Transition;

public abstract class NodeImpl implements Node {
	
	private String name;
	private CompositeActivity parent;
	private boolean woven;
	private ExecutionStatus status;
	private List<ControlInputPort> controlInputPorts;
	private List<ControlOutputPort> controlOutputPorts;
	
	public NodeImpl(String name) {
		
		this(name, null, false);
	}
	
	public NodeImpl(String name, boolean woven) {
		
		this(name, null, woven);
	}
	
	public NodeImpl(String name, CompositeActivity parent) {
		
		this(name, parent, false);
	}
	
	public NodeImpl(String name, CompositeActivity parent, boolean woven) {
		
		this.name = name;
		this.parent = parent;
		this.woven = woven;
		this.status = ExecutionStatus.NOT_YET_EXECUTED;
		this.controlInputPorts = new LinkedList<ControlInputPort>();
		this.controlOutputPorts = new LinkedList<ControlOutputPort>();
	}

	@Override
	public List<ControlInputPort> getControlInputPorts() {
		
		return this.controlInputPorts;
	}

	@Override
	public List<ControlOutputPort> getControlOutputPorts() {
		
		return this.controlOutputPorts;
	}

	@Override
	public List<Transition> getIncomingTransitions() {
		
		List<Transition> incomingTransitions = new LinkedList<Transition>();
		for (ControlInputPort controlInputPort : this.controlInputPorts) {
			Transition incomingTransition = controlInputPort.getIncomingTransition();
			if (incomingTransition != null) {
				incomingTransitions.add(incomingTransition);
			}
		}
		return incomingTransitions;
	}

	@Override
	public List<Transition> getOutgoingTransitions() {
		
		List<Transition> outgoingTransitions = new LinkedList<Transition>();
		for (ControlOutputPort controlOutputPort : this.controlOutputPorts) {
			Transition outgoingTransition = controlOutputPort.getOutgoingTransition();
			if (outgoingTransition != null) {
				outgoingTransitions.add(outgoingTransition);
			}
		}
		return outgoingTransitions;
	}

	@Override
	public abstract Node getCopy(Map<ControlPort, ControlPort> mapping);

	@Override
	public ExecutionStatus getExecutionStatus() {
		
		return this.status;
	}
	
	@Override
	public String getName() {
		
		return this.name;
	}

	@Override
	public ControlInputPortImpl getNewControlInputPort() {
		
		ControlInputPortImpl newControlInputPort = new ControlInputPortImpl("ControlIn", this);
		getControlInputPorts().add(newControlInputPort);
		return newControlInputPort;
	}

	@Override
	public ControlOutputPortImpl getNewControlOutputPort() {
		
		ControlOutputPortImpl newControlOutputPort = new ControlOutputPortImpl("ControlOut", this);
		getControlOutputPorts().add(newControlOutputPort);
		return newControlOutputPort;
	}

	@Override
	public CompositeActivity getParent() {
		
		return this.parent;
	}

	@Override
	public String getQualifiedName() {
		
		if (this.parent == null) {
			return this.name;
		} else {
			return this.parent.getQualifiedName() + "." + this.name;
		}
	}

	@Override
	public boolean getWoven() {
		
		return this.woven;
	}

	@Override
	public void setExecutionStatus(ExecutionStatus status) {
		
		this.status = status;
	}

	@Override
	public void setName(String name) {
		
		this.name = name;
	}

	@Override
	public void setParent(CompositeActivity parent) {
		
		this.parent = parent;
	}

	@Override
	public void setWoven(boolean woven) {
		
		this.woven = woven;
	}
}

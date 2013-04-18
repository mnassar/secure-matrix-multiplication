package org.unify_framework.abstract_syntax;

import java.util.List;
import java.util.Map;

import org.unify_framework.abstract_syntax.visitors.Element;

/**
 * @author Niels Joncheere
 * 
 * @model abstract="true"
 */
public interface Node extends Element {
	
	public List<ControlInputPort> getControlInputPorts();
	public List<ControlOutputPort> getControlOutputPorts();

	public Node getCopy(Map<ControlPort, ControlPort> mapping);
	
	public ExecutionStatus getExecutionStatus();

	/**
	 * @model opposite="destinationNode"
	 */
	public List<Transition> getIncomingTransitions();
	
	/**
	 * @model
	 */
	public String getName();

	public ControlInputPort getNewControlInputPort();
	public ControlOutputPort getNewControlOutputPort();
	
	/**
	 * @model opposite="sourceNode"
	 */
	public List<Transition> getOutgoingTransitions();
	
	/**
	 * @model opposite="children"
	 */
	public CompositeActivity getParent();

	public String getQualifiedName();
	
	public boolean getWoven();

	public void setExecutionStatus(ExecutionStatus executionStatus);
	
	public void setName(String name);
	
	public void setParent(CompositeActivity parent);
	
	public void setWoven(boolean woven);
}

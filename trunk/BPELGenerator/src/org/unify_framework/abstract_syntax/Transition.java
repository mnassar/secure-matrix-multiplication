package org.unify_framework.abstract_syntax;

import java.util.Map;

import org.unify_framework.abstract_syntax.visitors.Element;

/**
 * @author Niels Joncheere
 * 
 * @model
 */
public interface Transition extends Element {
	
	public ControlOutputPort getSource();

	/**
	 * @model opposite="outgoingTransitions"
	 */
	public Node getSourceNode();
	
	public void replaceSource(ControlOutputPort oldSource, ControlOutputPort newSource);
	
	public ControlInputPort getDestination();

	/**
	 * @model opposite="incomingTransitions"
	 */
	public Node getDestinationNode();
	
	public void replaceDestination(ControlInputPort oldDestination, ControlInputPort newDestination);
	
	public Transition getCopy(Map<ControlPort, ControlPort> mapping);
}

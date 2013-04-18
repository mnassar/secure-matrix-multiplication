package org.unify_framework.abstract_syntax;

import java.util.List;

/**
 * @author Niels Joncheere
 * 
 * @model abstract="true"
 */
public interface Split extends ControlNode {
	
	public ControlInputPort getControlInputPort();
	
	public List<Node> getDestinationNodes();
	
	public Transition getIncomingTransition();
	
	public Node getSourceNode();
}

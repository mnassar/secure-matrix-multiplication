package org.unify_framework.abstract_syntax;

import java.util.List;

/**
 * @author Niels Joncheere
 * 
 * @model abstract="true"
 */
public interface Join extends ControlNode {
	
	public ControlOutputPort getControlOutputPort();
	
	public Node getDestinationNode();
	
	public Transition getOutgoingTransition();

	public List<Node> getSourceNodes();
}

package org.unify_framework.abstract_syntax;

/**
 * Represents the point where control enters a {@link Node} object.
 * 
 * @author Niels Joncheere
 */
public interface ControlInputPort extends ControlPort {
	
	/**
	 * @return The transition that is connected to the control input port, if
	 * any (<code>null</code> otherwise)
	 */
	public Transition getIncomingTransition();
	
	/**
	 * Sets the transition that is connected to the control input port.
	 * 
	 * @param incomingTransition
	 */
	public void setIncomingTransition(Transition incomingTransition);
}

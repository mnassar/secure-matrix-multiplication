package org.unify_framework.abstract_syntax;

/**
 * Represents the point where control exits a {@link Node} object.
 * 
 * @author Niels Joncheere
 */
public interface ControlOutputPort extends ControlPort {
	
	/**
	 * @return The transition that is connected to the control output port, if
	 * any (<code>null</code> otherwise)
	 */
	public Transition getOutgoingTransition();
	
	/**
	 * Sets the transition that is connected to the control ouput port.
	 * 
	 * @param outgoingTransition
	 */
	public void setOutgoingTransition(Transition outgoingTransition);
}

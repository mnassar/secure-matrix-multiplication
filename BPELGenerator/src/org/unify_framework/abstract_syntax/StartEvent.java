package org.unify_framework.abstract_syntax;

/**
 * Represents the point where a {@link CompositeActivity} object's control flow
 * starts.
 * 
 * <p>A start event has no {@link ControlInputPort} object and one
 * {@link ControlOutputPort} object.</p>
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: StartEvent.java 12947 2010-08-11 13:31:10Z njonchee $
 * 
 * @model
 */
public interface StartEvent extends Event {
	
	/**
	 * @return The start event's control output port
	 */
	public ControlOutputPort getControlOutputPort();
	
	/**
	 * @return The transition that is connected to the start event's control
	 * output port, if any (<code>null</code> otherwise)
	 */
	public Transition getOutgoingTransition();
}

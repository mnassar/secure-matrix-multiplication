package org.unify_framework.abstract_syntax;

/**
 * Represents the point where a {@link CompositeActivity} object's control flow
 * ends.
 * 
 * <p>An end event has one {@link ControlInputPort} object and no
 * {@link ControlOutputPort} object.</p>
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: EndEvent.java 12947 2010-08-11 13:31:10Z njonchee $
 * 
 * @model
 */
public interface EndEvent extends Event {
	
	/**
	 * @return The end event's control input port
	 */
	public ControlInputPort getControlInputPort();

	/**
	 * @return The transition that is connected to the end event's control
	 * input port, if any (<code>null</code> otherwise)
	 */
	public Transition getIncomingTransition();
}

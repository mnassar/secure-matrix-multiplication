package org.unify_framework.abstract_syntax;

/**
 * Represents a unit of work.
 * 
 * <p>An activity is either a {@link CompositeActivity} object or a
 * {@link AtomicActivity} object.  It has one {@link ControlInputPort} object
 * and one {@link ControlOutputPort} object.</p>
 * 
 * @author Niels Joncheere
 * 
 * @model abstract="true"
 */
public interface Activity extends Node {
	
	/**
	 * @return The activity's control input port
	 */
	public ControlInputPort getControlInputPort();
	
	/**
	 * @return The activity's control output port
	 */
	public ControlOutputPort getControlOutputPort();
	
	/**
	 * @return The transition that is connected to the activity's control input
	 * port, if any (<code>null</code> otherwise)
	 */
	public Transition getIncomingTransition();
	
	/**
	 * @return The transition that is connected to the activity's control output
	 * port, if any (<code>null</code> otherwise)
	 */
	public Transition getOutgoingTransition();
	
	/**
	 * @return A (deep) copy of the activity
	 */
	public Activity getCopy();
	
	public void replaceString(String target, String replacement);
}

package org.unify_framework.abstract_syntax;

import org.unify_framework.abstract_syntax.visitors.Element;

/**
 * Represents the point where control enters or exits a {@link Node} object.
 * 
 * <p>A control port is either a {@link ControlInputPort} object or a
 * {@link ControlOutputPort} object.  It <i>may<i> have a condition.</p>
 * 
 * @author Niels Joncheere
 */
public interface ControlPort extends Element {
	
	/**
	 * @return The parent node
	 */
	public Node getParent();
	
	/**
	 * @return The control port's name
	 */
	public String getName();
	
	/**
	 * @return The control port's condition, if any (<code>null</code>
	 * otherwise)
	 */
	public String getCondition();
	
	/**
	 * @return The control port's qualified name
	 */
	public String getQualifiedName();
}

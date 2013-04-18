package org.unify_framework.abstract_syntax;

/**
 * Allows routing a composite activity's control flow.
 * 
 * <p>A control node is either a {@link Split} object or a {@link Join}
 * object.</p>
 * 
 * @author Niels Joncheere
 * 
 * @model abstract="true"
 */
public interface ControlNode extends Node {
	
	// A control node does not have any general behavior
}

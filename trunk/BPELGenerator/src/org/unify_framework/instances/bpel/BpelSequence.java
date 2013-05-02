package org.unify_framework.instances.bpel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * Represents a BPEL sequence in the Unify framework.
 * 
 * @author <a href="faridsabry@gmail.com">Farida Sabry</a>
 * 
 */

public class BpelSequence extends BpelCompositeActivity {
	
	private static final Log log = LogFactory.getLog(BpelProcess.class);
	
	/* PRIVATE FIELDS *********************************************************/
	
	private String name;

	
	/* CONSTRUCTORS ***********************************************************/
	
	/**
	 * Creates a new BPEL sequence.
	 * 
	 * @param name The process's name
	 * @param targetNamespace The process's target namespace
	 */
	public BpelSequence(String name) {
		
		super(name);
		
	}
	
	
	/* SETTERS ****************************************************************/
	
	/* PUBLIC METHODS *********************************************************/

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
}

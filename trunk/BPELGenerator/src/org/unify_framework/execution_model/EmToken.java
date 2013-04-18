package org.unify_framework.execution_model;

import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a token.
 * 
 * A token has a reference to its place, and a place has a reference to its
 * tokens.  The place is responsible for updating these references.
 * 
 * @author Niels Joncheere
 */
public class EmToken {
	
	private static final Log log = LogFactory.getLog(EmToken.class);
	
	private EmPetriNet petriNet;
	private EmPlace place;
	private boolean isWaiting;
	
	public EmToken(EmPetriNet petriNet) {
		
		this.petriNet = petriNet;
	}
	
	protected EmPetriNet getPetriNet() {
		
		return this.petriNet;
	}
	
	protected EmPlace getPlace() {
		
		return this.place;
	}
	
	protected boolean isAlive() {
		
		if (this.place == null || this.place instanceof EmEndPlace) {
			return false;
		}
		return true;
	}
	
	protected boolean isWaiting() {
		
		return this.isWaiting;
	}
	
	protected void setPlace(EmPlace place) {
		
		this.place = place;
	}
	
	public void setWaiting(boolean isWaiting) {
		
		this.isWaiting = isWaiting;
	}
	
	protected boolean signal() {
		
		// Get the outgoing transition:
		EmTransition outgoingTransition = this.place.getOutgoingTransition();
		if (outgoingTransition == null) {
			log.debug("Place '" + this.place.getName() + "' has no outgoing transition...");
			return false;
		}
		// Check whether it is enabled:
		boolean isEnabled = outgoingTransition.isEnabled();
		if (isEnabled) {
			// If so, fire it:
			outgoingTransition.fire(this);
			// Check whether it is an activity transition:
			if (outgoingTransition instanceof EmActivityTransition) {
				// If so, regenerate the Graphviz file:
				try {
					this.petriNet.getActivity().generateGraphviz(new FileOutputStream("./build/Execution.dot"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		return false;
	}
}

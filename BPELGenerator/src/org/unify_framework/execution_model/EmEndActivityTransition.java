package org.unify_framework.execution_model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.ExecutionStatus;

public class EmEndActivityTransition extends EmActivityTransition {
	
	private static final Log log = LogFactory.getLog(EmEndActivityTransition.class);
	
	public EmEndActivityTransition(String name, AtomicActivity activity) {
		
		super(name, activity);
	}
	
	@Override
	public void fire(EmToken token) {
		
		log.debug("Firing end activity transition " + getName());

		getActivity().setExecutionStatus(ExecutionStatus.EXECUTED);
		
		// Remove the token from the input place:
		EmPlace inputPlace = getInputPlace();
		inputPlace.removeToken(token);
		
		// Indicate that the token is no longer waiting for the end of the activity's execution:
		token.setWaiting(false);
		
		// Add the token to the output place:
		EmPlace outputPlace = getOutputPlace();
		outputPlace.addToken(token);
	}
	
	@Override
	public boolean isEnabled() {
		
		for (EmToken token : getInputPlace().getTokens()) {
			if (!token.isWaiting()) {
				return true;
			}
		}
		return false;
	}
}

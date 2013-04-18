package org.unify_framework.execution_model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.ExecutionStatus;
import org.unify_framework.execution_model.manager.WorkItem;

public class EmStartActivityTransition extends EmActivityTransition {
	
	private static final Log log = LogFactory.getLog(EmStartActivityTransition.class);
	
	public EmStartActivityTransition(String name, AtomicActivity activity) {
		
		super(name, activity);
	}
	
	@Override
	public void fire(EmToken token) {
		
		log.debug("Firing start activity transition " + getName());
		
		getActivity().setExecutionStatus(ExecutionStatus.EXECUTING);
		
		// Remove the token from the input place:
		EmPlace inputPlace = getInputPlace();
		inputPlace.removeToken(token);
		
		// Indicate that the token is waiting for the end of the activity's execution:
		token.setWaiting(true);
		
		// Add the token to the output place:
		EmPlace outputPlace = getOutputPlace();
		outputPlace.addToken(token);
		
		// Create a work item and add it to the Petri net's execution manager:
		getParent().getExecutionManager().addWorkItem(new WorkItem(getActivity().getName(), token));
	}
}

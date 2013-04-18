package org.unify_framework.execution_model;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.execution_model.visitors.ElementVisitor;

public class EmAndSplitTransition extends EmSplitTransition {
	
	private static final Log log = LogFactory.getLog(EmAndSplitTransition.class);
	
	private AndSplit andSplit;
	
	public EmAndSplitTransition(String name, AndSplit split) {
		
		super(name);
		this.andSplit = split;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public void addInputPlace(EmPlace place) {
		
		if (getInputPlaces().size() > 0) {
			throw new RuntimeException("This activity transition already has an input place");
		}
		super.addInputPlace(place);
	}
	
	@Override
	public void fire(EmToken token) {
		
		log.debug("Firing AND-split transition");
		Iterator<EmPlace> iterator = getOutputPlaces().iterator();
		iterator.next().addToken(token);
		while (iterator.hasNext()) {
			EmToken newToken = new EmToken(token.getPetriNet());
			iterator.next().addToken(newToken);
			getParent().addToken(newToken);
		}
	}
	
	public AndSplit getAndSplit() {
		
		return this.andSplit;
	}
	
	public EmPlace getInputPlace() {
		
		Iterator<EmPlace> iterator = getInputPlaces().iterator();
		EmPlace result = iterator.next();
		if (iterator.hasNext()) {
			throw new RuntimeException("An activity transition should only have one input place");
		}
		return result;
	}
	
	@Override
	public boolean isEnabled() {
		
		return getInputPlace().hasToken();
	}
	
	@Override
	public String toString() {
		
		return "AND-split transition";
	}
}

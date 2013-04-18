package org.unify_framework.execution_model;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.execution_model.visitors.ElementVisitor;

public class EmAndJoinTransition extends EmJoinTransition {
	
	private static final Log log = LogFactory.getLog(EmAndJoinTransition.class);
	
	private AndJoin andJoin;
	
	public EmAndJoinTransition(String name, AndJoin join) {
		
		super(name);
		this.andJoin = join;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public void addOutputPlace(EmPlace place) {
		
		if (getOutputPlaces().size() > 0) {
			throw new RuntimeException("This activity transition already has an output place");
		}
		super.addOutputPlace(place);
	}
	
	@Override
	public void fire(EmToken token) {
		
		log.debug("Firing AND-join transition");
		
		for (EmPlace inputPlace : getInputPlaces()) {
			if (inputPlace.hasToken(token)) {
				inputPlace.removeToken(token);
			} else {
				inputPlace.removeToken();
			}
		}
		
		getOutputPlace().addToken(token);
	}
	
	public AndJoin getAndJoin() {
		
		return this.andJoin;
	}
	
	public EmPlace getOutputPlace() {

		Iterator<EmPlace> iterator = getOutputPlaces().iterator();
		EmPlace result = iterator.next();
		if (iterator.hasNext()) {
			throw new RuntimeException("An activity transition should only have one output place");
		}
		return result;
	}
	
	@Override
	public boolean isEnabled() {
		
		boolean result = true;
		for (EmPlace inputPlace : getInputPlaces()) {
			result &= inputPlace.hasToken();
		}
		return result;
	}
	
	@Override
	public String toString() {
		
		return "AND-join transition";
	}
}

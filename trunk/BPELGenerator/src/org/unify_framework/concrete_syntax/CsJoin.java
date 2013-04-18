package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.parser.Source;

public abstract class CsJoin extends CsTransition {
	
	public CsJoin() {
		
		// Do nothing
	}
	
	@Override
	public void addDestination(String destination) {
		
		if (getDestinations().size() == 0) {
			super.addDestination(destination);
		} else {
			throw new RuntimeException("This join already has a destination");
		}
	}
	
	public void addSource(Source source) {
		
		addSource(source.getSource());
	}
	
	public String getDestination() {
		
		for (String destination : getDestinations()) {
			return destination;
		}
		throw new RuntimeException("This join has no destination");
	}
	
	public void setDestination(String destination) {
		
		addDestination(destination);
	}
}

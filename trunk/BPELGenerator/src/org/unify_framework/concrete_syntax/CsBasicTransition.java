package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.visitors.ElementVisitor;

public class CsBasicTransition extends CsTransition {
	
	public CsBasicTransition() {
		
		// Do nothing
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public void addDestination(String destination) {
		
		if (getDestinations().size() == 0) {
			super.addDestination(destination);
		} else {
			throw new RuntimeException("This basic transition already has a destination");
		}
	}
	
	@Override
	public void addSource(String source) {
		
		if (getSources().size() == 0) {
			super.addSource(source);
		} else {
			throw new RuntimeException("This basic transition already has a source");
		}
	}
	
	public String getDestination() {
		
		for (String destination : getDestinations()) {
			return destination;
		}
		throw new RuntimeException("This basic transition has no destination");
	}
	
	public String getSource() {
		
		for (String source : getSources()) {
			return source;
		}
		throw new RuntimeException("This basic transition has no source");
	}
	
	public void setDestination(String destination) {
		
		addDestination(destination);
	}
	
	public void setSource(String source) {
		
		addSource(source);
	}
}

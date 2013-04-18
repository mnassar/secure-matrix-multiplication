package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.parser.Destination;

public abstract class CsSplit extends CsTransition {
	
	public CsSplit() {
		
		// Do nothing
	}
	
	public void addDestination(Destination destination) {
		
		addDestination(destination.getDestination());
	}
	
	@Override
	public void addSource(String source) {
		
		if (getSources().size() == 0) {
			super.addSource(source);
		} else {
			throw new RuntimeException("This split already has a source");
		}
	}
	
	public String getSource() {
		
		for (String source : getSources()) {
			return source;
		}
		throw new RuntimeException("This split has no source");
	}
	
	public void setSource(String source) {
		
		addSource(source);
	}
}

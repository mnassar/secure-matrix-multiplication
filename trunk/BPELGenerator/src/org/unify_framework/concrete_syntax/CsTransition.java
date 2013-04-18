package org.unify_framework.concrete_syntax;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.unify_framework.concrete_syntax.visitors.Element;

public abstract class CsTransition implements Element {
	
	private List<String> sources;
	private List<String> destinations;
	
	public CsTransition() {
		
		this.sources = new LinkedList<String>();
		this.destinations = new LinkedList<String>();
	}
	
	public void addDestination(String destination) {
		
		this.destinations.add(destination);
	}
	
	public void addSource(String source) {
		
		this.sources.add(source);
	}
	
	public Collection<String> getDestinations() {
		
		return this.destinations;
	}
	
	public Collection<String> getSources() {
		
		return this.sources;
	}
}

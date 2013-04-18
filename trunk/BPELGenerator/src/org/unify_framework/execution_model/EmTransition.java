package org.unify_framework.execution_model;

import java.util.HashSet;
import java.util.Set;

import org.unify_framework.execution_model.visitors.Element;

public abstract class EmTransition implements Element {
	
	private String name;
	
	private EmPetriNet parent;
	
	private Set<EmPlace> inputPlaces;
	private Set<EmPlace> outputPlaces;
	
	public EmTransition(String name) {
		
		this.name = name;
		
		this.inputPlaces = new HashSet<EmPlace>();
		this.outputPlaces = new HashSet<EmPlace>();
	}
	
	public void addInputPlace(EmPlace place) {
		
		this.inputPlaces.add(place);
		place.setOutgoingTransition(this);
	}
	
	public void addOutputPlace(EmPlace place) {
		
		this.outputPlaces.add(place);
		place.setIncomingTransition(this);
	}

	public abstract void fire(EmToken token);
	
	public Set<EmPlace> getInputPlaces() {
	
		return this.inputPlaces;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public Set<EmPlace> getOutputPlaces() {
	
		return this.outputPlaces;
	}
	
	protected EmPetriNet getParent() {
		
		return this.parent;
	}
	
	public abstract boolean isEnabled();
	
	protected void setParent(EmPetriNet parent) {
		
		this.parent = parent;
	}
}

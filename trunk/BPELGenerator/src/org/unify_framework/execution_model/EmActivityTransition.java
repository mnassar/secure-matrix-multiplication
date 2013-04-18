package org.unify_framework.execution_model;

import java.util.Iterator;

import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.execution_model.visitors.ElementVisitor;

public abstract class EmActivityTransition extends EmTransition {
	
	private AtomicActivity activity;
	
	public EmActivityTransition(String name, AtomicActivity activity) {
		
		super(name);
		this.activity = activity;
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
	public void addOutputPlace(EmPlace place) {
		
		if (getOutputPlaces().size() > 0) {
			throw new RuntimeException("This activity transition already has an output place");
		}
		super.addOutputPlace(place);
	}
	
	public AtomicActivity getActivity() {
		
		return this.activity;
	}
	
	public EmPlace getInputPlace() {
		
		Iterator<EmPlace> iterator = getInputPlaces().iterator();
		EmPlace result = iterator.next();
		if (iterator.hasNext()) {
			throw new RuntimeException("An activity transition should only have one input place");
		}
		return result;
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
		
		return getInputPlace().hasToken();
	}
	
	@Override
	public String toString() {
		
		return "Activity transition " + getName();
	}
}

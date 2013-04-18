package org.unify_framework.abstract_syntax.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;

public class AtomicActivityImpl extends ActivityImpl implements AtomicActivity {
	
	public AtomicActivityImpl(String name) {
		
		this(name, false);
	}
	
	public AtomicActivityImpl(String name, boolean woven) {
		
		super(name, woven);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public AtomicActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		AtomicActivityImpl copy = new AtomicActivityImpl(getName(), getWoven());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	@Override
	public String toString() {

		return this.getQualifiedName();
	}

	@Override
	public void replaceString(String target, String replacement) {
		
		// Do nothing
	}
}

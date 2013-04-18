package org.unify_framework.instances.bpel;

import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

import org.unify_framework.instances.bpel.visitors.Element;

public abstract class BpelAtomicActivity extends AtomicActivityImpl implements Element {
	
	public BpelAtomicActivity(String name) {
		
		super(name);
	}
}

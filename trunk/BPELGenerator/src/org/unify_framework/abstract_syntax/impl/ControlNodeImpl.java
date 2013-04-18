package org.unify_framework.abstract_syntax.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.ControlPort;

public abstract class ControlNodeImpl extends NodeImpl implements ControlNode {
	
	public ControlNodeImpl(String name, boolean woven) {

		super(name, woven);
	}
	
	@Override
	public abstract ControlNodeImpl getCopy(Map<ControlPort, ControlPort> mapping);
}

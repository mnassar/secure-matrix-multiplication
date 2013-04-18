package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.visitors.ElementVisitor;

public class CsAndJoin extends CsJoin {
	
	public CsAndJoin() {
		
		// Do nothing
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

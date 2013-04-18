package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.visitors.ElementVisitor;

public class CsAndSplit extends CsSplit {
	
	public CsAndSplit() {
		
		// Do nothing
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

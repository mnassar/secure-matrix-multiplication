package org.unify_framework.concrete_syntax;

import org.unify_framework.concrete_syntax.visitors.ElementVisitor;

public class CsAtomicActivity extends CsActivity {
	
	public CsAtomicActivity() {
		
		// Do nothing
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

package org.unify_framework.abstract_syntax.connector_mechanism;

import org.unify_framework.abstract_syntax.Activity;

public abstract class ControlStructureConnector extends InversionOfControlConnector {
	
	public ControlStructureConnector(Activity adviceActivity) {
		
		super(adviceActivity);
	}
	
	public ControlStructureConnector(QualifiedName adviceActivity) {
		
		super(adviceActivity);
	}
}

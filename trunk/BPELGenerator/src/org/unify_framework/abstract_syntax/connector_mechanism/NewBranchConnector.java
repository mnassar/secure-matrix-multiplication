package org.unify_framework.abstract_syntax.connector_mechanism;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: NewBranchConnector.java 18246 2011-10-20 10:20:55Z njonchee $
 */
public class NewBranchConnector extends ControlStructureConnector {
	
	public NewBranchConnector(Activity adviceActivity) {
		
		super(adviceActivity);
	}
	
	public NewBranchConnector(QualifiedName adviceActivity) {
		
		super(adviceActivity);
	}
	
	@Override
	public String getName() {
		
		return "InConnector";
	}
}

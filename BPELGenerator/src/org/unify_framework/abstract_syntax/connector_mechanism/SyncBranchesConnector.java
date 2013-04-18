package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: SyncBranchesConnector.java 18349 2011-10-28 13:58:28Z njonchee $
 */
public class SyncBranchesConnector extends SyncOrSwitchBranchesConnector {
	
//	public SyncBranchesConnector(Activity adviceActivity, ControlPort splittingControlPort, ControlPort joiningControlPort) {
//		
//		super(adviceActivity, splittingControlPort, joiningControlPort);
//	}
	
	public SyncBranchesConnector(QualifiedName adviceActivity, ControlPortPointcut splittingControlPort, ControlPortPointcut joiningControlPort) {
		
		super(adviceActivity, splittingControlPort, joiningControlPort);
	}
	
	public SyncBranchesConnector(QualifiedName adviceActivity, ControlPortPointcut splittingControlPort, ControlPortPointcut joiningControlPort, List<DataMapping> dataMappings) {
		
		super(adviceActivity, splittingControlPort, joiningControlPort, dataMappings);
	}
	
	@Override
	public String getName() {
		
		return "SyncBranchesConnector";
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " AND-SPLITTING AT " + getSplittingControlPortPointcut() + " SYNCHRONIZING AT " + getJoiningControlPortPointcut();
	}
}

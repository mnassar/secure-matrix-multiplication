package org.unify_framework.abstract_syntax.connector_mechanism;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: SwitchBranchesConnector.java 18294 2011-10-25 11:12:17Z njonchee $
 */
public class SwitchBranchesConnector extends SyncOrSwitchBranchesConnector {
	
//	public SwitchBranchesConnector(Activity adviceActivity, ControlPort splittingControlPort, ControlPort joiningControlPort) {
//		
//		super(adviceActivity, splittingControlPort, joiningControlPort);
//	}
	
	public SwitchBranchesConnector(QualifiedName adviceActivity, ControlPortPointcut splittingControlPort, ControlPortPointcut joiningControlPort) {
		
		super(adviceActivity, splittingControlPort, joiningControlPort);
	}
	
	@Override
	public String getName() {
		
		return "SwitchBrancesConnector";
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " SWITCHING AT " + getSplittingControlPortPointcut() + " XOR-JOINING AT " + getJoiningControlPortPointcut();
	}
}

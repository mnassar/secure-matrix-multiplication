package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.ControlPort;

/**
 * Common superclass for SyncBranchesConnector and SwitchBranchesConnector
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: SyncOrSwitchBranchesConnector.java 18349 2011-10-28 13:58:28Z njonchee $
 */
public abstract class SyncOrSwitchBranchesConnector extends ControlStructureConnector {
	
	private static final Log log = LogFactory.getLog(SyncOrSwitchBranchesConnector.class);
	
	private ControlPort splittingControlPort;
	private ControlPort joiningControlPort;
	private ControlPortPointcut splittingControlPortPointcut;
	private ControlPortPointcut joiningControlPortPointcut;
	
//	public SyncOrSwitchBranchesConnector(Activity adviceActivity, ControlPort splittingControlPort, ControlPort joiningControlPort) {
//		
//		super(adviceActivity);
//		this.splittingControlPort = splittingControlPort;
//		this.joiningControlPort = joiningControlPort;
//	}
	
	public SyncOrSwitchBranchesConnector(QualifiedName adviceActivity, ControlPortPointcut splittingControlPort, ControlPortPointcut joiningControlPort) {
		
		super(adviceActivity);
		this.splittingControlPortPointcut = splittingControlPort;
		this.joiningControlPortPointcut = joiningControlPort;
	}
	
	public SyncOrSwitchBranchesConnector(QualifiedName adviceActivity, ControlPortPointcut splittingControlPort, ControlPortPointcut joiningControlPort, List<DataMapping> dataMappings) {
		
		super(adviceActivity);
		this.splittingControlPortPointcut = splittingControlPort;
		this.joiningControlPortPointcut = joiningControlPort;
		this.setDataMappings(dataMappings);
	}
	
	public ControlPort getJoiningControlPort() {
		
		return this.joiningControlPort;
	}
	
	public ControlPortPointcut getJoiningControlPortPointcut() {
		
		return this.joiningControlPortPointcut;
	}
	
	public ControlPort getSplittingControlPort() {
		
		return this.splittingControlPort;
	}
	
	public ControlPortPointcut getSplittingControlPortPointcut() {
		
		return this.splittingControlPortPointcut;
	}
	
	public void resolveJoiningControlPort(Composition composition) {
		
		if (this.joiningControlPort == null) {
			this.joiningControlPort = composition.findControlPort(this.joiningControlPortPointcut);
		} else {
			log.warn("The joining control port pointcut of this sync or switch brances connector has already been resolved!");
		}
	}
	
	public void resolveSplittingControlPort(Composition composition) {
		
		if (this.splittingControlPort == null) {
			this.splittingControlPort = composition.findControlPort(this.splittingControlPortPointcut);
		} else {
			log.warn("The splitting control port pointcut of this sync or switch brances connector has already been resolved!");
		}
	}
}

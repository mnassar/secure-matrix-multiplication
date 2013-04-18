package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;
import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BeforeConnector.java 18599 2011-11-23 15:43:04Z njonchee $
 */
public class BeforeConnector extends ActivityOrRegionConnector {
	
	public BeforeConnector(Activity adviceActivity, Activity joinpointActivity) {
		
		super(adviceActivity, joinpointActivity);
	}
	
	public BeforeConnector(Activity adviceActivity, Set<Activity> activityPointcut) {
		
		super(adviceActivity, activityPointcut);
	}
	
	public BeforeConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName, activityPointcut);
	}
	
	public BeforeConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, List<DataMapping> dataMappings) {
		
		super(adviceActivityQName, activityPointcut);
		this.setDataMappings(dataMappings);
	}

	@Override
	public String getName() {
		
		return "BeforeConnector";
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " BEFORE " + getActivityPointcut();
	}
}

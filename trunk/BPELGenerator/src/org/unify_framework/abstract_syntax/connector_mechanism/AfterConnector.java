package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;
import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AfterConnector.java 18599 2011-11-23 15:43:04Z njonchee $
 */
public class AfterConnector extends ActivityOrRegionConnector {
	
	public AfterConnector(Activity adviceActivity, Activity joinpointActivity) {
		
		super(adviceActivity, joinpointActivity);
	}
	
	public AfterConnector(Activity adviceActivity, Set<Activity> activityPointcut) {
		
		super(adviceActivity, activityPointcut);
	}
	
	public AfterConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName, activityPointcut);
	}
	
	public AfterConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, List<DataMapping> dataMappings) {
		
		super(adviceActivityQName, activityPointcut);
		this.setDataMappings(dataMappings);
	}

	@Override
	public String getName() {
		
		return "AfterConnector";
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " AFTER " + getActivityPointcut();
	}
}

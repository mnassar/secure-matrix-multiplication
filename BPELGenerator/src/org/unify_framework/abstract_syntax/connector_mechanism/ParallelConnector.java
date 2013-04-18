package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;
import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ParallelConnector.java 18464 2011-11-10 12:01:02Z njonchee $
 */
public class ParallelConnector extends ActivityOrRegionConnector {
	
	public ParallelConnector(Activity adviceActivity, Activity joinpointActivity) {
		
		super(adviceActivity, joinpointActivity);
	}
	
	public ParallelConnector(Activity adviceActivity, Set<Activity> activityPointcut) {
		
		super(adviceActivity, activityPointcut);
	}
	
	public ParallelConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName, activityPointcut);
	}
	
	public ParallelConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, List<DataMapping> dataMappings) {
		
		super(adviceActivityQName, activityPointcut);
		this.setDataMappings(dataMappings);
	}

	@Override
	public String getName() {
		
		return "ParallelConnector";
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " PARALLEL TO " + getActivityPointcut();
	}
}

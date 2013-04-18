package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;
import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AroundConnector.java 18599 2011-11-23 15:43:04Z njonchee $
 */
public class AroundConnector extends ActivityOrRegionConnector {
	
	private Activity proceedActivity;
	
	public AroundConnector(Activity adviceActivity, Activity joinpointActivity) {
		
		super(adviceActivity, joinpointActivity);
	}
	
	public AroundConnector(Activity adviceActivity, Activity joinpointActivity, Activity proceedActivity) {
		
		super(adviceActivity, joinpointActivity);
		this.proceedActivity = proceedActivity;
	}
	
	public AroundConnector(Activity adviceActivity, Activity proceedActivity, Set<Activity> activityPointcut) {
		
		super(adviceActivity, activityPointcut);
		this.proceedActivity = proceedActivity;
	}
	
	public AroundConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName, activityPointcut);
	}
	
	public AroundConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, List<DataMapping> dataMappings) {
		
		super(adviceActivityQName, activityPointcut);
		this.setDataMappings(dataMappings);
	}

	@Override
	public String getName() {
		
		return "AroundConnector";
	}
	
	public Activity getProceedActivity() {
		
		return this.proceedActivity;
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " AROUND " + getActivityPointcut();
	}
}

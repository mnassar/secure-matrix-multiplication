package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;

public abstract class ActivityOrRegionConnector extends InversionOfControlConnector {
	
	private static final Log log = LogFactory.getLog(ActivityOrRegionConnector.class);

	private ActivityPointcut activityPointcut;
	private Set<Activity> resolvedActivityPointcut;
	
	public ActivityOrRegionConnector(Activity adviceActivity, Activity joinpointActivity) {
		
		super(adviceActivity);
		this.resolvedActivityPointcut = new HashSet<Activity>();
		this.resolvedActivityPointcut.add(joinpointActivity);
	}
	
	public ActivityOrRegionConnector(Activity adviceActivity, Set<Activity> resolvedActivityPointcut) {
		
		super(adviceActivity);
		this.resolvedActivityPointcut = resolvedActivityPointcut;
	}
	
	public ActivityOrRegionConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName);
		this.activityPointcut = activityPointcut;
	}
	
	public ActivityPointcut getActivityPointcut() {
		
		return this.activityPointcut;
	}
	
	public Set<Activity> getResolvedActivityPointcut() {
		
		return this.resolvedActivityPointcut;
	}
	
	public void resolvePointcut(Composition composition) {
		
		if (this.resolvedActivityPointcut == null) {
			this.resolvedActivityPointcut = composition.findActivities(this.activityPointcut);
		} else {
			log.warn("The pointcut of this activity or region connector has already been resolved!");
		}
	}
}

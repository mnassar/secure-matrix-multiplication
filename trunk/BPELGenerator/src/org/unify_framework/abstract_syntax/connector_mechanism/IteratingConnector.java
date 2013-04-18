package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class IteratingConnector extends ActivityOrRegionConnector {
	
public IteratingConnector(Activity adviceActivity, Set<Activity> activityPointcut) {
		
		super(adviceActivity, activityPointcut);
	}
	
	public IteratingConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName, activityPointcut);
	}
	
	@Override
	public String getName() {
		
		return "IteratingConnector";
	}
}

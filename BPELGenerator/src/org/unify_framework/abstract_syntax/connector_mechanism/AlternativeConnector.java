package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;
import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AlternativeConnector.java 18537 2011-11-18 12:58:01Z njonchee $
 */
public class AlternativeConnector extends ActivityOrRegionConnector {

	private Expression condition;
	
	// CONSTRUCTORS ////////////////////////////////////////////////////////////
	
	public AlternativeConnector(Activity adviceActivity, Set<Activity> activityPointcut) {
		
		super(adviceActivity, activityPointcut);
	}
	
	public AlternativeConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut) {
		
		super(adviceActivityQName, activityPointcut);
	}
	
	public AlternativeConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, String conditionExpression) {
		
		super(adviceActivityQName, activityPointcut);
		this.setCondition(new XpathExpressionImpl(conditionExpression));
	}
	
	public AlternativeConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, String conditionExpression, List<DataMapping> dataMappings) {
		
		super(adviceActivityQName, activityPointcut);
		this.setCondition(new XpathExpressionImpl(conditionExpression));
		this.setDataMappings(dataMappings);
	}
	
	public AlternativeConnector(QualifiedName adviceActivityQName, ActivityPointcut activityPointcut, Expression condition) {
		
		super(adviceActivityQName, activityPointcut);
		this.setCondition(condition);
	}
	
	// ACCESSORS ///////////////////////////////////////////////////////////////
	
	@Override
	public String getName() {
		
		return "AlternativeConnector";
	}
	
	public Expression getCondition() {
		
		return condition;
	}
	
	// MUTATORS ////////////////////////////////////////////////////////////////

	public void setCondition(Expression condition) {
		
		this.condition = condition;
	}
	
	// OPERATIONS //////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		
		return "CONNECT " + getAdviceActivityQName() + " ALTERNATIVE TO " + getActivityPointcut();
	}
}

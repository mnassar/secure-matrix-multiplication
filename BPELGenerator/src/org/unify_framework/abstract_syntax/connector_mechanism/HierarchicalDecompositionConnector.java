package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: HierarchicalDecompositionConnector.java 18346 2011-10-28 13:11:08Z njonchee $
 */
public class HierarchicalDecompositionConnector extends Connector {
	
	private static final Log log = LogFactory.getLog(HierarchicalDecompositionConnector.class);
	
	private Activity callingActivity;
	private Activity calledActivity;
	private QualifiedName callingActivityQName;
	private QualifiedName calledActivityQName;
	
	public HierarchicalDecompositionConnector(Activity callingActivity, Activity calledActivity) {
		
		this.callingActivity = callingActivity;
		this.calledActivity = calledActivity;
	}
	
	public HierarchicalDecompositionConnector(QualifiedName callingActivity, QualifiedName calledActivity) {
		
		this.callingActivityQName = callingActivity;
		this.calledActivityQName = calledActivity;
	}
	
	public HierarchicalDecompositionConnector(QualifiedName callingActivity, QualifiedName calledActivity, List<DataMapping> dataMappings) {
		
		this.callingActivityQName = callingActivity;
		this.calledActivityQName = calledActivity;
		this.setDataMappings(dataMappings);
	}
	
	public Activity getCalledActivity() {

		if (this.calledActivity == null) { log.error("The called activity has not yet been resolved!"); }
		return this.calledActivity;
	}
	
	public Activity getCallingActivity() {
		
		if (this.callingActivity == null) { log.error("The calling activity has not yet been resolved!"); }
		return this.callingActivity;
	}
	
	@Override
	public String getName() {
		
		return "HierarchicalDecompositionConnector";
	}
	
	public void resolveCalledActivity(Composition composition) {
		
		if (this.calledActivity == null) {
			this.calledActivity = composition.findActivity(this.calledActivityQName);
		} else {
			log.warn("The called activity of this hierarchical decomposition connector has already been resolved!");
		}
	}
	
	public void resolveCallingActivity(Composition composition) {
		
		if (this.callingActivity == null) {
			this.callingActivity = composition.findActivity(this.callingActivityQName);
		} else {
			log.warn("The calling activity of this hierarchical decomposition connector has already been resolved!");
		}
	}
	
	@Override
	public String toString() {
		
		return "CONNECT " + this.callingActivityQName + " TO " + this.calledActivityQName;
	}
}

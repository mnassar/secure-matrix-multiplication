package org.unify_framework.abstract_syntax.connector_mechanism;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;

public abstract class InversionOfControlConnector extends Connector {
	
	private static final Log log = LogFactory.getLog(InversionOfControlConnector.class);

	private Activity adviceActivity;
	private QualifiedName adviceActivityQName;
	
	public InversionOfControlConnector(Activity adviceActivity) {
		
		this.adviceActivity = adviceActivity;
	}
	
	public InversionOfControlConnector(QualifiedName adviceActivity) {
		
		this.adviceActivityQName = adviceActivity;
	}
	
	public Activity getAdviceActivity() {
		
		return this.adviceActivity;
	}
	
	public QualifiedName getAdviceActivityQName() {
		
		return this.adviceActivityQName;
	}
	
	public void resolveActivity(Composition composition) {
		
		if (this.adviceActivity == null) {
			this.adviceActivity = composition.findActivity(this.adviceActivityQName);
		} else {
			log.warn("The activity of this inversion of control connector has already been resolved!");
		}
	}
}

package org.unify_framework.instances.bpel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

import org.w3c.dom.Node;

public class BpelFromExpression extends BpelFrom {
	
	/** The BPEL from expression's Apache Commons Logging {@link Log}. */
	private static final Log log = LogFactory.getLog(BpelFromExpression.class);
	
	private Node expression;
	
	public BpelFromExpression(Node expression) {
		
		log.debug("Creating BPEL from expression with expression '" + expression + "'...");
		this.expression = expression;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelFromExpression getCopy() {
		
		return new BpelFromExpression(this.expression);
	}
	
	public Node getExpression() {
		
		return this.expression;
	}
}

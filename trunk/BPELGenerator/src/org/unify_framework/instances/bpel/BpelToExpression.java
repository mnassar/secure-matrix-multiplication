package org.unify_framework.instances.bpel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

import org.w3c.dom.Node;

public class BpelToExpression extends BpelTo {
	
	/** The BPEL from expression's Apache Commons Logging {@link Log}. */
	private static final Log log = LogFactory.getLog(BpelToExpression.class);
	
	private Node expression;
	
	public BpelToExpression(Node expression) {
		
		log.debug("Creating BPEL to expression with expression '" + expression + "'...");
		this.expression = expression;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public Node getExpression() {
		
		return this.expression;
	}
	
	@Override
	public BpelToExpression getCopy() {
		
		return new BpelToExpression(this.expression);
	}
}

package org.unify_framework.concrete_syntax.parser;

import org.apache.commons.digester.SetNextRule;

public class CustomSetNextRule extends SetNextRule {
	
	/**
	 * Construct a custom "set next" rule with the specified method name.  The
	 * method's argument type is assumed to be the class of the child object.
	 * 
	 * @param methodName Method name of the parent method to call
	 */
	public CustomSetNextRule(String methodName) {
		
		super(methodName);
	}
	
	/**
	 * Process the end of this element.
	 */
	@Override
	public void end() throws Exception {
		
		if (this.digester.getCount() > 1) {
			super.end();
		}
	}
	
	/**
	 * Render a printable version of this Rule.
	 */
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("CustomSetNextRule[");
		sb.append("methodName=");
		sb.append(this.methodName);
		sb.append(", paramType=");
		sb.append(this.paramType);
		sb.append("]");
		return (sb.toString());
	}
}

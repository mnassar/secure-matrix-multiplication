package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelVariableType.java 18528 2011-11-17 12:39:57Z njonchee $
 */
public class BpelVariableType extends BpelVariable {
	
	private String type;
	private String nsPrefix;
	private String nsUri;
	
	public BpelVariableType(String name, String type) {
		
		super(name);
		this.type = type;
	}
	
	public BpelVariableType(String name, String type, String nsPrefix, String nsUri) {
		
		super(name);
		this.type = type;
		this.nsPrefix = nsPrefix;
		this.nsUri = nsUri;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public String getType() {
		
		return this.type;
	}
	
	@Override
	public String toString() {
		
		return getName() + " : " + this.type;
	}
	
	@Override
	public BpelVariable getCopy() {
		
		return new BpelVariableType(getName(), getType());
	}
	
	public String getNsPrefix() {
		
		return nsPrefix;
	}
	
	public String getNsUri() {
		
		return nsUri;
	}
}

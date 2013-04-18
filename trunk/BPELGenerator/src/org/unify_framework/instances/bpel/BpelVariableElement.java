package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelVariableElement extends BpelVariable {
	
	private String element;
	private String nsPrefix;
	private String nsUri;

	public BpelVariableElement(String name, String element) {
		
		super(name);
		this.element = element;
	}
	
	public BpelVariableElement(String name, String element, String nsPrefix, String nsUri) {
		
		super(name);
		this.element = element;
		this.nsPrefix = nsPrefix;
		this.nsUri = nsUri;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public String getElement() {
		
		return this.element;
	}
	
	@Override
	public String toString() {
		
		return this.getName() + " : " + this.element;
	}
	
	@Override
	public BpelVariable getCopy() {
		
		return new BpelVariableElement(getName(), getElement(), getNsPrefix(), getNsUri());
	}
	
	public String getNsPrefix() {
		
		return nsPrefix;
	}
	
	public String getNsUri() {
		
		return nsUri;
	}
}

package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelVariableMessageType extends BpelVariable {
	
	private String messageType;
	
	public BpelVariableMessageType(String name, String messageType) {
		
		super(name);
		this.messageType = messageType;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public String getMessageType() {
		
		return this.messageType;
	}
	
	@Override
	public String toString() {
		
		return this.getName() + " : " + this.messageType;
	}
	
	@Override
	public BpelVariable getCopy() {
		
		return new BpelVariableMessageType(getName(), getMessageType());
	}
}

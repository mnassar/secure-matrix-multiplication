package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelCopy implements Element {
	
	private BpelFrom from;
	private BpelTo to;

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public BpelFrom getFrom() {
		
		return this.from;
	}
	
	public BpelTo getTo() {
		
		return this.to;
	}
	
	public void setFrom(BpelFrom from) {
		
		if (this.from != null) {
			this.from.setParent(null);
		}
		this.from = from;
		this.from.setParent(this);
	}
	
	public void setTo(BpelTo to) {
		
		this.to = to;
	}
	
	public BpelCopy getCopy() {
		
		BpelCopy copy = new BpelCopy();
		copy.setFrom(this.from.getCopy());
		copy.setTo(this.to.getCopy());
		return copy;
	}
}

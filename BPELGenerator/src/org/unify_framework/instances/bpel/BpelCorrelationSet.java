package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelCorrelationSet implements Element {

	
	private String name;
	private String properties;
	
	public BpelCorrelationSet(String name, String props) {
		
		this.name = name;
		this.properties = props;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	
	public String getName() {
		
		return this.name;
	}
	
	public String getProperties() {
		
		return this.properties;
	}
	
	public BpelCorrelationSet getCopy() {
		
		return new BpelCorrelationSet(this.name, this.properties);
	}
}

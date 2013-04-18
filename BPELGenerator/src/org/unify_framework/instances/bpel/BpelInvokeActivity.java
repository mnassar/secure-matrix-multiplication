package org.unify_framework.instances.bpel;

import org.unify_framework.abstract_syntax.ControlPort;

import java.util.Map;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelInvokeActivity extends BpelAtomicActivity {

	private String inputVariable;
	private String operation;
	private String outputVariable;
	private String partnerLink;
	private String portType;

	public BpelInvokeActivity(String name) {
		
		super(name);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public String getInputVariable() {
		
		return this.inputVariable;
	}
	
	public String getOperation() {
		
		return this.operation;
	}
	
	public String getOutputVariable() {
		
		return this.outputVariable;
	}
	
	public String getPartnerLink() {
		
		return this.partnerLink;
	}
	
	public String getPortType() {
		
		return this.portType;
	}
	
	public void setInputVariable(String inputVariable) {
		
		this.inputVariable = inputVariable;
	}
	
	public void setOperation(String operation) {
		
		this.operation = operation;
	}
	
	public void setOutputVariable(String outputVariable) {
		
		this.outputVariable = outputVariable;
	}
	
	public void setPartnerLink(String partnerLink) {
		
		this.partnerLink = partnerLink;
	}
	
	public void setPortType(String portType) {
		
		this.portType = portType;
	}
	
	/**
	 * Returns a copy of the invoke activity.
	 */
	@Override
	public BpelInvokeActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelInvokeActivity copy = new BpelInvokeActivity(getName());
		copy.setInputVariable(getInputVariable());
		copy.setOperation(getOperation());
		copy.setOutputVariable(getOutputVariable());
		copy.setPartnerLink(getPartnerLink());
		copy.setPortType(getPortType());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

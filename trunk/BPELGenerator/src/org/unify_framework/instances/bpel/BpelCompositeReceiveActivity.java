package org.unify_framework.instances.bpel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelCompositeReceiveActivity extends BpelCompositeActivity {

	
	private String createInstance;
	private String operation;
	private String partnerLink;
	private String portType;
	private String variable;

	private List<BpelCorrelation> correlations = new ArrayList<BpelCorrelation>();

	public String getCreateInstance() {
		
		return this.createInstance;
	}
	
	public String getOperation() {
		
		return this.operation;
	}
	
	public String getPartnerLink() {
		
		return this.partnerLink;
	}
	
	public String getPortType() {
		
		return this.portType;
	}
	
	public String getVariable() {
		
		return this.variable;
	}

	public void setCreateInstance(String createInstance) {
		
		this.createInstance = createInstance;
	}

	public void setOperation(String operation) {
		
		this.operation = operation;
	}

	public void setPartnerLink(String partnerLink) {
		
		this.partnerLink = partnerLink;
	}

	public void setPortType(String portType) {
		
		this.portType = portType;
	}

	public void setVariable(String variable) {
		
		this.variable = variable;
	}
	
	
	public void addCorrelation(BpelCorrelation correlation) {
		
		this.correlations.add(correlation);
	}
	
	
	public List<BpelCorrelation> getCorrelations(){
		
		return this.correlations;
	}
	/**
	 * Returns a copy of the receive activity.
	 */
	@Override
	public BpelReceiveActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelReceiveActivity copy = new BpelReceiveActivity(getName());
		copy.setCreateInstance(getCreateInstance());
		copy.setOperation(getOperation());
		copy.setPartnerLink(getPartnerLink());
		copy.setPortType(getPortType());
		copy.setVariable(getVariable());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	public BpelCompositeReceiveActivity(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ElementVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
	
	

}

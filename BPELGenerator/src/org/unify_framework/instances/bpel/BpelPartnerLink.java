package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelPartnerLink implements Element {

	private String myRole;
	private String name;
	private String partnerLinkType;
	private String partnerRole;
	
	public BpelPartnerLink(String name, String partnerLinkType, String myRole, String partnerRole) {
		
		this.name = name;
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public String getMyRole() {
		
		return this.myRole;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getPartnerLinkType() {
		
		return this.partnerLinkType;
	}
	
	public String getPartnerRole() {
		
		return this.partnerRole;
	}
	
	public BpelPartnerLink getCopy() {
		
		return new BpelPartnerLink(this.name, this.partnerLinkType, this.myRole, this.partnerRole);
	}
}

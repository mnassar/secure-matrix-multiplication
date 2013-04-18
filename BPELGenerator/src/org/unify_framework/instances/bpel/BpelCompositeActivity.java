package org.unify_framework.instances.bpel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;

import org.unify_framework.instances.bpel.visitors.Element;

public abstract class BpelCompositeActivity extends CompositeActivityImpl implements Element {
	
	private static final Log log = LogFactory.getLog(BpelCompositeActivity.class);
	
	private List<BpelPartnerLink> partnerLinks = new ArrayList<BpelPartnerLink>();
	private List<BpelCorrelationSet> correlationSets = new ArrayList<BpelCorrelationSet>();
	private BpelScope scope;
	
	public BpelCompositeActivity(String name) {
		
		super(name);
		this.scope = new BpelScope();
	}
	
	public void addPartnerLink(BpelPartnerLink partnerLink) {
		
		this.partnerLinks.add(partnerLink);
	}
	
	public void addCorrelationSet(BpelCorrelationSet corrSet) {
		
		this.correlationSets.add(corrSet);
	}
	public List<BpelPartnerLink> getPartnerLinks() {
		
		return this.partnerLinks;
	}
	
	public List<BpelCorrelationSet> getCorrelationSets(){
		
		return this.correlationSets;
	}
	
	public BpelScope getScope() {
		
		return this.scope;
	}
	
	public void setScope(BpelScope scope) {
		
		this.scope = scope;
	}
	
	/**
	 * Attempts to copy the BPEL composite activity into its argument.
	 * 
	 * @param copy The BPEL composite activity into which the composite activity
	 *		should be copied.
	 */
	@Override
	public void copy(CompositeActivity c) {
		
		log.debug("Copying BPEL composite activity '" + getName() + "'");
		if (c instanceof BpelCompositeActivity) {
			BpelCompositeActivity copy = (BpelCompositeActivity) c;
			super.copy(copy);
			for (BpelPartnerLink partnerLink : this.partnerLinks) {
				if (partnerLink.getMyRole() != null) {
					log.warn("Partner link has a myRole attribute; ignoring it");
				} else {
					BpelPartnerLink partnerLinkCopy = partnerLink.getCopy();
					copy.addPartnerLink(partnerLinkCopy);
				}
			}
			
			/*for (BpelCorrelationSet corrSet : this.correlationSets) {
				if (corrSet.getProperties() != null) {
					log.warn("Partner link has a myRole attribute; ignoring it");
				} else {
					BpelPartnerLink partnerLinkCopy = partnerLink.getCopy();
					copy.addPartnerLink(partnerLinkCopy);
				}
			}*/
			copy.scope = this.scope.getCopy(); // TODO Set scope copy's parent?
		} else {
			throw new NotImplementedException("A BPEL composite activity can only be copied into another BPEL composite activity");
		}
	}
}

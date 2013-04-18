package org.unify_framework.instances.bpmn;

import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnSubProcess.java 18712 2011-12-02 11:51:41Z njonchee $
 */
public class BpmnSubProcess extends BpmnCompositeActivity implements Element {
	
	public BpmnSubProcess(String name, String id) {
		
		super(name);
		this.setId(id);
	}
	
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

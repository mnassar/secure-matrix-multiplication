package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.StartEventImpl;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnStartEvent.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public class BpmnStartEvent extends StartEventImpl implements Element {
	
	private String id;
	
	public BpmnStartEvent(String name, String id) {
		
		super(name);
		this.id = id;
	}
	
	public String getId() {
		
		return id;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpmnStartEvent getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpmnStartEvent copy = new BpmnStartEvent(getName(), getId());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

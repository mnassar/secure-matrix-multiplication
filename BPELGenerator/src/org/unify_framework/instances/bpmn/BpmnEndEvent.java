package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.EndEventImpl;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnEndEvent.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public class BpmnEndEvent extends EndEventImpl implements Element {
	
	private String id;
	
	public BpmnEndEvent(String name, String id) {
		
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
	
	public BpmnEndEvent getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpmnEndEvent copy = new BpmnEndEvent(getName(), getId());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		return copy;
	}
}

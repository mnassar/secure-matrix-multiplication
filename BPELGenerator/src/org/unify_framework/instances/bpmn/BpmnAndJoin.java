package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnAndJoin.java 18724 2011-12-04 10:33:10Z njonchee $
 */
public class BpmnAndJoin extends AndJoinImpl implements Element {
	
	private String id;
	
	public BpmnAndJoin(String name, String id) {
		
		super(name);
		this.id = id;
	}
	
	public String getId() {
		
		return id;
	}
	
	public void setId(String id) {
		
		this.id = id;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpmnAndJoin getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpmnAndJoin copy = new BpmnAndJoin(getName(), getId());
		for (ControlInputPort controlInputPort : getControlInputPorts()) {
			mapping.put(controlInputPort, copy.getNewControlInputPort());
		}
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

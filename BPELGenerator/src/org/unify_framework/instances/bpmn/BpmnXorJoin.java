package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.XorJoinImpl;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnXorJoin.java 18724 2011-12-04 10:33:10Z njonchee $
 */
public class BpmnXorJoin extends XorJoinImpl implements Element {
	
	private String id;
	
	public BpmnXorJoin(String name, String id) {
		
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
	public BpmnXorJoin getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpmnXorJoin copy = new BpmnXorJoin(getName(), getId());
		for (ControlInputPort controlInputPort : getControlInputPorts()) {
			mapping.put(controlInputPort, copy.getNewControlInputPort());
		}
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

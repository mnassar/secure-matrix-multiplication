package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnAndSplit.java 18724 2011-12-04 10:33:10Z njonchee $
 */
public class BpmnAndSplit extends AndSplitImpl implements Element {
	
	private String id;
	
	public BpmnAndSplit(String name, String id) {
		
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
	public BpmnAndSplit getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpmnAndSplit copy = new BpmnAndSplit(getName(), getId());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		for (ControlOutputPort controlOutputPort : getControlOutputPorts()) {
			mapping.put(controlOutputPort, copy.getNewControlOutputPort());
		}
		return copy;
	}
}

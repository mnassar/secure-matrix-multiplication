package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnScriptTask.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public class BpmnScriptTask extends BpmnTask implements Element {
	
	private String script;
	
	public BpmnScriptTask(String name, String id) {
		
		super(name, id);
	}
	
	public String getScript() {
		
		return script;
	}
	
	public void setScript(String script) {
		
		this.script = script;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	/**
	 * Returns a copy of the script task.
	 */
	@Override
	public BpmnScriptTask getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpmnScriptTask copy = new BpmnScriptTask(getName(), getId());
		copy.setScript(getScript());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

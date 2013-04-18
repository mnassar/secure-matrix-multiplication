package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.GetValueFromDictionaryActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelGetValueFromDictionaryActivity extends GetValueFromDictionaryActivityImpl implements Element {
	
	public BpelGetValueFromDictionaryActivity(String name) {
		
		super(name);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelGetValueFromDictionaryActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelGetValueFromDictionaryActivity copy = new BpelGetValueFromDictionaryActivity(getName());
		copy.setSourceVariableName(this.getSourceVariableName());
		copy.setKey(this.getKey());
		copy.setTargetExpression(this.getTargetExpression());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}

	@Override
	public void replaceString(String target, String replacement) {
		
		this.setSourceVariableName(this.getSourceVariableName().replace(target, replacement));
		this.setKey(this.getKey().replace(target, replacement));
	}
}

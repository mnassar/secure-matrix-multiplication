package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.GetValueFromNestedDictionaryActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelGetValueFromNestedDictionaryActivity.java 18440 2011-11-08 11:54:15Z njonchee $
 */
public class BpelGetValueFromNestedDictionaryActivity extends GetValueFromNestedDictionaryActivityImpl implements Element {
	
	public BpelGetValueFromNestedDictionaryActivity(String name) {
		
		super(name);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelGetValueFromNestedDictionaryActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelGetValueFromNestedDictionaryActivity copy = new BpelGetValueFromNestedDictionaryActivity(getName());
		copy.setSourceVariableName(this.getSourceVariableName());
		copy.setFirstKey(this.getFirstKey());
		copy.setSecondKey(this.getSecondKey());
		copy.setTargetVariableName(this.getTargetVariableName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}

	@Override
	public void replaceString(String target, String replacement) {
		
		this.setSourceVariableName(this.getSourceVariableName().replace(target, replacement));
		this.setFirstKey(this.getFirstKey().replace(target, replacement));
		this.setSecondKey(this.getSecondKey().replace(target, replacement));
		this.setTargetVariableName(this.getTargetVariableName().replace(target, replacement));
	}
}

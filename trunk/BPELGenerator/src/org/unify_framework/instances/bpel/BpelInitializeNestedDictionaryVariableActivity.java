package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.InitializeNestedDictionaryVariableActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelInitializeNestedDictionaryVariableActivity extends InitializeNestedDictionaryVariableActivityImpl implements Element {
	
	public BpelInitializeNestedDictionaryVariableActivity(String name) {
		
		super(name);
	}
	
	@Override
	public BpelInitializeNestedDictionaryVariableActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelInitializeNestedDictionaryVariableActivity copy = new BpelInitializeNestedDictionaryVariableActivity(getName());
		copy.setDictionary(this.getDictionaryCopy());
		copy.setVariable(this.getVariable().getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

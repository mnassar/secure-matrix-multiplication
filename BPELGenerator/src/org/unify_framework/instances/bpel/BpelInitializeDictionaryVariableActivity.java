package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.InitializeDictionaryVariableActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelInitializeDictionaryVariableActivity extends InitializeDictionaryVariableActivityImpl implements Element {
	
	public BpelInitializeDictionaryVariableActivity(String name) {
		
		super(name);
	}
	
	@Override
	public BpelInitializeDictionaryVariableActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelInitializeDictionaryVariableActivity copy = new BpelInitializeDictionaryVariableActivity(getName());
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

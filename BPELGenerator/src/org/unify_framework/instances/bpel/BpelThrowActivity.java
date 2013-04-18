package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.exception_handling_perspective.impl.RaiseErrorActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelThrowActivity extends RaiseErrorActivityImpl implements Element {
	
	public BpelThrowActivity(String name) {
		
		super(name);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelThrowActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelThrowActivity copy = new BpelThrowActivity(getName());
		copy.setErrorName(this.getErrorName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

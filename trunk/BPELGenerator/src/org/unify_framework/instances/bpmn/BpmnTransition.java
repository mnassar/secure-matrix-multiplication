package org.unify_framework.instances.bpmn;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.impl.ControlInputPortImpl;
import org.unify_framework.abstract_syntax.impl.ControlOutputPortImpl;
import org.unify_framework.abstract_syntax.impl.TransitionImpl;
import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnTransition.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public class BpmnTransition extends TransitionImpl implements Element {
	
	private String id;
	
	public BpmnTransition(ControlOutputPort source, ControlInputPort destination) {
		
		super(source, destination);
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
	public BpmnTransition getCopy(Map<ControlPort, ControlPort> mapping) {
		
		ControlOutputPortImpl copiedSource = (ControlOutputPortImpl) mapping.get(getSource());
		ControlInputPortImpl copiedDestination = (ControlInputPortImpl) mapping.get(getDestination());
		BpmnTransition copy = new BpmnTransition(copiedSource, copiedDestination);
		copy.setId(getId());
		return copy;
	}
}

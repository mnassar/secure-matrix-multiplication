package org.unify_framework.instances.bpel;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelScopeActivity extends BpelCompositeActivity {
	
	private static final Log log = LogFactory.getLog(BpelScopeActivity.class);
	
	public BpelScopeActivity(String name) {
		
		super(name);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelScopeActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		log.debug("Copying BPEL scope activity '" + getName() + "'");
		BpelScopeActivity copy = new BpelScopeActivity(getName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		mapping.put(getStartEvent().getControlOutputPort(), copy.getStartEvent().getControlOutputPort());
		mapping.put(getEndEvent().getControlInputPort(), copy.getEndEvent().getControlInputPort());
		for (Activity activity : this.getActivities()) {
			Activity activityCopy = (Activity) activity.getCopy(mapping);
			copy.addActivity(activityCopy);
		}
		for (ControlNode controlNode : this.getControlNodes()) {
			ControlNode controlNodeCopy = (ControlNode) controlNode.getCopy(mapping);
			copy.addControlNode(controlNodeCopy);
		}
		for (Transition transition : this.getTransitions()) {
			Transition transitionCopy = transition.getCopy(mapping);
			copy.addTransition(transitionCopy);
		}
		copy.setScope(this.getScope().getCopy());
		return copy;
	}
}

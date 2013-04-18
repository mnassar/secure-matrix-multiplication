package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * Represents a WS-BPEL &lt;empty&gt; activity.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelEmptyActivity.java 18432 2011-11-08 10:16:55Z njonchee $
 */
public class BpelEmptyActivity extends BpelAtomicActivity {
	
	/**
	 * Creates a new WS-BPEL &lt;empty&gt; activity.
	 * 
	 * @param name The name of the activity
	 */
	public BpelEmptyActivity(String name) {
		
		super(name);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	/**
	 * Returns a copy of the WS-BPEL &lt;empty&gt; activity.
	 */
	@Override
	public BpelEmptyActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelEmptyActivity copy = new BpelEmptyActivity(getName());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

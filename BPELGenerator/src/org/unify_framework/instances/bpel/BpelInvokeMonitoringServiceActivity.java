package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.InvokeMonitoringServiceActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelInvokeMonitoringServiceActivity extends InvokeMonitoringServiceActivityImpl implements Element {
	
	public BpelInvokeMonitoringServiceActivity(String name) {
		
		super(name);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public BpelInvokeMonitoringServiceActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelInvokeMonitoringServiceActivity copy = new BpelInvokeMonitoringServiceActivity(this.getName());
		copy.setMessage(this.getMessage());
		copy.setUsernameVariable(this.getUsernameVariable().getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

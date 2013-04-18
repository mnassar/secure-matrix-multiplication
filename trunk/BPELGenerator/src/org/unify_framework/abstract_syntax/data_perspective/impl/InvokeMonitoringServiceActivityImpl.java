package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.InvokeMonitoringServiceActivity;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class InvokeMonitoringServiceActivityImpl extends AtomicActivityImpl implements InvokeMonitoringServiceActivity {
	
	private String message;
	private Expression usernameVariable;
	
	public InvokeMonitoringServiceActivityImpl(String name) {
		
		super(name);
	}
	
	@Override
	public String getMessage() {
		
		return message;
	}
	
	@Override
	public void setMessage(String message) {
		
		this.message = message;
	}
	
	@Override
	public Expression getUsernameVariable() {
		
		return usernameVariable;
	}
	
	@Override
	public void setUsernameVariable(Expression usernameVariable) {
		
		this.usernameVariable = usernameVariable;
	}
	
	@Override
	public InvokeMonitoringServiceActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		InvokeMonitoringServiceActivityImpl copy = new InvokeMonitoringServiceActivityImpl(this.getName());
		copy.setMessage(this.getMessage());
		copy.setUsernameVariable(this.getUsernameVariable().getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
}

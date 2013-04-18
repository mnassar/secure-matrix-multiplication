package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.CopyExpressionToVariableActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelCopyExpressionToVariableActivity extends CopyExpressionToVariableActivityImpl implements Element {
	
	public BpelCopyExpressionToVariableActivity(String name) {
		
		super(name);
	}
	
	@Override
	public BpelCopyExpressionToVariableActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelCopyExpressionToVariableActivity copy = new BpelCopyExpressionToVariableActivity(getName());
		copy.setSourceExpression(this.getSourceExpression().getCopy());
		copy.setTargetVariable(this.getTargetVariable().getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

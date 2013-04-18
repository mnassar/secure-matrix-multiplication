package org.unify_framework.instances.bpel;

import java.util.Map;

import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.data_perspective.impl.CopyExpressionToExpressionActivityImpl;
import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class BpelCopyExpressionToExpressionActivity extends CopyExpressionToExpressionActivityImpl implements Element {
	
	public BpelCopyExpressionToExpressionActivity(String name) {
		
		super(name);
	}
	
	@Override
	public BpelCopyExpressionToExpressionActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelCopyExpressionToExpressionActivity copy = new BpelCopyExpressionToExpressionActivity(getName());
		copy.setSourceExpression(this.getSourceExpression().getCopy());
		copy.setTargetExpression(this.getTargetExpression().getCopy());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		return copy;
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
}

package org.unify_framework.instances.bpmn;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnCompositeActivity.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public abstract class BpmnCompositeActivity extends CompositeActivityImpl {

	private static final Log log = LogFactory.getLog(BpmnCompositeActivity.class);
	
	private String id;
	
	public BpmnCompositeActivity(String name) {
		
		super(name);
	}

	public String getId() {
		
		return id;
	}

	public void setId(String id) {
		
		this.id = id;
	}
	
	@Override
	public Transition connect(ControlOutputPort source, ControlInputPort destination) {
		
		BpmnTransition transition = new BpmnTransition(source, destination);
		this.addTransition(transition);
		return transition;
	}
	
	/**
	 * Attempts to copy the BPMN composite activity into its argument.
	 * 
	 * @param copy The BPMN composite activity into which the composite activity
	 *		should be copied.
	 */
	@Override
	public void copy(CompositeActivity c) {
		
		log.debug("Copying BPMN composite activity '" + getName() + "'");
		if (c instanceof BpmnCompositeActivity) {
			BpmnCompositeActivity copy = (BpmnCompositeActivity) c;
			super.copy(copy);
		} else {
			throw new NotImplementedException("A BPMN composite activity can only be copied into another BPMN composite activity");
		}
	}
}

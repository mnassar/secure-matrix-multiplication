package org.unify_framework.instances.bpmn.source_code_weaver;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.RandomStringUtils;
import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.instances.bpmn.BpmnAndJoin;
import org.unify_framework.instances.bpmn.BpmnAndSplit;
import org.unify_framework.instances.bpmn.BpmnProcess;
import org.unify_framework.instances.bpmn.BpmnSubProcess;
import org.unify_framework.instances.bpmn.BpmnXorJoin;
import org.unify_framework.instances.bpmn.BpmnXorSplit;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnSourceCodeWeaver.java 18724 2011-12-04 10:33:10Z njonchee $
 */
public class BpmnSourceCodeWeaver extends SourceCodeWeaver {
	
	/* CONSTRUCTORS ***********************************************************/
	
	public BpmnSourceCodeWeaver() {
		
		super();
	}
	
	public BpmnSourceCodeWeaver(Composition composition) {
		
		super(composition);
	}
	
	@Override
	protected Activity copy(Activity activity) {
		
		if (activity == null) {
			throw new RuntimeException("The activity to be copied is null!");
		} else if (activity instanceof BpmnProcess) {
			BpmnProcess process = (BpmnProcess) activity;
			BpmnSubProcess copy = new BpmnSubProcess(process.getName(), RandomStringUtils.randomAlphanumeric(4)); // Generate new id
			process.copy(copy);
			return copy;
		} else {
			throw new NotImplementedException("The BPMN source code weaver can only copy activities of type BpmnProcess; activity to be copied is of type " + activity.getClass().getSimpleName());
		}
	}
	
	@Override
	public XorJoin createXorJoin() {
		
		return new BpmnXorJoin(generateRandomName("WovenBpmnXorJoin_"), RandomStringUtils.randomAlphanumeric(4));
	}
	
	@Override
	public XorJoin createXorJoin(String name) {
		
		return new BpmnXorJoin(name, RandomStringUtils.randomAlphanumeric(4));
	}

	@Override
	public XorSplit createXorSplit() {
		
		return new BpmnXorSplit(generateRandomName("WovenBpmnXorSplit_"), RandomStringUtils.randomAlphanumeric(4));
	}
	
	@Override
	public XorSplit createXorSplit(String name) {
		
		return new BpmnXorSplit(name, RandomStringUtils.randomAlphanumeric(4));
	}

	@Override
	public AndJoin createAndJoin() {
		
		return new BpmnAndJoin(generateRandomName("WovenBpmnAndJoin_") + "_Join", RandomStringUtils.randomAlphanumeric(4));
	}

	@Override
	public AndSplit createAndSplit() {
		
		return new BpmnAndSplit(generateRandomName("WovenBpmnAndSplit_") + "_Split", RandomStringUtils.randomAlphanumeric(4));
	}
}

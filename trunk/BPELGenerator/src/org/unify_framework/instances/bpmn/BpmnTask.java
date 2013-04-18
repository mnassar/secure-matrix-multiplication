package org.unify_framework.instances.bpmn;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnTask.java 18688 2011-11-29 15:40:52Z njonchee $
 */
public class BpmnTask extends BpmnAtomicActivity {
	
	private String id;
	
	public BpmnTask(String name, String id) {
		
		super(name);
		this.id = id;
	}
	
	public String getId() {
		
		return id;
	}
}

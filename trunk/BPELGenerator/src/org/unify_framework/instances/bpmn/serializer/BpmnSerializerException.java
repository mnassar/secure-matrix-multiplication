package org.unify_framework.instances.bpmn.serializer;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnSerializerException.java 18712 2011-12-02 11:51:41Z njonchee $
 */
public class BpmnSerializerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BpmnSerializerException(String message) {
		
		super(message);
	}
}

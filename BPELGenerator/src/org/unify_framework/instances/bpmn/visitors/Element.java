package org.unify_framework.instances.bpmn.visitors;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: Element.java 18713 2011-12-02 12:49:30Z njonchee $
 */
public interface Element {
	
	public void accept(ElementVisitor visitor);
}

package org.unify_framework.instances.bpmn.visitors;

import org.unify_framework.instances.bpmn.BpmnAndJoin;
import org.unify_framework.instances.bpmn.BpmnAndSplit;
import org.unify_framework.instances.bpmn.BpmnEndEvent;
import org.unify_framework.instances.bpmn.BpmnProcess;
import org.unify_framework.instances.bpmn.BpmnScriptTask;
import org.unify_framework.instances.bpmn.BpmnStartEvent;
import org.unify_framework.instances.bpmn.BpmnSubProcess;
import org.unify_framework.instances.bpmn.BpmnTransition;
import org.unify_framework.instances.bpmn.BpmnXorJoin;
import org.unify_framework.instances.bpmn.BpmnXorSplit;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ElementVisitor.java 18713 2011-12-02 12:49:30Z njonchee $
 */
public interface ElementVisitor {
	
	public void visit(BpmnAndJoin andJoin);
	public void visit(BpmnAndSplit andSplit);
	public void visit(BpmnEndEvent endEvent);
	public void visit(BpmnProcess process);
	public void visit(BpmnScriptTask scriptTask);
	public void visit(BpmnStartEvent startEvent);
	public void visit(BpmnSubProcess subProcess);
	public void visit(BpmnTransition transition);
	public void visit(BpmnXorJoin xorJoin);
	public void visit(BpmnXorSplit xorSplit);
}

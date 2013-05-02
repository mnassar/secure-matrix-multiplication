package org.unify_framework.instances.bpel.visitors;

import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelCopyExpressionToExpressionActivity;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelEmptyActivity;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelFromVariable;
import org.unify_framework.instances.bpel.BpelGetValueFromDictionaryActivity;
import org.unify_framework.instances.bpel.BpelGetValueFromNestedDictionaryActivity;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInitializeDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelInitializeNestedDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelCopyExpressionToVariableActivity;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelInvokeMonitoringServiceActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelSequence;
import org.unify_framework.instances.bpel.BpelThrowActivity;
import org.unify_framework.instances.bpel.BpelToExpression;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariableElement;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.BpelXorJoin;
import org.unify_framework.instances.bpel.BpelXorSplit;

public interface ElementVisitor {

//	public void visit(BpelAndJoin andJoin);
	public void visit(BpelAndSplit andSplit);
	public void visit(BpelAssignActivity assignActivity);
	public void visit(BpelCopy copy);
	public void visit(BpelCopyExpressionToExpressionActivity copyExpressionToExpressionActivity);
	public void visit(BpelCopyExpressionToVariableActivity copyExpressionToVariableActivity);
	public void visit(BpelEmptyActivity emptyActivity);
	public void visit(BpelFromExpression from);
	public void visit(BpelFromVariable from);
	public void visit(BpelGetValueFromDictionaryActivity getValueFromDictionaryActivity);
	public void visit(BpelGetValueFromNestedDictionaryActivity getValueFromNestedDictionaryActivity);
	public void visit(BpelImport bpelImport);
	public void visit(BpelInitializeDictionaryVariableActivity initializeDictionaryVariableActivity);
	public void visit(BpelInitializeNestedDictionaryVariableActivity initializeNestedDictionaryVariableActivity);
	public void visit(BpelInvokeMonitoringServiceActivity invokeMonitoringServiceActivity);
	public void visit(BpelInvokeActivity invokeActivity);
	public void visit(BpelPartnerLink partnerLink);
	public void visit(BpelProcess process);
	public void visit(BpelReceiveActivity receiveActivity);
	
	public void visit(BpelSequence sequence);
	public void visit(BpelReplyActivity replyActivity);
	public void visit(BpelScopeActivity scopeActivity);
	public void visit(BpelThrowActivity throwActivity);
	public void visit(BpelToExpression to);
	public void visit(BpelToVariable to);
	public void visit(BpelVariableElement variable);
	public void visit(BpelVariableMessageType variable);
	public void visit(BpelVariableType variable);
	public void visit(BpelXorJoin xorJoin);
	public void visit(BpelXorSplit andSplit);
	
	//Added by Farida Sabry
	public void visit(BpelCorrelationSet bpelCorrelationSet);
	public void visit(BpelCompositeReceiveActivity receiveActivity);
	public void visit(BpelCorrelation bpelCorrelation);
}

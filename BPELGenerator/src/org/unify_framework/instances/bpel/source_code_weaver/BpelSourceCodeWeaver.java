package org.unify_framework.instances.bpel.source_code_weaver;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.ActivityOrRegionConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.DataMapping;
import org.unify_framework.abstract_syntax.connector_mechanism.HierarchicalDecompositionConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.SyncOrSwitchBranchesConnector;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToExpressionActivity;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromDictionaryActivity;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromNestedDictionaryActivity;
import org.unify_framework.abstract_syntax.data_perspective.InitializeDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.InitializeNestedDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.InvokeMonitoringServiceActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.exception_handling_perspective.RaiseErrorActivity;
import org.unify_framework.instances.bpel.BpelAndJoin;
import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelCompositeActivity;
import org.unify_framework.instances.bpel.BpelCopyExpressionToExpressionActivity;
import org.unify_framework.instances.bpel.BpelEmptyActivity;
import org.unify_framework.instances.bpel.BpelGetValueFromDictionaryActivity;
import org.unify_framework.instances.bpel.BpelGetValueFromNestedDictionaryActivity;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInitializeDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelInitializeNestedDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelCopyExpressionToVariableActivity;
import org.unify_framework.instances.bpel.BpelInvokeMonitoringServiceActivity;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScope;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelThrowActivity;
import org.unify_framework.instances.bpel.BpelVariable;
import org.unify_framework.instances.bpel.BpelVariableElement;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.BpelXorJoin;
import org.unify_framework.instances.bpel.BpelXorSplit;
import org.unify_framework.instances.bpel.visitors.PerformDataMappingVisitor;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;

/**
 * Weaves a composition of WS-BPEL processes and Unify connectors
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelSourceCodeWeaver.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class BpelSourceCodeWeaver extends SourceCodeWeaver {
	
	private static final Log log = LogFactory.getLog(BpelSourceCodeWeaver.class);
	
	/* CONSTRUCTORS ***********************************************************/
	
	public BpelSourceCodeWeaver() {
		
		super();
	}
	
	public BpelSourceCodeWeaver(Composition composition) {
		
		super(composition);
	}
	
	/* FACTORY METHODS ********************************************************/
	
	@Override
	public AndJoin createAndJoin() {
		
		return new BpelAndJoin(generateRandomName("WovenBpelAndJoin_") + "_Join");
	}

	@Override
	public AndSplit createAndSplit() {
		
		return new BpelAndSplit(generateRandomName("WovenBpelAndSplit_") + "_Split");
	}
	
	@Override
	public CompositeActivity createCompositeActivity(String name) {
		
		// Create a new BPEL process with the specified name and with target namespace null:
		return new BpelProcess(name, null);
	}
	
	@Override
	public CopyExpressionToExpressionActivity createCopyExpressionToExpressionActivity(String name) {
		
		return new BpelCopyExpressionToExpressionActivity(name);
	}
	
	@Override
	public CopyExpressionToVariableActivity createCopyExpressionToVariableActivity(String name) {
		
		return new BpelCopyExpressionToVariableActivity(name);
	}
	
	@Override
	public AtomicActivity createDummyActivity(String name) {
		
		return new BpelEmptyActivity(name);
	}
	
	@Override
	public GetValueFromDictionaryActivity createGetValueFromDictionaryActivity(String name) {
		
		return new BpelGetValueFromDictionaryActivity(name);
	}
	
	@Override
	public GetValueFromNestedDictionaryActivity createGetValueFromNestedDictionaryActivity(String name) {
		
		return new BpelGetValueFromNestedDictionaryActivity(name);
	}
	
	@Override
	public InitializeDictionaryVariableActivity createInitializeDictionaryVariableActivity(String name) {
		
		return new BpelInitializeDictionaryVariableActivity(name);
	}
	
	@Override
	public InitializeNestedDictionaryVariableActivity createInitializeNestedDictionaryVariableActivity(String name) {
		
		return new BpelInitializeNestedDictionaryVariableActivity(name);
	}
	
	@Override
	public InvokeMonitoringServiceActivity createInvokeMonitoringServiceActivity(String name) {
		
		return new BpelInvokeMonitoringServiceActivity(name);
	}
	
	public RaiseErrorActivity createRaiseErrorActivity(String name) {
		
		return new BpelThrowActivity(name);
	}
	
	public Variable createVariableType(CompositeActivity compositeActivity, String variableName, String variableType, String nsPrefix, String nsUri) {
		
		BpelCompositeActivity bpelCompositeActivity = (BpelCompositeActivity) compositeActivity;
		BpelScope scope = bpelCompositeActivity.getScope();
		BpelVariable variable = new BpelVariableType(variableName, variableType, nsPrefix, nsUri);
		scope.addVariable(variable);
		return variable;
	}
	
	public Variable createVariableElement(CompositeActivity compositeActivity, String variableName, String variableElement, String nsPrefix, String nsUri) {
		
		BpelCompositeActivity bpelCompositeActivity = (BpelCompositeActivity) compositeActivity;
		BpelScope scope = bpelCompositeActivity.getScope();
		BpelVariable variable = new BpelVariableElement(variableName, variableElement, nsPrefix, nsUri);
		scope.addVariable(variable);
		return variable;
	}
	
	@Override
	public XorJoin createXorJoin() {
		
		return new BpelXorJoin(generateRandomName("WovenBpelXorJoin_"));
	}
	
	@Override
	public XorJoin createXorJoin(String name) {
		
		return new BpelXorJoin(name);
	}

	@Override
	public XorSplit createXorSplit() {
		
		return new BpelXorSplit(generateRandomName("WovenBpelXorSplit_"));
	}
	
	@Override
	public XorSplit createXorSplit(String name) {
		
		return new BpelXorSplit(name);
	}
	
	/* PROTECTED METHODS ******************************************************/
	
	@Override
	protected Activity copy(Activity activity) {
		
		if (activity == null) {
			throw new RuntimeException("The activity to be copied is null!");
		} else if (activity instanceof BpelProcess) {
			BpelProcess process = (BpelProcess) activity;
			BpelScopeActivity copy = new BpelScopeActivity(process.getName());
			process.copy(copy);
			BpelProcess mainConcern = (BpelProcess) getMainConcern();
			for (Map.Entry<String, String> entry : process.getNamespaceDeclarations().entrySet()) {
				// Don't copy the namespace declaration with prefix "tns":
				if (!entry.getKey().equals("tns")) {
					mainConcern.addNamespaceDeclaration(entry.getKey(), entry.getValue());
				}
			}
			for (BpelImport bpelImport : process.getImports()) {
				// Don't copy the import that corresponds to the BPEL process itself:
				if (process.getTargetNamespace().equals(bpelImport.getNamespace())) {
					log.warn("BPEL import corresponds to the BPEL process itself; ignoring it");
				} else {
					mainConcern.addImport(bpelImport.getCopy());
				}
			}
			return copy;
		} else {
			throw new NotImplementedException("The BPEL source code weaver can only copy activities of type BpelProcess; activity to be copied is of type " + activity.getClass().getSimpleName());
		}
	}
	
	@Override
	protected void performDataMapping(ActivityOrRegionConnector connector, Activity adviceActivity, Activity joinpoint) {
		
		// TODO Update the following comments
		
		// Data mappings have the form <qname> = <expression>, where <qname>
		// refers to the (crosscutting) concern's input variable, and
		// <expression> refers to the base concern.
		//
		// For example:
		//      $reportInput.payload = "{$thisJoinPoint.activityName} has been executed"
		//
		// $thisJoinPoint is a special variable that allows querying the
		// current joinpoint and will be expanded by the weaver.  Of course,
		// the base concern's variables can be used as well.
		//
		// During the weaving, every reference from the (crosscutting)
		// concern to <qname> should be replaced with <expression>.
		
		if (adviceActivity instanceof BpelScopeActivity) {
			
			BpelScopeActivity bpelAdviceActivity = (BpelScopeActivity) adviceActivity;
			
			// Remove the initial receive activity:
			Node firstRealNode = bpelAdviceActivity.getStartEvent().getOutgoingTransition().getDestinationNode();
			if (firstRealNode instanceof BpelReceiveActivity) {
				
				BpelReceiveActivity receiveActivity = (BpelReceiveActivity) firstRealNode;
				log.debug("Removing initial receive activity...");
				
				// Remove the receive activity's output variable:
				bpelAdviceActivity.getScope().removeVariable(receiveActivity.getVariable());
				
				Transition incomingTransition = receiveActivity.getIncomingTransition();
				Transition outgoingTransition = receiveActivity.getOutgoingTransition();
				incomingTransition.replaceDestination(incomingTransition.getDestination(), outgoingTransition.getDestination());
				outgoingTransition.getSource().setOutgoingTransition(null);
				bpelAdviceActivity.removeTransition(outgoingTransition);
				bpelAdviceActivity.removeActivity(receiveActivity);
			}
			
			// Remove the final reply activity:
			Node lastRealNode = bpelAdviceActivity.getEndEvent().getIncomingTransition().getSourceNode();
			if (lastRealNode instanceof BpelReplyActivity) {
				
				BpelReplyActivity replyActivity = (BpelReplyActivity) lastRealNode;
				log.debug("Removing final reply activity...");
				
				// Remove the reply activity's input variable:
				bpelAdviceActivity.getScope().removeVariable(replyActivity.getVariable());
				
				Transition incomingTransition = replyActivity.getIncomingTransition();
				Transition outgoingTransition = replyActivity.getOutgoingTransition();
				outgoingTransition.replaceSource(outgoingTransition.getSource(), incomingTransition.getSource());
				incomingTransition.getDestination().setIncomingTransition(null);
				bpelAdviceActivity.removeTransition(incomingTransition);
				bpelAdviceActivity.removeActivity(replyActivity);
			}
			
			List<DataMapping> dataMappings = connector.getDataMappings();
			if (dataMappings == null) {
				log.debug("There are no data mappings");
				return;
			} else {
				log.debug("There are " + dataMappings.size() + " data mappings");
				for (DataMapping dataMapping : dataMappings) {
					log.debug("Performing data mapping...");
					// Replace all references to the variable identified by <qname> with <expression>:
					PerformDataMappingVisitor visitor = new PerformDataMappingVisitor(dataMapping, joinpoint);
					visitor.visitScopeActivity(bpelAdviceActivity);
				}
			}
		}
	}
	
	@Override
	protected void performDataMapping(HierarchicalDecompositionConnector connector, Activity adviceActivity, Activity joinpoint) {
		
		if (adviceActivity instanceof BpelScopeActivity) {
			
			BpelScopeActivity bpelAdviceActivity = (BpelScopeActivity) adviceActivity;
			
			// Remove the initial receive activity:
			Node firstRealNode = bpelAdviceActivity.getStartEvent().getOutgoingTransition().getDestinationNode();
			if (firstRealNode instanceof BpelReceiveActivity) {
				
				BpelReceiveActivity receiveActivity = (BpelReceiveActivity) firstRealNode;
				log.debug("Removing initial receive activity...");
				
				// Remove the receive activity's output variable:
				bpelAdviceActivity.getScope().removeVariable(receiveActivity.getVariable());
				
				Transition incomingTransition = receiveActivity.getIncomingTransition();
				Transition outgoingTransition = receiveActivity.getOutgoingTransition();
				incomingTransition.replaceDestination(incomingTransition.getDestination(), outgoingTransition.getDestination());
				outgoingTransition.getSource().setOutgoingTransition(null);
				bpelAdviceActivity.removeTransition(outgoingTransition);
				bpelAdviceActivity.removeActivity(receiveActivity);
			}
			
			// Remove the final reply activity:
			Node lastRealNode = bpelAdviceActivity.getEndEvent().getIncomingTransition().getSourceNode();
			if (lastRealNode instanceof BpelReplyActivity) {
				
				BpelReplyActivity replyActivity = (BpelReplyActivity) lastRealNode;
				log.debug("Removing final reply activity...");
				
				// Remove the reply activity's input variable:
				bpelAdviceActivity.getScope().removeVariable(replyActivity.getVariable());
				
				Transition incomingTransition = replyActivity.getIncomingTransition();
				Transition outgoingTransition = replyActivity.getOutgoingTransition();
				outgoingTransition.replaceSource(outgoingTransition.getSource(), incomingTransition.getSource());
				incomingTransition.getDestination().setIncomingTransition(null);
				bpelAdviceActivity.removeTransition(incomingTransition);
				bpelAdviceActivity.removeActivity(replyActivity);
			}
			
			List<DataMapping> dataMappings = connector.getDataMappings();
			log.debug("There are " + dataMappings.size() + " data mappings");
			for (DataMapping dataMapping : dataMappings) {
				log.debug("Performing data mapping...");
				// Replace all references to the variable identified by <qname> with <expression>:
				PerformDataMappingVisitor visitor = new PerformDataMappingVisitor(dataMapping, joinpoint);
				visitor.visitScopeActivity(bpelAdviceActivity);
			}
		}
	}
	
	@Override
	protected void performDataMapping(SyncOrSwitchBranchesConnector connector, Activity adviceActivity) {
		
		if (adviceActivity instanceof BpelScopeActivity) {
			
			BpelScopeActivity bpelAdviceActivity = (BpelScopeActivity) adviceActivity;
			
			// Remove the initial receive activity:
			Node firstRealNode = bpelAdviceActivity.getStartEvent().getOutgoingTransition().getDestinationNode();
			if (firstRealNode instanceof BpelReceiveActivity) {
				
				BpelReceiveActivity receiveActivity = (BpelReceiveActivity) firstRealNode;
				log.debug("Removing initial receive activity...");
				
				// Remove the receive activity's output variable:
				bpelAdviceActivity.getScope().removeVariable(receiveActivity.getVariable());
				
				Transition incomingTransition = receiveActivity.getIncomingTransition();
				Transition outgoingTransition = receiveActivity.getOutgoingTransition();
				incomingTransition.replaceDestination(incomingTransition.getDestination(), outgoingTransition.getDestination());
				outgoingTransition.getSource().setOutgoingTransition(null);
				bpelAdviceActivity.removeTransition(outgoingTransition);
				bpelAdviceActivity.removeActivity(receiveActivity);
			}
			
			// Remove the final reply activity:
			Node lastRealNode = bpelAdviceActivity.getEndEvent().getIncomingTransition().getSourceNode();
			if (lastRealNode instanceof BpelReplyActivity) {
				
				BpelReplyActivity replyActivity = (BpelReplyActivity) lastRealNode;
				log.debug("Removing final reply activity...");
				
				// Remove the reply activity's input variable:
				bpelAdviceActivity.getScope().removeVariable(replyActivity.getVariable());
				
				Transition incomingTransition = replyActivity.getIncomingTransition();
				Transition outgoingTransition = replyActivity.getOutgoingTransition();
				outgoingTransition.replaceSource(outgoingTransition.getSource(), incomingTransition.getSource());
				incomingTransition.getDestination().setIncomingTransition(null);
				bpelAdviceActivity.removeTransition(incomingTransition);
				bpelAdviceActivity.removeActivity(replyActivity);
			}
			
			List<DataMapping> dataMappings = connector.getDataMappings();
			log.debug("There are " + dataMappings.size() + " data mappings");
			for (DataMapping dataMapping : dataMappings) {
				log.debug("Performing data mapping...");
				// Replace all references to the variable identified by <qname> with <expression>:
				PerformDataMappingVisitor visitor = new PerformDataMappingVisitor(dataMapping);
				visitor.visitScopeActivity(bpelAdviceActivity);
			}
		}
	}
}

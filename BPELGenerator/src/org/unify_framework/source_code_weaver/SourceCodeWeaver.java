package org.unify_framework.source_code_weaver;

import org.apache.commons.lang.RandomStringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.impl.XorJoinImpl;
import org.unify_framework.abstract_syntax.impl.XorSplitImpl;
import org.unify_framework.abstract_syntax.visitors.FindActivitiesVisitor;
import org.unify_framework.abstract_syntax.connector_mechanism.HierarchicalDecompositionConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.ActivityPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.AfterConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.AroundConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.ActivityOrRegionConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.AlternativeConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.BeforeConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.Connector;
import org.unify_framework.abstract_syntax.connector_mechanism.SyncBranchesConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.ParallelConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.SyncOrSwitchBranchesConnector;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToExpressionActivity;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromDictionaryActivity;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromNestedDictionaryActivity;
import org.unify_framework.abstract_syntax.data_perspective.InitializeDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.InitializeNestedDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.InvokeMonitoringServiceActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.data_perspective.impl.CopyExpressionToExpressionActivityImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.GetValueFromDictionaryActivityImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.GetValueFromNestedDictionaryActivityImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.InitializeDictionaryVariableActivityImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.InitializeNestedDictionaryVariableActivityImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.CopyExpressionToVariableActivityImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.InvokeMonitoringServiceActivityImpl;
import org.unify_framework.abstract_syntax.exception_handling_perspective.RaiseErrorActivity;
import org.unify_framework.abstract_syntax.exception_handling_perspective.impl.RaiseErrorActivityImpl;

// TODO When adding an activity to a composite activity, check for duplicate names

/**
 * The main source code weaver class.
 * 
 * <p>This class can be subclassed in order to instantiate Unify towards an existing workflow language, such as BPEL or BPMN.  The subclass should probably extend the create*() factory methods.</p>
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: SourceCodeWeaver.java 18724 2011-12-04 10:33:10Z njonchee $
 */
public class SourceCodeWeaver {
	
	private static final Log log = LogFactory.getLog(SourceCodeWeaver.class);
	
	public static String generateRandomName(String prefix) {
		
		return prefix + RandomStringUtils.randomAlphanumeric(4);
	}
	
	/* PRIVATE FIELDS *********************************************************/
	
	private Composition composition;
	private CompositeActivity mainConcern;
	
	/* CONSTRUCTORS ***********************************************************/
	
	/**
	 * Creates a new SourceCodeWeaver without specifying its Composition.
	 * 
	 * <p>Before invoking weave(), the Composition should be specified by invoking setComposition(Composition).</p>
	 */
	public SourceCodeWeaver() {
		
		// Do nothing
	}
	
	public SourceCodeWeaver(Composition composition) {
		
		this.composition = composition;
		this.mainConcern = composition.getBaseConcern();
	}
	
	/* GETTERS ****************************************************************/
	
	public Composition getComposition() {
		
		return this.composition;
	}
	
	public CompositeActivity getMainConcern() {
		
		return this.mainConcern;
	}
	
	/* SETTERS ****************************************************************/
	
	/**
	 * Sets the source code weaver's Composition, if it has not already been set.
	 */
	public void setComposition(Composition composition) {
		
		if (this.composition != null) {
			throw new RuntimeException("The Composition has already been set!");
		}
		
		this.composition = composition;
		this.mainConcern = composition.getBaseConcern();
	}
	
	/* FACTORY METHODS ********************************************************/
	
	public AndJoin createAndJoin() {
		
		return new AndJoinImpl(generateRandomName("WovenAndJoinImpl_"));
	}
	
	public AndSplit createAndSplit() {
		
		return new AndSplitImpl(generateRandomName("WovenAndSplitImpl_"));
	}
	
	public CompositeActivity createCompositeActivity(String name) {
		
		return new CompositeActivityImpl(name);
	}
	
	public CopyExpressionToExpressionActivity createCopyExpressionToExpressionActivity(String name) {
		
		return new CopyExpressionToExpressionActivityImpl(name);
	}
	
	public CopyExpressionToVariableActivity createCopyExpressionToVariableActivity(String name) {
		
		return new CopyExpressionToVariableActivityImpl(name);
	}
	
	public AtomicActivity createDummyActivity(String name) {
		
		return new AtomicActivityImpl(name);
	}
	
	public GetValueFromDictionaryActivity createGetValueFromDictionaryActivity(String name) {
		
		return new GetValueFromDictionaryActivityImpl(name);
	}
	
	public GetValueFromNestedDictionaryActivity createGetValueFromNestedDictionaryActivity(String name) {
		
		return new GetValueFromNestedDictionaryActivityImpl(name);
	}
	
	public InitializeDictionaryVariableActivity createInitializeDictionaryVariableActivity(String name) {
		
		return new InitializeDictionaryVariableActivityImpl(name);
	}
	
	public InitializeNestedDictionaryVariableActivity createInitializeNestedDictionaryVariableActivity(String name) {
		
		return new InitializeNestedDictionaryVariableActivityImpl(name);
	}
	
	public InvokeMonitoringServiceActivity createInvokeMonitoringServiceActivity(String name) {
		
		return new InvokeMonitoringServiceActivityImpl(name);
	}
	
	public RaiseErrorActivity createRaiseErrorActivity(String name) {
		
		return new RaiseErrorActivityImpl(name);
	}
	
	public Variable createVariableType(CompositeActivity compositeActivity, String variableName, String variableType, String nsPrefix, String nsUri) {
		
		return null;
	}
	
	public Variable createVariableElement(CompositeActivity compositeActivity, String variableName, String variableType, String nsPrefix, String nsUri) {
		
		return null;
	}
	
	public XorJoin createXorJoin() {
		
		return new XorJoinImpl(generateRandomName("WovenXorJoinImpl_"));
	}
	
	public XorJoin createXorJoin(String name) {
		
		return new XorJoinImpl(name);
	}
	
	public XorSplit createXorSplit() {
		
		return new XorSplitImpl(generateRandomName("WovenXorSplitImpl"));
	}
	
	public XorSplit createXorSplit(String name) {
		
		return new XorSplitImpl(name);
	}
	
	/* PROTECTED METHODS ******************************************************/
	
	protected Activity copy(Activity activity) {
		
		return activity.getCopy();
	}
	
	protected void performDataMapping(ActivityOrRegionConnector connector, Activity adviceActivity, Activity joinpoint) {
		
		// Do nothing
	}
	
	protected void performDataMapping(HierarchicalDecompositionConnector connector, Activity adviceActivity, Activity joinpoint) {
		
		// Do nothing
	}
	
	protected void performDataMapping(SyncOrSwitchBranchesConnector connector, Activity adviceActivity) {
		
		// Do nothing
	}
	
	/* PUBLIC METHODS *********************************************************/
	
	public void weave() { // TODO Implement weaving of IteratingConnector, NewBranchConnector and SwitchBranchesConnector
		
		for (Connector connector : this.composition.getConnectors()) {
			log.debug("Trying to weave connector '" + connector.getName() + "'");
			if (connector instanceof HierarchicalDecompositionConnector) {
				weave((HierarchicalDecompositionConnector) connector);
			} else if (connector instanceof BeforeConnector) {
				weave((BeforeConnector) connector);
			} else if (connector instanceof AfterConnector) {
				weave((AfterConnector) connector);
			} else if (connector instanceof AroundConnector) {
				weave((AroundConnector) connector);
			} else if (connector instanceof ParallelConnector) {
				weave((ParallelConnector) connector);
			} else if (connector instanceof AlternativeConnector) {
				weave((AlternativeConnector) connector);
			} else if (connector instanceof SyncBranchesConnector) {
				weave((SyncBranchesConnector) connector);
			} else {
				throw new RuntimeException("Cannot weave connector '" + connector.getName() + "'!");
			}
		}
	}
	
	public void weave(HierarchicalDecompositionConnector connector) {
		
		log.debug("Weaving hierarchical decomposition connector '" + connector.getName() + "'");
		
		connector.resolveCallingActivity(composition);
		Activity callingActivity = connector.getCallingActivity();
		if (callingActivity == null) { log.error("Calling activity is null!"); }
		connector.resolveCalledActivity(composition);
		Activity calledActivity = connector.getCalledActivity();
		if (calledActivity == null) { log.error("Called activity is null!"); }
		
		Activity calledActivityCopy = copy(calledActivity); // Use a /copy/ of the called activity
		
		log.debug("Calling activity is '" + callingActivity.getQualifiedName() + "', called activity is '" + calledActivityCopy.getQualifiedName() + "'");
		
		this.mainConcern.replaceActivity(callingActivity, calledActivityCopy);
		
		performDataMapping(connector, calledActivityCopy, callingActivity);
	}
	
	public void weave(BeforeConnector connector) {
		
		log.debug("Weaving before connector '" + connector.getName() + "'");
		
		connector.resolveActivity(composition); // Advice and pointcut are resolved right before weaving
		connector.resolvePointcut(composition);
		
		for (Activity joinpoint : connector.getResolvedActivityPointcut()) {
			
			Activity adviceActivity = copy(connector.getAdviceActivity());
			adviceActivity.setName(generateRandomName(adviceActivity.getName() + "_")); // Generate a new name
			log.debug("Weaving advice activity '" + adviceActivity.getName() + "' before joinpoint activity '" + joinpoint.getQualifiedName() + "'");
			CompositeActivity parent = joinpoint.getParent();
			parent.addChild(adviceActivity);
			
			Transition incomingTransition = joinpoint.getIncomingTransition();
			incomingTransition.replaceDestination(joinpoint.getControlInputPort(), adviceActivity.getControlInputPort());
			parent.connect(adviceActivity, joinpoint);
			
			performDataMapping(connector, adviceActivity, joinpoint);
		}
	}
	
	public void weave(AfterConnector connector) {
		
		log.debug("Weaving after connector '" + connector.getName() + "'");
		
		connector.resolveActivity(composition); // Advice and pointcut are resolved right before weaving
		connector.resolvePointcut(composition);
		
		for (Activity joinpoint : connector.getResolvedActivityPointcut()) {
			
			Activity adviceActivity = copy(connector.getAdviceActivity());
			adviceActivity.setName(generateRandomName(adviceActivity.getName() + "_")); // Generate a new name
			log.debug("Weaving advice activity '" + adviceActivity.getName() + "' after joinpoint activity '" + joinpoint.getQualifiedName() + "'");
			CompositeActivity parent = joinpoint.getParent();
			parent.addChild(adviceActivity);
			
			Transition outgoingTransition = joinpoint.getOutgoingTransition();
			outgoingTransition.replaceSource(joinpoint.getControlOutputPort(), adviceActivity.getControlOutputPort());
			parent.connect(joinpoint, adviceActivity);
			
			performDataMapping(connector, adviceActivity, joinpoint);
		}
	}
	
	public void weave(AroundConnector connector) {
		
		log.debug("Weaving around connector '" + connector.getName() + "'");
		
		for (Activity joinpoint : connector.getResolvedActivityPointcut()) {
			
			CompositeActivity adviceActivity = (CompositeActivity) copy(connector.getAdviceActivity()); // Get a /copy/ of the advice
			adviceActivity.setName(adviceActivity.getName().replace("$JpName$", joinpoint.getName()));
			for (Activity child : adviceActivity.getActivities()) {
				child.replaceString("$JpName$", joinpoint.getName());
			}
			log.debug("Weaving advice activity '" + adviceActivity.getName() + "' around joinpoint activity '" + joinpoint.getQualifiedName() + "'");
			CompositeActivity parent = joinpoint.getParent();
			parent.addChild(adviceActivity);
			
			Transition incomingTransition = joinpoint.getIncomingTransition();
			incomingTransition.replaceDestination(joinpoint.getControlInputPort(), adviceActivity.getControlInputPort());
			Transition outgoingTransition = joinpoint.getOutgoingTransition();
			outgoingTransition.replaceSource(joinpoint.getControlOutputPort(), adviceActivity.getControlOutputPort());
			parent.removeActivity(joinpoint);
			
			if (connector.getProceedActivity() != null) {

				// Find the copy of the proceed activity:
				FindActivitiesVisitor visitor = new FindActivitiesVisitor(new ActivityPointcut(".*\\." + connector.getProceedActivity().getQualifiedName().replace("$JpName$", joinpoint.getName()).replace(".", "\\."))); // Match the last part of the qualified activity name
				visitor.visitWorkflow(adviceActivity);
				Activity proceedActivity = null;
				for (Activity activity : visitor.getResult()) {
					if (proceedActivity == null) {
						proceedActivity = activity;
					} else {
						throw new RuntimeException("Multiple activities match the proceed activity name!");
					}
				}
				if (proceedActivity == null) {
					throw new RuntimeException("No activity matches the proceed activity name!");
				}
				
				adviceActivity.addChild(joinpoint);
				Transition proceedIncomingTransition = proceedActivity.getIncomingTransition();
				proceedIncomingTransition.replaceDestination(proceedActivity.getControlInputPort(), joinpoint.getControlInputPort());
				Transition proceedOutgoingTransition = proceedActivity.getOutgoingTransition();
				proceedOutgoingTransition.replaceSource(proceedActivity.getControlOutputPort(), joinpoint.getControlOutputPort());
				adviceActivity.removeActivity(proceedActivity);
			}
			
			// TODO Perform data mapping?
		}
	}
	
	public void weave(AlternativeConnector connector) {
		
		log.debug("Weaving alternative connector '" + connector.getName() + "'");
		
		connector.resolveActivity(composition); // Advice and pointcut are resolved right before weaving
		connector.resolvePointcut(composition);
		
		for (Activity joinpoint : connector.getResolvedActivityPointcut()) {
			
			Activity adviceActivity = copy(connector.getAdviceActivity());
			log.debug("Weaving advice activity '" + adviceActivity.getName() + "' alternative to joinpoint activity '" + joinpoint.getQualifiedName() + "'");
			CompositeActivity parent = joinpoint.getParent();
			parent.addChild(adviceActivity);
			
			XorSplit xorSplit = createXorSplit(); // FIXME
			parent.addChild(xorSplit);
			Transition incomingTransition = joinpoint.getIncomingTransition();
			incomingTransition.replaceDestination(joinpoint.getControlInputPort(), xorSplit.getControlInputPort());
			ControlOutputPort port = xorSplit.getNewControlOutputPort();
			parent.connect(port, adviceActivity.getControlInputPort());
			xorSplit.addGuard(port, connector.getCondition());
			parent.connect(xorSplit.getNewControlOutputPort(), joinpoint.getControlInputPort());
			
			XorJoin xorJoin = createXorJoin();
			xorJoin.setCorrespondingXorSplit(xorSplit);
			xorSplit.setCorrespondingXorJoin(xorJoin);
			parent.addChild(xorJoin);
			Transition outgoingTransition = joinpoint.getOutgoingTransition();
			outgoingTransition.replaceSource(joinpoint.getControlOutputPort(), xorJoin.getControlOutputPort());
			parent.connect(adviceActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
			parent.connect(joinpoint.getControlOutputPort(), xorJoin.getNewControlInputPort());
			
			performDataMapping(connector, adviceActivity, joinpoint);
		}
	}
	
	public void weave(SyncBranchesConnector connector) {
		
		log.debug("Weaving sync branches connector '" + connector.getName() + "'");
		
		connector.resolveActivity(composition); // The advice is resolved right before weaving
		
		Activity adviceActivity = copy(connector.getAdviceActivity()); // Get a /copy/ of the advice
		adviceActivity.setName(generateRandomName(adviceActivity.getName() + "_")); // Generate a new name for the advice
		
		connector.resolveSplittingControlPort(composition);
		ControlPort splittingControlPort = connector.getSplittingControlPort();
		if (splittingControlPort == null) { log.error("AND-splitting control port is null!"); }
		
		CompositeActivity parent = splittingControlPort.getParent().getParent();
		parent.addChild(adviceActivity);
		log.debug("Added advice activity '" + adviceActivity.getName() + "'");
		
		AndSplit split = createAndSplit();
		parent.addChild(split);
		
		connector.resolveJoiningControlPort(composition);
		ControlPort joiningControlPort = connector.getJoiningControlPort();
		if (joiningControlPort == null) { log.error("Synchronizing control port is null!"); }
		AndJoin join = createAndJoin();
		parent.addChild(join);
		
		split.setUnstructuredCorrespondingAndJoin(join);
		join.setUnstructuredCorrespondingAndSplit(split);
		
		if (splittingControlPort instanceof ControlInputPort) {
			log.debug("The splitting control port is a control input port...");
			ControlInputPort splittingControlInputPort = (ControlInputPort) splittingControlPort;
			Transition incomingTransition = splittingControlInputPort.getIncomingTransition();
			log.debug("The incoming transition is " + incomingTransition);
			incomingTransition.replaceDestination(splittingControlInputPort, split.getControlInputPort());
			parent.connect(split.getNewControlOutputPort(), splittingControlInputPort);
			parent.connect(split.getNewControlOutputPort(), adviceActivity.getControlInputPort());
		}
		
		if (joiningControlPort instanceof ControlInputPort) {
			log.debug("The joining control port is a control input port...");
			ControlInputPort joiningControlInputPort = (ControlInputPort) joiningControlPort;
			Transition incomingTransition = joiningControlInputPort.getIncomingTransition();
			log.debug("The incoming transition is " + incomingTransition);
			incomingTransition.replaceDestination(joiningControlInputPort, join.getNewControlInputPort());
			parent.connect(adviceActivity.getControlOutputPort(), join.getNewControlInputPort());
			parent.connect(join.getControlOutputPort(), joiningControlInputPort);
		}
		
		if (joiningControlPort instanceof ControlOutputPort) {
			log.debug("The joining control port is a control output port...");
			ControlOutputPort joiningControlOutputPort = (ControlOutputPort) joiningControlPort;
			Transition outgoingTransition = joiningControlOutputPort.getOutgoingTransition();
			log.debug("The outgoing transition is " + outgoingTransition);
			outgoingTransition.replaceSource(joiningControlOutputPort, join.getControlOutputPort());
			parent.connect(joiningControlOutputPort, join.getNewControlInputPort());
			parent.connect(adviceActivity.getControlOutputPort(), join.getNewControlInputPort());
		}
		
		if (splittingControlPort instanceof ControlOutputPort) {
			log.debug("The splitting control port is a control output port...");
			ControlOutputPort splittingControlOutputPort = (ControlOutputPort) splittingControlPort;
			Transition outgoingTransition = splittingControlOutputPort.getOutgoingTransition();
			log.debug("The outgoing transition is " + outgoingTransition);
			outgoingTransition.replaceSource(splittingControlOutputPort, split.getNewControlOutputPort());
			parent.connect(split.getNewControlOutputPort(), adviceActivity.getControlInputPort());
			parent.connect(splittingControlOutputPort, split.getControlInputPort());
			
			performDataMapping(connector, adviceActivity);
		}
	}
	
	public void weave(ParallelConnector connector) {
		
		log.debug("Weaving parallel connector '" + connector.getName() + "'");
		
		connector.resolveActivity(composition); // Advice and pointcut are resolved right before weaving
		connector.resolvePointcut(composition);
		
		for (Activity joinpoint : connector.getResolvedActivityPointcut()) {
			
			Activity adviceActivity = copy(connector.getAdviceActivity()); // Get a /copy/ of the advice
			adviceActivity.setName(generateRandomName(adviceActivity.getName() + "_")); // Generate a new name
			log.debug("Weaving advice activity '" + adviceActivity.getName() + "' parallel to joinpoint activity '" + joinpoint.getQualifiedName() + "'");
			CompositeActivity parent = joinpoint.getParent();
			parent.addChild(adviceActivity);
			
			AndSplit andSplit = createAndSplit();
			parent.addChild(andSplit);
			Transition incomingTransition = joinpoint.getIncomingTransition();
			incomingTransition.replaceDestination(joinpoint.getControlInputPort(), andSplit.getControlInputPort());
			parent.connect(andSplit.getNewControlOutputPort(), adviceActivity.getControlInputPort());
			parent.connect(andSplit.getNewControlOutputPort(), joinpoint.getControlInputPort());
			
			AndJoin andJoin = createAndJoin();
			andJoin.setCorrespondingAndSplit(andSplit);
			andSplit.setCorrespondingAndJoin(andJoin);
			parent.addChild(andJoin);
			Transition outgoingTransition = joinpoint.getOutgoingTransition();
			outgoingTransition.replaceSource(joinpoint.getControlOutputPort(), andJoin.getControlOutputPort());
			parent.connect(adviceActivity.getControlOutputPort(), andJoin.getNewControlInputPort());
			parent.connect(joinpoint.getControlOutputPort(), andJoin.getNewControlInputPort());
			
			performDataMapping(connector, adviceActivity, joinpoint);
		}
	}
}

package org.unify_framework.dsls.parental_control.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.ActivityPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.AfterConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.AlternativeConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.Connector;
import org.unify_framework.abstract_syntax.connector_mechanism.ParallelConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.QualifiedName;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToExpressionActivity;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromDictionaryActivity;
import org.unify_framework.abstract_syntax.data_perspective.InitializeDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.InvokeMonitoringServiceActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.abstract_syntax.visitors.FindActivitiesVisitor;
import org.unify_framework.dsls.parental_control.DenyUsagePolicy;
import org.unify_framework.dsls.parental_control.FilteringPolicy;
import org.unify_framework.dsls.parental_control.MonitoringPolicy;
import org.unify_framework.dsls.parental_control.ParentalControlConcern;
import org.unify_framework.dsls.parental_control.Policy;
import org.unify_framework.dsls.parental_control.ReferUsagePolicy;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ParentalControlConcernUnifyArtifactsGenerator.java 18565 2011-11-21 15:38:36Z njonchee $
 */
public class ParentalControlConcernUnifyArtifactsGenerator {

	private CompositeActivity workflow;
	private ParentalControlConcern pcc;
	private SourceCodeWeaver weaver;
	
	private List<CompositeActivity> concerns = new ArrayList<CompositeActivity>();
	private List<Connector> connectors = new ArrayList<Connector>();
	
	public Collection<CompositeActivity> getConcerns() {
		
		return concerns;
	}
	
	public Collection<Connector> getConnectors() {
		
		return connectors;
	}
	
	public void setParentalControlConcern(ParentalControlConcern pcc) {
		
		this.pcc = pcc;
	}
	
	public void setSourceCodeWeaver(SourceCodeWeaver weaver) {
		
		this.weaver = weaver;
	}
	
	public void generate() {
		
		for (Policy policy : pcc.getPolicies()) {
			switch (policy.getPolicyEnum()) {
				case FILTER:
					generateFilteringPolicy((FilteringPolicy) policy);
					break;
				case DENY_USAGE:
					generateDenyUsagePolicy((DenyUsagePolicy) policy);
					break;
				case REFER_USAGE:
					generateReferUsagePolicy((ReferUsagePolicy) policy);
					break;
				case MONITOR:
					generateMonitoringPolicy((MonitoringPolicy) policy);
					break;
				default:
					throw new NotImplementedException("This type of policy is not (yet) supported!");
			}
		}
	}
	
	public void generateFilteringPolicy(FilteringPolicy policy) {

		String jpActivityName = policy.getActivityName();
		String jpActivityNameLastIdentifier = jpActivityName.substring(jpActivityName.lastIndexOf('.') + 1, jpActivityName.length());
		
		// GENERATE UNIFY CONCERN
		
		CompositeActivity concern = this.weaver.createCompositeActivity(jpActivityNameLastIdentifier + "_PC_Filtering");

		StartEvent startEvent = concern.getStartEvent();
		
		XorSplit xorSplit = this.weaver.createXorSplit("PC_Split");
		
		AtomicActivity doNothingActivity = this.weaver.createDummyActivity("DoNothing");
		
		AtomicActivity filterActivity = this.weaver.createDummyActivity("FilterResultVariable"); // TODO Implement actual filtering activity
		
		XorJoin xorJoin = this.weaver.createXorJoin("PC_Join");
		xorSplit.setCorrespondingXorJoin(xorJoin);
		xorJoin.setCorrespondingXorSplit(xorSplit);
		
		EndEvent endEvent = concern.getEndEvent();

		concern.addChild(xorSplit);
		concern.addChild(doNothingActivity);
		concern.addChild(filterActivity);
		concern.addChild(xorJoin);
		concern.connect(startEvent.getControlOutputPort(), xorSplit.getControlInputPort());
		ControlOutputPort filterPort = xorSplit.getNewControlOutputPort();
		concern.connect(filterPort, filterActivity.getControlInputPort());
		Expression ageVariableExpression = pcc.getAgeVariableExpression();
		Expression conditionExpression = ageVariableExpression.smallerThanConstant(policy.getAge());
		xorSplit.addGuard(filterPort, conditionExpression);
		ControlOutputPort skipPort = xorSplit.getNewControlOutputPort();
		concern.connect(skipPort, doNothingActivity.getControlInputPort());
		concern.connect(doNothingActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
		concern.connect(filterActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
		concern.connect(xorJoin.getControlOutputPort(), endEvent.getControlInputPort());
		
		concerns.add(concern);
		
		// GENERATE UNIFY CONNECTOR
		
		QualifiedName adviceActivityQName = new QualifiedName();
		adviceActivityQName.addIdentifier(concern.getName());
		connectors.add(new AfterConnector(adviceActivityQName, new ActivityPointcut(jpActivityName.replace(".", "\\."))));
	}
	
	public void generateDenyUsagePolicy(DenyUsagePolicy policy) {
		
		String jpActivityName = policy.getActivityName();
		String jpActivityNameLastIdentifier = jpActivityName.substring(jpActivityName.lastIndexOf('.') + 1, jpActivityName.length());
		
		// GENERATE UNIFY CONCERN
		
		CompositeActivity concern = this.weaver.createCompositeActivity(jpActivityNameLastIdentifier + "_PC_DenyUsage");
		
		StartEvent startEvent = concern.getStartEvent();
		
		AtomicActivity doNothingActivity = this.weaver.createDummyActivity("DoNothing");
		
		EndEvent endEvent = concern.getEndEvent();

		concern.addChild(doNothingActivity);
		concern.connect(startEvent, doNothingActivity);
		concern.connect(doNothingActivity, endEvent);
		
		concerns.add(concern);
		
		// GENERATE UNIFY CONNECTOR
		
		QualifiedName adviceActivityQName = new QualifiedName();
		adviceActivityQName.addIdentifier(concern.getName());
		Expression ageVariableExpression = pcc.getAgeVariableExpression();
		Expression conditionExpression = ageVariableExpression.smallerThanConstant(policy.getAge());
		connectors.add(new AlternativeConnector(adviceActivityQName, new ActivityPointcut(jpActivityName.replace(".", "\\.")), conditionExpression));
	}
	
	public void generateReferUsagePolicy(ReferUsagePolicy policy) {
		
		String jpActivityName = policy.getActivityName();
		String jpActivityNameLastIdentifier = jpActivityName.substring(jpActivityName.lastIndexOf('.') + 1, jpActivityName.length());
		
		// Find all activities in the workflow that match the above activity name:
		Activity jpActivity = null;
		FindActivitiesVisitor visitor = new FindActivitiesVisitor(new ActivityPointcut(jpActivityName));
		visitor.visitWorkflow(this.workflow);
		for (Activity activity : visitor.getResult()) {
			if (jpActivity == null) {
				jpActivity = activity;
			} else {
				throw new RuntimeException("Expected only one joinpoint activity...");
			}
		}
		if (jpActivity == null) {
			throw new RuntimeException("Joinpoint activity could not be found...");
		}
		
		// GENERATE UNIFY CONCERN
		
		CompositeActivity concern = this.weaver.createCompositeActivity(jpActivityNameLastIdentifier + "_PC_ReferUsage");
		
		StartEvent startEvent = concern.getStartEvent();
		
		// Generate activity that initializes parents database
		InitializeDictionaryVariableActivity initializeParentsDb = this.weaver.createInitializeDictionaryVariableActivity("InitializeParentsDatabase");
		initializeParentsDb.setDictionary(pcc.getChildParentMapping());
		Variable variable = this.weaver.createVariableElement(concern, "ParentsDb", "d:dictionary", "d", BpelSerializer.DICTIONARY_NAMESPACE_URI);
		initializeParentsDb.setVariable(variable);
		
		// Generate activity that backs up username
		CopyExpressionToVariableActivity backupUsername = this.weaver.createCopyExpressionToVariableActivity("BackupUsername");
		backupUsername.setSourceExpression(policy.getUsernameVariable());
		variable = this.weaver.createVariableType(concern, "UsernameBackup", "xsd:string", "xsd", BpelSerializer.XSD_NAMESPACE);
		backupUsername.setTargetVariable(variable);
		
		GetValueFromDictionaryActivity replaceUsername = this.weaver.createGetValueFromDictionaryActivity("ReplaceUsername");
		replaceUsername.setSourceVariableName("ParentsDb");
		replaceUsername.setKey("$UsernameBackup");
		replaceUsername.setTargetExpression(policy.getUsernameVariable());
		
		// Generate a copy of the joinpoint activity
		Activity copyOfJpActivity = jpActivity.getCopy();
		
		// Generate activity that restores backed up username
		CopyExpressionToExpressionActivity restoreUsername = this.weaver.createCopyExpressionToExpressionActivity("RestoreUsername");
		restoreUsername.setSourceExpression(new XpathExpressionImpl("$UsernameBackup"));
		restoreUsername.setTargetExpression(policy.getUsernameVariable());
		
		EndEvent endEvent = concern.getEndEvent();

		concern.addChild(initializeParentsDb);
		concern.addChild(backupUsername);
		concern.addChild(replaceUsername);
		concern.addChild(copyOfJpActivity);
		concern.addChild(restoreUsername);
		concern.connect(startEvent, initializeParentsDb);
		concern.connect(initializeParentsDb, backupUsername);
		concern.connect(backupUsername, replaceUsername);
		concern.connect(replaceUsername, copyOfJpActivity);
		concern.connect(copyOfJpActivity, restoreUsername);
		concern.connect(restoreUsername, endEvent);
		
		concerns.add(concern);
		
		// GENERATE UNIFY CONNECTOR
		
		QualifiedName adviceActivityQName = new QualifiedName();
		adviceActivityQName.addIdentifier(concern.getName());
		Expression ageVariableExpression = pcc.getAgeVariableExpression();
		Expression conditionExpression = ageVariableExpression.smallerThanConstant(policy.getAge());
		connectors.add(new AlternativeConnector(adviceActivityQName, new ActivityPointcut(jpActivityName.replace(".", "\\.")), conditionExpression));
	}
	
	public void generateMonitoringPolicy(MonitoringPolicy policy) {
		
		String jpActivityName = policy.getActivityName();
		String jpActivityNameLastIdentifier = jpActivityName.substring(jpActivityName.lastIndexOf('.') + 1, jpActivityName.length());
		
		// GENERATE UNIFY CONCERN
		
		CompositeActivity concern = this.weaver.createCompositeActivity(jpActivityNameLastIdentifier + "_PC_Monitor");
		
		StartEvent startEvent = concern.getStartEvent();
		
		XorSplit xorSplit = this.weaver.createXorSplit("PC_Split");
		
		AtomicActivity doNothingActivity = this.weaver.createDummyActivity("DoNothing");
		
		InvokeMonitoringServiceActivity monitoringActivity = this.weaver.createInvokeMonitoringServiceActivity("Monitor");
		monitoringActivity.setMessage("Underage user is executing activity " + jpActivityName);
		monitoringActivity.setUsernameVariable(policy.getUsernameVariable());
		
		XorJoin xorJoin = this.weaver.createXorJoin("PC_Join");
		xorSplit.setCorrespondingXorJoin(xorJoin);
		xorJoin.setCorrespondingXorSplit(xorSplit);
		
		EndEvent endEvent = concern.getEndEvent();
		
		concern.addChild(xorSplit);
		concern.addChild(doNothingActivity);
		concern.addChild(monitoringActivity);
		concern.addChild(xorJoin);
		concern.connect(startEvent.getControlOutputPort(), xorSplit.getControlInputPort());
		ControlOutputPort monitorPort = xorSplit.getNewControlOutputPort();
		concern.connect(monitorPort, monitoringActivity.getControlInputPort());
		Expression ageVariableExpression = pcc.getAgeVariableExpression();
		Expression conditionExpression = ageVariableExpression.smallerThanConstant(policy.getAge());
		xorSplit.addGuard(monitorPort, conditionExpression);
		ControlOutputPort skipPort = xorSplit.getNewControlOutputPort();
		concern.connect(skipPort, doNothingActivity.getControlInputPort());
		concern.connect(doNothingActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
		concern.connect(monitoringActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
		concern.connect(xorJoin.getControlOutputPort(), endEvent.getControlInputPort());
		
		concerns.add(concern);
		
		// GENERATE UNIFY CONNECTOR
		
		QualifiedName adviceActivityQName = new QualifiedName();
		adviceActivityQName.addIdentifier(concern.getName());
		connectors.add(new ParallelConnector(adviceActivityQName, new ActivityPointcut(jpActivityName.replace(".", "\\."))));
	}
	
	public void setWorkflow(CompositeActivity workflow) {
		
		this.workflow = workflow;
	}
}

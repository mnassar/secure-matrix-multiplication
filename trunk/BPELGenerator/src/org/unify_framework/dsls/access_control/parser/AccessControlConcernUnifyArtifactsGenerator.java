package org.unify_framework.dsls.access_control.parser;

import java.util.HashSet;
import java.util.Set;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.ActivityPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.AroundConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.Connector;
import org.unify_framework.abstract_syntax.data_perspective.GetValueFromNestedDictionaryActivity;
import org.unify_framework.abstract_syntax.data_perspective.InitializeNestedDictionaryVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.CopyExpressionToVariableActivity;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.abstract_syntax.exception_handling_perspective.RaiseErrorActivity;
import org.unify_framework.abstract_syntax.visitors.FindActivitiesVisitor;
import org.unify_framework.dsls.access_control.AccessControlConcern;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AccessControlConcernUnifyArtifactsGenerator.java 18546 2011-11-19 10:17:26Z njonchee $
 */
public class AccessControlConcernUnifyArtifactsGenerator {
	
	private CompositeActivity workflow;
	private AccessControlConcern acc;
	private SourceCodeWeaver weaver;
	
	private CompositeActivity concern;
	private Connector connector;
	
	public AccessControlConcernUnifyArtifactsGenerator() {
		
		// Do nothing
	}
	
	public CompositeActivity getConcern() {
		
		return concern;
	}
	
	public Connector getConnector() {
		
		return connector;
	}
	
	public void setWorkflow(CompositeActivity workflow) {
		
		this.workflow = workflow;
	}
	
	public void setAccessControlConcern(AccessControlConcern acc) {
		
		this.acc = acc;
	}
	
	public void setSourceCodeWeaver(SourceCodeWeaver weaver) {
		
		this.weaver = weaver;
	}
	
	public CompositeActivity getWorkflow() {
		
		return this.workflow;
	}
	
	public AccessControlConcern getAccessControlConcern() {
		
		return this.acc;
	}
	
	public void generate() {
		
		// Retrieve the names of the activities that have at least one Deny permission associated with them:
		Set<String> jpActivityNames = this.acc.getDenyActivityNames();
		
		// Find all activities in the workflow that match the above activity names:
		Set<Activity> jpActivities = new HashSet<Activity>();
		for (String activityName : jpActivityNames) {
			FindActivitiesVisitor visitor = new FindActivitiesVisitor(new ActivityPointcut(".*\\." + activityName)); // Match the last part of the qualified activity name
			visitor.visitWorkflow(this.workflow);
			for (Activity activity : visitor.getResult()) {
				jpActivities.add(activity);
			}
		}
		
		if (!jpActivities.isEmpty()) {
			
			// GENERATE UNIFY CONCERN
			
			concern = this.weaver.createCompositeActivity("$JpName$");
			
			StartEvent startEvent = concern.getStartEvent();
			
			// Generate activity that initializes permissions database
			InitializeNestedDictionaryVariableActivity initializePermissionsDb = this.weaver.createInitializeNestedDictionaryVariableActivity("InitializePermissionsDatabase");
			initializePermissionsDb.setDictionary(acc.getActivityUsernamePermissionMapping());
			Variable variable = this.weaver.createVariableElement(concern, "PermissionsDb", "nd:nestedDictionary", "nd", BpelSerializer.NESTED_DICTIONARY_NAMESPACE_URI);
			initializePermissionsDb.setVariable(variable);
			
			// Generate activity that initializes username
			CopyExpressionToVariableActivity initializeUsername = this.weaver.createCopyExpressionToVariableActivity("InitializeUsername");
			initializeUsername.setSourceExpression(acc.getUsernameVariableExpression());
			variable = this.weaver.createVariableType(concern, "Username", "xsd:string", "xsd", BpelSerializer.XSD_NAMESPACE);
			initializeUsername.setTargetVariable(variable);
			
			GetValueFromNestedDictionaryActivity verifyPermissionsActivity = this.weaver.createGetValueFromNestedDictionaryActivity("VerifyPermissions");
			verifyPermissionsActivity.setSourceVariableName("PermissionsDb");
			verifyPermissionsActivity.setFirstKey("'$JpName$'");
			verifyPermissionsActivity.setSecondKey("$Username");
			variable = this.weaver.createVariableType(concern, "Action", "xsd:string", "xsd", BpelSerializer.XSD_NAMESPACE);
			verifyPermissionsActivity.setTargetVariableName(variable.getName());
			
			XorSplit xorSplit = this.weaver.createXorSplit("AC_Split");

			AtomicActivity doNothingActivity = this.weaver.createDummyActivity("DoNothing");
			
			RaiseErrorActivity raiseErrorActivity = this.weaver.createRaiseErrorActivity("RaiseError");
			raiseErrorActivity.setErrorName("PermissionDenied");
			
			AtomicActivity proceedActivity = this.weaver.createDummyActivity("Proceed");
			
			XorJoin xorJoin = this.weaver.createXorJoin("AC_Join");
			xorSplit.setCorrespondingXorJoin(xorJoin);
			xorJoin.setCorrespondingXorSplit(xorSplit);
			
			EndEvent endEvent = concern.getEndEvent();

			concern.addChild(initializePermissionsDb);
			concern.addChild(initializeUsername);
			concern.addChild(verifyPermissionsActivity);
			concern.addChild(xorSplit);
			concern.addChild(doNothingActivity);
			concern.addChild(raiseErrorActivity);
			concern.addChild(proceedActivity); // This will be the proceed activity
			concern.addChild(xorJoin);
			concern.connect(startEvent, initializePermissionsDb);
			concern.connect(initializePermissionsDb, initializeUsername);
			concern.connect(initializeUsername, verifyPermissionsActivity);
			concern.connect(verifyPermissionsActivity.getControlOutputPort(), xorSplit.getControlInputPort());
			ControlOutputPort skipPort = xorSplit.getNewControlOutputPort();
			concern.connect(skipPort, doNothingActivity.getControlInputPort());
			xorSplit.addGuard(skipPort, new XpathExpressionImpl("$Action='DenyBySkipping'"));
			ControlOutputPort raiseErrorPort = xorSplit.getNewControlOutputPort();
			concern.connect(raiseErrorPort, raiseErrorActivity.getControlInputPort());
			xorSplit.addGuard(raiseErrorPort, new XpathExpressionImpl("$Action='DenyByRaisingError'"));
			concern.connect(xorSplit.getNewControlOutputPort(), proceedActivity.getControlInputPort());
			concern.connect(doNothingActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
			concern.connect(raiseErrorActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
			concern.connect(proceedActivity.getControlOutputPort(), xorJoin.getNewControlInputPort());
			concern.connect(xorJoin.getControlOutputPort(), endEvent.getControlInputPort());
			
			// Generate Unify connector:
			connector = new AroundConnector(concern, proceedActivity, jpActivities);
		}
	}
}

tree grammar ConnectorTree;

options {
	ASTLabelType=CommonTree;
	output=AST;
	tokenVocab=Connector;
}

@header {
	package org.unify_framework.abstract_syntax.connector_mechanism.parser;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	
	import org.unify_framework.abstract_syntax.connector_mechanism.ActivityPointcut;
	import org.unify_framework.abstract_syntax.connector_mechanism.AfterConnector;
	import org.unify_framework.abstract_syntax.connector_mechanism.AlternativeConnector;
	import org.unify_framework.abstract_syntax.connector_mechanism.AtomicActivityPointcut;
	import org.unify_framework.abstract_syntax.connector_mechanism.CompositeActivityPointcut;
	import org.unify_framework.abstract_syntax.connector_mechanism.Connector;
	import org.unify_framework.abstract_syntax.connector_mechanism.ControlInputPortPointcut;
	import org.unify_framework.abstract_syntax.connector_mechanism.ControlOutputPortPointcut;
	import org.unify_framework.abstract_syntax.connector_mechanism.ControlPortPointcut;
	import org.unify_framework.abstract_syntax.connector_mechanism.DataMapping;
	import org.unify_framework.abstract_syntax.connector_mechanism.HierarchicalDecompositionConnector;
	import org.unify_framework.abstract_syntax.connector_mechanism.ParallelConnector;
	import org.unify_framework.abstract_syntax.connector_mechanism.QualifiedName;
	import org.unify_framework.abstract_syntax.connector_mechanism.SyncBranchesConnector;
}

@members {
	private static final Log log = LogFactory.getLog(ConnectorTree.class);
	
	private Connector connector;
	private QualifiedName callingActivity;
	private QualifiedName calledActivity;
	private QualifiedName adviceActivity;
	private ActivityPointcut activityPointcut;
	private List<DataMapping> dataMappings;
	private List<DataMapping.Variable> variableExpressions;
	private ControlPortPointcut splittingControlPortPointcut;
	private ControlPortPointcut joiningControlPortPointcut;
	private ControlPortPointcut controlPortPointcut;
	private List<QualifiedName> qualifiedNames;
	
	public Connector getConnector() {
		
		return connector;
	}
}

connector
	: hierarchical_decomposition_connector
	| inversion_of_control_connector
	;

hierarchical_decomposition_connector
	: ^(HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity data_mapping*) {
			log.debug("Parsing hierarchical decomposition connector...");
			this.connector = new HierarchicalDecompositionConnector(this.callingActivity, this.calledActivity, this.dataMappings);
			log.debug("Parsed hierarchical decomposition connector '" + this.connector + "'");
		}
	;

calling_activity
	: ^(CALLING_ACTIVITY qualified_name) {
			this.callingActivity = this.qualifiedNames.get(0);
			this.qualifiedNames = null;
		}
	;

called_activity
	: ^(CALLED_ACTIVITY qualified_name) {
			this.calledActivity = this.qualifiedNames.get(0);
			this.qualifiedNames = null;
		}
	;

inversion_of_control_connector
	: after_connector
	| parallel_connector
	| alternative_connector
	| sync_branches_connector
	;

after_connector
	: ^(AFTER_CONNECTOR advice_activity activity_pointcut data_mapping*) {
			log.debug("Parsing after connector...");
			this.connector = new AfterConnector(this.adviceActivity, this.activityPointcut, this.dataMappings);
			log.debug("Parsed after connector '" + this.connector + "'");
		}
	;

parallel_connector
	: ^(PARALLEL_CONNECTOR advice_activity activity_pointcut data_mapping*) {
			log.debug("Parsing parallel connector...");
			this.connector = new ParallelConnector(this.adviceActivity, this.activityPointcut, this.dataMappings);
			log.debug("Parsed parallel connector '" + this.connector + "'");
		}
	;

alternative_connector
	: ^(ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING data_mapping*) {
			log.debug("Parsing alternative connector...");
			String guard = $STRING.getText();
			guard = guard.substring(1, guard.length() - 1);
			this.connector = new AlternativeConnector(this.adviceActivity, this.activityPointcut, guard, this.dataMappings);
			log.debug("Parsed alternative connector '" + this.connector + "'");
		}
	;

sync_branches_connector
	: ^(SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut data_mapping*) {
			log.debug("Parsing sync branches connector...");
			this.connector = new SyncBranchesConnector(this.adviceActivity, this.splittingControlPortPointcut, this.joiningControlPortPointcut, this.dataMappings);
			log.debug("Parsed sync branches connector '" + this.connector + "'");
		}
	;
	
advice_activity
	: ^(ADVICE_ACTIVITY qualified_name) {
			this.adviceActivity = this.qualifiedNames.get(0);
			this.qualifiedNames = null;
		}
	;

activity_pointcut
	: ^(ACTIVITY_POINTCUT ^(EXECUTING_ACTIVITY STRING)) {
			log.debug("Parsing activity pointcut...");
			String expression = $STRING.getText();
			expression = expression.substring(1, expression.length() - 1);
			this.activityPointcut = new ActivityPointcut(expression);
			log.debug("Parsed activity pointcut '" + this.activityPointcut + "'");
		}
	| ^(ACTIVITY_POINTCUT ^(EXECUTING_COMPOSITE_ACTIVITY STRING)) {
			log.debug("Parsing composite activity pointcut...");
			String expression = $STRING.getText();
			expression = expression.substring(1, expression.length() - 1);
			this.activityPointcut = new CompositeActivityPointcut(expression);
			log.debug("Parsed composite activity pointcut '" + this.activityPointcut + "'");
		}
	| ^(ACTIVITY_POINTCUT ^(EXECUTING_ATOMIC_ACTIVITY STRING)) {
			log.debug("Parsing atomic activity pointcut...");
			String expression = $STRING.getText();
			expression = expression.substring(1, expression.length() - 1);
			this.activityPointcut = new AtomicActivityPointcut(expression);
			log.debug("Parsed atomic activity pointcut '" + this.activityPointcut + "'");
		}
	;

data_mapping
	: ^(DATA_MAPPING variable_expression STRING) {
			log.debug("Parsing data mapping of the form '^(DATA_MAPPING variable_expression STRING)'...");
			String stringConstant = $STRING.getText();
			stringConstant = stringConstant.substring(1, stringConstant.length() - 1);
			if (this.dataMappings == null) {
				this.dataMappings = new ArrayList<DataMapping>();
			}
			this.dataMappings.add(new DataMapping(this.variableExpressions.get(0), stringConstant));
			this.variableExpressions = null;
			log.debug("Parsed data mapping");
		}
	| ^(DATA_MAPPING variable_expression variable_expression) {
			log.debug("Parsing data mapping of the form '^(DATA_MAPPING variable_expression variable_expression)'...");
			if (this.dataMappings == null) {
				this.dataMappings = new ArrayList<DataMapping>();
			}
			this.dataMappings.add(new DataMapping(this.variableExpressions.get(0), this.variableExpressions.get(1)));
			this.variableExpressions = null;
			log.debug("Parsed data mapping");
		}
	;

variable_expression
	: ^(MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING1=STRING STRING2=STRING STRING3=STRING) {
			log.debug("Parsing message type variable...");
			String query = $STRING1.getText();
			query = query.substring(1, query.length() - 1);
			String queryNamespacePrefix = $STRING2.getText();
			queryNamespacePrefix = queryNamespacePrefix.substring(1, queryNamespacePrefix.length() - 1);
			String queryNamespace = $STRING3.getText();
			queryNamespace = queryNamespace.substring(1, queryNamespace.length() - 1);
			if (this.variableExpressions == null) {
				this.variableExpressions = new ArrayList<DataMapping.Variable>();
			}
			this.variableExpressions.add(new DataMapping.MessageTypeVariable(this.qualifiedNames.get(0), this.qualifiedNames.get(1), query, queryNamespacePrefix, queryNamespace));
			this.qualifiedNames = null;
			log.debug("Parsed message type variable");
		}
	| ^(TYPE_VARIABLE qualified_name STRING1=STRING STRING2=STRING STRING3=STRING) {
			log.debug("Parsing type variable...");
			String query = $STRING1.getText();
			query = query.substring(1, query.length() - 1);
			String queryNamespacePrefix = $STRING2.getText();
			queryNamespacePrefix = queryNamespacePrefix.substring(1, queryNamespacePrefix.length() - 1);
			String queryNamespace = $STRING3.getText();
			queryNamespace = queryNamespace.substring(1, queryNamespace.length() - 1);
			if (this.variableExpressions == null) {
				this.variableExpressions = new ArrayList<DataMapping.Variable>();
			}
			this.variableExpressions.add(new DataMapping.TypeVariable(this.qualifiedNames.get(0), query, queryNamespacePrefix, queryNamespace));
			this.qualifiedNames = null;
			log.debug("Parsed type variable");
		}
	;

splitting_control_port_pointcut
	: control_port_pointcut {
			this.splittingControlPortPointcut = this.controlPortPointcut;
		}
	;

joining_control_port_pointcut
	: control_port_pointcut {
			this.joiningControlPortPointcut = this.controlPortPointcut;
		}
	;

control_port_pointcut
	: ^(CONTROL_PORT_POINTCUT ^(CONTROL_PORT qualified_name)) {
			log.debug("Parsing control port pointcut...");
			this.controlPortPointcut = new ControlPortPointcut(this.qualifiedNames.get(0));
			this.qualifiedNames = null;
			log.debug("Parsed control port pointcut '" + this.controlPortPointcut + "'");
		}
	| ^(CONTROL_PORT_POINTCUT ^(CONTROL_INPUT_PORT qualified_name)) {
			log.debug("Parsing control input port pointcut...");
			this.controlPortPointcut = new ControlInputPortPointcut(this.qualifiedNames.get(0));
			this.qualifiedNames = null;
			log.debug("Parsed control input port pointcut '" + this.controlPortPointcut + "'");
		}
	| ^(CONTROL_PORT_POINTCUT ^(CONTROL_OUTPUT_PORT qualified_name)) {
			log.debug("Parsing control output port pointcut...");
			this.controlPortPointcut = new ControlOutputPortPointcut(this.qualifiedNames.get(0));
			this.qualifiedNames = null;
			log.debug("Parsed control output port pointcut '" + this.controlPortPointcut + "'");
		}
	;

qualified_name
	: ^(QUALIFIED_NAME (identifiers+=IDENTIFIER)*) {
			log.debug("Parsing qualified name...");
			QualifiedName qualifiedName = new QualifiedName();
			for (Object identifier : $identifiers) {
				qualifiedName.addIdentifier(((CommonTree) identifier).getText());
			}
			if (this.qualifiedNames == null) {
				this.qualifiedNames = new ArrayList<QualifiedName>();
			}
			this.qualifiedNames.add(qualifiedName);
			log.debug("Parsed qualified name '" + qualifiedName + "'");
		}
	;

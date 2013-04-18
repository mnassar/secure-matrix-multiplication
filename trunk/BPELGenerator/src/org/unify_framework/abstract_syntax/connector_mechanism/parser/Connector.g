grammar Connector;

options {
	output=AST;
}

tokens {
	HIERARCHICAL_DECOMPOSITION_CONNECTOR='hierarchical_decomposition_connector';
	CALLING_ACTIVITY='calling_activity';
	CALLED_ACTIVITY='called_activity';
	BEFORE_CONNECTOR='before_connector';
	AFTER_CONNECTOR='after_connector';
	AROUND_CONNECTOR='around_connector';
	PARALLEL_CONNECTOR='parallel_connector';
	ALTERNATIVE_CONNECTOR='alternative_connector';
	ITERATING_CONNECTOR='iterating_connector';
	NEW_BRANCH_CONNECTOR='new_branch_connector';
	SYNC_BRANCHES_CONNECTOR='sync_branches_connector';
	SWITCH_BRANCHES_CONNECTOR='switch_branches_connector';
	ADVICE_ACTIVITY='advice_activity';
	ACTIVITY_POINTCUT='activity_pointcut';
	EXECUTING_ACTIVITY='executing_activity';
	EXECUTING_COMPOSITE_ACTIVITY='executing_composite_activity';
	EXECUTING_ATOMIC_ACTIVITY='executing_atomic_activity';
	DATA_MAPPING='data_mapping';
	MESSAGE_TYPE_VARIABLE='message_type_variable';
	TYPE_VARIABLE='type_variable';
	SPLIT_POINTCUT='split_pointcut';
	SPLIT='split';
	ANDSPLIT='andsplit';
	XORSPLIT='xorsplit';
	CONTROL_PORT_POINTCUT='control_port_pointcut';
	CONTROL_PORT='control_port';
	CONTROL_INPUT_PORT='control_input_port';
	CONTROL_OUTPUT_PORT='control_output_port';
	QUALIFIED_NAME='qualified_name';
}

// applies only to the parser:
@header { package org.unify_framework.abstract_syntax.connector_mechanism.parser; }

// applies only to the lexer:
@lexer::header { package org.unify_framework.abstract_syntax.connector_mechanism.parser; }

// = PARSER RULES ==============================================================

connector
	: hierarchical_decomposition_connector
	| inversion_of_control_connector
	;

// - HIERARCHICAL DECOMPOSITION CONNECTOR

hierarchical_decomposition_connector
	: 'CONNECT' calling_activity 'TO' called_activity ('WITH' data_mapping (',' data_mapping)*)? -> ^(HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity data_mapping*)
	;

calling_activity
	: qualified_name -> ^(CALLING_ACTIVITY qualified_name)
	;

called_activity
	: qualified_name -> ^(CALLED_ACTIVITY qualified_name)
	;

// - INVERSION OF CONTROL CONNECTOR

inversion_of_control_connector
	: before_connector
	| after_connector
	| around_connector
	| parallel_connector
	| alternative_connector
	| iterating_connector
	| new_branch_connector
	| sync_branches_connector
	| switch_branches_connector
	;

before_connector
	: 'CONNECT' advice_activity 'BEFORE' activity_pointcut -> ^(BEFORE_CONNECTOR advice_activity activity_pointcut)
	;

after_connector
	: 'CONNECT' advice_activity 'AFTER' activity_pointcut ('WITH' data_mapping (',' data_mapping)*)? -> ^(AFTER_CONNECTOR advice_activity activity_pointcut data_mapping*)
	;

around_connector
	: 'CONNECT' advice_activity 'AROUND' activity_pointcut -> ^(AROUND_CONNECTOR advice_activity activity_pointcut)
	;

parallel_connector
	: 'CONNECT' advice_activity 'PARALLEL TO' activity_pointcut ('WITH' data_mapping (',' data_mapping)*)? -> ^(PARALLEL_CONNECTOR advice_activity activity_pointcut data_mapping*)
	;

alternative_connector
	: 'CONNECT' advice_activity 'ALTERNATIVE TO' activity_pointcut 'IF' STRING ('WITH' data_mapping (',' data_mapping)*)? -> ^(ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING data_mapping*)
	;

iterating_connector
	: 'CONNECT' advice_activity 'ITERATING OVER' activity_pointcut 'IF' STRING ('WITH' data_mapping (',' data_mapping)*)? -> ^(ITERATING_CONNECTOR advice_activity activity_pointcut STRING data_mapping*)
	;

new_branch_connector
	: 'CONNECT' advice_activity 'IN' split_pointcut -> ^(NEW_BRANCH_CONNECTOR advice_activity split_pointcut)
	;

sync_branches_connector
	: 'CONNECT' advice_activity 'AND-SPLITTING AT' splitting_control_port_pointcut 'SYNCHRONIZING AT' joining_control_port_pointcut ('WITH' data_mapping (',' data_mapping)*)? -> ^(SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut data_mapping*)
	;

switch_branches_connector
	: 'CONNECT' advice_activity 'SWITCHING AT' splitting_control_port_pointcut 'XOR-JOINING AT' joining_control_port_pointcut -> ^(SWITCH_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut)
	;

advice_activity
	: qualified_name -> ^(ADVICE_ACTIVITY qualified_name)
	;

activity_pointcut
	: 'executingactivity(' STRING ')' -> ^(ACTIVITY_POINTCUT ^(EXECUTING_ACTIVITY STRING))
	| 'executingcompositeactivity(' STRING ')' -> ^(ACTIVITY_POINTCUT ^(EXECUTING_COMPOSITE_ACTIVITY STRING))
	| 'executingatomicactivity(' STRING ')' -> ^(ACTIVITY_POINTCUT ^(EXECUTING_ATOMIC_ACTIVITY STRING))
	;

data_mapping
	: variable_expression '=' STRING -> ^(DATA_MAPPING variable_expression STRING)
	| variable_expression '=' variable_expression -> ^(DATA_MAPPING variable_expression variable_expression)
	;

variable_expression
	: 'messageTypeVariable(' qualified_name ',' qualified_name ',' STRING ',' STRING ',' STRING ')' -> ^(MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING STRING STRING)
	| 'typeVariable(' qualified_name ',' STRING ',' STRING ',' STRING ')' -> ^(TYPE_VARIABLE qualified_name STRING STRING STRING)
	;

split_pointcut
	: 'split(' STRING ')' -> ^(SPLIT_POINTCUT ^(SPLIT STRING))
	| 'andsplit(' STRING ')' -> ^(SPLIT_POINTCUT ^(ANDSPLIT STRING))
	| 'xorsplit(' STRING ')' -> ^(SPLIT_POINTCUT ^(XORSPLIT STRING))
	;

splitting_control_port_pointcut
	: control_port_pointcut
	;

joining_control_port_pointcut
	: control_port_pointcut
	;

control_port_pointcut
	: 'controlport(' qualified_name ')' -> ^(CONTROL_PORT_POINTCUT ^(CONTROL_PORT qualified_name))
	| 'controlinputport(' qualified_name ')' -> ^(CONTROL_PORT_POINTCUT ^(CONTROL_INPUT_PORT qualified_name))
	| 'controloutputport(' qualified_name ')' -> ^(CONTROL_PORT_POINTCUT ^(CONTROL_OUTPUT_PORT qualified_name))
	;

// - GENERAL RULES

qualified_name
	: IDENTIFIER ('.' IDENTIFIER)* -> ^(QUALIFIED_NAME IDENTIFIER*)
	;

// = LEXER RULES ===============================================================

STRING
	: '"' (~('"'))* '"'
	;

IDENTIFIER
	: ('A'..'Z'|'a'..'z') ('A'..'Z'|'a'..'z'|'_'|'0'..'9')*
	;

WHITESPACE
	: (' '|'\r'|'\n'|'\t')+ { skip(); }
	;

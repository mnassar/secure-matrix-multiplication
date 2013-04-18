// $ANTLR 3.3 Nov 30, 2010 12:45:30 /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g 2011-10-28 15:48:18
 package org.unify_framework.abstract_syntax.connector_mechanism.parser; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ConnectorLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int HIERARCHICAL_DECOMPOSITION_CONNECTOR=4;
    public static final int CALLING_ACTIVITY=5;
    public static final int CALLED_ACTIVITY=6;
    public static final int BEFORE_CONNECTOR=7;
    public static final int AFTER_CONNECTOR=8;
    public static final int AROUND_CONNECTOR=9;
    public static final int PARALLEL_CONNECTOR=10;
    public static final int ALTERNATIVE_CONNECTOR=11;
    public static final int ITERATING_CONNECTOR=12;
    public static final int NEW_BRANCH_CONNECTOR=13;
    public static final int SYNC_BRANCHES_CONNECTOR=14;
    public static final int SWITCH_BRANCHES_CONNECTOR=15;
    public static final int ADVICE_ACTIVITY=16;
    public static final int ACTIVITY_POINTCUT=17;
    public static final int EXECUTING_ACTIVITY=18;
    public static final int EXECUTING_COMPOSITE_ACTIVITY=19;
    public static final int EXECUTING_ATOMIC_ACTIVITY=20;
    public static final int DATA_MAPPING=21;
    public static final int MESSAGE_TYPE_VARIABLE=22;
    public static final int TYPE_VARIABLE=23;
    public static final int SPLIT_POINTCUT=24;
    public static final int SPLIT=25;
    public static final int ANDSPLIT=26;
    public static final int XORSPLIT=27;
    public static final int CONTROL_PORT_POINTCUT=28;
    public static final int CONTROL_PORT=29;
    public static final int CONTROL_INPUT_PORT=30;
    public static final int CONTROL_OUTPUT_PORT=31;
    public static final int QUALIFIED_NAME=32;
    public static final int STRING=33;
    public static final int IDENTIFIER=34;
    public static final int WHITESPACE=35;

    // delegates
    // delegators

    public ConnectorLexer() {;} 
    public ConnectorLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ConnectorLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g"; }

    // $ANTLR start "HIERARCHICAL_DECOMPOSITION_CONNECTOR"
    public final void mHIERARCHICAL_DECOMPOSITION_CONNECTOR() throws RecognitionException {
        try {
            int _type = HIERARCHICAL_DECOMPOSITION_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:5:38: ( 'hierarchical_decomposition_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:5:40: 'hierarchical_decomposition_connector'
            {
            match("hierarchical_decomposition_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HIERARCHICAL_DECOMPOSITION_CONNECTOR"

    // $ANTLR start "CALLING_ACTIVITY"
    public final void mCALLING_ACTIVITY() throws RecognitionException {
        try {
            int _type = CALLING_ACTIVITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:6:18: ( 'calling_activity' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:6:20: 'calling_activity'
            {
            match("calling_activity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CALLING_ACTIVITY"

    // $ANTLR start "CALLED_ACTIVITY"
    public final void mCALLED_ACTIVITY() throws RecognitionException {
        try {
            int _type = CALLED_ACTIVITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:7:17: ( 'called_activity' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:7:19: 'called_activity'
            {
            match("called_activity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CALLED_ACTIVITY"

    // $ANTLR start "BEFORE_CONNECTOR"
    public final void mBEFORE_CONNECTOR() throws RecognitionException {
        try {
            int _type = BEFORE_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:8:18: ( 'before_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:8:20: 'before_connector'
            {
            match("before_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BEFORE_CONNECTOR"

    // $ANTLR start "AFTER_CONNECTOR"
    public final void mAFTER_CONNECTOR() throws RecognitionException {
        try {
            int _type = AFTER_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:9:17: ( 'after_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:9:19: 'after_connector'
            {
            match("after_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AFTER_CONNECTOR"

    // $ANTLR start "AROUND_CONNECTOR"
    public final void mAROUND_CONNECTOR() throws RecognitionException {
        try {
            int _type = AROUND_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:10:18: ( 'around_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:10:20: 'around_connector'
            {
            match("around_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AROUND_CONNECTOR"

    // $ANTLR start "PARALLEL_CONNECTOR"
    public final void mPARALLEL_CONNECTOR() throws RecognitionException {
        try {
            int _type = PARALLEL_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:11:20: ( 'parallel_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:11:22: 'parallel_connector'
            {
            match("parallel_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARALLEL_CONNECTOR"

    // $ANTLR start "ALTERNATIVE_CONNECTOR"
    public final void mALTERNATIVE_CONNECTOR() throws RecognitionException {
        try {
            int _type = ALTERNATIVE_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:12:23: ( 'alternative_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:12:25: 'alternative_connector'
            {
            match("alternative_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALTERNATIVE_CONNECTOR"

    // $ANTLR start "ITERATING_CONNECTOR"
    public final void mITERATING_CONNECTOR() throws RecognitionException {
        try {
            int _type = ITERATING_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:13:21: ( 'iterating_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:13:23: 'iterating_connector'
            {
            match("iterating_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ITERATING_CONNECTOR"

    // $ANTLR start "NEW_BRANCH_CONNECTOR"
    public final void mNEW_BRANCH_CONNECTOR() throws RecognitionException {
        try {
            int _type = NEW_BRANCH_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:14:22: ( 'new_branch_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:14:24: 'new_branch_connector'
            {
            match("new_branch_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEW_BRANCH_CONNECTOR"

    // $ANTLR start "SYNC_BRANCHES_CONNECTOR"
    public final void mSYNC_BRANCHES_CONNECTOR() throws RecognitionException {
        try {
            int _type = SYNC_BRANCHES_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:15:25: ( 'sync_branches_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:15:27: 'sync_branches_connector'
            {
            match("sync_branches_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SYNC_BRANCHES_CONNECTOR"

    // $ANTLR start "SWITCH_BRANCHES_CONNECTOR"
    public final void mSWITCH_BRANCHES_CONNECTOR() throws RecognitionException {
        try {
            int _type = SWITCH_BRANCHES_CONNECTOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:16:27: ( 'switch_branches_connector' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:16:29: 'switch_branches_connector'
            {
            match("switch_branches_connector"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SWITCH_BRANCHES_CONNECTOR"

    // $ANTLR start "ADVICE_ACTIVITY"
    public final void mADVICE_ACTIVITY() throws RecognitionException {
        try {
            int _type = ADVICE_ACTIVITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:17:17: ( 'advice_activity' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:17:19: 'advice_activity'
            {
            match("advice_activity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ADVICE_ACTIVITY"

    // $ANTLR start "ACTIVITY_POINTCUT"
    public final void mACTIVITY_POINTCUT() throws RecognitionException {
        try {
            int _type = ACTIVITY_POINTCUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:18:19: ( 'activity_pointcut' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:18:21: 'activity_pointcut'
            {
            match("activity_pointcut"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ACTIVITY_POINTCUT"

    // $ANTLR start "EXECUTING_ACTIVITY"
    public final void mEXECUTING_ACTIVITY() throws RecognitionException {
        try {
            int _type = EXECUTING_ACTIVITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:19:20: ( 'executing_activity' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:19:22: 'executing_activity'
            {
            match("executing_activity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXECUTING_ACTIVITY"

    // $ANTLR start "EXECUTING_COMPOSITE_ACTIVITY"
    public final void mEXECUTING_COMPOSITE_ACTIVITY() throws RecognitionException {
        try {
            int _type = EXECUTING_COMPOSITE_ACTIVITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:20:30: ( 'executing_composite_activity' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:20:32: 'executing_composite_activity'
            {
            match("executing_composite_activity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXECUTING_COMPOSITE_ACTIVITY"

    // $ANTLR start "EXECUTING_ATOMIC_ACTIVITY"
    public final void mEXECUTING_ATOMIC_ACTIVITY() throws RecognitionException {
        try {
            int _type = EXECUTING_ATOMIC_ACTIVITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:21:27: ( 'executing_atomic_activity' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:21:29: 'executing_atomic_activity'
            {
            match("executing_atomic_activity"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXECUTING_ATOMIC_ACTIVITY"

    // $ANTLR start "DATA_MAPPING"
    public final void mDATA_MAPPING() throws RecognitionException {
        try {
            int _type = DATA_MAPPING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:22:14: ( 'data_mapping' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:22:16: 'data_mapping'
            {
            match("data_mapping"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATA_MAPPING"

    // $ANTLR start "MESSAGE_TYPE_VARIABLE"
    public final void mMESSAGE_TYPE_VARIABLE() throws RecognitionException {
        try {
            int _type = MESSAGE_TYPE_VARIABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:23:23: ( 'message_type_variable' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:23:25: 'message_type_variable'
            {
            match("message_type_variable"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MESSAGE_TYPE_VARIABLE"

    // $ANTLR start "TYPE_VARIABLE"
    public final void mTYPE_VARIABLE() throws RecognitionException {
        try {
            int _type = TYPE_VARIABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:24:15: ( 'type_variable' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:24:17: 'type_variable'
            {
            match("type_variable"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TYPE_VARIABLE"

    // $ANTLR start "SPLIT_POINTCUT"
    public final void mSPLIT_POINTCUT() throws RecognitionException {
        try {
            int _type = SPLIT_POINTCUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:25:16: ( 'split_pointcut' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:25:18: 'split_pointcut'
            {
            match("split_pointcut"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPLIT_POINTCUT"

    // $ANTLR start "SPLIT"
    public final void mSPLIT() throws RecognitionException {
        try {
            int _type = SPLIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:26:7: ( 'split' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:26:9: 'split'
            {
            match("split"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPLIT"

    // $ANTLR start "ANDSPLIT"
    public final void mANDSPLIT() throws RecognitionException {
        try {
            int _type = ANDSPLIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:27:10: ( 'andsplit' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:27:12: 'andsplit'
            {
            match("andsplit"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANDSPLIT"

    // $ANTLR start "XORSPLIT"
    public final void mXORSPLIT() throws RecognitionException {
        try {
            int _type = XORSPLIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:28:10: ( 'xorsplit' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:28:12: 'xorsplit'
            {
            match("xorsplit"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "XORSPLIT"

    // $ANTLR start "CONTROL_PORT_POINTCUT"
    public final void mCONTROL_PORT_POINTCUT() throws RecognitionException {
        try {
            int _type = CONTROL_PORT_POINTCUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:29:23: ( 'control_port_pointcut' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:29:25: 'control_port_pointcut'
            {
            match("control_port_pointcut"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTROL_PORT_POINTCUT"

    // $ANTLR start "CONTROL_PORT"
    public final void mCONTROL_PORT() throws RecognitionException {
        try {
            int _type = CONTROL_PORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:30:14: ( 'control_port' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:30:16: 'control_port'
            {
            match("control_port"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTROL_PORT"

    // $ANTLR start "CONTROL_INPUT_PORT"
    public final void mCONTROL_INPUT_PORT() throws RecognitionException {
        try {
            int _type = CONTROL_INPUT_PORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:31:20: ( 'control_input_port' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:31:22: 'control_input_port'
            {
            match("control_input_port"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTROL_INPUT_PORT"

    // $ANTLR start "CONTROL_OUTPUT_PORT"
    public final void mCONTROL_OUTPUT_PORT() throws RecognitionException {
        try {
            int _type = CONTROL_OUTPUT_PORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:32:21: ( 'control_output_port' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:32:23: 'control_output_port'
            {
            match("control_output_port"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTROL_OUTPUT_PORT"

    // $ANTLR start "QUALIFIED_NAME"
    public final void mQUALIFIED_NAME() throws RecognitionException {
        try {
            int _type = QUALIFIED_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:33:16: ( 'qualified_name' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:33:18: 'qualified_name'
            {
            match("qualified_name"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUALIFIED_NAME"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:34:7: ( 'CONNECT' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:34:9: 'CONNECT'
            {
            match("CONNECT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:35:7: ( 'TO' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:35:9: 'TO'
            {
            match("TO"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:36:7: ( 'WITH' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:36:9: 'WITH'
            {
            match("WITH"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:37:7: ( ',' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:37:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:38:7: ( 'BEFORE' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:38:9: 'BEFORE'
            {
            match("BEFORE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:39:7: ( 'AFTER' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:39:9: 'AFTER'
            {
            match("AFTER"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:40:7: ( 'AROUND' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:40:9: 'AROUND'
            {
            match("AROUND"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:41:7: ( 'PARALLEL TO' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:41:9: 'PARALLEL TO'
            {
            match("PARALLEL TO"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:42:7: ( 'ALTERNATIVE TO' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:42:9: 'ALTERNATIVE TO'
            {
            match("ALTERNATIVE TO"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:43:7: ( 'IF' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:43:9: 'IF'
            {
            match("IF"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:44:7: ( 'ITERATING OVER' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:44:9: 'ITERATING OVER'
            {
            match("ITERATING OVER"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:45:7: ( 'IN' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:45:9: 'IN'
            {
            match("IN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:46:7: ( 'AND-SPLITTING AT' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:46:9: 'AND-SPLITTING AT'
            {
            match("AND-SPLITTING AT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:47:7: ( 'SYNCHRONIZING AT' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:47:9: 'SYNCHRONIZING AT'
            {
            match("SYNCHRONIZING AT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:48:7: ( 'SWITCHING AT' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:48:9: 'SWITCHING AT'
            {
            match("SWITCHING AT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:49:7: ( 'XOR-JOINING AT' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:49:9: 'XOR-JOINING AT'
            {
            match("XOR-JOINING AT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:50:7: ( 'executingactivity(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:50:9: 'executingactivity('
            {
            match("executingactivity("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:51:7: ( ')' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:51:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:52:7: ( 'executingcompositeactivity(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:52:9: 'executingcompositeactivity('
            {
            match("executingcompositeactivity("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:53:7: ( 'executingatomicactivity(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:53:9: 'executingatomicactivity('
            {
            match("executingatomicactivity("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:54:7: ( '=' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:54:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:7: ( 'messageTypeVariable(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:9: 'messageTypeVariable('
            {
            match("messageTypeVariable("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:56:7: ( 'typeVariable(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:56:9: 'typeVariable('
            {
            match("typeVariable("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:57:7: ( 'split(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:57:9: 'split('
            {
            match("split("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:58:7: ( 'andsplit(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:58:9: 'andsplit('
            {
            match("andsplit("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:59:7: ( 'xorsplit(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:59:9: 'xorsplit('
            {
            match("xorsplit("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:60:7: ( 'controlport(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:60:9: 'controlport('
            {
            match("controlport("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:61:7: ( 'controlinputport(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:61:9: 'controlinputport('
            {
            match("controlinputport("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:62:7: ( 'controloutputport(' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:62:9: 'controloutputport('
            {
            match("controloutputport("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:63:7: ( '.' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:63:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:165:2: ( '\"' (~ ( '\"' ) )* '\"' )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:165:4: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:165:8: (~ ( '\"' ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:165:9: ~ ( '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:169:2: ( ( 'A' .. 'Z' | 'a' .. 'z' ) ( 'A' .. 'Z' | 'a' .. 'z' | '_' | '0' .. '9' )* )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:169:4: ( 'A' .. 'Z' | 'a' .. 'z' ) ( 'A' .. 'Z' | 'a' .. 'z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:169:24: ( 'A' .. 'Z' | 'a' .. 'z' | '_' | '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:173:2: ( ( ' ' | '\\r' | '\\n' | '\\t' )+ )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:173:4: ( ' ' | '\\r' | '\\n' | '\\t' )+
            {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:173:4: ( ' ' | '\\r' | '\\n' | '\\t' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\t' && LA3_0<='\n')||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE"

    public void mTokens() throws RecognitionException {
        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:8: ( HIERARCHICAL_DECOMPOSITION_CONNECTOR | CALLING_ACTIVITY | CALLED_ACTIVITY | BEFORE_CONNECTOR | AFTER_CONNECTOR | AROUND_CONNECTOR | PARALLEL_CONNECTOR | ALTERNATIVE_CONNECTOR | ITERATING_CONNECTOR | NEW_BRANCH_CONNECTOR | SYNC_BRANCHES_CONNECTOR | SWITCH_BRANCHES_CONNECTOR | ADVICE_ACTIVITY | ACTIVITY_POINTCUT | EXECUTING_ACTIVITY | EXECUTING_COMPOSITE_ACTIVITY | EXECUTING_ATOMIC_ACTIVITY | DATA_MAPPING | MESSAGE_TYPE_VARIABLE | TYPE_VARIABLE | SPLIT_POINTCUT | SPLIT | ANDSPLIT | XORSPLIT | CONTROL_PORT_POINTCUT | CONTROL_PORT | CONTROL_INPUT_PORT | CONTROL_OUTPUT_PORT | QUALIFIED_NAME | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | STRING | IDENTIFIER | WHITESPACE )
        int alt4=62;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:10: HIERARCHICAL_DECOMPOSITION_CONNECTOR
                {
                mHIERARCHICAL_DECOMPOSITION_CONNECTOR(); 

                }
                break;
            case 2 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:47: CALLING_ACTIVITY
                {
                mCALLING_ACTIVITY(); 

                }
                break;
            case 3 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:64: CALLED_ACTIVITY
                {
                mCALLED_ACTIVITY(); 

                }
                break;
            case 4 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:80: BEFORE_CONNECTOR
                {
                mBEFORE_CONNECTOR(); 

                }
                break;
            case 5 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:97: AFTER_CONNECTOR
                {
                mAFTER_CONNECTOR(); 

                }
                break;
            case 6 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:113: AROUND_CONNECTOR
                {
                mAROUND_CONNECTOR(); 

                }
                break;
            case 7 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:130: PARALLEL_CONNECTOR
                {
                mPARALLEL_CONNECTOR(); 

                }
                break;
            case 8 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:149: ALTERNATIVE_CONNECTOR
                {
                mALTERNATIVE_CONNECTOR(); 

                }
                break;
            case 9 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:171: ITERATING_CONNECTOR
                {
                mITERATING_CONNECTOR(); 

                }
                break;
            case 10 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:191: NEW_BRANCH_CONNECTOR
                {
                mNEW_BRANCH_CONNECTOR(); 

                }
                break;
            case 11 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:212: SYNC_BRANCHES_CONNECTOR
                {
                mSYNC_BRANCHES_CONNECTOR(); 

                }
                break;
            case 12 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:236: SWITCH_BRANCHES_CONNECTOR
                {
                mSWITCH_BRANCHES_CONNECTOR(); 

                }
                break;
            case 13 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:262: ADVICE_ACTIVITY
                {
                mADVICE_ACTIVITY(); 

                }
                break;
            case 14 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:278: ACTIVITY_POINTCUT
                {
                mACTIVITY_POINTCUT(); 

                }
                break;
            case 15 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:296: EXECUTING_ACTIVITY
                {
                mEXECUTING_ACTIVITY(); 

                }
                break;
            case 16 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:315: EXECUTING_COMPOSITE_ACTIVITY
                {
                mEXECUTING_COMPOSITE_ACTIVITY(); 

                }
                break;
            case 17 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:344: EXECUTING_ATOMIC_ACTIVITY
                {
                mEXECUTING_ATOMIC_ACTIVITY(); 

                }
                break;
            case 18 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:370: DATA_MAPPING
                {
                mDATA_MAPPING(); 

                }
                break;
            case 19 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:383: MESSAGE_TYPE_VARIABLE
                {
                mMESSAGE_TYPE_VARIABLE(); 

                }
                break;
            case 20 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:405: TYPE_VARIABLE
                {
                mTYPE_VARIABLE(); 

                }
                break;
            case 21 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:419: SPLIT_POINTCUT
                {
                mSPLIT_POINTCUT(); 

                }
                break;
            case 22 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:434: SPLIT
                {
                mSPLIT(); 

                }
                break;
            case 23 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:440: ANDSPLIT
                {
                mANDSPLIT(); 

                }
                break;
            case 24 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:449: XORSPLIT
                {
                mXORSPLIT(); 

                }
                break;
            case 25 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:458: CONTROL_PORT_POINTCUT
                {
                mCONTROL_PORT_POINTCUT(); 

                }
                break;
            case 26 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:480: CONTROL_PORT
                {
                mCONTROL_PORT(); 

                }
                break;
            case 27 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:493: CONTROL_INPUT_PORT
                {
                mCONTROL_INPUT_PORT(); 

                }
                break;
            case 28 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:512: CONTROL_OUTPUT_PORT
                {
                mCONTROL_OUTPUT_PORT(); 

                }
                break;
            case 29 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:532: QUALIFIED_NAME
                {
                mQUALIFIED_NAME(); 

                }
                break;
            case 30 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:547: T__36
                {
                mT__36(); 

                }
                break;
            case 31 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:553: T__37
                {
                mT__37(); 

                }
                break;
            case 32 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:559: T__38
                {
                mT__38(); 

                }
                break;
            case 33 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:565: T__39
                {
                mT__39(); 

                }
                break;
            case 34 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:571: T__40
                {
                mT__40(); 

                }
                break;
            case 35 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:577: T__41
                {
                mT__41(); 

                }
                break;
            case 36 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:583: T__42
                {
                mT__42(); 

                }
                break;
            case 37 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:589: T__43
                {
                mT__43(); 

                }
                break;
            case 38 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:595: T__44
                {
                mT__44(); 

                }
                break;
            case 39 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:601: T__45
                {
                mT__45(); 

                }
                break;
            case 40 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:607: T__46
                {
                mT__46(); 

                }
                break;
            case 41 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:613: T__47
                {
                mT__47(); 

                }
                break;
            case 42 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:619: T__48
                {
                mT__48(); 

                }
                break;
            case 43 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:625: T__49
                {
                mT__49(); 

                }
                break;
            case 44 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:631: T__50
                {
                mT__50(); 

                }
                break;
            case 45 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:637: T__51
                {
                mT__51(); 

                }
                break;
            case 46 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:643: T__52
                {
                mT__52(); 

                }
                break;
            case 47 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:649: T__53
                {
                mT__53(); 

                }
                break;
            case 48 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:655: T__54
                {
                mT__54(); 

                }
                break;
            case 49 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:661: T__55
                {
                mT__55(); 

                }
                break;
            case 50 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:667: T__56
                {
                mT__56(); 

                }
                break;
            case 51 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:673: T__57
                {
                mT__57(); 

                }
                break;
            case 52 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:679: T__58
                {
                mT__58(); 

                }
                break;
            case 53 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:685: T__59
                {
                mT__59(); 

                }
                break;
            case 54 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:691: T__60
                {
                mT__60(); 

                }
                break;
            case 55 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:697: T__61
                {
                mT__61(); 

                }
                break;
            case 56 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:703: T__62
                {
                mT__62(); 

                }
                break;
            case 57 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:709: T__63
                {
                mT__63(); 

                }
                break;
            case 58 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:715: T__64
                {
                mT__64(); 

                }
                break;
            case 59 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:721: T__65
                {
                mT__65(); 

                }
                break;
            case 60 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:727: STRING
                {
                mSTRING(); 

                }
                break;
            case 61 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:734: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 62 :
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:1:745: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\1\uffff\21\35\1\uffff\6\35\6\uffff\27\35\1\133\7\35\1\143\1\35"+
        "\1\145\32\35\1\uffff\7\35\1\uffff\1\35\1\uffff\32\35\1\u00a4\4\35"+
        "\1\uffff\4\35\1\uffff\20\35\1\u00bf\10\35\1\uffff\1\35\1\u00c9\27"+
        "\35\2\uffff\10\35\1\u00e9\1\uffff\1\u00ea\35\35\1\u010c\2\uffff"+
        "\22\35\1\u0122\14\35\1\u0130\1\35\1\uffff\24\35\2\uffff\14\35\2"+
        "\uffff\2\35\1\uffff\42\35\1\uffff\1\35\1\uffff\45\35\1\u01a2\2\35"+
        "\1\uffff\24\35\1\u01b9\5\35\1\uffff\5\35\1\uffff\26\35\1\uffff\2"+
        "\35\1\u01dc\1\uffff\25\35\1\u01f2\10\35\1\uffff\1\u01fb\1\uffff"+
        "\2\35\1\u01fe\6\35\1\u0205\2\35\1\u0208\6\35\1\uffff\10\35\1\uffff"+
        "\1\35\1\u0218\1\uffff\5\35\1\u021e\1\uffff\1\u021f\1\35\1\uffff"+
        "\17\35\1\uffff\3\35\1\uffff\1\35\2\uffff\1\35\1\u0235\17\35\1\u0245"+
        "\1\35\1\uffff\1\35\1\uffff\1\u0248\4\35\1\u024d\2\35\1\uffff\6\35"+
        "\1\uffff\1\u0256\1\35\1\uffff\1\u0258\3\35\1\uffff\10\35\1\uffff"+
        "\1\35\1\uffff\1\u0265\7\35\1\uffff\1\35\1\u026e\1\u026f\1\uffff"+
        "\6\35\1\u0276\1\35\2\uffff\6\35\1\uffff\1\35\1\u027f\6\35\1\uffff"+
        "\3\35\1\uffff\2\35\1\u028b\1\u028c\3\35\2\uffff\4\35\1\uffff\1\35"+
        "\1\u0295\1\35\1\uffff\6\35\1\u029d\1\uffff";
    static final String DFA4_eofS =
        "\u029e\uffff";
    static final String DFA4_minS =
        "\1\11\1\151\1\141\1\145\1\143\1\141\1\164\1\145\1\160\1\170\1\141"+
        "\1\145\1\171\1\157\1\165\2\117\1\111\1\uffff\1\105\1\106\1\101\1"+
        "\106\1\127\1\117\6\uffff\1\145\1\154\1\156\1\146\1\164\1\157\1\164"+
        "\1\166\1\164\1\144\1\162\1\145\1\167\1\156\1\151\1\154\1\145\1\164"+
        "\1\163\1\160\1\162\1\141\1\116\1\60\1\124\1\106\1\124\1\117\1\124"+
        "\1\104\1\122\1\60\1\105\1\60\1\116\1\111\1\122\1\162\1\154\1\164"+
        "\1\157\1\145\1\165\1\145\2\151\1\163\1\141\1\162\1\137\1\143\1\164"+
        "\1\151\1\143\1\141\1\163\1\145\1\163\1\154\1\116\1\uffff\1\110\1"+
        "\117\1\105\1\125\1\105\1\55\1\101\1\uffff\1\122\1\uffff\1\103\1"+
        "\124\1\55\1\141\1\145\3\162\1\156\1\162\1\143\1\166\1\160\1\154"+
        "\1\141\1\142\1\137\1\143\1\164\1\165\1\137\1\141\1\126\1\160\1\151"+
        "\1\105\1\60\2\122\1\116\1\122\1\uffff\1\114\1\101\1\110\1\103\1"+
        "\uffff\1\162\1\156\1\144\1\157\1\145\1\137\1\144\1\156\1\145\1\151"+
        "\2\154\1\164\1\162\1\142\1\150\1\50\1\164\1\155\1\147\1\166\1\141"+
        "\1\154\1\146\1\103\1\uffff\1\105\1\60\1\104\1\116\1\114\1\124\1"+
        "\122\1\110\1\143\1\147\1\137\1\154\1\137\1\143\1\137\1\141\1\137"+
        "\1\164\1\151\1\145\1\151\1\141\1\162\1\137\1\160\2\uffff\1\151\1"+
        "\141\1\145\1\141\1\162\2\151\1\124\1\60\1\uffff\1\60\1\101\1\105"+
        "\1\111\1\117\1\111\1\150\1\137\1\141\1\137\1\143\1\157\1\143\1\164"+
        "\1\141\1\171\1\164\1\154\2\156\1\141\1\142\1\157\1\156\1\160\1\124"+
        "\1\162\1\151\1\164\1\145\1\60\2\uffff\1\124\1\114\3\116\1\151\1"+
        "\141\1\143\1\151\1\157\1\156\1\165\1\157\1\156\1\157\1\151\1\143"+
        "\1\137\1\50\1\137\1\147\1\143\1\156\1\162\1\151\1\147\1\160\1\164"+
        "\1\171\1\151\1\141\1\50\1\144\1\uffff\1\111\1\40\1\107\1\111\1\107"+
        "\2\143\1\164\1\157\1\156\1\165\1\162\1\160\1\164\3\156\1\166\1\164"+
        "\1\160\2\uffff\1\143\1\137\1\150\1\143\1\141\1\156\1\137\1\151\1"+
        "\171\1\160\1\141\1\142\2\uffff\1\137\1\126\1\uffff\1\40\1\132\1"+
        "\40\1\141\1\164\1\151\1\162\1\160\2\164\1\165\1\160\1\156\1\145"+
        "\1\156\1\145\1\151\2\157\1\143\1\137\1\150\1\156\1\164\1\141\1\143"+
        "\1\157\1\156\1\160\1\145\1\142\1\154\1\156\1\105\1\uffff\1\111\1"+
        "\uffff\1\154\1\151\1\166\1\164\1\165\1\160\1\50\1\164\1\165\1\145"+
        "\1\143\1\145\1\137\1\166\1\151\1\156\1\157\1\143\1\145\3\143\1\157"+
        "\1\164\1\157\1\155\1\147\1\145\1\126\1\154\1\145\1\141\1\40\1\116"+
        "\1\137\1\166\1\151\1\60\1\164\1\165\1\uffff\1\160\1\164\1\143\1"+
        "\164\2\143\1\151\3\156\1\157\1\163\1\150\1\165\1\164\1\157\1\155"+
        "\1\151\1\155\1\160\1\60\1\137\1\141\1\145\1\50\1\155\1\uffff\1\107"+
        "\1\144\1\151\1\164\1\160\1\uffff\1\137\1\164\1\157\1\160\1\164\1"+
        "\157\1\164\1\157\2\164\1\145\2\156\1\137\1\145\1\164\1\151\1\155"+
        "\1\160\1\166\1\151\1\157\1\uffff\1\166\1\162\1\60\1\uffff\1\145"+
        "\1\40\1\145\1\164\1\171\1\157\1\160\1\137\1\162\2\157\1\162\1\157"+
        "\1\156\1\171\2\143\1\145\1\156\1\143\1\163\1\60\1\166\1\151\1\157"+
        "\1\151\1\143\1\163\1\141\1\151\1\uffff\1\60\1\uffff\1\143\1\171"+
        "\1\60\1\151\1\157\1\160\1\164\2\162\1\60\1\162\1\156\1\60\1\165"+
        "\1\164\1\143\1\145\1\157\1\137\1\uffff\1\151\1\143\1\163\1\164\1"+
        "\141\1\151\1\162\1\141\1\uffff\1\157\1\60\1\uffff\1\156\1\162\1"+
        "\157\1\50\1\164\1\60\1\uffff\1\60\1\145\1\uffff\1\164\1\157\1\164"+
        "\1\143\1\156\1\143\1\164\1\137\1\151\1\171\1\143\1\164\1\151\1\142"+
        "\1\155\1\uffff\2\164\1\162\1\uffff\1\50\2\uffff\1\143\1\60\1\162"+
        "\1\157\1\164\1\156\1\157\1\171\1\141\1\164\1\50\1\164\1\145\1\141"+
        "\1\154\1\160\1\143\1\60\1\164\1\uffff\1\164\1\uffff\1\60\1\162\1"+
        "\157\1\145\1\156\1\60\1\143\1\145\1\uffff\1\151\1\141\1\142\1\145"+
        "\1\157\1\165\1\uffff\1\60\1\157\1\uffff\1\60\1\162\1\143\1\156\1"+
        "\uffff\1\164\1\137\1\166\1\143\1\154\1\50\1\163\1\164\1\uffff\1"+
        "\162\1\uffff\1\60\1\164\1\145\1\151\1\141\1\151\1\164\1\145\1\uffff"+
        "\1\151\2\60\1\uffff\1\157\1\143\1\166\1\143\1\164\1\151\1\60\1\164"+
        "\2\uffff\1\162\1\164\1\151\1\164\1\171\1\166\1\uffff\1\151\1\60"+
        "\1\157\1\164\1\151\1\50\1\151\1\157\1\uffff\1\162\1\171\1\166\1"+
        "\uffff\1\164\1\156\2\60\1\151\1\171\1\137\2\uffff\1\164\1\50\1\143"+
        "\1\171\1\uffff\1\157\1\60\1\156\1\uffff\1\156\1\145\1\143\1\164"+
        "\1\157\1\162\1\60\1\uffff";
    static final String DFA4_maxS =
        "\1\172\1\151\1\157\1\145\1\162\1\141\1\164\1\145\1\171\1\170\1\141"+
        "\1\145\1\171\1\157\1\165\2\117\1\111\1\uffff\1\105\1\122\1\101\1"+
        "\124\1\131\1\117\6\uffff\1\145\1\154\1\156\1\146\1\164\1\157\1\164"+
        "\1\166\1\164\1\144\1\162\1\145\1\167\1\156\1\151\1\154\1\145\1\164"+
        "\1\163\1\160\1\162\1\141\1\116\1\172\1\124\1\106\1\124\1\117\1\124"+
        "\1\104\1\122\1\172\1\105\1\172\1\116\1\111\1\122\1\162\1\154\1\164"+
        "\1\157\1\145\1\165\1\145\2\151\1\163\1\141\1\162\1\137\1\143\1\164"+
        "\1\151\1\143\1\141\1\163\1\145\1\163\1\154\1\116\1\uffff\1\110\1"+
        "\117\1\105\1\125\1\105\1\55\1\101\1\uffff\1\122\1\uffff\1\103\1"+
        "\124\1\55\1\141\1\151\3\162\1\156\1\162\1\143\1\166\1\160\1\154"+
        "\1\141\1\142\1\137\1\143\1\164\1\165\1\137\1\141\1\137\1\160\1\151"+
        "\1\105\1\172\2\122\1\116\1\122\1\uffff\1\114\1\101\1\110\1\103\1"+
        "\uffff\1\162\1\156\1\144\1\157\1\145\1\137\1\144\1\156\1\145\1\151"+
        "\2\154\1\164\1\162\1\142\1\150\1\172\1\164\1\155\1\147\1\166\1\141"+
        "\1\154\1\146\1\103\1\uffff\1\105\1\172\1\104\1\116\1\114\1\124\1"+
        "\122\1\110\1\143\1\147\1\137\1\154\1\137\1\143\1\137\1\141\1\137"+
        "\1\164\1\151\1\145\1\151\1\141\1\162\1\137\1\160\2\uffff\1\151\1"+
        "\141\1\145\1\141\1\162\2\151\1\124\1\172\1\uffff\1\172\1\101\1\105"+
        "\1\111\1\117\1\111\1\150\1\137\1\141\1\160\1\143\1\157\1\143\1\164"+
        "\1\141\1\171\1\164\1\154\2\156\1\141\1\142\1\157\1\156\1\160\1\137"+
        "\1\162\1\151\1\164\1\145\1\172\2\uffff\1\124\1\114\3\116\1\151\1"+
        "\141\1\143\1\160\1\157\1\156\1\165\1\157\1\156\1\157\1\151\1\143"+
        "\1\137\1\172\1\137\1\147\1\143\1\156\1\162\1\151\1\147\1\160\1\164"+
        "\1\171\1\151\1\141\1\172\1\144\1\uffff\1\111\1\40\1\107\1\111\1"+
        "\107\2\143\1\164\1\157\1\156\1\165\1\162\1\160\1\164\3\156\1\166"+
        "\1\164\1\160\2\uffff\1\143\1\137\1\150\1\143\1\141\1\156\1\143\1"+
        "\151\1\171\1\160\1\141\1\142\2\uffff\1\137\1\126\1\uffff\1\40\1"+
        "\132\1\40\1\141\1\164\1\151\1\162\1\160\2\164\1\165\1\160\1\156"+
        "\1\145\1\156\1\145\1\151\2\157\1\143\1\137\1\150\1\156\1\164\1\143"+
        "\1\164\1\157\1\156\1\160\1\145\1\142\1\154\1\156\1\105\1\uffff\1"+
        "\111\1\uffff\1\154\1\151\1\166\1\164\1\165\1\160\1\50\1\164\1\165"+
        "\1\145\1\143\1\145\1\137\1\166\1\151\1\156\1\157\1\143\1\145\2\143"+
        "\1\164\1\157\1\164\1\157\1\155\1\147\1\145\1\126\1\154\1\145\1\141"+
        "\1\40\1\116\1\137\1\166\1\151\1\172\1\164\1\165\1\uffff\1\160\1"+
        "\164\1\143\1\164\2\143\1\151\3\156\1\157\1\163\1\150\1\165\1\164"+
        "\1\157\1\155\1\151\1\155\1\160\1\172\1\137\1\141\1\145\1\50\1\155"+
        "\1\uffff\1\107\1\144\1\151\1\164\1\160\1\uffff\1\137\1\164\1\157"+
        "\1\160\1\164\1\157\1\164\1\157\2\164\1\145\2\156\1\137\1\145\1\164"+
        "\1\151\1\155\1\160\1\166\1\151\1\157\1\uffff\1\166\1\162\1\172\1"+
        "\uffff\1\145\1\40\1\145\1\164\1\171\1\157\1\160\1\137\1\162\2\157"+
        "\1\162\1\157\1\156\1\171\2\143\1\145\1\156\1\143\1\163\1\172\1\166"+
        "\1\151\1\157\1\151\1\143\1\163\1\141\1\151\1\uffff\1\172\1\uffff"+
        "\1\143\1\171\1\172\1\151\1\157\1\160\1\164\2\162\1\172\1\162\1\156"+
        "\1\172\1\165\1\164\1\143\1\145\1\157\1\137\1\uffff\1\151\1\143\1"+
        "\163\1\164\1\141\1\151\1\162\1\141\1\uffff\1\157\1\172\1\uffff\1"+
        "\156\1\162\1\157\1\50\1\164\1\172\1\uffff\1\172\1\145\1\uffff\1"+
        "\164\1\157\1\164\1\143\1\156\1\143\1\164\1\137\1\151\1\171\1\143"+
        "\1\164\1\151\1\142\1\155\1\uffff\2\164\1\162\1\uffff\1\50\2\uffff"+
        "\1\143\1\172\1\162\1\157\1\164\1\156\1\157\1\171\1\141\1\164\1\50"+
        "\1\164\1\145\1\141\1\154\1\160\1\143\1\172\1\164\1\uffff\1\164\1"+
        "\uffff\1\172\1\162\1\157\1\145\1\156\1\172\1\143\1\145\1\uffff\1"+
        "\151\1\141\1\142\1\145\1\157\1\165\1\uffff\1\172\1\157\1\uffff\1"+
        "\172\1\162\1\143\1\156\1\uffff\1\164\1\137\1\166\1\143\1\154\1\50"+
        "\1\163\1\164\1\uffff\1\162\1\uffff\1\172\1\164\1\145\1\151\1\141"+
        "\1\151\1\164\1\145\1\uffff\1\151\2\172\1\uffff\1\157\1\143\1\166"+
        "\1\143\1\164\1\151\1\172\1\164\2\uffff\1\162\1\164\1\151\1\164\1"+
        "\171\1\166\1\uffff\1\151\1\172\1\157\1\164\1\151\1\50\1\151\1\157"+
        "\1\uffff\1\162\1\171\1\166\1\uffff\1\164\1\156\2\172\1\151\1\171"+
        "\1\137\2\uffff\1\164\1\50\1\143\1\171\1\uffff\1\157\1\172\1\156"+
        "\1\uffff\1\156\1\145\1\143\1\164\1\157\1\162\1\172\1\uffff";
    static final String DFA4_acceptS =
        "\22\uffff\1\41\6\uffff\1\57\1\62\1\73\1\74\1\75\1\76\74\uffff\1"+
        "\37\7\uffff\1\47\1\uffff\1\51\37\uffff\1\52\4\uffff\1\55\31\uffff"+
        "\1\40\31\uffff\1\65\1\26\11\uffff\1\43\37\uffff\1\42\1\44\41\uffff"+
        "\1\36\24\uffff\1\66\1\27\14\uffff\1\67\1\30\2\uffff\1\45\42\uffff"+
        "\1\50\1\uffff\1\54\50\uffff\1\70\32\uffff\1\46\5\uffff\1\32\26\uffff"+
        "\1\22\3\uffff\1\64\36\uffff\1\24\1\uffff\1\53\23\uffff\1\25\10\uffff"+
        "\1\35\2\uffff\1\3\6\uffff\1\5\2\uffff\1\15\17\uffff\1\2\3\uffff"+
        "\1\71\1\uffff\1\4\1\6\23\uffff\1\72\1\uffff\1\16\10\uffff\1\56\6"+
        "\uffff\1\33\2\uffff\1\7\4\uffff\1\17\10\uffff\1\34\1\uffff\1\11"+
        "\10\uffff\1\63\3\uffff\1\12\10\uffff\1\31\1\10\6\uffff\1\23\10\uffff"+
        "\1\13\3\uffff\1\61\7\uffff\1\14\1\21\4\uffff\1\60\3\uffff\1\20\7"+
        "\uffff\1\1";
    static final String DFA4_specialS =
        "\u029e\uffff}>";
    static final String[] DFA4_transitionS = {
            "\2\36\2\uffff\1\36\22\uffff\1\36\1\uffff\1\34\6\uffff\1\31\2"+
            "\uffff\1\22\1\uffff\1\33\16\uffff\1\32\3\uffff\1\24\1\23\1\17"+
            "\5\35\1\26\6\35\1\25\2\35\1\27\1\20\2\35\1\21\1\30\2\35\6\uffff"+
            "\1\4\1\3\1\2\1\12\1\11\2\35\1\1\1\6\3\35\1\13\1\7\1\35\1\5\1"+
            "\16\1\35\1\10\1\14\3\35\1\15\2\35",
            "\1\37",
            "\1\40\15\uffff\1\41",
            "\1\42",
            "\1\47\1\46\1\uffff\1\43\5\uffff\1\45\1\uffff\1\50\3\uffff\1"+
            "\44",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\56\6\uffff\1\55\1\uffff\1\54",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "",
            "\1\70",
            "\1\71\5\uffff\1\73\1\uffff\1\74\3\uffff\1\72",
            "\1\75",
            "\1\76\7\uffff\1\100\5\uffff\1\77",
            "\1\102\1\uffff\1\101",
            "\1\103",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\144",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
            "",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "",
            "\1\u0087",
            "",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008d\3\uffff\1\u008c",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u00a0\10\uffff\1\u009f",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00be\7\uffff\12\35\7\uffff\32\35\4\uffff\1\u00bd\1\uffff"+
            "\32\35",
            "\1\u00c0",
            "\1\u00c1",
            "\1\u00c2",
            "\1\u00c3",
            "\1\u00c4",
            "\1\u00c5",
            "\1\u00c6",
            "\1\u00c7",
            "",
            "\1\u00c8",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cd",
            "\1\u00ce",
            "\1\u00cf",
            "\1\u00d0",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "",
            "",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "\1\u00e7",
            "\1\u00e8",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\1\u00f1",
            "\1\u00f2",
            "\1\u00f3\11\uffff\1\u00f5\5\uffff\1\u00f6\1\u00f4",
            "\1\u00f7",
            "\1\u00f8",
            "\1\u00f9",
            "\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "\1\u00fd",
            "\1\u00fe",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\1\u0104",
            "\1\u0105",
            "\1\u0107\12\uffff\1\u0106",
            "\1\u0108",
            "\1\u0109",
            "\1\u010a",
            "\1\u010b",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "",
            "\1\u010d",
            "\1\u010e",
            "\1\u010f",
            "\1\u0110",
            "\1\u0111",
            "\1\u0112",
            "\1\u0113",
            "\1\u0114",
            "\1\u0116\5\uffff\1\u0117\1\u0115",
            "\1\u0118",
            "\1\u0119",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\1\u011d",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120",
            "\1\u0121\7\uffff\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32"+
            "\35",
            "\1\u0123",
            "\1\u0124",
            "\1\u0125",
            "\1\u0126",
            "\1\u0127",
            "\1\u0128",
            "\1\u0129",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e",
            "\1\u012f\7\uffff\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32"+
            "\35",
            "\1\u0131",
            "",
            "\1\u0132",
            "\1\u0133",
            "\1\u0134",
            "\1\u0135",
            "\1\u0136",
            "\1\u0137",
            "\1\u0138",
            "\1\u0139",
            "\1\u013a",
            "\1\u013b",
            "\1\u013c",
            "\1\u013d",
            "\1\u013e",
            "\1\u013f",
            "\1\u0140",
            "\1\u0141",
            "\1\u0142",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "",
            "",
            "\1\u0146",
            "\1\u0147",
            "\1\u0148",
            "\1\u0149",
            "\1\u014a",
            "\1\u014b",
            "\1\u014c\1\uffff\1\u014d\1\uffff\1\u014e",
            "\1\u014f",
            "\1\u0150",
            "\1\u0151",
            "\1\u0152",
            "\1\u0153",
            "",
            "",
            "\1\u0154",
            "\1\u0155",
            "",
            "\1\u0156",
            "\1\u0157",
            "\1\u0158",
            "\1\u0159",
            "\1\u015a",
            "\1\u015b",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\1\u015f",
            "\1\u0160",
            "\1\u0161",
            "\1\u0162",
            "\1\u0163",
            "\1\u0164",
            "\1\u0165",
            "\1\u0166",
            "\1\u0167",
            "\1\u0168",
            "\1\u0169",
            "\1\u016a",
            "\1\u016b",
            "\1\u016c",
            "\1\u016d",
            "\1\u016e\1\uffff\1\u016f",
            "\1\u0170\20\uffff\1\u0171",
            "\1\u0172",
            "\1\u0173",
            "\1\u0174",
            "\1\u0175",
            "\1\u0176",
            "\1\u0177",
            "\1\u0178",
            "\1\u0179",
            "",
            "\1\u017a",
            "",
            "\1\u017b",
            "\1\u017c",
            "\1\u017d",
            "\1\u017e",
            "\1\u017f",
            "\1\u0180",
            "\1\u0181",
            "\1\u0182",
            "\1\u0183",
            "\1\u0184",
            "\1\u0185",
            "\1\u0186",
            "\1\u0187",
            "\1\u0188",
            "\1\u0189",
            "\1\u018a",
            "\1\u018b",
            "\1\u018c",
            "\1\u018d",
            "\1\u018e",
            "\1\u018f",
            "\1\u0190\20\uffff\1\u0191",
            "\1\u0192",
            "\1\u0193",
            "\1\u0194",
            "\1\u0195",
            "\1\u0196",
            "\1\u0197",
            "\1\u0198",
            "\1\u0199",
            "\1\u019a",
            "\1\u019b",
            "\1\u019c",
            "\1\u019d",
            "\1\u019e",
            "\1\u019f",
            "\1\u01a0",
            "\12\35\7\uffff\32\35\4\uffff\1\u01a1\1\uffff\32\35",
            "\1\u01a3",
            "\1\u01a4",
            "",
            "\1\u01a5",
            "\1\u01a6",
            "\1\u01a7",
            "\1\u01a8",
            "\1\u01a9",
            "\1\u01aa",
            "\1\u01ab",
            "\1\u01ac",
            "\1\u01ad",
            "\1\u01ae",
            "\1\u01af",
            "\1\u01b0",
            "\1\u01b1",
            "\1\u01b2",
            "\1\u01b3",
            "\1\u01b4",
            "\1\u01b5",
            "\1\u01b6",
            "\1\u01b7",
            "\1\u01b8",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u01ba",
            "\1\u01bb",
            "\1\u01bc",
            "\1\u01bd",
            "\1\u01be",
            "",
            "\1\u01bf",
            "\1\u01c0",
            "\1\u01c1",
            "\1\u01c2",
            "\1\u01c3",
            "",
            "\1\u01c4",
            "\1\u01c5",
            "\1\u01c6",
            "\1\u01c7",
            "\1\u01c8",
            "\1\u01c9",
            "\1\u01ca",
            "\1\u01cb",
            "\1\u01cc",
            "\1\u01cd",
            "\1\u01ce",
            "\1\u01cf",
            "\1\u01d0",
            "\1\u01d1",
            "\1\u01d2",
            "\1\u01d3",
            "\1\u01d4",
            "\1\u01d5",
            "\1\u01d6",
            "\1\u01d7",
            "\1\u01d8",
            "\1\u01d9",
            "",
            "\1\u01da",
            "\1\u01db",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\1\u01dd",
            "\1\u01de",
            "\1\u01df",
            "\1\u01e0",
            "\1\u01e1",
            "\1\u01e2",
            "\1\u01e3",
            "\1\u01e4",
            "\1\u01e5",
            "\1\u01e6",
            "\1\u01e7",
            "\1\u01e8",
            "\1\u01e9",
            "\1\u01ea",
            "\1\u01eb",
            "\1\u01ec",
            "\1\u01ed",
            "\1\u01ee",
            "\1\u01ef",
            "\1\u01f0",
            "\1\u01f1",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u01f3",
            "\1\u01f4",
            "\1\u01f5",
            "\1\u01f6",
            "\1\u01f7",
            "\1\u01f8",
            "\1\u01f9",
            "\1\u01fa",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\1\u01fc",
            "\1\u01fd",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u01ff",
            "\1\u0200",
            "\1\u0201",
            "\1\u0202",
            "\1\u0203",
            "\1\u0204",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0206",
            "\1\u0207",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0209",
            "\1\u020a",
            "\1\u020b",
            "\1\u020c",
            "\1\u020d",
            "\1\u020e",
            "",
            "\1\u020f",
            "\1\u0210",
            "\1\u0211",
            "\1\u0212",
            "\1\u0213",
            "\1\u0214",
            "\1\u0215",
            "\1\u0216",
            "",
            "\1\u0217",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\1\u0219",
            "\1\u021a",
            "\1\u021b",
            "\1\u021c",
            "\1\u021d",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0220",
            "",
            "\1\u0221",
            "\1\u0222",
            "\1\u0223",
            "\1\u0224",
            "\1\u0225",
            "\1\u0226",
            "\1\u0227",
            "\1\u0228",
            "\1\u0229",
            "\1\u022a",
            "\1\u022b",
            "\1\u022c",
            "\1\u022d",
            "\1\u022e",
            "\1\u022f",
            "",
            "\1\u0230",
            "\1\u0231",
            "\1\u0232",
            "",
            "\1\u0233",
            "",
            "",
            "\1\u0234",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0236",
            "\1\u0237",
            "\1\u0238",
            "\1\u0239",
            "\1\u023a",
            "\1\u023b",
            "\1\u023c",
            "\1\u023d",
            "\1\u023e",
            "\1\u023f",
            "\1\u0240",
            "\1\u0241",
            "\1\u0242",
            "\1\u0243",
            "\1\u0244",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0246",
            "",
            "\1\u0247",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0249",
            "\1\u024a",
            "\1\u024b",
            "\1\u024c",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u024e",
            "\1\u024f",
            "",
            "\1\u0250",
            "\1\u0251",
            "\1\u0252",
            "\1\u0253",
            "\1\u0254",
            "\1\u0255",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0257",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0259",
            "\1\u025a",
            "\1\u025b",
            "",
            "\1\u025c",
            "\1\u025d",
            "\1\u025e",
            "\1\u025f",
            "\1\u0260",
            "\1\u0261",
            "\1\u0262",
            "\1\u0263",
            "",
            "\1\u0264",
            "",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0266",
            "\1\u0267",
            "\1\u0268",
            "\1\u0269",
            "\1\u026a",
            "\1\u026b",
            "\1\u026c",
            "",
            "\1\u026d",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "",
            "\1\u0270",
            "\1\u0271",
            "\1\u0272",
            "\1\u0273",
            "\1\u0274",
            "\1\u0275",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0277",
            "",
            "",
            "\1\u0278",
            "\1\u0279",
            "\1\u027a",
            "\1\u027b",
            "\1\u027c",
            "\1\u027d",
            "",
            "\1\u027e",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0280",
            "\1\u0281",
            "\1\u0282",
            "\1\u0283",
            "\1\u0284",
            "\1\u0285",
            "",
            "\1\u0286",
            "\1\u0287",
            "\1\u0288",
            "",
            "\1\u0289",
            "\1\u028a",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u028d",
            "\1\u028e",
            "\1\u028f",
            "",
            "",
            "\1\u0290",
            "\1\u0291",
            "\1\u0292",
            "\1\u0293",
            "",
            "\1\u0294",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            "\1\u0296",
            "",
            "\1\u0297",
            "\1\u0298",
            "\1\u0299",
            "\1\u029a",
            "\1\u029b",
            "\1\u029c",
            "\12\35\7\uffff\32\35\4\uffff\1\35\1\uffff\32\35",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( HIERARCHICAL_DECOMPOSITION_CONNECTOR | CALLING_ACTIVITY | CALLED_ACTIVITY | BEFORE_CONNECTOR | AFTER_CONNECTOR | AROUND_CONNECTOR | PARALLEL_CONNECTOR | ALTERNATIVE_CONNECTOR | ITERATING_CONNECTOR | NEW_BRANCH_CONNECTOR | SYNC_BRANCHES_CONNECTOR | SWITCH_BRANCHES_CONNECTOR | ADVICE_ACTIVITY | ACTIVITY_POINTCUT | EXECUTING_ACTIVITY | EXECUTING_COMPOSITE_ACTIVITY | EXECUTING_ATOMIC_ACTIVITY | DATA_MAPPING | MESSAGE_TYPE_VARIABLE | TYPE_VARIABLE | SPLIT_POINTCUT | SPLIT | ANDSPLIT | XORSPLIT | CONTROL_PORT_POINTCUT | CONTROL_PORT | CONTROL_INPUT_PORT | CONTROL_OUTPUT_PORT | QUALIFIED_NAME | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | STRING | IDENTIFIER | WHITESPACE );";
        }
    }
 

}
// $ANTLR 3.3 Nov 30, 2010 12:45:30 /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g 2011-10-28 15:48:50

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


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


@SuppressWarnings("all")
public class ConnectorTree extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "HIERARCHICAL_DECOMPOSITION_CONNECTOR", "CALLING_ACTIVITY", "CALLED_ACTIVITY", "BEFORE_CONNECTOR", "AFTER_CONNECTOR", "AROUND_CONNECTOR", "PARALLEL_CONNECTOR", "ALTERNATIVE_CONNECTOR", "ITERATING_CONNECTOR", "NEW_BRANCH_CONNECTOR", "SYNC_BRANCHES_CONNECTOR", "SWITCH_BRANCHES_CONNECTOR", "ADVICE_ACTIVITY", "ACTIVITY_POINTCUT", "EXECUTING_ACTIVITY", "EXECUTING_COMPOSITE_ACTIVITY", "EXECUTING_ATOMIC_ACTIVITY", "DATA_MAPPING", "MESSAGE_TYPE_VARIABLE", "TYPE_VARIABLE", "SPLIT_POINTCUT", "SPLIT", "ANDSPLIT", "XORSPLIT", "CONTROL_PORT_POINTCUT", "CONTROL_PORT", "CONTROL_INPUT_PORT", "CONTROL_OUTPUT_PORT", "QUALIFIED_NAME", "STRING", "IDENTIFIER", "WHITESPACE", "'CONNECT'", "'TO'", "'WITH'", "','", "'BEFORE'", "'AFTER'", "'AROUND'", "'PARALLEL TO'", "'ALTERNATIVE TO'", "'IF'", "'ITERATING OVER'", "'IN'", "'AND-SPLITTING AT'", "'SYNCHRONIZING AT'", "'SWITCHING AT'", "'XOR-JOINING AT'", "'executingactivity('", "')'", "'executingcompositeactivity('", "'executingatomicactivity('", "'='", "'messageTypeVariable('", "'typeVariable('", "'split('", "'andsplit('", "'xorsplit('", "'controlport('", "'controlinputport('", "'controloutputport('", "'.'"
    };
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


        public ConnectorTree(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public ConnectorTree(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ConnectorTree.tokenNames; }
    public String getGrammarFileName() { return "/Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g"; }


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


    public static class connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:55:1: connector : ( hierarchical_decomposition_connector | inversion_of_control_connector );
    public final ConnectorTree.connector_return connector() throws RecognitionException {
        ConnectorTree.connector_return retval = new ConnectorTree.connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ConnectorTree.hierarchical_decomposition_connector_return hierarchical_decomposition_connector1 = null;

        ConnectorTree.inversion_of_control_connector_return inversion_of_control_connector2 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:56:2: ( hierarchical_decomposition_connector | inversion_of_control_connector )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==HIERARCHICAL_DECOMPOSITION_CONNECTOR) ) {
                alt1=1;
            }
            else if ( (LA1_0==AFTER_CONNECTOR||(LA1_0>=PARALLEL_CONNECTOR && LA1_0<=ALTERNATIVE_CONNECTOR)||LA1_0==SYNC_BRANCHES_CONNECTOR) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:56:4: hierarchical_decomposition_connector
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_hierarchical_decomposition_connector_in_connector48);
                    hierarchical_decomposition_connector1=hierarchical_decomposition_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, hierarchical_decomposition_connector1.getTree());

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:57:4: inversion_of_control_connector
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_inversion_of_control_connector_in_connector53);
                    inversion_of_control_connector2=inversion_of_control_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, inversion_of_control_connector2.getTree());

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "connector"

    public static class hierarchical_decomposition_connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "hierarchical_decomposition_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:60:1: hierarchical_decomposition_connector : ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* ) ;
    public final ConnectorTree.hierarchical_decomposition_connector_return hierarchical_decomposition_connector() throws RecognitionException {
        ConnectorTree.hierarchical_decomposition_connector_return retval = new ConnectorTree.hierarchical_decomposition_connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree HIERARCHICAL_DECOMPOSITION_CONNECTOR3=null;
        ConnectorTree.calling_activity_return calling_activity4 = null;

        ConnectorTree.called_activity_return called_activity5 = null;

        ConnectorTree.data_mapping_return data_mapping6 = null;


        CommonTree HIERARCHICAL_DECOMPOSITION_CONNECTOR3_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:61:2: ( ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:61:4: ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            HIERARCHICAL_DECOMPOSITION_CONNECTOR3=(CommonTree)match(input,HIERARCHICAL_DECOMPOSITION_CONNECTOR,FOLLOW_HIERARCHICAL_DECOMPOSITION_CONNECTOR_in_hierarchical_decomposition_connector65); 
            HIERARCHICAL_DECOMPOSITION_CONNECTOR3_tree = (CommonTree)adaptor.dupNode(HIERARCHICAL_DECOMPOSITION_CONNECTOR3);

            root_1 = (CommonTree)adaptor.becomeRoot(HIERARCHICAL_DECOMPOSITION_CONNECTOR3_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_calling_activity_in_hierarchical_decomposition_connector67);
            calling_activity4=calling_activity();

            state._fsp--;

            adaptor.addChild(root_1, calling_activity4.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_called_activity_in_hierarchical_decomposition_connector69);
            called_activity5=called_activity();

            state._fsp--;

            adaptor.addChild(root_1, called_activity5.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:61:76: ( data_mapping )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DATA_MAPPING) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:61:76: data_mapping
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_data_mapping_in_hierarchical_decomposition_connector71);
            	    data_mapping6=data_mapping();

            	    state._fsp--;

            	    adaptor.addChild(root_1, data_mapping6.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			log.debug("Parsing hierarchical decomposition connector...");
            			this.connector = new HierarchicalDecompositionConnector(this.callingActivity, this.calledActivity, this.dataMappings);
            			log.debug("Parsed hierarchical decomposition connector '" + this.connector + "'");
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "hierarchical_decomposition_connector"

    public static class calling_activity_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "calling_activity"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:68:1: calling_activity : ^( CALLING_ACTIVITY qualified_name ) ;
    public final ConnectorTree.calling_activity_return calling_activity() throws RecognitionException {
        ConnectorTree.calling_activity_return retval = new ConnectorTree.calling_activity_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree CALLING_ACTIVITY7=null;
        ConnectorTree.qualified_name_return qualified_name8 = null;


        CommonTree CALLING_ACTIVITY7_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:69:2: ( ^( CALLING_ACTIVITY qualified_name ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:69:4: ^( CALLING_ACTIVITY qualified_name )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            CALLING_ACTIVITY7=(CommonTree)match(input,CALLING_ACTIVITY,FOLLOW_CALLING_ACTIVITY_in_calling_activity87); 
            CALLING_ACTIVITY7_tree = (CommonTree)adaptor.dupNode(CALLING_ACTIVITY7);

            root_1 = (CommonTree)adaptor.becomeRoot(CALLING_ACTIVITY7_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_qualified_name_in_calling_activity89);
            qualified_name8=qualified_name();

            state._fsp--;

            adaptor.addChild(root_1, qualified_name8.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			this.callingActivity = this.qualifiedNames.get(0);
            			this.qualifiedNames = null;
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "calling_activity"

    public static class called_activity_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "called_activity"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:75:1: called_activity : ^( CALLED_ACTIVITY qualified_name ) ;
    public final ConnectorTree.called_activity_return called_activity() throws RecognitionException {
        ConnectorTree.called_activity_return retval = new ConnectorTree.called_activity_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree CALLED_ACTIVITY9=null;
        ConnectorTree.qualified_name_return qualified_name10 = null;


        CommonTree CALLED_ACTIVITY9_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:76:2: ( ^( CALLED_ACTIVITY qualified_name ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:76:4: ^( CALLED_ACTIVITY qualified_name )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            CALLED_ACTIVITY9=(CommonTree)match(input,CALLED_ACTIVITY,FOLLOW_CALLED_ACTIVITY_in_called_activity104); 
            CALLED_ACTIVITY9_tree = (CommonTree)adaptor.dupNode(CALLED_ACTIVITY9);

            root_1 = (CommonTree)adaptor.becomeRoot(CALLED_ACTIVITY9_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_qualified_name_in_called_activity106);
            qualified_name10=qualified_name();

            state._fsp--;

            adaptor.addChild(root_1, qualified_name10.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			this.calledActivity = this.qualifiedNames.get(0);
            			this.qualifiedNames = null;
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "called_activity"

    public static class inversion_of_control_connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inversion_of_control_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:82:1: inversion_of_control_connector : ( after_connector | parallel_connector | alternative_connector | sync_branches_connector );
    public final ConnectorTree.inversion_of_control_connector_return inversion_of_control_connector() throws RecognitionException {
        ConnectorTree.inversion_of_control_connector_return retval = new ConnectorTree.inversion_of_control_connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ConnectorTree.after_connector_return after_connector11 = null;

        ConnectorTree.parallel_connector_return parallel_connector12 = null;

        ConnectorTree.alternative_connector_return alternative_connector13 = null;

        ConnectorTree.sync_branches_connector_return sync_branches_connector14 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:83:2: ( after_connector | parallel_connector | alternative_connector | sync_branches_connector )
            int alt3=4;
            switch ( input.LA(1) ) {
            case AFTER_CONNECTOR:
                {
                alt3=1;
                }
                break;
            case PARALLEL_CONNECTOR:
                {
                alt3=2;
                }
                break;
            case ALTERNATIVE_CONNECTOR:
                {
                alt3=3;
                }
                break;
            case SYNC_BRANCHES_CONNECTOR:
                {
                alt3=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:83:4: after_connector
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_after_connector_in_inversion_of_control_connector120);
                    after_connector11=after_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, after_connector11.getTree());

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:84:4: parallel_connector
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_parallel_connector_in_inversion_of_control_connector125);
                    parallel_connector12=parallel_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, parallel_connector12.getTree());

                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:85:4: alternative_connector
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_alternative_connector_in_inversion_of_control_connector130);
                    alternative_connector13=alternative_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, alternative_connector13.getTree());

                    }
                    break;
                case 4 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:86:4: sync_branches_connector
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_sync_branches_connector_in_inversion_of_control_connector135);
                    sync_branches_connector14=sync_branches_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, sync_branches_connector14.getTree());

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "inversion_of_control_connector"

    public static class after_connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "after_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:89:1: after_connector : ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) ;
    public final ConnectorTree.after_connector_return after_connector() throws RecognitionException {
        ConnectorTree.after_connector_return retval = new ConnectorTree.after_connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree AFTER_CONNECTOR15=null;
        ConnectorTree.advice_activity_return advice_activity16 = null;

        ConnectorTree.activity_pointcut_return activity_pointcut17 = null;

        ConnectorTree.data_mapping_return data_mapping18 = null;


        CommonTree AFTER_CONNECTOR15_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:90:2: ( ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:90:4: ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            AFTER_CONNECTOR15=(CommonTree)match(input,AFTER_CONNECTOR,FOLLOW_AFTER_CONNECTOR_in_after_connector147); 
            AFTER_CONNECTOR15_tree = (CommonTree)adaptor.dupNode(AFTER_CONNECTOR15);

            root_1 = (CommonTree)adaptor.becomeRoot(AFTER_CONNECTOR15_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_advice_activity_in_after_connector149);
            advice_activity16=advice_activity();

            state._fsp--;

            adaptor.addChild(root_1, advice_activity16.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_activity_pointcut_in_after_connector151);
            activity_pointcut17=activity_pointcut();

            state._fsp--;

            adaptor.addChild(root_1, activity_pointcut17.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:90:56: ( data_mapping )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==DATA_MAPPING) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:90:56: data_mapping
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_data_mapping_in_after_connector153);
            	    data_mapping18=data_mapping();

            	    state._fsp--;

            	    adaptor.addChild(root_1, data_mapping18.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			log.debug("Parsing after connector...");
            			this.connector = new AfterConnector(this.adviceActivity, this.activityPointcut, this.dataMappings);
            			log.debug("Parsed after connector '" + this.connector + "'");
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "after_connector"

    public static class parallel_connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parallel_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:97:1: parallel_connector : ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) ;
    public final ConnectorTree.parallel_connector_return parallel_connector() throws RecognitionException {
        ConnectorTree.parallel_connector_return retval = new ConnectorTree.parallel_connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PARALLEL_CONNECTOR19=null;
        ConnectorTree.advice_activity_return advice_activity20 = null;

        ConnectorTree.activity_pointcut_return activity_pointcut21 = null;

        ConnectorTree.data_mapping_return data_mapping22 = null;


        CommonTree PARALLEL_CONNECTOR19_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:98:2: ( ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:98:4: ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PARALLEL_CONNECTOR19=(CommonTree)match(input,PARALLEL_CONNECTOR,FOLLOW_PARALLEL_CONNECTOR_in_parallel_connector169); 
            PARALLEL_CONNECTOR19_tree = (CommonTree)adaptor.dupNode(PARALLEL_CONNECTOR19);

            root_1 = (CommonTree)adaptor.becomeRoot(PARALLEL_CONNECTOR19_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_advice_activity_in_parallel_connector171);
            advice_activity20=advice_activity();

            state._fsp--;

            adaptor.addChild(root_1, advice_activity20.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_activity_pointcut_in_parallel_connector173);
            activity_pointcut21=activity_pointcut();

            state._fsp--;

            adaptor.addChild(root_1, activity_pointcut21.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:98:59: ( data_mapping )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==DATA_MAPPING) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:98:59: data_mapping
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_data_mapping_in_parallel_connector175);
            	    data_mapping22=data_mapping();

            	    state._fsp--;

            	    adaptor.addChild(root_1, data_mapping22.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			log.debug("Parsing parallel connector...");
            			this.connector = new ParallelConnector(this.adviceActivity, this.activityPointcut, this.dataMappings);
            			log.debug("Parsed parallel connector '" + this.connector + "'");
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parallel_connector"

    public static class alternative_connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "alternative_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:105:1: alternative_connector : ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* ) ;
    public final ConnectorTree.alternative_connector_return alternative_connector() throws RecognitionException {
        ConnectorTree.alternative_connector_return retval = new ConnectorTree.alternative_connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ALTERNATIVE_CONNECTOR23=null;
        CommonTree STRING26=null;
        ConnectorTree.advice_activity_return advice_activity24 = null;

        ConnectorTree.activity_pointcut_return activity_pointcut25 = null;

        ConnectorTree.data_mapping_return data_mapping27 = null;


        CommonTree ALTERNATIVE_CONNECTOR23_tree=null;
        CommonTree STRING26_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:106:2: ( ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:106:4: ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            ALTERNATIVE_CONNECTOR23=(CommonTree)match(input,ALTERNATIVE_CONNECTOR,FOLLOW_ALTERNATIVE_CONNECTOR_in_alternative_connector191); 
            ALTERNATIVE_CONNECTOR23_tree = (CommonTree)adaptor.dupNode(ALTERNATIVE_CONNECTOR23);

            root_1 = (CommonTree)adaptor.becomeRoot(ALTERNATIVE_CONNECTOR23_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_advice_activity_in_alternative_connector193);
            advice_activity24=advice_activity();

            state._fsp--;

            adaptor.addChild(root_1, advice_activity24.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_activity_pointcut_in_alternative_connector195);
            activity_pointcut25=activity_pointcut();

            state._fsp--;

            adaptor.addChild(root_1, activity_pointcut25.getTree());
            _last = (CommonTree)input.LT(1);
            STRING26=(CommonTree)match(input,STRING,FOLLOW_STRING_in_alternative_connector197); 
            STRING26_tree = (CommonTree)adaptor.dupNode(STRING26);

            adaptor.addChild(root_1, STRING26_tree);

            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:106:69: ( data_mapping )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==DATA_MAPPING) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:106:69: data_mapping
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_data_mapping_in_alternative_connector199);
            	    data_mapping27=data_mapping();

            	    state._fsp--;

            	    adaptor.addChild(root_1, data_mapping27.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			log.debug("Parsing alternative connector...");
            			String guard = STRING26.getText();
            			guard = guard.substring(1, guard.length() - 1);
            			this.connector = new AlternativeConnector(this.adviceActivity, this.activityPointcut, guard, this.dataMappings);
            			log.debug("Parsed alternative connector '" + this.connector + "'");
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "alternative_connector"

    public static class sync_branches_connector_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sync_branches_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:115:1: sync_branches_connector : ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* ) ;
    public final ConnectorTree.sync_branches_connector_return sync_branches_connector() throws RecognitionException {
        ConnectorTree.sync_branches_connector_return retval = new ConnectorTree.sync_branches_connector_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SYNC_BRANCHES_CONNECTOR28=null;
        ConnectorTree.advice_activity_return advice_activity29 = null;

        ConnectorTree.splitting_control_port_pointcut_return splitting_control_port_pointcut30 = null;

        ConnectorTree.joining_control_port_pointcut_return joining_control_port_pointcut31 = null;

        ConnectorTree.data_mapping_return data_mapping32 = null;


        CommonTree SYNC_BRANCHES_CONNECTOR28_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:116:2: ( ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:116:4: ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SYNC_BRANCHES_CONNECTOR28=(CommonTree)match(input,SYNC_BRANCHES_CONNECTOR,FOLLOW_SYNC_BRANCHES_CONNECTOR_in_sync_branches_connector215); 
            SYNC_BRANCHES_CONNECTOR28_tree = (CommonTree)adaptor.dupNode(SYNC_BRANCHES_CONNECTOR28);

            root_1 = (CommonTree)adaptor.becomeRoot(SYNC_BRANCHES_CONNECTOR28_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_advice_activity_in_sync_branches_connector217);
            advice_activity29=advice_activity();

            state._fsp--;

            adaptor.addChild(root_1, advice_activity29.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_splitting_control_port_pointcut_in_sync_branches_connector219);
            splitting_control_port_pointcut30=splitting_control_port_pointcut();

            state._fsp--;

            adaptor.addChild(root_1, splitting_control_port_pointcut30.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_joining_control_port_pointcut_in_sync_branches_connector221);
            joining_control_port_pointcut31=joining_control_port_pointcut();

            state._fsp--;

            adaptor.addChild(root_1, joining_control_port_pointcut31.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:116:108: ( data_mapping )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==DATA_MAPPING) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:116:108: data_mapping
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_data_mapping_in_sync_branches_connector223);
            	    data_mapping32=data_mapping();

            	    state._fsp--;

            	    adaptor.addChild(root_1, data_mapping32.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			log.debug("Parsing sync branches connector...");
            			this.connector = new SyncBranchesConnector(this.adviceActivity, this.splittingControlPortPointcut, this.joiningControlPortPointcut, this.dataMappings);
            			log.debug("Parsed sync branches connector '" + this.connector + "'");
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sync_branches_connector"

    public static class advice_activity_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "advice_activity"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:123:1: advice_activity : ^( ADVICE_ACTIVITY qualified_name ) ;
    public final ConnectorTree.advice_activity_return advice_activity() throws RecognitionException {
        ConnectorTree.advice_activity_return retval = new ConnectorTree.advice_activity_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ADVICE_ACTIVITY33=null;
        ConnectorTree.qualified_name_return qualified_name34 = null;


        CommonTree ADVICE_ACTIVITY33_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:124:2: ( ^( ADVICE_ACTIVITY qualified_name ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:124:4: ^( ADVICE_ACTIVITY qualified_name )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            ADVICE_ACTIVITY33=(CommonTree)match(input,ADVICE_ACTIVITY,FOLLOW_ADVICE_ACTIVITY_in_advice_activity240); 
            ADVICE_ACTIVITY33_tree = (CommonTree)adaptor.dupNode(ADVICE_ACTIVITY33);

            root_1 = (CommonTree)adaptor.becomeRoot(ADVICE_ACTIVITY33_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_qualified_name_in_advice_activity242);
            qualified_name34=qualified_name();

            state._fsp--;

            adaptor.addChild(root_1, qualified_name34.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			this.adviceActivity = this.qualifiedNames.get(0);
            			this.qualifiedNames = null;
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "advice_activity"

    public static class activity_pointcut_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "activity_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:130:1: activity_pointcut : ( ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) ) | ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) ) | ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) ) );
    public final ConnectorTree.activity_pointcut_return activity_pointcut() throws RecognitionException {
        ConnectorTree.activity_pointcut_return retval = new ConnectorTree.activity_pointcut_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ACTIVITY_POINTCUT35=null;
        CommonTree EXECUTING_ACTIVITY36=null;
        CommonTree STRING37=null;
        CommonTree ACTIVITY_POINTCUT38=null;
        CommonTree EXECUTING_COMPOSITE_ACTIVITY39=null;
        CommonTree STRING40=null;
        CommonTree ACTIVITY_POINTCUT41=null;
        CommonTree EXECUTING_ATOMIC_ACTIVITY42=null;
        CommonTree STRING43=null;

        CommonTree ACTIVITY_POINTCUT35_tree=null;
        CommonTree EXECUTING_ACTIVITY36_tree=null;
        CommonTree STRING37_tree=null;
        CommonTree ACTIVITY_POINTCUT38_tree=null;
        CommonTree EXECUTING_COMPOSITE_ACTIVITY39_tree=null;
        CommonTree STRING40_tree=null;
        CommonTree ACTIVITY_POINTCUT41_tree=null;
        CommonTree EXECUTING_ATOMIC_ACTIVITY42_tree=null;
        CommonTree STRING43_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:131:2: ( ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) ) | ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) ) | ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) ) )
            int alt8=3;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ACTIVITY_POINTCUT) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==DOWN) ) {
                    switch ( input.LA(3) ) {
                    case EXECUTING_ACTIVITY:
                        {
                        alt8=1;
                        }
                        break;
                    case EXECUTING_COMPOSITE_ACTIVITY:
                        {
                        alt8=2;
                        }
                        break;
                    case EXECUTING_ATOMIC_ACTIVITY:
                        {
                        alt8=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 2, input);

                        throw nvae;
                    }

                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:131:4: ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    ACTIVITY_POINTCUT35=(CommonTree)match(input,ACTIVITY_POINTCUT,FOLLOW_ACTIVITY_POINTCUT_in_activity_pointcut257); 
                    ACTIVITY_POINTCUT35_tree = (CommonTree)adaptor.dupNode(ACTIVITY_POINTCUT35);

                    root_1 = (CommonTree)adaptor.becomeRoot(ACTIVITY_POINTCUT35_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXECUTING_ACTIVITY36=(CommonTree)match(input,EXECUTING_ACTIVITY,FOLLOW_EXECUTING_ACTIVITY_in_activity_pointcut260); 
                    EXECUTING_ACTIVITY36_tree = (CommonTree)adaptor.dupNode(EXECUTING_ACTIVITY36);

                    root_2 = (CommonTree)adaptor.becomeRoot(EXECUTING_ACTIVITY36_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    STRING37=(CommonTree)match(input,STRING,FOLLOW_STRING_in_activity_pointcut262); 
                    STRING37_tree = (CommonTree)adaptor.dupNode(STRING37);

                    adaptor.addChild(root_2, STRING37_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing activity pointcut...");
                    			String expression = STRING37.getText();
                    			expression = expression.substring(1, expression.length() - 1);
                    			this.activityPointcut = new ActivityPointcut(expression);
                    			log.debug("Parsed activity pointcut '" + this.activityPointcut + "'");
                    		

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:138:4: ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    ACTIVITY_POINTCUT38=(CommonTree)match(input,ACTIVITY_POINTCUT,FOLLOW_ACTIVITY_POINTCUT_in_activity_pointcut272); 
                    ACTIVITY_POINTCUT38_tree = (CommonTree)adaptor.dupNode(ACTIVITY_POINTCUT38);

                    root_1 = (CommonTree)adaptor.becomeRoot(ACTIVITY_POINTCUT38_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXECUTING_COMPOSITE_ACTIVITY39=(CommonTree)match(input,EXECUTING_COMPOSITE_ACTIVITY,FOLLOW_EXECUTING_COMPOSITE_ACTIVITY_in_activity_pointcut275); 
                    EXECUTING_COMPOSITE_ACTIVITY39_tree = (CommonTree)adaptor.dupNode(EXECUTING_COMPOSITE_ACTIVITY39);

                    root_2 = (CommonTree)adaptor.becomeRoot(EXECUTING_COMPOSITE_ACTIVITY39_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    STRING40=(CommonTree)match(input,STRING,FOLLOW_STRING_in_activity_pointcut277); 
                    STRING40_tree = (CommonTree)adaptor.dupNode(STRING40);

                    adaptor.addChild(root_2, STRING40_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing composite activity pointcut...");
                    			String expression = STRING40.getText();
                    			expression = expression.substring(1, expression.length() - 1);
                    			this.activityPointcut = new CompositeActivityPointcut(expression);
                    			log.debug("Parsed composite activity pointcut '" + this.activityPointcut + "'");
                    		

                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:145:4: ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    ACTIVITY_POINTCUT41=(CommonTree)match(input,ACTIVITY_POINTCUT,FOLLOW_ACTIVITY_POINTCUT_in_activity_pointcut287); 
                    ACTIVITY_POINTCUT41_tree = (CommonTree)adaptor.dupNode(ACTIVITY_POINTCUT41);

                    root_1 = (CommonTree)adaptor.becomeRoot(ACTIVITY_POINTCUT41_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXECUTING_ATOMIC_ACTIVITY42=(CommonTree)match(input,EXECUTING_ATOMIC_ACTIVITY,FOLLOW_EXECUTING_ATOMIC_ACTIVITY_in_activity_pointcut290); 
                    EXECUTING_ATOMIC_ACTIVITY42_tree = (CommonTree)adaptor.dupNode(EXECUTING_ATOMIC_ACTIVITY42);

                    root_2 = (CommonTree)adaptor.becomeRoot(EXECUTING_ATOMIC_ACTIVITY42_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    STRING43=(CommonTree)match(input,STRING,FOLLOW_STRING_in_activity_pointcut292); 
                    STRING43_tree = (CommonTree)adaptor.dupNode(STRING43);

                    adaptor.addChild(root_2, STRING43_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing atomic activity pointcut...");
                    			String expression = STRING43.getText();
                    			expression = expression.substring(1, expression.length() - 1);
                    			this.activityPointcut = new AtomicActivityPointcut(expression);
                    			log.debug("Parsed atomic activity pointcut '" + this.activityPointcut + "'");
                    		

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "activity_pointcut"

    public static class data_mapping_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "data_mapping"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:154:1: data_mapping : ( ^( DATA_MAPPING variable_expression STRING ) | ^( DATA_MAPPING variable_expression variable_expression ) );
    public final ConnectorTree.data_mapping_return data_mapping() throws RecognitionException {
        ConnectorTree.data_mapping_return retval = new ConnectorTree.data_mapping_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree DATA_MAPPING44=null;
        CommonTree STRING46=null;
        CommonTree DATA_MAPPING47=null;
        ConnectorTree.variable_expression_return variable_expression45 = null;

        ConnectorTree.variable_expression_return variable_expression48 = null;

        ConnectorTree.variable_expression_return variable_expression49 = null;


        CommonTree DATA_MAPPING44_tree=null;
        CommonTree STRING46_tree=null;
        CommonTree DATA_MAPPING47_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:155:2: ( ^( DATA_MAPPING variable_expression STRING ) | ^( DATA_MAPPING variable_expression variable_expression ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:155:4: ^( DATA_MAPPING variable_expression STRING )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    DATA_MAPPING44=(CommonTree)match(input,DATA_MAPPING,FOLLOW_DATA_MAPPING_in_data_mapping308); 
                    DATA_MAPPING44_tree = (CommonTree)adaptor.dupNode(DATA_MAPPING44);

                    root_1 = (CommonTree)adaptor.becomeRoot(DATA_MAPPING44_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_variable_expression_in_data_mapping310);
                    variable_expression45=variable_expression();

                    state._fsp--;

                    adaptor.addChild(root_1, variable_expression45.getTree());
                    _last = (CommonTree)input.LT(1);
                    STRING46=(CommonTree)match(input,STRING,FOLLOW_STRING_in_data_mapping312); 
                    STRING46_tree = (CommonTree)adaptor.dupNode(STRING46);

                    adaptor.addChild(root_1, STRING46_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing data mapping of the form '^(DATA_MAPPING variable_expression STRING)'...");
                    			String stringConstant = STRING46.getText();
                    			stringConstant = stringConstant.substring(1, stringConstant.length() - 1);
                    			if (this.dataMappings == null) {
                    				this.dataMappings = new ArrayList<DataMapping>();
                    			}
                    			this.dataMappings.add(new DataMapping(this.variableExpressions.get(0), stringConstant));
                    			this.variableExpressions = null;
                    			log.debug("Parsed data mapping");
                    		

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:166:4: ^( DATA_MAPPING variable_expression variable_expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    DATA_MAPPING47=(CommonTree)match(input,DATA_MAPPING,FOLLOW_DATA_MAPPING_in_data_mapping321); 
                    DATA_MAPPING47_tree = (CommonTree)adaptor.dupNode(DATA_MAPPING47);

                    root_1 = (CommonTree)adaptor.becomeRoot(DATA_MAPPING47_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_variable_expression_in_data_mapping323);
                    variable_expression48=variable_expression();

                    state._fsp--;

                    adaptor.addChild(root_1, variable_expression48.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_variable_expression_in_data_mapping325);
                    variable_expression49=variable_expression();

                    state._fsp--;

                    adaptor.addChild(root_1, variable_expression49.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing data mapping of the form '^(DATA_MAPPING variable_expression variable_expression)'...");
                    			if (this.dataMappings == null) {
                    				this.dataMappings = new ArrayList<DataMapping>();
                    			}
                    			this.dataMappings.add(new DataMapping(this.variableExpressions.get(0), this.variableExpressions.get(1)));
                    			this.variableExpressions = null;
                    			log.debug("Parsed data mapping");
                    		

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "data_mapping"

    public static class variable_expression_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variable_expression"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:177:1: variable_expression : ( ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING1= STRING STRING2= STRING STRING3= STRING ) | ^( TYPE_VARIABLE qualified_name STRING1= STRING STRING2= STRING STRING3= STRING ) );
    public final ConnectorTree.variable_expression_return variable_expression() throws RecognitionException {
        ConnectorTree.variable_expression_return retval = new ConnectorTree.variable_expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree STRING1=null;
        CommonTree STRING2=null;
        CommonTree STRING3=null;
        CommonTree MESSAGE_TYPE_VARIABLE50=null;
        CommonTree TYPE_VARIABLE53=null;
        ConnectorTree.qualified_name_return qualified_name51 = null;

        ConnectorTree.qualified_name_return qualified_name52 = null;

        ConnectorTree.qualified_name_return qualified_name54 = null;


        CommonTree STRING1_tree=null;
        CommonTree STRING2_tree=null;
        CommonTree STRING3_tree=null;
        CommonTree MESSAGE_TYPE_VARIABLE50_tree=null;
        CommonTree TYPE_VARIABLE53_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:178:2: ( ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING1= STRING STRING2= STRING STRING3= STRING ) | ^( TYPE_VARIABLE qualified_name STRING1= STRING STRING2= STRING STRING3= STRING ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==MESSAGE_TYPE_VARIABLE) ) {
                alt10=1;
            }
            else if ( (LA10_0==TYPE_VARIABLE) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:178:4: ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING1= STRING STRING2= STRING STRING3= STRING )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    MESSAGE_TYPE_VARIABLE50=(CommonTree)match(input,MESSAGE_TYPE_VARIABLE,FOLLOW_MESSAGE_TYPE_VARIABLE_in_variable_expression340); 
                    MESSAGE_TYPE_VARIABLE50_tree = (CommonTree)adaptor.dupNode(MESSAGE_TYPE_VARIABLE50);

                    root_1 = (CommonTree)adaptor.becomeRoot(MESSAGE_TYPE_VARIABLE50_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_qualified_name_in_variable_expression342);
                    qualified_name51=qualified_name();

                    state._fsp--;

                    adaptor.addChild(root_1, qualified_name51.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_qualified_name_in_variable_expression344);
                    qualified_name52=qualified_name();

                    state._fsp--;

                    adaptor.addChild(root_1, qualified_name52.getTree());
                    _last = (CommonTree)input.LT(1);
                    STRING1=(CommonTree)match(input,STRING,FOLLOW_STRING_in_variable_expression348); 
                    STRING1_tree = (CommonTree)adaptor.dupNode(STRING1);

                    adaptor.addChild(root_1, STRING1_tree);

                    _last = (CommonTree)input.LT(1);
                    STRING2=(CommonTree)match(input,STRING,FOLLOW_STRING_in_variable_expression352); 
                    STRING2_tree = (CommonTree)adaptor.dupNode(STRING2);

                    adaptor.addChild(root_1, STRING2_tree);

                    _last = (CommonTree)input.LT(1);
                    STRING3=(CommonTree)match(input,STRING,FOLLOW_STRING_in_variable_expression356); 
                    STRING3_tree = (CommonTree)adaptor.dupNode(STRING3);

                    adaptor.addChild(root_1, STRING3_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing message type variable...");
                    			String query = STRING1.getText();
                    			query = query.substring(1, query.length() - 1);
                    			String queryNamespacePrefix = STRING2.getText();
                    			queryNamespacePrefix = queryNamespacePrefix.substring(1, queryNamespacePrefix.length() - 1);
                    			String queryNamespace = STRING3.getText();
                    			queryNamespace = queryNamespace.substring(1, queryNamespace.length() - 1);
                    			if (this.variableExpressions == null) {
                    				this.variableExpressions = new ArrayList<DataMapping.Variable>();
                    			}
                    			this.variableExpressions.add(new DataMapping.MessageTypeVariable(this.qualifiedNames.get(0), this.qualifiedNames.get(1), query, queryNamespacePrefix, queryNamespace));
                    			this.qualifiedNames = null;
                    			log.debug("Parsed message type variable");
                    		

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:193:4: ^( TYPE_VARIABLE qualified_name STRING1= STRING STRING2= STRING STRING3= STRING )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    TYPE_VARIABLE53=(CommonTree)match(input,TYPE_VARIABLE,FOLLOW_TYPE_VARIABLE_in_variable_expression365); 
                    TYPE_VARIABLE53_tree = (CommonTree)adaptor.dupNode(TYPE_VARIABLE53);

                    root_1 = (CommonTree)adaptor.becomeRoot(TYPE_VARIABLE53_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_qualified_name_in_variable_expression367);
                    qualified_name54=qualified_name();

                    state._fsp--;

                    adaptor.addChild(root_1, qualified_name54.getTree());
                    _last = (CommonTree)input.LT(1);
                    STRING1=(CommonTree)match(input,STRING,FOLLOW_STRING_in_variable_expression371); 
                    STRING1_tree = (CommonTree)adaptor.dupNode(STRING1);

                    adaptor.addChild(root_1, STRING1_tree);

                    _last = (CommonTree)input.LT(1);
                    STRING2=(CommonTree)match(input,STRING,FOLLOW_STRING_in_variable_expression375); 
                    STRING2_tree = (CommonTree)adaptor.dupNode(STRING2);

                    adaptor.addChild(root_1, STRING2_tree);

                    _last = (CommonTree)input.LT(1);
                    STRING3=(CommonTree)match(input,STRING,FOLLOW_STRING_in_variable_expression379); 
                    STRING3_tree = (CommonTree)adaptor.dupNode(STRING3);

                    adaptor.addChild(root_1, STRING3_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing type variable...");
                    			String query = STRING1.getText();
                    			query = query.substring(1, query.length() - 1);
                    			String queryNamespacePrefix = STRING2.getText();
                    			queryNamespacePrefix = queryNamespacePrefix.substring(1, queryNamespacePrefix.length() - 1);
                    			String queryNamespace = STRING3.getText();
                    			queryNamespace = queryNamespace.substring(1, queryNamespace.length() - 1);
                    			if (this.variableExpressions == null) {
                    				this.variableExpressions = new ArrayList<DataMapping.Variable>();
                    			}
                    			this.variableExpressions.add(new DataMapping.TypeVariable(this.qualifiedNames.get(0), query, queryNamespacePrefix, queryNamespace));
                    			this.qualifiedNames = null;
                    			log.debug("Parsed type variable");
                    		

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "variable_expression"

    public static class splitting_control_port_pointcut_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "splitting_control_port_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:210:1: splitting_control_port_pointcut : control_port_pointcut ;
    public final ConnectorTree.splitting_control_port_pointcut_return splitting_control_port_pointcut() throws RecognitionException {
        ConnectorTree.splitting_control_port_pointcut_return retval = new ConnectorTree.splitting_control_port_pointcut_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ConnectorTree.control_port_pointcut_return control_port_pointcut55 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:211:2: ( control_port_pointcut )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:211:4: control_port_pointcut
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_control_port_pointcut_in_splitting_control_port_pointcut393);
            control_port_pointcut55=control_port_pointcut();

            state._fsp--;

            adaptor.addChild(root_0, control_port_pointcut55.getTree());

            			this.splittingControlPortPointcut = this.controlPortPointcut;
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "splitting_control_port_pointcut"

    public static class joining_control_port_pointcut_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "joining_control_port_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:216:1: joining_control_port_pointcut : control_port_pointcut ;
    public final ConnectorTree.joining_control_port_pointcut_return joining_control_port_pointcut() throws RecognitionException {
        ConnectorTree.joining_control_port_pointcut_return retval = new ConnectorTree.joining_control_port_pointcut_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ConnectorTree.control_port_pointcut_return control_port_pointcut56 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:217:2: ( control_port_pointcut )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:217:4: control_port_pointcut
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_control_port_pointcut_in_joining_control_port_pointcut406);
            control_port_pointcut56=control_port_pointcut();

            state._fsp--;

            adaptor.addChild(root_0, control_port_pointcut56.getTree());

            			this.joiningControlPortPointcut = this.controlPortPointcut;
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "joining_control_port_pointcut"

    public static class control_port_pointcut_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "control_port_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:222:1: control_port_pointcut : ( ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) ) | ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) ) | ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) ) );
    public final ConnectorTree.control_port_pointcut_return control_port_pointcut() throws RecognitionException {
        ConnectorTree.control_port_pointcut_return retval = new ConnectorTree.control_port_pointcut_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree CONTROL_PORT_POINTCUT57=null;
        CommonTree CONTROL_PORT58=null;
        CommonTree CONTROL_PORT_POINTCUT60=null;
        CommonTree CONTROL_INPUT_PORT61=null;
        CommonTree CONTROL_PORT_POINTCUT63=null;
        CommonTree CONTROL_OUTPUT_PORT64=null;
        ConnectorTree.qualified_name_return qualified_name59 = null;

        ConnectorTree.qualified_name_return qualified_name62 = null;

        ConnectorTree.qualified_name_return qualified_name65 = null;


        CommonTree CONTROL_PORT_POINTCUT57_tree=null;
        CommonTree CONTROL_PORT58_tree=null;
        CommonTree CONTROL_PORT_POINTCUT60_tree=null;
        CommonTree CONTROL_INPUT_PORT61_tree=null;
        CommonTree CONTROL_PORT_POINTCUT63_tree=null;
        CommonTree CONTROL_OUTPUT_PORT64_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:223:2: ( ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) ) | ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) ) | ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) ) )
            int alt11=3;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==CONTROL_PORT_POINTCUT) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==DOWN) ) {
                    switch ( input.LA(3) ) {
                    case CONTROL_PORT:
                        {
                        alt11=1;
                        }
                        break;
                    case CONTROL_INPUT_PORT:
                        {
                        alt11=2;
                        }
                        break;
                    case CONTROL_OUTPUT_PORT:
                        {
                        alt11=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 2, input);

                        throw nvae;
                    }

                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:223:4: ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    CONTROL_PORT_POINTCUT57=(CommonTree)match(input,CONTROL_PORT_POINTCUT,FOLLOW_CONTROL_PORT_POINTCUT_in_control_port_pointcut420); 
                    CONTROL_PORT_POINTCUT57_tree = (CommonTree)adaptor.dupNode(CONTROL_PORT_POINTCUT57);

                    root_1 = (CommonTree)adaptor.becomeRoot(CONTROL_PORT_POINTCUT57_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    CONTROL_PORT58=(CommonTree)match(input,CONTROL_PORT,FOLLOW_CONTROL_PORT_in_control_port_pointcut423); 
                    CONTROL_PORT58_tree = (CommonTree)adaptor.dupNode(CONTROL_PORT58);

                    root_2 = (CommonTree)adaptor.becomeRoot(CONTROL_PORT58_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_qualified_name_in_control_port_pointcut425);
                    qualified_name59=qualified_name();

                    state._fsp--;

                    adaptor.addChild(root_2, qualified_name59.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing control port pointcut...");
                    			this.controlPortPointcut = new ControlPortPointcut(this.qualifiedNames.get(0));
                    			this.qualifiedNames = null;
                    			log.debug("Parsed control port pointcut '" + this.controlPortPointcut + "'");
                    		

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:229:4: ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    CONTROL_PORT_POINTCUT60=(CommonTree)match(input,CONTROL_PORT_POINTCUT,FOLLOW_CONTROL_PORT_POINTCUT_in_control_port_pointcut435); 
                    CONTROL_PORT_POINTCUT60_tree = (CommonTree)adaptor.dupNode(CONTROL_PORT_POINTCUT60);

                    root_1 = (CommonTree)adaptor.becomeRoot(CONTROL_PORT_POINTCUT60_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    CONTROL_INPUT_PORT61=(CommonTree)match(input,CONTROL_INPUT_PORT,FOLLOW_CONTROL_INPUT_PORT_in_control_port_pointcut438); 
                    CONTROL_INPUT_PORT61_tree = (CommonTree)adaptor.dupNode(CONTROL_INPUT_PORT61);

                    root_2 = (CommonTree)adaptor.becomeRoot(CONTROL_INPUT_PORT61_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_qualified_name_in_control_port_pointcut440);
                    qualified_name62=qualified_name();

                    state._fsp--;

                    adaptor.addChild(root_2, qualified_name62.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing control input port pointcut...");
                    			this.controlPortPointcut = new ControlInputPortPointcut(this.qualifiedNames.get(0));
                    			this.qualifiedNames = null;
                    			log.debug("Parsed control input port pointcut '" + this.controlPortPointcut + "'");
                    		

                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:235:4: ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    CONTROL_PORT_POINTCUT63=(CommonTree)match(input,CONTROL_PORT_POINTCUT,FOLLOW_CONTROL_PORT_POINTCUT_in_control_port_pointcut450); 
                    CONTROL_PORT_POINTCUT63_tree = (CommonTree)adaptor.dupNode(CONTROL_PORT_POINTCUT63);

                    root_1 = (CommonTree)adaptor.becomeRoot(CONTROL_PORT_POINTCUT63_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    CONTROL_OUTPUT_PORT64=(CommonTree)match(input,CONTROL_OUTPUT_PORT,FOLLOW_CONTROL_OUTPUT_PORT_in_control_port_pointcut453); 
                    CONTROL_OUTPUT_PORT64_tree = (CommonTree)adaptor.dupNode(CONTROL_OUTPUT_PORT64);

                    root_2 = (CommonTree)adaptor.becomeRoot(CONTROL_OUTPUT_PORT64_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_qualified_name_in_control_port_pointcut455);
                    qualified_name65=qualified_name();

                    state._fsp--;

                    adaptor.addChild(root_2, qualified_name65.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			log.debug("Parsing control output port pointcut...");
                    			this.controlPortPointcut = new ControlOutputPortPointcut(this.qualifiedNames.get(0));
                    			this.qualifiedNames = null;
                    			log.debug("Parsed control output port pointcut '" + this.controlPortPointcut + "'");
                    		

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "control_port_pointcut"

    public static class qualified_name_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "qualified_name"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:243:1: qualified_name : ^( QUALIFIED_NAME (identifiers+= IDENTIFIER )* ) ;
    public final ConnectorTree.qualified_name_return qualified_name() throws RecognitionException {
        ConnectorTree.qualified_name_return retval = new ConnectorTree.qualified_name_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree QUALIFIED_NAME66=null;
        CommonTree identifiers=null;
        List list_identifiers=null;

        CommonTree QUALIFIED_NAME66_tree=null;
        CommonTree identifiers_tree=null;

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:244:2: ( ^( QUALIFIED_NAME (identifiers+= IDENTIFIER )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:244:4: ^( QUALIFIED_NAME (identifiers+= IDENTIFIER )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            QUALIFIED_NAME66=(CommonTree)match(input,QUALIFIED_NAME,FOLLOW_QUALIFIED_NAME_in_qualified_name471); 
            QUALIFIED_NAME66_tree = (CommonTree)adaptor.dupNode(QUALIFIED_NAME66);

            root_1 = (CommonTree)adaptor.becomeRoot(QUALIFIED_NAME66_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:244:21: (identifiers+= IDENTIFIER )*
                loop12:
                do {
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==IDENTIFIER) ) {
                        alt12=1;
                    }


                    switch (alt12) {
                	case 1 :
                	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/ConnectorTree.g:244:22: identifiers+= IDENTIFIER
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    identifiers=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_qualified_name476); 
                	    identifiers_tree = (CommonTree)adaptor.dupNode(identifiers);

                	    adaptor.addChild(root_1, identifiers_tree);

                	    if (list_identifiers==null) list_identifiers=new ArrayList();
                	    list_identifiers.add(identifiers);


                	    }
                	    break;

                	default :
                	    break loop12;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			log.debug("Parsing qualified name...");
            			QualifiedName qualifiedName = new QualifiedName();
            			for (Object identifier : list_identifiers) {
            				qualifiedName.addIdentifier(((CommonTree) identifier).getText());
            			}
            			if (this.qualifiedNames == null) {
            				this.qualifiedNames = new ArrayList<QualifiedName>();
            			}
            			this.qualifiedNames.add(qualifiedName);
            			log.debug("Parsed qualified name '" + qualifiedName + "'");
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "qualified_name"

    // Delegated rules


    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA9_eotS =
        "\35\uffff";
    static final String DFA9_eofS =
        "\35\uffff";
    static final String DFA9_minS =
        "\1\25\1\2\1\26\2\2\2\40\2\2\3\3\1\40\1\3\1\41\1\2\1\41\1\3\1\41"+
        "\1\3\1\41\1\3\1\41\1\26\1\41\2\uffff\1\3\1\26";
    static final String DFA9_maxS =
        "\1\25\1\2\1\27\2\2\2\40\2\2\3\42\1\40\1\42\1\41\1\2\1\41\1\42\1"+
        "\41\1\42\1\41\1\3\3\41\2\uffff\1\3\1\41";
    static final String DFA9_acceptS =
        "\31\uffff\1\1\1\2\2\uffff";
    static final String DFA9_specialS =
        "\35\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1",
            "\1\2",
            "\1\3\1\4",
            "\1\5",
            "\1\6",
            "\1\7",
            "\1\10",
            "\1\11",
            "\1\12",
            "\1\14\36\uffff\1\13",
            "\1\16\36\uffff\1\15",
            "\1\14\36\uffff\1\13",
            "\1\17",
            "\1\16\36\uffff\1\15",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\24\36\uffff\1\23",
            "\1\25",
            "\1\24\36\uffff\1\23",
            "\1\26",
            "\1\27",
            "\1\30",
            "\2\32\11\uffff\1\31",
            "\1\33",
            "",
            "",
            "\1\34",
            "\2\32\11\uffff\1\31"
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "154:1: data_mapping : ( ^( DATA_MAPPING variable_expression STRING ) | ^( DATA_MAPPING variable_expression variable_expression ) );";
        }
    }
 

    public static final BitSet FOLLOW_hierarchical_decomposition_connector_in_connector48 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inversion_of_control_connector_in_connector53 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HIERARCHICAL_DECOMPOSITION_CONNECTOR_in_hierarchical_decomposition_connector65 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_calling_activity_in_hierarchical_decomposition_connector67 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_called_activity_in_hierarchical_decomposition_connector69 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_data_mapping_in_hierarchical_decomposition_connector71 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_CALLING_ACTIVITY_in_calling_activity87 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_calling_activity89 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CALLED_ACTIVITY_in_called_activity104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_called_activity106 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_after_connector_in_inversion_of_control_connector120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parallel_connector_in_inversion_of_control_connector125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alternative_connector_in_inversion_of_control_connector130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sync_branches_connector_in_inversion_of_control_connector135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AFTER_CONNECTOR_in_after_connector147 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_advice_activity_in_after_connector149 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_activity_pointcut_in_after_connector151 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_data_mapping_in_after_connector153 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_PARALLEL_CONNECTOR_in_parallel_connector169 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_advice_activity_in_parallel_connector171 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_activity_pointcut_in_parallel_connector173 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_data_mapping_in_parallel_connector175 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_ALTERNATIVE_CONNECTOR_in_alternative_connector191 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_advice_activity_in_alternative_connector193 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_activity_pointcut_in_alternative_connector195 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_alternative_connector197 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_data_mapping_in_alternative_connector199 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_SYNC_BRANCHES_CONNECTOR_in_sync_branches_connector215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_advice_activity_in_sync_branches_connector217 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_splitting_control_port_pointcut_in_sync_branches_connector219 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_joining_control_port_pointcut_in_sync_branches_connector221 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_data_mapping_in_sync_branches_connector223 = new BitSet(new long[]{0x0000000000200008L});
    public static final BitSet FOLLOW_ADVICE_ACTIVITY_in_advice_activity240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_advice_activity242 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ACTIVITY_POINTCUT_in_activity_pointcut257 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_EXECUTING_ACTIVITY_in_activity_pointcut260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_activity_pointcut262 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ACTIVITY_POINTCUT_in_activity_pointcut272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_EXECUTING_COMPOSITE_ACTIVITY_in_activity_pointcut275 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_activity_pointcut277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ACTIVITY_POINTCUT_in_activity_pointcut287 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_EXECUTING_ATOMIC_ACTIVITY_in_activity_pointcut290 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_activity_pointcut292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATA_MAPPING_in_data_mapping308 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_expression_in_data_mapping310 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_data_mapping312 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATA_MAPPING_in_data_mapping321 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_expression_in_data_mapping323 = new BitSet(new long[]{0x0000000000C00000L});
    public static final BitSet FOLLOW_variable_expression_in_data_mapping325 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MESSAGE_TYPE_VARIABLE_in_variable_expression340 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_variable_expression342 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_qualified_name_in_variable_expression344 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression348 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression352 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression356 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_VARIABLE_in_variable_expression365 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_variable_expression367 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression371 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression375 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression379 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_control_port_pointcut_in_splitting_control_port_pointcut393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_control_port_pointcut_in_joining_control_port_pointcut406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONTROL_PORT_POINTCUT_in_control_port_pointcut420 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CONTROL_PORT_in_control_port_pointcut423 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_control_port_pointcut425 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONTROL_PORT_POINTCUT_in_control_port_pointcut435 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CONTROL_INPUT_PORT_in_control_port_pointcut438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_control_port_pointcut440 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONTROL_PORT_POINTCUT_in_control_port_pointcut450 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CONTROL_OUTPUT_PORT_in_control_port_pointcut453 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_qualified_name_in_control_port_pointcut455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_QUALIFIED_NAME_in_qualified_name471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_qualified_name476 = new BitSet(new long[]{0x0000000400000008L});

}
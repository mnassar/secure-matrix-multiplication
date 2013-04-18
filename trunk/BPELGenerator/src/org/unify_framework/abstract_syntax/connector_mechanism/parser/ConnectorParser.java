// $ANTLR 3.3 Nov 30, 2010 12:45:30 /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g 2011-10-28 15:48:17
 package org.unify_framework.abstract_syntax.connector_mechanism.parser; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

@SuppressWarnings("all")
public class ConnectorParser extends Parser {
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


        public ConnectorParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ConnectorParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ConnectorParser.tokenNames; }
    public String getGrammarFileName() { return "/Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g"; }


    public static class connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:47:1: connector : ( hierarchical_decomposition_connector | inversion_of_control_connector );
    public final ConnectorParser.connector_return connector() throws RecognitionException {
        ConnectorParser.connector_return retval = new ConnectorParser.connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.hierarchical_decomposition_connector_return hierarchical_decomposition_connector1 = null;

        ConnectorParser.inversion_of_control_connector_return inversion_of_control_connector2 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:48:2: ( hierarchical_decomposition_connector | inversion_of_control_connector )
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:48:4: hierarchical_decomposition_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_hierarchical_decomposition_connector_in_connector220);
                    hierarchical_decomposition_connector1=hierarchical_decomposition_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, hierarchical_decomposition_connector1.getTree());

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:49:4: inversion_of_control_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_inversion_of_control_connector_in_connector225);
                    inversion_of_control_connector2=inversion_of_control_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, inversion_of_control_connector2.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "connector"

    public static class hierarchical_decomposition_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "hierarchical_decomposition_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:54:1: hierarchical_decomposition_connector : 'CONNECT' calling_activity 'TO' called_activity ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* ) ;
    public final ConnectorParser.hierarchical_decomposition_connector_return hierarchical_decomposition_connector() throws RecognitionException {
        ConnectorParser.hierarchical_decomposition_connector_return retval = new ConnectorParser.hierarchical_decomposition_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal3=null;
        Token string_literal5=null;
        Token string_literal7=null;
        Token char_literal9=null;
        ConnectorParser.calling_activity_return calling_activity4 = null;

        ConnectorParser.called_activity_return called_activity6 = null;

        ConnectorParser.data_mapping_return data_mapping8 = null;

        ConnectorParser.data_mapping_return data_mapping10 = null;


        Object string_literal3_tree=null;
        Object string_literal5_tree=null;
        Object string_literal7_tree=null;
        Object char_literal9_tree=null;
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_calling_activity=new RewriteRuleSubtreeStream(adaptor,"rule calling_activity");
        RewriteRuleSubtreeStream stream_called_activity=new RewriteRuleSubtreeStream(adaptor,"rule called_activity");
        RewriteRuleSubtreeStream stream_data_mapping=new RewriteRuleSubtreeStream(adaptor,"rule data_mapping");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:2: ( 'CONNECT' calling_activity 'TO' called_activity ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:4: 'CONNECT' calling_activity 'TO' called_activity ( 'WITH' data_mapping ( ',' data_mapping )* )?
            {
            string_literal3=(Token)match(input,36,FOLLOW_36_in_hierarchical_decomposition_connector238);  
            stream_36.add(string_literal3);

            pushFollow(FOLLOW_calling_activity_in_hierarchical_decomposition_connector240);
            calling_activity4=calling_activity();

            state._fsp--;

            stream_calling_activity.add(calling_activity4.getTree());
            string_literal5=(Token)match(input,37,FOLLOW_37_in_hierarchical_decomposition_connector242);  
            stream_37.add(string_literal5);

            pushFollow(FOLLOW_called_activity_in_hierarchical_decomposition_connector244);
            called_activity6=called_activity();

            state._fsp--;

            stream_called_activity.add(called_activity6.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:52: ( 'WITH' data_mapping ( ',' data_mapping )* )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==38) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:53: 'WITH' data_mapping ( ',' data_mapping )*
                    {
                    string_literal7=(Token)match(input,38,FOLLOW_38_in_hierarchical_decomposition_connector247);  
                    stream_38.add(string_literal7);

                    pushFollow(FOLLOW_data_mapping_in_hierarchical_decomposition_connector249);
                    data_mapping8=data_mapping();

                    state._fsp--;

                    stream_data_mapping.add(data_mapping8.getTree());
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:73: ( ',' data_mapping )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==39) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:74: ',' data_mapping
                    	    {
                    	    char_literal9=(Token)match(input,39,FOLLOW_39_in_hierarchical_decomposition_connector252);  
                    	    stream_39.add(char_literal9);

                    	    pushFollow(FOLLOW_data_mapping_in_hierarchical_decomposition_connector254);
                    	    data_mapping10=data_mapping();

                    	    state._fsp--;

                    	    stream_data_mapping.add(data_mapping10.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    }
                    break;

            }



            // AST REWRITE
            // elements: calling_activity, called_activity, data_mapping
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 55:95: -> ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:98: ^( HIERARCHICAL_DECOMPOSITION_CONNECTOR calling_activity called_activity ( data_mapping )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(HIERARCHICAL_DECOMPOSITION_CONNECTOR, "HIERARCHICAL_DECOMPOSITION_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_calling_activity.nextTree());
                adaptor.addChild(root_1, stream_called_activity.nextTree());
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:55:170: ( data_mapping )*
                while ( stream_data_mapping.hasNext() ) {
                    adaptor.addChild(root_1, stream_data_mapping.nextTree());

                }
                stream_data_mapping.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "hierarchical_decomposition_connector"

    public static class calling_activity_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "calling_activity"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:58:1: calling_activity : qualified_name -> ^( CALLING_ACTIVITY qualified_name ) ;
    public final ConnectorParser.calling_activity_return calling_activity() throws RecognitionException {
        ConnectorParser.calling_activity_return retval = new ConnectorParser.calling_activity_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.qualified_name_return qualified_name11 = null;


        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:59:2: ( qualified_name -> ^( CALLING_ACTIVITY qualified_name ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:59:4: qualified_name
            {
            pushFollow(FOLLOW_qualified_name_in_calling_activity282);
            qualified_name11=qualified_name();

            state._fsp--;

            stream_qualified_name.add(qualified_name11.getTree());


            // AST REWRITE
            // elements: qualified_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 59:19: -> ^( CALLING_ACTIVITY qualified_name )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:59:22: ^( CALLING_ACTIVITY qualified_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CALLING_ACTIVITY, "CALLING_ACTIVITY"), root_1);

                adaptor.addChild(root_1, stream_qualified_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "calling_activity"

    public static class called_activity_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "called_activity"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:62:1: called_activity : qualified_name -> ^( CALLED_ACTIVITY qualified_name ) ;
    public final ConnectorParser.called_activity_return called_activity() throws RecognitionException {
        ConnectorParser.called_activity_return retval = new ConnectorParser.called_activity_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.qualified_name_return qualified_name12 = null;


        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:63:2: ( qualified_name -> ^( CALLED_ACTIVITY qualified_name ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:63:4: qualified_name
            {
            pushFollow(FOLLOW_qualified_name_in_called_activity301);
            qualified_name12=qualified_name();

            state._fsp--;

            stream_qualified_name.add(qualified_name12.getTree());


            // AST REWRITE
            // elements: qualified_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 63:19: -> ^( CALLED_ACTIVITY qualified_name )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:63:22: ^( CALLED_ACTIVITY qualified_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CALLED_ACTIVITY, "CALLED_ACTIVITY"), root_1);

                adaptor.addChild(root_1, stream_qualified_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "called_activity"

    public static class inversion_of_control_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inversion_of_control_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:68:1: inversion_of_control_connector : ( before_connector | after_connector | around_connector | parallel_connector | alternative_connector | iterating_connector | new_branch_connector | sync_branches_connector | switch_branches_connector );
    public final ConnectorParser.inversion_of_control_connector_return inversion_of_control_connector() throws RecognitionException {
        ConnectorParser.inversion_of_control_connector_return retval = new ConnectorParser.inversion_of_control_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.before_connector_return before_connector13 = null;

        ConnectorParser.after_connector_return after_connector14 = null;

        ConnectorParser.around_connector_return around_connector15 = null;

        ConnectorParser.parallel_connector_return parallel_connector16 = null;

        ConnectorParser.alternative_connector_return alternative_connector17 = null;

        ConnectorParser.iterating_connector_return iterating_connector18 = null;

        ConnectorParser.new_branch_connector_return new_branch_connector19 = null;

        ConnectorParser.sync_branches_connector_return sync_branches_connector20 = null;

        ConnectorParser.switch_branches_connector_return switch_branches_connector21 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:69:2: ( before_connector | after_connector | around_connector | parallel_connector | alternative_connector | iterating_connector | new_branch_connector | sync_branches_connector | switch_branches_connector )
            int alt4=9;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:69:4: before_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_before_connector_in_inversion_of_control_connector322);
                    before_connector13=before_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, before_connector13.getTree());

                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:70:4: after_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_after_connector_in_inversion_of_control_connector327);
                    after_connector14=after_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, after_connector14.getTree());

                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:71:4: around_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_around_connector_in_inversion_of_control_connector332);
                    around_connector15=around_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, around_connector15.getTree());

                    }
                    break;
                case 4 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:72:4: parallel_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_parallel_connector_in_inversion_of_control_connector337);
                    parallel_connector16=parallel_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, parallel_connector16.getTree());

                    }
                    break;
                case 5 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:73:4: alternative_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_alternative_connector_in_inversion_of_control_connector342);
                    alternative_connector17=alternative_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, alternative_connector17.getTree());

                    }
                    break;
                case 6 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:74:4: iterating_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_iterating_connector_in_inversion_of_control_connector347);
                    iterating_connector18=iterating_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, iterating_connector18.getTree());

                    }
                    break;
                case 7 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:75:4: new_branch_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_new_branch_connector_in_inversion_of_control_connector352);
                    new_branch_connector19=new_branch_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, new_branch_connector19.getTree());

                    }
                    break;
                case 8 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:76:4: sync_branches_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sync_branches_connector_in_inversion_of_control_connector357);
                    sync_branches_connector20=sync_branches_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, sync_branches_connector20.getTree());

                    }
                    break;
                case 9 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:77:4: switch_branches_connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_switch_branches_connector_in_inversion_of_control_connector362);
                    switch_branches_connector21=switch_branches_connector();

                    state._fsp--;

                    adaptor.addChild(root_0, switch_branches_connector21.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "inversion_of_control_connector"

    public static class before_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "before_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:80:1: before_connector : 'CONNECT' advice_activity 'BEFORE' activity_pointcut -> ^( BEFORE_CONNECTOR advice_activity activity_pointcut ) ;
    public final ConnectorParser.before_connector_return before_connector() throws RecognitionException {
        ConnectorParser.before_connector_return retval = new ConnectorParser.before_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal22=null;
        Token string_literal24=null;
        ConnectorParser.advice_activity_return advice_activity23 = null;

        ConnectorParser.activity_pointcut_return activity_pointcut25 = null;


        Object string_literal22_tree=null;
        Object string_literal24_tree=null;
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleSubtreeStream stream_activity_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule activity_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:81:2: ( 'CONNECT' advice_activity 'BEFORE' activity_pointcut -> ^( BEFORE_CONNECTOR advice_activity activity_pointcut ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:81:4: 'CONNECT' advice_activity 'BEFORE' activity_pointcut
            {
            string_literal22=(Token)match(input,36,FOLLOW_36_in_before_connector373);  
            stream_36.add(string_literal22);

            pushFollow(FOLLOW_advice_activity_in_before_connector375);
            advice_activity23=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity23.getTree());
            string_literal24=(Token)match(input,40,FOLLOW_40_in_before_connector377);  
            stream_40.add(string_literal24);

            pushFollow(FOLLOW_activity_pointcut_in_before_connector379);
            activity_pointcut25=activity_pointcut();

            state._fsp--;

            stream_activity_pointcut.add(activity_pointcut25.getTree());


            // AST REWRITE
            // elements: advice_activity, activity_pointcut
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 81:57: -> ^( BEFORE_CONNECTOR advice_activity activity_pointcut )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:81:60: ^( BEFORE_CONNECTOR advice_activity activity_pointcut )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BEFORE_CONNECTOR, "BEFORE_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_activity_pointcut.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "before_connector"

    public static class after_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "after_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:84:1: after_connector : 'CONNECT' advice_activity 'AFTER' activity_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) ;
    public final ConnectorParser.after_connector_return after_connector() throws RecognitionException {
        ConnectorParser.after_connector_return retval = new ConnectorParser.after_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal26=null;
        Token string_literal28=null;
        Token string_literal30=null;
        Token char_literal32=null;
        ConnectorParser.advice_activity_return advice_activity27 = null;

        ConnectorParser.activity_pointcut_return activity_pointcut29 = null;

        ConnectorParser.data_mapping_return data_mapping31 = null;

        ConnectorParser.data_mapping_return data_mapping33 = null;


        Object string_literal26_tree=null;
        Object string_literal28_tree=null;
        Object string_literal30_tree=null;
        Object char_literal32_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_activity_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule activity_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        RewriteRuleSubtreeStream stream_data_mapping=new RewriteRuleSubtreeStream(adaptor,"rule data_mapping");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:2: ( 'CONNECT' advice_activity 'AFTER' activity_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:4: 'CONNECT' advice_activity 'AFTER' activity_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )?
            {
            string_literal26=(Token)match(input,36,FOLLOW_36_in_after_connector400);  
            stream_36.add(string_literal26);

            pushFollow(FOLLOW_advice_activity_in_after_connector402);
            advice_activity27=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity27.getTree());
            string_literal28=(Token)match(input,41,FOLLOW_41_in_after_connector404);  
            stream_41.add(string_literal28);

            pushFollow(FOLLOW_activity_pointcut_in_after_connector406);
            activity_pointcut29=activity_pointcut();

            state._fsp--;

            stream_activity_pointcut.add(activity_pointcut29.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:56: ( 'WITH' data_mapping ( ',' data_mapping )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==38) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:57: 'WITH' data_mapping ( ',' data_mapping )*
                    {
                    string_literal30=(Token)match(input,38,FOLLOW_38_in_after_connector409);  
                    stream_38.add(string_literal30);

                    pushFollow(FOLLOW_data_mapping_in_after_connector411);
                    data_mapping31=data_mapping();

                    state._fsp--;

                    stream_data_mapping.add(data_mapping31.getTree());
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:77: ( ',' data_mapping )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==39) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:78: ',' data_mapping
                    	    {
                    	    char_literal32=(Token)match(input,39,FOLLOW_39_in_after_connector414);  
                    	    stream_39.add(char_literal32);

                    	    pushFollow(FOLLOW_data_mapping_in_after_connector416);
                    	    data_mapping33=data_mapping();

                    	    state._fsp--;

                    	    stream_data_mapping.add(data_mapping33.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }



            // AST REWRITE
            // elements: data_mapping, activity_pointcut, advice_activity
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 85:99: -> ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:102: ^( AFTER_CONNECTOR advice_activity activity_pointcut ( data_mapping )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(AFTER_CONNECTOR, "AFTER_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_activity_pointcut.nextTree());
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:85:154: ( data_mapping )*
                while ( stream_data_mapping.hasNext() ) {
                    adaptor.addChild(root_1, stream_data_mapping.nextTree());

                }
                stream_data_mapping.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "after_connector"

    public static class around_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "around_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:88:1: around_connector : 'CONNECT' advice_activity 'AROUND' activity_pointcut -> ^( AROUND_CONNECTOR advice_activity activity_pointcut ) ;
    public final ConnectorParser.around_connector_return around_connector() throws RecognitionException {
        ConnectorParser.around_connector_return retval = new ConnectorParser.around_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal34=null;
        Token string_literal36=null;
        ConnectorParser.advice_activity_return advice_activity35 = null;

        ConnectorParser.activity_pointcut_return activity_pointcut37 = null;


        Object string_literal34_tree=null;
        Object string_literal36_tree=null;
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleSubtreeStream stream_activity_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule activity_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:89:2: ( 'CONNECT' advice_activity 'AROUND' activity_pointcut -> ^( AROUND_CONNECTOR advice_activity activity_pointcut ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:89:4: 'CONNECT' advice_activity 'AROUND' activity_pointcut
            {
            string_literal34=(Token)match(input,36,FOLLOW_36_in_around_connector444);  
            stream_36.add(string_literal34);

            pushFollow(FOLLOW_advice_activity_in_around_connector446);
            advice_activity35=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity35.getTree());
            string_literal36=(Token)match(input,42,FOLLOW_42_in_around_connector448);  
            stream_42.add(string_literal36);

            pushFollow(FOLLOW_activity_pointcut_in_around_connector450);
            activity_pointcut37=activity_pointcut();

            state._fsp--;

            stream_activity_pointcut.add(activity_pointcut37.getTree());


            // AST REWRITE
            // elements: activity_pointcut, advice_activity
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 89:57: -> ^( AROUND_CONNECTOR advice_activity activity_pointcut )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:89:60: ^( AROUND_CONNECTOR advice_activity activity_pointcut )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(AROUND_CONNECTOR, "AROUND_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_activity_pointcut.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "around_connector"

    public static class parallel_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parallel_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:92:1: parallel_connector : 'CONNECT' advice_activity 'PARALLEL TO' activity_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) ;
    public final ConnectorParser.parallel_connector_return parallel_connector() throws RecognitionException {
        ConnectorParser.parallel_connector_return retval = new ConnectorParser.parallel_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal38=null;
        Token string_literal40=null;
        Token string_literal42=null;
        Token char_literal44=null;
        ConnectorParser.advice_activity_return advice_activity39 = null;

        ConnectorParser.activity_pointcut_return activity_pointcut41 = null;

        ConnectorParser.data_mapping_return data_mapping43 = null;

        ConnectorParser.data_mapping_return data_mapping45 = null;


        Object string_literal38_tree=null;
        Object string_literal40_tree=null;
        Object string_literal42_tree=null;
        Object char_literal44_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_activity_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule activity_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        RewriteRuleSubtreeStream stream_data_mapping=new RewriteRuleSubtreeStream(adaptor,"rule data_mapping");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:2: ( 'CONNECT' advice_activity 'PARALLEL TO' activity_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:4: 'CONNECT' advice_activity 'PARALLEL TO' activity_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )?
            {
            string_literal38=(Token)match(input,36,FOLLOW_36_in_parallel_connector471);  
            stream_36.add(string_literal38);

            pushFollow(FOLLOW_advice_activity_in_parallel_connector473);
            advice_activity39=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity39.getTree());
            string_literal40=(Token)match(input,43,FOLLOW_43_in_parallel_connector475);  
            stream_43.add(string_literal40);

            pushFollow(FOLLOW_activity_pointcut_in_parallel_connector477);
            activity_pointcut41=activity_pointcut();

            state._fsp--;

            stream_activity_pointcut.add(activity_pointcut41.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:62: ( 'WITH' data_mapping ( ',' data_mapping )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==38) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:63: 'WITH' data_mapping ( ',' data_mapping )*
                    {
                    string_literal42=(Token)match(input,38,FOLLOW_38_in_parallel_connector480);  
                    stream_38.add(string_literal42);

                    pushFollow(FOLLOW_data_mapping_in_parallel_connector482);
                    data_mapping43=data_mapping();

                    state._fsp--;

                    stream_data_mapping.add(data_mapping43.getTree());
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:83: ( ',' data_mapping )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==39) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:84: ',' data_mapping
                    	    {
                    	    char_literal44=(Token)match(input,39,FOLLOW_39_in_parallel_connector485);  
                    	    stream_39.add(char_literal44);

                    	    pushFollow(FOLLOW_data_mapping_in_parallel_connector487);
                    	    data_mapping45=data_mapping();

                    	    state._fsp--;

                    	    stream_data_mapping.add(data_mapping45.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;

            }



            // AST REWRITE
            // elements: data_mapping, advice_activity, activity_pointcut
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 93:105: -> ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:108: ^( PARALLEL_CONNECTOR advice_activity activity_pointcut ( data_mapping )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARALLEL_CONNECTOR, "PARALLEL_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_activity_pointcut.nextTree());
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:93:163: ( data_mapping )*
                while ( stream_data_mapping.hasNext() ) {
                    adaptor.addChild(root_1, stream_data_mapping.nextTree());

                }
                stream_data_mapping.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parallel_connector"

    public static class alternative_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "alternative_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:96:1: alternative_connector : 'CONNECT' advice_activity 'ALTERNATIVE TO' activity_pointcut 'IF' STRING ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* ) ;
    public final ConnectorParser.alternative_connector_return alternative_connector() throws RecognitionException {
        ConnectorParser.alternative_connector_return retval = new ConnectorParser.alternative_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal46=null;
        Token string_literal48=null;
        Token string_literal50=null;
        Token STRING51=null;
        Token string_literal52=null;
        Token char_literal54=null;
        ConnectorParser.advice_activity_return advice_activity47 = null;

        ConnectorParser.activity_pointcut_return activity_pointcut49 = null;

        ConnectorParser.data_mapping_return data_mapping53 = null;

        ConnectorParser.data_mapping_return data_mapping55 = null;


        Object string_literal46_tree=null;
        Object string_literal48_tree=null;
        Object string_literal50_tree=null;
        Object STRING51_tree=null;
        Object string_literal52_tree=null;
        Object char_literal54_tree=null;
        RewriteRuleTokenStream stream_45=new RewriteRuleTokenStream(adaptor,"token 45");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_activity_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule activity_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        RewriteRuleSubtreeStream stream_data_mapping=new RewriteRuleSubtreeStream(adaptor,"rule data_mapping");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:2: ( 'CONNECT' advice_activity 'ALTERNATIVE TO' activity_pointcut 'IF' STRING ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:4: 'CONNECT' advice_activity 'ALTERNATIVE TO' activity_pointcut 'IF' STRING ( 'WITH' data_mapping ( ',' data_mapping )* )?
            {
            string_literal46=(Token)match(input,36,FOLLOW_36_in_alternative_connector515);  
            stream_36.add(string_literal46);

            pushFollow(FOLLOW_advice_activity_in_alternative_connector517);
            advice_activity47=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity47.getTree());
            string_literal48=(Token)match(input,44,FOLLOW_44_in_alternative_connector519);  
            stream_44.add(string_literal48);

            pushFollow(FOLLOW_activity_pointcut_in_alternative_connector521);
            activity_pointcut49=activity_pointcut();

            state._fsp--;

            stream_activity_pointcut.add(activity_pointcut49.getTree());
            string_literal50=(Token)match(input,45,FOLLOW_45_in_alternative_connector523);  
            stream_45.add(string_literal50);

            STRING51=(Token)match(input,STRING,FOLLOW_STRING_in_alternative_connector525);  
            stream_STRING.add(STRING51);

            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:77: ( 'WITH' data_mapping ( ',' data_mapping )* )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==38) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:78: 'WITH' data_mapping ( ',' data_mapping )*
                    {
                    string_literal52=(Token)match(input,38,FOLLOW_38_in_alternative_connector528);  
                    stream_38.add(string_literal52);

                    pushFollow(FOLLOW_data_mapping_in_alternative_connector530);
                    data_mapping53=data_mapping();

                    state._fsp--;

                    stream_data_mapping.add(data_mapping53.getTree());
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:98: ( ',' data_mapping )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==39) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:99: ',' data_mapping
                    	    {
                    	    char_literal54=(Token)match(input,39,FOLLOW_39_in_alternative_connector533);  
                    	    stream_39.add(char_literal54);

                    	    pushFollow(FOLLOW_data_mapping_in_alternative_connector535);
                    	    data_mapping55=data_mapping();

                    	    state._fsp--;

                    	    stream_data_mapping.add(data_mapping55.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;

            }



            // AST REWRITE
            // elements: data_mapping, STRING, activity_pointcut, advice_activity
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 97:120: -> ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:123: ^( ALTERNATIVE_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALTERNATIVE_CONNECTOR, "ALTERNATIVE_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_activity_pointcut.nextTree());
                adaptor.addChild(root_1, stream_STRING.nextNode());
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:97:188: ( data_mapping )*
                while ( stream_data_mapping.hasNext() ) {
                    adaptor.addChild(root_1, stream_data_mapping.nextTree());

                }
                stream_data_mapping.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "alternative_connector"

    public static class iterating_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "iterating_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:100:1: iterating_connector : 'CONNECT' advice_activity 'ITERATING OVER' activity_pointcut 'IF' STRING ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( ITERATING_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* ) ;
    public final ConnectorParser.iterating_connector_return iterating_connector() throws RecognitionException {
        ConnectorParser.iterating_connector_return retval = new ConnectorParser.iterating_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal56=null;
        Token string_literal58=null;
        Token string_literal60=null;
        Token STRING61=null;
        Token string_literal62=null;
        Token char_literal64=null;
        ConnectorParser.advice_activity_return advice_activity57 = null;

        ConnectorParser.activity_pointcut_return activity_pointcut59 = null;

        ConnectorParser.data_mapping_return data_mapping63 = null;

        ConnectorParser.data_mapping_return data_mapping65 = null;


        Object string_literal56_tree=null;
        Object string_literal58_tree=null;
        Object string_literal60_tree=null;
        Object STRING61_tree=null;
        Object string_literal62_tree=null;
        Object char_literal64_tree=null;
        RewriteRuleTokenStream stream_45=new RewriteRuleTokenStream(adaptor,"token 45");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_activity_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule activity_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        RewriteRuleSubtreeStream stream_data_mapping=new RewriteRuleSubtreeStream(adaptor,"rule data_mapping");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:2: ( 'CONNECT' advice_activity 'ITERATING OVER' activity_pointcut 'IF' STRING ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( ITERATING_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:4: 'CONNECT' advice_activity 'ITERATING OVER' activity_pointcut 'IF' STRING ( 'WITH' data_mapping ( ',' data_mapping )* )?
            {
            string_literal56=(Token)match(input,36,FOLLOW_36_in_iterating_connector565);  
            stream_36.add(string_literal56);

            pushFollow(FOLLOW_advice_activity_in_iterating_connector567);
            advice_activity57=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity57.getTree());
            string_literal58=(Token)match(input,46,FOLLOW_46_in_iterating_connector569);  
            stream_46.add(string_literal58);

            pushFollow(FOLLOW_activity_pointcut_in_iterating_connector571);
            activity_pointcut59=activity_pointcut();

            state._fsp--;

            stream_activity_pointcut.add(activity_pointcut59.getTree());
            string_literal60=(Token)match(input,45,FOLLOW_45_in_iterating_connector573);  
            stream_45.add(string_literal60);

            STRING61=(Token)match(input,STRING,FOLLOW_STRING_in_iterating_connector575);  
            stream_STRING.add(STRING61);

            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:77: ( 'WITH' data_mapping ( ',' data_mapping )* )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==38) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:78: 'WITH' data_mapping ( ',' data_mapping )*
                    {
                    string_literal62=(Token)match(input,38,FOLLOW_38_in_iterating_connector578);  
                    stream_38.add(string_literal62);

                    pushFollow(FOLLOW_data_mapping_in_iterating_connector580);
                    data_mapping63=data_mapping();

                    state._fsp--;

                    stream_data_mapping.add(data_mapping63.getTree());
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:98: ( ',' data_mapping )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==39) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:99: ',' data_mapping
                    	    {
                    	    char_literal64=(Token)match(input,39,FOLLOW_39_in_iterating_connector583);  
                    	    stream_39.add(char_literal64);

                    	    pushFollow(FOLLOW_data_mapping_in_iterating_connector585);
                    	    data_mapping65=data_mapping();

                    	    state._fsp--;

                    	    stream_data_mapping.add(data_mapping65.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    }
                    break;

            }



            // AST REWRITE
            // elements: activity_pointcut, advice_activity, data_mapping, STRING
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 101:120: -> ^( ITERATING_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:123: ^( ITERATING_CONNECTOR advice_activity activity_pointcut STRING ( data_mapping )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ITERATING_CONNECTOR, "ITERATING_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_activity_pointcut.nextTree());
                adaptor.addChild(root_1, stream_STRING.nextNode());
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:101:186: ( data_mapping )*
                while ( stream_data_mapping.hasNext() ) {
                    adaptor.addChild(root_1, stream_data_mapping.nextTree());

                }
                stream_data_mapping.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "iterating_connector"

    public static class new_branch_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "new_branch_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:104:1: new_branch_connector : 'CONNECT' advice_activity 'IN' split_pointcut -> ^( NEW_BRANCH_CONNECTOR advice_activity split_pointcut ) ;
    public final ConnectorParser.new_branch_connector_return new_branch_connector() throws RecognitionException {
        ConnectorParser.new_branch_connector_return retval = new ConnectorParser.new_branch_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal66=null;
        Token string_literal68=null;
        ConnectorParser.advice_activity_return advice_activity67 = null;

        ConnectorParser.split_pointcut_return split_pointcut69 = null;


        Object string_literal66_tree=null;
        Object string_literal68_tree=null;
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        RewriteRuleSubtreeStream stream_split_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule split_pointcut");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:105:2: ( 'CONNECT' advice_activity 'IN' split_pointcut -> ^( NEW_BRANCH_CONNECTOR advice_activity split_pointcut ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:105:4: 'CONNECT' advice_activity 'IN' split_pointcut
            {
            string_literal66=(Token)match(input,36,FOLLOW_36_in_new_branch_connector615);  
            stream_36.add(string_literal66);

            pushFollow(FOLLOW_advice_activity_in_new_branch_connector617);
            advice_activity67=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity67.getTree());
            string_literal68=(Token)match(input,47,FOLLOW_47_in_new_branch_connector619);  
            stream_47.add(string_literal68);

            pushFollow(FOLLOW_split_pointcut_in_new_branch_connector621);
            split_pointcut69=split_pointcut();

            state._fsp--;

            stream_split_pointcut.add(split_pointcut69.getTree());


            // AST REWRITE
            // elements: advice_activity, split_pointcut
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 105:50: -> ^( NEW_BRANCH_CONNECTOR advice_activity split_pointcut )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:105:53: ^( NEW_BRANCH_CONNECTOR advice_activity split_pointcut )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NEW_BRANCH_CONNECTOR, "NEW_BRANCH_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_split_pointcut.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "new_branch_connector"

    public static class sync_branches_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sync_branches_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:108:1: sync_branches_connector : 'CONNECT' advice_activity 'AND-SPLITTING AT' splitting_control_port_pointcut 'SYNCHRONIZING AT' joining_control_port_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* ) ;
    public final ConnectorParser.sync_branches_connector_return sync_branches_connector() throws RecognitionException {
        ConnectorParser.sync_branches_connector_return retval = new ConnectorParser.sync_branches_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal70=null;
        Token string_literal72=null;
        Token string_literal74=null;
        Token string_literal76=null;
        Token char_literal78=null;
        ConnectorParser.advice_activity_return advice_activity71 = null;

        ConnectorParser.splitting_control_port_pointcut_return splitting_control_port_pointcut73 = null;

        ConnectorParser.joining_control_port_pointcut_return joining_control_port_pointcut75 = null;

        ConnectorParser.data_mapping_return data_mapping77 = null;

        ConnectorParser.data_mapping_return data_mapping79 = null;


        Object string_literal70_tree=null;
        Object string_literal72_tree=null;
        Object string_literal74_tree=null;
        Object string_literal76_tree=null;
        Object char_literal78_tree=null;
        RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_splitting_control_port_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule splitting_control_port_pointcut");
        RewriteRuleSubtreeStream stream_joining_control_port_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule joining_control_port_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        RewriteRuleSubtreeStream stream_data_mapping=new RewriteRuleSubtreeStream(adaptor,"rule data_mapping");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:2: ( 'CONNECT' advice_activity 'AND-SPLITTING AT' splitting_control_port_pointcut 'SYNCHRONIZING AT' joining_control_port_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )? -> ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:4: 'CONNECT' advice_activity 'AND-SPLITTING AT' splitting_control_port_pointcut 'SYNCHRONIZING AT' joining_control_port_pointcut ( 'WITH' data_mapping ( ',' data_mapping )* )?
            {
            string_literal70=(Token)match(input,36,FOLLOW_36_in_sync_branches_connector642);  
            stream_36.add(string_literal70);

            pushFollow(FOLLOW_advice_activity_in_sync_branches_connector644);
            advice_activity71=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity71.getTree());
            string_literal72=(Token)match(input,48,FOLLOW_48_in_sync_branches_connector646);  
            stream_48.add(string_literal72);

            pushFollow(FOLLOW_splitting_control_port_pointcut_in_sync_branches_connector648);
            splitting_control_port_pointcut73=splitting_control_port_pointcut();

            state._fsp--;

            stream_splitting_control_port_pointcut.add(splitting_control_port_pointcut73.getTree());
            string_literal74=(Token)match(input,49,FOLLOW_49_in_sync_branches_connector650);  
            stream_49.add(string_literal74);

            pushFollow(FOLLOW_joining_control_port_pointcut_in_sync_branches_connector652);
            joining_control_port_pointcut75=joining_control_port_pointcut();

            state._fsp--;

            stream_joining_control_port_pointcut.add(joining_control_port_pointcut75.getTree());
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:130: ( 'WITH' data_mapping ( ',' data_mapping )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==38) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:131: 'WITH' data_mapping ( ',' data_mapping )*
                    {
                    string_literal76=(Token)match(input,38,FOLLOW_38_in_sync_branches_connector655);  
                    stream_38.add(string_literal76);

                    pushFollow(FOLLOW_data_mapping_in_sync_branches_connector657);
                    data_mapping77=data_mapping();

                    state._fsp--;

                    stream_data_mapping.add(data_mapping77.getTree());
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:151: ( ',' data_mapping )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==39) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:152: ',' data_mapping
                    	    {
                    	    char_literal78=(Token)match(input,39,FOLLOW_39_in_sync_branches_connector660);  
                    	    stream_39.add(char_literal78);

                    	    pushFollow(FOLLOW_data_mapping_in_sync_branches_connector662);
                    	    data_mapping79=data_mapping();

                    	    state._fsp--;

                    	    stream_data_mapping.add(data_mapping79.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }



            // AST REWRITE
            // elements: splitting_control_port_pointcut, data_mapping, joining_control_port_pointcut, advice_activity
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 109:173: -> ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:176: ^( SYNC_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ( data_mapping )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SYNC_BRANCHES_CONNECTOR, "SYNC_BRANCHES_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_splitting_control_port_pointcut.nextTree());
                adaptor.addChild(root_1, stream_joining_control_port_pointcut.nextTree());
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:109:280: ( data_mapping )*
                while ( stream_data_mapping.hasNext() ) {
                    adaptor.addChild(root_1, stream_data_mapping.nextTree());

                }
                stream_data_mapping.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sync_branches_connector"

    public static class switch_branches_connector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "switch_branches_connector"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:112:1: switch_branches_connector : 'CONNECT' advice_activity 'SWITCHING AT' splitting_control_port_pointcut 'XOR-JOINING AT' joining_control_port_pointcut -> ^( SWITCH_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ) ;
    public final ConnectorParser.switch_branches_connector_return switch_branches_connector() throws RecognitionException {
        ConnectorParser.switch_branches_connector_return retval = new ConnectorParser.switch_branches_connector_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal80=null;
        Token string_literal82=null;
        Token string_literal84=null;
        ConnectorParser.advice_activity_return advice_activity81 = null;

        ConnectorParser.splitting_control_port_pointcut_return splitting_control_port_pointcut83 = null;

        ConnectorParser.joining_control_port_pointcut_return joining_control_port_pointcut85 = null;


        Object string_literal80_tree=null;
        Object string_literal82_tree=null;
        Object string_literal84_tree=null;
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_splitting_control_port_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule splitting_control_port_pointcut");
        RewriteRuleSubtreeStream stream_joining_control_port_pointcut=new RewriteRuleSubtreeStream(adaptor,"rule joining_control_port_pointcut");
        RewriteRuleSubtreeStream stream_advice_activity=new RewriteRuleSubtreeStream(adaptor,"rule advice_activity");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:113:2: ( 'CONNECT' advice_activity 'SWITCHING AT' splitting_control_port_pointcut 'XOR-JOINING AT' joining_control_port_pointcut -> ^( SWITCH_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:113:4: 'CONNECT' advice_activity 'SWITCHING AT' splitting_control_port_pointcut 'XOR-JOINING AT' joining_control_port_pointcut
            {
            string_literal80=(Token)match(input,36,FOLLOW_36_in_switch_branches_connector692);  
            stream_36.add(string_literal80);

            pushFollow(FOLLOW_advice_activity_in_switch_branches_connector694);
            advice_activity81=advice_activity();

            state._fsp--;

            stream_advice_activity.add(advice_activity81.getTree());
            string_literal82=(Token)match(input,50,FOLLOW_50_in_switch_branches_connector696);  
            stream_50.add(string_literal82);

            pushFollow(FOLLOW_splitting_control_port_pointcut_in_switch_branches_connector698);
            splitting_control_port_pointcut83=splitting_control_port_pointcut();

            state._fsp--;

            stream_splitting_control_port_pointcut.add(splitting_control_port_pointcut83.getTree());
            string_literal84=(Token)match(input,51,FOLLOW_51_in_switch_branches_connector700);  
            stream_51.add(string_literal84);

            pushFollow(FOLLOW_joining_control_port_pointcut_in_switch_branches_connector702);
            joining_control_port_pointcut85=joining_control_port_pointcut();

            state._fsp--;

            stream_joining_control_port_pointcut.add(joining_control_port_pointcut85.getTree());


            // AST REWRITE
            // elements: advice_activity, joining_control_port_pointcut, splitting_control_port_pointcut
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 113:124: -> ^( SWITCH_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:113:127: ^( SWITCH_BRANCHES_CONNECTOR advice_activity splitting_control_port_pointcut joining_control_port_pointcut )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SWITCH_BRANCHES_CONNECTOR, "SWITCH_BRANCHES_CONNECTOR"), root_1);

                adaptor.addChild(root_1, stream_advice_activity.nextTree());
                adaptor.addChild(root_1, stream_splitting_control_port_pointcut.nextTree());
                adaptor.addChild(root_1, stream_joining_control_port_pointcut.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "switch_branches_connector"

    public static class advice_activity_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "advice_activity"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:116:1: advice_activity : qualified_name -> ^( ADVICE_ACTIVITY qualified_name ) ;
    public final ConnectorParser.advice_activity_return advice_activity() throws RecognitionException {
        ConnectorParser.advice_activity_return retval = new ConnectorParser.advice_activity_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.qualified_name_return qualified_name86 = null;


        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:117:2: ( qualified_name -> ^( ADVICE_ACTIVITY qualified_name ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:117:4: qualified_name
            {
            pushFollow(FOLLOW_qualified_name_in_advice_activity725);
            qualified_name86=qualified_name();

            state._fsp--;

            stream_qualified_name.add(qualified_name86.getTree());


            // AST REWRITE
            // elements: qualified_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 117:19: -> ^( ADVICE_ACTIVITY qualified_name )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:117:22: ^( ADVICE_ACTIVITY qualified_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ADVICE_ACTIVITY, "ADVICE_ACTIVITY"), root_1);

                adaptor.addChild(root_1, stream_qualified_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "advice_activity"

    public static class activity_pointcut_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "activity_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:120:1: activity_pointcut : ( 'executingactivity(' STRING ')' -> ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) ) | 'executingcompositeactivity(' STRING ')' -> ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) ) | 'executingatomicactivity(' STRING ')' -> ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) ) );
    public final ConnectorParser.activity_pointcut_return activity_pointcut() throws RecognitionException {
        ConnectorParser.activity_pointcut_return retval = new ConnectorParser.activity_pointcut_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal87=null;
        Token STRING88=null;
        Token char_literal89=null;
        Token string_literal90=null;
        Token STRING91=null;
        Token char_literal92=null;
        Token string_literal93=null;
        Token STRING94=null;
        Token char_literal95=null;

        Object string_literal87_tree=null;
        Object STRING88_tree=null;
        Object char_literal89_tree=null;
        Object string_literal90_tree=null;
        Object STRING91_tree=null;
        Object char_literal92_tree=null;
        Object string_literal93_tree=null;
        Object STRING94_tree=null;
        Object char_literal95_tree=null;
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:121:2: ( 'executingactivity(' STRING ')' -> ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) ) | 'executingcompositeactivity(' STRING ')' -> ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) ) | 'executingatomicactivity(' STRING ')' -> ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) ) )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 52:
                {
                alt15=1;
                }
                break;
            case 54:
                {
                alt15=2;
                }
                break;
            case 55:
                {
                alt15=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:121:4: 'executingactivity(' STRING ')'
                    {
                    string_literal87=(Token)match(input,52,FOLLOW_52_in_activity_pointcut744);  
                    stream_52.add(string_literal87);

                    STRING88=(Token)match(input,STRING,FOLLOW_STRING_in_activity_pointcut746);  
                    stream_STRING.add(STRING88);

                    char_literal89=(Token)match(input,53,FOLLOW_53_in_activity_pointcut748);  
                    stream_53.add(char_literal89);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 121:36: -> ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:121:39: ^( ACTIVITY_POINTCUT ^( EXECUTING_ACTIVITY STRING ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTIVITY_POINTCUT, "ACTIVITY_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:121:59: ^( EXECUTING_ACTIVITY STRING )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXECUTING_ACTIVITY, "EXECUTING_ACTIVITY"), root_2);

                        adaptor.addChild(root_2, stream_STRING.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:122:4: 'executingcompositeactivity(' STRING ')'
                    {
                    string_literal90=(Token)match(input,54,FOLLOW_54_in_activity_pointcut765);  
                    stream_54.add(string_literal90);

                    STRING91=(Token)match(input,STRING,FOLLOW_STRING_in_activity_pointcut767);  
                    stream_STRING.add(STRING91);

                    char_literal92=(Token)match(input,53,FOLLOW_53_in_activity_pointcut769);  
                    stream_53.add(char_literal92);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 122:45: -> ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:122:48: ^( ACTIVITY_POINTCUT ^( EXECUTING_COMPOSITE_ACTIVITY STRING ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTIVITY_POINTCUT, "ACTIVITY_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:122:68: ^( EXECUTING_COMPOSITE_ACTIVITY STRING )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXECUTING_COMPOSITE_ACTIVITY, "EXECUTING_COMPOSITE_ACTIVITY"), root_2);

                        adaptor.addChild(root_2, stream_STRING.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:123:4: 'executingatomicactivity(' STRING ')'
                    {
                    string_literal93=(Token)match(input,55,FOLLOW_55_in_activity_pointcut786);  
                    stream_55.add(string_literal93);

                    STRING94=(Token)match(input,STRING,FOLLOW_STRING_in_activity_pointcut788);  
                    stream_STRING.add(STRING94);

                    char_literal95=(Token)match(input,53,FOLLOW_53_in_activity_pointcut790);  
                    stream_53.add(char_literal95);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 123:42: -> ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:123:45: ^( ACTIVITY_POINTCUT ^( EXECUTING_ATOMIC_ACTIVITY STRING ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ACTIVITY_POINTCUT, "ACTIVITY_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:123:65: ^( EXECUTING_ATOMIC_ACTIVITY STRING )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXECUTING_ATOMIC_ACTIVITY, "EXECUTING_ATOMIC_ACTIVITY"), root_2);

                        adaptor.addChild(root_2, stream_STRING.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "activity_pointcut"

    public static class data_mapping_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "data_mapping"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:126:1: data_mapping : ( variable_expression '=' STRING -> ^( DATA_MAPPING variable_expression STRING ) | variable_expression '=' variable_expression -> ^( DATA_MAPPING variable_expression variable_expression ) );
    public final ConnectorParser.data_mapping_return data_mapping() throws RecognitionException {
        ConnectorParser.data_mapping_return retval = new ConnectorParser.data_mapping_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal97=null;
        Token STRING98=null;
        Token char_literal100=null;
        ConnectorParser.variable_expression_return variable_expression96 = null;

        ConnectorParser.variable_expression_return variable_expression99 = null;

        ConnectorParser.variable_expression_return variable_expression101 = null;


        Object char_literal97_tree=null;
        Object STRING98_tree=null;
        Object char_literal100_tree=null;
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_variable_expression=new RewriteRuleSubtreeStream(adaptor,"rule variable_expression");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:127:2: ( variable_expression '=' STRING -> ^( DATA_MAPPING variable_expression STRING ) | variable_expression '=' variable_expression -> ^( DATA_MAPPING variable_expression variable_expression ) )
            int alt16=2;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:127:4: variable_expression '=' STRING
                    {
                    pushFollow(FOLLOW_variable_expression_in_data_mapping813);
                    variable_expression96=variable_expression();

                    state._fsp--;

                    stream_variable_expression.add(variable_expression96.getTree());
                    char_literal97=(Token)match(input,56,FOLLOW_56_in_data_mapping815);  
                    stream_56.add(char_literal97);

                    STRING98=(Token)match(input,STRING,FOLLOW_STRING_in_data_mapping817);  
                    stream_STRING.add(STRING98);



                    // AST REWRITE
                    // elements: STRING, variable_expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 127:35: -> ^( DATA_MAPPING variable_expression STRING )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:127:38: ^( DATA_MAPPING variable_expression STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DATA_MAPPING, "DATA_MAPPING"), root_1);

                        adaptor.addChild(root_1, stream_variable_expression.nextTree());
                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:128:4: variable_expression '=' variable_expression
                    {
                    pushFollow(FOLLOW_variable_expression_in_data_mapping832);
                    variable_expression99=variable_expression();

                    state._fsp--;

                    stream_variable_expression.add(variable_expression99.getTree());
                    char_literal100=(Token)match(input,56,FOLLOW_56_in_data_mapping834);  
                    stream_56.add(char_literal100);

                    pushFollow(FOLLOW_variable_expression_in_data_mapping836);
                    variable_expression101=variable_expression();

                    state._fsp--;

                    stream_variable_expression.add(variable_expression101.getTree());


                    // AST REWRITE
                    // elements: variable_expression, variable_expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 128:48: -> ^( DATA_MAPPING variable_expression variable_expression )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:128:51: ^( DATA_MAPPING variable_expression variable_expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DATA_MAPPING, "DATA_MAPPING"), root_1);

                        adaptor.addChild(root_1, stream_variable_expression.nextTree());
                        adaptor.addChild(root_1, stream_variable_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "data_mapping"

    public static class variable_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variable_expression"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:131:1: variable_expression : ( 'messageTypeVariable(' qualified_name ',' qualified_name ',' STRING ',' STRING ',' STRING ')' -> ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING STRING STRING ) | 'typeVariable(' qualified_name ',' STRING ',' STRING ',' STRING ')' -> ^( TYPE_VARIABLE qualified_name STRING STRING STRING ) );
    public final ConnectorParser.variable_expression_return variable_expression() throws RecognitionException {
        ConnectorParser.variable_expression_return retval = new ConnectorParser.variable_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal102=null;
        Token char_literal104=null;
        Token char_literal106=null;
        Token STRING107=null;
        Token char_literal108=null;
        Token STRING109=null;
        Token char_literal110=null;
        Token STRING111=null;
        Token char_literal112=null;
        Token string_literal113=null;
        Token char_literal115=null;
        Token STRING116=null;
        Token char_literal117=null;
        Token STRING118=null;
        Token char_literal119=null;
        Token STRING120=null;
        Token char_literal121=null;
        ConnectorParser.qualified_name_return qualified_name103 = null;

        ConnectorParser.qualified_name_return qualified_name105 = null;

        ConnectorParser.qualified_name_return qualified_name114 = null;


        Object string_literal102_tree=null;
        Object char_literal104_tree=null;
        Object char_literal106_tree=null;
        Object STRING107_tree=null;
        Object char_literal108_tree=null;
        Object STRING109_tree=null;
        Object char_literal110_tree=null;
        Object STRING111_tree=null;
        Object char_literal112_tree=null;
        Object string_literal113_tree=null;
        Object char_literal115_tree=null;
        Object STRING116_tree=null;
        Object char_literal117_tree=null;
        Object STRING118_tree=null;
        Object char_literal119_tree=null;
        Object STRING120_tree=null;
        Object char_literal121_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:132:2: ( 'messageTypeVariable(' qualified_name ',' qualified_name ',' STRING ',' STRING ',' STRING ')' -> ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING STRING STRING ) | 'typeVariable(' qualified_name ',' STRING ',' STRING ',' STRING ')' -> ^( TYPE_VARIABLE qualified_name STRING STRING STRING ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==57) ) {
                alt17=1;
            }
            else if ( (LA17_0==58) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:132:4: 'messageTypeVariable(' qualified_name ',' qualified_name ',' STRING ',' STRING ',' STRING ')'
                    {
                    string_literal102=(Token)match(input,57,FOLLOW_57_in_variable_expression857);  
                    stream_57.add(string_literal102);

                    pushFollow(FOLLOW_qualified_name_in_variable_expression859);
                    qualified_name103=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name103.getTree());
                    char_literal104=(Token)match(input,39,FOLLOW_39_in_variable_expression861);  
                    stream_39.add(char_literal104);

                    pushFollow(FOLLOW_qualified_name_in_variable_expression863);
                    qualified_name105=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name105.getTree());
                    char_literal106=(Token)match(input,39,FOLLOW_39_in_variable_expression865);  
                    stream_39.add(char_literal106);

                    STRING107=(Token)match(input,STRING,FOLLOW_STRING_in_variable_expression867);  
                    stream_STRING.add(STRING107);

                    char_literal108=(Token)match(input,39,FOLLOW_39_in_variable_expression869);  
                    stream_39.add(char_literal108);

                    STRING109=(Token)match(input,STRING,FOLLOW_STRING_in_variable_expression871);  
                    stream_STRING.add(STRING109);

                    char_literal110=(Token)match(input,39,FOLLOW_39_in_variable_expression873);  
                    stream_39.add(char_literal110);

                    STRING111=(Token)match(input,STRING,FOLLOW_STRING_in_variable_expression875);  
                    stream_STRING.add(STRING111);

                    char_literal112=(Token)match(input,53,FOLLOW_53_in_variable_expression877);  
                    stream_53.add(char_literal112);



                    // AST REWRITE
                    // elements: qualified_name, qualified_name, STRING, STRING, STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 132:98: -> ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING STRING STRING )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:132:101: ^( MESSAGE_TYPE_VARIABLE qualified_name qualified_name STRING STRING STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MESSAGE_TYPE_VARIABLE, "MESSAGE_TYPE_VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_qualified_name.nextTree());
                        adaptor.addChild(root_1, stream_qualified_name.nextTree());
                        adaptor.addChild(root_1, stream_STRING.nextNode());
                        adaptor.addChild(root_1, stream_STRING.nextNode());
                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:133:4: 'typeVariable(' qualified_name ',' STRING ',' STRING ',' STRING ')'
                    {
                    string_literal113=(Token)match(input,58,FOLLOW_58_in_variable_expression898);  
                    stream_58.add(string_literal113);

                    pushFollow(FOLLOW_qualified_name_in_variable_expression900);
                    qualified_name114=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name114.getTree());
                    char_literal115=(Token)match(input,39,FOLLOW_39_in_variable_expression902);  
                    stream_39.add(char_literal115);

                    STRING116=(Token)match(input,STRING,FOLLOW_STRING_in_variable_expression904);  
                    stream_STRING.add(STRING116);

                    char_literal117=(Token)match(input,39,FOLLOW_39_in_variable_expression906);  
                    stream_39.add(char_literal117);

                    STRING118=(Token)match(input,STRING,FOLLOW_STRING_in_variable_expression908);  
                    stream_STRING.add(STRING118);

                    char_literal119=(Token)match(input,39,FOLLOW_39_in_variable_expression910);  
                    stream_39.add(char_literal119);

                    STRING120=(Token)match(input,STRING,FOLLOW_STRING_in_variable_expression912);  
                    stream_STRING.add(STRING120);

                    char_literal121=(Token)match(input,53,FOLLOW_53_in_variable_expression914);  
                    stream_53.add(char_literal121);



                    // AST REWRITE
                    // elements: STRING, STRING, STRING, qualified_name
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 133:72: -> ^( TYPE_VARIABLE qualified_name STRING STRING STRING )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:133:75: ^( TYPE_VARIABLE qualified_name STRING STRING STRING )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE_VARIABLE, "TYPE_VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_qualified_name.nextTree());
                        adaptor.addChild(root_1, stream_STRING.nextNode());
                        adaptor.addChild(root_1, stream_STRING.nextNode());
                        adaptor.addChild(root_1, stream_STRING.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "variable_expression"

    public static class split_pointcut_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "split_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:136:1: split_pointcut : ( 'split(' STRING ')' -> ^( SPLIT_POINTCUT ^( SPLIT STRING ) ) | 'andsplit(' STRING ')' -> ^( SPLIT_POINTCUT ^( ANDSPLIT STRING ) ) | 'xorsplit(' STRING ')' -> ^( SPLIT_POINTCUT ^( XORSPLIT STRING ) ) );
    public final ConnectorParser.split_pointcut_return split_pointcut() throws RecognitionException {
        ConnectorParser.split_pointcut_return retval = new ConnectorParser.split_pointcut_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal122=null;
        Token STRING123=null;
        Token char_literal124=null;
        Token string_literal125=null;
        Token STRING126=null;
        Token char_literal127=null;
        Token string_literal128=null;
        Token STRING129=null;
        Token char_literal130=null;

        Object string_literal122_tree=null;
        Object STRING123_tree=null;
        Object char_literal124_tree=null;
        Object string_literal125_tree=null;
        Object STRING126_tree=null;
        Object char_literal127_tree=null;
        Object string_literal128_tree=null;
        Object STRING129_tree=null;
        Object char_literal130_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:137:2: ( 'split(' STRING ')' -> ^( SPLIT_POINTCUT ^( SPLIT STRING ) ) | 'andsplit(' STRING ')' -> ^( SPLIT_POINTCUT ^( ANDSPLIT STRING ) ) | 'xorsplit(' STRING ')' -> ^( SPLIT_POINTCUT ^( XORSPLIT STRING ) ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case 59:
                {
                alt18=1;
                }
                break;
            case 60:
                {
                alt18=2;
                }
                break;
            case 61:
                {
                alt18=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:137:4: 'split(' STRING ')'
                    {
                    string_literal122=(Token)match(input,59,FOLLOW_59_in_split_pointcut939);  
                    stream_59.add(string_literal122);

                    STRING123=(Token)match(input,STRING,FOLLOW_STRING_in_split_pointcut941);  
                    stream_STRING.add(STRING123);

                    char_literal124=(Token)match(input,53,FOLLOW_53_in_split_pointcut943);  
                    stream_53.add(char_literal124);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 137:24: -> ^( SPLIT_POINTCUT ^( SPLIT STRING ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:137:27: ^( SPLIT_POINTCUT ^( SPLIT STRING ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SPLIT_POINTCUT, "SPLIT_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:137:44: ^( SPLIT STRING )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(SPLIT, "SPLIT"), root_2);

                        adaptor.addChild(root_2, stream_STRING.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:138:4: 'andsplit(' STRING ')'
                    {
                    string_literal125=(Token)match(input,60,FOLLOW_60_in_split_pointcut960);  
                    stream_60.add(string_literal125);

                    STRING126=(Token)match(input,STRING,FOLLOW_STRING_in_split_pointcut962);  
                    stream_STRING.add(STRING126);

                    char_literal127=(Token)match(input,53,FOLLOW_53_in_split_pointcut964);  
                    stream_53.add(char_literal127);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 138:27: -> ^( SPLIT_POINTCUT ^( ANDSPLIT STRING ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:138:30: ^( SPLIT_POINTCUT ^( ANDSPLIT STRING ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SPLIT_POINTCUT, "SPLIT_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:138:47: ^( ANDSPLIT STRING )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(ANDSPLIT, "ANDSPLIT"), root_2);

                        adaptor.addChild(root_2, stream_STRING.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:139:4: 'xorsplit(' STRING ')'
                    {
                    string_literal128=(Token)match(input,61,FOLLOW_61_in_split_pointcut981);  
                    stream_61.add(string_literal128);

                    STRING129=(Token)match(input,STRING,FOLLOW_STRING_in_split_pointcut983);  
                    stream_STRING.add(STRING129);

                    char_literal130=(Token)match(input,53,FOLLOW_53_in_split_pointcut985);  
                    stream_53.add(char_literal130);



                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 139:27: -> ^( SPLIT_POINTCUT ^( XORSPLIT STRING ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:139:30: ^( SPLIT_POINTCUT ^( XORSPLIT STRING ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SPLIT_POINTCUT, "SPLIT_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:139:47: ^( XORSPLIT STRING )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(XORSPLIT, "XORSPLIT"), root_2);

                        adaptor.addChild(root_2, stream_STRING.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "split_pointcut"

    public static class splitting_control_port_pointcut_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "splitting_control_port_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:142:1: splitting_control_port_pointcut : control_port_pointcut ;
    public final ConnectorParser.splitting_control_port_pointcut_return splitting_control_port_pointcut() throws RecognitionException {
        ConnectorParser.splitting_control_port_pointcut_return retval = new ConnectorParser.splitting_control_port_pointcut_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.control_port_pointcut_return control_port_pointcut131 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:143:2: ( control_port_pointcut )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:143:4: control_port_pointcut
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_control_port_pointcut_in_splitting_control_port_pointcut1008);
            control_port_pointcut131=control_port_pointcut();

            state._fsp--;

            adaptor.addChild(root_0, control_port_pointcut131.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "splitting_control_port_pointcut"

    public static class joining_control_port_pointcut_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "joining_control_port_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:146:1: joining_control_port_pointcut : control_port_pointcut ;
    public final ConnectorParser.joining_control_port_pointcut_return joining_control_port_pointcut() throws RecognitionException {
        ConnectorParser.joining_control_port_pointcut_return retval = new ConnectorParser.joining_control_port_pointcut_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ConnectorParser.control_port_pointcut_return control_port_pointcut132 = null;



        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:147:2: ( control_port_pointcut )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:147:4: control_port_pointcut
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_control_port_pointcut_in_joining_control_port_pointcut1019);
            control_port_pointcut132=control_port_pointcut();

            state._fsp--;

            adaptor.addChild(root_0, control_port_pointcut132.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "joining_control_port_pointcut"

    public static class control_port_pointcut_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "control_port_pointcut"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:150:1: control_port_pointcut : ( 'controlport(' qualified_name ')' -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) ) | 'controlinputport(' qualified_name ')' -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) ) | 'controloutputport(' qualified_name ')' -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) ) );
    public final ConnectorParser.control_port_pointcut_return control_port_pointcut() throws RecognitionException {
        ConnectorParser.control_port_pointcut_return retval = new ConnectorParser.control_port_pointcut_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal133=null;
        Token char_literal135=null;
        Token string_literal136=null;
        Token char_literal138=null;
        Token string_literal139=null;
        Token char_literal141=null;
        ConnectorParser.qualified_name_return qualified_name134 = null;

        ConnectorParser.qualified_name_return qualified_name137 = null;

        ConnectorParser.qualified_name_return qualified_name140 = null;


        Object string_literal133_tree=null;
        Object char_literal135_tree=null;
        Object string_literal136_tree=null;
        Object char_literal138_tree=null;
        Object string_literal139_tree=null;
        Object char_literal141_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:151:2: ( 'controlport(' qualified_name ')' -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) ) | 'controlinputport(' qualified_name ')' -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) ) | 'controloutputport(' qualified_name ')' -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) ) )
            int alt19=3;
            switch ( input.LA(1) ) {
            case 62:
                {
                alt19=1;
                }
                break;
            case 63:
                {
                alt19=2;
                }
                break;
            case 64:
                {
                alt19=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:151:4: 'controlport(' qualified_name ')'
                    {
                    string_literal133=(Token)match(input,62,FOLLOW_62_in_control_port_pointcut1030);  
                    stream_62.add(string_literal133);

                    pushFollow(FOLLOW_qualified_name_in_control_port_pointcut1032);
                    qualified_name134=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name134.getTree());
                    char_literal135=(Token)match(input,53,FOLLOW_53_in_control_port_pointcut1034);  
                    stream_53.add(char_literal135);



                    // AST REWRITE
                    // elements: qualified_name
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 151:38: -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:151:41: ^( CONTROL_PORT_POINTCUT ^( CONTROL_PORT qualified_name ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONTROL_PORT_POINTCUT, "CONTROL_PORT_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:151:65: ^( CONTROL_PORT qualified_name )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONTROL_PORT, "CONTROL_PORT"), root_2);

                        adaptor.addChild(root_2, stream_qualified_name.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:152:4: 'controlinputport(' qualified_name ')'
                    {
                    string_literal136=(Token)match(input,63,FOLLOW_63_in_control_port_pointcut1051);  
                    stream_63.add(string_literal136);

                    pushFollow(FOLLOW_qualified_name_in_control_port_pointcut1053);
                    qualified_name137=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name137.getTree());
                    char_literal138=(Token)match(input,53,FOLLOW_53_in_control_port_pointcut1055);  
                    stream_53.add(char_literal138);



                    // AST REWRITE
                    // elements: qualified_name
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 152:43: -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:152:46: ^( CONTROL_PORT_POINTCUT ^( CONTROL_INPUT_PORT qualified_name ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONTROL_PORT_POINTCUT, "CONTROL_PORT_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:152:70: ^( CONTROL_INPUT_PORT qualified_name )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONTROL_INPUT_PORT, "CONTROL_INPUT_PORT"), root_2);

                        adaptor.addChild(root_2, stream_qualified_name.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:153:4: 'controloutputport(' qualified_name ')'
                    {
                    string_literal139=(Token)match(input,64,FOLLOW_64_in_control_port_pointcut1072);  
                    stream_64.add(string_literal139);

                    pushFollow(FOLLOW_qualified_name_in_control_port_pointcut1074);
                    qualified_name140=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name140.getTree());
                    char_literal141=(Token)match(input,53,FOLLOW_53_in_control_port_pointcut1076);  
                    stream_53.add(char_literal141);



                    // AST REWRITE
                    // elements: qualified_name
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 153:44: -> ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) )
                    {
                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:153:47: ^( CONTROL_PORT_POINTCUT ^( CONTROL_OUTPUT_PORT qualified_name ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONTROL_PORT_POINTCUT, "CONTROL_PORT_POINTCUT"), root_1);

                        // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:153:71: ^( CONTROL_OUTPUT_PORT qualified_name )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONTROL_OUTPUT_PORT, "CONTROL_OUTPUT_PORT"), root_2);

                        adaptor.addChild(root_2, stream_qualified_name.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "control_port_pointcut"

    public static class qualified_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "qualified_name"
    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:158:1: qualified_name : IDENTIFIER ( '.' IDENTIFIER )* -> ^( QUALIFIED_NAME ( IDENTIFIER )* ) ;
    public final ConnectorParser.qualified_name_return qualified_name() throws RecognitionException {
        ConnectorParser.qualified_name_return retval = new ConnectorParser.qualified_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER142=null;
        Token char_literal143=null;
        Token IDENTIFIER144=null;

        Object IDENTIFIER142_tree=null;
        Object char_literal143_tree=null;
        Object IDENTIFIER144_tree=null;
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:159:2: ( IDENTIFIER ( '.' IDENTIFIER )* -> ^( QUALIFIED_NAME ( IDENTIFIER )* ) )
            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:159:4: IDENTIFIER ( '.' IDENTIFIER )*
            {
            IDENTIFIER142=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_qualified_name1101);  
            stream_IDENTIFIER.add(IDENTIFIER142);

            // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:159:15: ( '.' IDENTIFIER )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==65) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:159:16: '.' IDENTIFIER
            	    {
            	    char_literal143=(Token)match(input,65,FOLLOW_65_in_qualified_name1104);  
            	    stream_65.add(char_literal143);

            	    IDENTIFIER144=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_qualified_name1106);  
            	    stream_IDENTIFIER.add(IDENTIFIER144);


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);



            // AST REWRITE
            // elements: IDENTIFIER
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 159:33: -> ^( QUALIFIED_NAME ( IDENTIFIER )* )
            {
                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:159:36: ^( QUALIFIED_NAME ( IDENTIFIER )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(QUALIFIED_NAME, "QUALIFIED_NAME"), root_1);

                // /Development/Workspaces/Unify/Unify/src/org/unify_framework/abstract_syntax/connector_mechanism/parser/Connector.g:159:53: ( IDENTIFIER )*
                while ( stream_IDENTIFIER.hasNext() ) {
                    adaptor.addChild(root_1, stream_IDENTIFIER.nextNode());

                }
                stream_IDENTIFIER.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "qualified_name"

    // Delegated rules


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA16 dfa16 = new DFA16(this);
    static final String DFA1_eotS =
        "\7\uffff";
    static final String DFA1_eofS =
        "\7\uffff";
    static final String DFA1_minS =
        "\1\44\1\42\1\45\1\42\2\uffff\1\45";
    static final String DFA1_maxS =
        "\1\44\1\42\1\101\1\42\2\uffff\1\101";
    static final String DFA1_acceptS =
        "\4\uffff\1\1\1\2\1\uffff";
    static final String DFA1_specialS =
        "\7\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\1",
            "\1\2",
            "\1\4\2\uffff\5\5\1\uffff\3\5\1\uffff\1\5\16\uffff\1\3",
            "\1\6",
            "",
            "",
            "\1\4\2\uffff\5\5\1\uffff\3\5\1\uffff\1\5\16\uffff\1\3"
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "47:1: connector : ( hierarchical_decomposition_connector | inversion_of_control_connector );";
        }
    }
    static final String DFA4_eotS =
        "\16\uffff";
    static final String DFA4_eofS =
        "\16\uffff";
    static final String DFA4_minS =
        "\1\44\1\42\1\50\1\42\11\uffff\1\50";
    static final String DFA4_maxS =
        "\1\44\1\42\1\101\1\42\11\uffff\1\101";
    static final String DFA4_acceptS =
        "\4\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff";
    static final String DFA4_specialS =
        "\16\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1",
            "\1\2",
            "\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\1\12\1\13\1\uffff\1\14\16"+
            "\uffff\1\3",
            "\1\15",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\1\12\1\13\1\uffff\1\14\16"+
            "\uffff\1\3"
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
            return "68:1: inversion_of_control_connector : ( before_connector | after_connector | around_connector | parallel_connector | alternative_connector | iterating_connector | new_branch_connector | sync_branches_connector | switch_branches_connector );";
        }
    }
    static final String DFA16_eotS =
        "\36\uffff";
    static final String DFA16_eofS =
        "\36\uffff";
    static final String DFA16_minS =
        "\1\71\2\42\2\47\3\42\1\41\4\47\1\42\2\41\3\47\2\41\1\47\1\65\1\41"+
        "\1\70\1\65\1\41\1\70\2\uffff";
    static final String DFA16_maxS =
        "\1\72\2\42\2\101\3\42\1\41\3\101\1\47\1\42\2\41\1\101\2\47\2\41"+
        "\1\47\1\65\1\41\1\70\1\65\1\72\1\70\2\uffff";
    static final String DFA16_acceptS =
        "\34\uffff\1\1\1\2";
    static final String DFA16_specialS =
        "\36\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\1\1\2",
            "\1\3",
            "\1\4",
            "\1\6\31\uffff\1\5",
            "\1\10\31\uffff\1\7",
            "\1\11",
            "\1\12",
            "\1\13",
            "\1\14",
            "\1\6\31\uffff\1\5",
            "\1\16\31\uffff\1\15",
            "\1\10\31\uffff\1\7",
            "\1\17",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\16\31\uffff\1\15",
            "\1\23",
            "\1\24",
            "\1\25",
            "\1\26",
            "\1\27",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34\27\uffff\2\35",
            "\1\32",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "126:1: data_mapping : ( variable_expression '=' STRING -> ^( DATA_MAPPING variable_expression STRING ) | variable_expression '=' variable_expression -> ^( DATA_MAPPING variable_expression variable_expression ) );";
        }
    }
 

    public static final BitSet FOLLOW_hierarchical_decomposition_connector_in_connector220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inversion_of_control_connector_in_connector225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_hierarchical_decomposition_connector238 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_calling_activity_in_hierarchical_decomposition_connector240 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_hierarchical_decomposition_connector242 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_called_activity_in_hierarchical_decomposition_connector244 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_hierarchical_decomposition_connector247 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_hierarchical_decomposition_connector249 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_hierarchical_decomposition_connector252 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_hierarchical_decomposition_connector254 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_qualified_name_in_calling_activity282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualified_name_in_called_activity301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_before_connector_in_inversion_of_control_connector322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_after_connector_in_inversion_of_control_connector327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_around_connector_in_inversion_of_control_connector332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parallel_connector_in_inversion_of_control_connector337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alternative_connector_in_inversion_of_control_connector342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterating_connector_in_inversion_of_control_connector347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_new_branch_connector_in_inversion_of_control_connector352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sync_branches_connector_in_inversion_of_control_connector357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_switch_branches_connector_in_inversion_of_control_connector362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_before_connector373 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_before_connector375 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_before_connector377 = new BitSet(new long[]{0x00D0000000000000L});
    public static final BitSet FOLLOW_activity_pointcut_in_before_connector379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_after_connector400 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_after_connector402 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_after_connector404 = new BitSet(new long[]{0x00D0000000000000L});
    public static final BitSet FOLLOW_activity_pointcut_in_after_connector406 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_after_connector409 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_after_connector411 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_after_connector414 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_after_connector416 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_36_in_around_connector444 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_around_connector446 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_around_connector448 = new BitSet(new long[]{0x00D0000000000000L});
    public static final BitSet FOLLOW_activity_pointcut_in_around_connector450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_parallel_connector471 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_parallel_connector473 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_parallel_connector475 = new BitSet(new long[]{0x00D0000000000000L});
    public static final BitSet FOLLOW_activity_pointcut_in_parallel_connector477 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_parallel_connector480 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_parallel_connector482 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_parallel_connector485 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_parallel_connector487 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_36_in_alternative_connector515 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_alternative_connector517 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_alternative_connector519 = new BitSet(new long[]{0x00D0000000000000L});
    public static final BitSet FOLLOW_activity_pointcut_in_alternative_connector521 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_alternative_connector523 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_alternative_connector525 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_alternative_connector528 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_alternative_connector530 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_alternative_connector533 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_alternative_connector535 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_36_in_iterating_connector565 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_iterating_connector567 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_iterating_connector569 = new BitSet(new long[]{0x00D0000000000000L});
    public static final BitSet FOLLOW_activity_pointcut_in_iterating_connector571 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_iterating_connector573 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_iterating_connector575 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_iterating_connector578 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_iterating_connector580 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_iterating_connector583 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_iterating_connector585 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_36_in_new_branch_connector615 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_new_branch_connector617 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_new_branch_connector619 = new BitSet(new long[]{0x3800000000000000L});
    public static final BitSet FOLLOW_split_pointcut_in_new_branch_connector621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_sync_branches_connector642 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_sync_branches_connector644 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_sync_branches_connector646 = new BitSet(new long[]{0xC000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_splitting_control_port_pointcut_in_sync_branches_connector648 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_sync_branches_connector650 = new BitSet(new long[]{0xC000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_joining_control_port_pointcut_in_sync_branches_connector652 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_sync_branches_connector655 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_sync_branches_connector657 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_39_in_sync_branches_connector660 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_data_mapping_in_sync_branches_connector662 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_36_in_switch_branches_connector692 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_advice_activity_in_switch_branches_connector694 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_switch_branches_connector696 = new BitSet(new long[]{0xC000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_splitting_control_port_pointcut_in_switch_branches_connector698 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_switch_branches_connector700 = new BitSet(new long[]{0xC000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_joining_control_port_pointcut_in_switch_branches_connector702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualified_name_in_advice_activity725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_activity_pointcut744 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_activity_pointcut746 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_activity_pointcut748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_activity_pointcut765 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_activity_pointcut767 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_activity_pointcut769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_activity_pointcut786 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_activity_pointcut788 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_activity_pointcut790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_expression_in_data_mapping813 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_data_mapping815 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_data_mapping817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_expression_in_data_mapping832 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_data_mapping834 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_variable_expression_in_data_mapping836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_variable_expression857 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_qualified_name_in_variable_expression859 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression861 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_qualified_name_in_variable_expression863 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression865 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression867 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression869 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression871 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression873 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression875 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_variable_expression877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_variable_expression898 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_qualified_name_in_variable_expression900 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression902 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression904 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression906 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression908 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_variable_expression910 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_variable_expression912 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_variable_expression914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_split_pointcut939 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_split_pointcut941 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_split_pointcut943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_split_pointcut960 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_split_pointcut962 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_split_pointcut964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_split_pointcut981 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_split_pointcut983 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_split_pointcut985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_control_port_pointcut_in_splitting_control_port_pointcut1008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_control_port_pointcut_in_joining_control_port_pointcut1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_control_port_pointcut1030 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_qualified_name_in_control_port_pointcut1032 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_control_port_pointcut1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_control_port_pointcut1051 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_qualified_name_in_control_port_pointcut1053 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_control_port_pointcut1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_control_port_pointcut1072 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_qualified_name_in_control_port_pointcut1074 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_control_port_pointcut1076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_qualified_name1101 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_qualified_name1104 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_qualified_name1106 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});

}
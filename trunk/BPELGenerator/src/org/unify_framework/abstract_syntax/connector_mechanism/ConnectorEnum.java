package org.unify_framework.abstract_syntax.connector_mechanism;

import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorLexer;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorParser;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorTree;

import java.util.HashMap;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeAdaptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public enum ConnectorEnum {

	ACTIVITY_CONNECTOR	("ACTIVITY_CONNECTOR"),
	BEFORE_CONNECTOR	("BEFORE_CONNECTOR"),
	AFTER_CONNECTOR		("AFTER_CONNECTOR"),
	REPLACE_CONNECTOR	("REPLACE_CONNECTOR"),
	IN_CONNECTOR		("IN_CONNECTOR"),
	FREE_AND_CONNECTOR	("FREE_AND_CONNECTOR"),
	FREE_XOR_CONNECTOR	("FREE_XOR_CONNECTOR");
	
	private static final Log log = LogFactory.getLog(ConnectorEnum.class);
	private static final TreeAdaptor adaptor = new CommonTreeAdaptor();
	
	private static Map<String, ConnectorEnum> tokens;
	
	public static Connector parseConnector(String connectorExpression, Composition composition) {
		
		try {
			log.debug("Parsing connector expression '" + connectorExpression + "'");
			ANTLRStringStream stringStream = new ANTLRStringStream(connectorExpression);
			ConnectorLexer lexer = new ConnectorLexer(stringStream);
			TokenRewriteStream trs = new TokenRewriteStream(lexer);
			ConnectorParser parser = new ConnectorParser(trs);
			parser.setTreeAdaptor(adaptor);
			ConnectorParser.connector_return result = parser.connector();
			CommonTree tree = (CommonTree) result.getTree();
			ConnectorEnum.printTree(tree, 0);
			
			CommonTreeNodeStream ctns = new CommonTreeNodeStream(adaptor, tree);
			ConnectorTree ct = new ConnectorTree(ctns);
			ct.connector();
			Connector connector = ct.getConnector();
			return connector;
			
//			ConnectorEnum ce = ConnectorEnum.getToken(tree.getText());
//			switch (ce) {
//				case ACTIVITY_CONNECTOR:
//					log.debug("Creating activity connector");
//					return createActivityConnector(tree, composition);
//				case AFTER_CONNECTOR:
//					log.debug("Creating after connector");
//					return createAfterConnector(tree, composition);
//				case FREE_AND_CONNECTOR:
//					log.debug("Creating free (AND) connector");
//					return createFreeAndConnector(tree, composition);
//				default:
//					log.error("This connector type has not yet been implemented!");
//					return null;
//			}
		} catch (Exception e) {
			log.error("The connector expression could not be parsed!", e);
			return null;
		}
	}
	
	private static void addToken(String token, ConnectorEnum ce) {
		
		if (tokens == null) {
			tokens = new HashMap<String, ConnectorEnum>();
		}
		tokens.put(token, ce);
	}
	
//	private static ActivityConnector createActivityConnector(CommonTree tree, Composition composition) {
//		
//		Activity callingActivity = composition.findActivity(tree.getChild(0).getChild(0));
//		Activity calledActivity = composition.findActivity(tree.getChild(1).getChild(0));
//		ActivityConnector ac = new ActivityConnector(callingActivity, calledActivity);
//		return ac;
//		
//	}
//	
//	private static AfterConnector createAfterConnector(CommonTree tree, Composition composition) {
//		
//		Activity adviceActivity = composition.findActivity(tree.getChild(0).getChild(0));
//		Set<Activity> activityPointcut = composition.findActivities(tree.getChild(1).getChild(0));
//		AfterConnector ac = new AfterConnector(adviceActivity, activityPointcut);
//		return ac;
//	}
//	
//	private static FreeAndConnector createFreeAndConnector(CommonTree tree, Composition composition) {
//
//		Activity adviceActivity = composition.findActivity(tree.getChild(0).getChild(0));
//		ControlPort splittingControlPort = composition.findControlPort(tree.getChild(1).getChild(0));
//		ControlPort joiningControlPort = composition.findControlPort(tree.getChild(2).getChild(0));
//		FreeAndConnector fac = new FreeAndConnector(adviceActivity, splittingControlPort, joiningControlPort);
//		return fac;
//	}
//	
//	private static ConnectorEnum getToken(String token) {
//		
//		return tokens.get(token);
//	}
	
	private static void printTree(CommonTree t, int indent) {
		
		if (t != null) {
			StringBuffer sb = new StringBuffer(indent);
			for (int i = 0; i < indent; i++)
				sb = sb.append("    ");
			log.debug(sb.toString() + t.toString());
			sb = sb.append("    ");
			for (int i = 0; i < t.getChildCount(); i++) {
				printTree((CommonTree) t.getChild(i), indent + 1);
			}
		}
	}
	
	private ConnectorEnum(String token) {
		
		addToken(token, this);
	}
}

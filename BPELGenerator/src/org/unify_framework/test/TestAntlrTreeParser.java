package org.unify_framework.test;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorLexer;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorParser;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorTree;
import org.unify_framework.test.TestAntlrParser;

/**
 * Shows how the generated ANTLR tree parser can be used to parse a Unify
 * connector.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @see TestAntlrParser
 * @version $Id: TestAntlrTreeParser.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestAntlrTreeParser {
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		try {
			ANTLRStringStream stringStream = new ANTLRStringStream("CONNECT PerformBilling AND-SPLITTING AT controlport(ProcessOrder.FillOrder.ControlOut) SYNCHRONIZING AT controlport(ProcessOrder.CloseOrder.ControlIn)");
			ConnectorLexer lexer = new ConnectorLexer(stringStream);
			TokenRewriteStream tokens = new TokenRewriteStream(lexer);
			ConnectorParser parser = new ConnectorParser(tokens);
			parser.setTreeAdaptor(new CommonTreeAdaptor());
			ConnectorParser.connector_return result = parser.connector();
			CommonTree tree = (CommonTree) result.getTree();
			ConnectorTree treeParser = new ConnectorTree(new CommonTreeNodeStream(tree));
			treeParser.connector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

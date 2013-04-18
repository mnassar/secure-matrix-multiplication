package org.unify_framework.test;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorLexer;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.ConnectorParser;
import org.unify_framework.test.TestAntlrTreeParser;

/**
 * Shows how the generated ANTLR (non-tree) parser can be used to parse a Unify
 * connector.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @see TestAntlrTreeParser
 * @version $Id: TestAntlrParser.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestAntlrParser {

	static final TreeAdaptor adaptor = new CommonTreeAdaptor();

	public static void printTree(CommonTree t, int indent) {

		if (t != null) {
			StringBuffer sb = new StringBuffer(indent);
			for (int i = 0; i < indent; i++)
				sb = sb.append("    ");
			System.out.println(sb.toString() + t.toString());
			sb = sb.append("    ");
			for (int i = 0; i < t.getChildCount(); i++) {
				printTree((CommonTree) t.getChild(i), indent + 1);
			}
		}
	}

	public static void main(String[] args) {

		try {
			String inputString = "CONNECT ProcessOrder.ProduceParts TO ProduceParts";
			System.out.println(inputString);
			ANTLRStringStream input = new ANTLRStringStream(inputString);
			ConnectorLexer lexer = new ConnectorLexer(input);
			TokenRewriteStream tokens = new TokenRewriteStream(lexer);
			ConnectorParser parser = new ConnectorParser(tokens);
			parser.setTreeAdaptor(adaptor);
			ConnectorParser.connector_return result = parser.connector();
			CommonTree tree = (CommonTree) result.getTree();
			TestAntlrParser.printTree(tree, 0);
			System.out.println();

			inputString = "CONNECT PerformReporting AFTER executingactivity(\"*.Receive*\")";
			System.out.println(inputString);
			input = new ANTLRStringStream(inputString);
			lexer = new ConnectorLexer(input);
			tokens = new TokenRewriteStream(lexer);
			parser = new ConnectorParser(tokens);
			parser.setTreeAdaptor(adaptor);
			result = parser.connector();
			tree = (CommonTree) result.getTree();
			TestAntlrParser.printTree(tree, 0);
			System.out.println();

			inputString = "CONNECT PerformBilling AND-SPLITTING AT controlport(ProcessOrder.FillOrder.ControlOut) SYNCHRONIZING AT controlport(ProcessOrder.CloseOrder.ControlIn)";
			System.out.println(inputString);
			input = new ANTLRStringStream(inputString);
			lexer = new ConnectorLexer(input);
			tokens = new TokenRewriteStream(lexer);
			parser = new ConnectorParser(tokens);
			parser.setTreeAdaptor(adaptor);
			result = parser.connector();
			tree = (CommonTree) result.getTree();
			TestAntlrParser.printTree(tree, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.unify_framework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.CompositionParser;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;

/**
 * Shows how a Composition XML file can be used to weave a composition of
 * WS-BPEL processes, Unify connectors, and CSL artifacts.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestSourceCodeWeaver.java 13447 2010-10-19 11:12:30Z njonchee $
 */
public class TestBpelSourceCodeWeaverUsingComposition {
	
	private static final Log log = LogFactory.getLog(TestBpelSourceCodeWeaverUsingComposition.class);
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		log.debug("Working directory is " + System.getProperty("user.dir"));
		
		CompositionParser parser = new CompositionParser(replaceSeparatorChar("./examples/OrderBooks/OrderBooks.composition"));
		Composition composition = parser.parse();
		composition.weave();
		
		// Visualize the order books process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/OrderBooks.Woven.dot"));
			visitor.generateGraphviz(composition.getBaseConcern());
		} catch (Exception ex) {
			log.error(ex);
		}
		
		composition.serialize(replaceSeparatorChar("./build/OrderBooks.Woven.bpel"));
	}
	
	private static String replaceSeparatorChar(String path) {
		
		return path.replace('/', File.separatorChar);
	}
}

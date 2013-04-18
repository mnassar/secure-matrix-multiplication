package org.unify_framework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.parser.BpelParser;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.instances.bpel.source_code_weaver.BpelSourceCodeWeaver;

/**
 * Shows how one can manually create a Unify composition of several WS-BPEL
 * processes and Unify connectors, and how this composition can be woven using
 * a {@link BpelSourceCodeWeaver}.  A Composition XML file would allow doing
 * the same in a more user-friendly manner, as is illustrated in
 * {@link TestBpelSourceCodeWeaverUsingComposition}. 
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestBpelSourceCodeWeaver.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestBpelSourceCodeWeaver {
	
	private static final Log log = LogFactory.getLog(TestBpelSourceCodeWeaver.class);
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		log.debug("Working directory is " + System.getProperty("user.dir"));
		
		BpelParser parser = new BpelParser();
		BpelProcess orderBooksProcess = parser.parse(replaceSeparatorChar("./examples/OrderBooks/OrderBooks.bpel"));
		
		// Visualize the order books process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/OrderBooks.dot"));
			visitor.generateGraphviz(orderBooksProcess);
		} catch (Exception ex) {
			log.error(ex);
		}
		
		BpelProcess reportingProcess = parser.parse(replaceSeparatorChar("./examples/OrderBooks/Reporting.bpel"));
		BpelProcess preferencesProcess = parser.parse(replaceSeparatorChar("./examples/OrderBooks/Preferences.bpel"));
		
		BpelSerializer serializer = new BpelSerializer();
		System.out.println("Trying to serialize OrderBooks");
		serializer.serialize(orderBooksProcess, replaceSeparatorChar("./build/OrderBooks.bpel"));
		System.out.println("Trying to serialize Reporting");
		serializer.serialize(reportingProcess, replaceSeparatorChar("./build/Reporting.bpel"));
		System.out.println("Trying to serialize Preferences");
		serializer.serialize(preferencesProcess, replaceSeparatorChar("./build/Preferences.bpel"));
		
		// Create a composition with OrderBooks as its main concern:
		Composition orderBooksComposition = new Composition("OrderBooks", orderBooksProcess);
		// Add the Reporting concern to the composition:
		orderBooksComposition.addConcern(reportingProcess);
		// Add the Preferences concern to the composition:
		orderBooksComposition.addConcern(preferencesProcess);
		// Add the ReportingToOrderBooks connector to the composition:
		orderBooksComposition.addConnectorFromFile(replaceSeparatorChar("./examples/OrderBooks/ReportingToOrderBooks.connector"));
		// Add the PreferencesToOrderBooks connector to the composition:
		orderBooksComposition.addConnectorFromFile(replaceSeparatorChar("./examples/OrderBooks/PreferencesToOrderBooks.connector"));
		
		// Create a weaver for the composition:
		BpelSourceCodeWeaver weaver = new BpelSourceCodeWeaver(orderBooksComposition);
		// Weave the composition:
		weaver.weave();
		// Serialize the woven composition:
		serializer.serialize(orderBooksProcess, replaceSeparatorChar("./build/OrderBooks.Woven.bpel"));
		
		// Visualize the woven order books process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/OrderBooks.Woven.dot"));
			visitor.generateGraphviz(orderBooksProcess);
		} catch (Exception ex) {
			log.error(ex);
		}
	}
	
	private static String replaceSeparatorChar(String path) {
		
		return path.replace('/', File.separatorChar);
	}
}

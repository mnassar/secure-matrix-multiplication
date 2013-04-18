package org.unify_framework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.instances.bpmn.BpmnProcess;
import org.unify_framework.instances.bpmn.parser.BpmnParser;
import org.unify_framework.instances.bpmn.serializer.BpmnSerializer;
import org.unify_framework.instances.bpmn.source_code_weaver.BpmnSourceCodeWeaver;

/**
 * Shows how one can manually create a Unify composition of BPMN processes and
 * Unify connectors, and how this composition can be woven using a
 * {@link BpmnSourceCodeWeaver}.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestBpmnSourceCodeWeaver.java 18725 2011-12-04 10:38:05Z njonchee $
 */
public class TestBpmnSourceCodeWeaver {
	
	private static final Log log = LogFactory.getLog(TestBpmnSourceCodeWeaver.class);
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		log.debug("Working directory is " + System.getProperty("user.dir"));
		
		BpmnParser parser = new BpmnParser();
		BpmnProcess sampleProcess = parser.parse(replaceSeparatorChar("./examples/Sample/Sample.bpmn"));
		
		// Visualize the order books process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/OrderBooks.dot"));
			visitor.generateGraphviz(sampleProcess);
		} catch (Exception ex) {
			log.error(ex);
		}
		
		BpmnProcess dummyProcess = parser.parse(replaceSeparatorChar("./examples/Sample/Dummy.bpmn"));
		
		BpmnSerializer serializer = new BpmnSerializer();
		log.debug("Trying to serialize Sample");
		serializer.serialize(sampleProcess, replaceSeparatorChar("./build/Sample.bpmn"));
		log.debug("Trying to serialize Dummy");
		serializer.serialize(dummyProcess, replaceSeparatorChar("./build/Dummy.bpmn"));
		
		// Create a composition with Sample as its main concern:
		Composition orderBooksComposition = new Composition("Sample", sampleProcess);
		// Add the Dummy concern to the composition:
		orderBooksComposition.addConcern(dummyProcess);
		// Add an AFTER connector to the composition:
		orderBooksComposition.addConnector("CONNECT Dummy AFTER executingactivity(\"Sample\\.FinalScript\")");
		// Add a SYNCHRONIZING connector to the composition:
		orderBooksComposition.addConnector("CONNECT Dummy AND-SPLITTING AT controlport(Sample.SubProcess.ScriptA.ControlOut) SYNCHRONIZING AT controlport(Sample.SubProcess.ScriptD.ControlIn)");
		
		// Create a weaver for the composition:
		BpmnSourceCodeWeaver weaver = new BpmnSourceCodeWeaver(orderBooksComposition);
		// Weave the composition:
		weaver.weave();
		// Serialize the woven composition:
		serializer.serialize(sampleProcess, replaceSeparatorChar("./build/Sample.Woven.bpmn"));
		
		// Visualize the woven Sample process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/Sample.Woven.dot"));
			visitor.generateGraphviz(sampleProcess);
		} catch (Exception ex) {
			log.error(ex);
		}
	}
	
	private static String replaceSeparatorChar(String path) {
		
		return path.replace('/', File.separatorChar);
	}
}

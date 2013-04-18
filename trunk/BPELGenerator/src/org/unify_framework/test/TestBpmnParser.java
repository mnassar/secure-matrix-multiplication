package org.unify_framework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.instances.bpmn.BpmnProcess;
import org.unify_framework.instances.bpmn.parser.BpmnParser;
import org.unify_framework.instances.bpmn.serializer.BpmnSerializer;

/**
 * Shows how a {@link BpmnParser} can be used to parse a BPMN process into
 * Unify, and how a {@link BpmnSerializer} can be used to serialize the parsed
 * process back to BPMN.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestBpmnParser.java 18715 2011-12-02 13:00:38Z njonchee $
 */
public class TestBpmnParser {
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		BpmnParser parser = new BpmnParser();
		BpmnProcess process = parser.parse(new File("./examples/Sample/Sample.bpmn"));
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/Sample.dot"));
			visitor.generateGraphviz(process);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BpmnSerializer serializer = new BpmnSerializer();
		serializer.serialize(process, new File("./build/Sample.bpmn"));
	}
}

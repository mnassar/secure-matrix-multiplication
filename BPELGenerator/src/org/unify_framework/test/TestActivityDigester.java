package org.unify_framework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.BasicConfigurator;

import org.apache.xerces.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.abstract_syntax.visitors.PetriNetVisitor;
import org.unify_framework.concrete_syntax.parser.ActivityDigester;
import org.unify_framework.execution_model.EmPetriNet;
import org.unify_framework.execution_model.manager.ExecutionManager;
import org.unify_framework.execution_model.manager.WorkMonitor;
import org.unify_framework.execution_model.visitors.PnmlVisitor;

/**
 * Shows how the (deprecated) Unify concrete syntax can be used to specify a
 * workflow, how such a workflow can be transformed into a Graphviz graph
 * specification file and/or a PNML Petri net specification file, and how such
 * a workflow can be executed by the Unify Petri net execution model.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestActivityDigester.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestActivityDigester {
	
	private static class Validator extends DefaultHandler {
		
		public void error(SAXParseException exception) throws SAXException {
			
			printException(exception);
		}
		
		public void fatalError(SAXParseException exception) throws SAXException {
			
			printException(exception);
		}
		
		private void printException(SAXParseException exception) {
			
			String message = new String();
			message += "Line " + exception.getLineNumber() + ", ";
			message += "column "+ exception.getColumnNumber() + ": ";
			message += exception.getMessage();
			System.err.println(message);
		}
		
		public void warning(SAXParseException exception) throws SAXException {
			
			printException(exception);
		}	
	}
	
	private static final Log log = LogFactory.getLog(TestActivityDigester.class);
	
	static {
		
		BasicConfigurator.configure();
		log.debug("Configured log4j");
	}
	
	public static void main(String[] args) {
		
		try {
			
			File file = new File("./examples/ProcessOrder/ProcessOrder.unify");
			CompositeActivityImpl compositeActivity = ActivityDigester.parse(file).parse();
			compositeActivity.print();
			
			try {
				GraphvizVisitor graphvizVisitor = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.dot"));
				graphvizVisitor.generateGraphviz(compositeActivity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			PetriNetVisitor petriNetVisitor = new PetriNetVisitor();
			EmPetriNet petriNet = petriNetVisitor.generatePetriNet(compositeActivity);
			petriNet.print();
			
			try {
				
				PnmlVisitor pnmlVisitor = new PnmlVisitor(new FileOutputStream("./build/ProcessOrder.pnml"));
				pnmlVisitor.generatePnml(petriNet);
				
				SAXParser parser = new SAXParser();
				parser.setFeature("http://xml.org/sax/features/validation", true);
				parser.setFeature("http://apache.org/xml/features/validation/schema", true);
				parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
				Validator handler = new Validator();
				parser.setErrorHandler(handler);
				log.debug("Validating PNML document...");
				parser.parse("./build/ProcessOrder.pnml");
				log.debug("Validated PNML document.");
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			// The work monitor should be started before the execution manager!
			
			WorkMonitor wm = new WorkMonitor();
			ExecutionManager em = new ExecutionManager();
			em.addObserver(wm);
			petriNet.setExecutionManager(em);
			
			wm.start();
			em.start();
			petriNet.start();
			
			try {
				while (wm.isAlive()) {
					Thread.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

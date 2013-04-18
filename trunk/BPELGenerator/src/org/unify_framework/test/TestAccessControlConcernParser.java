package org.unify_framework.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.parser.CompositionParser;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.dsls.access_control.AccessControlConcern;
import org.unify_framework.dsls.access_control.parser.AccessControlConcernParser;
import org.unify_framework.dsls.access_control.parser.AccessControlConcernUnifyArtifactsGenerator;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.parser.BpelParser;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.instances.bpel.source_code_weaver.BpelSourceCodeWeaver;

/**
 * Shows how an {@link AccessControlConcernParser} can be invoked manually in
 * order to parse an Access Control XML file and generate the corresponding
 * Unify concern and connector.  However, this is usually done automatically by
 * the {@link CompositionParser}, as is illustrated in
 * {@link TestBpelSourceCodeWeaverUsingComposition}.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestAccessControlConcernParser.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestAccessControlConcernParser {
	
	private static final Log log = LogFactory.getLog(TestAccessControlConcernParser.class);
	
	static {
		
		BasicConfigurator.configure();
		log.debug("Configured log4j");
	}
	
	public static void main(String[] args) {
		
		log.debug("Working directory is " + System.getProperty("user.dir"));
		
		// Parse the base process
		BpelParser parser = new BpelParser();
		BpelProcess confCallProcess = parser.parse(replaceSeparatorChar("./examples/ConferenceCall/ConferenceCall.bpel"));
		
		// Visualize the base process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ConferenceCall.dot"));
			visitor.generateGraphviz(confCallProcess);
		} catch (Exception ex) {
			log.error(ex);
		}
		
		// Parse the Access Control concern
		File file = new File("./examples/ConferenceCall/Example1.AccessControl.xml");
		AccessControlConcern acc = AccessControlConcernParser.parse(file);
		
		// Generate the Unify connector(s) and concern(s)
		AccessControlConcernUnifyArtifactsGenerator generator = new AccessControlConcernUnifyArtifactsGenerator();
		generator.setWorkflow(confCallProcess);
		generator.setAccessControlConcern(acc);
		BpelSourceCodeWeaver scw = new BpelSourceCodeWeaver();
		generator.setSourceCodeWeaver(scw);
		generator.generate();
		
		Composition composition = new Composition();
		composition.setBaseConcern(confCallProcess);
		composition.addConcern(generator.getConcern());
		composition.addConnector(generator.getConnector());
		scw.setComposition(composition);
		scw.weave();
		
		// Visualize the woven process
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ConferenceCall.Woven.dot"));
			visitor.generateGraphviz(scw.getMainConcern());
		} catch (Exception ex) {
			log.error(ex);
		}
		
		// Serialize the woven process
		BpelSerializer serializer = new BpelSerializer();
		serializer.serialize((BpelProcess) scw.getMainConcern(), "./build/ConferenceCall.Woven.bpel");
	}
	
	private static String replaceSeparatorChar(String path) {
		
		return path.replace('/', File.separatorChar);
	}
}

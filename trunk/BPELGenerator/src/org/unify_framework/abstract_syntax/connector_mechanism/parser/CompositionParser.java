package org.unify_framework.abstract_syntax.connector_mechanism.parser;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.Connector;
import org.unify_framework.dsls.access_control.AccessControlConcern;
import org.unify_framework.dsls.access_control.parser.AccessControlConcernParser;
import org.unify_framework.dsls.access_control.parser.AccessControlConcernUnifyArtifactsGenerator;
import org.unify_framework.dsls.parental_control.ParentalControlConcern;
import org.unify_framework.dsls.parental_control.parser.ParentalControlConcernParser;
import org.unify_framework.dsls.parental_control.parser.ParentalControlConcernUnifyArtifactsGenerator;
import org.unify_framework.instances.bpel.parser.BpelParser;
import org.unify_framework.instances.bpel.source_code_weaver.BpelSourceCodeWeaver;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses a Composition XML file.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: CompositionParser.java 20928 2012-07-04 14:04:15Z njonchee $
 */
public class CompositionParser {
	
	private static class CompositionHandler extends DefaultHandler {
		
		public Composition result;
		
		private String compositionDirectory;
		private Attributes attributes;
		private char separatorChar;
		private String s;
		private BpelParser parser = new BpelParser();
		
		public CompositionHandler(String compositionDirectory) {
			
			this.compositionDirectory = compositionDirectory;
		}
		
		public void characters(char ch[], int start, int length) throws SAXException {
			
			this.s += new String(ch, start, length);
		}
	 
		public void endElement(String uri, String localName, String qName) throws SAXException {
			
			if (qName.equals("BaseConcern")) {
				log.debug("Visiting element " + qName + "...");
				File f = new File(this.compositionDirectory + replaceSeparatorChar(s));
				if (!f.exists()) {
					throw new RuntimeException("Base concern file '" + f.getAbsolutePath() + "' does not exist!");
				}
				this.result.setBaseConcern(parser.parse(f));
				this.result.setWeaver(new BpelSourceCodeWeaver(this.result));
			} else if (qName.equals("Concern")) {
				log.debug("Visiting element " + qName + "...");
				File f = new File(this.compositionDirectory + replaceSeparatorChar(s));
				if (!f.exists()) {
					throw new RuntimeException("Concern file '" + f.getAbsolutePath() + "' does not exist!");
				}
				String csl = this.attributes.getValue("csl");
				if (csl == null) {
					this.result.addConcern(parser.parse(f));
				} else if (csl.equals("AccessControl")) {
					AccessControlConcern acc = AccessControlConcernParser.parse(f);
					AccessControlConcernUnifyArtifactsGenerator generator = new AccessControlConcernUnifyArtifactsGenerator();
					generator.setWorkflow(this.result.getBaseConcern());
					generator.setAccessControlConcern(acc);
					generator.setSourceCodeWeaver(this.result.getWeaver());
					generator.generate();
					this.result.addConcern(generator.getConcern());
					this.result.addConnector(generator.getConnector());
				} else if (csl.equals("ParentalControl")) {
					ParentalControlConcern pcc = ParentalControlConcernParser.parse(f);
					ParentalControlConcernUnifyArtifactsGenerator generator = new ParentalControlConcernUnifyArtifactsGenerator();
					generator.setWorkflow(this.result.getBaseConcern());
					generator.setParentalControlConcern(pcc);
					generator.setSourceCodeWeaver(this.result.getWeaver());
					generator.generate();
					for (CompositeActivity concern : generator.getConcerns()) {
						this.result.addConcern(concern);
					}
					for (Connector connector : generator.getConnectors()) {
						this.result.addConnector(connector);
					}
				} else {
					throw new NotImplementedException("The CSL '" + csl + "' is not (yet) supported!");
				}
			} else if (qName.equals("Connector")) {
				log.debug("Visiting element " + qName + " (s = \""+ s + "\")...");
				File f = new File(this.compositionDirectory + replaceSeparatorChar(s));
				if (!f.exists()) {
					throw new RuntimeException("Connector file '" + f.getAbsolutePath() + "' does not exist!");
				}
				this.result.addConnectorFromFile(f);
			}
		}
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			
			this.attributes = attributes;
			this.s = new String();
			if (qName.equals("Composition")) {
				log.debug("Visiting element " + qName + "...");
				this.result = new Composition();
				String name = attributes.getValue("name");
				if (name != null) {
					this.result.setName(name);
				}
				this.separatorChar = attributes.getValue("separatorChar").charAt(0); // NOTE All characters but the first are ignored
			}
		}
		
		private String replaceSeparatorChar(String path) {
			
			return path.replace(this.separatorChar, File.separatorChar);
		}
	}
	
	private static final Log log = LogFactory.getLog(CompositionParser.class);
	
	private File compositionFile;
	private File compositionDirectory;
	
	public CompositionParser(String compositionPath) {
		
		this.compositionFile = new File(compositionPath);
		this.compositionDirectory = this.compositionFile.getParentFile();
		if (!this.compositionFile.exists()) {
			throw new RuntimeException("Composition file '" + this.compositionFile.getAbsolutePath() + "' does not exist!");
		}
	}
	
	public Composition parse() {
		
		if (!this.compositionFile.exists()) {
			throw new RuntimeException("Composition file '" + this.compositionFile.getAbsolutePath() + "' does not exist!");
		}
		
		try {
			
			log.info("Parsing composition file '" + compositionFile.getCanonicalPath() + "'...");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			CompositionHandler handler = new CompositionHandler(this.compositionDirectory.getCanonicalPath() + File.separatorChar);
			parser.parse(compositionFile, handler);
			log.info("Parsed composition file '" + compositionFile.getCanonicalPath() + "'");
			return handler.result;
			
		} catch (Exception ex) {
			
			log.error(ex);
			ex.printStackTrace();
			return null;
		}
	}
}

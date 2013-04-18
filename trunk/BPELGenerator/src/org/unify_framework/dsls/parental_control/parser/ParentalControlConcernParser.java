package org.unify_framework.dsls.parental_control.parser;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.dsls.parental_control.Child;
import org.unify_framework.dsls.parental_control.DenyUsagePolicy;
import org.unify_framework.dsls.parental_control.FilteringPolicy;
import org.unify_framework.dsls.parental_control.MonitoringPolicy;
import org.unify_framework.dsls.parental_control.ParentalControlConcern;
import org.unify_framework.dsls.parental_control.ReferUsagePolicy;
import org.unify_framework.dsls.parental_control.User;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ParentalControlConcernParser.java 18603 2011-11-23 18:59:46Z njonchee $
 */
public class ParentalControlConcernParser {
	
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 
	
	private static class ParentalControlConcernHandler extends DefaultHandler {
		
		public ParentalControlConcern result = null;
		private int age = 0;
		private FilteringPolicy currentFilteringPolicy;
		private Child currentChild;
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			
			log.debug("Visiting element " + qName + "...");
			if (qName.equals("ParentalControlConcern")) {
				result = new ParentalControlConcern();
				String name = attributes.getValue("name");
				if (name != null) {
					result.setName(name);
				}
			} else if (qName.equals("Policies")) {
				age = Integer.parseInt(attributes.getValue("youngerThan"));
			} else if (qName.equals("Filter")) {
				currentFilteringPolicy = new FilteringPolicy(age, attributes.getValue("activity"), attributes.getValue("resultVariable"));
				result.addPolicy(currentFilteringPolicy);
			} else if (qName.equals("Exclude")) {
				currentFilteringPolicy.addExclude(attributes.getValue("property"), attributes.getValue("value"));
			} else if (qName.equals("Deny")) {
				result.addPolicy(new DenyUsagePolicy(age, attributes.getValue("activity")));
			} else if (qName.equals("ReferToParent")) {
				XpathExpressionImpl expression = new XpathExpressionImpl(attributes.getValue("usernameVariableExpression"));
				for (int i = 0; i < attributes.getLength(); i++) {
					String attributeLocalName = attributes.getLocalName(i);
					if (attributeLocalName.startsWith("xmlns:")) {
						expression.addNamespace(attributeLocalName.substring(6), attributes.getValue(i));
					}
				}
				result.addPolicy(new ReferUsagePolicy(age, attributes.getValue("activity"), expression));
			} else if (qName.equals("Monitor")) {
				XpathExpressionImpl expression = new XpathExpressionImpl(attributes.getValue("usernameVariableExpression"));
				for (int i = 0; i < attributes.getLength(); i++) {
					String attributeLocalName = attributes.getLocalName(i);
					if (attributeLocalName.startsWith("xmlns:")) {
						expression.addNamespace(attributeLocalName.substring(6), attributes.getValue(i));
					}
				}
				result.addPolicy(new MonitoringPolicy(age, attributes.getValue("activity"), expression));
			} else if (qName.equals("Child")) {
				currentChild = new Child(attributes.getValue("name"));
				result.addChild(currentChild);
			} else if (qName.equals("Parent")) {
				currentChild.setParent(new User(attributes.getValue("name")));
			} else if (qName.equals("AgeVariable")) {
				XpathExpressionImpl expression = new XpathExpressionImpl(attributes.getValue("expression"));
				for (int i = 0; i < attributes.getLength(); i++) {
					String attributeLocalName = attributes.getLocalName(i);
					if (attributeLocalName.startsWith("xmlns:")) {
						expression.addNamespace(attributeLocalName.substring(6), attributes.getValue(i));
					}
				}
				result.setAgeVariableExpression(expression);
			}
		}
	 
		public void endElement(String uri, String localName, String qName) throws SAXException {
			
			// Do nothing
		}
		
		public void characters(char ch[], int start, int length) throws SAXException {
			
			// Do nothing
		}
		
		public void error(SAXParseException e) throws SAXParseException {
			
			throw e;
		} 
	}
	
	private static final Log log = LogFactory.getLog(ParentalControlConcernParser.class);
	
	public static ParentalControlConcern parse(File file) {
		
		try {
			
			log.info("Parsing '" + file.getCanonicalPath() + "'...");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			ParentalControlConcernHandler handler = new ParentalControlConcernHandler();
			parser.parse(file, handler);
			log.info("Parsed '" + file.getCanonicalPath() + "'");
			return handler.result;
			
		} catch (SAXParseException ex) {

			log.error("Could not validate the parental control concern XML file!  Validation error follows.");
			log.error(ex.getMessage());
			ex.printStackTrace();
			return null;
			
		} catch (Exception ex) {
			
			log.error(ex);
			return null;
		}
	}
}

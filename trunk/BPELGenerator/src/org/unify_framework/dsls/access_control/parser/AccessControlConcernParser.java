package org.unify_framework.dsls.access_control.parser;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.dsls.access_control.AccessControlConcern;
import org.unify_framework.dsls.access_control.AllowPermission;
import org.unify_framework.dsls.access_control.DenyPermission;
import org.unify_framework.dsls.access_control.Role;
import org.unify_framework.dsls.access_control.User;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AccessControlConcernParser.java 18596 2011-11-23 14:54:25Z njonchee $
 */
public class AccessControlConcernParser {
	
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 
	
	private static class AccessControlConcernHandler extends DefaultHandler {
		
		private static enum CurrentElement {
			DEFAULT_USER,
			USER
		}
		
		public AccessControlConcern result = null;
		private Role role = null;
		private User user = null;
		private CurrentElement currentElement = null;
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			
			log.debug("Visiting element " + qName + "...");
			if (qName.equals("AccessControlConcern")) {
				result = new AccessControlConcern();
				String name = attributes.getValue("name");
				if (name != null) {
					result.setName(name);
				}
			} else if (qName.equals("DefaultPermission")) {
				result.setDefaultPermission(attributes.getValue("permission"));
			} else if (qName.equals("DefaultAction")) {
				result.setDefaultAction(attributes.getValue("action"));
			} else if (qName.equals("Role")) {
				role = new Role(attributes.getValue("name"));
				result.addRole(role);
			} else if (qName.equals("Allow")) {
				role.addPermission(new AllowPermission(attributes.getValue("activity")));
			} else if (qName.equals("Deny")) {
				DenyPermission denyPermission = new DenyPermission(attributes.getValue("activity"));
				String action = attributes.getValue("action");
				if (action != null) {
					denyPermission.setAction(action);
				}
				role.addPermission(denyPermission);
			} else if (qName.equals("DefaultUser")) {
				currentElement = CurrentElement.DEFAULT_USER;
			} else if (qName.equals("UserRole")) {
				switch (currentElement) {
					case DEFAULT_USER:
						result.addDefaultUserRole(attributes.getValue("role"));
						break;
					case USER:
						user.addUserRole(attributes.getValue("role"));
						break;
					default:
						throw new RuntimeException("Unexpected UserRole for current element '" + currentElement + "'");
				}
			} else if (qName.equals("User")) {
				currentElement = CurrentElement.USER;
				user = new User(attributes.getValue("name"));
				String password = attributes.getValue("password");
				if (password != null) {
					user.setPassword(password);
				}
				result.addUser(user);
			} else if (qName.equals("UsernameVariable")) {
				XpathExpressionImpl expression = new XpathExpressionImpl(attributes.getValue("expression"));
				for (int i = 0; i < attributes.getLength(); i++) {
					String attributeLocalName = attributes.getLocalName(i);
					if (attributeLocalName.startsWith("xmlns:")) {
						expression.addNamespace(attributeLocalName.substring(6), attributes.getValue(i));
					}
				}
				result.setUsernameVariableExpression(expression);
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
	
	private static final Log log = LogFactory.getLog(AccessControlConcernParser.class);
	
	public static AccessControlConcern parse(File file) {
		
		try {
			
			log.info("Parsing '" + file.getCanonicalPath() + "'...");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			AccessControlConcernHandler handler = new AccessControlConcernHandler();
			parser.parse(file, handler);
			log.info("Parsed '" + file.getCanonicalPath() + "'");
			return handler.result;
			
		} catch (SAXParseException ex) {

			log.error("Could not validate the access control concern XML file!  Validation error follows.");
			log.error(ex.getMessage());
			ex.printStackTrace();
			return null;
			
		} catch (Exception ex) {
			
			log.error(ex);
			return null;
		}
	}
}

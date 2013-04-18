package org.unify_framework.instances.bpel.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.instances.bpel.BpelAndJoin;
import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelCompositeActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelEmptyActivity;
import org.unify_framework.instances.bpel.BpelFrom;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelFromVariable;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScope;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelTo;
import org.unify_framework.instances.bpel.BpelToExpression;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariableElement;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.BpelXorJoin;
import org.unify_framework.instances.bpel.BpelXorSplit;

import org.w3c.dom.Document;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

/**
 * Defines a BPEL parser, which can be used to parse XML files into {@link BpelProcess} objects.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelParser.java 18669 2011-11-28 14:46:43Z njonchee $
 */
public class BpelParser {
	
	private enum SupportedActivity {
		
		RECEIVE ("receive"),
		REPLY ("reply"),
		INVOKE ("invoke"),
		ASSIGN ("assign"),
		SEQUENCE ("sequence"),
		IF ("if"),
		REPEAT_UNTIL ("repeatUntil"),
		FLOW ("flow"),
		SCOPE ("scope"),
		EMPTY ("empty");
		
		static SupportedActivity exists(String localName) {
			
			for (SupportedActivity activity : SupportedActivity.values()) {
				if (activity.localName.equals(localName)) {
					return activity;
				}
			}
			return null;
		}
		
		private String localName;
		
		private SupportedActivity(String localName) {
			
			this.localName = localName;
		}
		
		@Override
		public String toString() {
			
			return this.localName;
		}
	}
	
	/** The BPEL parser's Apache Commons Logging {@link Log}. */
	private static final Log log = LogFactory.getLog(BpelParser.class);
	
	private static String generateRandomName(String prefix) {
		
		return prefix + RandomStringUtils.randomAlphanumeric(4);
	}
	
	private BpelProcess bp;
	
	public BpelProcess parse(File file) {
		
		try {
			
			// Parse the BPEL process using a DOM Level 3 parser:
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSParser parser = ls.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
			DOMConfiguration config = parser.getDomConfig();
			config.setParameter("namespaces", Boolean.TRUE);
//			config.setParameter("schema-type", "http://www.w3.org/2001/XMLSchema");
//			config.setParameter("validate", Boolean.TRUE);
			Document document = parser.parseURI(file.getPath());
			
			// Retrieve the name of the process:
			Element processElement = document.getDocumentElement(); // This should be the process element
			if (!processElement.getLocalName().equals("process")) { // getLocalName() is a DOM Level 2 method, and will thus return null if a DOM Level 1 parser was used
				throw new BpelParserException("The document element's local name should be 'process'!");
			}
			String processName = processElement.getAttribute("name");
			String targetNamespace = processElement.getAttribute("targetNamespace");
			String queryLanguage = processElement.getAttribute("queryLanguage");
			String expressionLanguage = processElement.getAttribute("expressionLanguage");
			
			// Create an instance of BpelProcess with the appropriate name:
			this.bp = new BpelProcess(processName, targetNamespace);
			if (!queryLanguage.equals("")) this.bp.setQueryLanguage(queryLanguage);
			if (!expressionLanguage.equals("")) this.bp.setExpressionLanguage(expressionLanguage);
			log.debug("Created BPEL process '" + this.bp.getName() + "'");
			
			parseNamespaceDeclarations(processElement, this.bp);
			
			// (Start with generic processing of BPEL scopes?)
			// Process any import elements:
			parseChildImports(processElement, this.bp);
			// Process any partner links:
			parseChildPartnerLinks(processElement, this.bp);
			// Process any variables:
			parseChildVariables(processElement, this.bp);
			// Process any control flow elements:
			parseChildActivity(processElement, this.bp);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		return this.bp;
	}
	
	public BpelProcess parse(String path) {
		
		return parse(new File(path));
	}
	
	public ControlOutputPort parseActivity(Element activityElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		String activityElementLocalName = activityElement.getLocalName();
		SupportedActivity supportedActivity = SupportedActivity.exists(activityElementLocalName);
		if (supportedActivity == null) {
	 		throw new BpelParserException("Activity '" + supportedActivity + "' is not supported!");
		}
		switch (supportedActivity) {
			case RECEIVE:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseReceive(activityElement, compositeActivity, predecessor);
			case REPLY:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseReply(activityElement, compositeActivity, predecessor);
			case INVOKE:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseInvoke(activityElement, compositeActivity, predecessor);
			case ASSIGN:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseAssign(activityElement, compositeActivity, predecessor);
		 	case SEQUENCE:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseSequence(activityElement, compositeActivity, predecessor);
		 	case IF:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseIf(activityElement, compositeActivity, predecessor);
		 	case REPEAT_UNTIL:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseRepeatUntil(activityElement, compositeActivity, predecessor);
		 	case FLOW:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseFlow(activityElement, compositeActivity, predecessor);
		 	case SCOPE:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseScope(activityElement, compositeActivity, predecessor);
		 	case EMPTY:
		 		log.debug("Parsing activity '" + supportedActivity + "'");
		 		return parseEmpty(activityElement, compositeActivity, predecessor);
		 	default:
		 		throw new BpelParserException("Activity '" + supportedActivity + "' is not supported (yet)!");
		 }
	}

	public ControlOutputPort parseAssign(Element assignElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		// Retrieve the assign element's attributes:
		String name = assignElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Assign_");
			log.debug("Element 'assign' does not have a name; using '" + name + "'...");
		}
		
		// Create the assign activity:
		BpelAssignActivity assignActivity = new BpelAssignActivity(name);
		NodeList nodeList = assignElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (!element.getLocalName().equals("copy")) {
				continue;
			}
			assignActivity.addCopy(parseCopy(element));
		}
		
		// Add the created assign activity to its parent:
		compositeActivity.addActivity(assignActivity);
		// Connect the predecessor to the created assign activity:
		compositeActivity.connect(predecessor, assignActivity.getControlInputPort());
		// Return the created assign activity's control output port:
		return assignActivity.getControlOutputPort();
	}

	public void parseChildActivity(Element parentElement, BpelCompositeActivity compositeActivity) throws BpelParserException {
		
		Element activity = null;
		NodeList nodeList = parentElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			String elementLocalName = element.getLocalName();
			if (elementLocalName.equals("import") || elementLocalName.equals("partnerLinks") || elementLocalName.equals("variables")) {
				continue;
			}
			if (SupportedActivity.exists(elementLocalName) == null) {
				log.warn("Skipping element '" + elementLocalName + "'...");
				continue;
			}
			if (activity != null) {
				throw new BpelParserException("Element '" + parentElement.getLocalName() + "' (named '" + parentElement.getAttribute("name") +  "') has more than one child activity; expected exactly one");
			}
			activity = element;
		}
		if (activity == null) {
			throw new BpelParserException("Element '" + parentElement.getLocalName() + "' has no child activities; expected exactly one");
		}
		log.debug("Found activity '" + activity.getLocalName() + "'");
		StartEvent startEvent = compositeActivity.getStartEvent();
		ControlOutputPort controlOutputPort = parseActivity(activity, compositeActivity, startEvent.getControlOutputPort());
		EndEvent endEvent = compositeActivity.getEndEvent();
		compositeActivity.connect(controlOutputPort, endEvent.getControlInputPort());
	}
	
	public void parseChildImports(Element parentElement, BpelProcess process) throws BpelParserException {
		
		NodeList nodeList = parentElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (!element.getLocalName().equals("import")) {
				continue;
			} else {
				// The element is an import element
				String namespace = element.getAttribute("namespace");
				if (namespace.equals("")) {
					namespace = null;
//					throw new BpelParserException("All 'import' elements should have a 'namespace' attribute!");
				}
				String location = element.getAttribute("location");
				if (location.equals("")) {
					location = null;
//					throw new BpelParserException("All 'import' elements should have a 'location' attribute!");
				}
				String importType = element.getAttribute("importType");
				if (importType.equals("")) {
					throw new BpelParserException("All 'import' elements should have an 'importType' attribute!");
				}
				process.addImport(new BpelImport(namespace, location, importType));
			}
		}
	}
	
	public void parseChildPartnerLinks(Element parentElement, BpelCompositeActivity compositeActivity) throws BpelParserException {
		
		Element partnerLinksElement = null;
		NodeList nodeList = parentElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (!element.getLocalName().equals("partnerLinks")) {
				continue;
			} else {
				partnerLinksElement = element;
			}
		}
		if (partnerLinksElement == null) {
			throw new BpelParserException("The 'process' element should have exactly one 'partnerLinks' child!");
		}
		nodeList = partnerLinksElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (!element.getLocalName().equals("partnerLink")) {
				continue;
			} else {
				String name = element.getAttribute("name");
				if (name.equals("")) {
					throw new BpelParserException("All 'partnerLink' elements should have a 'name' attribute!");
				}
				String partnerLinkType = element.getAttribute("partnerLinkType");
				if (partnerLinkType.equals("")) {
					throw new BpelParserException("All 'partnerLink' elements should have a 'partnerLinkType' attribute!");
				}
				String myRole = element.getAttribute("myRole");
				if (myRole.equals("")) {
					myRole = null;
				}
				String partnerRole = element.getAttribute("partnerRole");
				if (partnerRole.equals("")) {
					partnerRole = null;
				}
				if (myRole == null && partnerRole == null) {
					throw new BpelParserException("All 'partnerLink' elements should have a 'myRole' attribute, a 'partnerRole' attribute, or both!");
				}
				compositeActivity.addPartnerLink(new BpelPartnerLink(name, partnerLinkType, myRole, partnerRole));
			}
		}
	}
	
	public void parseChildVariables(Element parentElement, BpelCompositeActivity compositeActivity) throws BpelParserException {
		
		Element variablesElement = null;
		NodeList nodeList = parentElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (!element.getLocalName().equals("variables")) {
				continue;
			} else {
				variablesElement = element;
			}
		}
		if (variablesElement == null) {
			throw new BpelParserException("The 'process' and 'scope' elements should have exactly one 'variables' child!");
		}
		BpelScope scope = compositeActivity.getScope();
		nodeList = variablesElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (!element.getLocalName().equals("variable")) {
				continue;
			} else {
				String variableName = element.getAttribute("name");
				if (variableName.equals("")) {
					throw new BpelParserException("All 'variable' elements should have a 'name' attribute!");
				}
				String variableMessageType = element.getAttribute("messageType");
				if (!variableMessageType.equals("")) {
					scope.addVariable(new BpelVariableMessageType(variableName, variableMessageType));
					continue;
				}
				String variableType = element.getAttribute("type");
				if (!variableType.equals("")) {
					scope.addVariable(new BpelVariableType(variableName, variableType));
					continue;
				}
				String variableElement = element.getAttribute("element");
				if (!variableElement.equals("")) {
					scope.addVariable(new BpelVariableElement(variableName, variableElement));
					continue;
				}
				throw new BpelParserException("All 'variable' elements should have a 'messageType', 'type', or 'element' attribute!");
			}
		}
	}
	
	private BpelCopy parseCopy(Element copyElement) throws BpelParserException {
		
		BpelCopy copy = new BpelCopy();
		NodeList nodeList = copyElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			if (element.getLocalName().equals("from")) {
				copy.setFrom(parseFrom(element));
			} else if (element.getLocalName().equals("to")) {
				copy.setTo(parseTo(element));
			} else {
				continue;
			}
		}
		return copy;
	}
	
	private ControlOutputPort parseFlow(Element flowElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		// Retrieve the flow element's attributes:
		String name = flowElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Flow_");
			log.debug("Element 'flow' does not have a name; using '" + name + "'...");
		} else {
			log.debug("Element 'flow' has name '" + name + "'");
		}
		
		// Create a BPEL AND-split:
		BpelAndSplit split = new BpelAndSplit(name + "_Split");
		compositeActivity.addControlNode(split);
		compositeActivity.connect(predecessor, split.getControlInputPort());
		// Create a BPEL AND-join:
		BpelAndJoin join = new BpelAndJoin(name + "_Join");
		compositeActivity.addControlNode(join);
		// Set the 'corresponding' fields:
		split.setCorrespondingAndJoin(join);
		join.setCorrespondingAndSplit(split);
		
		// Process the branches:
		NodeList nodeList = flowElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			String elementLocalName = element.getLocalName();
			if (SupportedActivity.exists(elementLocalName) == null) {
				log.warn("Skipping element '" + elementLocalName + "'...");
				continue;
			}
			ControlOutputPort controlOutputPort = parseActivity(element, compositeActivity, split.getNewControlOutputPort());
			compositeActivity.connect(controlOutputPort, join.getNewControlInputPort());
		}
		
		// Return the created BPEL AND-join's control output port:
		return join.getControlOutputPort();
	}
	
	private BpelFrom parseFrom(Element fromElement) throws BpelParserException {
		
		String variable = fromElement.getAttribute("variable");
		if (variable.equals("")) {
			Node node = null;
			NodeList nodeList = fromElement.getChildNodes();
			int nodeListLength = nodeList.getLength();
			if (nodeListLength == 0) {
				throw new BpelParserException("A BPEL from expression should have one or more child nodes!");
			}
			if (nodeListLength == 1) {
				node = nodeList.item(0);
				if (node.getNodeType() != Node.TEXT_NODE) {
					node = null;
				}
			} else {
				for (int i = 0; i < nodeListLength; i++) {
					node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						break;
					} else {
						node = null;
					}
				}
			}
			if (node == null) {
				throw new BpelParserException("A BPEL from expression should have exactly one child node of the text type, or a child node of the element type!");
			}
			return new BpelFromExpression(node);
		} else {
			BpelFromVariable fromVariable = new BpelFromVariable(variable);
			String part = fromElement.getAttribute("part");
			if (!part.equals("")) {
				fromVariable.setPart(part);
			}
			String query = null;
			NodeList nodeList = fromElement.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				Element element = (Element) node;
				if (!element.getLocalName().equals("query")) {
					continue;
				}
				if (query != null) {
					throw new BpelParserException("A 'from' element may only have one 'query' child!");
				}
				query = element.getTextContent();
				parseNamespaceDeclarations(element, fromVariable);
			}
			fromVariable.setQuery(query);
			return fromVariable;
		}
	}
	
	private ControlOutputPort parseIf(Element ifElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		// Retrieve the if element's attributes:
		String name = ifElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("If_");
			log.debug("Element 'if' does not have a name; using '" + name + "'...");
		} else {
			log.debug("Element 'if' has name '" + name + "'");
		}
		
		// Create a BPEL XOR-split:
		BpelXorSplit split = new BpelXorSplit(name + "_Split");
		compositeActivity.addControlNode(split);
		compositeActivity.connect(predecessor, split.getControlInputPort());
		// Create a BPEL XOR-join:
		BpelXorJoin join = new BpelXorJoin(name + "_Join");
		compositeActivity.addControlNode(join);
		// Set the 'corresponding' fields:
		split.setCorrespondingXorJoin(join);
		join.setCorrespondingXorSplit(split);
		
		// Parse the <if> branch:
		parseIfBranch(ifElement, split, join, compositeActivity);
		// Parse the <elseif> branches:
		NodeList nodeList = ifElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element elseifElement = (Element) node;
			String elseifElementLocalName = elseifElement.getLocalName();
			if (elseifElementLocalName.equals("elseif")) {
				parseIfBranch(elseifElement, split, join, compositeActivity);
			}
		}
		// Parse the <else> branch:
		boolean elseBranchFound = false;
		nodeList = ifElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element elseElement = (Element) node;
			String elseElementLocalName = elseElement.getLocalName();
			if (elseElementLocalName.equals("else")) {
				parseIfBranch(elseElement, split, join, compositeActivity);
				elseBranchFound = true;
			}
		}
		if (!elseBranchFound) {
			BpelEmptyActivity elseActivity = new BpelEmptyActivity(generateRandomName("Else_"));
			compositeActivity.addActivity(elseActivity);
			compositeActivity.connect(split.getNewControlOutputPort(), elseActivity.getControlInputPort());
			compositeActivity.connect(elseActivity.getControlOutputPort(), join.getNewControlInputPort());
		}
		
		// Return the created BPEL XOR-join's control output port:
		return join.getControlOutputPort();
	}
	
	/**
	 * Parses an if branch, which is either the &lt;if&gt; branch, one of the
	 * &lt;elseif&gt; branches, or the &lt;else&gt; branch.
	 * 
	 * @param element The DOM element representing the branch
	 * @param split The BPEL XOR-split to which the branch should be added
	 * @param join The BPEL XOR-join to which the branch should be added
	 * @param compositeActivity The BPEL composite activity that contains this
	 *		branch
	 */
	private void parseIfBranch(Element element, BpelXorSplit split, BpelXorJoin join, BpelCompositeActivity compositeActivity) throws BpelParserException {
		
		String guard = null;
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element childElement = (Element) node;
			String childElementLocalName = childElement.getLocalName();
			if (childElementLocalName.equals("condition")) {
				guard = childElement.getTextContent();
			} else if (SupportedActivity.exists(childElementLocalName) != null) {
				ControlOutputPort splitNewControlOutputPort = split.getNewControlOutputPort();
				if (guard == null || guard == "") {
					split.addGuard(splitNewControlOutputPort, null);
				} else {
					split.addGuard(splitNewControlOutputPort, new XpathExpressionImpl(guard));
				}
				ControlOutputPort controlOutputPort = parseActivity(childElement, compositeActivity, splitNewControlOutputPort);
				compositeActivity.connect(controlOutputPort, join.getNewControlInputPort());
			}
		}
	}
	
	public ControlOutputPort parseEmpty(Element emptyElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) {
		
		// Retrieve the empty element's attributes:
		String name = emptyElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Empty_");
			log.debug("Element 'empty' does not have a name; using '" + name + "'...");
		} else {
			log.debug("Element 'empty' has name '" + name + "'");
		}
		
		// Create the empty activity:
		BpelEmptyActivity emptyActivity = new BpelEmptyActivity(name);
		
		// Add the created invoke activity to its parent:
		compositeActivity.addActivity(emptyActivity);
		// Connect the predecessor to the created invoke activity:
		compositeActivity.connect(predecessor, emptyActivity.getControlInputPort());
		// Return the created invoke activity's control output port:
		return emptyActivity.getControlOutputPort();
	}

	/** Parses the given invoke element into a {@link BpelInvokeActivity}. */
	public ControlOutputPort parseInvoke(Element invokeElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) {
		
		// Retrieve the invoke element's attributes:
		String name = invokeElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Invoke_");
			log.debug("Element 'invoke' does not have a name; using '" + name + "'...");
		} else {
			log.debug("Element 'invoke' has name '" + name + "'");
		}
		String inputVariable = invokeElement.getAttribute("inputVariable");
		String operation = invokeElement.getAttribute("operation");
		String outputVariable = invokeElement.getAttribute("outputVariable");
		String partnerLink = invokeElement.getAttribute("partnerLink");
		String portType = invokeElement.getAttribute("portType");
		
		// Create the invoke activity:
		BpelInvokeActivity invokeActivity = new BpelInvokeActivity(name);
		if (!inputVariable.equals("")) invokeActivity.setInputVariable(inputVariable);
		invokeActivity.setOperation(operation);
		if (!outputVariable.equals("")) invokeActivity.setOutputVariable(outputVariable);
		invokeActivity.setPartnerLink(partnerLink);
		invokeActivity.setPortType(portType);
		
		// Add the created invoke activity to its parent:
		compositeActivity.addActivity(invokeActivity);
		// Connect the predecessor to the created invoke activity:
		compositeActivity.connect(predecessor, invokeActivity.getControlInputPort());
		// Return the created invoke activity's control output port:
		return invokeActivity.getControlOutputPort();
	}
	
	private void parseNamespaceDeclarations(Element element, Object o) throws BpelParserException {
		
		Map<String, String> namespaceDeclarations = new HashMap<String, String>();
		
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			String name = attribute.getNodeName();
			if (name.startsWith("xmlns:")) {
				String prefix = name.substring(6);
				String uri = attribute.getNodeValue();
				log.debug("Encountered namespace declaration with prefix '" + prefix + "' and URI '" + uri + "'");
				namespaceDeclarations.put(prefix, uri);
			}
		}
		
		if (o instanceof BpelProcess) {
			BpelProcess process = (BpelProcess) o;
			for (Map.Entry<String, String> entry : namespaceDeclarations.entrySet()) {
				process.addNamespaceDeclaration(entry.getKey(), entry.getValue());
			}
		} else if (o instanceof BpelFromVariable) {
			BpelFromVariable fromVariable = (BpelFromVariable) o;
			for (Map.Entry<String, String> entry : namespaceDeclarations.entrySet()) {
				fromVariable.addNamespaceDeclaration(entry.getKey(), entry.getValue());
			}
		} else if (o instanceof BpelToVariable) {
			BpelToVariable toVariable = (BpelToVariable) o;
			for (Map.Entry<String, String> entry : namespaceDeclarations.entrySet()) {
				toVariable.addNamespaceDeclaration(entry.getKey(), entry.getValue());
			}
		} else {
			throw new BpelParserException("The BPEL parser cannot (yet) add namespace declarations to an object of this type");
		}
	}
	
	/** Parses the given receive element into a {@link BpelReceiveActivity}. */
	public ControlOutputPort parseReceive(Element receiveElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) {
		
		// Retrieve the receive element's attributes:
		String name = receiveElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Receive_");
			log.debug("Element 'receive' does not have a name; using '" + name + "'...");
		}
		String createInstance = receiveElement.getAttribute("createInstance");
		String operation = receiveElement.getAttribute("operation");
		String partnerLink = receiveElement.getAttribute("partnerLink");
		String portType = receiveElement.getAttribute("portType");
		String variable = receiveElement.getAttribute("variable");
		
		// Create the receive activity:
		BpelReceiveActivity receiveActivity = new BpelReceiveActivity(name);
		receiveActivity.setCreateInstance(createInstance);
		receiveActivity.setOperation(operation);
		receiveActivity.setPartnerLink(partnerLink);
		receiveActivity.setPortType(portType);
		receiveActivity.setVariable(variable);
		
		// Add the created receive activity to its parent:
		compositeActivity.addActivity(receiveActivity);
		// Connect the predecessor to the created receive activity:
		compositeActivity.connect(predecessor, receiveActivity.getControlInputPort());
		// Return the created receive activity's control output port:
		return receiveActivity.getControlOutputPort();
	}
	
	private ControlOutputPort parseRepeatUntil(Element repeatUntilElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		// Retrieve the repeatUntil element's attributes:
		String name = repeatUntilElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("RepeatUntil_");
			log.debug("Element 'repeatUntil' does not have a name; using '" + name + "'...");
		} else {
			log.debug("Element 'repeatUntil' has name '" + name + "'");
		}
		
		// Create a BPEL XOR-join:
		BpelXorJoin join = new BpelXorJoin(name + "_Join");
		compositeActivity.addControlNode(join);
		compositeActivity.connect(predecessor, join.getNewControlInputPort());
		// Create a BPEL XOR-split:
		BpelXorSplit split = new BpelXorSplit(name + "_Split");
		compositeActivity.addControlNode(split);
		// Set the 'corresponding' fields:
		split.setCorrespondingXorJoin(join);
		join.setCorrespondingXorSplit(split);
		
		// Connect the XOR-split to the XOR-join:
		ControlOutputPort loopControlOutputPort = split.getNewControlOutputPort();
		compositeActivity.connect(loopControlOutputPort, join.getNewControlInputPort());
		
		NodeList nodeList = repeatUntilElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element childElement = (Element) node;
			String childElementLocalName = childElement.getLocalName();
			if (SupportedActivity.exists(childElementLocalName) != null) {
				// Connect the XOR-join to the XOR-split:
				ControlOutputPort controlOutputPort = parseActivity(childElement, compositeActivity, join.getControlOutputPort());
				compositeActivity.connect(controlOutputPort, split.getControlInputPort());
			} else if (childElementLocalName.equals("condition")) {
				// Add the guard:
				String guard = childElement.getTextContent();
				split.addGuard(loopControlOutputPort, new XpathExpressionImpl(guard));
			}
		}
		
		// Return a new control output port of the created BPEL XOR-split:
		return split.getNewControlOutputPort();
	}
	
	/** Parses the given reply element into a {@link BpelReplyActivity}. */
	public ControlOutputPort parseReply(Element replyElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) {
		
		// Retrieve the reply element's attributes:
		String name = replyElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Reply_");
			log.debug("Element 'reply' does not have a name; using '" + name + "'...");
		}
		String operation = replyElement.getAttribute("operation");
		String partnerLink = replyElement.getAttribute("partnerLink");
		String portType = replyElement.getAttribute("portType");
		String variable = replyElement.getAttribute("variable");
		
		// Create the reply activity:
		BpelReplyActivity replyActivity = new BpelReplyActivity(name);
		replyActivity.setOperation(operation);
		replyActivity.setPartnerLink(partnerLink);
		replyActivity.setPortType(portType);
		replyActivity.setVariable(variable);
		
		// Add the created reply activity to its parent:
		compositeActivity.addActivity(replyActivity);
		// Connect the predecessor to the created reply activity:
		compositeActivity.connect(predecessor, replyActivity.getControlInputPort());
		// Return the created reply activity's control output port:
		return replyActivity.getControlOutputPort();
	}
	
	public ControlOutputPort parseScope(Element scopeElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		String name = scopeElement.getAttribute("name");
		if (name.equals("")) {
			name = generateRandomName("Scope_");
			log.debug("Element 'scope' does not have a name; using '" + name + "'...");
		} else {
			log.debug("Element 'scope' has name '" + name + "'");
		}
		BpelCompositeActivity scopeActivity = new BpelScopeActivity(name);
		
		// (Start with generic processing of BPEL scopes?)
		// Process any partner links:
//		parseChildPartnerLinks(scopeElement, scopeActivity);
		// Process any variables:
		parseChildVariables(scopeElement, scopeActivity);
		// Process any control flow elements:
		parseChildActivity(scopeElement, scopeActivity);
		
		compositeActivity.addActivity(scopeActivity);
		compositeActivity.connect(predecessor, scopeActivity.getControlInputPort());
		return scopeActivity.getControlOutputPort();
	}
	
	public ControlOutputPort parseSequence(Element sequenceElement, BpelCompositeActivity compositeActivity, ControlOutputPort predecessor) throws BpelParserException {
		
		ControlOutputPort currentPredecessor = predecessor;
		NodeList nodeList = sequenceElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element element = (Element) node;
			String elementLocalName = element.getLocalName();
			if (SupportedActivity.exists(elementLocalName) == null) {
				log.warn("Skipping element '" + elementLocalName + "'...");
				continue;
			}
			currentPredecessor = parseActivity(element, compositeActivity, currentPredecessor);
		}
		return currentPredecessor;
	}
	
	private BpelTo parseTo(Element toElement) throws BpelParserException {

		String variable = toElement.getAttribute("variable");
		if (variable.equals("")) {
			Node node = null;
			NodeList nodeList = toElement.getChildNodes();
			int nodeListLength = nodeList.getLength();
			if (nodeListLength == 0) {
				throw new BpelParserException("A BPEL to expression should have one or more child nodes!");
			}
			if (nodeListLength == 1) {
				node = nodeList.item(0);
				if (node.getNodeType() != Node.TEXT_NODE) {
					node = null;
				}
			} else {
				for (int i = 0; i < nodeListLength; i++) {
					node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						break;
					} else {
						node = null;
					}
				}
			}
			if (node == null) {
				throw new BpelParserException("A BPEL to expression should have exactly one child node of the text type, or a child node of the element type!");
			}
			return new BpelToExpression(node);
		} else {
			BpelToVariable toVariable = new BpelToVariable(variable);
			String part = toElement.getAttribute("part");
			if (!part.equals("")) {
				toVariable.setPart(part);
			}
			String query = null;
			NodeList nodeList = toElement.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				Element element = (Element) node;
				if (!element.getLocalName().equals("query")) {
					continue;
				}
				if (query != null) {
					throw new BpelParserException("A 'from' element may only have one 'query' child!");
				}
				query = element.getTextContent();
				parseNamespaceDeclarations(element, toVariable);
			}
			toVariable.setQuery(query);
			return toVariable;
		}
	}
}

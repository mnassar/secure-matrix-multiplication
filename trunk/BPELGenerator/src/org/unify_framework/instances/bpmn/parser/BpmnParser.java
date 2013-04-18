package org.unify_framework.instances.bpmn.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.Join;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Split;
import org.unify_framework.instances.bpmn.BpmnAndJoin;
import org.unify_framework.instances.bpmn.BpmnAndSplit;
import org.unify_framework.instances.bpmn.BpmnCompositeActivity;
import org.unify_framework.instances.bpmn.BpmnEndEvent;
import org.unify_framework.instances.bpmn.BpmnProcess;
import org.unify_framework.instances.bpmn.BpmnScriptTask;
import org.unify_framework.instances.bpmn.BpmnStartEvent;
import org.unify_framework.instances.bpmn.BpmnSubProcess;
import org.unify_framework.instances.bpmn.BpmnTransition;
import org.unify_framework.instances.bpmn.BpmnXorJoin;
import org.unify_framework.instances.bpmn.BpmnXorSplit;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnParser.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public class BpmnParser {
	
	private static final Log log = LogFactory.getLog(BpmnParser.class);
	
	private BpmnProcess bp;
	
	private Map<String, Node> idToNodeMap = new HashMap<String, Node>();
	
	public BpmnProcess parse(File file) {
		
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
			
			// Retrieve the definitions element:
			Element definitionsElement = document.getDocumentElement(); // This should be the definitions element
			if (!definitionsElement.getLocalName().equals("definitions")) { // getLocalName() is a DOM Level 2 method, and will thus return null if a DOM Level 1 parser was used
				throw new BpmnParserException("The document element's local name should be 'definitions'!");
			}
			String definitionsId = definitionsElement.getAttribute("id");
			String definitionsTargetNamespace = definitionsElement.getAttribute("targetNamespace");
			String definitionsTypeLanguage = definitionsElement.getAttribute("typeLanguage");
			String definitionsExpressionLanguage = definitionsElement.getAttribute("expressionLanguage");
			
			// Retrieve the process element:
			Element processElement = null;
			NodeList definitionsElementChildNodes = definitionsElement.getChildNodes();
			for (int i = 0; i < definitionsElementChildNodes.getLength(); i++) {
				org.w3c.dom.Node definitionsElementChildNode = definitionsElementChildNodes.item(i);
				if (definitionsElementChildNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element definitionsElementChildElement = (Element) definitionsElementChildNode;
					if (definitionsElementChildElement.getLocalName().equals("process")) {
						if (processElement == null) {
							processElement = definitionsElementChildElement;
						} else {
							throw new BpmnParserException("Expected only one 'process' element; found more!");
						}
					}
				}
			}
			if (processElement == null) {
				throw new BpmnParserException("Expected only one 'process' element; found none!");
			}
			String processType = processElement.getAttribute("processType");
			String processIsExecutable = processElement.getAttribute("isExecutable");
			String processId = processElement.getAttribute("id");
			String processName = processElement.getAttribute("name");
			
			// Create an instance of BpmnProcess with the appropriate name:
			this.bp = new BpmnProcess(processName);
			log.debug("Created BPMN process '" + this.bp.getName() + "'");
			this.bp.setDefinitionsId(definitionsId);
			this.bp.setTargetNamespace(definitionsTargetNamespace);
			this.bp.setTypeLanguage(definitionsTypeLanguage);
			this.bp.setExpressionLanguage(definitionsExpressionLanguage);
			this.bp.setType(processType);
			this.bp.setExecutable(processIsExecutable.equals("true"));
			this.bp.setId(processId);
			
			NodeList processElementChildNodes = processElement.getChildNodes();
			for (int i = 0; i < processElementChildNodes.getLength(); i++) {
				org.w3c.dom.Node processElementChildNode = processElementChildNodes.item(i);
				if (processElementChildNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element processElementChildElement = (Element) processElementChildNode;
					parseElement(processElementChildElement, this.bp);
				}
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		return this.bp;
	}
	
	public BpmnProcess parse(String path) {
		
		return parse(new File(path));
	}
	
	private void parseElement(Element element, BpmnCompositeActivity ca) throws BpmnParserException {
		
		String elementLocalName = element.getLocalName();
		if (elementLocalName.equals("scriptTask")) {
			parseScriptTaskElement(element, ca);
		} else if (elementLocalName.equals("startEvent")) {
			parseStartEventElement(element, ca);
		} else if (elementLocalName.equals("endEvent")) {
			parseEndEventElement(element, ca);
		} else if (elementLocalName.equals("sequenceFlow")) {
			parseSequenceFlowElement(element, ca);
		} else if (elementLocalName.equals("subProcess")) {
			parseSubProcessElement(element, ca);
		} else if (elementLocalName.equals("parallelGateway")) {
			parseParallelGatewayElement(element, ca);
		} else if (elementLocalName.equals("exclusiveGateway")) {
			parseExclusiveGatewayElement(element, ca);
		} else {
			throw new BpmnParserException("Unsupported element '" + elementLocalName + "'");
		}
	}
	
	private void parseScriptTaskElement(Element element, BpmnCompositeActivity ca) {
		
		String scriptTaskId = element.getAttribute("id");
		String scriptTaskName = element.getAttribute("name");
		BpmnScriptTask scriptTask = new BpmnScriptTask(scriptTaskName, scriptTaskId);
		String script = null;
		NodeList scriptTaskChildNodes = element.getChildNodes();
		for (int i = 0; i < scriptTaskChildNodes.getLength(); i++) {
			org.w3c.dom.Node scriptTaskChildNode = scriptTaskChildNodes.item(i);
			if (scriptTaskChildNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element scriptTaskChildElement = (Element) scriptTaskChildNode;
				if (scriptTaskChildElement.getLocalName().equals("script")) {
					script = scriptTaskChildNode.getTextContent();
				}
			}
		}
		scriptTask.setScript(script);
		ca.addActivity(scriptTask);
		this.idToNodeMap.put(scriptTaskId, scriptTask);
	}
	
	private void parseStartEventElement(Element element, BpmnCompositeActivity ca) {
		
		String startEventId = element.getAttribute("id");
		String startEventName = element.getAttribute("name");
		BpmnStartEvent startEvent = new BpmnStartEvent(startEventName, startEventId);
		ca.setStartEvent(startEvent);
		this.idToNodeMap.put(startEventId, startEvent);
	}
	
	private void parseEndEventElement(Element element, BpmnCompositeActivity ca) {
		
		String endEventId = element.getAttribute("id");
		String endEventName = element.getAttribute("name");
		BpmnEndEvent endEvent = new BpmnEndEvent(endEventName, endEventId);
		ca.setEndEvent(endEvent);
		this.idToNodeMap.put(endEventId, endEvent);
	}
	
	private void parseSequenceFlowElement(Element element, BpmnCompositeActivity ca) {
		
		// TODO A sequence flow may have a condition associated with it (if its source is an XOR split)
		
		String id = element.getAttribute("id");
		String sourceId = element.getAttribute("sourceRef");
		String targetId = element.getAttribute("targetRef");
		ControlOutputPort source;
		Node sourceNode = this.idToNodeMap.get(sourceId);
		if (sourceNode instanceof Split) {
			source = ((Split) sourceNode).getNewControlOutputPort();
		} else {
			source = sourceNode.getControlOutputPorts().get(0); // All nodes except splits have exactly one control output port
		}
		ControlInputPort target;
		Node targetNode = this.idToNodeMap.get(targetId);
		if (targetNode instanceof Join) {
			target = ((Join) targetNode).getNewControlInputPort();
		} else {
			target = targetNode.getControlInputPorts().get(0); // All nodes except joins have exactly one control input port
		}
		((BpmnTransition) ca.connect(source, target)).setId(id);
	}
	
	private void parseSubProcessElement(Element element, BpmnCompositeActivity ca) throws BpmnParserException {
		
		String subProcessId = element.getAttribute("id");
		String subProcessName = element.getAttribute("name");
		BpmnSubProcess subProcess = new BpmnSubProcess(subProcessName, subProcessId);
		NodeList elementChildNodes = element.getChildNodes();
		for (int i = 0; i < elementChildNodes.getLength(); i++) {
			org.w3c.dom.Node elementChildNode = elementChildNodes.item(i);
			if (elementChildNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element elementChildElement = (Element) elementChildNode;
				parseElement(elementChildElement, subProcess);
			}
		}
		ca.addActivity(subProcess);
		this.idToNodeMap.put(subProcessId, subProcess);
	}
	
	private void parseParallelGatewayElement(Element element, BpmnCompositeActivity ca) throws BpmnParserException {
		
		String parallelGatewayId = element.getAttribute("id");
		String parallelGatewayName = element.getAttribute("name");
		String parallelGatewayDirection = element.getAttribute("gatewayDirection");
		if (parallelGatewayDirection.equals("Diverging")) {
			BpmnAndSplit andSplit = new BpmnAndSplit(parallelGatewayName, parallelGatewayId);
			ca.addControlNode(andSplit);
			this.idToNodeMap.put(parallelGatewayId, andSplit);
		} else if (parallelGatewayDirection.equals("Converging")) {
			BpmnAndJoin andJoin = new BpmnAndJoin(parallelGatewayName, parallelGatewayId);
			ca.addControlNode(andJoin);
			this.idToNodeMap.put(parallelGatewayId, andJoin);
		} else {
			throw new BpmnParserException("Parallel gateway direction '" + parallelGatewayDirection + "' is not supported");
		}
	}
	
	private void parseExclusiveGatewayElement(Element element, BpmnCompositeActivity ca) throws BpmnParserException {
		
		String exclusiveGatewayId = element.getAttribute("id");
		String exclusiveGatewayName = element.getAttribute("name");
		String exclusiveGatewayDirection = element.getAttribute("gatewayDirection");
		if (exclusiveGatewayDirection.equals("Diverging")) {
			BpmnXorSplit xorSplit = new BpmnXorSplit(exclusiveGatewayName, exclusiveGatewayId);
			ca.addControlNode(xorSplit);
			this.idToNodeMap.put(exclusiveGatewayId, xorSplit);
		} else if (exclusiveGatewayDirection.equals("Converging")) {
			BpmnXorJoin xorJoin = new BpmnXorJoin(exclusiveGatewayName, exclusiveGatewayId);
			ca.addControlNode(xorJoin);
			this.idToNodeMap.put(exclusiveGatewayId, xorJoin);
		} else {
			throw new BpmnParserException("Exclusive gateway direction '" + exclusiveGatewayDirection + "' is not supported");
		}
	}
}

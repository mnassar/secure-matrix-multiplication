package org.unify_framework.instances.bpmn.serializer;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Transition;
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
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnSerializer.java 18724 2011-12-04 10:33:10Z njonchee $
 */
public class BpmnSerializer implements ElementVisitor {
	
	public static final String BPMN_NAMESPACE_URI = "http://www.omg.org/spec/BPMN/20100524/MODEL";
	
	private static final Log log = LogFactory.getLog(BpmnSerializer.class);
	
	private Element currentElement;
	private Document document;
	
	private int activityCount;
	
	public void serialize(BpmnProcess process, File file) {
		
		try {
			
			// Initialize the document:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			this.document = db.newDocument();
			
			this.activityCount = 0;
			
			// Visit the BPMN process, thus generating the document:
			process.accept(this);
			
			log.debug("Serialized " + this.activityCount + " activities");
			
			// Serialize the document using a DOM Level 3 serializer:
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSSerializer serializer = ls.createLSSerializer();
			DOMConfiguration config = serializer.getDomConfig();
			config.setParameter("format-pretty-print", Boolean.TRUE);
			LSOutput output = ls.createLSOutput();
			output.setByteStream(new FileOutputStream(file));
			serializer.write(this.document, output);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}
	
	public void serialize(BpmnProcess process, String path) {
		
		serialize(process, new File(path));
	}
	
	private void visitBpmnCompositeActivity(BpmnCompositeActivity compositeActivity) {
		
		((BpmnStartEvent) compositeActivity.getStartEvent()).accept(this);
		
		for (Activity activity : compositeActivity.getActivities()) {
			if (activity instanceof BpmnScriptTask) {
				((BpmnScriptTask) activity).accept(this);
			} else if (activity instanceof BpmnSubProcess) {
				((BpmnSubProcess) activity).accept(this);
			} else {
				String msg = "Cannot serialize activities of type '" + activity.getClass().getCanonicalName() + "'";
				log.error(msg);
				throw new BpmnSerializerException(msg);
			}
		}
		
		for (ControlNode controlNode : compositeActivity.getControlNodes()) {
			if (controlNode instanceof BpmnAndSplit) {
				((BpmnAndSplit) controlNode).accept(this);
			} else if (controlNode instanceof BpmnAndJoin) {
				((BpmnAndJoin) controlNode).accept(this);
			} else if (controlNode instanceof BpmnXorSplit) {
				((BpmnXorSplit) controlNode).accept(this);
			} else if (controlNode instanceof BpmnXorJoin) {
				((BpmnXorJoin) controlNode).accept(this);
			} else {
				throw new BpmnSerializerException("Cannot serialize control nodes of type '" + controlNode.getClass().getCanonicalName() + "'");
			}
		}
		
		((BpmnEndEvent) compositeActivity.getEndEvent()).accept(this);
		
		for (Transition transition : compositeActivity.getTransitions()) {
			if (transition instanceof BpmnTransition) {
				((BpmnTransition) transition).accept(this);
			} else {
				throw new BpmnSerializerException("Cannot serialize transitions of type '" + transition.getClass().getCanonicalName() + "'");
			}
		}
	}
	
	@Override
	public void visit(BpmnSubProcess subProcess) {
		
		log.debug("Visiting BPMN sub-process '" + subProcess.getName() + "'");
		
		Element subProcessElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "subProcess");
		subProcessElement.setAttribute("id", subProcess.getId());
		subProcessElement.setAttribute("name", subProcess.getName());
		this.currentElement.appendChild(subProcessElement);
		
		Element oldCurrentElement = this.currentElement;
		this.currentElement = subProcessElement;
		
		visitBpmnCompositeActivity(subProcess);

		this.currentElement = oldCurrentElement;
	}
	
	@Override
	public void visit(BpmnProcess process) {
		
		log.debug("Visiting BPMN process '" + process.getName() + "'");
		
		Element definitionsElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "definitions");
		definitionsElement.setAttribute("id", process.getDefinitionsId());
		definitionsElement.setAttribute("targetNamespace", process.getTargetNamespace());
		definitionsElement.setAttribute("typeLanguage", process.getTypeLanguage());
		definitionsElement.setAttribute("expressionLanguage", process.getExpressionLanguage());
		this.document.appendChild(definitionsElement);
		
		Element processElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "process");
		processElement.setAttribute("processType", process.getType());
		processElement.setAttribute("isExecutable", process.isExecutable() ? "true" : "false");
		processElement.setAttribute("id", process.getId());
		processElement.setAttribute("name", process.getName());
		definitionsElement.appendChild(processElement);
		
		this.currentElement = processElement;
		
		visitBpmnCompositeActivity(process);
	}
	
	@Override
	public void visit(BpmnScriptTask scriptTask) {
		
		Element scriptTaskElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "scriptTask");
		scriptTaskElement.setAttribute("id", scriptTask.getId());
		scriptTaskElement.setAttribute("name", scriptTask.getName());
		currentElement.appendChild(scriptTaskElement);
		
		String script = scriptTask.getScript();
		if (script != null) {
			Element scriptElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "script");
			scriptElement.setTextContent(script);
			scriptTaskElement.appendChild(scriptElement);
		}
		
		this.activityCount++;
	}
	
	@Override
	public void visit(BpmnStartEvent startEvent) {

		Element startEventElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "startEvent");
		startEventElement.setAttribute("id", startEvent.getId());
		startEventElement.setAttribute("name", startEvent.getName());
		currentElement.appendChild(startEventElement);
	}
	
	@Override
	public void visit(BpmnEndEvent endEvent) {
		
		Element endEventElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "endEvent");
		endEventElement.setAttribute("id", endEvent.getId());
		endEventElement.setAttribute("name", endEvent.getName());
		currentElement.appendChild(endEventElement);
		
		Element terminateEventDefinitionElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "terminateEventDefinition");
		endEventElement.appendChild(terminateEventDefinitionElement);
	}
	
	@Override
	public void visit(BpmnTransition transition) {
		
		Element startEventElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "sequenceFlow");
		String sourceId;
		Node source = transition.getSourceNode();
		if (source instanceof BpmnScriptTask) {
			sourceId = ((BpmnScriptTask) source).getId();
		} else if (source instanceof BpmnStartEvent) {
			sourceId = ((BpmnStartEvent) source).getId();
		} else if (source instanceof BpmnEndEvent) {
			sourceId = ((BpmnEndEvent) source).getId();
		} else if (source instanceof BpmnSubProcess) {
			sourceId = ((BpmnSubProcess) source).getId();
		} else if (source instanceof BpmnAndSplit) {
			sourceId = ((BpmnAndSplit) source).getId();
		} else if (source instanceof BpmnAndJoin) {
			sourceId = ((BpmnAndJoin) source).getId();
		} else if (source instanceof BpmnXorSplit) {
			sourceId = ((BpmnXorSplit) source).getId();
		} else if (source instanceof BpmnXorJoin) {
			sourceId = ((BpmnXorJoin) source).getId();
		} else {
			throw new BpmnSerializerException("Cannot serialize transitions with source of type '" + source.getClass().getCanonicalName() + "'");
		}
		startEventElement.setAttribute("sourceRef", sourceId);
		String targetId;
		Node target = transition.getDestinationNode();
		if (target instanceof BpmnScriptTask) {
			targetId = ((BpmnScriptTask) target).getId();
		} else if (target instanceof BpmnStartEvent) {
			targetId = ((BpmnStartEvent) target).getId();
		} else if (target instanceof BpmnEndEvent) {
			targetId = ((BpmnEndEvent) target).getId();
		} else if (target instanceof BpmnSubProcess) {
			targetId = ((BpmnSubProcess) target).getId();
		} else if (target instanceof BpmnAndSplit) {
			targetId = ((BpmnAndSplit) target).getId();
		} else if (target instanceof BpmnAndJoin) {
			targetId = ((BpmnAndJoin) target).getId();
		} else if (target instanceof BpmnXorSplit) {
			targetId = ((BpmnXorSplit) target).getId();
		} else if (target instanceof BpmnXorJoin) {
			targetId = ((BpmnXorJoin) target).getId();
		} else {
			throw new BpmnSerializerException("Cannot serialize transitions with destination of type '" + target.getClass().getCanonicalName() + "'");
		}
		startEventElement.setAttribute("targetRef", targetId);
		String id = transition.getId();
		if (id == null) { id = sourceId + "-" + targetId; }
		startEventElement.setAttribute("id", id);
		currentElement.appendChild(startEventElement);
	}
	
	@Override
	public void visit(BpmnAndSplit andSplit) {
		
		Element parallelGatewayElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "parallelGateway");
		parallelGatewayElement.setAttribute("id", andSplit.getId());
		parallelGatewayElement.setAttribute("name", andSplit.getName());
		parallelGatewayElement.setAttribute("gatewayDirection", "Diverging");
		currentElement.appendChild(parallelGatewayElement);
	}
	
	@Override
	public void visit(BpmnAndJoin andJoin) {
		
		Element parallelGatewayElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "parallelGateway");
		parallelGatewayElement.setAttribute("id", andJoin.getId());
		parallelGatewayElement.setAttribute("name", andJoin.getName());
		parallelGatewayElement.setAttribute("gatewayDirection", "Converging");
		currentElement.appendChild(parallelGatewayElement);
	}
	
	@Override
	public void visit(BpmnXorSplit xorSplit) {
		
		Element exclusiveGatewayElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "exclusiveGateway");
		exclusiveGatewayElement.setAttribute("id", xorSplit.getId());
		exclusiveGatewayElement.setAttribute("name", xorSplit.getName());
		exclusiveGatewayElement.setAttribute("gatewayDirection", "Diverging");
		currentElement.appendChild(exclusiveGatewayElement);
	}
	
	@Override
	public void visit(BpmnXorJoin xorJoin) {
		
		Element exclusiveGatewayElement = this.document.createElementNS(BPMN_NAMESPACE_URI, "exclusiveGateway");
		exclusiveGatewayElement.setAttribute("id", xorJoin.getId());
		exclusiveGatewayElement.setAttribute("name", xorJoin.getName());
		exclusiveGatewayElement.setAttribute("gatewayDirection", "Converging");
		currentElement.appendChild(exclusiveGatewayElement);
	}
}

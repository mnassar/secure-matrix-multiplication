package org.unify_framework.instances.bpel.serializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.instances.bpel.BpelAndJoin;
import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelAtomicActivity;
import org.unify_framework.instances.bpel.BpelCompositeActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelCopyExpressionToExpressionActivity;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelEmptyActivity;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelFromVariable;
import org.unify_framework.instances.bpel.BpelGetValueFromDictionaryActivity;
import org.unify_framework.instances.bpel.BpelGetValueFromNestedDictionaryActivity;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInitializeDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelInitializeNestedDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelCopyExpressionToVariableActivity;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelInvokeMonitoringServiceActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelSequence;
import org.unify_framework.instances.bpel.BpelThrowActivity;
import org.unify_framework.instances.bpel.BpelToExpression;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariable;
import org.unify_framework.instances.bpel.BpelVariableElement;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.BpelXorJoin;
import org.unify_framework.instances.bpel.BpelXorSplit;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

import org.w3c.dom.Document;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelSerializer.java 20937 2012-07-05 17:12:38Z njonchee $
 */
public class BpelSerializer implements ElementVisitor {
	
	public static final String BPEL_NAMESPACE_URI = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";
	public static final String DICTIONARY_NAMESPACE_URI = "http://unify-framework.org/Util/Dictionary";
	public static final String MONITORING_NAMESPACE_URI = "http://back_end.monitoring.examples.unify_framework.org/";
	public static final String MONITORING_XSD_NAMESPACE_URI = "http://back_end.monitoring.examples.unify_framework.org/xsd";
	public static final String NESTED_DICTIONARY_NAMESPACE_URI = "http://unify-framework.org/Util/NestedDictionary";
	public static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
	
	/** The BPEL serializer's Apache Commons Logging {@link Log}. */
	private static final Log log = LogFactory.getLog(BpelSerializer.class);
	
	private Element currentElement;
	private Element currentFlowElement;
	private Document document;
	
	private int activityCount;
	
	private Map<Node, Element> unifyToDomMapping = new HashMap<Node, Element>();
	private Map<Node, Element> elementsToInsert = new HashMap<Node, Element>();
	
	public BpelSerializer() {
		
		// Do nothing
	}
	
	public void serialize(BpelProcess process, File file) {
		
		try {
			
			// Initialize the document:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			this.document = db.newDocument();
			
			this.activityCount = 0;
			
			// Visit the BPEL process, thus generating the document:
			process.accept(this);
			
			log.info("Serialized " + this.activityCount + " activities");
			
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
	
	public void serialize(BpelProcess process, String path) {
		
		serialize(process, new File(path));
	}
	
	private Element traversePathUntil(ControlOutputPort predecessor, Node until) throws BpelSerializerException {
		
		Element oldCurrentElement = this.currentElement;
		Element sequenceElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:sequence");
		this.currentElement = sequenceElement;
		Node currentNode = predecessor.getOutgoingTransition().getDestinationNode();
		while (currentNode != until) {
			if (currentNode instanceof BpelAtomicActivity) {
				this.activityCount++;
				BpelAtomicActivity atomicActivity = (BpelAtomicActivity) currentNode; 
				atomicActivity.accept(this);
				currentNode = atomicActivity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelGetValueFromDictionaryActivity) {
				this.activityCount++;
				BpelGetValueFromDictionaryActivity activity = (BpelGetValueFromDictionaryActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelGetValueFromNestedDictionaryActivity) {
				this.activityCount++;
				BpelGetValueFromNestedDictionaryActivity activity = (BpelGetValueFromNestedDictionaryActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelInitializeDictionaryVariableActivity) {
				this.activityCount++;
				BpelInitializeDictionaryVariableActivity activity = (BpelInitializeDictionaryVariableActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelInitializeNestedDictionaryVariableActivity) {
				this.activityCount++;
				BpelInitializeNestedDictionaryVariableActivity activity = (BpelInitializeNestedDictionaryVariableActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelThrowActivity) {
				this.activityCount++;
				BpelThrowActivity activity = (BpelThrowActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelCopyExpressionToVariableActivity) {
				this.activityCount++;
				BpelCopyExpressionToVariableActivity activity = (BpelCopyExpressionToVariableActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelCopyExpressionToExpressionActivity) {
				this.activityCount++;
				BpelCopyExpressionToExpressionActivity activity = (BpelCopyExpressionToExpressionActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelInvokeMonitoringServiceActivity) {
				this.activityCount++;
				BpelInvokeMonitoringServiceActivity activity = (BpelInvokeMonitoringServiceActivity) currentNode; 
				activity.accept(this);
				currentNode = activity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelCompositeActivity) {
				this.activityCount++;
				BpelCompositeActivity compositeActivity = (BpelCompositeActivity) currentNode;
				compositeActivity.accept(this);
				currentNode = compositeActivity.getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelAndSplit) {
				BpelAndSplit andSplit = (BpelAndSplit) currentNode;
				if (andSplit.getCorrespondingAndJoin() != null) {
					andSplit.accept(this);
					currentNode = andSplit.getCorrespondingAndJoin().getOutgoingTransition().getDestinationNode();
				} else if (andSplit.getUnstructuredCorrespondingAndJoin() != null) {
					BpelAndJoin andJoin = (BpelAndJoin) andSplit.getUnstructuredCorrespondingAndJoin();
					if (andSplit.getDestinationNodes().size() != 2) {
						throw new BpelSerializerException("The AND-join '" + andJoin.getQualifiedName() + "' does not have exactly two outgoing transitions.");
					}
					Node nextNodeA = andSplit.getDestinationNodes().get(0);
					Node nextNodeB = andSplit.getDestinationNodes().get(1);
					Node defaultNode;
					Node syncingNode;
					if (findNodeBeforeOtherNode(nextNodeA, andJoin, until)) {
						defaultNode = nextNodeB;
						syncingNode = nextNodeA;
					} else if (findNodeBeforeOtherNode(nextNodeB, andJoin, until)) {
						defaultNode = nextNodeA;
						syncingNode = nextNodeB;
					} else {
						throw new BpelSerializerException("There is a bug in this code. :-)");
					}
					currentNode = defaultNode;
					if (syncingNode.getOutgoingTransitions().size() == 1 && syncingNode.getOutgoingTransitions().get(0).getDestinationNode() == andJoin) {
						// There is only a single syncing activity between the split and the join
						// - Add advice activity to flow element:
						Element otherOldCurrentElement = this.currentElement;
						this.currentElement = currentFlowElement;
						visit((BpelScopeActivity) syncingNode);
						this.currentElement = otherOldCurrentElement;
						// - Add two links to flow element, e.g., LinkA and LinkB:
						Element linksElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:links");
						Element linkElementA = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:link");
						linkElementA.setAttribute("name", "LinkA"); // NOTE This code assumes a maximum of only one sync per flow element
						linksElement.appendChild(linkElementA);
						Element linkElementB = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:link");
						linkElementB.setAttribute("name", "LinkB");
						linksElement.appendChild(linkElementB);
						currentFlowElement.insertBefore(linksElement, currentFlowElement.getFirstChild());
						// - LinkA source = predecessor of andSplit
						Node predecessorOfAndSplit = andSplit.getSourceNode();
						Element predecessorOfAndSplitElement = this.unifyToDomMapping.get(predecessorOfAndSplit);
						if (predecessorOfAndSplitElement == null) { throw new BpelSerializerException("Predecessor of AND-split hasn't been serialized yet!"); }
						Element sourcesElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:sources");
						Element sourceElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:source");
						sourceElement.setAttribute("linkName", "LinkA");
						sourcesElement.appendChild(sourceElement);
						predecessorOfAndSplitElement.insertBefore(sourcesElement, predecessorOfAndSplitElement.getFirstChild());
						// - LinkA target = advice activity
						Element syncingNodeElement = this.unifyToDomMapping.get(syncingNode);
						Element targetsElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:targets");
						Element targetElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:target");
						targetElement.setAttribute("linkName", "LinkA");
						targetsElement.appendChild(targetElement);
						syncingNodeElement.insertBefore(targetsElement, syncingNodeElement.getFirstChild());
						// - LinkB source = advice activity
						sourcesElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:sources");
						sourceElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:source");
						sourceElement.setAttribute("linkName", "LinkB");
						sourcesElement.appendChild(sourceElement);
						syncingNodeElement.insertBefore(sourcesElement, syncingNodeElement.getFirstChild());
						// - LinkB target = successor of andJoin
						Node successorOfAndJoin = andJoin.getDestinationNode();
						targetsElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:targets");
						targetElement = document.createElementNS(BPEL_NAMESPACE_URI, "bpel:target");
						targetElement.setAttribute("linkName", "LinkB");
						targetsElement.appendChild(targetElement);
						this.elementsToInsert.put(successorOfAndJoin, targetsElement);
//						if (successorOfAndJoinElement == null) { throw new BpelSerializerException("Successor of AND-join hasn't been serialized yet!"); }
//						log.error("The WS-BPEL serializer cannot serialize syncs between branches (yet)!");
					} else {
						throw new NotImplementedException("Can't handle more than one synchronizing activity...");
					}
				} else {
					throw new NotImplementedException("The WS-BPEL serializer does not support AND-splits that do not have a (possibly unstructured) corresponding AND-join.");
				}
			} else if (currentNode instanceof BpelAndJoin) {
				BpelAndJoin andJoin = (BpelAndJoin) currentNode;
				if (andJoin.getCorrespondingAndSplit() != null) {
					// Do nothing
				} else {
					// Continue
					currentNode = andJoin.getOutgoingTransition().getDestinationNode();
				}
			} else if (currentNode instanceof BpelXorSplit) {
				BpelXorSplit xorSplit = (BpelXorSplit) currentNode;
				xorSplit.accept(this);
				currentNode = xorSplit.getCorrespondingXorJoin().getOutgoingTransition().getDestinationNode();
			} else if (currentNode instanceof BpelXorJoin) {
				BpelXorJoin xorJoin = (BpelXorJoin) currentNode;
				xorJoin.accept(this);
				currentNode = xorJoin.getCorrespondingXorSplit().getDefaultOutgoingTransition().getDestinationNode();
			} else {
				log.debug("Current node name is '" + currentNode.getQualifiedName() + "'");
				throw new BpelSerializerException("The BPEL serializer currently does not support nodes of type '" + currentNode.getClass().getName() + "'");
			}
		}
		this.currentElement = oldCurrentElement;
		return sequenceElement;
	}
	
	private boolean findNodeBeforeOtherNode(Node start, Node node, Node otherNode) {
		
		if (start == node) {
			return true;
		} else if (start == otherNode) {
			return false;
		} else if (start.getOutgoingTransitions().size() == 1) {
			return findNodeBeforeOtherNode(start.getOutgoingTransitions().get(0).getDestinationNode(), node, otherNode);
		} else {
			throw new BpelSerializerException("Didn't expect any splits here...");
		}
	}

	@Override
	public void visit(BpelAndSplit andSplit) {
		
		Element flowElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:flow");
		this.unifyToDomMapping.put(andSplit, flowElement);
		this.currentFlowElement = flowElement;
		flowElement.setAttribute("name", andSplit.getName());//.substring(0, andSplit.getName().length() - 6)); // Trim '_Split' from the end of the name
		for (ControlOutputPort controlOutputPort : andSplit.getControlOutputPorts()) {
			Element childElement = traversePathUntil(controlOutputPort, andSplit.getCorrespondingAndJoin());
			flowElement.appendChild(childElement);
		}
		this.currentElement.appendChild(flowElement);
	}

	@Override
	public void visit(BpelAssignActivity assignActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		this.unifyToDomMapping.put(assignActivity, assignElement);
		assignElement.setAttribute("name", assignActivity.getName());
		assignElement.setAttribute("validate", assignActivity.getValidate());
		Element oldCurrentElement = this.currentElement;
		this.currentElement = assignElement;
		for (BpelCopy copy : assignActivity.getCopies()) {
			copy.accept(this);
		}
		this.currentElement = oldCurrentElement;
		this.currentElement.appendChild(assignElement);
	}

	@Override
	public void visit(BpelCopy copy) {
		
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		Element oldCurrentElement = this.currentElement;
		this.currentElement = copyElement;
		copy.getFrom().accept(this);
		copy.getTo().accept(this);
		this.currentElement = oldCurrentElement;
		this.currentElement.appendChild(copyElement);
	}
	
	@Override
	public void visit(BpelCopyExpressionToExpressionActivity copyExpressionToExpressionActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		assignElement.setAttribute("name", copyExpressionToExpressionActivity.getName());
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		Expression source = copyExpressionToExpressionActivity.getSourceExpression();
		if (source instanceof XpathExpressionImpl) {
			XpathExpressionImpl xpathSource = (XpathExpressionImpl) source;
			fromElement.setTextContent(xpathSource.getExpression());
			for (Map.Entry<String, String> namespace : xpathSource.getNamespaces().entrySet()) {
				fromElement.setAttribute(namespace.getKey(), namespace.getValue());
			}
		} else {
			throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
		}
		copyElement.appendChild(fromElement);
		
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		Expression target = copyExpressionToExpressionActivity.getTargetExpression();
		if (target instanceof XpathExpressionImpl) {
			XpathExpressionImpl xpathTarget = (XpathExpressionImpl) target;
			toElement.setTextContent(xpathTarget.getExpression());
			for (Map.Entry<String, String> namespace : xpathTarget.getNamespaces().entrySet()) {
				toElement.setAttribute( namespace.getKey(), namespace.getValue());
			}
		} else {
			throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
		}
		copyElement.appendChild(toElement);
		
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
	}
	
	@Override
	public void visit(BpelCopyExpressionToVariableActivity copyExpressionToVariableActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		assignElement.setAttribute("name", copyExpressionToVariableActivity.getName());
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		Expression source = copyExpressionToVariableActivity.getSourceExpression();
		if (source instanceof XpathExpressionImpl) {
			XpathExpressionImpl xpathSource = (XpathExpressionImpl) source;
			fromElement.setTextContent(xpathSource.getExpression());
			for (Map.Entry<String, String> namespace : xpathSource.getNamespaces().entrySet()) {
				fromElement.setAttribute( namespace.getKey(), namespace.getValue());
			}
		} else {
			throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
		}
		copyElement.appendChild(fromElement);
		
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", copyExpressionToVariableActivity.getTargetVariable().getName());
		copyElement.appendChild(toElement);
		
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
	}

	@Override
	public void visit(BpelFromExpression from) {
		
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		fromElement.appendChild(this.document.importNode(from.getExpression(), true));
		this.currentElement.appendChild(fromElement);
	}

	@Override
	public void visit(BpelFromVariable from) {
		
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		fromElement.setAttribute("variable", from.getVariable());
		String part = from.getPart();
		if (part != null) {
			fromElement.setAttribute("part", part);
		}
		String query = from.getQuery();
		if (query != null) {
			Element queryElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:query");
			queryElement.setTextContent(query);
			// Serialize the from variable's namespace declarations:
			for (Map.Entry<String, String> entry : from.getNamespaceDeclarations().entrySet()) {
				queryElement.setAttribute( entry.getKey(), entry.getValue());
			}
			fromElement.appendChild(queryElement);
		}
		this.currentElement.appendChild(fromElement);
	}
	
	@Override
	public void visit(BpelGetValueFromDictionaryActivity getValueFromDictionaryActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		assignElement.setAttribute("name", getValueFromDictionaryActivity.getName());
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		fromElement.setAttribute("xmlns:d", DICTIONARY_NAMESPACE_URI);
		fromElement.setTextContent("$" + getValueFromDictionaryActivity.getSourceVariableName() + "/d:key[@key=" + getValueFromDictionaryActivity.getKey() + "]/d:value/@value");
		copyElement.appendChild(fromElement);
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		Expression target = getValueFromDictionaryActivity.getTargetExpression();
		if (target instanceof XpathExpressionImpl) {
			XpathExpressionImpl xpathTarget = (XpathExpressionImpl) target;
			toElement.setTextContent(xpathTarget.getExpression());
			for (Map.Entry<String, String> namespace : xpathTarget.getNamespaces().entrySet()) {
				toElement.setAttribute("xmlns:" + namespace.getKey(), namespace.getValue());
			}
		} else {
			throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
		}
		copyElement.appendChild(toElement);
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
	}
	
	@Override
	public void visit(BpelGetValueFromNestedDictionaryActivity getValueFromNestedDictionaryActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		assignElement.setAttribute("name", getValueFromNestedDictionaryActivity.getName());
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		fromElement.setAttribute("xmlns:nd", NESTED_DICTIONARY_NAMESPACE_URI);
		
//		fromElement.setTextContent("$" + getValueFromNestedDictionaryActivity.getSourceVariableName());
//		fromElement.setTextContent("$" + getValueFromNestedDictionaryActivity.getSourceVariableName() + "/nd:firstKey[@key=" + getValueFromNestedDictionaryActivity.getFirstKey() + "]");
		fromElement.setTextContent("$" + getValueFromNestedDictionaryActivity.getSourceVariableName() + "/nd:firstKey[@key=" + getValueFromNestedDictionaryActivity.getFirstKey() + "]/nd:secondKey[@key=" + getValueFromNestedDictionaryActivity.getSecondKey() + "]/nd:value/@value");
		
		copyElement.appendChild(fromElement);
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", getValueFromNestedDictionaryActivity.getTargetVariableName());
		copyElement.appendChild(toElement);
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
	}
	
	@Override
	public void visit(BpelImport bpelImport) {
		
		Element importElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:import");
		if (bpelImport.getNamespace() != null) importElement.setAttribute("namespace", bpelImport.getNamespace());
		if (bpelImport.getLocation() != null) importElement.setAttribute("location", bpelImport.getLocation());
		importElement.setAttribute("importType", bpelImport.getImportType());
		this.currentElement.appendChild(importElement);
	}
	
	@Override
	public void visit(BpelInitializeDictionaryVariableActivity initializeDictionaryVariableActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		assignElement.setAttribute("name", initializeDictionaryVariableActivity.getName());
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		Element literalElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:literal");
		Element dictionaryElement = this.document.createElementNS(DICTIONARY_NAMESPACE_URI, "dictionary");
		Map<String, String> dictionary = initializeDictionaryVariableActivity.getDictionary();
		for (Map.Entry<String, String> entry : dictionary.entrySet()) {
			Element keyElement = this.document.createElementNS(DICTIONARY_NAMESPACE_URI, "key");
			keyElement.setAttribute("key", entry.getKey());
			Element valueElement = this.document.createElementNS(DICTIONARY_NAMESPACE_URI, "value");
			valueElement.setAttribute("value", entry.getValue());
			keyElement.appendChild(valueElement);
			dictionaryElement.appendChild(keyElement);
		}
		literalElement.appendChild(dictionaryElement);
		fromElement.appendChild(literalElement);
		copyElement.appendChild(fromElement);
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", initializeDictionaryVariableActivity.getVariable().getName());
		// TODO Do we need to add a query element here?
		copyElement.appendChild(toElement);
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
	}
	
	@Override
	public void visit(BpelInitializeNestedDictionaryVariableActivity initializeNestedDictionaryVariableActivity) {
		
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		assignElement.setAttribute("name", initializeNestedDictionaryVariableActivity.getName());
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		Element literalElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:literal");
		Element dictionaryElement = this.document.createElementNS(NESTED_DICTIONARY_NAMESPACE_URI, "nestedDictionary");
		Map<String, Map<String, String>> dictionary = initializeNestedDictionaryVariableActivity.getDictionary();
		for (String firstKey : dictionary.keySet()) {
			Element firstKeyElement = this.document.createElementNS(NESTED_DICTIONARY_NAMESPACE_URI, "firstKey");
			firstKeyElement.setAttribute("key", firstKey);
			Map<String, String> nestedDictionary = dictionary.get(firstKey);
			for (String secondKey : nestedDictionary.keySet()) {
				Element secondKeyElement = this.document.createElementNS(NESTED_DICTIONARY_NAMESPACE_URI, "secondKey");
				secondKeyElement.setAttribute("key", secondKey);
				String value = nestedDictionary.get(secondKey);
				Element valueElement = this.document.createElementNS(NESTED_DICTIONARY_NAMESPACE_URI, "value");
				valueElement.setAttribute("value", value);
				secondKeyElement.appendChild(valueElement);
				firstKeyElement.appendChild(secondKeyElement);
			}
			dictionaryElement.appendChild(firstKeyElement);
		}
		literalElement.appendChild(dictionaryElement);
		fromElement.appendChild(literalElement);
		copyElement.appendChild(fromElement);
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", initializeNestedDictionaryVariableActivity.getVariable().getName());
		// TODO Do we need to add a query element here?
		copyElement.appendChild(toElement);
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
	}

	@Override
	public void visit(BpelInvokeMonitoringServiceActivity invokeActivity) {
		
		Element documentElement = document.getDocumentElement(); // <process>
		Element importElement =  this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:import");
		importElement.setAttribute("importType", "http://schemas.xmlsoap.org/wsdl/");
		importElement.setAttribute("location", "MonitoringBackEndPartnerLinkType.wsdl");
		importElement.setAttribute("namespace", MONITORING_NAMESPACE_URI);
		documentElement.insertBefore(importElement, documentElement.getFirstChild());
		importElement =  this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:import");
		importElement.setAttribute("importType", "http://schemas.xmlsoap.org/wsdl/");
		importElement.setAttribute("location", "MonitoringBackEnd.wsdl");
		importElement.setAttribute("namespace", MONITORING_NAMESPACE_URI);
		documentElement.insertBefore(importElement, documentElement.getFirstChild());
		
		CompositeActivity parent = invokeActivity.getParent();
		Element parentElement = this.unifyToDomMapping.get(parent);
		
		// Generate XML namespaces
		parentElement.setAttribute("xmlns:mbe", MONITORING_NAMESPACE_URI);
		parentElement.setAttribute("xmlns:mbed", MONITORING_XSD_NAMESPACE_URI);
		
		// Generate partner link
		Element partnerLinksElement = null;
		NodeList childNodes = parentElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			org.w3c.dom.Node node = childNodes.item(i);
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (element.getLocalName().equals("partnerLinks")) {
					partnerLinksElement = element;
				}
			}
		}
		if (partnerLinksElement == null) {
			throw new BpelSerializerException("No partnerLinks element was found in the BpelInvokeMonitoringServiceActivity's surrounding scope");
		}
		Element partnerLinkElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:partnerLink");
		partnerLinkElement.setAttribute("name", "MonitoringBackEndPartnerLink");
		partnerLinkElement.setAttribute("partnerLinkType", "mbe:MonitoringBackEndPartnerLinkType");
		partnerLinkElement.setAttribute("partnerRole", "me");
		partnerLinksElement.appendChild(partnerLinkElement);
		
		// Generate input variable in surrounding scope
		Element variablesElement = null;
		childNodes = parentElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			org.w3c.dom.Node node = childNodes.item(i);
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (element.getLocalName().equals("variables")) {
					variablesElement = element;
				}
			}
		}
		if (variablesElement == null) {
			throw new BpelSerializerException("No variables element was found in the BpelInvokeMonitoringServiceActivity's surrounding scope");
		}
		Element inputVariableElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variable");
		inputVariableElement.setAttribute("messageType", "mbe:monitorRequest");
		inputVariableElement.setAttribute("name", "backEndMonitorInput");
		variablesElement.appendChild(inputVariableElement);
		Element outputVariableElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variable");
		outputVariableElement.setAttribute("messageType", "mbe:monitorResponse");
		outputVariableElement.setAttribute("name", "backEndMonitorOutput");
		variablesElement.appendChild(outputVariableElement);
		
		// Generate assign element
		Element assignElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:assign");
		Element copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		Element fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		Element literalElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:literal");
		Element monitorElement = this.document.createElementNS(MONITORING_XSD_NAMESPACE_URI, "monitor");
		Element messageElement = this.document.createElementNS(MONITORING_XSD_NAMESPACE_URI, "message");
		messageElement.setTextContent(invokeActivity.getMessage());
		monitorElement.appendChild(messageElement);
		Element usernameElement = this.document.createElementNS(MONITORING_XSD_NAMESPACE_URI, "username");
		usernameElement.setTextContent("username");
		monitorElement.appendChild(usernameElement);
		literalElement.appendChild(monitorElement);
		fromElement.appendChild(literalElement);
		copyElement.appendChild(fromElement);
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", "backEndMonitorInput");
		toElement.setAttribute("part", "parameters");
		copyElement.appendChild(toElement);
		assignElement.appendChild(copyElement);
		copyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:copy");
		fromElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:from");
		Expression usernameVariable = invokeActivity.getUsernameVariable();
		fromElement.setTextContent(usernameVariable.getExpression() + "/text()");
		if (usernameVariable instanceof XpathExpressionImpl) {
			for (Map.Entry<String, String> entry : ((XpathExpressionImpl) usernameVariable).getNamespaces().entrySet()) {
				fromElement.setAttribute("xmlns:" + entry.getKey(), entry.getValue());
			}
		}
		copyElement.appendChild(fromElement);
		toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", "backEndMonitorInput");
		toElement.setAttribute("part", "parameters");
		Element queryElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:query");
		queryElement.setTextContent("//mbed:monitor/mbed:username/text()");
		toElement.appendChild(queryElement);
		copyElement.appendChild(toElement);
		assignElement.appendChild(copyElement);
		this.currentElement.appendChild(assignElement);
		
		// Generate invoke element
		Element invokeElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:invoke");
		this.unifyToDomMapping.put(invokeActivity, invokeElement);
		invokeElement.setAttribute("inputVariable", "backEndMonitorInput");
		invokeElement.setAttribute("name", invokeActivity.getName());
		invokeElement.setAttribute("operation", "monitor");
		invokeElement.setAttribute("outputVariable", "backEndMonitorOutput");
		invokeElement.setAttribute("partnerLink", "MonitoringBackEndPartnerLink");
		invokeElement.setAttribute("portType", "mbe:MonitoringBackEndPortType");
		this.currentElement.appendChild(invokeElement);
	}
	
	@Override
	public void visit(BpelEmptyActivity emptyActivity) {
		
		Element emptyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:empty");
		this.unifyToDomMapping.put(emptyActivity, emptyElement);
		Element elementToInsert = this.elementsToInsert.get(emptyActivity);
		if (elementToInsert != null) {
			emptyElement.appendChild(elementToInsert);
		}
		emptyElement.setAttribute("name", emptyActivity.getName());
		this.currentElement.appendChild(emptyElement);
	}

	@Override
	public void visit(BpelInvokeActivity invokeActivity) {
		
		Element invokeElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:invoke");
		this.unifyToDomMapping.put(invokeActivity, invokeElement);
		invokeElement.setAttribute("name", invokeActivity.getName());
		invokeElement.setAttribute("operation", invokeActivity.getOperation());
		invokeElement.setAttribute("partnerLink", invokeActivity.getPartnerLink());
		invokeElement.setAttribute("portType", invokeActivity.getPortType());
		if (invokeActivity.getInputVariable() != null) invokeElement.setAttribute("inputVariable", invokeActivity.getInputVariable());
		if (invokeActivity.getOutputVariable() != null) invokeElement.setAttribute("outputVariable", invokeActivity.getOutputVariable());
		this.currentElement.appendChild(invokeElement);
	}

	@Override
	public void visit(BpelPartnerLink partnerLink) {
		
		Element partnerLinkElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:partnerLink");
		partnerLinkElement.setAttribute("name", partnerLink.getName());
		partnerLinkElement.setAttribute("partnerLinkType", partnerLink.getPartnerLinkType());
		String myRole = partnerLink.getMyRole();
		if (myRole != null) {
			partnerLinkElement.setAttribute("myRole", myRole);
		}
		String partnerRole = partnerLink.getPartnerRole();
		if (partnerRole != null) {
			partnerLinkElement.setAttribute("partnerRole", partnerRole);
		}
		this.currentElement.appendChild(partnerLinkElement);
	}

	
	
	@Override
	public void visit(BpelReceiveActivity receiveActivity) {
		
		Element receiveElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:receive");
		receiveElement.setAttribute("createInstance", receiveActivity.getCreateInstance());
		receiveElement.setAttribute("name", receiveActivity.getName());
		receiveElement.setAttribute("operation", receiveActivity.getOperation());
		receiveElement.setAttribute("partnerLink", receiveActivity.getPartnerLink());
		receiveElement.setAttribute("portType", receiveActivity.getPortType());
		receiveElement.setAttribute("variable", receiveActivity.getVariable());
		this.currentElement.appendChild(receiveElement);
	}

	@Override
	public void visit(BpelReplyActivity replyActivity) {
		
		Element replyElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:reply");
		replyElement.setAttribute("name", replyActivity.getName());
		replyElement.setAttribute("operation", replyActivity.getOperation());
		replyElement.setAttribute("partnerLink", replyActivity.getPartnerLink());
		replyElement.setAttribute("portType", replyActivity.getPortType());
		replyElement.setAttribute("variable", replyActivity.getVariable());
		this.currentElement.appendChild(replyElement);
	}

	@Override
	public void visit(BpelScopeActivity scopeActivity) {
		
		log.debug("Visiting BPEL scope '" + scopeActivity.getName() + "'");
		Element oldCurrentElement = this.currentElement;
		
		Element scopeElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:scope");
		this.unifyToDomMapping.put(scopeActivity, scopeElement);
		Element elementToInsert = this.elementsToInsert.get(scopeActivity);
		if (elementToInsert != null) {
			scopeElement.appendChild(elementToInsert);
		}
		scopeElement.setAttribute("name", scopeActivity.getName());
		
		Element partnerLinksElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:partnerLinks");
		this.currentElement = partnerLinksElement;
		for (BpelPartnerLink partnerLink : scopeActivity.getPartnerLinks()) {
			partnerLink.accept(this);
		}
		scopeElement.appendChild(partnerLinksElement);
		
		Element variablesElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variables");
		this.currentElement = variablesElement;
		for (Variable variable : scopeActivity.getScope().getVariables()) {
			BpelVariable bpelVariable = (BpelVariable) variable;
			bpelVariable.accept(this);
		}
		scopeElement.appendChild(variablesElement);
		
		// Traverse the BPEL scope activity, starting at its start event.
		// Each path will give rise to a new sequence element.
		Element sequenceElement = traversePathUntil(scopeActivity.getStartEvent().getControlOutputPort(), scopeActivity.getEndEvent());
		scopeElement.appendChild(sequenceElement);
		
		this.currentElement = oldCurrentElement;
		this.currentElement.appendChild(scopeElement);
	}
	
	@Override
	public void visit(BpelThrowActivity throwActivity) {
		
		Element throwElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:throw");
		this.unifyToDomMapping.put(throwActivity, throwElement);
		Element elementToInsert = this.elementsToInsert.get(throwActivity);
		if (elementToInsert != null) {
			throwElement.appendChild(elementToInsert);
		}
		throwElement.setAttribute("name", throwActivity.getName());
		throwElement.setAttribute("faultName", throwActivity.getErrorName());
		this.currentElement.appendChild(throwElement);
	}

	@Override
	public void visit(BpelToExpression to) {
		
		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.appendChild(this.document.importNode(to.getExpression(), true));
		this.currentElement.appendChild(toElement);
	}

	@Override
	public void visit(BpelToVariable to) {

		Element toElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:to");
		toElement.setAttribute("variable", to.getVariable());
		String part = to.getPart();
		if (part != null) {
			toElement.setAttribute("part", part);
		}
		String query = to.getQuery();
		org.w3c.dom.Node queryNode = to.getQueryNode();
		if (query != null) {
			Element queryElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:query");
			queryElement.setTextContent(query);
			// Serialize the to variable's namespace declarations:
			for (Map.Entry<String, String> entry : to.getNamespaceDeclarations().entrySet()) {
				queryElement.setAttribute(entry.getKey(), entry.getValue());
			}

			toElement.appendChild(queryElement);
		}
		if(queryNode!=null)
		{
			Element queryElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:query");
			queryElement.appendChild(this.document.importNode(to.getQueryNode(), true));
			// Serialize the to variable's namespace declarations:
			for (Map.Entry<String, String> entry : to.getNamespaceDeclarations().entrySet()) {
				queryElement.setAttribute(entry.getKey(), entry.getValue());
			}

			toElement.appendChild(queryElement);
		}
		this.currentElement.appendChild(toElement);
	}

	@Override
	public void visit(BpelVariableElement variable) {
		
		Element variableElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variable");
		variableElement.setAttribute("name", variable.getName());
		variableElement.setAttribute("element", variable.getElement());
		if (variable.getNsPrefix() != null) {
			variableElement.setAttribute("xmlns:" + variable.getNsPrefix(), variable.getNsUri());
		}
		this.currentElement.appendChild(variableElement);
	}

	@Override
	public void visit(BpelVariableMessageType variable) {

		Element variableElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variable");
		variableElement.setAttribute("name", variable.getName());
		variableElement.setAttribute("messageType", variable.getMessageType());
		this.currentElement.appendChild(variableElement);
	}

	@Override
	public void visit(BpelVariableType variable) {

		Element variableElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variable");
		variableElement.setAttribute("name", variable.getName());
		variableElement.setAttribute("type", variable.getType());
		if (variable.getNsPrefix() != null) {
			variableElement.setAttribute("xmlns:" + variable.getNsPrefix(), variable.getNsUri());
		}
		this.currentElement.appendChild(variableElement);
	}

	@Override
	public void visit(BpelXorJoin xorJoin) {
		
		Element repeatUntilElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:repeatUntil");
		
		Element childElement = traversePathUntil(xorJoin.getControlOutputPort(), xorJoin.getCorrespondingXorSplit());
		repeatUntilElement.appendChild(childElement);
		
		Element conditionElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:condition");
		BpelXorSplit xorSplit = (BpelXorSplit) xorJoin.getCorrespondingXorSplit();
		ControlOutputPort loopControlOutputPort = null;
		ControlOutputPort defaultControlOutputPort = xorSplit.getDefaultOutgoingTransition().getSource();
		for (ControlOutputPort controlOutputPort : xorSplit.getControlOutputPorts()) {
			if (controlOutputPort != defaultControlOutputPort) {
				if (loopControlOutputPort == null) {
					loopControlOutputPort = controlOutputPort;
				} else {
					throw new BpelSerializerException("The process seems to contain a loop that is not wellformed");
				}
			}
		}
		Expression condition = xorSplit.getGuard(loopControlOutputPort);
		if (condition instanceof XpathExpressionImpl) {
			XpathExpressionImpl xpathCondition = (XpathExpressionImpl) condition;
			conditionElement.setTextContent(xpathCondition.getExpression());
			for (Map.Entry<String, String> namespace : xpathCondition.getNamespaces().entrySet()) {
				conditionElement.setAttribute("xmlns:" + namespace.getKey(), namespace.getValue());
			}
		} else {
			throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
		}
		repeatUntilElement.appendChild(conditionElement);
		
		this.currentElement.appendChild(repeatUntilElement);
	}

	@Override
	public void visit(BpelXorSplit xorSplit) {
		
		Element ifElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "if");
		ifElement.setAttribute("name", xorSplit.getName().substring(0, xorSplit.getName().length() - 6)); // Trim '_Split' from the end of the name
		Iterator<ControlOutputPort> iterator = xorSplit.getControlOutputPorts().iterator();
		
		// Serialize the <if> branch:
		ControlOutputPort controlOutputPort = iterator.next();
		Element conditionElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:condition");
		Expression condition = xorSplit.getGuard(controlOutputPort);
		if (condition instanceof XpathExpressionImpl) {
			XpathExpressionImpl xpathCondition = (XpathExpressionImpl) condition;
			conditionElement.setTextContent(xpathCondition.getExpression());
			for (Map.Entry<String, String> namespace : xpathCondition.getNamespaces().entrySet()) {
				conditionElement.setAttribute("xmlns:" + namespace.getKey(), namespace.getValue());
			}
		} else {
			throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
		}
		ifElement.appendChild(conditionElement);
		Element childElement = traversePathUntil(controlOutputPort, xorSplit.getCorrespondingXorJoin());
		ifElement.appendChild(childElement);
		
		// Serialize the <elseif> and <else> branches:
		while (iterator.hasNext()) {
			controlOutputPort = iterator.next();
			condition = xorSplit.getGuard(controlOutputPort);
			if (condition != null) {
				Element elseifElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:elseif");
				conditionElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:condition");
				if (condition instanceof XpathExpressionImpl) {
					XpathExpressionImpl xpathCondition = (XpathExpressionImpl) condition;
					conditionElement.setTextContent(xpathCondition.getExpression());
					for (Map.Entry<String, String> namespace : xpathCondition.getNamespaces().entrySet()) {
						conditionElement.setAttribute("xmlns:" + namespace.getKey(), namespace.getValue());
					}
				} else {
					throw new NotImplementedException("The BPEL serializer does not (yet) support non-XPath expressions");
				}
				elseifElement.appendChild(conditionElement);
				childElement = traversePathUntil(controlOutputPort, xorSplit.getCorrespondingXorJoin());
				elseifElement.appendChild(childElement);
				ifElement.appendChild(elseifElement);
			} else {
				Element elseElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:else");
				childElement = traversePathUntil(controlOutputPort, xorSplit.getCorrespondingXorJoin());
				elseElement.appendChild(childElement);
				ifElement.appendChild(elseElement);
			}
		}
		
		this.currentElement.appendChild(ifElement);
	}
	
	
	@Override
	public void visit(BpelProcess process) {
		
		log.debug("Visiting BPEL process '" + process.getName() + "'");
		Element processElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:process");
		processElement.setAttribute("name", process.getName());
		processElement.setAttribute("targetNamespace", process.getTargetNamespace());
		if (process.getQueryLanguage() != null) processElement.setAttribute("queryLanguage", process.getQueryLanguage());
		if (process.getExpressionLanguage() != null) processElement.setAttribute("expressionLanguage", process.getExpressionLanguage());
		//Added for suppressJoinFailure and exitOnStandardFault
		
		if (process.getSuppressJoinFailure() != null) processElement.setAttribute("suppressJoinFailure", process.getSuppressJoinFailure());
		if (process.getExitOnStandardFault() != null) processElement.setAttribute("exitOnStandardFault", process.getExitOnStandardFault());
		
		// Serialize the BPEL process's namespace declarations:
		for (Map.Entry<String, String> entry : process.getNamespaceDeclarations().entrySet()) {
			processElement.setAttribute("xmlns:" + entry.getKey(), entry.getValue());
		}
		
		this.document.appendChild(processElement); // Append processElement to the document before processing the BpelProcess's children
		
		this.currentElement = processElement;
		for (BpelImport bpelImport : process.getImports()) {
			bpelImport.accept(this);
		}
		
		Element partnerLinksElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:partnerLinks");
		this.currentElement = partnerLinksElement;
		for (BpelPartnerLink partnerLink : process.getPartnerLinks()) {
			partnerLink.accept(this);
		}
		processElement.appendChild(partnerLinksElement);
		
		
		
		Element variablesElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:variables");
		this.currentElement = variablesElement;
		for (Variable variable : process.getScope().getVariables()) {
			BpelVariable bpelVariable = (BpelVariable) variable;
			bpelVariable.accept(this);
		}
		processElement.appendChild(variablesElement);
		
		/////*************Added correlation set 
			Element corrSetsElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:correlationSets");
			this.currentElement = corrSetsElement;
			for (BpelCorrelationSet corrSet : process.getCorrelationSets()) {
				corrSet.accept(this);
			}
			processElement.appendChild(corrSetsElement);
		//////////////////////////////////
			
		// Traverse the BPEL process, starting at its start event.
		// Each path will give rise to a new sequence element.
		Element sequenceElement = traversePathUntil(process.getStartEvent().getControlOutputPort(), process.getEndEvent());
		processElement.appendChild(sequenceElement);
	}

	//////////////////////////////////Added by Farida Sabry April 2013 	//////////////////////////////////
	@Override
	public void visit(BpelCompositeReceiveActivity receiveActivity) {
		// TODO Auto-generated method stub
	
		Element receiveElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:receive");
		if(receiveActivity.getCreateInstance()!=null)
			receiveElement.setAttribute("createInstance", receiveActivity.getCreateInstance());
		receiveElement.setAttribute("name", receiveActivity.getName());
		receiveElement.setAttribute("operation", receiveActivity.getOperation());
		receiveElement.setAttribute("partnerLink", receiveActivity.getPartnerLink());
		receiveElement.setAttribute("portType", receiveActivity.getPortType());
		receiveElement.setAttribute("variable", receiveActivity.getVariable());
		
		Element current = this.currentElement;
		Element correlationsElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:correlations");
		this.currentElement = correlationsElement;
		for (BpelCorrelation corr : receiveActivity.getCorrelations()) {
				corr.accept(this);
		}

		receiveElement.appendChild(correlationsElement);
		this.currentElement = current;
		this.currentElement.appendChild(receiveElement);
		
	}

	@Override
	public void visit(BpelCorrelationSet corrSet) {
		
		Element corrSetElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:correlationSet");
		corrSetElement.setAttribute("name", corrSet.getName());
		corrSetElement.setAttribute("properties", corrSet.getProperties());
		
		this.currentElement.appendChild(corrSetElement);
	}
	
	@Override
	public void visit(BpelCorrelation bpelCorrelation) {
		// TODO Auto-generated method stub
		
		Element corrElement = this.document.createElementNS(BPEL_NAMESPACE_URI, "bpel:correlation");
		corrElement.setAttribute("initiate", bpelCorrelation.getInitiate());
		corrElement.setAttribute("set", bpelCorrelation.getSet());
		if(bpelCorrelation.getDirection()!=null)
			corrElement.setAttribute("direction", bpelCorrelation.getDirection());
		
		this.currentElement.appendChild(corrElement);
		
	}

	@Override
	public void visit(BpelSequence sequence) {
		// TODO Auto-generated method stub
		
	}
	
	//////////////////////////////////	//////////////////////////////////	//////////////////////////////////
}

package org.unify_framework.execution_model.visitors;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSOutput;

import org.unify_framework.execution_model.EmActivityTransition;
import org.unify_framework.execution_model.EmAndJoinTransition;
import org.unify_framework.execution_model.EmAndSplitTransition;
import org.unify_framework.execution_model.EmEndPlace;
import org.unify_framework.execution_model.EmIntermediatePlace;
import org.unify_framework.execution_model.EmPetriNet;
import org.unify_framework.execution_model.EmPlace;
import org.unify_framework.execution_model.EmStartPlace;
import org.unify_framework.execution_model.EmTransition;

public class PnmlVisitor implements ElementVisitor {
	
	private static final String ERROR_MESSAGE = "An error has occured while attempting to generate the composite activity's PNML representation";
	private static final String XMLNS = "pnml.woped.org";
	private static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String XSD = "http://soft.vub.ac.be/~njonchee/artifacts/unify/xsd/pnml_wf.xsd";
	private static final String TYPE = "http://www.informatik.hu-berlin.de/top/pntd/ptNetb";
	
	private OutputStream os;
	private Document dom;
	private Element element;
	private List<Element> arcs;
	
	public PnmlVisitor(OutputStream os) {
		
		this.os = os;
		this.arcs = new LinkedList<Element>();
	}
	
	public void generatePnml(EmPetriNet petriNet) {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			this.dom = db.newDocument();
			this.element = this.dom.createElementNS(XMLNS, "pnml");
			this.element.setAttributeNS(XSI, "schemaLocation", XMLNS + " " + XSD);
			this.dom.appendChild(this.element);
			petriNet.accept(this);
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSSerializer writer = impl.createLSSerializer();
			if (writer.getDomConfig().canSetParameter("format-pretty-print", Boolean.TRUE)) {
				writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			}
			LSOutput output = impl.createLSOutput();
			output.setByteStream(this.os);
			writer.write(this.dom, output);
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmActivityTransition activityTransition) {
		
		visitTransition(activityTransition);
	}

	@Override
	public void visit(EmAndJoinTransition andJoinTransition) {
		
		visitTransition(andJoinTransition);
	}

	@Override
	public void visit(EmAndSplitTransition andSplitTransition) {
		
		visitTransition(andSplitTransition);
	}

	@Override
	public void visit(EmEndPlace endPlace) {
		
		visitPlace(endPlace);
	}

	@Override
	public void visit(EmIntermediatePlace intermediatePlace) {
		
		visitPlace(intermediatePlace);
	}

	@Override
	public void visit(EmStartPlace startPlace) {
		
		visitPlace(startPlace);
	}

	@Override
	public void visit(EmPetriNet petriNet) {
		
		Element netElement = this.dom.createElement("net");
		netElement.setAttribute("id", petriNet.getName());
		netElement.setAttribute("type", TYPE);
		this.element.appendChild(netElement);
		this.element = netElement;
		petriNet.getStartPlace().accept(this);
		for (EmIntermediatePlace place : petriNet.getIntermediatePlaces()) {
			place.accept(this);
		}
		petriNet.getEndPlace().accept(this);
		for (EmTransition transition : petriNet.getTransitions()) {
			transition.accept(this);
		}
		for (Element arcElement : this.arcs) {
			this.element.appendChild(arcElement);
		}
		this.element = (Element) netElement.getParentNode();
	}
	
	private void visitPlace(EmPlace place) {
		
		Element placeElement = this.dom.createElement("place");
		placeElement.setAttribute("id", place.getName());
		this.element.appendChild(placeElement);
		Element nameElement = this.dom.createElement("name");
		placeElement.appendChild(nameElement);
		Element textElement = this.dom.createElement("text");
		textElement.setTextContent(place.getName());
		nameElement.appendChild(textElement);
		
		Element graphicsElement = this.dom.createElement("graphics");
		placeElement.appendChild(graphicsElement);
		Element positionElement = this.dom.createElement("position");
		positionElement.setAttribute("x", "100");
		positionElement.setAttribute("y", "100");
		graphicsElement.appendChild(positionElement);
		Element dimensionElement = this.dom.createElement("dimension");
		dimensionElement.setAttribute("x", "40");
		dimensionElement.setAttribute("y", "40");
		graphicsElement.appendChild(dimensionElement);
		
		EmTransition transition = place.getOutgoingTransition();
		if (transition != null) {
			Element arcElement = this.dom.createElement("arc");
			arcElement.setAttribute("id", place.getName() + "_to_" + transition.getName());
			arcElement.setAttribute("source", place.getName());
			arcElement.setAttribute("target", transition.getName());
			this.arcs.add(arcElement);
		}
	}
	
	private void visitTransition(EmTransition transition) {
		
		Element transitionElement = this.dom.createElement("transition");
		transitionElement.setAttribute("id", transition.getName());
		this.element.appendChild(transitionElement);
		Element nameElement = this.dom.createElement("name");
		transitionElement.appendChild(nameElement);
		Element textElement = this.dom.createElement("text");
		textElement.setTextContent(transition.getName());
		nameElement.appendChild(textElement);
		
		Element graphicsElement = this.dom.createElement("graphics");
		transitionElement.appendChild(graphicsElement);
		Element positionElement = this.dom.createElement("position");
		positionElement.setAttribute("x", "100");
		positionElement.setAttribute("y", "100");
		graphicsElement.appendChild(positionElement);
		Element dimensionElement = this.dom.createElement("dimension");
		dimensionElement.setAttribute("x", "40");
		dimensionElement.setAttribute("y", "40");
		graphicsElement.appendChild(dimensionElement);
		
		for (EmPlace place : transition.getOutputPlaces()) {
			Element arcElement = this.dom.createElement("arc");
			arcElement.setAttribute("id", transition.getName() + "_to_" + place.getName());
			arcElement.setAttribute("source", transition.getName());
			arcElement.setAttribute("target", place.getName());
			this.arcs.add(arcElement);
		}
	}
}

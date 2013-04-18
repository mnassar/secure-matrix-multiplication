package org.unify_framework.abstract_syntax.visitors;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Join;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.Split;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;

public class GraphvizVisitor implements ElementVisitor {
	
	private static final String ERROR_MESSAGE = "An error has occured while attempting to generate the composite activity's Graphviz representation";
	
	private static final Log log = LogFactory.getLog(GraphvizVisitor.class);
	
	private Writer writer;
	
	public GraphvizVisitor(OutputStream os) {
		
		this.writer = new BufferedWriter(new OutputStreamWriter(os));
	}
	
//	private String getGuard(XorSplit split, Node destination) {
//		
//		String guard = null;
//		
//		for (ControlOutputPort controlOutputPort : split.getControlOutputPorts()) {
//			if (controlOutputPort.getOutgoingTransition().getDestinationNode() == destination) {
//				guard = split.getGuard(controlOutputPort);
//				break;
//			}
//		}
//		
//		if (guard == null) {
//			return "";
//		} else {
//			return guard;
//		}
//	}
	
	private String formatName(String name) {
		
		return name.replace('.', '_');
	}
	
	private String getColor(Node node) {
		
		switch (node.getExecutionStatus()) {
			case NOT_YET_EXECUTED:
				return "color=black, ";
			case EXECUTING:
				return "color=red, ";
			case EXECUTED:
				return "color=green, ";
		}
		return "";
		
//		if (node.isWoven()) {
//			return "color=red, ";
//		} else {
//			return "";
//		}
	}
	
//	private String getColor(Transition transition) {
//		
//		if (transition.isWoven()) {
//			return "color=red, ";
//		} else {
//			return "";
//		}
//	}
	
	@Override
	public void visit(AndJoin join) {
		
		try {
			log.debug("Visiting AND-join " + join.getName());
			this.writer.write(formatName(join.getQualifiedName()) + " [" + getColor(join) + "label=\"" + join.getName() + "\", shape=diamond]\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public void visit(AndSplit split) {
		
		try {
			log.debug("Visiting AND-split " + split.getName());
			this.writer.write(formatName(split.getQualifiedName()) + " [" + getColor(split) + "label=\"" + split.getName() + "\", shape=diamond]\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public void visit(AtomicActivity activity) {
		
		try {
			log.debug("Visiting atomic activity " + activity.getName());
			this.writer.write(formatName(activity.getQualifiedName()) + " [" + getColor(activity) + "label=\"" + activity.getName() + "\", shape=box]\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public void visit(Transition transition) {
		
		try {
			
			Node source = transition.getSource().getParent();
			Node destination = transition.getDestination().getParent();
			
			String sourceText;
			String destinationText;
			String propertiesText = "";
			
			// Generate the source text:
			if (source instanceof CompositeActivity) {
				CompositeActivity sourceCA = (CompositeActivity) source;
				sourceText = formatName(sourceCA.getEndEvent().getQualifiedName());
				propertiesText += "ltail=cluster_" + formatName(sourceCA.getQualifiedName());
			} else {
				sourceText = formatName(source.getQualifiedName());
			}
			
			// Generate the destination text:
			if (destination instanceof CompositeActivity) {
				CompositeActivity destinationCA = (CompositeActivity) destination;
				destinationText = formatName(destinationCA.getStartEvent().getQualifiedName());
				propertiesText += ((propertiesText == "") ? "" : ",") + "lhead=cluster_" + formatName(destinationCA.getQualifiedName());
			} else {
				destinationText = formatName(destination.getQualifiedName());
			}
			
			// Write the final line by combining the source text and the destination text:
			if (source instanceof Split && destination instanceof Join) { // The arrow should be reversed if it is the returning arrow in a cycle
				propertiesText += ((propertiesText == "") ? "" : ",") + "dir=back";
				this.writer.write(destinationText + " -> " + sourceText + " [" + propertiesText + "];\n");
			} else { // The arrow should not be reversed in all other cases
				this.writer.write(sourceText + " -> " + destinationText + " [" + propertiesText + "];\n");
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public void visit(CompositeActivity activity) {
		
		try {
			this.writer.write("subgraph cluster_" + formatName(activity.getQualifiedName()) + " {\n");
			this.writer.write(getColor(activity) + "label=" + activity.getName() + ";\n");
			this.writer.write(formatName(activity.getStartEvent().getQualifiedName()) + " [label=\"" + activity.getStartEvent().getName() + "\"]\n");
			this.writer.write(formatName(activity.getEndEvent().getQualifiedName()) + " [label=\"" + activity.getEndEvent().getName() + "\"]\n");
			for (Node child : activity.getChildren()) {
				child.accept(this);
			}
			for (Transition transition : activity.getTransitions()) {
				transition.accept(this);
			}
			this.writer.write("}\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public void visit(Composition composition) {
		
		// Do nothing
	}
	
	@Override
	public void visit(ControlInputPort controlPort) {
		
		// Do nothing
	}
	
	@Override
	public void visit(ControlOutputPort controlPort) {
		
		// Do nothing
	}
	
	@Override
	public void visit(EndEvent event) {
		
		// Do nothing
	}
	
	@Override
	public void visit(StartEvent event) {
		
		// Do nothing
	}
	
	@Override
	public void visit(XorJoin join) {
		
		try {
			log.debug("Visiting XOR-join " + join.getName());
			this.writer.write(formatName(join.getQualifiedName()) + " [" + getColor(join) + "label=\"" + join.getName() + "\", shape=diamond]\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public void visit(XorSplit split) {
		
		try {
			log.debug("Visiting XOR-split " + split.getName());
			this.writer.write(formatName(split.getQualifiedName()) + " [" + getColor(split) + "label=\"" + split.getName() + "\", shape=diamond]\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	public void generateGraphviz(CompositeActivity workflow) {
		
		try {
			this.writer.write("digraph " + workflow.getName() + " {\n");
			this.writer.write("compound=true;\n");
//			writer.write("rankdir=LR;\n");
			this.writer.write(formatName(workflow.getStartEvent().getQualifiedName()) + " [label=\"" + workflow.getStartEvent().getName() + "\"]\n");
			this.writer.write(formatName(workflow.getEndEvent().getQualifiedName()) + " [label=\"" + workflow.getEndEvent().getName() + "\"]\n");
			for (Activity childActivity : workflow.getActivities()) {
				childActivity.accept(this);
			}
			for (ControlNode controlNode : workflow.getControlNodes()) {
				controlNode.accept(this);
			}
			for (Transition transition : workflow.getTransitions()) {
				transition.accept(this);
			}
			this.writer.write("}\n");
			this.writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
}

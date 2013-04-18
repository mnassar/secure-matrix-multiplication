package org.unify_framework.concrete_syntax.visitors;

import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.impl.EndEventImpl;
import org.unify_framework.abstract_syntax.impl.StartEventImpl;
import org.unify_framework.abstract_syntax.impl.TransitionImpl;
import org.unify_framework.concrete_syntax.CsActivity;
import org.unify_framework.concrete_syntax.CsAndJoin;
import org.unify_framework.concrete_syntax.CsAndSplit;
import org.unify_framework.concrete_syntax.CsAtomicActivity;
import org.unify_framework.concrete_syntax.CsBasicTransition;
import org.unify_framework.concrete_syntax.CsCompositeActivity;
import org.unify_framework.concrete_syntax.CsEndEvent;
import org.unify_framework.concrete_syntax.CsStartEvent;
import org.unify_framework.concrete_syntax.CsTransition;

import java.util.Iterator;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParseVisitor implements ElementVisitor {
	
	private static final Log log = LogFactory.getLog(ParseVisitor.class);
	
	private Stack<CompositeActivityImpl> stack;
	private CompositeActivityImpl result;
	
	public ParseVisitor() {
		
		this.stack = new Stack<CompositeActivityImpl>();
	}
	
	private ControlInputPort findDestination(CompositeActivityImpl parent, String destinationName) {
		
		Node destinationNode = parent.getChild(destinationName);
		if (destinationNode == null) {
			EndEvent parentEndEvent = parent.getEndEvent();
			if (parentEndEvent.getName().equals(destinationName)) {
				return parentEndEvent.getControlInputPort();
			} else {
				throw new RuntimeException("Composite activity '" + parent.getName() + "' does not contain a node named '" + destinationName + "'");
			}
		} else {
			Iterator<ControlInputPort> iterator = destinationNode.getControlInputPorts().iterator();
			if (iterator.hasNext()) {
				ControlInputPort destination = iterator.next();
				if (iterator.hasNext()) {
					throw new RuntimeException("The destination node '" + destinationName + "' has more than one control input port");
				} else {
					return destination;
				}
			}
			throw new RuntimeException("The destination node '" + destinationName + "' has no control input ports");
		}
	}
	
	private ControlOutputPort findSource(CompositeActivityImpl parent, String sourceName) {
		
		Node sourceNode = parent.getChild(sourceName);
		if (sourceNode == null) {
			StartEvent parentStartEvent = parent.getStartEvent();
			if (parentStartEvent.getName().equals(sourceName)) {
				return parentStartEvent.getControlOutputPort();
			} else {
				throw new RuntimeException("Composite activity '" + parent.getName() + "' does not contain a node named '" + sourceName + "'");
			}
		} else {
			Iterator<ControlOutputPort> iterator = sourceNode.getControlOutputPorts().iterator();
			if (iterator.hasNext()) {
				ControlOutputPort source = iterator.next();
				if (iterator.hasNext()) {
					throw new RuntimeException("The source node '" + sourceName + "' has more than one control output port");
				} else {
					return source;
				}
			}
			throw new RuntimeException("The source node '" + sourceName + "' has no control output ports");
		}
	}
	
	public CompositeActivityImpl parse(CsCompositeActivity csCompositeActivity) {
		
		csCompositeActivity.accept(this);
		return this.result;
	}

	@Override
	public void visit(CsAndJoin csAndJoin) {

		log.debug("Visiting AND-join...");
		// Find the destination:
		CompositeActivityImpl parent = this.stack.peek();
		ControlInputPort destination = findDestination(parent, csAndJoin.getDestination());
		// Create a new AND-join:
		AndJoinImpl andJoin = new AndJoinImpl("AndJoin");
		// Connect the AND-join to the destination:
		parent.connect(andJoin.getControlOutputPort(), destination);
		// Find the sources and connect them to the AND-join:
		for (String sourceName : csAndJoin.getSources()) {
			parent.connect(findSource(parent, sourceName), andJoin.getNewControlInputPort());
		}
		// Add the AND-join to its parent:
		parent.addChild(andJoin);
	}

	@Override
	public void visit(CsAndSplit csAndSplit) {
		
		log.debug("Visiting AND-split...");
		// Find the source:
		CompositeActivityImpl parent = this.stack.peek();
		ControlOutputPort source = findSource(parent, csAndSplit.getSource());
		// Create a new AND-split:
		AndSplitImpl andSplit = new AndSplitImpl("AndSplit");
		//Connect the source to the AND-split:
		parent.connect(source, andSplit.getControlInputPort());
		// Find the destinations and connect the AND-split to them:
		for (String destinationName : csAndSplit.getDestinations()) {
			parent.connect(andSplit.getNewControlOutputPort(), findDestination(parent, destinationName));
		}
		// Add the AND-split to its parent:
		parent.addChild(andSplit);
	}

	@Override
	public void visit(CsAtomicActivity csAtomicActivity) {
		
		// Create a new atomic activity with the appropriate name:
		AtomicActivityImpl asAtomicActivity = new AtomicActivityImpl(csAtomicActivity.getName());
		// Add it to its parent:
		this.stack.peek().addChild(asAtomicActivity);
	}

	@Override
	public void visit(CsBasicTransition csBasicTransition) {
		
		// Find the source and destination:
		CompositeActivityImpl parent = this.stack.peek();
		ControlOutputPort source = findSource(parent, csBasicTransition.getSource());
		ControlInputPort destination = findDestination(parent, csBasicTransition.getDestination());
		// Create a new basic transition with the appropriate source and destination:
		TransitionImpl asTransition = new TransitionImpl(source, destination);
		// Add it to its parent:
		parent.addTransition(asTransition);
	}

	@Override
	public void visit(CsCompositeActivity csCompositeActivity) {
		
		// Create a new composite activity with the appropriate name:
		CompositeActivityImpl asCompositeActivity = new CompositeActivityImpl(csCompositeActivity.getName());
		// If it is not the root composite activity, add it to its parent:
		if (this.stack.size() > 0) {
			this.stack.peek().addChild(asCompositeActivity);
		}
		// Push the composite activity on the stack:
		this.stack.push(asCompositeActivity);
		// Create the start event:
		csCompositeActivity.getStartEvent().accept(this);
		// Create the children:
		for (CsActivity child : csCompositeActivity.getChildren()) {
			child.accept(this);
		}
		// Create the end event:
		csCompositeActivity.getEndEvent().accept(this);
		// Create the transitions:
		for (CsTransition transition : csCompositeActivity.getTransitions()) {
			transition.accept(this);
		}
		// Pop the composite activity from the stack:
		this.result = this.stack.pop();
	}

	@Override
	public void visit(CsEndEvent csEndEvent) {
		
		// Create a new end event with the appropriate name:
		EndEventImpl asEndEvent = new EndEventImpl(csEndEvent.getName());
		// Add it to its parent:
		this.stack.peek().setEndEvent(asEndEvent);
	}

	@Override
	public void visit(CsStartEvent csStartEvent) {
		
		// Create a new start event with the appropriate name:
		StartEventImpl asStartEvent = new StartEventImpl(csStartEvent.getName());
		// Add it to its parent:
		this.stack.peek().setStartEvent(asStartEvent);
	}
}

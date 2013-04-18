package org.unify_framework.abstract_syntax.impl;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.abstract_syntax.visitors.PrintVisitor;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Reference implementation of the Unify {@link CompositeActivity} interface.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: CompositeActivityImpl.java 18723 2011-12-04 09:52:01Z njonchee $
 */
public class CompositeActivityImpl extends ActivityImpl implements CompositeActivity {
	
	private static final Log log = LogFactory.getLog(CompositeActivityImpl.class);

	private Map<String, Node> childrenMap;
	private StartEvent startEvent;
	private EndEvent endEvent;
	private List<Activity> activities;
	private List<ControlNode> controlNodes;
	private List<Transition> transitions;
	
	/**
	 * Creates a new composite activity.
	 * 
	 * @param name The composite activity's name
	 */
	public CompositeActivityImpl(String name) {
		
		this(name, false);
	}
	
	public CompositeActivityImpl(String name, boolean woven) {
		
		super(name, woven);
		
		this.childrenMap = new HashMap<String, Node>();
		this.activities = new LinkedList<Activity>();
		this.controlNodes = new LinkedList<ControlNode>();
		this.transitions = new LinkedList<Transition>();
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public void addActivity(Activity activity) {
		
		activity.setParent(this);
		this.activities.add(activity);
		this.childrenMap.put(activity.getName(), activity);
	}

	@Override
	public void addChild(Node child) {
		
		if (child instanceof StartEvent) {
			setStartEvent((StartEvent) child);
		} else if (child instanceof EndEvent) {
			setEndEvent((EndEvent) child);
		} else if (child instanceof Activity) {
			addActivity((Activity) child);
		} else if (child instanceof ControlNode) {
			addControlNode((ControlNode) child);
		} else {
			throw new RuntimeException("Unknown child type " + child.getClass().getName() + "!");
		}
	}

	@Override
	public void addControlNode(ControlNode controlNode) {
		
		controlNode.setParent(this);
		this.controlNodes.add(controlNode);
		this.childrenMap.put(controlNode.getName(), controlNode);
	}
	
	public void addTransition(Transition transition) {
		
//		transition.setParent(this);
		this.transitions.add(transition);
	}

	@Override
	public Transition connect(ControlOutputPort source, ControlInputPort destination) {
		
		TransitionImpl transition = new TransitionImpl(source, destination);
		addTransition(transition);
		return transition;
	}

	@Override
	public Transition connect(Node source, Node destination) {
		
		// TODO Make sure this method supports splits/joins
		ControlOutputPort sourcePort;
		ControlInputPort destinationPort;
		if (source instanceof StartEventImpl) {
			sourcePort = ((StartEventImpl) source).getControlOutputPort();
		} else {
			sourcePort = ((ActivityImpl) source).getControlOutputPort();
		}
		if (destination instanceof EndEventImpl) {
			destinationPort = ((EndEventImpl) destination).getControlInputPort();
		} else {
			destinationPort = ((ActivityImpl) destination).getControlInputPort();
		}
		return connect(sourcePort, destinationPort);
	}
	
	/**
	 * Attempts to copy the composite activity into its argument.
	 * 
	 * @param copy The composite activity into which the composite activity
	 *		should be copied.
	 */
	public void copy(CompositeActivity c) {
		
		if (c instanceof CompositeActivityImpl) {

			log.debug("Copying composite activity '" + getName() + "'");
			CompositeActivityImpl copy = (CompositeActivityImpl) c;
			Map<ControlPort, ControlPort> mapping = new HashMap<ControlPort, ControlPort>();
			mapping.put(getControlInputPort(), copy.getControlInputPort());
			mapping.put(getControlOutputPort(), copy.getControlOutputPort());

			copy.setStartEvent((StartEvent) getStartEvent().getCopy(mapping));
			copy.setEndEvent((EndEvent) getEndEvent().getCopy(mapping));
			
			for (Activity activity : this.activities) {
				Activity activityCopy = (Activity) activity.getCopy(mapping);
				copy.addActivity(activityCopy);
			}
			
			Map<ControlNode, ControlNode> controlNodeMapping = new HashMap<ControlNode, ControlNode>(); // Build a mapping of control nodes in order to be able to copy corresponding join links
			for (ControlNode controlNode : this.controlNodes) {
				ControlNode controlNodeCopy = (ControlNode) controlNode.getCopy(mapping);
				copy.addControlNode(controlNodeCopy);
				controlNodeMapping.put(controlNode, controlNodeCopy);
				log.debug("Adding ('" + controlNode.getQualifiedName() + "', '" + controlNodeCopy.getQualifiedName() + "') to mapping");
			}
			// Copy any corresponding join links:
			for (ControlNode controlNode : this.controlNodes) {
				if (controlNode instanceof XorSplit) {
					log.debug("Setting corresponding join links");
					XorSplit split = (XorSplit) controlNode;
					XorJoin correspondingJoin = split.getCorrespondingXorJoin();
					if (correspondingJoin != null) {
						XorSplit splitCopy = (XorSplit) controlNodeMapping.get(split);
						XorJoin joinCopy = (XorJoin) controlNodeMapping.get(correspondingJoin);
						splitCopy.setCorrespondingXorJoin(joinCopy);
						joinCopy.setCorrespondingXorSplit(splitCopy);
					}
				} else if (controlNode instanceof AndSplit) {
					log.debug("Setting corresponding join links");
					AndSplit split = (AndSplit) controlNode;
					AndJoin correspondingJoin = split.getCorrespondingAndJoin();
					if (correspondingJoin != null) {
						AndSplit splitCopy = (AndSplit) controlNodeMapping.get(split);
						AndJoin joinCopy = (AndJoin) controlNodeMapping.get(correspondingJoin);
						splitCopy.setCorrespondingAndJoin(joinCopy);
						joinCopy.setCorrespondingAndSplit(splitCopy);
					}
				}
			}
			// Copy any guards:
			for (ControlNode controlNode : this.controlNodes) {
				if (controlNode instanceof XorSplit) {
					XorSplitImpl split = (XorSplitImpl) controlNode;
					XorSplitImpl splitCopy = (XorSplitImpl) controlNodeMapping.get(split);
					for (Map.Entry<ControlOutputPort, Expression> entry : split.getGuards().entrySet()) {
						splitCopy.addGuard((ControlOutputPort) mapping.get(entry.getKey()), entry.getValue());
					}
				}
			}
			
			for (Transition transition : this.transitions) {
				Transition transitionCopy = transition.getCopy(mapping);
				copy.addTransition(transitionCopy);
			}
			
		} else {
			
			throw new RuntimeException("NYI");
		}
	}

	@Override
	public void generateGraphviz(OutputStream os) {
		
		log.debug("Generating Graphviz file...");
		try {
			GraphvizVisitor gv = new GraphvizVisitor(os);
			gv.generateGraphviz(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Activity> getActivities() {
		
		return this.activities;
	}
	
	public Node getChild(String name) {
		
		return this.childrenMap.get(name);
	}

	@Override
	public List<Node> getChildren() {
		
		List<Node> children = new LinkedList<Node>();
		children.add(this.startEvent);
		children.add(this.endEvent);
		for (Activity activity : this.activities) {
			children.add(activity);
		}
		for (ControlNode controlNode : this.controlNodes) {
			children.add(controlNode);
		}
		return children;
	}

	@Override
	public List<ControlNode> getControlNodes() {
		
		return this.controlNodes;
	}
	
	@Override
	public ActivityImpl getCopy(Map<ControlPort, ControlPort> mapping) {
		
		log.debug("Copying composite activity '" + getName() + "' (while creating mapping)");
		CompositeActivityImpl copy = new CompositeActivityImpl(getName(), getWoven());
		mapping.put(getControlInputPort(), copy.getControlInputPort());
		mapping.put(getControlOutputPort(), copy.getControlOutputPort());
		mapping.put(getStartEvent().getControlOutputPort(), copy.getStartEvent().getControlOutputPort());
		mapping.put(getEndEvent().getControlInputPort(), copy.getEndEvent().getControlInputPort());
		for (Activity activity : this.activities) {
			Activity activityCopy = (Activity) activity.getCopy(mapping);
			copy.addActivity(activityCopy);
		}
		for (ControlNode controlNode : this.controlNodes) {
			ControlNode controlNodeCopy = (ControlNode) controlNode.getCopy(mapping);
			copy.addControlNode(controlNodeCopy);
		}
		for (Transition transition : this.transitions) {
			Transition transitionCopy = transition.getCopy(mapping);
			copy.addTransition(transitionCopy);
		}
		return copy;
	}

	@Override
	public EndEvent getEndEvent() {
		
		if (this.endEvent == null) {
			setEndEvent(new EndEventImpl("End"));
		}
		return this.endEvent;
	}
	
	public Transition getIncomingTransition(NodeImpl node) {
		
		for (Transition transition : this.transitions) {
			ControlInputPort destination = transition.getDestination();
			if (destination.getParent() == node) {
				return transition;
			}
		}
		throw new RuntimeException("This composite activity does not contain an incoming transition for the given element");
	}
	
	public Transition getOutgoingTransition(NodeImpl node) {
		
		for (Transition transition : this.transitions) {
			ControlOutputPort source = transition.getSource();
			if (source.getParent() == node) {
				return transition;
			}
		}
		throw new RuntimeException("This composite activity does not contain an outgoing transition for the given element");
	}

	@Override
	public StartEvent getStartEvent() {
		
		if (this.startEvent == null) {
			setStartEvent(new StartEventImpl("Start"));
		}
		return this.startEvent;
	}

	@Override
	public List<Transition> getTransitions() {
		
		return this.transitions;
	}
	
	public void print() {
		
		try {
			PrintVisitor tev = new PrintVisitor(System.out);
			tev.print(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeActivity(Activity activity) {
		
		// This method is the inverse of addActivity(Activity)
		this.childrenMap.remove(activity.getName());
		this.activities.remove(activity);
		activity.setParent(null);
	}
	
	@Override
	public void removeTransition(Transition transition) {

		// This method is the inverse of addTransition(Transition)
		this.transitions.remove(transition);
	}
	
	@Override
	public void replaceActivity(Activity existingActivity, Activity newActivity) {
		
		// Modify the incoming and outgoing transitions:
		Transition incomingTransition = existingActivity.getIncomingTransition();
		Transition outgoingTransition = existingActivity.getOutgoingTransition();
		incomingTransition.replaceDestination(existingActivity.getControlInputPort(), newActivity.getControlInputPort());
		outgoingTransition.replaceSource(existingActivity.getControlOutputPort(), newActivity.getControlOutputPort());
		
		// Remove the old activity:
		existingActivity.setParent(null);
		this.activities.remove(existingActivity);
		this.childrenMap.remove(existingActivity.getName());
		
		// Add the new activity, /after/ the old activity has been removed:
		addChild(newActivity);
	}
	
	public void setEndEvent(EndEvent endEvent) {
		
		if (this.endEvent != null) {
			this.childrenMap.remove(this.endEvent.getName());
		}
		endEvent.setParent(this);
		this.endEvent = endEvent;
		this.childrenMap.put(endEvent.getName(), endEvent);
	}
	
	public void setStartEvent(StartEvent startEvent) {
		
		if (this.startEvent != null) {
			this.childrenMap.remove(this.startEvent.getName());
		}
		startEvent.setParent(this);
		this.startEvent = startEvent;
		this.childrenMap.put(startEvent.getName(), startEvent);
	}

	@Override
	public void replaceString(String target, String replacement) {
		
		// Do nothing
	}
}

package org.unify_framework.abstract_syntax.visitors;

import java.util.HashMap;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.execution_model.EmAndJoinTransition;
import org.unify_framework.execution_model.EmAndSplitTransition;
import org.unify_framework.execution_model.EmEndActivityTransition;
import org.unify_framework.execution_model.EmEndPlace;
import org.unify_framework.execution_model.EmIntermediatePlace;
import org.unify_framework.execution_model.EmPetriNet;
import org.unify_framework.execution_model.EmPlace;
import org.unify_framework.execution_model.EmStartActivityTransition;
import org.unify_framework.execution_model.EmStartPlace;

public class PetriNetVisitor implements ElementVisitor {
	
	private EmPetriNet petriNet;
	private HashMap<Transition, HashMap<Node, EmPlace>> transitionToInputPlaceMapping;
	private HashMap<Transition, HashMap<Node, EmPlace>> transitionToOutputPlaceMapping;
	private int placeCounter;
	private int transitionCounter;
	
	public PetriNetVisitor() {

		this.transitionToInputPlaceMapping = new HashMap<Transition, HashMap<Node, EmPlace>>();
		this.transitionToOutputPlaceMapping = new HashMap<Transition, HashMap<Node, EmPlace>>();
		this.placeCounter = 1;
	}
	
	private EmPlace getTransitionToInputPlaceMapping(Transition transition, Node node) {
		
		return this.transitionToInputPlaceMapping.get(transition).get(node);
	}
	
	private EmPlace getTransitionToOutputPlaceMapping(Transition transition, Node node) {
		
		return this.transitionToOutputPlaceMapping.get(transition).get(node);
	}
	
	private void addTransitionToInputPlaceMapping(Transition transition, Node element, EmPlace place) {
		
		HashMap<Node, EmPlace> map = this.transitionToInputPlaceMapping.get(transition);
		if (map == null) {
			map = new HashMap<Node, EmPlace>();
			this.transitionToInputPlaceMapping.put(transition, map);
		}
		map.put(element, place);
	}
	
	private void addTransitionToOutputPlaceMapping(Transition transition, Node element, EmPlace place) {
		
		HashMap<Node, EmPlace> map = this.transitionToOutputPlaceMapping.get(transition);
		if (map == null) {
			map = new HashMap<Node, EmPlace>();
			this.transitionToOutputPlaceMapping.put(transition, map);
		}
		map.put(element, place);
	}
	
	private int incrementPlaceCounter() {
		
		return this.placeCounter++;
	}
	
	private int incrementTransitionCounter() {
		
		return this.transitionCounter++;
	}
	
	private String getPlaceName() {
		
		return "p" + Integer.toString(incrementPlaceCounter());
	}
	
	private String getTransitionName() {
		
		return "t" + Integer.toString(incrementTransitionCounter());
	}

	@Override
	public void visit(AndJoin join) {

		EmAndJoinTransition transition = new EmAndJoinTransition(getTransitionName(), join);
		this.petriNet.addTransition(transition);
		EmPlace outputPlace = getTransitionToOutputPlaceMapping(join.getOutgoingTransition(), join);
		transition.addOutputPlace(outputPlace);
		for (ControlInputPort controlIn : join.getControlInputPorts()) {
			EmPlace inputPlace = getTransitionToInputPlaceMapping(controlIn.getIncomingTransition(), join);
			transition.addInputPlace(inputPlace);
		}
	}
	
//	public void visit(AsAndJoin join) {
//		
//		AsDestination destination = join.getDestination();
//		if (destination instanceof AsEndEvent) {
//			addTransitionToOutputPlaceMapping(join, destination, petriNet.getEndPlace());
//		} else {
//			EmAndJoinTransition transition = new EmAndJoinTransition(getTransitionName(), join);
//			petriNet.addTransition(transition);
//			for (AsControlOutputPort sourcePort : join.getSources()) {
//				AsConnectable source = sourcePort.getParent();
//				EmIntermediatePlace inputPlace = new EmIntermediatePlace(getPlaceName(), join);
//				petriNet.addIntermediatePlace(inputPlace);
//				transition.addInputPlace(inputPlace);
//				addTransitionToOutputPlaceMapping(join, source, inputPlace);
//			}
//			EmIntermediatePlace outputPlace = new EmIntermediatePlace(getPlaceName(), join);
//			petriNet.addIntermediatePlace(outputPlace);
//			transition.addOutputPlace(outputPlace);
//			addTransitionToInputPlaceMapping(join, destination, outputPlace);
//		}
//	}

	@Override
	public void visit(AndSplit split) {

		EmAndSplitTransition transition = new EmAndSplitTransition(getTransitionName(), split);
		this.petriNet.addTransition(transition);
		EmPlace inputPlace = getTransitionToInputPlaceMapping(split.getIncomingTransition(), split);
		transition.addInputPlace(inputPlace);
		for (ControlOutputPort controlOut : split.getControlOutputPorts()) {
			EmPlace outputPlace = getTransitionToOutputPlaceMapping(controlOut.getOutgoingTransition(), split);
			transition.addOutputPlace(outputPlace);
		}
	}
	
//	public void visit(AsAndSplit split) {
//		
//		AsSource source = split.getSource();
//		if (source instanceof AsStartEvent) {
//			addTransitionToInputPlaceMapping(split, source, petriNet.getStartPlace());
//		} else {
//			EmAndSplitTransition transition = new EmAndSplitTransition(getTransitionName(), split);
//			petriNet.addTransition(transition);
//			EmIntermediatePlace inputPlace = new EmIntermediatePlace(getPlaceName(), split);
//			petriNet.addIntermediatePlace(inputPlace);
//			transition.addInputPlace(inputPlace);
//			addTransitionToOutputPlaceMapping(split, source, inputPlace);
//			for (AsControlInputPort destinationPort : split.getDestinations()) {
//				AsConnectable destination = destinationPort.getParent();
//				EmIntermediatePlace outputPlace = new EmIntermediatePlace(getPlaceName(), split);
//				petriNet.addIntermediatePlace(outputPlace);
//				transition.addOutputPlace(outputPlace);
//				addTransitionToInputPlaceMapping(split, destination, outputPlace);
//			}
//		}
//	}
	
//	public void visit(AsAtomicActivity activity) {
//		
//		System.out.println("Visiting activity " + activity.getName());
//		EmActivityTransition transition = new EmActivityTransition(activity.getName(), activity);
//		EmPlace inputPlace = getTransitionToInputPlaceMapping(activity.getIncomingTransition(), activity);
//		EmPlace outputPlace = getTransitionToOutputPlaceMapping(activity.getOutgoingTransition(), activity);
//		transition.addInputPlace(inputPlace);
//		transition.addOutputPlace(outputPlace);
//		petriNet.addTransition(transition);
//	}

	@Override
	public void visit(AtomicActivity activity) {
		
//		System.out.println("Visiting activity " + activity.getName());
		EmStartActivityTransition startTransition = new EmStartActivityTransition(activity.getName() + "_Start", activity);
		EmEndActivityTransition endTransition = new EmEndActivityTransition(activity.getName() + "_End", activity);
		EmPlace inputPlace = getTransitionToInputPlaceMapping(activity.getIncomingTransition(), activity);
		EmIntermediatePlace intermediatePlace = new EmIntermediatePlace(getPlaceName(), null);
		EmPlace outputPlace = getTransitionToOutputPlaceMapping(activity.getOutgoingTransition(), activity);
		startTransition.addInputPlace(inputPlace);
		startTransition.addOutputPlace(intermediatePlace);
		endTransition.addInputPlace(intermediatePlace);
		endTransition.addOutputPlace(outputPlace);
		this.petriNet.addTransition(startTransition);
		this.petriNet.addIntermediatePlace(intermediatePlace);
		this.petriNet.addTransition(endTransition);
	}

	@Override
	public void visit(Transition transition) {
		
		Node source = transition.getSource().getParent();
		Node destination = transition.getDestination().getParent();
		if (source instanceof StartEvent) {
			addTransitionToInputPlaceMapping(transition, destination, this.petriNet.getStartPlace());
		} else if (destination instanceof EndEvent) {
			addTransitionToOutputPlaceMapping(transition, source, this.petriNet.getEndPlace());
		} else {
			EmIntermediatePlace place = new EmIntermediatePlace(getPlaceName(), transition);
			this.petriNet.addIntermediatePlace(place);
			addTransitionToOutputPlaceMapping(transition, source, place);
			addTransitionToInputPlaceMapping(transition, destination, place);
		}
	}

	@Override
	public void visit(CompositeActivity activity) {
		
		// TODO Handle composite pattern
//		System.out.println("Visiting start event");
		activity.getStartEvent().accept(this);
//		System.out.println("Visiting end event");
		activity.getEndEvent().accept(this);
//		System.out.println("Visiting transitions");
		for (Transition transition : activity.getTransitions()) {
			transition.accept(this);
		}
//		System.out.println("Visiting activities");
		for (Activity childActivity : activity.getActivities()) {
			childActivity.accept(this);
		}
		for (ControlNode controlNode : activity.getControlNodes()) {
			controlNode.accept(this);
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
	public void visit(EndEvent endEvent) {
		
		EmEndPlace endPlace = new EmEndPlace(endEvent.getName());
		this.petriNet.setEndPlace(endPlace);
	}

	@Override
	public void visit(StartEvent startEvent) {
		
		EmStartPlace startPlace = new EmStartPlace(startEvent.getName());
		this.petriNet.setStartPlace(startPlace);
	}

	@Override
	public void visit(XorJoin join) {
		
		// TODO Handle XOR-join
	}
	
	@Override
	public void visit(XorSplit split) {
		
		// TODO Handle XOR-split
	}
	
	public EmPetriNet generatePetriNet(CompositeActivity compositeActivity) {
		
		this.petriNet = new EmPetriNet(compositeActivity.getName(), compositeActivity);
		compositeActivity.accept(this);
		return this.petriNet;
	}
}

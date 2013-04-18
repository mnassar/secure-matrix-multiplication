package org.unify_framework.automaton.translator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;
import org.unify_framework.automaton.Automaton;
import org.unify_framework.automaton.SubAutomatonConstructor;
import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;


public class Translator implements ElementVisitor {
	
	
	Automaton automaton = new Automaton();
	
	Stack<Automaton> automatonStack = new Stack<Automaton>();
	
	Map<Node, Automaton.State> incomingMap = new HashMap<Node, Automaton.State>();
	Map<Node, Automaton.State> outgoingMap  = new HashMap<Node, Automaton.State>();
	

	int stateNum = 1;
	
	public Automaton translate(CompositeActivity composite){
		visit(composite);
		return this.automaton.reduce().toNFA(false).toDFA();
	}

	@Override
	public void visit(AndJoin andJoin) {
		
		
		Set<State> endingStates = new HashSet<State>();
		for (Node node : andJoin.getSourceNodes()) {
			State state = this.incomingMap.get(node);
			endingStates.add(state);
		}
		
		
		//get corresponding join
		AndSplit corresponding = andJoin.getCorrespondingAndSplit();
		//get starting set
		
		List<Node> toStates = corresponding.getDestinationNodes();
		Set<State> startingStates = new HashSet<State>();
		for (Node node : toStates) {
			State state2 = this.outgoingMap.get(node);
			startingStates.add(state2);
		}
		
		//for each start, construct a new automaton, ending on one of the ending states
		
		//make branches
		Queue<Automaton> aList = new LinkedList<Automaton>();
		for (State state : startingStates) {
			Automaton aux= new SubAutomatonConstructor(this.automaton, state, endingStates).execute();
			aList.add(aux);
		}
		
		//accumulate shuffled branches
		Automaton shuffle = aList.poll();
		for (Automaton currentAutomaton : aList) {
			shuffle = shuffle.interleave(currentAutomaton);
		}
		
		//copy to current automaton
		startingStates.clear();
		endingStates.clear();
		Map<State,State> oldNew = new HashMap<State, State>();
		for (State state : shuffle.getStates()) {
			State newState = this.automaton.new State(state.label, false);
			if(state.isFinal) endingStates.add(newState);
			if(shuffle.getStarts().contains(state)) startingStates.add(newState);
			oldNew.put(state, newState);
		}
		
		for (Automaton.Transition trans : shuffle.getTransitions()) {
			this.automaton.createTransition(oldNew.get(trans.from), oldNew.get(trans.to), trans.symbolSet);
		}
		
		
		//reconnect
		
		State state = this.incomingMap.get(corresponding.getSourceNode());
		
		for (State state2 : startingStates) {
			addTransition(state, state2, null);
		}
		
		State ending = this.outgoingMap.get(andJoin.getDestinationNode());
		for(State state1 : endingStates){
			addTransition(state1, ending, null);
		}
		
	}

	
	@Override
	public void visit(AndSplit andSplit) {
		
		// annotate each state with split
		
	}

	@Override
	public void visit(AtomicActivity atomicActivity) {
		//pair incomming with outgoing
		
		State state1 = this.outgoingMap.get(atomicActivity);
		State state2 = this.incomingMap.get(atomicActivity);
		
		addTransition(state1, state2, atomicActivity.getName());
		
		
	
	}
	
	private void addTransition(State state1, State state2, String name){
		addTransition(state1, state2, name, this.automaton);
	}
	
	private void addTransition(State state1, State state2, String name, Automaton a) {
		
		if (name == null) {
			a.createTransition(state1, state2);
		} else {
			a.createTransition(state1, state2, name);
		}
	}
	
	@Override
	public void visit(CompositeActivity compositeActivity) {
		
		//push new Automata
		
		this.automatonStack.push(this.automaton);
		this.automaton = new Automaton();
		
		List<Transition> transitions = compositeActivity.getTransitions();
		
		for (Transition transition : transitions) {
			transition.accept(this);
		}
		
		List<Node> nodes = compositeActivity.getChildren();
		for (Node node : nodes) {
			node.accept(this);
		}
		
				
//		//pop and reconnect
		Automaton automatonNew = this.automatonStack.pop();
		State start = null;
		State end = null;
		
		State state1 = this.outgoingMap.get(compositeActivity);
		State state2 = this.incomingMap.get(compositeActivity);
		
		Map<String, State> namedStates = new HashMap<String, State>();
		for (State oldState : this.automaton.getStates()) {
			State newState = automatonNew.new State(oldState.label,oldState.isFinal && (state2 ==null));
			namedStates.put(oldState.label, newState); 
			if(oldState.isFinal) end = newState;
			if(this.automaton.getStarts().contains(oldState)){ 
				start = newState;
				if(state1 == null){
					automatonNew.addStart(newState);
				}
			}
		}
		
		for (Automaton.Transition trans : this.automaton.getTransitions()) {
			automatonNew.createTransition(namedStates.get(trans.from.label), namedStates.get(trans.to.label), trans.symbolSet); 
			
		}
		
		
		
		if(state1 != null)		
		addTransition(state1, start, compositeActivity.getName()+"/In",automatonNew);
		
		if(state2 !=null)
			addTransition(end, state2, compositeActivity.getName()+"/Out", automatonNew);
		
		this.automaton = automatonNew;
		
	}

	@Override
	public void visit(Composition composition) {
		
		// Do nothing
	}

	@Override
	public void visit(ControlInputPort controlInputPort) {
		
		// Do nothing
	}

	@Override
	public void visit(ControlOutputPort controlOutputPort) {
		
		// Do nothing
	}

	@Override
	public void visit(EndEvent endEvent) {
		
		// Do nothing
	}

	@Override
	public void visit(StartEvent startEvent) {
		
		// Do nothing
	}

	@Override
	public void visit(Transition transition) {
		//create a state
		Automaton.State currState = this.automaton.new State(""+this.stateNum++,transition.getDestinationNode() instanceof EndEvent);
		if(transition.getSourceNode() instanceof StartEvent){
			this.automaton.addStart(currState);
		}
			
		//notify incoming and outgoing endings
		this.incomingMap.put(transition.getSourceNode(), currState);
		this.outgoingMap.put(transition.getDestinationNode(), currState);
		
	}

	@Override
	public void visit(XorJoin xorJoin) {
		
		State state2 = this.outgoingMap.get(xorJoin.getDestinationNode());
		
		List<Node> fromNodes = xorJoin.getSourceNodes();
		for (Node node : fromNodes) {
			State state1 = this.incomingMap.get(node);
			addTransition(state1, state2, null);
		}
		
	}

	@Override
	public void visit(XorSplit xorSplit) {
		
		// get incomming state
		
		State state1 = this.incomingMap.get(xorSplit.getSourceNode());
		
		//add edges for each outgoing state
		List<Node> toStates = xorSplit.getDestinationNodes();
		for (Node node : toStates) {
			State state2 = this.outgoingMap.get(node);
			addTransition(state1, state2, null);
		}
		
	}


	
}

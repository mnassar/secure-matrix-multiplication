package org.unify_framework.automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

public class SubAutomatonConstructor extends AutomatonConstructor {
	
	Set<State> startingStates;
	Set<State> endingStates;
	
	public SubAutomatonConstructor(Automaton src, State starting, Set<State>ending){
		super(src);
		this.startingStates = new HashSet<State>();
		this.startingStates.add(starting);
		this.endingStates = ending;
	}
	
	public SubAutomatonConstructor(Automaton src, Set<State> starting, Set<State>ending){
		super(src);
		this.startingStates = starting;
		this.endingStates = ending;
	}
	

	protected final Queue<State> q = new LinkedList<State>();
	protected final Map<State,State> smap = new HashMap<State,State>();

	protected State newState(State s) {
		State ns = this.dst.new State(s.label,this.endingStates.contains(s));
		this.smap.put(s,ns);
		if(!this.endingStates.contains(s))
			this.q.add(s);
		return ns;
	}

	@Override
	protected void construct() {
		for(State s: this.startingStates)
			this.dst.addStart(newState(s));
		
		while(!this.q.isEmpty()) {
			State s = this.q.poll();
			State ns = this.smap.get(s);
			for(Transition t: s.getTransitionsFrom()) {
				State nt = this.smap.get(t.to);
				if(nt == null)
					nt = newState(t.to);
				this.dst.createTransition(ns,nt,t.symbolSet);
			}
		}
	}			

}

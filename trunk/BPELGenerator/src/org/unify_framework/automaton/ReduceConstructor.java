package org.unify_framework.automaton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

/**
 * Reduce an automaton by omitting any unreachable states
 */
public class ReduceConstructor extends AutomatonConstructor {

	public ReduceConstructor(Automaton src) {
		super(src);
	}

	protected final Queue<State> q = new LinkedList<State>();
	protected final Map<State,State> smap = new HashMap<State,State>();

	protected State newState(State s) {
		State ns = this.dst.new State(s.label,s.isFinal);
		this.smap.put(s,ns);
		this.q.add(s);
		return ns;
	}

	@Override
	protected void construct() {
		for(State s: this.src.getStarts())
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

package org.unify_framework.automaton;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

public class NFAConstructor extends AutomatonConstructor {
	
	protected final boolean preemptive;

	public NFAConstructor(Automaton src, boolean preemptive) {
		super(src);
		this.preemptive = preemptive;
	}
	
	protected final Map<State,State> smap = new HashMap<State,State>();
	protected final Map<State,Set<State>> closures = new HashMap<State,Set<State>>();
	
	private boolean isFinal(State s) {
		if(this.preemptive) {
			for(State t: this.closures.get(s))
				if(t.isFinal)
					return true;
			return false;
		} else
			return s.isFinal;		
	}

	@Override
	protected void construct() {
		// Calculate closures
		for(State s: this.src.getStates()) {
			final Queue<State> q = new LinkedList<State>();
			final Set<State> clo = new LinkedHashSet<State>();
			q.add(s);
			clo.add(s);
			while(!q.isEmpty())
				for(State t: q.poll().getEmptyTransitionTos())
					if(!clo.contains(t)) {
						q.add(t);
						clo.add(t);
					}
			this.closures.put(s, clo);
		}
		// Add states
		for(State s: this.src.getStates())
			this.smap.put(s,this.dst.new State(s.label,isFinal(s)));
		// Add starts
		for(State s: this.src.getStarts())
			if(this.preemptive)
				this.dst.addStart(this.smap.get(s));
			else
				for(State t: this.closures.get(s))
					this.dst.addStart(this.smap.get(t));
		// Add transitions
		if (this.preemptive)
			for(State s: this.src.getStates()) {
				State ns = this.smap.get(s);
				for(State u: this.closures.get(s))
					for(Transition t: u.getTransitionsFrom())
						if(!t.isEmpty())
							this.dst.createTransition(ns,this.smap.get(t.to),t.symbolSet);
			}
		else
			for(Transition t: this.src.getTransitions())
				if(!t.isEmpty()) {
					State ns = this.smap.get(t.from);
					for(State u: this.closures.get(t.to))
						this.dst.createTransition(ns,this.smap.get(u),t.symbolSet);
				}
	}

}

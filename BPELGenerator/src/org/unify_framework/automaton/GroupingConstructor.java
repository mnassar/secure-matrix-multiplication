package org.unify_framework.automaton;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

public class GroupingConstructor extends AutomatonConstructor {

	public GroupingConstructor(Automaton src) {
		super(src);
	}
	
	protected final Map<State,State> smap = new HashMap<State,State>();

	@Override
	protected void construct() {
		for(State s: this.src.getStates()) {
			State ns = this.dst.new State(s.label,s.isFinal);
			this.smap.put(s,ns);
		}

		for(State s: this.src.getStarts())
			this.dst.addStart(this.smap.get(s));

		for(State s: this.src.getStates()) {
			final Collection<Transition> ts = new LinkedList<Transition>(s.getTransitionsFrom());
			while(!ts.isEmpty()) {
				final Iterator<Transition> it = ts.iterator();
				final Transition t = it.next();
				it.remove();
				SymbolSet symbolSet = t.symbolSet;
				while(it.hasNext()) {
					Transition cand = it.next();
					if(t.to.equals(cand.to)) {
						symbolSet = symbolSet.unify(cand.symbolSet);
						it.remove();
					}
				}
				this.dst.createTransition(this.smap.get(s), this.smap.get(t.to), symbolSet);
			}
		}
	}

}

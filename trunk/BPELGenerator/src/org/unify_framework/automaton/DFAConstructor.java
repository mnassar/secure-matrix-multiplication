package org.unify_framework.automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedHashSet;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

public class DFAConstructor extends AutomatonConstructor {

	public DFAConstructor(Automaton src) {
		super(src);
	}
	
	protected Queue<Set<State>> q = new LinkedList<Set<State>>();
	protected Map<Set<State>,State> smap = new HashMap<Set<State>,State>();

	protected State newState(Set<State> states) {
		boolean isFinal = false;
		StringBuffer label = new StringBuffer();
		boolean first = true;
		for(State s: states) {
			isFinal = isFinal || s.isFinal;
			if(first) {
				first = false;
				label.append(s.label);
			} else
				label.append("," + s.label);
		}
		if(first)
			throw new IllegalArgumentException("State for no states");
		State ns = this.dst.new State(label.toString(),isFinal);
		this.smap.put(states,ns);
		this.q.add(states);
		return ns;
	}
	
	private static <E> Set<E> append(Set<E> s, E x) {
		Set<E> res = new LinkedHashSet<E>(s.size()+1);
		res.addAll(s);
		res.add(x);
		return Collections.unmodifiableSet(res);
	}

	@Override
	protected void construct() {
		Set<State> starts = this.src.getStarts();
		if(starts.isEmpty())
			return;
		this.dst.addStart(newState(starts));

		while(!this.q.isEmpty()) {
			Set<State> states = this.q.poll();
			State ns = this.smap.get(states);
			// Iterate over transitions and Group transition ends
			Collection<TransitionEnd> tes = Collections.emptyList();
			for(State s: states)
				for(Transition t: s.getTransitionsFrom()) {
					final Collection<TransitionEnd> ntes = new ArrayList<TransitionEnd>(tes.size()*2+1);
					if(t.isEmpty())
						throw new EpsilonException();
					SymbolSet r = t.symbolSet;
					for(TransitionEnd te: tes) {
						SymbolSet o1 = te.symbolSet.substract(r);
						if(!o1.isNone())
							ntes.add(new TransitionEnd(te.tos,o1));
						SymbolSet o2 = te.symbolSet.intersect(r);
						if(!o2.isNone())
							ntes.add(new TransitionEnd(append(te.tos,t.to),o2));
						r = r.substract(te.symbolSet);
					}
					if(!r.isNone())
						ntes.add(new TransitionEnd(Collections.singleton(t.to),r));
					tes = ntes;
				}
			// Add transitions
			for (TransitionEnd te : tes) {
				State nt = this.smap.get(te.tos);
				if (nt == null)
					nt = newState(te.tos);
				this.dst.createTransition(ns, nt, te.symbolSet);
			}
		}
	}
	
	public static class TransitionEnd {
		public final Set<State> tos;
		public final SymbolSet symbolSet;
		
		public TransitionEnd(Set<State> tos, SymbolSet symbolSet) {
			this.tos = tos;
			this.symbolSet = symbolSet;
		}
		
		@Override
		public int hashCode() {
			return this.tos.hashCode() ^ this.symbolSet.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == this)
				return true;
			else if(o instanceof TransitionEnd) {
				TransitionEnd ot = (TransitionEnd)o;
				return this.tos.equals(ot.tos) && this.symbolSet.equals(ot.symbolSet);
			} else
				return false;
		}
	}

}

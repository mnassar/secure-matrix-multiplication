package org.unify_framework.automaton;

import java.util.HashMap;
import java.util.Map;

import org.unify_framework.automaton.Automaton.State;

public abstract class PairingConstructor extends AutomatonConstructor {

	protected final Automaton other;
	protected final Map<StatePair,State> smap = new HashMap<StatePair,State>();

	public PairingConstructor(Automaton src, Automaton other) {
		super(src);
		this.other = other;
	}

	protected void putPairState(State a, State b, State n) {
		this.smap.put(new StatePair(a,b),n);
	}

	protected State getPairState(State a, State b) {
		return this.smap.get(new StatePair(a,b));
	}

	public static class StatePair {
		public final State a;
		public final State b;
		
		public StatePair(State a, State b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public int hashCode() {
			return this.a.hashCode() ^ this.b.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if(o == this)
				return true;
			else if(o instanceof StatePair) {
				StatePair so = (StatePair)o;
				return this.a.equals(so.a) && this.b.equals(so.b);
			} else
				return false;
				
		}		
	}
}
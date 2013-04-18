package org.unify_framework.automaton;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

/**
 * Interleave automaton with another automaton
 */
public class InterleaveConstructor extends PairingConstructor {

	public InterleaveConstructor(Automaton src, Automaton other) {
		super(src,other);
	}
	
	@Override
	protected void construct() {
		
		for (State sa : this.src.getStates())
			for (State sb : this.other.getStates()) {
				State sr = this.dst.new State(sa.label + "*" + sb.label, sa.isFinal && sb.isFinal);
				putPairState(sa, sb, sr);
			}
		
		this.dst.addStart(getPairState(this.src.getStart(), this.other.getStart()));

		for (State sa : this.src.getStates())
			for (State sb : this.other.getStates()) {
				State sr = getPairState(sa, sb);
				for (Transition t : sa.getTransitionsFrom())
					this.dst.createTransition(sr, getPairState(t.to, sb), t.symbolSet);
				for (Transition t : sb.getTransitionsFrom())
					this.dst.createTransition(sr, getPairState(sa, t.to), t.symbolSet);
			}
	}
}

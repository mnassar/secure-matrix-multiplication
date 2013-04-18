package org.unify_framework.automaton;

import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.Automaton.Transition;

/**
 * Intersect automaton with other automaton (DFA only)
 */
public class IntersectConstructor extends PairingConstructor {
	
	public IntersectConstructor(Automaton src, Automaton other) {
		super(src,other);
	}
	
	@Override
	protected void construct() {
		for(State sa: this.src.getStates())
			for(State sb: this.other.getStates()) {
				State sr = this.dst.new State(sa.label + "*" + sb.label, sa.isFinal && sb.isFinal);
				putPairState(sa,sb,sr);
			}
		
		this.dst.addStart(getPairState(this.src.getStart(),this.other.getStart()));

		for(State sa: this.src.getStates())
			for(State sb: this.other.getStates()) {
				State sr = getPairState(sa,sb);
				for(Transition ta: sa.getTransitionsFrom())
					for(Transition tb: sb.getTransitionsFrom()) {
						if(ta.isEmpty())
							throw new EpsilonException();
						SymbolSet ss = ta.symbolSet.intersect(tb.symbolSet);
						if(!ss.isNone())
							this.dst.createTransition(sr,getPairState(ta.to,tb.to),ss);
					}
			}
	}
}

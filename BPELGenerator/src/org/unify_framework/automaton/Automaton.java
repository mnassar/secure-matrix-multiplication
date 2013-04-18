package org.unify_framework.automaton;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;

public class Automaton {

	protected final Set<State> states = new LinkedHashSet<State>();
	protected final Set<State> starts = new LinkedHashSet<State>();
	protected final Set<Transition> transitions = new LinkedHashSet<Transition>();
		
	protected void addState(State st) {
		if(st.getAutomaton() != this)
			throw new IllegalArgumentException("State from another automaton");
		this.states.add(st);
	}

	public void addStart(State start) {
		if(start.getAutomaton() != this)
			throw new IllegalArgumentException("State from another automaton");
		this.starts.add(start);
	}
	
	protected void addTransition(Transition t) {
		if(t.getAutomaton() != this)
			throw new IllegalArgumentException("Transition from another automaton");
		this.transitions.add(t);
	}
	
	public Set<State> getStates() {
		return Collections.unmodifiableSet(this.states);
	}
	
	public Set<State> getStarts() {
		return Collections.unmodifiableSet(this.starts);
	}
	
	public Set<Transition> getTransitions() {
		return Collections.unmodifiableSet(this.transitions);
	}
	
	/**
	 * Return the starting state (DFA only)
	 */
	public State getStart() {
		Iterator<State> it = getStarts().iterator();
		if(it.hasNext()) {
			State res = it.next();
			if(it.hasNext())
				throw new UndeterminedException();
			else
				return res;
		} else
			return null;
	}

	public class State {
		public final String label;
		public boolean isFinal;
		
		public State(String label, boolean isFinal) {
			this.label = label;
			this.isFinal = isFinal;
			addState(this);
		}
		
		public Automaton getAutomaton() {
			return Automaton.this;
		}
		
		public Set<Transition> getTransitionsFrom() {
			
			Set<Transition> result = new LinkedHashSet<Transition>(Automaton.this.transitions.size());
			for (Transition t : Automaton.this.transitions) {
				if (t.isFrom(this)) {
					result.add(t);
				}
			}
			return result;
		}
		
		public Set<State> getEmptyTransitionTos() {
			Set<State> tos = new LinkedHashSet<State>(Automaton.this.transitions.size());
			for(Transition t: getTransitionsFrom())
				if(t.isEmpty())
					tos.add(t.to);
			return tos;
		}
		
		public void setFinal() {
			this.isFinal = true;
		}
		
		public void clearFinal() {
			this.isFinal = false;
		}
		
		/**
		 * Return the next state for a given symbol (DFA only)
		 */
		public State getNext(String symbol) {
			
			State next = null;
			for (Transition t : getTransitionsFrom()) {
				if (t.matches(symbol)) {
					if (next == null) {
						next = t.to;
					} else {
						throw new UndeterminedException();
					}
				}
			}
			return next;
		}
		
		@Override
		public String toString() {
			return this.label;
		}
	}
	
	public void createTransition(State from, State to) {
		
		addTransition(new Transition(from, to));
	}
	
	public void createTransition(State from, State to, String symbol) {
		
		addTransition(new Transition(from, to, symbol));
	}
	
	public void createTransition(State from, State to, SymbolSet symbolSet) {
		
		addTransition(new Transition(from, to, symbolSet));
	}
	
	public class Transition {
		
		public final State from;
		public final State to;
		public final SymbolSet symbolSet;
		
		public Transition(State from, State to) {
			
			this(from, to, (SymbolSet) null);
		}
		
		public Transition(State from, State to, String symbol) {
			
			this(from, to, new SymbolSet(true, symbol));
		}
		
		public Transition(State from, State to, SymbolSet symbolSet) {
			
			if (from.getAutomaton() != getAutomaton())
				throw new IllegalArgumentException("From-state from different automaton");
			this.from = from;
			if (to.getAutomaton() != getAutomaton())
				throw new IllegalArgumentException("To-state from different automaton");
			this.to = to;
			this.symbolSet = symbolSet;
		}
		
		public Automaton getAutomaton() {
			return Automaton.this;
		}
		
		public boolean isFrom(State state) {
			
			return this.from.equals(state);
		}
		
		public boolean matches(String symbol) {
			if(this.symbolSet == null)
				throw new EpsilonException();
			return this.symbolSet.match(symbol);
		}
		
		public boolean isEmpty() {
			return this.symbolSet == null;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == this)
				return true;
			else if(o instanceof Transition) {
				Transition ot = (Transition)o;
				return this.from.equals(ot.from) && this.to.equals(ot.to) &&
					(this.symbolSet == null ? ot.symbolSet == null : this.symbolSet.equals(ot.symbolSet));
			} else
				return false;
		}
		
		@Override
		public int hashCode() {
			return this.from.hashCode() ^ this.to.hashCode() ^ (this.symbolSet == null ? 0 : this.symbolSet.hashCode());
		}
		
		
		@Override
		public String toString() {
			return this.from + "-" + this.symbolSet + "-" + this.to;
		}
	}
	
	/**
	 * Write a graphviz 'dot' representation of the automaton to a given OutputStream
	 */
	public void generateGraphviz(OutputStream os) {
		final PrintWriter out = new PrintWriter(new OutputStreamWriter(os,Charset.forName("utf-8")));

		out.println("digraph {");
		out.println("\trankdir=LR;");

		out.println("\tstart [shape=point];");
		Map<State,String> names = new HashMap<State,String>();
		int i = 0;
		for(State s: getStates())
			if(!names.containsKey(s)) {
				String name = "q" + i++;
				names.put(s,name);
				out.format("\t%s [label=\"%s\",shape=%s];%n", name, s.label, s.isFinal ? "doublecircle" : "circle");
			}
		
		for(State start: getStarts())
			out.format("\tstart -> %s;%n", names.get(start));
		for(Transition t: getTransitions())
			out.format("\t%s -> %s [label=\"%s\"];%n", names.get(t.from), names.get(t.to),
					t.symbolSet == null ? "\u03b5" : t.symbolSet);
		
		out.println("}");
		out.flush();
	}
	
	/**
	 * Simulate a trace on this automaton (DFA only)
	 */
	public boolean simulate(Iterable<String> trace) {
		State state = getStart();
		if(state == null)
			return false;
		for(String symbol: trace) {
			state = state.getNext(symbol);
			if(state == null)
				return false;
		}
		return state.isFinal;
	}
	
	public Automaton group() {
		return new GroupingConstructor(this).execute();
	}

	public Automaton intersect(Automaton o) {
		return new IntersectConstructor(this,o).execute();
	}
	
	public Automaton interleave(Automaton o) {
		return new InterleaveConstructor(this,o).execute();
	}
	
	public Automaton reduce() {
		return new ReduceConstructor(this).execute();
	}
	
	public Automaton toDFA() {
		return new DFAConstructor(this).execute();
	}
	
	public Automaton toNFA(boolean preemptive) {
		return new NFAConstructor(this,preemptive).execute();
	}
	
	/**
	 * Check whether the automaton has final states
	 * (for a reduced automaton, this check whether it is satisfiable)
	 */
	public boolean hasFinalState() {
		for(State s: getStates())
			if(s.isFinal)
				return true;
		return false;
	}

}

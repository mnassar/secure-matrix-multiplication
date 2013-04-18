package org.unify_framework.automaton;

public abstract class AutomatonConstructor {
	
	protected final Automaton src;
	protected final Automaton dst = new Automaton();
	
	public AutomatonConstructor(Automaton src) {
		
		this.src = src;
	}
	
	protected abstract void construct();
	
	public Automaton execute() {
		
		construct();
		return this.dst;
	}
}

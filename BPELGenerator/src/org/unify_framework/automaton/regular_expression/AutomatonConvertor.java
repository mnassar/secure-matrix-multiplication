package org.unify_framework.automaton.regular_expression;

import java.util.ArrayList;
import java.util.Collection;

import org.unify_framework.automaton.Automaton;
import org.unify_framework.automaton.SymbolSet;
import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.regular_expression.ast.*;

public class AutomatonConvertor implements RegExpTreeVisitor {
	
	protected final Automaton dst;
	protected State cur;
	protected int i;
	
	protected State newState() {
		return this.dst.new State(Integer.toString(this.i++),false);
	}
 	
	public AutomatonConvertor() {
		this.dst = new Automaton();
		this.i = 0;
		this.cur = newState();
		this.dst.addStart(this.cur);
	}

	@Override
	public void visit(Symbol symbol) {
		State n = newState();
		this.dst.createTransition(this.cur,n,symbol.getSymbol());
		this.cur = n;
	}

	@Override
	public void visit(SymbolClass symbolClass) {
		State n = newState();
		Collection<String> symbols = new ArrayList<String>(symbolClass.getSymbols().size());
		for(Symbol s: symbolClass.getSymbols())
			symbols.add(s.getSymbol());
		this.dst.createTransition(this.cur,n,new SymbolSet(!symbolClass.isNegated(),symbols));
		this.cur = n;
	}

	@Override
	public void visit(Dot dot) {
		State n = newState();
		this.dst.createTransition(this.cur,n,new SymbolSet(false));
		this.cur = n;
	}
	
	@Override
	public void visit(Concatenation concatenation) {
		concatenation.getLhs().accept(this);
		concatenation.getRhs().accept(this);	
	}

	
	@Override
	public void visit(Alternative alternative) {
		State o = this.cur;
		State n = newState();
		alternative.getLhs().accept(this);
		this.dst.createTransition(this.cur,n,(SymbolSet)null);
		this.cur = o;
		alternative.getRhs().accept(this);
		this.dst.createTransition(this.cur,n,(SymbolSet)null);
		this.cur = n;
	}

	@Override
	public void visit(Star star) {
		State n = newState();
		this.dst.createTransition(this.cur,n,(SymbolSet)null);
		this.cur = n;
		star.getLhs().accept(this);
		this.dst.createTransition(this.cur,n,(SymbolSet)null);
		this.cur = n;
	}

	@Override
	public void visit(Plus plus) {
		State n = newState();
		this.dst.createTransition(this.cur,n,(SymbolSet)null);
		this.cur = n;
		plus.getLhs().accept(this);
		this.dst.createTransition(this.cur,n,(SymbolSet)null);
	}

	@Override
	public void visit(RegExp regexp) {
		// Do nothing
		
	}

	@Override
	public void visit(Repetition repetition) {
		// Do nothing
		
	}
	
	public Automaton getResult() {
		this.cur.setFinal();
		return this.dst;
	}
	
	public static Automaton convert(RegExp regexp) {
		AutomatonConvertor conv = new AutomatonConvertor();
		regexp.accept(conv);
		return conv.getResult();
	}

}

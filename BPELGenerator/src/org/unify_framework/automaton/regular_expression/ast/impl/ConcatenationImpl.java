package org.unify_framework.automaton.regular_expression.ast.impl;

import org.unify_framework.automaton.regular_expression.ast.Concatenation;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.RegExpTreeVisitor;

public class ConcatenationImpl extends RegExpImpl implements Concatenation{
	RegExp lhs;
	RegExp rhs;
	
	public static Concatenation ConcatenationOf(RegExp... list){
		if(list.length <2) throw new IllegalArgumentException("must have more than two arguments");
		
		Concatenation accum = new ConcatenationImpl(list[0], list[1]);
		for (int i = 2; i < list.length; i++) {
			accum = new ConcatenationImpl(accum, list[i]);
		}
		
		return accum;
	}
	
	public ConcatenationImpl(RegExp lhs, RegExp rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	@Override
	public RegExp getLhs() {
		return this.lhs;
	}
	@Override
	public RegExp getRhs() {
		return this.rhs;
	}
	@Override
	public void accept(RegExpTreeVisitor visitor) {
		visitor.visit(this);
	}
	
	
}

package org.unify_framework.automaton.regular_expression.test;

import java.io.IOException;

import org.unify_framework.automaton.Automaton;
import org.unify_framework.automaton.TestAutomaton;
import org.unify_framework.automaton.Automaton.State;
import org.unify_framework.automaton.regular_expression.AutomatonConvertor;
import org.unify_framework.automaton.regular_expression.ast.PrettyPrinter;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.impl.*;

public class TestRegExp {
	
	public static void main(String[] args) throws Exception {
		testSimpleConstruct();
		testConcatOf();
		testDotStar();
		testAutomatonConvertor();
	}
	
	private static void testAutomatonConvertor() throws IOException {
		RegExp reg = ConcatenationImpl.ConcatenationOf(
			new SymbolImpl("a"),
			RegExp.DOT_STAR,
			new SymbolImpl("b")
		);
		System.out.println(PrettyPrinter.prettyPrint(reg));
		Automaton a = AutomatonConvertor.convert(reg);
		TestAutomaton.writeAutomaton(a, "/Users/bruno/Desktop/reg.dot");
		Automaton b = a.toNFA(true);
		TestAutomaton.writeAutomaton(b, "/Users/bruno/Desktop/reg-nfa.dot");
		Automaton c = b.toDFA();
		TestAutomaton.writeAutomaton(c, "/Users/bruno/Desktop/reg-dfa.dot");
		Automaton z = new Automaton();
		State z0 = z.new State("z0",false);
		State z1 = z.new State("z1",false);
		State z2 = z.new State("z2",true);
		State z3 = z.new State("z3",false);
		z.addStart(z0);
		z.createTransition(z0,z1,"a");
		z.createTransition(z0,z0,"a");
		z.createTransition(z1,z2,"c");
		z.createTransition(z2,z3,"b");
		TestAutomaton.writeAutomaton(z, "/Users/bruno/Desktop/z.dot");
		Automaton zint = b.intersect(z);
		TestAutomaton.writeAutomaton(zint, "/Users/bruno/Desktop/zint.dot");
	}
		
	private static void testDotStar() {
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				RegExp.DOT_STAR,
				new SymbolImpl("a"),
				RegExp.DOT_STAR,
				new SymbolImpl("b"),
				RegExp.DOT_STAR
		);
		//// .*a.*b.*
		System.out.println(PrettyPrinter.prettyPrint(reg));
	}

	private static void testSimpleConstruct(){
		RegExp reg = new AlternativeImpl(new ConcatenationImpl(new SymbolImpl("a"),new DotImpl()), new SymbolClassImpl(false, new SymbolImpl("b")));
		System.out.println(PrettyPrinter.prettyPrint(reg));
	}
	
	private static void testConcatOf(){
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				new StarImpl(new DotImpl()),
				new SymbolImpl("a"),
				new SymbolClassImpl(true,new SymbolImpl("b")),
				new StarImpl(new DotImpl())
		);
		//.*a[^b].*
		System.out.println(PrettyPrinter.prettyPrint(reg));
	}

}

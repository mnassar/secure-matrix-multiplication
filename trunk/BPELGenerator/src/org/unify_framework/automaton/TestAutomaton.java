package org.unify_framework.automaton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.unify_framework.automaton.Automaton.*;

public class TestAutomaton {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Automaton a = new Automaton();
		State q0 = a.new State("q0",false);
		State q1 = a.new State("q1",false);
		State q2 = a.new State("q2",true);
		a.addStart(q0);
		a.createTransition(q0,q1,"a");
		a.createTransition(q1,q2,"a");
		a.createTransition(q2,q1,"b");
		writeAutomaton(a,"/Users/bruno/Desktop/simple.dot");
		simulate(a,"a","b","a");
		simulate(a,"a","a","b","a");
		
		Automaton b = new Automaton();
		State s0 = b.new State("s0",false);
		State s1 = b.new State("s1",true);
		b.addStart(s0);
		b.createTransition(s0,s0,"a");
		b.createTransition(s0,s1,"b");
		b.createTransition(s1,s1,"a");
		writeAutomaton(b,"/Users/bruno/Desktop/simple2.dot");
		simulate(b,"a","b","a");
		simulate(b,"a","a","b","a");
		
		Automaton c = a.intersect(b);
		writeAutomaton(c,"/Users/bruno/Desktop/intersect.dot");
		simulate(c,"a","a","b","a");
		System.out.println(c.reduce().hasFinalState());
		
		Automaton d = a.interleave(b);
		writeAutomaton(d,"/Users/bruno/Desktop/interleave.dot");
		
		Automaton e = new Automaton();
		State z0 = e.new State("z0",false);
		e.addStart(z0);
		State z1 = e.new State("z1",true);
		e.createTransition(z0,z0,"0");
		e.createTransition(z0,z1,"0");
		e.createTransition(z0,z1,"1");
		e.createTransition(z0,z1,"2");
		e.createTransition(z1,z0,"1");
		e.createTransition(z1,z1,"1");
		writeAutomaton(e.group(),"/Users/bruno/Desktop/nfa.dot");	
		writeAutomaton(e.toDFA().group(),"/Users/bruno/Desktop/dfa.dot");
		
		Automaton f = new Automaton();
		State x0 = f.new State("x0",false);
		f.addStart(x0);
		State x1 = f.new State("x1",false);
		State x2 = f.new State("x2",true);
		f.createTransition(x0,x0,"0");
		f.createTransition(x1,x1,"1");
		f.createTransition(x2,x2,"2");
		f.createTransition(x0,x1,(SymbolSet)null);
		f.createTransition(x1,x2,(SymbolSet)null);
		writeAutomaton(f,"/Users/bruno/Desktop/eps.dot");
		writeAutomaton(f.toNFA(true),"/Users/bruno/Desktop/epsp.dot");
		writeAutomaton(f.toNFA(false).toDFA(),"/Users/bruno/Desktop/epsn.dot");
	}
	
	public static void writeAutomaton(Automaton a, String file) throws IOException {
		final OutputStream os = new FileOutputStream(file);
		try {
			a.generateGraphviz(os);
		} finally {
			os.close();
		}
	}
	
	public static void simulate(Automaton a, String... symbols) {
		List<String> trace = Arrays.asList(symbols);
		System.out.format("%s %b%n",trace,new Boolean(a.simulate(trace)));
	}

}

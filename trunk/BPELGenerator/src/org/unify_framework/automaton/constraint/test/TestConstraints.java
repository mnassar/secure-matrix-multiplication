package org.unify_framework.automaton.constraint.test;

import java.io.IOException;

import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.impl.XorJoinImpl;
import org.unify_framework.abstract_syntax.impl.XorSplitImpl;
import org.unify_framework.automaton.Automaton;
import org.unify_framework.automaton.TestAutomaton;
import org.unify_framework.automaton.constraint.After;
import org.unify_framework.automaton.constraint.Before;
import org.unify_framework.automaton.constraint.Between;
import org.unify_framework.automaton.constraint.Exclude;
//import org.unify_framework.automaton.constraint.Excludes;
import org.unify_framework.automaton.constraint.Follows;
import org.unify_framework.automaton.constraint.May;
import org.unify_framework.automaton.constraint.Predicate;
import org.unify_framework.automaton.constraint.RegExpPredicate;
import org.unify_framework.automaton.regular_expression.ast.RegExp;
import org.unify_framework.automaton.regular_expression.ast.impl.ConcatenationImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.StarImpl;
import org.unify_framework.automaton.regular_expression.ast.impl.SymbolImpl;
import org.unify_framework.automaton.translator.Translator;

@SuppressWarnings("deprecation")
public class TestConstraints {

	public static void main(String[] args) {

		try {
			test01SeqWorkflow();
			testMainWorkflow();
			testXOrWorkflow();
			testXOrLoopWorkflow();
			testCompositeWorkflow();
			testAndWorkflow();
			testAndLoopWorkflow(); // Doesn't work (I think it shouldn't)
			testNestedAndWorkflow();
			testNestedWorkflow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void test01SeqWorkflow() {

		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(startEvent, receiveOrder);

		mainWorkflow.connect(fillOrder, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);

		String file = "automaton.dot";
		Automaton aut = new Translator().translate(mainWorkflow);
		printAutomaton(file, aut);
		
		
		//ROFOCO
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				new SymbolImpl(receiveOrder.getName()),
				new SymbolImpl(fillOrder.getName()),
				new SymbolImpl(closeOrder.getName())
		);
		Predicate p = new RegExpPredicate(reg);		
		System.out.println("true >"+p.eval(mainWorkflow));
		
		//RO*FOCO
		reg = ConcatenationImpl.ConcatenationOf(
				new StarImpl(new SymbolImpl(receiveOrder.getName())),
				new SymbolImpl(fillOrder.getName()),
				new SymbolImpl(closeOrder.getName())
		);
		p = new RegExpPredicate(reg);		
		System.out.println("true >"+p.eval(mainWorkflow));
		
		//RO*CO
		reg = ConcatenationImpl.ConcatenationOf(
				new StarImpl(new SymbolImpl(receiveOrder.getName())),
				new SymbolImpl(closeOrder.getName())
		);
		p = new RegExpPredicate(reg);		
		System.out.println("false >"+p.eval(mainWorkflow));
		
	}
	
	public static void printAutomaton(String file, Automaton aut) {
		
		try {
			TestAutomaton.writeAutomaton(aut, file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void testAndLoopWorkflow() {
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");

		AndSplitImpl andSplit = new AndSplitImpl("andSplit", false);
		AndJoinImpl andJoin = new AndJoinImpl("andJoin", false, andSplit);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(andSplit);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(andJoin);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andJoin
				.getNewControlInputPort());
		mainWorkflow.connect(andJoin.getControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(producePart1.getControlOutputPort(), andSplit
				.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), testQuality
				.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), producePart2.getControlInputPort());
		mainWorkflow.connect(producePart2.getControlOutputPort(),andJoin.getNewControlInputPort());
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);
		
		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "andLoop.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	private static void testAndWorkflow() {
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");

		AndSplitImpl andSplit = new AndSplitImpl("andSplit", false);
		AndJoinImpl andJoin = new AndJoinImpl("andJoin", false, andSplit);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(andSplit);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(andJoin);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(andJoin.getControlOutputPort(), testQuality
				.getControlInputPort());
		mainWorkflow.connect(producePart1.getControlOutputPort(), andJoin
				.getNewControlInputPort());
		mainWorkflow.connect(producePart2.getControlOutputPort(), andJoin
				.getNewControlInputPort());
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andSplit
				.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), producePart2
				.getControlInputPort());
		
		
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		String file = "and.dot";
		printAutomaton(file, aut);
	}

	private static void testCompositeWorkflow() {

		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");

		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl(
				"BillingWorkflow");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl(
				"ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);

		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(billingWorkflow);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, billingWorkflow);
		mainWorkflow.connect(billingWorkflow, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "composite.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void testMainWorkflow() {

		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "mainWorkflow.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private static void testNestedAndWorkflow() {
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl a = new AtomicActivityImpl("A");
		AtomicActivityImpl b = new AtomicActivityImpl("B");
		AtomicActivityImpl c = new AtomicActivityImpl("C");
//		AtomicActivityImpl d = new AtomicActivityImpl("D");
		AtomicActivityImpl e = new AtomicActivityImpl("E");
		AtomicActivityImpl f = new AtomicActivityImpl("F");
		AtomicActivityImpl g = new AtomicActivityImpl("G");

		AndSplitImpl andSplit1 = new AndSplitImpl("andSplit1", false);
		AndJoinImpl andJoin1 = new AndJoinImpl("andJoin1", false, andSplit1);
		AndSplitImpl andSplit2 = new AndSplitImpl("andSplit1", false);
		AndJoinImpl andJoin2 = new AndJoinImpl("andJoin1", false, andSplit2);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		
		mainWorkflow.addChild(a);
		mainWorkflow.addChild(b);
		mainWorkflow.addChild(c);
//		mainWorkflow.addChild(d);
		mainWorkflow.addChild(e);
		mainWorkflow.addChild(f);
		mainWorkflow.addChild(g);
		mainWorkflow.addChild(andSplit1);
		mainWorkflow.addChild(andSplit2);
		mainWorkflow.addChild(andJoin2);
		mainWorkflow.addChild(andJoin1);
		
		
		mainWorkflow.connect(startEvent, a);
		mainWorkflow.connect(a.getControlOutputPort(), andSplit1.getControlInputPort());
		mainWorkflow.connect(andSplit1.getNewControlOutputPort(), b.getControlInputPort());
		mainWorkflow.connect(andSplit1.getNewControlOutputPort(), c.getControlInputPort());
		mainWorkflow.connect(c.getControlOutputPort(), andSplit2.getControlInputPort());
		mainWorkflow.connect(andSplit2.getNewControlOutputPort(), e.getControlInputPort());
		mainWorkflow.connect(andSplit2.getNewControlOutputPort(), f.getControlInputPort());
		mainWorkflow.connect(f.getControlOutputPort(), andJoin2.getNewControlInputPort());
		mainWorkflow.connect(e.getControlOutputPort(), andJoin2.getNewControlInputPort());
		mainWorkflow.connect(andJoin2.getControlOutputPort(), andJoin1.getNewControlInputPort());
		mainWorkflow.connect(b.getControlOutputPort(), andJoin1.getNewControlInputPort());
		mainWorkflow.connect(andJoin1.getControlOutputPort(), g.getControlInputPort());
		mainWorkflow.connect(g, endEvent);
		
		
		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "andNested.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// abcfeg
		RegExp reg = ConcatenationImpl.ConcatenationOf(
				new SymbolImpl(a.getName()),
				new SymbolImpl(b.getName()),
				new SymbolImpl(c.getName()),
				new SymbolImpl(f.getName()),
				new SymbolImpl(e.getName()),
				new SymbolImpl(g.getName())
		);
		Predicate p = new RegExpPredicate(reg);		
		System.out.println("true >"+p.eval(mainWorkflow));
		
	}

	private static void testNestedWorkflow() {

		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AndSplitImpl andSplit = new AndSplitImpl("AND-split");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");

		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl(
				"BillingWorkflow");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl(
				"ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);

		AndJoinImpl andJoin = new AndJoinImpl("AND-join", false, andSplit);
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(andSplit);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(billingWorkflow);
		mainWorkflow.addChild(andJoin);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andSplit
				.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(),
				billingWorkflow.getControlInputPort());
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship.getControlOutputPort(), andJoin
				.getNewControlInputPort());
		mainWorkflow.connect(billingWorkflow.getControlOutputPort(), andJoin
				.getNewControlInputPort());
		mainWorkflow.connect(andJoin.getControlOutputPort(), closeOrder
				.getControlInputPort());
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "nested.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Nested");
		Predicate p = new Follows(producePart1,producePart2);
		System.out.println("\tfollows(producePart1, producePart2)? false>"+ p.eval(mainWorkflow));
		
		
		p = new After(producePart2, producePart1);
		System.out.println("\tAfter(producePart2, producePart1)? true>"+ p.eval(mainWorkflow));
		
		p = new Between(fillOrder, closeOrder, ship);
		System.out.println("\tBetween(fillOrder, closeOrder, ship)? true>"+ p.eval(mainWorkflow));
		
		p = new Between(receivePayment, closeOrder, ship);
		System.out.println("\tBetween(receivePayment, closeOrder, ship)? false>"+ p.eval(mainWorkflow));
		
		p = new Before(receivePayment, ship);
		System.out.println("\tBefore(receivePayment, ship)? false>"+ p.eval(mainWorkflow));
		
		
		p = new May(producePart1, receivePayment);
		System.out.println("\tMay(producePart1, receivePayment)? true>"+ p.eval(mainWorkflow));
		
		p = new Exclude(producePart1, ship, receiveOrder);
		System.out.println("\tExclude(producePart1, ship, receiveOrder)? false>"+ p.eval(mainWorkflow));
		
	}

	private static void testXOrLoopWorkflow() {
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");

		XorSplitImpl xorS = new XorSplitImpl("xorSplit", false);
		XorJoinImpl xorJ = new XorJoinImpl("xorJoin", false);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(xorS);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(xorJ);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(xorJ.getControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(producePart1.getControlOutputPort(), xorS
				.getControlInputPort());
		mainWorkflow.connect(xorS.getNewControlOutputPort(), testQuality
				.getControlInputPort());
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder.getControlOutputPort(), xorJ
				.getNewControlInputPort());
		
		mainWorkflow.connect(xorS.getNewControlOutputPort(), xorJ
				.getNewControlInputPort());
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "xorLoop.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void testXOrWorkflow() {
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl(
				"MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");

		XorSplitImpl xorS = new XorSplitImpl("xorSplit", false);
		XorJoinImpl xorJ = new XorJoinImpl("xorJoin", false);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(xorS);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(xorJ);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder.getControlOutputPort(), xorS
				.getControlInputPort());
		mainWorkflow.connect(xorS.getNewControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(xorS.getNewControlOutputPort(), producePart2
				.getControlInputPort());
		mainWorkflow.connect(producePart1.getControlOutputPort(), xorJ
				.getNewControlInputPort());
		mainWorkflow.connect(producePart2.getControlOutputPort(), xorJ
				.getNewControlInputPort());
		mainWorkflow.connect(xorJ.getControlOutputPort(), testQuality
				.getControlInputPort());
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		String file = "xor.dot";
		printAutomaton(file, aut);
	}

	
}

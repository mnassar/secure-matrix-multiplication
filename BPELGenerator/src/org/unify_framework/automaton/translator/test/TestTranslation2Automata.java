package org.unify_framework.automaton.translator.test;

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
import org.unify_framework.automaton.translator.Translator;

@SuppressWarnings("deprecation")
public class TestTranslation2Automata {

	public static void main(String[] args) {

		try {
			testSeqWorkflow();
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
	
	private static void testNestedAndWorkflow() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("MainWorkflow");
		
		StartEvent startEvent = mainWorkflow.getStartEvent();
		
		AtomicActivityImpl a = new AtomicActivityImpl("A");
		AtomicActivityImpl b = new AtomicActivityImpl("B");
		AtomicActivityImpl c = new AtomicActivityImpl("C");
//		AtomicActivityImpl d = new AtomicActivityImpl("D");
		AtomicActivityImpl e = new AtomicActivityImpl("E");
		AtomicActivityImpl f = new AtomicActivityImpl("F");
		AtomicActivityImpl g = new AtomicActivityImpl("G");

		AndSplitImpl andS1 = new AndSplitImpl("andSplit1", false);
		AndJoinImpl andJ1 = new AndJoinImpl("andJoin1", false, andS1);
		AndSplitImpl andS2 = new AndSplitImpl("andSplit1", false);
		AndJoinImpl andJ2 = new AndJoinImpl("andJoin1", false, andS2);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		
		mainWorkflow.addChild(a);
		mainWorkflow.addChild(b);
		mainWorkflow.addChild(c);
//		mainWorkflow.addChild(d);
		mainWorkflow.addChild(e);
		mainWorkflow.addChild(f);
		mainWorkflow.addChild(g);
		
		mainWorkflow.addChild(andS1);
		mainWorkflow.addChild(andS2);
		mainWorkflow.addChild(andJ2);
		mainWorkflow.addChild(andJ1);
		
		mainWorkflow.connect(startEvent, a);
		mainWorkflow.connect(a.getControlOutputPort(), andS1.getControlInputPort());
		mainWorkflow.connect(andS1.getNewControlOutputPort(), b.getControlInputPort());
		mainWorkflow.connect(andS1.getNewControlOutputPort(), c.getControlInputPort());
		mainWorkflow.connect(c.getControlOutputPort(), andS2.getControlInputPort());
		mainWorkflow.connect(andS2.getNewControlOutputPort(), e.getControlInputPort());
		mainWorkflow.connect(andS2.getNewControlOutputPort(), f.getControlInputPort());
		mainWorkflow.connect(f.getControlOutputPort(), andJ2.getNewControlInputPort());
		mainWorkflow.connect(e.getControlOutputPort(), andJ2.getNewControlInputPort());
		mainWorkflow.connect(andJ2.getControlOutputPort(), andJ1.getNewControlInputPort());
		mainWorkflow.connect(b.getControlOutputPort(), andJ1.getNewControlInputPort());
		mainWorkflow.connect(andJ1.getControlOutputPort(), g.getControlInputPort());
		mainWorkflow.connect(g, endEvent);
		
		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "andNested.dot");
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

		AndSplitImpl andS = new AndSplitImpl("andSplit", false);
		AndJoinImpl andJ = new AndJoinImpl("andJoin", false, andS);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(andS);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(andJ);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andJ
				.getNewControlInputPort());
		mainWorkflow.connect(andJ.getControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(producePart1.getControlOutputPort(), andS
				.getControlInputPort());
		mainWorkflow.connect(andS.getNewControlOutputPort(), testQuality
				.getControlInputPort());
		mainWorkflow.connect(andS.getNewControlOutputPort(), producePart2.getControlInputPort());
		mainWorkflow.connect(producePart2.getControlOutputPort(),andJ.getNewControlInputPort());
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

		AndSplitImpl andS = new AndSplitImpl("andSplit", false);
		AndJoinImpl andJ = new AndJoinImpl("andJoin", false,andS);

		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(andS);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(andJ);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(andJ.getControlOutputPort(), testQuality
				.getControlInputPort());
		mainWorkflow.connect(producePart1.getControlOutputPort(), andJ
				.getNewControlInputPort());
		mainWorkflow.connect(producePart2.getControlOutputPort(), andJ
				.getNewControlInputPort());
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andS
				.getControlInputPort());
		mainWorkflow.connect(andS.getNewControlOutputPort(), producePart1
				.getControlInputPort());
		mainWorkflow.connect(andS.getNewControlOutputPort(), producePart2
				.getControlInputPort());
		
		
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);

		Automaton aut = new Translator().translate(mainWorkflow);
		String file = "and.dot";
		try {
			TestAutomaton.writeAutomaton(aut, file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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

	private static void testSeqWorkflow() {

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

		Automaton aut = new Translator().translate(mainWorkflow);
		try {
			TestAutomaton.writeAutomaton(aut, "automaton.dot");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
		try {
			TestAutomaton.writeAutomaton(aut, file);
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

		AndJoinImpl andJoin = new AndJoinImpl("AND-join",false,andSplit);
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
	}
}

package org.unify_framework.test;

import java.io.FileOutputStream;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;

/**
 * Shows how Unify workflows can be created using its abstract syntax.  However,
 * Unify workflows are typically loaded from a concrete workflow language, such
 * as WS-BPEL, as is illustrated in {@link TestBpelSourceCodeWeaver} and
 * {@link TestBpelSourceCodeWeaverUsingComposition}.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: TestAbstractSyntax.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestAbstractSyntax {
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		try {
			testMainWorkflow();
			testBillingWorkflow();
			testNestedWorkflow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testMainWorkflow() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("MainWorkflow");
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
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, endEvent);
		mainWorkflow.print();
	}
	
	private static void testBillingWorkflow() {
		
		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl("BillingWorkflow");
		StartEvent startEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent endEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(startEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, endEvent);
		billingWorkflow.print();
	}
	
	private static void testNestedWorkflow() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AndSplitImpl andSplit = new AndSplitImpl("AndSplit");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		
		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl("BillingWorkflow");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);
		
		AndJoinImpl andJoin = new AndJoinImpl("AndJoin");
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
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andSplit.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), producePart1.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), billingWorkflow.getControlInputPort());
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship.getControlOutputPort(), andJoin.getNewControlInputPort());
		mainWorkflow.connect(billingWorkflow.getControlOutputPort(), andJoin.getNewControlInputPort());
		mainWorkflow.connect(andJoin.getControlOutputPort(), closeOrder.getControlInputPort());
		mainWorkflow.connect(closeOrder, endEvent);
		
		mainWorkflow.print();
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/NestedWorkflow.dot"));
			visitor.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

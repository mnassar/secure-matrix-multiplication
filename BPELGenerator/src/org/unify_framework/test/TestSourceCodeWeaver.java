package org.unify_framework.test;

import java.io.FileOutputStream;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.abstract_syntax.connector_mechanism.AfterConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.AroundConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.BeforeConnector;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.ParallelConnector;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;
import org.unify_framework.test.TestConnectorMechanism;

/**
 * Shows how Unify can be used to manually create compositions of concerns and
 * connectors that have themselves been manually specified.  The compositions
 * are subsequently woven using {@link SourceCodeWeaver} objects.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @see TestConnectorMechanism
 * @version $Id: TestSourceCodeWeaver.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestSourceCodeWeaver {
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		try {
			testSynchronizingConnector();
			testBeforeConnector();
			testAfterConnector();
			testReplaceConnector();
			testParallelConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testSynchronizingConnector() {
		
		CompositeActivityImpl mainConcern = new CompositeActivityImpl("ProcessOrder");
		StartEvent mainStartEvent = mainConcern.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent mainEndEvent = mainConcern.getEndEvent();
		mainConcern.addChild(receiveOrder);
		mainConcern.addChild(fillOrder);
		mainConcern.addChild(producePart1);
		mainConcern.addChild(producePart2);
		mainConcern.addChild(testQuality);
		mainConcern.addChild(ship);
		mainConcern.addChild(closeOrder);
		mainConcern.connect(mainStartEvent, receiveOrder);
		mainConcern.connect(receiveOrder, fillOrder);
		mainConcern.connect(fillOrder, producePart1);
		mainConcern.connect(producePart1, producePart2);
		mainConcern.connect(producePart2, testQuality);
		mainConcern.connect(testQuality, ship);
		mainConcern.connect(ship, closeOrder);
		mainConcern.connect(closeOrder, mainEndEvent);
		
		mainConcern.print();
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.dot"));
			visitor.generateGraphviz(mainConcern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CompositeActivityImpl billingConcern = new CompositeActivityImpl("PerformBilling");
		StartEvent billingStartEvent = billingConcern.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent billingEndEvent = billingConcern.getEndEvent();
		billingConcern.addChild(sendInvoice);
		billingConcern.addChild(receivePayment);
		billingConcern.connect(billingStartEvent, sendInvoice);
		billingConcern.connect(sendInvoice, receivePayment);
		billingConcern.connect(receivePayment, billingEndEvent);
		
		billingConcern.print();
		
		Composition composition = new Composition("ProcessOrder", mainConcern);
		composition.addConcern(billingConcern);
		composition.addConnector("CONNECT PerformBilling AND-SPLITTING AT controlport(ProcessOrder.FillOrder.ControlOut) SYNCHRONIZING AT controlport(ProcessOrder.CloseOrder.ControlIn)");
		SourceCodeWeaver weaver = new SourceCodeWeaver(composition);
		weaver.weave();
		
		mainConcern.print();
		
		try {
			GraphvizVisitor gev = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder-Synchronizing.Woven.dot"));
			gev.generateGraphviz(mainConcern);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testBeforeConnector() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("ProcessOrder");
		StartEvent mainStartEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent mainEndEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(mainStartEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, mainEndEvent);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ProcesOrder.dot"));
			visitor.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl("PerformBilling");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);
		billingWorkflow.print();
		
		BeforeConnector beforeConnector = new BeforeConnector(billingWorkflow, closeOrder);
		SourceCodeWeaver weaver = new SourceCodeWeaver();
		weaver.weave(beforeConnector);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor gev = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder-Before.Woven.dot"));
			gev.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testAfterConnector() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("ProcessOrder");
		StartEvent mainStartEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent mainEndEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(mainStartEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, mainEndEvent);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.dot"));
			visitor.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl("PerformBilling");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);
		billingWorkflow.print();
		
		AfterConnector afterConnector = new AfterConnector(billingWorkflow, ship);
		SourceCodeWeaver weaver = new SourceCodeWeaver();
		weaver.weave(afterConnector);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor gev = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder-After.Woven.dot"));
			gev.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testReplaceConnector() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("ProcessOrder");
		StartEvent mainStartEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent mainEndEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(mainStartEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, mainEndEvent);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.dot"));
			visitor.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl("PerformBilling");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl doNothing = new AtomicActivityImpl("DoNothing");
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(doNothing);
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, doNothing);
		billingWorkflow.connect(doNothing, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);
		billingWorkflow.print();
		
		AroundConnector afterConnector = new AroundConnector(billingWorkflow, ship, doNothing);
		SourceCodeWeaver weaver = new SourceCodeWeaver();
		weaver.weave(afterConnector);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor gev = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder-Around.Woven.dot"));
			gev.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testParallelConnector() {
		
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("ProcessOrder");
		StartEvent mainStartEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent mainEndEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(mainStartEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder, producePart1);
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(ship, closeOrder);
		mainWorkflow.connect(closeOrder, mainEndEvent);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.dot"));
			visitor.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CompositeActivityImpl billingWorkflow = new CompositeActivityImpl("PerformBilling");
		StartEvent billingStartEvent = billingWorkflow.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent billingEndEvent = billingWorkflow.getEndEvent();
		billingWorkflow.addChild(sendInvoice);
		billingWorkflow.addChild(receivePayment);
		billingWorkflow.connect(billingStartEvent, sendInvoice);
		billingWorkflow.connect(sendInvoice, receivePayment);
		billingWorkflow.connect(receivePayment, billingEndEvent);
		billingWorkflow.print();
		
		ParallelConnector parallelConnector = new ParallelConnector(billingWorkflow, ship);
		SourceCodeWeaver weaver = new SourceCodeWeaver();
		weaver.weave(parallelConnector);
		mainWorkflow.print();
		
		try {
			GraphvizVisitor gev = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder-Parallel.Woven.dot"));
			gev.generateGraphviz(mainWorkflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

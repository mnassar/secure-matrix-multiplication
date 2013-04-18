package org.unify_framework.test;

import java.io.FileOutputStream;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.visitors.GraphvizVisitor;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;
import org.unify_framework.test.TestSourceCodeWeaver;

/**
 * Shows how Unify can be used to manually create a composition of concerns
 * that have themselves been manually specified, and connectors that are parsed
 * using ANTLR.  The composition is subsequently woven using a
 * {@link SourceCodeWeaver}.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @see TestSourceCodeWeaver
 * @version $Id: TestConnectorMechanism.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestConnectorMechanism {
	
	static {
		
		BasicConfigurator.configure();
	}
	
	public static void main(String[] args) {
		
		CompositeActivityImpl mainConcern = getMainConcern();
		CompositeActivityImpl productionConcern = getProductionConcern();
		CompositeActivityImpl billingConcern = getBillingConcern();
		CompositeActivityImpl reportingConcern = getReportingConcern();
		Composition composition = new Composition("ProcessOrder", mainConcern);
		composition.addConcern(productionConcern);
		composition.addConcern(billingConcern);
		composition.addConcern(reportingConcern);
		composition.addConnector("CONNECT ProcessOrder.ProduceParts TO ProduceParts");
		composition.addConnector("CONNECT PerformBilling AND-SPLITTING AT controlport(ProcessOrder.FillOrder.ControlOut) SYNCHRONIZING AT controlport(ProcessOrder.CloseOrder.ControlIn)");
		composition.addConnector("CONNECT PerformReporting AFTER executingactivity(\".*\\.Receive.*\")");
		
		try {
			GraphvizVisitor visitor = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.dot"));
			visitor.generateGraphviz(mainConcern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SourceCodeWeaver weaver = new SourceCodeWeaver(composition);
		weaver.weave();
		
		mainConcern.print();
		
		try {
			GraphvizVisitor gev = new GraphvizVisitor(new FileOutputStream("./build/ProcessOrder.Woven.dot"));
			gev.generateGraphviz(mainConcern);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static CompositeActivityImpl getMainConcern() {
		
		CompositeActivityImpl mainConcern = new CompositeActivityImpl("ProcessOrder");
		StartEvent startEvent = mainConcern.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AtomicActivityImpl produceParts = new AtomicActivityImpl("ProduceParts");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent endEvent = mainConcern.getEndEvent();
		mainConcern.addChild(receiveOrder);
		mainConcern.addChild(fillOrder);
		mainConcern.addChild(produceParts);
		mainConcern.addChild(testQuality);
		mainConcern.addChild(ship);
		mainConcern.addChild(closeOrder);
		mainConcern.connect(startEvent, receiveOrder);
		mainConcern.connect(receiveOrder, fillOrder);
		mainConcern.connect(fillOrder, produceParts);
		mainConcern.connect(produceParts, testQuality);
		mainConcern.connect(testQuality, ship);
		mainConcern.connect(ship, closeOrder);
		mainConcern.connect(closeOrder, endEvent);
		
		mainConcern.print();
		
		return mainConcern;
	}
	
	private static CompositeActivityImpl getProductionConcern() {
		
		CompositeActivityImpl productionConcern = new CompositeActivityImpl("ProduceParts");
		StartEvent startEvent = productionConcern.getStartEvent();
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		EndEvent endEvent = productionConcern.getEndEvent();
		productionConcern.addChild(producePart1);
		productionConcern.addChild(producePart2);
		productionConcern.connect(startEvent, producePart1);
		productionConcern.connect(producePart1, producePart2);
		productionConcern.connect(producePart2, endEvent);
		
		productionConcern.print();
		
		return productionConcern;
	}
	
	private static CompositeActivityImpl getBillingConcern() {
		
		CompositeActivityImpl billingConcern = new CompositeActivityImpl("PerformBilling");
		StartEvent startEvent = billingConcern.getStartEvent();
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		EndEvent endEvent = billingConcern.getEndEvent();
		billingConcern.addChild(sendInvoice);
		billingConcern.addChild(receivePayment);
		billingConcern.connect(startEvent, sendInvoice);
		billingConcern.connect(sendInvoice, receivePayment);
		billingConcern.connect(receivePayment, endEvent);
		
		billingConcern.print();
		
		return billingConcern;
	}
	
	private static CompositeActivityImpl getReportingConcern() {
		
		CompositeActivityImpl reportingConcern = new CompositeActivityImpl("PerformReporting");
		StartEvent startEvent = reportingConcern.getStartEvent();
		AtomicActivityImpl report = new AtomicActivityImpl("Report");
		EndEvent endEvent = reportingConcern.getEndEvent();
		reportingConcern.addChild(report);
		reportingConcern.connect(startEvent, report);
		reportingConcern.connect(report, endEvent);
		
		reportingConcern.print();
		
		return reportingConcern;
	}
}

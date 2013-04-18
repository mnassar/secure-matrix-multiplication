package org.unify_framework.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.abstract_syntax.impl.AtomicActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.abstract_syntax.visitors.PetriNetVisitor;
import org.unify_framework.execution_model.EmPetriNet;
import org.unify_framework.execution_model.manager.ExecutionManager;
import org.unify_framework.execution_model.manager.WorkMonitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @deprecated The execution model is currently under revision.
 * @version $Id: TestExecutionModel.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestExecutionModel {
	
	private static final Log log = LogFactory.getLog(TestExecutionModel.class);
	
	static {
		
		BasicConfigurator.configure();
		log.debug("Configured log4j");
	}
	
	public static void main(String[] args) {
		
		try {
			testParallelWorkflow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testParallelWorkflow() {
		
		// TODO Support execution of sub-workflows
		CompositeActivityImpl mainWorkflow = new CompositeActivityImpl("MainWorkflow");
		StartEvent startEvent = mainWorkflow.getStartEvent();
		AtomicActivityImpl receiveOrder = new AtomicActivityImpl("ReceiveOrder");
		AtomicActivityImpl fillOrder = new AtomicActivityImpl("FillOrder");
		AndSplitImpl andSplit = new AndSplitImpl("AND-split");
		AtomicActivityImpl producePart1 = new AtomicActivityImpl("ProducePart1");
		AtomicActivityImpl producePart2 = new AtomicActivityImpl("ProducePart2");
		AtomicActivityImpl testQuality = new AtomicActivityImpl("TestQuality");
		AtomicActivityImpl ship = new AtomicActivityImpl("Ship");
		AtomicActivityImpl sendInvoice = new AtomicActivityImpl("SendInvoice");
		AtomicActivityImpl receivePayment = new AtomicActivityImpl("ReceivePayment");
		AndJoinImpl andJoin = new AndJoinImpl("AND-join");
		AtomicActivityImpl closeOrder = new AtomicActivityImpl("CloseOrder");
		EndEvent endEvent = mainWorkflow.getEndEvent();
		mainWorkflow.addChild(receiveOrder);
		mainWorkflow.addChild(fillOrder);
		mainWorkflow.addChild(andSplit);
		mainWorkflow.addChild(producePart1);
		mainWorkflow.addChild(producePart2);
		mainWorkflow.addChild(testQuality);
		mainWorkflow.addChild(ship);
		mainWorkflow.addChild(sendInvoice);
		mainWorkflow.addChild(receivePayment);
		mainWorkflow.addChild(andJoin);
		mainWorkflow.addChild(closeOrder);
		mainWorkflow.connect(startEvent, receiveOrder);
		mainWorkflow.connect(receiveOrder, fillOrder);
		mainWorkflow.connect(fillOrder.getControlOutputPort(), andSplit.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), producePart1.getControlInputPort());
		mainWorkflow.connect(andSplit.getNewControlOutputPort(), sendInvoice.getControlInputPort());
		mainWorkflow.connect(producePart1, producePart2);
		mainWorkflow.connect(producePart2, testQuality);
		mainWorkflow.connect(testQuality, ship);
		mainWorkflow.connect(sendInvoice, receivePayment);
		mainWorkflow.connect(ship.getControlOutputPort(), andJoin.getNewControlInputPort());
		mainWorkflow.connect(receivePayment.getControlOutputPort(), andJoin.getNewControlInputPort());
		mainWorkflow.connect(andJoin.getControlOutputPort(), closeOrder.getControlInputPort());
		mainWorkflow.connect(closeOrder, endEvent);
		mainWorkflow.print();
		
		PetriNetVisitor visitor = new PetriNetVisitor();
		EmPetriNet petriNet = visitor.generatePetriNet(mainWorkflow);
		petriNet.print();
		
		WorkMonitor wm = new WorkMonitor();
		wm.start();
		ExecutionManager em = new ExecutionManager();
		em.start();
		em.addObserver(wm);
		petriNet.setExecutionManager(em);
		petriNet.start();
		
		try {
			while (wm.isAlive()) {
				Thread.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

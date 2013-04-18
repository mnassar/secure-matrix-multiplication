package org.unify_framework.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.log4j.BasicConfigurator;

import org.unify_framework.execution_model.manager.ExecutionManager;
import org.unify_framework.execution_model.manager.WorkItem;
import org.unify_framework.execution_model.manager.WorkMonitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @deprecated The execution model is currently under revision.
 * @version $Id: TestExecutionManager.java 18609 2011-11-24 10:19:18Z njonchee $
 */
public class TestExecutionManager {
	
	private static final Log log = LogFactory.getLog(TestExecutionManager.class);
	
	static {
		
		BasicConfigurator.configure();
		log.debug("Configured log4j");
	}
	
	public static void main(String[] args) {
		
		ExecutionManager em = new ExecutionManager();
		WorkMonitor wm = new WorkMonitor();
		em.addObserver(wm);
		
		Thread emThread = new Thread(em);
		emThread.start();
		Thread wmThread = new Thread(wm);
		wmThread.start();
		
		em.addWorkItem(new WorkItem("One", null));
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		em.addWorkItem(new WorkItem("Two", null));
		em.addWorkItem(new WorkItem("Three", null));
		
		while (wmThread.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

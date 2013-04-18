package org.unify_framework.execution_model.manager;

import org.unify_framework.execution_model.EmToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExecutionManager extends Observable implements Runnable {
	
	private static final Log log = LogFactory.getLog(ExecutionManager.class);
	
	private Set<WorkItem> incomingWorkItems;
	private Map<String, WorkItem> pendingWorkItems;
	private Set<WorkItem> finishedWorkItems;
	
	public ExecutionManager() {
		
//		log.debug("Creating execution manager...");
		this.incomingWorkItems = new HashSet<WorkItem>();
		this.pendingWorkItems = new HashMap<String, WorkItem>();
		this.finishedWorkItems = new HashSet<WorkItem>();
	}
	
	public void addWorkItem(WorkItem workItem) {

    	log.debug("Adding work item to execution manager...");
	    synchronized (this) {
	    	workItem.setExecutionManager(this);
	    	this.incomingWorkItems.add(workItem);
	    }
	}
	
	public void removeWorkItem(WorkItem workItem) {

    	log.debug("Removing work item from execution manager...");
		synchronized (this) {
			workItem.setExecutionManager(null);
			this.pendingWorkItems.remove(workItem);
			this.finishedWorkItems.add(workItem);
			setChanged();
			notifyObservers(workItem);
		}
	}

	@Override
	public void run() {

		try {
			log.debug("Running execution manager...");
			while (true) {
				synchronized (this) {
					
					for (WorkItem workItem : this.incomingWorkItems) {
						this.pendingWorkItems.put(workItem.getName(), workItem);
						setChanged();
						notifyObservers(workItem);
					}
					// In order to avoid ConcurrentModificationExceptions, the
					// incoming WorkItems cannot be removed in the for loop:
					this.incomingWorkItems.clear();
					
					for (WorkItem workItem : this.finishedWorkItems) {
						EmToken token = workItem.getToken();
						if (token != null) {
							token.setWaiting(false);
						}
					}
					// In order to avoid ConcurrentModificationExceptions, the
					// finished WorkItems cannot be removed in the for loop:
					this.finishedWorkItems.clear();
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			log.fatal("The execution manager has encountered a fatal error!", e);
		}
	}
	
	public void start() {
		
		Thread thread = new Thread(this);
		thread.start();
	}
}

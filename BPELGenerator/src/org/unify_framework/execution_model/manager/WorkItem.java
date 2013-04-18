package org.unify_framework.execution_model.manager;

import org.unify_framework.execution_model.EmToken;

public class WorkItem {

	private String name;
	private EmToken token;
	private ExecutionManager em;

	public WorkItem(String name, EmToken token) {
		
		this.name = name;
		this.token = token;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public void finish() {
		
		this.em.removeWorkItem(this);
	}
	
	public ExecutionManager getExecutionManager() {
		
		return this.em;
	}
	
	public EmToken getToken() {
		
		return this.token;
	}
	
	public void setExecutionManager(ExecutionManager em) {
		
		this.em = em;
	}
}

package org.unify_framework.execution_model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.execution_model.manager.ExecutionManager;
import org.unify_framework.execution_model.visitors.Element;
import org.unify_framework.execution_model.visitors.ElementVisitor;
import org.unify_framework.execution_model.visitors.PrintVisitor;

public class EmPetriNet implements Element, Runnable {
	
	private static final Log log = LogFactory.getLog(EmPetriNet.class);
	
	private String name;
	private CompositeActivity activity;
	private EmStartPlace startPlace;
	private Set<EmIntermediatePlace> intermediatePlaces;
	private EmEndPlace endPlace;
	private Set<EmTransition> transitions;
	private Set<EmToken> tokens;
	private ExecutionManager em;
	
	public EmPetriNet(String name, CompositeActivity activity) {

//		log.debug("Creating Petri net...");
		this.name = name;
		this.activity = activity;
		this.intermediatePlaces = new HashSet<EmIntermediatePlace>();
		this.transitions = new HashSet<EmTransition>();
		this.tokens = new HashSet<EmToken>();
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public void addIntermediatePlace(EmIntermediatePlace place) {
		
		place.setParent(this);
		this.intermediatePlaces.add(place);
	}
	
	public void addToken(EmToken token) {
		
		this.tokens.add(token);
	}
	
	public void addTransition(EmTransition transition) {
		
		transition.setParent(this);
		this.transitions.add(transition);
	}
	
	public CompositeActivity getActivity() {
		
		return this.activity;
	}
	
	public EmEndPlace getEndPlace() {
	
		return this.endPlace;
	}
	
	public ExecutionManager getExecutionManager() {
		
		return this.em;
	}
	
	public Collection<EmIntermediatePlace> getIntermediatePlaces() {
		
		return this.intermediatePlaces;
	}
	
	public String getName() {
		
		return this.name;
	}

	
	public EmStartPlace getStartPlace() {
	
		return this.startPlace;
	}
	
	public Collection<EmTransition> getTransitions() {
		
		return this.transitions;
	}
	
	public void print() {
		
		try {
			PrintVisitor visitor = new PrintVisitor(System.out);
			visitor.print(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		try {
			log.debug("Running Petri net '" + getName() + "'...");
			EmToken initialToken = new EmToken(this);
			this.startPlace.addToken(initialToken);
			addToken(initialToken);
			boolean executing = true;
			while (executing) {
				executing = false;
				for (EmToken token : this.tokens) {
					if (token.isAlive()) {
						executing = true;
						token.signal();
					}
				}
				Thread.sleep(1);
			}
			log.info("The execution of Petri net '" + getName() + "' has finished successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setEndPlace(EmEndPlace place) {

		place.setParent(this);
		this.endPlace = place;
	}
	
	public void setExecutionManager(ExecutionManager em) {
		
		this.em = em;
	}
	
	public void setStartPlace(EmStartPlace place) {
		
//		log.debug("Setting start place '" + place.getName() + "', outgoing transition is " + place.getOutgoingTransition());
		place.setParent(this);
		this.startPlace = place;
	}
	
	public void start() {
		
		Thread thread = new Thread(this);
		thread.start();
	}
}

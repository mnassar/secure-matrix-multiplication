package org.unify_framework.execution_model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import org.unify_framework.execution_model.visitors.Element;

/**
 * Represents a place.
 * 
 * A token has a reference to its place, and a place has a reference to its
 * tokens.  The place is responsible for updating these references.
 * 
 * @author Niels Joncheere
 */
public abstract class EmPlace implements Element {
	
//	private static final Log log = LogFactory.getLog(EmPlace.class);
	
	private EmPetriNet parent;
	
	private String name;
	private Set<EmToken> tokens;
	
	private EmTransition incomingTransition;
	private EmTransition outgoingTransition;
	
	public EmPlace(String name) {
		
		this.name = name;
		this.tokens = new HashSet<EmToken>();
	}
	
	public void addToken(EmToken token) {
		
		token.setPlace(this);
		this.tokens.add(token);
	}
	
	public EmTransition getIncomingTransition() {
	
		return this.incomingTransition;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public EmTransition getOutgoingTransition() {
	
		return this.outgoingTransition;
	}
	
	protected EmPetriNet getParent() {
		
		return this.parent;
	}
	
	protected Collection<EmToken> getTokens() {
		
		return this.tokens;
	}
	
	public boolean hasToken() {
		
		return this.tokens.size() > 0;
	}
	
	public boolean hasToken(EmToken token) {
		
		return this.tokens.contains(token);
	}
	
	public void setIncomingTransition(EmTransition incomingTransition) {
	
		this.incomingTransition = incomingTransition;
	}
	
	public void setOutgoingTransition(EmTransition outgoingTransition) {
		
//		log.debug("Setting outgoing transition on place '" + getName() + "'");
		this.outgoingTransition = outgoingTransition;
	}
	
	public EmToken removeToken() {
		
		Iterator<EmToken> iterator = this.tokens.iterator();
		EmToken token = iterator.next();
		removeToken(token);
		return token;
	}
	
	public void removeToken(EmToken token) {
		
		token.setPlace(null);
		this.tokens.remove(token);
	}
	
	protected void setParent(EmPetriNet parent) {
		
		this.parent = parent;
	}
}

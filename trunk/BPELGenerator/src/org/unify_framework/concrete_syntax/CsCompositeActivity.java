package org.unify_framework.concrete_syntax;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.concrete_syntax.visitors.ElementVisitor;
import org.unify_framework.concrete_syntax.visitors.ParseVisitor;
import org.unify_framework.concrete_syntax.visitors.PrintVisitor;

public class CsCompositeActivity extends CsActivity {

	private CsStartEvent startEvent;
	private List<CsActivity> children;
	private CsEndEvent endEvent;
	private List<CsTransition> transitions;
	
	public CsCompositeActivity() {
		
		this.children = new LinkedList<CsActivity>();
		this.transitions = new LinkedList<CsTransition>();
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public void addChild(CsActivity child) {
		
		this.children.add(child);
	}
	
	public void addTransition(CsTransition transition) {
		
		this.transitions.add(transition);
	}
	
	public Collection<CsActivity> getChildren() {
		
		return this.children;
	}
	
	public CsEndEvent getEndEvent() {
		
		return this.endEvent;
	}
	
	public CsStartEvent getStartEvent() {
		
		return this.startEvent;
	}
	
	public Collection<CsTransition> getTransitions() {
		
		return this.transitions;
	}
	
	public CompositeActivityImpl parse() {
		
		ParseVisitor visitor = new ParseVisitor();
		return visitor.parse(this);
	}
	
	public void print() {
		
		PrintVisitor visitor = new PrintVisitor(System.out);
		visitor.print(this);
	}
	
	public void setEndEvent(CsEndEvent endEvent) {
		
		this.endEvent = endEvent;
	}
	
	public void setStartEvent(CsStartEvent startEvent) {
		
		this.startEvent = startEvent;
	}
}

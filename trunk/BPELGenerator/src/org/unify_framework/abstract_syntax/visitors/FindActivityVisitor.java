package org.unify_framework.abstract_syntax.visitors;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.QualifiedName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FindActivityVisitor implements ElementVisitor {
	
	private static final Log log = LogFactory.getLog(FindActivityVisitor.class);
	
	private QualifiedName qualifiedName;
	private int i;
	private int n;
	private String name;
	private Activity result;
	
	public FindActivityVisitor(QualifiedName qualifiedName) {
		
		this.qualifiedName = qualifiedName;
		this.i = 0;
		this.n = qualifiedName.getIdentifiers().size();
		this.name = getName();
	}
	
	private void decrement() {
		
		this.i--;
		this.name = getName();
	}
	
	private String getName() {
		
		return this.qualifiedName.getIdentifiers().get(this.i);
	}
	
	public Activity getResult() {
		
		return this.result;
	}
	
	private void increment() {
		
		this.i++;
		this.name = getName();
	}

	@Override
	public void visit(AndJoin join) {
		
		// Do nothing
	}

	@Override
	public void visit(AndSplit split) {
		
		// Do nothing
	}

	@Override
	public void visit(AtomicActivity activity) {

		String activityName = activity.getName();
//		log.debug("Checking whether atomic activity '" + activityName + "' matches name '" + this.name + "'");
		if (activityName.equals(this.name) && this.i + 1 == this.n) {
			log.debug("Found atomic activity '" + activity.getQualifiedName() + "'");
			this.result = activity;
		}
	}

	@Override
	public void visit(Transition transition) {
		
		// Do nothing
	}

	@Override
	public void visit(CompositeActivity activity) {
		
		String activityName = activity.getName();
//		log.debug("Checking whether composite activity '" + activityName + "' matches name '" + this.name + "'");
		if (activityName.equals(this.name)) {
			if (this.i + 1 == this.n) {
				log.debug("Found composite activity '" + activity.getQualifiedName() + "'");
				this.result = activity;
			} else {
				increment();
				for (Activity childActivity : activity.getActivities()) {
					childActivity.accept(this);
					if (this.result != null) {
						break;
					}
				}
				decrement();
			}
		}
	}

	@Override
	public void visit(Composition composition) {
		
		for (CompositeActivity concern : composition.getConcerns()) {
			concern.accept(this);
			if (this.result != null) {
				break;
			}
		}
	}

	@Override
	public void visit(ControlInputPort controlPort) {
		
		// Do nothing
	}

	@Override
	public void visit(ControlOutputPort controlPort) {
		
		// Do nothing
	}

	@Override
	public void visit(EndEvent event) {
		
		// Do nothing
	}

	@Override
	public void visit(StartEvent event) {
		
		// Do nothing
	}

	@Override
	public void visit(XorJoin join) {
		
		// Do nothing
	}

	@Override
	public void visit(XorSplit split) {
		
		// Do nothing
	}
	
	public void visitComposition(Composition composition) {
		
		composition.accept(this);
	}
	
	public void visitWorkflow(CompositeActivity workflow) {
		
		workflow.accept(this);
	}
}

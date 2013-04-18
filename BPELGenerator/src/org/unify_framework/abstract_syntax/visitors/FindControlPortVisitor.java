package org.unify_framework.abstract_syntax.visitors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;
import org.unify_framework.abstract_syntax.connector_mechanism.ControlInputPortPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.ControlOutputPortPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.ControlPortPointcut;

public class FindControlPortVisitor implements ElementVisitor {
	
	private static final Log log = LogFactory.getLog(FindControlPortVisitor.class);
	
	private ControlPortPointcut controlPortPointcut;
	private int i;
	private int n;
	private String name;
	private ControlPort result;
	private boolean visitControlInputPort;
	private boolean visitControlOutputPort;
	
	public FindControlPortVisitor(ControlPortPointcut controlPortPointcut) {
		
		this.controlPortPointcut = controlPortPointcut;
		this.i = 0;
		this.n = this.controlPortPointcut.getQualifiedName().getIdentifiers().size();
		this.name = getName();
		
		if (this.controlPortPointcut instanceof ControlInputPortPointcut) {
			this.visitControlInputPort = true;
			this.visitControlOutputPort = false;
		} else if (this.controlPortPointcut instanceof ControlOutputPortPointcut) {
			this.visitControlInputPort = false;
			this.visitControlOutputPort = true;
		} else {
			this.visitControlInputPort = true;
			this.visitControlOutputPort = true;
		}
	}
	
	private void decrement() {
		
		this.i--;
		this.name = getName();
	}
	
	private String getName() {
		
		return this.controlPortPointcut.getQualifiedName().getIdentifiers().get(this.i);
	}
	
	public ControlPort getResult() {
		
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
		log.debug("Checking whether atomic activity '" + activityName + "' matches name '" + this.name + "'");
		if (activityName.equals(this.name)) {
			increment();
			if (this.visitControlInputPort) {
				activity.getControlInputPort().accept(this);
			}
			if (this.result == null && this.visitControlOutputPort) {
				activity.getControlOutputPort().accept(this);
			}
			decrement();
		}
	}

	@Override
	public void visit(Transition transition) {
		
		// Do nothing
	}

	@Override
	public void visit(CompositeActivity activity) {
		
		String activityName = activity.getName();
		log.debug("Checking whether composite activity '" + activityName + "' matches name '" + this.name + "'");
		if (activityName.equals(this.name)) {
			increment();
			if (this.visitControlInputPort) {
				activity.getControlInputPort().accept(this);
			}
			if (this.result == null && this.visitControlOutputPort) {
				activity.getControlOutputPort().accept(this);
			}
			for (Node child : activity.getChildren()) {
				child.accept(this);
				if (this.result != null) {
					break;
				}
			}
			decrement();
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

		String controlPortName = controlPort.getName();
		log.debug("Checking whether control input port '" + controlPortName + "' matches name '" + this.name + "'");
		if (controlPortName.equals(this.name) && this.i + 1 == this.n) {
			log.debug("Found control input port '" + controlPort.getQualifiedName() + "'");
			this.result = controlPort;
		}
	}

	@Override
	public void visit(ControlOutputPort controlPort) {

		String controlPortName = controlPort.getName();
		log.debug("Checking whether control output port port '" + controlPortName + "' matches name '" + this.name + "'");
		if (controlPortName.equals(this.name) && this.i + 1 == this.n) {
			log.debug("Found control output port '" + controlPort.getQualifiedName() + "'");
			this.result = controlPort;
		}
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

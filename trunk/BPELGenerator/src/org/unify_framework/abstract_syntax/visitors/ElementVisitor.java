package org.unify_framework.abstract_syntax.visitors;

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

public interface ElementVisitor {
	
	public void visit(AndJoin andJoin);
	public void visit(AndSplit andSplit);
	public void visit(AtomicActivity atomicActivity);
	public void visit(CompositeActivity compositeActivity);
	public void visit(Composition composition);
	public void visit(ControlInputPort controlInputPort);
	public void visit(ControlOutputPort controlOutputPort);
	public void visit(EndEvent endEvent);
	public void visit(StartEvent startEvent);
	public void visit(Transition transition);
	public void visit(XorJoin xorJoin);
	public void visit(XorSplit xorSplit);
}

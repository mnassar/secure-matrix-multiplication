package org.unify_framework.concrete_syntax.visitors;

import org.unify_framework.concrete_syntax.CsAndJoin;
import org.unify_framework.concrete_syntax.CsAndSplit;
import org.unify_framework.concrete_syntax.CsAtomicActivity;
import org.unify_framework.concrete_syntax.CsBasicTransition;
import org.unify_framework.concrete_syntax.CsCompositeActivity;
import org.unify_framework.concrete_syntax.CsEndEvent;
import org.unify_framework.concrete_syntax.CsStartEvent;

public interface ElementVisitor {
	
	public void visit(CsAndJoin andJoin);
	public void visit(CsAndSplit andSplit);
	public void visit(CsAtomicActivity atomicActivity);
	public void visit(CsBasicTransition basicTransition);
	public void visit(CsCompositeActivity compositeActivity);
	public void visit(CsEndEvent endEvent);
	public void visit(CsStartEvent startEvent);
}

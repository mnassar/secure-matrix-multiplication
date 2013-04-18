package org.unify_framework.execution_model.visitors;

import org.unify_framework.execution_model.EmActivityTransition;
import org.unify_framework.execution_model.EmAndJoinTransition;
import org.unify_framework.execution_model.EmAndSplitTransition;
import org.unify_framework.execution_model.EmEndPlace;
import org.unify_framework.execution_model.EmIntermediatePlace;
import org.unify_framework.execution_model.EmPetriNet;
import org.unify_framework.execution_model.EmStartPlace;

public interface ElementVisitor {
	
	public void visit(EmActivityTransition activityTransition);
	public void visit(EmAndJoinTransition andJoinTransition);
	public void visit(EmAndSplitTransition andSplitTransition);
	public void visit(EmEndPlace endPlace);
	public void visit(EmIntermediatePlace intermediatePlace);
	public void visit(EmPetriNet petriNet);
	public void visit(EmStartPlace startPlace);
}

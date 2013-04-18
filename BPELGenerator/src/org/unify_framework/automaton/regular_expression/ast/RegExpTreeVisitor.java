package org.unify_framework.automaton.regular_expression.ast;


public interface RegExpTreeVisitor {

	void visit(Alternative alternative);

	void visit(Star star);

	void visit(Plus plus);

	void visit(SymbolClass symbolClass);

	void visit(Symbol symbol);

	void visit(Concatenation concatenation);
	
	void visit(Dot dot);
	
	void visit(RegExp regexp);
	
	void visit(Repetition repetition);

}

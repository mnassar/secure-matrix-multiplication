package org.unify_framework.automaton.regular_expression.ast;


public class PrettyPrinter implements RegExpTreeVisitor {
	
	StringBuffer buffer;
	
	public static String prettyPrint(RegExp regexp){
		PrettyPrinter pp = new PrettyPrinter();
		regexp.accept(pp);
		return pp.buffer.toString();
	}
	
	private PrettyPrinter(){
		this.buffer = new StringBuffer();
	}

	@Override
	public void visit(Alternative alternative) {
		this.buffer.append("(");
		alternative.getLhs().accept(this);
		this.buffer.append(")");
		this.buffer.append("|");
		this.buffer.append("(");
		alternative.getRhs().accept(this);
		this.buffer.append(")");
		
	}

	@Override
	public void visit(Star star) {
		this.buffer.append("(");
		star.getLhs().accept(this);
		this.buffer.append(")");
		this.buffer.append("*");
	}

	@Override
	public void visit(Plus plus) {
		this.buffer.append("(");
		plus.getLhs().accept(this);
		this.buffer.append(")");
		this.buffer.append("+");
	}

	@Override
	public void visit(SymbolClass symbolClass) {
		this.buffer.append("[");
		this.buffer.append(symbolClass.isNegated()?"^":"");
		for (Symbol symbol : symbolClass.getSymbols()) {
			symbol.accept(this);
		}
		this.buffer.append("]");
	}

	@Override
	public void visit(Symbol symbol) {
		this.buffer.append(symbol.getSymbol());
	}

	@Override
	public void visit(Concatenation concatenation) {
		concatenation.getLhs().accept(this);
		concatenation.getRhs().accept(this);
	}

	@Override
	public void visit(RegExp regexp) {
		// Do nothing
	}

	@Override
	public void visit(Repetition repetition) {
		// Do nothing
	}

	@Override
	public void visit(Dot dot) {
		this.buffer.append(".");
	}
	

}

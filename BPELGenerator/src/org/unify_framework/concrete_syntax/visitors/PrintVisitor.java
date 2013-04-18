package org.unify_framework.concrete_syntax.visitors;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.unify_framework.concrete_syntax.CsActivity;
import org.unify_framework.concrete_syntax.CsAndJoin;
import org.unify_framework.concrete_syntax.CsAndSplit;
import org.unify_framework.concrete_syntax.CsAtomicActivity;
import org.unify_framework.concrete_syntax.CsBasicTransition;
import org.unify_framework.concrete_syntax.CsCompositeActivity;
import org.unify_framework.concrete_syntax.CsEndEvent;
import org.unify_framework.concrete_syntax.CsStartEvent;
import org.unify_framework.concrete_syntax.CsTransition;

public class PrintVisitor implements ElementVisitor {
	
	private static final String ERROR_MESSAGE = "An error has occured while attempting to print the composite activity";
	
	private Writer writer;
	private int indentLevel;
	
	public PrintVisitor(OutputStream os) {
		
		this.writer = new BufferedWriter(new OutputStreamWriter(os));
		this.indentLevel = 0;
	}
	
	private void indent() {
		
		try {
			for (int i = 0; i < this.indentLevel * 4; i++) {
				this.writer.write(" ");
			}
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
	
	public void print(CsCompositeActivity compositeActivity) {
		
		try {
			compositeActivity.accept(this);
			this.writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsAndJoin andJoin) {
		
		try {
			int maximumLength = 0;
			for (String source : andJoin.getSources()) {
				int length = source.length();
				if (length > maximumLength) {
					maximumLength = length;
				}
			}
			indent();
			Iterator<String> iterator = andJoin.getSources().iterator();
			String source = iterator.next();
			String start = "AND-join : ";
			for (int i = 0; i < maximumLength - source.length(); i++) {
				start += " ";
			}
			start += source;
			this.writer.write(start + " ->|-> " + andJoin.getDestination() + "\n");
			while (iterator.hasNext()) {
				source = iterator.next();
				for (int i = 0; i < start.length() + this.indentLevel * 4 - source.length(); i++) {
					this.writer.write(" ");
				}
				this.writer.write(source + " ->|\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsAndSplit andSplit) {
		
		try {
			indent();
			String start = "AND-split : " + andSplit.getSource() + " ->";
			this.writer.write(start);
			Iterator<String> iterator = andSplit.getDestinations().iterator();
			while (iterator.hasNext()) {
				this.writer.write("|-> " + iterator.next() + "\n");
				if (iterator.hasNext()) {
					for (int i = 0; i < start.length() + this.indentLevel * 4; i++) {
						this.writer.write(" ");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsAtomicActivity atomicActivity) {
		
		try {
			indent();
			this.writer.write("Atomic activity : " + atomicActivity.getName() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsBasicTransition basicTransition) {
		
		try {
			indent();
			this.writer.write("Basic transition : " + basicTransition.getSource() + " -> " + basicTransition.getDestination() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsCompositeActivity compositeActivity) {
		
		try {
			indent();
			String start = "Composite activity : " + compositeActivity.getName() + " ";
			this.writer.write(start);
			for (int i = start.length(); i < 80 - this.indentLevel * 4; i++) {
				this.writer.write("=");
			}
			this.writer.write("\n");
			this.indentLevel++;
			compositeActivity.getStartEvent().accept(this);
			for (CsActivity child : compositeActivity.getChildren()) {
				child.accept(this);
			}
			compositeActivity.getEndEvent().accept(this);
			for (CsTransition transition : compositeActivity.getTransitions()) {
				transition.accept(this);
			}
			this.indentLevel--;
			indent();
			for (int i = 0; i < 80 - this.indentLevel * 4; i++) {
				this.writer.write("=");
			}
			this.writer.write("\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsEndEvent endEvent) {
		
		try {
			indent();
			this.writer.write("End event : " + endEvent.getName() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CsStartEvent startEvent) {
		
		try {
			indent();
			this.writer.write("Start event : " + startEvent.getName() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
}

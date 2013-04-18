package org.unify_framework.abstract_syntax.visitors;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlNode;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;

public class PrintVisitor implements ElementVisitor {
	
	private static final String ERROR_MESSAGE = "An error has occured while generating the workflow's text representation";
	
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
	
	public void print(CompositeActivity compositeActivity) {
		
		try {
			compositeActivity.accept(this);
			this.writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(AndJoin andJoin) {
		
		try {
			int maximumLength = 0;
			for (Node source : andJoin.getSourceNodes()) {
				int length = source.getName().length();
				if (length > maximumLength) {
					maximumLength = length;
				}
			}
			indent();
			Iterator<Node> iterator = andJoin.getSourceNodes().iterator();
			Node source = iterator.next();
			String start = "AND-join : ";
			for (int i = 0; i < maximumLength - source.getName().length(); i++) {
				start += " ";
			}
			start += source.getName();
			this.writer.write(start + " ->|-> " + andJoin.getDestinationNode().getName() + "\n");
			while (iterator.hasNext()) {
				source = iterator.next();
				for (int i = 0; i < start.length() + this.indentLevel * 4 - source.getName().length(); i++) {
					this.writer.write(" ");
				}
				this.writer.write(source.getName() + " ->|\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(AndSplit andSplit) {
		
		try {
			indent();
			String start = "AND-split : " + andSplit.getSourceNode().getName() + " ->";
			this.writer.write(start);
			Iterator<Node> iterator = andSplit.getDestinationNodes().iterator();
			Node destination;
			while (iterator.hasNext()) {
				destination = iterator.next();
				this.writer.write("|-> " + destination.getName() + "\n");
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
	public void visit(AtomicActivity activity) {
		
		try {
			indent();
			this.writer.write("Atomic activity : " + activity.getName() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(Transition transition) {
		
		try {
			Node source = transition.getSource().getParent();
			Node destination = transition.getDestination().getParent();
			if (source instanceof ControlNode || destination instanceof ControlNode) {
				return;
			} else {
				indent();
				this.writer.write("Transition : " + transition.getSource().getParent().getName() + " -> " + transition.getDestination().getParent().getName() + "\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(CompositeActivity compositeActivity) {
		
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
			for (Activity childActivity : compositeActivity.getActivities()) {
				childActivity.accept(this);
			}
			for (ControlNode controlNode : compositeActivity.getControlNodes()) {
				controlNode.accept(this);
			}
			compositeActivity.getEndEvent().accept(this);
			for (Transition transition : compositeActivity.getTransitions()) {
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
	public void visit(Composition composition) {
		
		// Do nothing
	}

	@Override
	public void visit(ControlInputPort controlInputPort) {
		
		// Do nothing
	}

	@Override
	public void visit(ControlOutputPort controlOutputPort) {
		
		// Do nothing
	}

	@Override
	public void visit(EndEvent endEvent) {
		
		try {
			indent();
			this.writer.write("End event : " + endEvent.getName() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(StartEvent startEvent) {
		
		try {
			indent();
			this.writer.write("Start event : " + startEvent.getName() + "\n");
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(XorJoin xorJoin) {
		
		try {
			int maximumLength = 0;
			for (Node source : xorJoin.getSourceNodes()) {
				int length = source.getName().length();
				if (length > maximumLength) {
					maximumLength = length;
				}
			}
			indent();
			Iterator<Node> iterator = xorJoin.getSourceNodes().iterator();
			Node source = iterator.next();
			String start = "XOR-join : ";
			for (int i = 0; i < maximumLength - source.getName().length(); i++) {
				start += " ";
			}
			start += source.getName();
			this.writer.write(start + " ->|-> " + xorJoin.getDestinationNode().getName() + "\n");
			while (iterator.hasNext()) {
				source = iterator.next();
				for (int i = 0; i < start.length() + this.indentLevel * 4 - source.getName().length(); i++) {
					this.writer.write(" ");
				}
				this.writer.write(source.getName() + " ->|\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(XorSplit xorSplit) {
		
		try {
			indent();
			String start = "XOR-split : " + xorSplit.getSourceNode().getName() + " ->";
			this.writer.write(start);
			Iterator<Node> iterator = xorSplit.getDestinationNodes().iterator();
			Node destination;
			while (iterator.hasNext()) {
				destination = iterator.next();
				this.writer.write("|-> " + destination.getName() + "\n");
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
}

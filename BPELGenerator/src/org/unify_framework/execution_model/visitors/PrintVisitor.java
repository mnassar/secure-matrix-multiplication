package org.unify_framework.execution_model.visitors;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.unify_framework.execution_model.EmActivityTransition;
import org.unify_framework.execution_model.EmAndJoinTransition;
import org.unify_framework.execution_model.EmAndSplitTransition;
import org.unify_framework.execution_model.EmEndPlace;
import org.unify_framework.execution_model.EmIntermediatePlace;
import org.unify_framework.execution_model.EmPetriNet;
import org.unify_framework.execution_model.EmPlace;
import org.unify_framework.execution_model.EmStartPlace;
import org.unify_framework.execution_model.EmTransition;

public class PrintVisitor implements ElementVisitor {
	
	private static final String ERROR_MESSAGE = "An error has occured while generating the Petri net's text representation";
	
	private Writer writer;
	
	public PrintVisitor(OutputStream os) {
		
		this.writer = new BufferedWriter(new OutputStreamWriter(os));
	}

	public void print(EmPetriNet petriNet) {
		
		petriNet.accept(this);
	}

	@Override
	public void visit(EmStartPlace place) {
		
		try {
			this.writer.write("Start place " + place.getName() + "\n");
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmEndPlace place) {
		
		try {
			this.writer.write("End place " + place.getName() + "\n");
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmIntermediatePlace place) {
		
		try {
			this.writer.write("Intermediate place " + place.getName() + "\n");
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmActivityTransition transition) {
		
		try {
			this.writer.write("Activity transition ");
			Iterator<EmPlace> iterator = transition.getInputPlaces().iterator();
			this.writer.write(iterator.next().getName());
			if (iterator.hasNext()) {
				throw new RuntimeException("An activity transition should only have one input place");
			}
			this.writer.write(" -> ");
			iterator = transition.getOutputPlaces().iterator();
			this.writer.write(iterator.next().getName());
			if (iterator.hasNext()) {
				throw new RuntimeException("An activity transition should only have one output place");
			}
			this.writer.write(" : " + transition.getName() + "\n");
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmAndJoinTransition transition) {
		
		try {
			this.writer.write("AND-join transition ");
			Iterator<EmPlace> iterator = transition.getInputPlaces().iterator();
			this.writer.write(iterator.next().getName());
			while (iterator.hasNext()) {
				this.writer.write(", " + iterator.next().getName());
			}
			this.writer.write(" -> ");
			iterator = transition.getOutputPlaces().iterator();
			this.writer.write(iterator.next().getName());
			while (iterator.hasNext()) {
				this.writer.write(", " + iterator.next().getName());
			}
			this.writer.write("\n");
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmAndSplitTransition transition) {
		
		try {
			this.writer.write("AND-split transition ");
			Iterator<EmPlace> iterator = transition.getInputPlaces().iterator();
			this.writer.write(iterator.next().getName());
			while (iterator.hasNext()) {
				this.writer.write(", " + iterator.next().getName());
			}
			this.writer.write(" -> ");
			iterator = transition.getOutputPlaces().iterator();
			this.writer.write(iterator.next().getName());
			while (iterator.hasNext()) {
				this.writer.write(", " + iterator.next().getName());
			}
			this.writer.write("\n");
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	@Override
	public void visit(EmPetriNet petriNet) {
		
		try {
			petriNet.getStartPlace().accept(this);
			for (EmIntermediatePlace place : petriNet.getIntermediatePlaces()) {
				place.accept(this);
			}
			petriNet.getEndPlace().accept(this);
			for (EmTransition transition : petriNet.getTransitions()) {
				transition.accept(this);
			}
			this.writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}
}

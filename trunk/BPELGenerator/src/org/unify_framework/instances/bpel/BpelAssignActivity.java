package org.unify_framework.instances.bpel;

import org.unify_framework.abstract_syntax.ControlPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelAssignActivity extends BpelAtomicActivity {
	
	private List<BpelCopy> copies = new ArrayList<BpelCopy>();
	
	public BpelAssignActivity(String name) {
		
		super(name);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public void addCopy(BpelCopy copy) {
		
		this.copies.add(copy);
	}
	
	public List<BpelCopy> getCopies() {
		
		return this.copies;
	}
	
	/**
	 * Returns a copy of the assign activity.
	 */
	@Override
	public BpelAssignActivity getCopy(Map<ControlPort, ControlPort> mapping) {
		
		BpelAssignActivity assignActivityCopy = new BpelAssignActivity(getName());
		for (BpelCopy copy : this.copies) {
			assignActivityCopy.addCopy(copy.getCopy());
		}
		mapping.put(getControlInputPort(), assignActivityCopy.getControlInputPort());
		mapping.put(getControlOutputPort(), assignActivityCopy.getControlOutputPort());
		return assignActivityCopy;
	}
}

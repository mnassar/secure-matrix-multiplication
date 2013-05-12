package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelCorrelation implements Element {

	
	private String initiate;
	private String direction =null;
	private String set;
	
	public BpelCorrelation(String init, String direction, String corr_set) {
		
		this.setInitiate(init);
		this.setDirection(direction);
		this.setSet(corr_set);
	}

	public BpelCorrelation(String init,  String corr_set) {
		
		this.setInitiate(init);

		this.setSet(corr_set);
	}
	/**
	 * @return the initiate
	 */
	public String getInitiate() {
		return initiate;
	}

	/**
	 * @param initiate the initiate to set
	 */
	public void setInitiate(String initiate) {
		this.initiate = initiate;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the set
	 */
	public String getSet() {
		return set;
	}

	/**
	 * @param set the set to set
	 */
	public void setSet(String set) {
		this.set = set;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	
	
	
	public BpelCorrelation getCopy() {
		
		return new BpelCorrelation(this.initiate, this.direction, this.set);
	}
}

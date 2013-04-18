package org.unify_framework.abstract_syntax;

/**
 * @author Niels Joncheere
 * 
 * @model
 */
public interface XorJoin extends Join {
	
	public XorSplit getCorrespondingXorSplit();
	
	public void setCorrespondingXorSplit(XorSplit correspondingXorSplit);
}

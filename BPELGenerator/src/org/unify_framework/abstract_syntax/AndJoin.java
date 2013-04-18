package org.unify_framework.abstract_syntax;

/**
 * Represents an AND-join.
 * 
 * <p>An AND-join <i>may</i> have a corresponding AND-split.</p>
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AndJoin.java 18296 2011-10-25 12:57:29Z njonchee $
 */
public interface AndJoin extends Join {
	
	/**
	 * @return The corresponding AND-split, if any (<code>null</code> otherwise)
	 */
	public AndSplit getCorrespondingAndSplit();
	public AndSplit getUnstructuredCorrespondingAndSplit();
	public void setCorrespondingAndSplit(AndSplit andSplit);
	public void setUnstructuredCorrespondingAndSplit(AndSplit andSplit);
}

package org.unify_framework.abstract_syntax;

/**
 * Represents an AND-split.
 * 
 * <p>An AND-split <i>may</i> have a corresponding AND-join.</p>
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: AndSplit.java 18296 2011-10-25 12:57:29Z njonchee $
 */
public interface AndSplit extends Split {
	
	/**
	 * @return The corresponding AND-join, if any (<code>null</code> otherwise)
	 */
	public AndJoin getCorrespondingAndJoin();
	public AndJoin getUnstructuredCorrespondingAndJoin();
	public void setCorrespondingAndJoin(AndJoin andJoin);
	public void setUnstructuredCorrespondingAndJoin(AndJoin andJoin);
}

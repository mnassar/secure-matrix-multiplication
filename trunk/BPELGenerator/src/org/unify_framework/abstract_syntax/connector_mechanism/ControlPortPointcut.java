package org.unify_framework.abstract_syntax.connector_mechanism;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: ControlPortPointcut.java 18294 2011-10-25 11:12:17Z njonchee $
 */
public class ControlPortPointcut {
	
	private QualifiedName qualifiedName;
	
	public ControlPortPointcut(QualifiedName qualifiedName) {
		
		this.qualifiedName = qualifiedName;
	}
	
	public QualifiedName getQualifiedName() {
		
		return this.qualifiedName;
	}
	
	@Override
	public String toString() {
		
		return "controlport(" + this.qualifiedName + ")";
	}
}

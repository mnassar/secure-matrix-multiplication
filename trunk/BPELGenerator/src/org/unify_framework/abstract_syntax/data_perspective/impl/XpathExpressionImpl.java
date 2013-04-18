package org.unify_framework.abstract_syntax.data_perspective.impl;

import java.util.HashMap;
import java.util.Map;

import org.unify_framework.abstract_syntax.data_perspective.Expression;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id$
 */
public class XpathExpressionImpl extends ExpressionImpl {
	
	private Map<String, String> namespaces = new HashMap<String, String>();
	
	// CONSTRUCTORS ////////////////////////////////////////////////////////////
	
	public XpathExpressionImpl(String expression) {
		
		super(expression);
	}
	
	// ACCESSORS ///////////////////////////////////////////////////////////////
	
	public Map<String, String> getNamespaces() {
		
		return namespaces;
	}
	
	// MUTATORS ////////////////////////////////////////////////////////////////
	
	public void addNamespace(String prefix, String uri) {
		
		namespaces.put(prefix, uri);
	}
	
	// OPERATIONS //////////////////////////////////////////////////////////////
	
	@Override
	public Expression getCopy() {
		
		XpathExpressionImpl copy = new XpathExpressionImpl(this.getExpression());
		copy.getNamespaces().putAll(this.getNamespaces());
		return copy;
	}
	
	@Override
	public Expression smallerThanConstant(int constant) {
		
		XpathExpressionImpl result = new XpathExpressionImpl(this.getExpression() + "<" + constant);
		result.getNamespaces().putAll(this.getNamespaces());
		return result;
	}
}

package org.unify_framework.instances.bpel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.unify_framework.instances.bpel.visitors.ElementVisitor;
import org.w3c.dom.Node;

public class BpelToVariable extends BpelTo {
	
	/* PRIVATE FIELDS *********************************************************/
	
	private String variable;
	private String part;
	private String query;
	private Node queryNode;
	
	private Map<String, String> namespaceDeclarations = new HashMap<String, String>();
	
	/* CONSTRUCTORS ***********************************************************/
	
	public BpelToVariable(String variable) {
		
		this.variable = variable;
	}
	
	/* GETTERS ****************************************************************/
	
	public String getVariable() {
		
		return this.variable;
	}
	
	public String getPart() {
		
		return this.part;
	}
	
	public String getQuery() {
		
		return this.query;
	}
	
public Node getQueryNode() {
		
		return this.queryNode;
	}
	public Map<String, String> getNamespaceDeclarations() {
		
		return this.namespaceDeclarations;
	}
	
	/* SETTERS ****************************************************************/
	
	public void setPart(String part) {
		
		this.part = part;
	}

	public void setQuery(String query) {
		
		this.query = query;
	}
	
public void setQueryNode(Node queryN) {
		
		this.queryNode = queryN;
	}
	/* PUBLIC METHODS *********************************************************/

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	public void addNamespaceDeclaration(String prefix, String uri) {
		
		this.namespaceDeclarations.put(prefix, uri);
	}
	
	@Override
	public BpelToVariable getCopy() {
		
		BpelToVariable copy = new BpelToVariable(this.variable);
		copy.setPart(this.part);
		copy.setQuery(this.query);
		copy.setQueryNode(this.queryNode);
		for (Entry<String, String> entry : this.namespaceDeclarations.entrySet()) {
			copy.addNamespaceDeclaration(entry.getKey(), entry.getValue());
		}
		return copy;
	}
}

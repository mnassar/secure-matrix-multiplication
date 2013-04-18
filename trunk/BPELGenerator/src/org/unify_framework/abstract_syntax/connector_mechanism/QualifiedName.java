package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.ArrayList;
import java.util.List;

public class QualifiedName {
	
	private List<String> identifiers;
	
	public QualifiedName() {
		
		this.identifiers = new ArrayList<String>();
	}
	
	public void addIdentifier(String identifier) {
		
		this.identifiers.add(identifier);
	}
	
	public List<String> getIdentifiers() {
		
		return this.identifiers;
	}
	
	@Override
	public String toString() {
		
		String result = this.identifiers.get(0);
		for (int i = 1; i < this.identifiers.size(); i++) {
			result += "." + this.identifiers.get(i);
		}
		return result;
	}
}

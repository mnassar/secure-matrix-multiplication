package org.unify_framework.instances.bpel;

import org.unify_framework.instances.bpel.visitors.Element;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

public class BpelImport implements Element {
	
	private String namespace;
	private String location;
	private String importType;
	
	public BpelImport(String namespace, String location, String importType) {
		
		this.namespace = namespace;
		this.location = location;
		this.importType = importType;
	}

	public String getNamespace() {
		
		return this.namespace;
	}

	public String getLocation() {
		
		return this.location;
	}

	public String getImportType() {
		
		return this.importType;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (object == this) {
			return true;
		} else if (object == null || object.getClass() != this.getClass()) {
			return false;
		} else {
			BpelImport bpelImport = (BpelImport) object;
			return (bpelImport.namespace == this.namespace || (bpelImport.namespace != null && bpelImport.namespace.equals(this.namespace)))
					&& (bpelImport.location == this.location || (bpelImport.location != null && bpelImport.location.equals(this.location)))
					&& (bpelImport.importType == this.importType || (bpelImport.importType != null && bpelImport.importType.equals(this.importType)));
		}
	}
	
	public BpelImport getCopy() {
		
		BpelImport copy = new BpelImport(this.namespace, this.location, this.importType);
		return copy;
	}
	
	@Override
	public int hashCode() {
		
		int hashCode = 7;
		hashCode = 31 * hashCode + (this.namespace == null ? 0 : this.namespace.hashCode());
		hashCode = 31 * hashCode + (this.location == null ? 0 : this.location.hashCode());
		hashCode = 31 * hashCode + (this.importType == null ? 0 : this.importType.hashCode());
		return hashCode;
	}
}

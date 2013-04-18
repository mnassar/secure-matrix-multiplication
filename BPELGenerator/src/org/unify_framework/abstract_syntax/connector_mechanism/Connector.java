package org.unify_framework.abstract_syntax.connector_mechanism;

import java.util.List;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: Connector.java 18346 2011-10-28 13:11:08Z njonchee $
 */
public abstract class Connector {
	
	private List<DataMapping> dataMappings;
	
	public List<DataMapping> getDataMappings() {
		
		return this.dataMappings;
	}
	
	public abstract String getName();
	
	public void setDataMappings(List<DataMapping> dataMappings) {
		
		this.dataMappings = dataMappings;
	}
}

package org.unify_framework.instances.bpel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;

/**
 * Represents a BPEL process in the Unify framework.
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpelProcess.java 12714 2010-07-22 13:53:16Z njonchee $
 */
public class BpelProcess extends BpelCompositeActivity {
	
	private static final Log log = LogFactory.getLog(BpelProcess.class);
	
	/* PRIVATE FIELDS *********************************************************/
	
	private String targetNamespace;
	private String queryLanguage;
	private String expressionLanguage;
	private String exitOnStandardFault;  //="yes"
	private String suppressJoinFailure; //="yes"
	
	private Map<String, String> namespaceDeclarations = new HashMap<String, String>();
	
	private List<BpelImport> imports = new ArrayList<BpelImport>();
	
	/* CONSTRUCTORS ***********************************************************/
	
	/**
	 * Creates a new BPEL process.
	 * 
	 * @param name The process's name
	 * @param targetNamespace The process's target namespace
	 */
	public BpelProcess(String name, String targetNamespace) {
		
		super(name);
		this.targetNamespace = targetNamespace;
	}
	
	/* GETTERS ****************************************************************/
	
	public String getTargetNamespace() {
		
		return this.targetNamespace;
	}
	
	public String getQueryLanguage() {
		
		return this.queryLanguage;
	}
	
	public String getExpressionLanguage() {
		
		return this.expressionLanguage;
	}
	
	public String getSuppressJoinFailure() {
		
		return this.suppressJoinFailure;
	}
	public String getExitOnStandardFault() {
	
		return this.exitOnStandardFault;
	}
	public Map<String, String> getNamespaceDeclarations() {
		
		return this.namespaceDeclarations;
	}
	
	public List<BpelImport> getImports() {
		
		return this.imports;
	}
	
	/* SETTERS ****************************************************************/
	
	public void setQueryLanguage(String queryLanguage) {
		
		this.queryLanguage = queryLanguage;
	}
	
	public void setExpressionLanguage(String expressionLanguage) {
		
		this.expressionLanguage = expressionLanguage;
	}
	
	public void setSuppressJoinFailure(String supressJoinFail) {
		
		this.suppressJoinFailure = supressJoinFail;
	}
	public void setExitOnStandardFault(String exitOnFail) {
	
		this.exitOnStandardFault = exitOnFail;
	}
	
	/* PUBLIC METHODS *********************************************************/

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public void addImport(BpelImport bpelImport) {
		
		boolean alreadyExists = false;
		for (BpelImport currentBpelImport : this.imports) {
			if (currentBpelImport.equals(bpelImport)) {
				alreadyExists = true;
				break;
			}
		}
		if (alreadyExists) {
			log.warn("BPEL import already exists; ignoring it");
		} else {
			this.imports.add(bpelImport);
		}
	}
	
	public void addNamespaceDeclaration(String prefix, String uri) {
		
		this.namespaceDeclarations.put(prefix, uri);
	}
}

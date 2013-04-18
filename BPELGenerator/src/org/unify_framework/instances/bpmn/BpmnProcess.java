package org.unify_framework.instances.bpmn;

import org.unify_framework.instances.bpmn.visitors.Element;
import org.unify_framework.instances.bpmn.visitors.ElementVisitor;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: BpmnProcess.java 18712 2011-12-02 11:51:41Z njonchee $
 */
public class BpmnProcess extends BpmnCompositeActivity implements Element {
	
	private String definitionsId;
	private String targetNamespace;
	private String typeLanguage;
	private String expressionLanguage;
	private String type;
	private boolean executable;
	
	public BpmnProcess(String name) {
		
		super(name);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}

	public String getDefinitionsId() {
		
		return definitionsId;
	}

	public String getTargetNamespace() {
		
		return targetNamespace;
	}

	public String getTypeLanguage() {
		
		return typeLanguage;
	}

	public String getExpressionLanguage() {
		
		return expressionLanguage;
	}

	public String getType() {
		
		return type;
	}

	public boolean isExecutable() {
		
		return executable;
	}

	public void setDefinitionsId(String definitionsId) {
		
		this.definitionsId = definitionsId;
	}

	public void setTargetNamespace(String targetNamespace) {
		
		this.targetNamespace = targetNamespace;
	}

	public void setTypeLanguage(String typeLanguage) {
		
		this.typeLanguage = typeLanguage;
	}

	public void setExpressionLanguage(String expressionLanguage) {
		
		this.expressionLanguage = expressionLanguage;
	}

	public void setType(String type) {
		
		this.type = type;
	}

	public void setExecutable(boolean executable) {
		
		this.executable = executable;
	}
}

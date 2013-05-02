package org.unify_framework.instances.bpel.visitors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.connector_mechanism.DataMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelCopyExpressionToExpressionActivity;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelEmptyActivity;
import org.unify_framework.instances.bpel.BpelFrom;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelFromVariable;
import org.unify_framework.instances.bpel.BpelGetValueFromDictionaryActivity;
import org.unify_framework.instances.bpel.BpelGetValueFromNestedDictionaryActivity;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInitializeDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelInitializeNestedDictionaryVariableActivity;
import org.unify_framework.instances.bpel.BpelCopyExpressionToVariableActivity;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelInvokeMonitoringServiceActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelSequence;
import org.unify_framework.instances.bpel.BpelThrowActivity;
import org.unify_framework.instances.bpel.BpelToExpression;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariableElement;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.BpelXorJoin;
import org.unify_framework.instances.bpel.BpelXorSplit;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;

import org.w3c.dom.Document;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: PerformDataMappingVisitor.java 20924 2012-07-04 12:45:48Z njonchee $
 */
public class PerformDataMappingVisitor implements ElementVisitor {
	
	private static final Log log = LogFactory.getLog(PerformDataMappingVisitor.class);
	
	private DataMapping dataMapping;
	private Activity joinpoint;
	
	public PerformDataMappingVisitor(DataMapping dataMapping, Activity joinpoint) {
		
		this.dataMapping = dataMapping;
		this.joinpoint = joinpoint;
	}
	
	public PerformDataMappingVisitor(DataMapping dataMapping) {
		
		this(dataMapping, null);
	}

	@Override
	public void visit(BpelAndSplit andSplit) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelAssignActivity assignActivity) {
		
		for (BpelCopy childCopy : assignActivity.getCopies()) {
			childCopy.accept(this);
		}
	}

	@Override
	public void visit(BpelCopy copy) {
		
		BpelFrom from = copy.getFrom();
		from.accept(this);
	}
	
	@Override
	public void visit(BpelCopyExpressionToExpressionActivity copyExpressionToExpressionActivity) {
		
		// Do nothing
	}
	
	@Override
	public void visit(BpelCopyExpressionToVariableActivity copyExpressionToVariableActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelFromExpression from) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelFromVariable from) {
		
		log.debug("Visiting from variable...");
		DataMapping.Variable lhs = this.dataMapping.getLhs();
		String variable = lhs.getVariable();
		String part = lhs.getPart();
		log.debug("Checking whether from variable '" + from.getVariable() + "' equals data mapping variable '" + variable + "'");
		if (from.getVariable().equals(variable)) {
			if (from.getPart() != null && from.getPart().equals(part)) {
				log.debug("The variable's part is '" + part + "'");
				// Perform data mapping:
				if (lhs.getQuery() == null || lhs.getQuery().equals("")) {
					try {
						DataMapping.Variable rhs = this.dataMapping.getRhs();
						log.debug("Performing data mapping '" + lhs + " = " + rhs + "'...");
						if (rhs instanceof DataMapping.StringConstant) {
							String literalText = ((DataMapping.StringConstant) rhs).getString();
							if (literalText.contains("{$thisJoinPoint.activityName}")) {
								if (this.joinpoint == null) {
									throw new RuntimeException("The joinpoint does not support the activityName property!");
								}
								String activityName = this.joinpoint.getName();
								literalText = literalText.replace("{$thisJoinPoint.activityName}", activityName);
							}
							DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
							DocumentBuilder db = dbf.newDocumentBuilder();
							Document document = db.newDocument();
							org.w3c.dom.Element literalElement = document.createElementNS(BpelSerializer.BPEL_NAMESPACE_URI, "literal");
							literalElement.appendChild(document.createTextNode(literalText));
							BpelFromExpression newFrom = new BpelFromExpression(literalElement);
							from.getParent().setFrom(newFrom);
						} else if (rhs instanceof DataMapping.TypeVariable) {
							DataMapping.TypeVariable rhsTypeVariable = (DataMapping.TypeVariable) rhs;
							BpelFromVariable newFrom = new BpelFromVariable(rhsTypeVariable.getVariable());
							String query = rhsTypeVariable.getQuery();
							if (!query.equals(""))
								newFrom.setQuery(query);
							String queryNamespacePrefix = rhsTypeVariable.getQueryNamespacePrefix();
							if (!queryNamespacePrefix.equals(""))
								newFrom.addNamespaceDeclaration(queryNamespacePrefix, rhsTypeVariable.getQueryNamespace());
							from.getParent().setFrom(newFrom);
						} else if (rhs instanceof DataMapping.MessageTypeVariable) {
							DataMapping.MessageTypeVariable rhsMessageTypeVariable = (DataMapping.MessageTypeVariable) rhs;
							BpelFromVariable newFrom = new BpelFromVariable(rhsMessageTypeVariable.getVariable());
							newFrom.setPart(rhsMessageTypeVariable.getPart());
							newFrom.setQuery(rhsMessageTypeVariable.getQuery());
							newFrom.addNamespaceDeclaration(rhsMessageTypeVariable.getQueryNamespacePrefix(), rhsMessageTypeVariable.getQueryNamespace());
							from.getParent().setFrom(newFrom);
						} else {
							throw new RuntimeException("NYI");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (lhs.getQuery() == null) {
						log.error("lhs.getQuery() = null");
					} else {
						log.error("lhs.getQuery() = \"" + lhs.getQuery() + "\"");
					}
					throw new RuntimeException("NYI");
				}
			} else {
				log.debug("The variable's part is null");
			}
		} else {
			// Do nothing
		}
	}
	
	@Override
	public void visit(BpelGetValueFromDictionaryActivity getValueFromDictionaryActivity) {
		
		// Do nothing
	}
	
	@Override
	public void visit(BpelGetValueFromNestedDictionaryActivity getValueFromNestedDictionaryActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelImport bpelImport) {
		
		// Do nothing
	}
	
	@Override
	public void visit(BpelInitializeDictionaryVariableActivity initializeDictionaryVariableActivity) {
		
		// Do nothing
	}
	
	@Override
	public void visit(BpelInitializeNestedDictionaryVariableActivity initializeNestedDictionaryVariableActivity) {
		
		// Do nothing
	}
	
	@Override
	public void visit(BpelInvokeMonitoringServiceActivity activity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelEmptyActivity emptyActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelInvokeActivity invokeActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelPartnerLink partnerLink) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelCorrelationSet corrSet) {
		
		// Do nothing
	}
	@Override
	public void visit(BpelProcess process) {
		
		// Do nothing
	}
	
	@Override
	public void visit(BpelReceiveActivity receiveActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelReplyActivity replyActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelScopeActivity scopeActivity) {
		
		log.debug("Visiting scope activity...");
		for (Activity childActivity : scopeActivity.getActivities()) {
			if (childActivity instanceof BpelAssignActivity) {
				((BpelAssignActivity) childActivity).accept(this);
			} else if (childActivity instanceof BpelScopeActivity) {
				((BpelScopeActivity) childActivity).accept(this);
			}
		}
	}

	@Override
	public void visit(BpelThrowActivity throwActivity) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelToExpression to) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelToVariable to) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelVariableElement variable) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelVariableMessageType variable) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelVariableType variable) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelXorJoin xorJoin) {
		
		// Do nothing
	}

	@Override
	public void visit(BpelXorSplit andSplit) {
		
		// Do nothing
	}

	public void visitScopeActivity(BpelScopeActivity scopeActivity) {
		
		visit(scopeActivity);
	}

	@Override
	public void visit(BpelCompositeReceiveActivity receiveActivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BpelCorrelation bpelCorrelation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BpelSequence sequence) {
		// TODO Auto-generated method stub
		
	}
}

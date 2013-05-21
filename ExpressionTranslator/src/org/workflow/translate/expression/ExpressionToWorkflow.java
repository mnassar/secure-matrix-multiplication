package org.workflow.translate.expression;

import org.lsmp.djep.matrixJep.MatrixJep;
import org.nfunk.jep.Node;

public class ExpressionToWorkflow {

	static MatrixJep j;
	public void initialise()
	{
		j = new MatrixJep();
		j.addStandardConstants();
		j.addStandardFunctions();
		j.addComplex();
		j.setAllowUndeclared(true);
		j.setImplicitMul(true);
		j.setAllowAssignment(true);
		((MatrixJep) j).addStandardDiffRules();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ExpressionToWorkflow exp_wf = new ExpressionToWorkflow();
		exp_wf.initialise();
		
		String expression = "A=B*C+D";
		
		Node exp_tree = j.parseExpression(expression);
		
		System.out.println("The root has "+ exp_tree.jjtGetNumChildren() +" children!");
		
		Node curr_node=  exp_tree;
		while(curr_node !=null && curr_node.jjtGetChild(0)!=null && curr_node.jjtGetChild(1)!=null)
		{
			
			Node left_node= curr_node.jjtGetChild(0);
			Node right_node= curr_node.jjtGetChild(1);
			
			System.out.println("Node "+ curr_node.toString() +" is the parent of "+left_node + " and " + right_node);
			curr_node = right_node;
		}
		
		
	}

}

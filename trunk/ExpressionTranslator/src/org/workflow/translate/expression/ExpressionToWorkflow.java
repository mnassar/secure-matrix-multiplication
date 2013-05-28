package org.workflow.translate.expression;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import org.bpel.deploy.FolderZipper;
import org.bpel.deploy.ProcessDeploy;
import org.lsmp.djep.matrixJep.MatrixJep;
import org.nfunk.jep.Node;

public class ExpressionToWorkflow {

	private MatrixJep j;
	private String job_ID;
	private Workflow exp_wf;
	private String expression;
	private ExpressionTranslator translator;
	private String broker_url;
	private MatrixMeta left_operand;
	private MatrixMeta right_operand;
	
	public ExpressionToWorkflow(ExpressionTranslator translator)
	{
		expression = new String(translator.getExpression());
		job_ID = new String(translator.getJob_ID());
		String expwf_process= "exp"+job_ID;
		exp_wf = new Workflow(expwf_process, "http://soc."+expwf_process+".workflow");
		this.translator = new ExpressionTranslator(translator);
		
		File conf = new File("/etc/soc/soc.conf");
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(conf));
 
			while ((sCurrentLine = br.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(sCurrentLine);
				String variable = tokenizer.nextToken("="); variable = variable.trim();
				String path = tokenizer.nextToken(); path  = path.trim();
			
				if(variable.equals("ODE_PATH")) exp_wf.setODE_PATH(path.substring(1,path.length()-1));
				if(variable.equals("WORKFLOWS_PATH")) exp_wf.setFolder_Path(path.substring(1,path.length()-1));
				if(variable.equals("ADDITIVE_SPLITTING_PROCESS"))  exp_wf.setAdditive_splitting_process( new String(path.substring(1,path.length()-1)));
				if(variable.equals("BROKER_URL"))  broker_url = new String(path.substring(1,path.length()-1));
				
				 
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
	}
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
	
	public void convert()
	{
		Node exp_tree = j.parseExpression(expression);
		//// Traverse the expression tree to create the workflow
		traverse(exp_tree);

		//SERIALIZE BPEL
		exp_wf.serialize();
		
		//**************************
		// SERIALIZE WSDL 
		
		
		
		
		
		//DEPLOY
		exp_wf.deploy(broker_url);
	
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String expression = "A*B+C*D";
		ExpressionTranslator translator = new ExpressionTranslator(expression);
		//**********************
				//This data should be read from the metadata store
				String key ="A";
				String[] storage_url = new String[4];
				storage_url[0] = new String("http://server1/matrix/A1");
				storage_url[1] = new String("http://server2/matrix/A2");
				storage_url[2] = new String("http://server3/matrix/A1");
				storage_url[3] = new String("http://server4/matrix/A2");
				
				MatrixMeta A = new MatrixMeta(key, "http://soc.org/matrix/A", 100, 100, (short)0, storage_url);
				translator.addExpressionVariable(key, A);
				
				key ="B";
				storage_url = new String[4];
				storage_url[0] = new String("http://server1/matrix/B1");
				storage_url[1] = new String("http://server2/matrix/B2");
				storage_url[2] = new String("http://server3/matrix/B1");
				storage_url[3] = new String("http://server4/matrix/B2");
				
				MatrixMeta B = new MatrixMeta(key, "http://soc.org/matrix/B", 100, 100, (short)0, storage_url);
				translator.addExpressionVariable(key, B);
				
				key ="C";
				storage_url = new String[4];
				storage_url[0] = new String("http://server1/matrix/C1");
				storage_url[1] = new String("http://server2/matrix/C2");
				storage_url[2] = new String("http://server3/matrix/C1");
				storage_url[3] = new String("http://server4/matrix/C2");
				
				MatrixMeta C = new MatrixMeta(key, "http://soc.org/matrix/C", 100, 100, (short)0, storage_url);
				translator.addExpressionVariable(key, C);
				
				key ="D";
				storage_url = new String[4];
				storage_url[0] = new String("http://server1/matrix/D1");
				storage_url[1] = new String("http://server2/matrix/D2");
				storage_url[2] = new String("http://server3/matrix/D1");
				storage_url[3] = new String("http://server4/matrix/D2");
				
				MatrixMeta D = new MatrixMeta(key, "http://soc.org/matrix/D", 100, 100, (short)0, storage_url);
				translator.addExpressionVariable(key, D);
				
		ExpressionToWorkflow expTowf = new ExpressionToWorkflow(translator);
		expTowf.initialise();
		expTowf.convert();
	}
	
	public  void traverse(Node tree)
	{
		Node curr_node=  tree;
		Node left_node= null;
		Node right_node= null;
		if(curr_node.jjtGetNumChildren() == 0)
		{
	
			System.out.println("Node "+ curr_node.toString() +" is a leaf node ");
			if(curr_node.jjtGetParent().jjtGetNumChildren()==1)  //Unary operator
			{
				String var_key= new String(curr_node.toString().substring(11, curr_node.toString().length()-1));
				left_operand = translator.getExpressionVariable(var_key);
				right_operand = null;
			}
			else //Binary operator
			{
				if(curr_node.jjtGetParent().jjtGetChild(1) !=curr_node )
				{
					//may need adjustment after adding metadata store
				
					String var_key= new String(curr_node.toString().substring(11, curr_node.toString().length()-1));
					
					left_operand = translator.getExpressionVariable(var_key);
					System.out.println(left_operand.getId() + " is the left operand!");
					
				}
				if(curr_node.jjtGetParent().jjtGetChild(0) !=curr_node)
				{
					
					//may need adjustment after addign metadata store
					String var_key=  new String(curr_node.toString().substring(11, curr_node.toString().length()-1));
					
					right_operand = translator.getExpressionVariable(var_key);
					System.out.println(right_operand.getId() + " is the right operand!");
				}
			
			}
				
			//exp_wf.addMessageVariable(var_name, var_type);
			return;
		}		
		else if(curr_node.jjtGetNumChildren()==2)
		{
			left_node= curr_node.jjtGetChild(0);
			right_node= curr_node.jjtGetChild(1);
		}
		else
		{
			left_node= curr_node.jjtGetChild(0);
		}
		
		if(left_node !=null)
		{
			traverse(left_node);
			//System.out.println("Visiting Node "+ left_node.toString() );
		}
		if(right_node !=null)
		{
			traverse(right_node);
			//System.out.println("Visiting Node "+ right_node.toString() );
		}	
			System.out.println("Visiting Node "+ curr_node.toString() );
			
			//If the children are not leaves i.e. they are not variables but operators
			//Then create a flow and get children corresponding activities to be added to the flow
			
			//SWITCH current_node operator --> binary or unary 
			
			if(curr_node.jjtGetNumChildren()==1)
			{
				//Unary operator
			}
			else
			{
				//Create ASSIGN Activity 
				//Assign 
				//<q0:WF_ProcessRequest>
				//<q0:input/>
				//<q0:matA/>
				//<q0:matB/>
				//</q0:WF_ProcessRequest>
				/*
				 * "<bpel:literal><tns:WF_ProcessResponse xmlns:tns=\"http://matrix.bpelprocess\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				  "<tns:result>tns:result</tns:result>"+
				  "<tns:instanceID>tns:instanceID</tns:instanceID>"+
				  "</tns:WF_ProcessResponse></bpel:literal>"
				 */
				//ADD INVOKE and Callback RECEIVE activities
				if(curr_node.toString().contains("*"))
				{
					if(left_operand.getProtocol()== MatrixMeta.ADDITIVE_SPLITTING)
					{
					//assign additive splitting message request
						
					//Invoke additive splitting process  
					}
					//else OTHER PROTOCOLS
					//receive callback
				}
				else if(curr_node.toString().contains("+"))
				{
					//assign add broker message request
					//Invoke broker webservice
					//receive callback
				}

			}
		
	}

}

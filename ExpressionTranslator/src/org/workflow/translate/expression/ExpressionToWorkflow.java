package org.workflow.translate.expression;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.bpel.deploy.FolderZipper;
import org.bpel.deploy.ProcessDeploy;
import org.lsmp.djep.matrixJep.MatrixJep;
import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.xml.sax.SAXException;

public class ExpressionToWorkflow {

	private MatrixJep j;
	private String job_ID;
	private Workflow exp_wf;
	private String expression;
	private ExpressionTranslator translator;
	private String broker_url;
	private MatrixMeta left_operand;
	private MatrixMeta right_operand;
	private Random sub_wf_job;
	private int assign_counter;
	private int invoke_counter;
	private int receive_counter;
	private int namespace_counter;
	private ArrayList<Integer> flow_stack;
	private int current_flow;
	private int flow_counter;
	
	public ExpressionToWorkflow(ExpressionTranslator translator)
	{
		expression = new String(translator.getExpression());
		job_ID = new String(translator.getJob_ID());
		String expwf_process= "exp"+job_ID;
		exp_wf = new Workflow(expwf_process, "http://soc."+expwf_process+".workflow");
		this.translator = new ExpressionTranslator(translator);
		
		sub_wf_job = new Random();
		assign_counter = 0;
		invoke_counter = 0;
		receive_counter = 0;
		namespace_counter = 0;
		current_flow = 0;
		flow_counter= 0;
		flow_stack = new ArrayList<Integer>();
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
				if(variable.equals("ADDITIVE_SPLITTING_PROCESS"))  exp_wf.setAdditive_splitting_url( new String(path.substring(1,path.length()-1)));
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
	
	public void convert() throws ParserConfigurationException, SAXException, IOException, ParseException
	{
		Node exp_tree = j.parse(expression);
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

		String expression = "(A*B+C*D)+(A*D+B*C)";
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
		try {
			expTowf.convert();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void traverse(Node tree) throws ParserConfigurationException, SAXException, IOException
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
			else //Two operands for the Binary operator
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
		else if(curr_node.jjtGetNumChildren()==2) //Binary operator
		{
			left_node= curr_node.jjtGetChild(0);
			right_node= curr_node.jjtGetChild(1);
			if(curr_node.jjtGetChild(0).getClass() == ASTFunNode.class && curr_node.jjtGetChild(1).getClass() == ASTFunNode.class )
			{
				flow_counter += 1;
			    flow_stack.add(current_flow++, flow_counter);
			    exp_wf.addParallelflow(flow_counter);
				if(flow_counter>1)
				{	
					//current_flow++;
					exp_wf.connectFlowToFlow(flow_counter, flow_stack.get(current_flow -2 ));
					
				}
				
			}
			
		}
		else
		{
			left_node= curr_node.jjtGetChild(0);
			if(curr_node.jjtGetChild(0).getClass() == ASTFunNode.class )
			{
				flow_counter += 1;
				flow_stack.add(current_flow++, flow_counter);
				exp_wf.addParallelflow(flow_counter);
				if(flow_counter>1)
				{
					//current_flow++;
					exp_wf.connectFlowToFlow(flow_counter, flow_stack.get(current_flow-2));
					
				}
				
			}
			
		}
		
		if(left_node !=null)
		{
			traverse(left_node);
			//System.out.println("Visiting Node "+ left_node.toString() );
		}
		if(right_node !=null)
		{
			
			traverse(right_node);
			//if(curr_node.jjtGetChild(0).getClass() == ASTFunNode.class  && curr_node.jjtGetChild(1).getClass() == ASTFunNode.class )
			//{
			//	current_flow -= 1;
			//}
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
				//ADD INVOKE and Callback RECEIVE activities
				if(curr_node.toString().contains("*"))
				{
					if(left_operand.getProtocol()== MatrixMeta.ADDITIVE_SPLITTING)
					{
						//boolean added =exp_wf.addNamespace("ns"+namespace_counter, exp_wf.getAdditive_splitting_process().getTargetNamespace());
						//if(added== true) namespace_counter++;
					//assign additive splitting message request
						String request_str= "<bpel:literal><tns:WF_ProcessRequest xmlns:tns=\""+exp_wf.getAdditiveSplitting_namespace()+"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
								  "<tns:jobID>"+exp_wf.getWf_name()+"</tns:jobID>"+
								  "<tns:sub_jobID>"+UUID.randomUUID().toString()+"</tns:sub_jobID>"+
								  "<tns:matA>"+left_operand.getId()+"</tns:matA>"+
								  "<tns:matB>"+right_operand.getId()+"</tns:matB>"+
								  "</tns:WF_ProcessRequest></bpel:literal>";
						
						exp_wf.addMessageVariable("var"+assign_counter, "ns1:WF_ProcessRequestMessage"); // reading variable type may be done from parsing the additive splitting bpel file and getting the variable type but this would be complicated and would take time, while they are not supposed to change 
						exp_wf.addAssign("Assign"+assign_counter, request_str, "var"+assign_counter);
						
					//Invoke additive splitting process  
						
						exp_wf.addInvokeActivity("Invoke"+invoke_counter, "process", exp_wf.AdditiveSplitting_PL.getName(), "ns1:WF_Process", "var"+assign_counter);
						
					}
					//else OTHER PROTOCOLS
			///
					///
				///	
					
					
					
					exp_wf.addMessageVariable("var"+assign_counter, "tns:callback"+receive_counter+"Request");   
					
					//receive callback
					exp_wf.addCallbackActivity("Callback"+receive_counter, "callback"+receive_counter, "CB_"+receive_counter+"_PL", "tns:"+exp_wf.getWf_name(), "var"+assign_counter);
					
					exp_wf.connect("Assign"+assign_counter, "Invoke"+invoke_counter);
					exp_wf.connect("Invoke"+invoke_counter, "Callback"+receive_counter );
					//exp_wf.connectToFlow(current_flow, "Assign"+assign_counter, "Callback"+receive_counter);
					exp_wf.connectToLastNode("Assign"+assign_counter);
					if(current_flow >0)
						exp_wf.connectToFlow(flow_stack.get(current_flow-1), "Callback"+receive_counter );
					else
						exp_wf.connectToEnd("Callback"+receive_counter );
					
					assign_counter++;
					invoke_counter++;
					receive_counter++;
				
					
				}
				else if(curr_node.toString().contains("+"))
				{
					//assign add broker message request
					
					String request_str= "<bpel:literal><tns:compute xmlns:tns=\""+exp_wf.getBroker_namespace()+ "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                     +"<operation>add</operation>"
                     +"<op_id>"+current_flow +"</op_id>"
                     +"<job_id>"+exp_wf.getWf_name() +"</job_id>"
                     + "<matA_ID>"+left_operand.getId()+"</matA_ID>"
                     +"<matB_ID>"+right_operand.getId()+"</matB_ID>"
                     +"<callback>addResult</callback>"
                     +"</tns:compute></bpel:literal>";
					

					exp_wf.addMessageVariable("var"+assign_counter, "ns2:computeRequest"); // reading variable type may be done from parsing the additive splitting bpel file and getting the variable type but this would be complicated and would take time, while they are not supposed to change 
					exp_wf.addAssign("Assign"+assign_counter, request_str, "var"+assign_counter);
			
					
					//Invoke broker webservice		
					exp_wf.addInvokeActivity("Invoke"+invoke_counter, "process", exp_wf.Broker_PL.getName(), "ns2:Broker_Services", "var"+ assign_counter);
			
					//receive callback
					exp_wf.addMessageVariable("var"+assign_counter, "tns:callback"+receive_counter+"Request");   
					
					//receive callback
					exp_wf.addCallbackActivity("Callback"+receive_counter, "callback"+receive_counter, "CB_"+receive_counter+"_PL", "tns:"+exp_wf.getWf_name(), "var"+assign_counter);
					
					exp_wf.connect("Assign"+assign_counter, "Invoke"+invoke_counter);
					exp_wf.connect("Invoke"+invoke_counter, "Callback"+receive_counter );
					//exp_wf.connectBetweenFlow(current_flow, 1, "Assign"+assign_counter, "Callback"+receive_counter);
					
					exp_wf.connectToLastNode("Assign"+assign_counter);
					if(current_flow >0)
						exp_wf.connectToFlow(flow_stack.get(current_flow-1), "Callback"+receive_counter );
					else
						exp_wf.connectToEnd("Callback"+receive_counter );
					
					assign_counter++;
					invoke_counter++;
					receive_counter++;
				
				}
			/*	if(curr_node.jjtGetChild(0).getClass() == ASTFunNode.class  && curr_node.jjtGetChild(1).getClass() == ASTFunNode.class )
				{
					current_flow -= 1;
				}*/
				
				if(curr_node.jjtGetParent().jjtGetChild(0) == curr_node) //I am the left hand side of the another expression
				{
					//update left operand with the result  ID returned and get corresponding data from metadata store 
					
			//		left_operand = new 
				}
				else if(curr_node.jjtGetParent().jjtGetChild(1) == curr_node) //I am the right hand side of the another expression
				{
					//update right operand with the result  ID returned and get corresponding data from metadata store 
					
			//		right_operand = new 
					
					//pop the flow on top of stack
					if(current_flow >0)
					{
						exp_wf.updateLastNode("FlowJoin"+flow_stack.get(current_flow-1));
						
						current_flow--;
					}
					else
					{
						//exp_wf.connec
					}
					
				}


			}
		
	}

}

package broker.workflow.translate.expression;


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

import matrix.splitters.AdditiveSplitter;

import org.bpel.deploy.FolderZipper;
import org.bpel.deploy.ProcessDeploy;
import org.lsmp.djep.matrixJep.MatrixJep;
import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import broker.BrokerSOCResource;
import broker.Location;
import broker.MetadataStoreConnection;
import broker.SOCConfiguration;
import broker.StorageProtocol;

public class ExpressionToWorkflow {

	private MatrixJep j;
	private String job_ID;
	private Workflow exp_wf;
	private String expression;
	private ExpressionTranslator translator;
	private String broker_url;
	private BrokerSOCResource left_operand;
	private BrokerSOCResource right_operand;
	private Random sub_wf_job;
	private int assign_counter;
	private int var_counter;
	private int invoke_counter;
	private int receive_counter;
	private int namespace_counter;
	private ArrayList<Integer> flow_stack;
	private int current_flow;
	private int flow_counter;
	
	private WSDLGenerator wsdl_gen;
	private DeployXML deploy_xml;
	
	public String getProcessURL()
	{
		return SOCConfiguration.BROKER_URL+"/ode/processes/"+ exp_wf.getWf_name();
	}
	
	public String getWorkflowNamespace()
	{
		return "http://soc."+ exp_wf.getWf_name()+".workflow";
	}
	public ExpressionToWorkflow(ExpressionTranslator translator) 
	{
		
		expression = new String(translator.getExpression());
		job_ID = new String(translator.getJob_ID());
		String expwf_process= "exp"+job_ID;
		
		SOCConfiguration soc_conf = new SOCConfiguration();
		
		exp_wf = new Workflow(expwf_process, "http://soc."+expwf_process+".workflow");
		
		exp_wf.setODE_PATH(SOCConfiguration.ODE_PATH);
		exp_wf.setFolder_Path(SOCConfiguration.WORKFLOWS_PATH);
		exp_wf.setAdditive_splitting_url(SOCConfiguration.ADDITIVE_SPLITTING_PROCESS);
		broker_url = new String(SOCConfiguration.BROKER_URL);
		exp_wf.setBroker_services_url(broker_url);
		
		this.translator = new ExpressionTranslator(translator);
	
		sub_wf_job = new Random();
		assign_counter = 0;
		var_counter=0;
		invoke_counter = 0;
		receive_counter = 0;
		namespace_counter = 0;
		current_flow = 0;
		flow_counter= 0;
		flow_stack = new ArrayList<Integer>();
	
		
		/*
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
				if(variable.equals("BROKER_URL"))  {
					broker_url = new String(path.substring(1,path.length()-1));
					exp_wf.setBroker_services_url(broker_url);
				}
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
	*/
		
		try {
			exp_wf.initialize();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.wsdl_gen = new WSDLGenerator("http://soc."+expwf_process+".workflow", expwf_process);
		
	//	wsdl_gen.initialize(exp_wf);
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
		wsdl_gen.initialize(exp_wf);
		wsdl_gen.write(exp_wf.getFolder_Path()+"/"+exp_wf.getWf_name()+"/"+exp_wf.getWf_name()+"Artifacts.wsdl");
		
		
		//DEPLOY
		deploy_xml = new DeployXML(exp_wf);
		deploy_xml.addPartnerLinks();
		deploy_xml.write();
		
		exp_wf.deploy(broker_url);
	
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub

	//	String expression = "(A*B+C*D)+(A*D+ B*C)+(A*C+B*D)";
		String expression = "A*B +C*D";
		ExpressionTranslator translator = new ExpressionTranslator(expression);
		//**********************
				//read data from the metadata store
				
		//***************************
		
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
					System.out.println(left_operand.getResource_id() + " is the left operand!");
					
				}
				if(curr_node.jjtGetParent().jjtGetChild(0) !=curr_node)
				{
					
					//may need adjustment after addign metadata store
					String var_key=  new String(curr_node.toString().substring(11, curr_node.toString().length()-1));
					
					right_operand = translator.getExpressionVariable(var_key);
					System.out.println(right_operand.getResource_id()+ " is the right operand!");
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
					//****
					//for testing only .. this should be updated and added to broker services 
					MetadataStoreConnection conn;
					try {
						conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
						BrokerSOCResource result = new BrokerSOCResource(left_operand.getResource_id()+right_operand.getResource_id());
						result.setFile_path(SOCConfiguration.BROKER_STORAGE_PATH+"/"+result.getResource_id());
						AdditiveSplitter splitter = new AdditiveSplitter(result);
						SOCConfiguration conf = new SOCConfiguration();
						Location loc_split1 = conf.getRandomCloud();
						Location loc_split2 ;
						while((loc_split2= conf.getRandomCloud())!=loc_split1);
						//splitter.Split(loc_split1, loc_split2);
						result.addLocation(loc_split1);
						result.addLocation(loc_split2);
						boolean added = conn.addSOCResource(result);
						
						conn.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//*************************88 for testing only ***************
					
					if(left_operand.getStorage_protocol() == StorageProtocol.ADDITIVE_SPLITTING)
					{
					//**************************8
						//check if resplitting is needed : is done now in the additive splitting protocol itself
						///**************************8
						
						//boolean added =exp_wf.addNamespace("ns"+namespace_counter, exp_wf.getAdditive_splitting_process().getTargetNamespace());
						//if(added== true) namespace_counter++;
					//assign additive splitting message request
						String request_str= "<bpel:literal><tns:AdditiveSplittingRequest xmlns:tns=\""+exp_wf.getAdditiveSplitting_namespace()+"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
								  "<tns:jobID>"+exp_wf.getWf_name()+"</tns:jobID>"+
								  "<tns:sub_jobID>"+UUID.randomUUID().toString()+"</tns:sub_jobID>"+
								  "<tns:matA>"+left_operand.getResource_id()+"</tns:matA>"+
								  "<tns:matB>"+right_operand.getResource_id()+"</tns:matB>"+
								  "</tns:AdditiveSplittingRequest></bpel:literal>";
					
						int input = var_counter;
						exp_wf.addMessageVariable("var"+input, "ns1:AdditiveSplittingRequestMessage"); // reading variable type may be done from parsing the additive splitting bpel file and getting the variable type but this would be complicated and would take time, while they are not supposed to change 
						exp_wf.addAssign("Assign"+assign_counter, request_str, "var"+input);
						
					
						int output = ++var_counter ;
						exp_wf.addMessageVariable("var"+output, "ns1:AdditiveSplittingResponseMessage");   
					
						
					//Invoke additive splitting process   activ_name, operation, pl, portType, input
						
						exp_wf.addInvokeActivity("Invoke"+invoke_counter, "initiate", exp_wf.AdditiveSplitting_PL.getName(), "ns1:AdditiveSplitting", "var"+input, "var"+output);
						
						
					}
					//else OTHER PROTOCOLS
			///
				
					
					
					
					
					//receive callback
					//exp_wf.addCallbackActivity("Callback"+receive_counter, "callback"+receive_counter, "CALLBACK_PL", "tns:"+exp_wf.getWf_name(), "var"+var_counter);
					
					
					exp_wf.connect("Assign"+assign_counter, "Invoke"+invoke_counter);
					//exp_wf.connect("Invoke"+invoke_counter, "Callback"+receive_counter );
					//////exp_wf.connectToFlow(current_flow, "Assign"+assign_counter, "Callback"+receive_counter);
					exp_wf.connectToLastNode("Assign"+assign_counter);
					if(current_flow >0)
						exp_wf.connectToFlow(flow_stack.get(current_flow-1), "Invoke"+invoke_counter );
					else
						exp_wf.connectToEnd("Invoke"+invoke_counter );
					
					assign_counter++;
					invoke_counter++;
					receive_counter++;
					var_counter++;
					
				}
				else if(curr_node.toString().contains("+"))
				{
					
					//****
					//for testing only .. this should be updated and added to broker services 
					MetadataStoreConnection conn;
					try {
						conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
						BrokerSOCResource result = new BrokerSOCResource(left_operand.getResource_id()+right_operand.getResource_id());
						result.setFile_path(SOCConfiguration.BROKER_STORAGE_PATH+"/"+result.getResource_id());
						AdditiveSplitter splitter = new AdditiveSplitter(result);
						SOCConfiguration conf = new SOCConfiguration();
						Location loc_split1 = conf.getRandomCloud();
						Location loc_split2 ;
						while((loc_split2= conf.getRandomCloud())!=loc_split1);
						//splitter.Split(loc_split1, loc_split2);
						result.addLocation(loc_split1);
						result.addLocation(loc_split2);
						boolean added = conn.addSOCResource(result);
						
						conn.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//************************* for testing only ***************
					
					//assign add broker message request
					
					String request_str= "<bpel:literal><tns:add xmlns:tns=\""+exp_wf.getBroker_namespace()+ "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                     +"<op_id>"+current_flow +"</op_id>"
                     +"<job_id>"+exp_wf.getWf_name() +"</job_id>"
                     + "<matA_ID>"+left_operand.getResource_id()+"</matA_ID>"
                     +"<matB_ID>"+right_operand.getResource_id()+"</matB_ID>"
                     +"<callback>"+"Callback"+receive_counter+"</callback>"
                     +"</tns:add></bpel:literal>";
					
/*
					 * <ws:add xmlns:ws="http://www.brokerservices.org/MatServ/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <op_id>5</op_id>
  <job_id>job_id</job_id>
  <matA_ID>matA_ID</matA_ID>
  <matB_ID>matB_ID</matB_ID>
  <callback>callBack_Operation1</callback>
</ws:add>
					 * 
*/
					
                    int input = var_counter;
					exp_wf.addMessageVariable("var"+input, "ns2:addRequest"); // reading variable type may be done from parsing the additive splitting bpel file and getting the variable type but this would be complicated and would take time, while they are not supposed to change 
					exp_wf.addAssign("Assign"+assign_counter, request_str, "var"+input);
			
					int output = ++var_counter;
					exp_wf.addMessageVariable("var"+output, "ns2:addResponse");   
					//Invoke broker webservice		
					exp_wf.addInvokeActivity("Invoke"+invoke_counter, "add", exp_wf.Broker_PL.getName(), "ns2:BrokerServices", "var"+ input,  "var"+ output );
			
					
					
					
					//receive callback
					//exp_wf.addCallbackActivity("Callback"+receive_counter, "callback"+receive_counter, "CALLBACK_PL", "tns:"+exp_wf.getWf_name(), "var"+assign_counter);
					
					exp_wf.connect("Assign"+assign_counter, "Invoke"+invoke_counter);
					
					//exp_wf.connect("Invoke"+invoke_counter, "Callback"+receive_counter );
				///	//exp_wf.connectBetweenFlow(current_flow, 1, "Assign"+assign_counter, "Callback"+receive_counter);
					
					exp_wf.connectToLastNode("Assign"+assign_counter);
					if(current_flow >0)
						exp_wf.connectToFlow(flow_stack.get(current_flow-1), "Invoke"+invoke_counter );
					else
						exp_wf.connectToEnd("Invoke"+invoke_counter );
					
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
					MetadataStoreConnection conn;
					try {
						conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
						String res = conn.getSOCResource(left_operand.getResource_id()+right_operand.getResource_id());
						Gson gson = new Gson();
						left_operand = gson.fromJson(res, BrokerSOCResource.class); 
						conn.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else if(curr_node.jjtGetParent().jjtGetChild(1) == curr_node) //I am the right hand side of the another expression
				{
					//update right operand with the result  ID returned and get corresponding data from metadata store 
					
					MetadataStoreConnection conn;
					try {
						conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
						String res = conn.getSOCResource(left_operand.getResource_id()+right_operand.getResource_id());
						Gson gson = new Gson();
						right_operand = gson.fromJson(res, BrokerSOCResource.class); 
						conn.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//pop the flow on top of stack
					if(current_flow >0)
					{
						exp_wf.updateLastNode("FlowJoin"+flow_stack.get(current_flow-1));
						
						current_flow--;
					}
					
					
				}


			}
		
	}

	public String getJob_ID() {
		return job_ID;
	}

	public void setJob_ID(String job_ID) {
		this.job_ID = job_ID;
	}

	public Workflow getExp_wf() {
		return exp_wf;
	}

	public void setExp_wf(Workflow exp_wf) {
		this.exp_wf = exp_wf;
	}

}

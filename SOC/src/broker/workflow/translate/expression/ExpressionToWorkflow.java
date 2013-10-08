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
import com.google.gson.GsonBuilder;

import broker.BrokerSOCResource;
import broker.Location;
import broker.MetadataStoreConnection;
import broker.ResourceMeta;
import broker.ResourceMetaAdapter;
import broker.SOCConfiguration;
import broker.SOCJob;
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
	private ArrayList<BrokerSOCResource> result_stack;
	private int tos;
	private int nInStack =0;
	private String result_matrix_ID;
	private Random sub_wf_job;
	private int assign_counter;
	private int var_counter;
	private int invoke_counter;
	private int receive_counter;
	private int namespace_counter;
	private ArrayList<Integer> flow_stack;
	private int current_flow;
	private int flow_counter;
	private int curr_scope;
	private WSDLGenerator wsdl_gen;
	private DeployXML deploy_xml;

	public String getProcessURL() {
		return SOCConfiguration.BROKER_URL + "/ode/processes/"
				+ exp_wf.getWf_name();
	}

	public String getWorkflowNamespace() {
		return "http://soc." + exp_wf.getWf_name() + ".workflow";
	}

	public ExpressionToWorkflow(ExpressionTranslator translator) {

		expression = new String(translator.getExpression());
		job_ID = new String(translator.getJob_ID());
		String expwf_process = "expjob_" + job_ID;

		SOCConfiguration soc_conf = new SOCConfiguration();

		exp_wf = new Workflow(expwf_process, "http://soc." + expwf_process
				+ ".workflow");

		exp_wf.setODE_PATH(SOCConfiguration.ODE_PATH);
		exp_wf.setFolder_Path(SOCConfiguration.WORKFLOWS_PATH);
		exp_wf.setAdditive_splitting_url(SOCConfiguration.ADDITIVE_SPLITTING_PROCESS);
		broker_url = new String(SOCConfiguration.BROKER_URL);
		exp_wf.setBroker_services_url(broker_url);

		this.translator = new ExpressionTranslator(translator);

		sub_wf_job = new Random();
		assign_counter = 0;
		var_counter = 0;
		invoke_counter = 0;
		receive_counter = 0;
		namespace_counter = 0;
		current_flow = 0;
		flow_counter = 0;
		curr_scope = 0;
		flow_stack = new ArrayList<Integer>();
		result_stack = new ArrayList<BrokerSOCResource>();
		tos=-1; 
		/*
		 * File conf = new File("/etc/soc/soc.conf");
		 * 
		 * BufferedReader br = null;
		 * 
		 * try {
		 * 
		 * String sCurrentLine;
		 * 
		 * br = new BufferedReader(new FileReader(conf));
		 * 
		 * while ((sCurrentLine = br.readLine()) != null) { StringTokenizer
		 * tokenizer = new StringTokenizer(sCurrentLine); String variable =
		 * tokenizer.nextToken("="); variable = variable.trim(); String path =
		 * tokenizer.nextToken(); path = path.trim();
		 * 
		 * if(variable.equals("ODE_PATH"))
		 * exp_wf.setODE_PATH(path.substring(1,path.length()-1));
		 * if(variable.equals("WORKFLOWS_PATH"))
		 * exp_wf.setFolder_Path(path.substring(1,path.length()-1));
		 * if(variable.equals("ADDITIVE_SPLITTING_PROCESS"))
		 * exp_wf.setAdditive_splitting_url( new
		 * String(path.substring(1,path.length()-1)));
		 * if(variable.equals("BROKER_URL")) { broker_url = new
		 * String(path.substring(1,path.length()-1));
		 * exp_wf.setBroker_services_url(broker_url); } }
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } finally { try { if
		 * (br != null)br.close(); } catch (IOException ex) {
		 * ex.printStackTrace(); } }
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
		this.wsdl_gen = new WSDLGenerator("http://soc." + expwf_process
				+ ".workflow", expwf_process);

		// wsdl_gen.initialize(exp_wf);
	}

	public void initialise() {
		j = new MatrixJep();
		j.addStandardConstants();
		j.addStandardFunctions();
		j.addComplex();
		j.setAllowUndeclared(true);
		j.setImplicitMul(true);
		j.setAllowAssignment(true);
		((MatrixJep) j).addStandardDiffRules();

	}

	public void convert() throws ParserConfigurationException, SAXException,
	IOException, ParseException {
		Node exp_tree = j.parse(expression);
		// // Traverse the expression tree to create the workflow
		traverse(exp_tree);

		BrokerSOCResource result = new  BrokerSOCResource(result_stack.get(nInStack-1)); 
		result.setAvailable(false);
		//result.setStorage_protocol(StorageProtocol.ADDITIVE_SPLITTING);
		//result.setUser_token(result_stack.get(tos).getUser_token());
		//tos--;

		result_matrix_ID = result.getResource_id();
		MetadataStoreConnection conn = null;
		try {
			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
			//Update the job with the result ID 
			String job_json= conn.getSOCJob(job_ID);
			Gson gson = new Gson();
			SOCJob job = gson.fromJson(job_json, SOCJob.class);
			job.getStatus().setResultID(result.getResource_id());
			conn.updateSOCJob(job);
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SERIALIZE BPEL
		exp_wf.serialize();

		// **************************
		// SERIALIZE WSDL
		wsdl_gen.initialize(exp_wf);
		wsdl_gen.write(exp_wf.getFolder_Path() + "/" + exp_wf.getWf_name()
				+ "/" + exp_wf.getWf_name() + "Artifacts.wsdl");

		// DEPLOY
		deploy_xml = new DeployXML(exp_wf);
		deploy_xml.addPartnerLinks(true);
		deploy_xml.write();

		exp_wf.deploy(broker_url);

	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void main(String[] args) throws ParserConfigurationException,
	SAXException, IOException {
		// TODO Auto-generated method stub

		// String expression = "(A*B+C*D)+(A*D+ B*C)+(A*C+B*D)";
		String expression = "A*B+C*D";
		ExpressionTranslator translator = new ExpressionTranslator(expression);
		// **********************
		// read data from the metadata store

		// ***************************

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

	public void traverse(Node tree) throws ParserConfigurationException,
	SAXException, IOException {
		Node curr_node = tree;
		Node left_node = null;
		Node right_node = null;
		if (curr_node.jjtGetNumChildren() == 0) {

			System.out.println("Node " + curr_node.toString()
					+ " is a leaf node ");
			if (curr_node.jjtGetParent().jjtGetNumChildren() == 1) // Unary
				// operator
			{
				String var_key = new String(curr_node.toString().substring(11,
						curr_node.toString().length() - 1));
				left_operand = translator.getExpressionVariable(var_key);
				right_operand = null;
				//	result_stack.add(new BrokerSOCResource(left_operand)); 
				//	tos++;

			} else // Two operands for the Binary operator
			{
				if (curr_node.jjtGetParent().jjtGetChild(1) != curr_node) {
					// may need adjustment after adding metadata store

					String var_key = new String(curr_node.toString().substring(
							11, curr_node.toString().length() - 1));

					left_operand = translator.getExpressionVariable(var_key);
					//	result_stack.add(new BrokerSOCResource(left_operand)); 
					//  tos++;
					System.out.println(left_operand.getResource_id()
							+ " is the left operand!");

				}
				if (curr_node.jjtGetParent().jjtGetChild(0) != curr_node) {

					// may need adjustment after addign metadata store
					String var_key = new String(curr_node.toString().substring(
							11, curr_node.toString().length() - 1));

					right_operand = translator.getExpressionVariable(var_key);
					//	result_stack.add(new BrokerSOCResource(right_operand)); 
					//	tos++;

					System.out.println(right_operand.getResource_id()
							+ " is the right operand!");
				}

			}

			// exp_wf.addMessageVariable(var_name, var_type);
			return;
		} else if (curr_node.jjtGetNumChildren() == 2) // Binary operator
		{
			left_node = curr_node.jjtGetChild(0);
			right_node = curr_node.jjtGetChild(1);
			if (curr_node.jjtGetChild(0).getClass() == ASTFunNode.class
					&& curr_node.jjtGetChild(1).getClass() == ASTFunNode.class) {
				flow_counter += 1;

				flow_stack.add(current_flow++, flow_counter);
				exp_wf.addParallelflow(flow_counter);
				if (flow_counter > 1) {
					// current_flow++;
					exp_wf.connectFlowToFlow(flow_counter,
							flow_stack.get(current_flow - 2));

				}
			}

		} else {
			left_node = curr_node.jjtGetChild(0);
			if (curr_node.jjtGetChild(0).getClass() == ASTFunNode.class) {
				flow_counter += 1;
				flow_stack.add(current_flow++, flow_counter);
				exp_wf.addParallelflow(flow_counter);
				if (flow_counter > 1) {
					// current_flow++;
					exp_wf.connectFlowToFlow(flow_counter,
							flow_stack.get(current_flow - 2));

				}

			}

		}

		if (left_node != null) {
			traverse(left_node);

		}
		if (right_node != null) {

			traverse(right_node);
			// if(curr_node.jjtGetChild(0).getClass() == ASTFunNode.class &&
			// curr_node.jjtGetChild(1).getClass() == ASTFunNode.class )
			// {
			// current_flow -= 1;
			// }
			// System.out.println("Visiting Node "+ right_node.toString() );
		}
		System.out.println("Visiting Node " + curr_node.toString());

		// If the children are not leaves i.e. they are not variables but
		// operators
		// Then create a flow and get children corresponding activities to be
		// added to the flow

		// SWITCH current_node operator --> binary or unary

		if (curr_node.jjtGetNumChildren() == 1) {
			// Unary operator
		} else {
			if(tos!= -1 && tos-1 !=-1)
			{
				right_operand = result_stack.get(tos--);
				left_operand = result_stack.get(tos--);
			}
			int output = 0;
			// Create ASSIGN Activity
			// ADD INVOKE and Callback RECEIVE activities
			if (curr_node.toString().contains("*")) {
				// ****
				// for testing only .. this should be updated and added to
				// broker services
				MetadataStoreConnection conn;
				try {
					conn = new MetadataStoreConnection(
							SOCConfiguration.METADATA_STORE_URL);
					BrokerSOCResource result = new BrokerSOCResource(
							left_operand.getResource_id() + "_"
									+ right_operand.getResource_id());
					result.setFile_path(SOCConfiguration.BROKER_STORAGE_PATH
							+ "/" + result.getResource_id());
					result.setUser_token(left_operand.getUser_token());
					result.setResource_meta(left_operand.getResource_meta());
					if(left_operand.getStorage_protocol() == StorageProtocol.ADDITIVE_SPLITTING)
					{
						AdditiveSplitter splitter = new AdditiveSplitter(result);

						SOCConfiguration conf = new SOCConfiguration();
						Location loc_split1 = conf.getRandomCloud();
						Location loc_split2;
						while ((loc_split2 = conf.getRandomCloud()) != loc_split1)
							;
						// splitter.Split(loc_split1, loc_split2);
						result.addLocation(loc_split1);
						result.addLocation(loc_split2);
						result.setStorage_protocol(StorageProtocol.ADDITIVE_SPLITTING);
					}
					boolean added = conn.addSOCResource(result);

					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// ************************* for testing only ***************

				if (left_operand.getStorage_protocol() == StorageProtocol.ADDITIVE_SPLITTING) {
					// *************************
					// Check if resplitting is needed : is done now in the
					// additive splitting protocol itself
					// **************************

					// boolean added
					// =exp_wf.addNamespace("ns"+namespace_counter,
					// exp_wf.getAdditive_splitting_process().getTargetNamespace());
					// if(added== true) namespace_counter++;
					// assign additive splitting message request
					String sub_jobID = UUID.randomUUID().toString();
					String request_str = "<bpel:literal><ws:AdditiveSplittingRequest xmlns:ws=\""
							+ exp_wf.getAdditiveSplitting_namespace()
							+ "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
							+ "<ws:jobID>"
							+ exp_wf.getWf_name()
							+ "</ws:jobID>"
							+ "<ws:sub_jobID>"
							+ sub_jobID
							+ "</ws:sub_jobID>"
							+ "<ws:matA_ID>"
							+ left_operand.getResource_id()
							+ "</ws:matA_ID>"
							+ "<ws:matB_ID>"
							+ right_operand.getResource_id()
							+ "</ws:matB_ID>"
							+ "</ws:AdditiveSplittingRequest></bpel:literal>";

					int input = var_counter;
					exp_wf.addMessageVariable("var" + input,
							"ns1:AdditiveSplittingRequestMessage"); // reading
					// variable
					// type may
					// be done
					// from
					// parsing
					// the
					// additive
					// splitting
					// bpel file
					// and
					// getting
					// the
					// variable
					// type but
					// this
					// would be
					// complicated
					// and would
					// take
					// time,
					// while
					// they are
					// not
					// supposed
					// to change
					// To add a local partner link to the scope
					curr_scope += 1;
					exp_wf.addScope(curr_scope);
					exp_wf.addLocalPartnerlinks("AdditiveSplitting",
							curr_scope, invoke_counter);
					exp_wf.addAssign("Assign" + assign_counter, request_str,
							"var" + input, curr_scope);
					// exp_wf.addAssignToProperty("Assign"+assign_counter,
					// "tns:subjob_"+invoke_counter,
					// "<bpel:literal xml:space=\"preserve\"><![CDATA["+sub_jobID+"]]></bpel:literal>",
					// "var"+input);

					// Invoke additive splitting process activ_name, operation,
					// pl, portType, input
					exp_wf.addCorrelationSet("SUBJOB_" + invoke_counter,
							"tns:subjob_" + invoke_counter);
					exp_wf.addInvokeActivity("Invoke" + invoke_counter,
							"initiate",
							"AdditiveSplitting_PL" + invoke_counter,
							"ns1:AdditiveSplitting", "var" + input, "SUBJOB_"
									+ invoke_counter, curr_scope);

					output = ++var_counter;
					exp_wf.addMessageVariable("var" + output,
							"ns1:AdditiveSplittingResponseMessage");

					// receive callback "callback"+receive_counter
					exp_wf.addCallbackActivity("Callback" + receive_counter,
							"onResult", "AdditiveSplittingCB_PL"
									+ invoke_counter,
									"ns1:AdditiveSplittingCallback", "var" + output,
									"SUBJOB_" + invoke_counter, curr_scope);

					exp_wf.connectToScopeStart("Assign" + assign_counter,
							"Scope" + curr_scope);
					exp_wf.connect("Assign" + assign_counter, "Invoke"
							+ invoke_counter, "Scope" + curr_scope);
					exp_wf.connect("Invoke" + invoke_counter, "Callback"
							+ receive_counter, "Scope" + curr_scope);
					exp_wf.connectToScopeEnd("Callback" + receive_counter,
							"Scope" + curr_scope);
					// ////exp_wf.connectToFlow(current_flow,
					// "Assign"+assign_counter, "Callback"+receive_counter);

					exp_wf.connectToLastNode("Scope" + curr_scope);
					if (current_flow > 0)
						exp_wf.connectToFlow(flow_stack.get(current_flow - 1),
								"Scope" + curr_scope);
					else
						exp_wf.connectToEnd("Scope" + curr_scope);
					/*
					 * exp_wf.connectToLastNode("Assign"+assign_counter);
					 * if(current_flow >0)
					 * exp_wf.connectToFlow(flow_stack.get(current_flow-1),
					 * "Callback"+receive_counter ); else
					 * exp_wf.connectToEnd("Callback"+receive_counter );
					 */
					assign_counter++;
					invoke_counter++;
					receive_counter++;
					var_counter++;

				}
				// else OTHER PROTOCOLS
				// /

			} else if (curr_node.toString().contains("+")) {

				// ****
				// for testing only .. this should be updated and added to
				// broker services
				MetadataStoreConnection conn;
				try {
					conn = new MetadataStoreConnection(
							SOCConfiguration.METADATA_STORE_URL);
					BrokerSOCResource result = new BrokerSOCResource(
							left_operand.getResource_id() + "_"
									+ right_operand.getResource_id());
					result.setFile_path(SOCConfiguration.BROKER_STORAGE_PATH
							+ "/" + result.getResource_id());
					result.setUser_token(left_operand.getUser_token());
					result.setResource_meta(left_operand.getResource_meta());
					if(left_operand.getStorage_protocol()== StorageProtocol.ADDITIVE_SPLITTING)
					{
						AdditiveSplitter splitter = new AdditiveSplitter(result);
						SOCConfiguration conf = new SOCConfiguration();
						Location loc_split1 = conf.getRandomCloud();
						Location loc_split2;
						while ((loc_split2 = conf.getRandomCloud()) != loc_split1)
							;
						// splitter.Split(loc_split1, loc_split2);
						result.addLocation(loc_split1);
						result.addLocation(loc_split2);
						result.setStorage_protocol(StorageProtocol.ADDITIVE_SPLITTING);
					}
					boolean added = conn.addSOCResource(result);
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// ************************* for testing only ***************

				// assign add broker message request

				String request_str = "<bpel:literal><bs:add xmlns:bs=\""
						+ exp_wf.getBroker_namespace()
						+ "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
						+ "<op_id>" + invoke_counter + "</op_id>" + "<job_id>"
						+ exp_wf.getWf_name() + "</job_id>"
						+ "<add_list> <cloudURL>" + SOCConfiguration.BROKER_URL
						+ "</cloudURL> <splitName>"
						+ left_operand.getResource_id()
						+ "</splitName>  </add_list>"
						+ "<add_list>  <cloudURL>"
						+ SOCConfiguration.BROKER_URL
						+ "</cloudURL> <splitName>"
						+ right_operand.getResource_id()
						+ "</splitName>  </add_list>" + "<matA_ID>"
						+ left_operand.getResource_id() + "</matA_ID>"
						+ "<matB_ID>" + right_operand.getResource_id()
						+ "</matB_ID>" + "<callback>" + "Callback"
						+ receive_counter + "</callback>"
						+ "</bs:add></bpel:literal>";

				/*
				 * <ws:add xmlns:ws="http://www.brokerservices.org/MatServ/"
				 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				 * <op_id>5</op_id> <job_id>job_id</job_id>
				 * <matA_ID>matA_ID</matA_ID> <matB_ID>matB_ID</matB_ID>
				 * <callback>callBack_Operation1</callback> </ws:add>
				 */

				int input = var_counter;
				exp_wf.addMessageVariable("var" + input, "ns2:addRequest"); // reading
				// variable
				// type
				// may
				// be
				// done
				// from
				// parsing
				// the
				// additive
				// splitting
				// bpel
				// file
				// and
				// getting
				// the
				// variable
				// type
				// but
				// this
				// would
				// be
				// complicated
				// and
				// would
				// take
				// time,
				// while
				// they
				// are
				// not
				// supposed
				// to
				// change
				exp_wf.addAssign("Assign" + assign_counter, request_str, "var"
						+ input);

				// exp_wf.addAssignToProperty("Assign"+assign_counter,
				// "tns:subjob_"+invoke_counter,
				// "<bpel:literal xml:space=\"preserve\"><![CDATA["+invoke_counter+"]]></bpel:literal>",
				// "var"+input);

				output = ++var_counter;
				exp_wf.addMessageVariable("var" + output, "ns2:addResponse");

				// Invoke broker webservice
				exp_wf.addCorrelationSet("BROKER_SUBJOB_" + invoke_counter,
						"tns:broker_subjob_" + invoke_counter);
				exp_wf.addInvokeActivity("Invoke" + invoke_counter, "add",
						exp_wf.Broker_PL.getName(), "ns2:MatServ", "var"
								+ input, "BROKER_SUBJOB_" + invoke_counter);

				output = ++var_counter;
				exp_wf.addMessageVariable("var" + output, "ns2:addResponse");

				//				exp_wf.addPartnerLink("BrokerAdd", "BrokerCB_PL"+ receive_counter);
				// receive callback
				//exp_wf.addCallbackActivity("Callback" + receive_counter,
				//		"onResult", "BrokerCB_PL" + receive_counter,
				//	"ns2:BrokerCallback", "var" + output, "BROKER_SUBJOB_"
				//		+ invoke_counter);

				exp_wf.addCallbackActivity("Callback" + receive_counter,
						"callback"+receive_counter, "client",
						"tns:"+exp_wf.getWf_name() , "var" + output, "BROKER_SUBJOB_"
								+ invoke_counter);


				exp_wf.connect("Assign" + assign_counter, "Invoke"
						+ invoke_counter);

				exp_wf.connect("Invoke" + invoke_counter, "Callback"
						+ receive_counter);
				// exp_wf.connectBetweenFlow(current_flow, 1,
				// "Assign"+assign_counter, "Callback"+receive_counter);

				exp_wf.connectToLastNode("Assign" + assign_counter);
				if (current_flow > 0)
					exp_wf.connectToFlow(flow_stack.get(current_flow - 1),
							"Callback" + receive_counter);
				else
					exp_wf.connectToEnd("Callback" + receive_counter);

				assign_counter++;
				invoke_counter++;
				receive_counter++;

			}
			/*
			 * if(curr_node.jjtGetChild(0).getClass() == ASTFunNode.class &&
			 * curr_node.jjtGetChild(1).getClass() == ASTFunNode.class ) {
			 * current_flow -= 1; }
			 */

			if (curr_node.jjtGetParent().jjtGetChild(0) == curr_node) // I am
				// the
				// left
				// hand
				// side
				// of
				// the
				// another
				// expression
			{
				// update left operand with the result ID returned and get
				// corresponding data from metadata store
				MetadataStoreConnection conn;
				try {
					conn = new MetadataStoreConnection(
							SOCConfiguration.METADATA_STORE_URL);
					String res = conn.getSOCResource(left_operand
							.getResource_id()
							+ "_"
							+ right_operand.getResource_id());
					Gson gson = new Gson();
					GsonBuilder builder = new GsonBuilder();
					builder.registerTypeAdapter(ResourceMeta.class   , new ResourceMetaAdapter());
					gson = builder.create();

					left_operand = gson.fromJson(res, BrokerSOCResource.class);
					conn.close();
					result_stack.add(new BrokerSOCResource(left_operand));
					tos++;
					nInStack++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (curr_node.jjtGetParent().jjtGetChild(1) == curr_node) // I
				// am
				// the
				// right
				// hand
				// side
				// of
				// the
				// another
				// expression
			{
				// update right operand with the result ID returned and get
				// corresponding data from metadata store

				MetadataStoreConnection conn;
				try {
					conn = new MetadataStoreConnection(
							SOCConfiguration.METADATA_STORE_URL);
					String res = conn.getSOCResource(left_operand
							.getResource_id()
							+ "_"
							+ right_operand.getResource_id());
					Gson gson = new Gson();
					GsonBuilder builder = new GsonBuilder();
					builder.registerTypeAdapter(ResourceMeta.class   , new ResourceMetaAdapter());
					gson = builder.create();

					right_operand = gson.fromJson(res, BrokerSOCResource.class);
					conn.close();
					result_stack.add(new BrokerSOCResource(right_operand));
					tos++;
					nInStack++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// pop the flow on top of stack
				if (current_flow > 0) {
					exp_wf.updateLastNode("FlowJoin"
							+ flow_stack.get(current_flow - 1));

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

	/**
	 * @return the result_matrix_ID
	 */
	public String getResult_matrix_ID() {
		return result_matrix_ID;
	}

	/**
	 * @param result_matrix_ID the result_matrix_ID to set
	 */
	public void setResult_matrix_ID(String result_matrix_ID) {
		this.result_matrix_ID = result_matrix_ID;
	}

}

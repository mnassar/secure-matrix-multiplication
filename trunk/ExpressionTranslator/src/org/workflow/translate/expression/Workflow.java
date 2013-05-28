package org.workflow.translate.expression;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bpel.deploy.FolderZipper;
import org.bpel.deploy.ProcessDeploy;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelCompositeActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelScope;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariable;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Workflow {

	
	private BpelProcess process ;
	private String wf_name;
	private String folder_Path;
	private String ODE_PATH;
	private String additive_splitting_process;
	private	String queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0";
	private String WSDL_SCHEMA= "http://schemas.xmlsoap.org/wsdl/";
	
	BpelCorrelationSet JOB_CS;
    BpelCompositeActivity bpelCompositeActivity;//= (BpelCompositeActivity) process ;
    
   
	BpelScope scope;// = bpelCompositeActivity.getScope();
	
	List<BpelVariable> variables;
	
	
	public Workflow(String process_name, String process_namespace)
	{
	
		wf_name = new String(process_name);
		
		process = new BpelProcess(process_name, process_namespace);
	///**************
		//TODO: Add to the process namespace declarations for the additive splitting and other protocols
		//and add namespace for the cloud and broker services
		//process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
	///	
		process.addNamespaceDeclaration("tns", process_namespace);
		process.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		process.addNamespaceDeclaration("ode", "http://www.apache.org/ode/type/extension");
		process.setExitOnStandardFault("yes");
		process.setSuppressJoinFailure("yes");
		
		///**************
			//TODO: Add necessary Imports
			//BpelImport imp  = new BpelImport("http://matrix.bpelprocess", "WF_ProcessArtifacts.wsdl", WSDL_SCHEMA);
			//process.addImport(imp);
	    ///
		
		///**************
			//TODO: Add necessary PartnerLinks
			BpelPartnerLink client_PL = new BpelPartnerLink("client", "tns:"+process_name, process_name+"Provider", null);
			process.addPartnerLink(client_PL);
        	//BpelPartnerLink  PL1 = new BpelPartnerLink("A1B1_PL", "tns:Mahout_PL24", null, "ServiceProvider24");
			// process.addPartnerLink(PL1);
		///
			
			  JOB_CS= new BpelCorrelationSet("JOB_CS", "tns:jobid_CS");
			  process.addCorrelationSet(JOB_CS);
			  bpelCompositeActivity= (BpelCompositeActivity) process ;
			  scope= bpelCompositeActivity.getScope();
	
			  BpelVariable variable = new BpelVariableMessageType("input", "tns:"+wf_name+"RequestMessage");
	    	  scope.addVariable(variable);
	    		
	    	  BpelVariable variable2 = new BpelVariableMessageType("output", "tns:"+wf_name+"ResponseMessage");
	    	  scope.addVariable(variable2);
	    	
	    
	}
	
	/**
	 * @return the folder_Path
	 */
	public String getFolder_Path() {
		return folder_Path;
	}

	/**
	 * @return the oDE_PATH
	 */
	public String getODE_PATH() {
		return ODE_PATH;
	}

	public String getWf_name() {
		return wf_name;
	}

	/**
	 * @return the additive_splitting_process
	 */
	public String getAdditive_splitting_process() {
		return additive_splitting_process;
	}

	/**
	 * @param additive_splitting_process the additive_splitting_process to set
	 */
	public void setAdditive_splitting_process(String additive_splitting_process) {
		this.additive_splitting_process = additive_splitting_process;
	}

	public void setWf_name(String wf_name) {
		this.wf_name = wf_name;
	}

	/**
	 * @param oDE_PATH the oDE_PATH to set
	 */
	public void setODE_PATH(String oDE_PATH) {
		ODE_PATH = oDE_PATH;
	}

	/**
	 * @param folder_Path the folder_Path to set
	 */
	public void setFolder_Path(String folder_Path) {
		this.folder_Path = folder_Path;
	}

	public void addNamespaces()
	{
		///**************
				//TODO: Add to the process namespace declarations for the additive splitting and other protocols
				//and add namespace for broker services
				//process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
			///	
	}
	
	public void addMessageVariable(String var_name, String var_type)
	{
		 variables.add(new BpelVariableMessageType(var_name, var_type));
		 scope.addVariable(new BpelVariableMessageType(var_name, var_type));
	}
	
	public void addTypeVariable(String var_name, String var_type)
	{
		 variables.add(new BpelVariableType(var_name, var_type));
		 scope.addVariable(new BpelVariableType(var_name, var_type));
	}
	
	public void addAssign(String activ_name, String message, BpelVariable var) throws ParserConfigurationException, SAXException, IOException
	{
		BpelAssignActivity assign = new BpelAssignActivity(activ_name);
        assign.setValidate("no");
        
        BpelCopy copy = new BpelCopy();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String(message).getBytes())).getDocumentElement();
		
		BpelFromExpression from = new BpelFromExpression(literalElement);
	    copy.setFrom(from);
        BpelToVariable to = new BpelToVariable(var.getName());
        to.setPart("payload");
        copy.setTo(to);
        assign.addCopy(copy);
        
        bpelCompositeActivity.addChild(assign);
	}
	public void addInvokeActivity(String activ_name, String operation, String pl, String portType, String input)
	{
		BpelInvokeActivity invoke = new BpelInvokeActivity(activ_name);
        invoke.setOperation(operation);
        invoke.setPartnerLink(pl);
        invoke.setPortType(portType);
        invoke.setInputVariable(input);
        bpelCompositeActivity.addChild(invoke);
    }
	
	public void addCallbackActivity(String activ_name, String operation, String pl, String portType, String input)
	{
		 BpelCompositeReceiveActivity callback= new BpelCompositeReceiveActivity(activ_name);
         callback.setOperation(operation);
         callback.setPartnerLink(pl);
         callback.setPortType(portType);
         callback.setVariable(input);
         
         BpelCorrelation corr_add= new BpelCorrelation("no", "JOB_CS");
         callback.addCorrelation(corr_add);
         
         bpelCompositeActivity.addChild(callback);
    }
	public void serialize()
	{
		System.out.println(getFolder_Path());
		System.out.println(wf_name);
		File file = new File(getFolder_Path()+"/"+wf_name);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		File bpel_file = new File(wf_name);
		BpelSerializer bpelSerializer = new BpelSerializer();
        bpelSerializer.serialize(process, bpel_file);
	}
	
	public void deploy(String url)
	{
		//
		//********************************
		//DEPLOY
		String zipTo_Path= folder_Path+"/"+wf_name+".zip" ;
		String deploymentService_URL =url+"/ode/processes/DeploymentService";
	
		ProcessDeploy deployProc = new ProcessDeploy();
		deployProc.setBpelprocess_folder(folder_Path);
		deployProc.setProcess_Name(wf_name);
		deployProc.setZippedProcess_Path(zipTo_Path);
		deployProc.setDeploymentService_URL(deploymentService_URL);

		FolderZipper appZip = new FolderZipper(folder_Path+"/"+wf_name,zipTo_Path);
		appZip.generateFileList(new File(appZip.getSource_Folder()));
		appZip.zipIt(appZip.getOutput_ZIP_Path());

		deployProc.deploy();
	}
}

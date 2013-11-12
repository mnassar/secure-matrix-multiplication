package broker.workflow.translate.expression;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.bpel.deploy.FolderZipper;
import org.bpel.deploy.ProcessDeploy;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.abstract_syntax.impl.ActivityImpl;
import org.unify_framework.abstract_syntax.impl.AndJoinImpl;
import org.unify_framework.abstract_syntax.impl.AndSplitImpl;
import org.unify_framework.abstract_syntax.impl.ControlNodeImpl;
import org.unify_framework.abstract_syntax.impl.NodeImpl;
import org.unify_framework.abstract_syntax.impl.TransitionImpl;
import org.unify_framework.instances.bpel.BpelAndJoin;
import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelAtomicActivity;
import org.unify_framework.instances.bpel.BpelCompositeActivity;
import org.unify_framework.instances.bpel.BpelCompositeInvokeActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCompositeReplyActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelFromVariable;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScope;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariable;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.parser.BpelParser;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.instances.bpel.visitors.Element;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import broker.SOCConfiguration;

public class Workflow {

	
	private BpelProcess process ;
	private String wf_name;
	private String folder_Path;
	private String ODE_PATH;
	private String additive_splitting_url;
	private String broker_services_url;
	private	String queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0";
	private String WSDL_SCHEMA= "http://schemas.xmlsoap.org/wsdl/";
	
	private BpelProcess additive_splitting_process;
	
	BpelCorrelationSet JOB_CS;
    BpelCompositeActivity bpelCompositeActivity;//= (BpelCompositeActivity) process ;
    BpelCompositeReceiveActivity initial_receive;
    BpelCompositeReplyActivity reply_response ;
    String last_node;
    
	BpelScope scope;// = bpelCompositeActivity.getScope();
	
	ArrayList<BpelVariable> variables;
	
	BpelPartnerLink client_PL;
	BpelPartnerLink CALLBACK_PL;
	BpelPartnerLink AdditiveSplitting_PL;
	String AdditiveSplitting_namespace;
	BpelPartnerLink Broker_PL;
	String Broker_namespace;
	
	public Workflow(String process_name, String process_namespace)
	{
		wf_name = new String(process_name);
		variables = new ArrayList<BpelVariable>();
		process = new BpelProcess(process_name, process_namespace);
		AdditiveSplitting_namespace = "http://additivesplitting.bpelprocess";
		Broker_namespace = "http://www.brokerservices.org/MatServ/";
		///**************
		//TODO: Add to the process namespace declarations for the additive splitting and other protocols
		//and add namespace for the cloud and broker services
		//process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
		///	
		process.addNamespaceDeclaration("tns", process_namespace);
		process.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		process.addNamespaceDeclaration("ode", "http://www.apache.org/ode/type/extension");
		process.addNamespaceDeclaration("ns1", AdditiveSplitting_namespace);
		process.addNamespaceDeclaration("ns2", Broker_namespace);
		process.setExitOnStandardFault("yes");
		process.setSuppressJoinFailure("yes");
	}
	
	public void initialize() throws ParserConfigurationException, SAXException, IOException 
	{
		///**************
				//TODO: Add necessary Imports
				BpelImport imp  = new BpelImport(AdditiveSplitting_namespace, "AdditiveSplittingArtifacts.wsdl", WSDL_SCHEMA);
				process.addImport(imp);
				BpelImport imp2  = new BpelImport(Broker_namespace, "BrokerServices.wsdl", WSDL_SCHEMA);
				process.addImport(imp2);
				imp = new BpelImport(process.getTargetNamespace(), wf_name+"Artifacts.wsdl", WSDL_SCHEMA);
				process.addImport(imp);
				
				///

				///**************
				//TODO: Add necessary PartnerLinks
				client_PL = new BpelPartnerLink("client", "tns:"+process.getName(), process.getName()+"Provider", null);
				process.addPartnerLink(client_PL);
			//	AdditiveSplitting_PL = new BpelPartnerLink("AdditiveSplitting_PL", "tns:AdditiveSplitting_PLT", null, "AdditiveSplittingServiceProvider");
			//	process.addPartnerLink(AdditiveSplitting_PL);
				Broker_PL  = new BpelPartnerLink("Broker_PL", "tns:Broker_PLT", null, "BrokerServicesProvider");
				process.addPartnerLink(Broker_PL);
				
				
			//BpelPartnerLink	Broker_PL = new BpelPartnerLink("CALLBACK_PL", "tns:"+process.getName(), process.getName()+"Provider", null);
			//	process.addPartnerLink(CALLBACK_PL);
				///
				JOB_CS= new BpelCorrelationSet("JOB_CS", "tns:jobid_CS");
				process.addCorrelationSet(JOB_CS);
				bpelCompositeActivity= (BpelCompositeActivity) process ;
				scope= bpelCompositeActivity.getScope();

				BpelVariable variable = new BpelVariableMessageType("input", "tns:"+wf_name+"RequestMessage");
				scope.addVariable(variable);

				BpelVariable variable2 = new BpelVariableMessageType("output", "tns:"+wf_name+"ResponseMessage");
				scope.addVariable(variable2);

				//Initial receive to start the workflow
				initial_receive = new BpelCompositeReceiveActivity("receiveInput");
				initial_receive.setOperation("start");
				initial_receive.setPartnerLink("client");
				initial_receive.setPortType("tns:"+wf_name);
				initial_receive.setVariable("input");
				initial_receive.setCreateInstance("yes");
		      
				BpelCorrelation corr = new BpelCorrelation("yes", "JOB_CS");
		        initial_receive.addCorrelation(corr);
		      
		        bpelCompositeActivity.addChild(initial_receive);
		        bpelCompositeActivity.connect(bpelCompositeActivity.getStartEvent(), initial_receive);
		        
		        BpelAssignActivity response_assign =  new BpelAssignActivity("responseAssign");
		        response_assign.setValidate("no");
		        
		        BpelCopy copy = new BpelCopy();
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.newDocument();
				org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String("<bpel:literal><tns:"+wf_name+"Response xmlns:tns=\""+process.getTargetNamespace()+"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
						  "<tns:jobID>tns:jobID</tns:jobID>"+
						  "<tns:instanceID>tns:instanceID</tns:instanceID>"+
						  "</tns:"+wf_name+"Response></bpel:literal>").getBytes())).getDocumentElement();
				
				BpelFromExpression from = new BpelFromExpression(literalElement);
			    copy.setFrom(from);
	            BpelToVariable to = new BpelToVariable("output");
	            to.setPart("parameters");
	            
	            copy.setTo(to);
	            response_assign.addCopy(copy);
	        //******************************************
	            BpelFromVariable from2 = new BpelFromVariable("input");
	            from2.setPart("parameters");
	            //document.createCDATASection("<![CDATA[tns:input]]>");		
	            from2.setQuery("tns:jobID");
	            from2.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            BpelCopy copy2 = new BpelCopy();
	            copy2.setFrom(from2);
	            BpelToVariable to2 = new BpelToVariable("output");
	            to2.setPart("parameters");
	            to2.setQuery("tns:jobID");
	            to2.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy2.setTo(to2);
	            response_assign.addCopy(copy2);
	
	            BpelCopy copy3 = new BpelCopy();
	            CDATASection cdata = document.createCDATASection("string($ode:pid)");
	            BpelFromExpression from3 = new BpelFromExpression(cdata.cloneNode(true));
	            
			    copy3.setFrom(from3);
	            BpelToVariable to3 = new BpelToVariable("output");
	            to3.setPart("parameters");
	            to3.setQuery("tns:instanceID");
	            to3.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy3.setTo(to3);
	            response_assign.addCopy(copy3);
	
	         
	            
		        bpelCompositeActivity.addChild(response_assign);
		        /*
		         * ><bpel:copy>
                <bpel:from><bpel:literal><tns:AdditiveSplittingResponse xmlns:tns="http://additivesplitting.bpelprocess" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <tns:jobID>tns:jobID</tns:jobID>
  <tns:sub_jobID>tns:sub_jobID</tns:sub_jobID>
  <tns:result>tns:result</tns:result>
</tns:AdditiveSplittingResponse>
</bpel:literal></bpel:from>
                <bpel:to variable="output" part="payload"></bpel:to>
            </bpel:copy>
            <bpel:copy>
                <bpel:from>
                    <![CDATA[concat($input.payload/tns:matA_ID, "_", $input.payload/tns:matB_ID,"_",string($ode:pid))]]>
                </bpel:from>
                <bpel:to>
                    <![CDATA[$output.payload/tns:result]]>
                </bpel:to>
            </bpel:copy>
            <bpel:copy>
                <bpel:from>
                    <![CDATA[$input.payload/tns:jobID]]>
                </bpel:from>
                <bpel:to part="payload" variable="output">
                </bpel:to>
            </bpel:copy>
            <bpel:copy>
                <bpel:from>
                    <![CDATA[$input.payload/tns:sub_jobID]]>
                </bpel:from>
                <bpel:to part="payload" variable="output">
                    <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[tns:sub_jobID]]></bpel:query>
                </bpel:to>
            </bpel:copy>
        
		         */
		        
		        
		  
		         reply_response = new BpelCompositeReplyActivity("replyResponse");
		        BpelCorrelation correlation = new BpelCorrelation("no", "JOB_CS");
		        reply_response.addCorrelation(correlation);
		        reply_response.setOperation("start");
		        reply_response.setPartnerLink("client");
		        reply_response.setVariable("output");
		        reply_response.setPortType("tns:"+wf_name);
		        
		        bpelCompositeActivity.addChild(reply_response);
		        bpelCompositeActivity.connect(initial_receive, response_assign);
		        bpelCompositeActivity.connect(response_assign, reply_response);
		        
		        last_node = reply_response.getName();
		        process.setScope(scope);
		        
				 //BpelParser bpelParser = new BpelParser();
		         //additive_splitting_process = bpelParser.parse(ODE_PATH+"/WF_Process/WF_Process.bpel");
		         //((BpelCompositeActivity)additive_splitting_process).getScope().getVariables().
			    
	}
	public void addPartnerLink(String service, String PL_name)
	{
		if(service.equals("BrokerAdd"))
		{
			BpelPartnerLink pl = new BpelPartnerLink(PL_name, "tns:BrokerCallback_PLT", "BrokerServicesRequester", null);
			bpelCompositeActivity.addPartnerLink(pl);
		}
		
	}
	public void addLocalPartnerlinks(String service, int scope, int PL_number)
	{
		if(service.equals("AdditiveSplitting"))
		{
			BpelPartnerLink Local_AddSplit_PL = new BpelPartnerLink("AdditiveSplitting_PL"+PL_number, "ns1:AdditiveSplitting", null, "AdditiveSplittingProvider");
			((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+scope)).addPartnerLink(Local_AddSplit_PL);
			
			BpelPartnerLink Local_AddSplitCB_PL = new BpelPartnerLink("AdditiveSplittingCB_PL"+PL_number, "ns1:AdditiveSplitting",  "AdditiveSplittingRequester",null);
			((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+scope)).addPartnerLink(Local_AddSplitCB_PL);
			
		}
		
		
	}
	/*
	public Workflow copy()
	{
		Workflow wf = new Workflow(this.wf_name, this.process.getTargetNamespace());
		wf.setFolder_Path(this.folder_Path);
		wf.setAdditive_splitting_process(this.additive_splitting_process);
		wf.setAdditive_splitting_url(additive_splitting_url);
		wf.setBroker_namespace(Broker_namespace);
		wf.setWf_name(wf_name);
		wf.setAdditiveSplitting_namespace(AdditiveSplitting_namespace);
		wf.setODE_PATH(ODE_PATH);
		wf.setProcess(process);
		
		return wf;
	}
	*/
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
	public BpelProcess getAdditive_splitting_process() {
		return additive_splitting_process;
	}

	public String getAdditive_splitting_url() {
		return additive_splitting_url;
	}

	public String getAdditiveSplitting_namespace() {
		return AdditiveSplitting_namespace;
	}



	public String getBroker_namespace() {
		return Broker_namespace;
	}



	public void setBroker_namespace(String broker_namespace) {
		Broker_namespace = broker_namespace;
	}



	public void setAdditiveSplitting_namespace(String additiveSplitting_namespace) {
		AdditiveSplitting_namespace = additiveSplitting_namespace;
	}



	public void setAdditive_splitting_url(String additive_splitting_url) {
		this.additive_splitting_url = additive_splitting_url;
	}

	/**
	 * @param additive_splitting_process the additive_splitting_process to set
	 */
	public void setAdditive_splitting_process(BpelProcess additive_splitting_process) {
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

	public boolean addNamespace(String prefix, String namespace)
	{
		///**************
				// Add to the process namespace declarations for the additive splitting and other protocols
				//and add namespace for broker services
		if(!process.getNamespaceDeclarations().containsValue(namespace))
		{
			process.addNamespaceDeclaration(prefix, namespace);
			return true;
		}
		return false;
			
	}

	public void addCorrelationSet(String name, String property)
	{
		BpelCorrelationSet CS= new BpelCorrelationSet(name, property);
		process.addCorrelationSet(CS);
		
	}
	
	public void addScope(int scope_no)
	{
		  //Adding a scope
		  BpelScopeActivity local_scope = new BpelScopeActivity("Scope"+scope_no); 
  	      bpelCompositeActivity.addChild(local_scope);
  	    
  	   //   bpelCompositeActivity.connect(bpelCompositeActivity.getChild("FlowSplit"+curr_flow).getNewControlOutputPort(),local_scope.getControlInputPort());
  	   // bpelCompositeActivity.connect(local_scope.getControlOutputPort(),bpelCompositeActivity.getChild("FlowJoin"+curr_flow).getNewControlInputPort());
	}
	public void addParallelflow(int i)
	{

		BpelAndSplit split = new BpelAndSplit("FlowSplit"+i);
		BpelAndJoin join = new BpelAndJoin("FlowJoin"+i);

		split.setCorrespondingAndJoin(join);
		join.setCorrespondingAndSplit(split);

		bpelCompositeActivity.addChild(split);
		bpelCompositeActivity.addChild(join);

		if(i==1)
		{
			bpelCompositeActivity.connect(reply_response.getControlOutputPort(),split.getControlInputPort());
			//bpelCompositeActivity.connect(join.getControlOutputPort(), bpelCompositeActivity.getEndEvent().getControlInputPort());
		}
		last_node = "FlowSplit"+i;

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
	
	public void addAssign(String activ_name, String message, String var) throws ParserConfigurationException, SAXException, IOException
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
        BpelToVariable to = new BpelToVariable(var); 
        to.setPart("parameters");
        copy.setTo(to);
        assign.addCopy(copy);
        
        bpelCompositeActivity.addChild(assign);
	}
	
	public void addAssign(String activ_name, String message, String var, int curr_scope) throws ParserConfigurationException, SAXException, IOException
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
        BpelToVariable to = new BpelToVariable(var); 
        to.setPart("parameters");
        copy.setTo(to);
       
        assign.addCopy(copy);
        
        ((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+curr_scope)).addChild(assign);
        
	}
	public void  addCopyVariableToAssign(String assign_name, String variable_name, String member_name, String from_variable, String from_part) throws ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		//org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String(message).getBytes())).getDocumentElement();
		 
        BpelCopy copy = new BpelCopy();
        
        
        BpelFromVariable from = new BpelFromVariable(from_variable);
        from.setPart("parameters");
        from.setQuery(from_part);
        from.addNamespaceDeclaration("queryLanguage", queryLanguage);
        copy.setFrom(from);
        
        BpelToVariable to = new BpelToVariable(variable_name);
        to.setPart("parameters");
        to.setQuery(member_name);
        to.addNamespaceDeclaration("queryLanguage", queryLanguage);
        copy.setTo(to);
        
        ((BpelAssignActivity)(bpelCompositeActivity.getChild(assign_name))).addCopy(copy);
		
	}
	public void  addCopyVariableToAssign(String assign_name, String variable_name, String member_name, String from_variable, String from_part, int curr_scope) throws ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		//org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String(message).getBytes())).getDocumentElement();
		 
        BpelCopy copy = new BpelCopy();
        
        
        BpelFromVariable from = new BpelFromVariable(from_variable);
        from.setPart("parameters");
        from.setQuery(from_part);
        from.addNamespaceDeclaration("queryLanguage", queryLanguage);
        copy.setFrom(from);
        
        BpelToVariable to = new BpelToVariable(variable_name);
        to.setPart("parameters");
        to.setQuery(member_name);
        to.addNamespaceDeclaration("queryLanguage", queryLanguage);
        copy.setTo(to);
        
        ((BpelAssignActivity)((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+curr_scope)).getChild(assign_name)).addCopy(copy);
		
	}
	
	public void addCopyToAssign(String assign_name, String variable_name, String member_name, String value) throws ParserConfigurationException
	{
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		//org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String(message).getBytes())).getDocumentElement();
		 
        BpelCopy copy = new BpelCopy();
        
        CDATASection cdata = document.createCDATASection("string("+value+")");
        BpelFromExpression from = new BpelFromExpression(cdata.cloneNode(true));
        
        copy.setFrom(from);
        
        BpelToVariable to = new BpelToVariable(variable_name);
        to.setPart("parameters");
        to.setQuery(member_name);
        to.addNamespaceDeclaration("queryLanguage", queryLanguage);
        copy.setTo(to);
        
        ((BpelAssignActivity)(bpelCompositeActivity.getChild(assign_name))).addCopy(copy);
	}
	public void addCopyToAssign(String assign_name, String variable_name, String member_name, String value, int curr_scope) throws ParserConfigurationException
	{
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		//org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String(message).getBytes())).getDocumentElement();
		 
        BpelCopy copy = new BpelCopy();
        
        CDATASection cdata = document.createCDATASection("string("+value+")");
        BpelFromExpression from = new BpelFromExpression(cdata.cloneNode(true));
        
        copy.setFrom(from);
        
        BpelToVariable to = new BpelToVariable(variable_name);
        to.setPart("parameters");
        to.setQuery(member_name);
        to.addNamespaceDeclaration("queryLanguage", queryLanguage);
        copy.setTo(to);
        
        ((BpelAssignActivity)((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+curr_scope)).getChild(assign_name)).addCopy(copy);
/*
        <bpel:copy>
        <bpel:from expressionLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0">
            
        <bpel:to part="parameters" variable="var0">
            <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[ns1:sub_jobID]]></bpel:query>
        </bpel:to>
    </bpel:copy>
  */
    
        
	}
	
	public void addAssignToProperty(String activ_name, String property,String value, String var) throws ParserConfigurationException, SAXException, IOException
	{
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();

		BpelCopy copy = new BpelCopy();
		//CDATASection cdata = document.createCDATASection("string($"+var+".parameters/"+value+")");
		//CDATASection cdata = document.createCDATASection(value);
		
		org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String(value).getBytes())).getDocumentElement();
		//
//		BpelFromExpression from = new BpelFromExpression(cdata.cloneNode(true));
		BpelFromExpression from = new BpelFromExpression(literalElement);
		copy.setFrom(from);
		
		BpelToVariable to = new BpelToVariable(var);
		//to.setPart("parameters");
		//to.setQuery("tns:instanceID");
		to.setProperty(property);
		//to.addNamespaceDeclaration("queryLanguage", queryLanguage);, 
		copy.setTo(to);
		
		((BpelAssignActivity)bpelCompositeActivity.getChild(activ_name)).addCopy(copy);


	}
	/*
	 *   <bpel:copy>
			<bpel:from>
                <![CDATA[string(1)]]>
            </bpel:from>
            <bpel:to property="tns:op1_CS" variable="A1B1_PLRequest"></bpel:to>
         </bpel:copy>
	 */
	public void addInvokeActivity(String activ_name, String operation, String pl, String portType, String input, String SUB_JOB_CS)
	{
		BpelCompositeInvokeActivity invoke = new BpelCompositeInvokeActivity(activ_name);
        invoke.setOperation(operation);
        invoke.setPartnerLink(pl);
        invoke.setPortType(portType);
        invoke.setInputVariable(input);
      //  invoke.setOutputVariable(output);
        BpelCorrelation corr= new BpelCorrelation("no", "JOB_CS");
        invoke.addCorrelation(corr);
        BpelCorrelation corr2= new BpelCorrelation("yes", SUB_JOB_CS);
        invoke.addCorrelation(corr2);
        bpelCompositeActivity.addChild(invoke);
    }
	
	public void addInvokeActivity(String activ_name, String operation, String pl, String portType, String input, String SUB_JOB_CS, int curr_scope)
	{
		BpelCompositeInvokeActivity invoke = new BpelCompositeInvokeActivity(activ_name);
        invoke.setOperation(operation);
        invoke.setPartnerLink(pl);
        invoke.setPortType(portType);
        invoke.setInputVariable(input);
      //  invoke.setOutputVariable(output);
        BpelCorrelation corr= new BpelCorrelation("no", "JOB_CS");
        invoke.addCorrelation(corr);
        BpelCorrelation corr2= new BpelCorrelation("yes", SUB_JOB_CS);
        invoke.addCorrelation(corr2);
        ((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+curr_scope)).addChild(invoke);
        //bpelCompositeActivity.addChild(invoke);
    }
	public void addCallbackActivity(String activ_name, String operation, String pl, String portType, String input, String SUB_JOB_CS)
	{
		 BpelCompositeReceiveActivity callback= new BpelCompositeReceiveActivity(activ_name);
         callback.setOperation(operation);
         callback.setPartnerLink(pl);
         callback.setPortType(portType);
         callback.setVariable(input);
         
         BpelCorrelation corr_add= new BpelCorrelation("no", "JOB_CS");
         callback.addCorrelation(corr_add);
         BpelCorrelation corr_flow= new BpelCorrelation("no", SUB_JOB_CS);
         callback.addCorrelation(corr_flow);
         
         bpelCompositeActivity.addChild(callback);
    }
	
	public void addCallbackActivity(String activ_name, String operation, String pl, String portType, String input, String SUB_JOB_CS, int curr_scope)
	{
		 BpelCompositeReceiveActivity callback= new BpelCompositeReceiveActivity(activ_name);
         callback.setOperation(operation);
         callback.setPartnerLink(pl);
         callback.setPortType(portType);
         callback.setVariable(input);
         
         BpelCorrelation corr_add= new BpelCorrelation("no", "JOB_CS");
         callback.addCorrelation(corr_add);
         BpelCorrelation corr_flow= new BpelCorrelation("no", SUB_JOB_CS);
         callback.addCorrelation(corr_flow);
         
         ((BpelScopeActivity)bpelCompositeActivity.getChild("Scope"+curr_scope)).addChild(callback);
         //bpelCompositeActivity.addChild(callback);
    }
	public void addReplyActivity(String activ_name, String operation, String pl, String portType, String input)
	{
		 BpelCompositeReplyActivity reply= new BpelCompositeReplyActivity(activ_name);
		 reply.setOperation(operation);
		 reply.setPartnerLink(pl);
		 reply.setPortType(portType);
		 reply.setVariable(input);
         
         BpelCorrelation corr_add= new BpelCorrelation("no", "JOB_CS");
         reply.addCorrelation(corr_add);
         
         bpelCompositeActivity.addChild(reply);
    }
	
	public void connect(String activ1_name, String activ2_name)
	{
		Node activ1 = bpelCompositeActivity.getChild(activ1_name);
		Node activ2 = bpelCompositeActivity.getChild(activ2_name);
		
		bpelCompositeActivity.connect(activ1, activ2);
		
	}
	
	public void connect(String activ1_name, String activ2_name, String scope_name)
	{
		Node activ1 = ((BpelScopeActivity)bpelCompositeActivity.getChild(scope_name)).getChild(activ1_name);
		Node activ2 = ((BpelScopeActivity)bpelCompositeActivity.getChild(scope_name)).getChild(activ2_name);
		
		((BpelScopeActivity)bpelCompositeActivity.getChild(scope_name)).connect(activ1, activ2);
		
	}
	public void connectToScopeStart(String activ1_name, String scope_name)
	{
		
		Node activ2 =(BpelScopeActivity)bpelCompositeActivity.getChild(scope_name);
		Node activ1 = ((BpelScopeActivity)activ2).getChild(activ1_name);
		
		bpelCompositeActivity.connect(((BpelScopeActivity)activ2).getStartEvent(), activ1);
		
	}
	
	public void connectToScopeEnd(String activ1_name, String scope_name)
	{
		Node activ2 =(BpelScopeActivity)bpelCompositeActivity.getChild(scope_name);
		Node activ1 = ((BpelScopeActivity)activ2).getChild(activ1_name);
		
		bpelCompositeActivity.connect(activ1, ((BpelScopeActivity)activ2).getEndEvent());
		
	}
	public void connectToFlow(int i, String start_activ, String end_activ)
	{
		Node activ1 = bpelCompositeActivity.getChild(start_activ);
		Node activ2 = bpelCompositeActivity.getChild(end_activ);
		
		bpelCompositeActivity.connect(bpelCompositeActivity.getChild("FlowSplit"+i).getNewControlOutputPort(), ((ActivityImpl)activ1).getControlInputPort());
		bpelCompositeActivity.connect(((ActivityImpl)activ2).getControlOutputPort(), bpelCompositeActivity.getChild("FlowJoin"+i).getNewControlInputPort());
		
	}
	public void connectToFlow(int i, String end_activ)
	{
		Node activ = bpelCompositeActivity.getChild(end_activ);
		bpelCompositeActivity.connect(((ActivityImpl)activ).getControlOutputPort(), bpelCompositeActivity.getChild("FlowJoin"+i).getNewControlInputPort());
		
	}
	public void connectBetweenFlow(int i, int j, String start_activ, String end_activ)
	{
		Node activ1 = bpelCompositeActivity.getChild(start_activ);
		Node activ2 = bpelCompositeActivity.getChild(end_activ);
		
		//TransitionImpl transition = new TransitionImpl(((BpelAndJoin)bpelCompositeActivity.getChild("FlowJoin"+i)).getControlOutputPort(), ((BpelAndJoin)bpelCompositeActivity.getChild("FlowJoin"+j)).getControlInputPorts().get(0));
		//bpelCompositeActivity.removeTransition(transition);
		
		bpelCompositeActivity.connect(((BpelAndJoin)bpelCompositeActivity.getChild("FlowJoin"+i)).getControlOutputPort(), ((ActivityImpl)activ1).getControlInputPort());
		bpelCompositeActivity.connect(((ActivityImpl)activ2).getControlOutputPort(), bpelCompositeActivity.getChild("FlowJoin"+j).getNewControlInputPort());
		
	}
	
	public void connectFlowToFlow(int i, int j)
	{
		bpelCompositeActivity.connect(bpelCompositeActivity.getChild("FlowSplit"+j).getNewControlOutputPort(), ((BpelAndSplit)bpelCompositeActivity.getChild("FlowSplit"+i)).getControlInputPort());
		last_node = "FlowSplit"+i;
		//bpelCompositeActivity.connect(((BpelAndJoin)bpelCompositeActivity.getChild("FlowJoin"+i)).getControlOutputPort(), bpelCompositeActivity.getChild("FlowJoin"+j).getNewControlInputPort());
	}
	
	public void connectToEnd(String node)
	{
		bpelCompositeActivity.connect(bpelCompositeActivity.getChild(node), bpelCompositeActivity.getEndEvent());
	}
	
	public void connectEndFlowToFlow(int i, int j)
	{
		//bpelCompositeActivity.connect(bpelCompositeActivity.getChild("FlowSplit"+j).getNewControlOutputPort(), ((BpelAndSplit)bpelCompositeActivity.getChild("FlowSplit"+i)).getControlInputPort());
		//last_node = "FlowSplit"+i;
		bpelCompositeActivity.connect(((BpelAndJoin)bpelCompositeActivity.getChild("FlowJoin"+i)).getControlOutputPort(), bpelCompositeActivity.getChild("FlowJoin"+j).getNewControlInputPort());
	}
	public void updateLastNode(String node_name)
	{
		last_node = new String(node_name);
		
	}
	
	public void connectToLastNode(String node_name)
	{
		Node node = bpelCompositeActivity.getChild(node_name);
		Node LastNode =  bpelCompositeActivity.getChild(last_node);
		
		//System.out.println("qname for last node:"+bpelCompositeActivity.getChild(last_node).getQualifiedName());
		//System.out.println("class for last node:"+bpelCompositeActivity.getChild(last_node).getClass().toString());
		//System.out.println("class for current node:"+bpelCompositeActivity.getChild(node_name).getClass().toString());
		if(bpelCompositeActivity.getChild(last_node).getClass() == BpelAssignActivity.class 
				|| bpelCompositeActivity.getChild(last_node).getClass() == BpelInvokeActivity.class 
				|| bpelCompositeActivity.getChild(last_node).getClass() == BpelCompositeReceiveActivity.class )
		{
			if(bpelCompositeActivity.getChild(node_name).getClass() == BpelAssignActivity.class 
					|| bpelCompositeActivity.getChild(node_name).getClass() == BpelInvokeActivity.class 
					|| bpelCompositeActivity.getChild(node_name).getClass() == BpelCompositeReceiveActivity.class 
					)
				bpelCompositeActivity.connect(bpelCompositeActivity.getChild(last_node),bpelCompositeActivity.getChild(node_name));
			else if(bpelCompositeActivity.getChild(node_name).getClass() == BpelScopeActivity.class)
				bpelCompositeActivity.connect(bpelCompositeActivity.getChild(last_node),bpelCompositeActivity.getChild(node_name));
		}
		else if (bpelCompositeActivity.getChild(last_node).getClass() == BpelAndJoin.class )
		{
			if(bpelCompositeActivity.getChild(node_name).getClass() == BpelAssignActivity.class 
					|| bpelCompositeActivity.getChild(node_name).getClass() == BpelInvokeActivity.class 
					|| bpelCompositeActivity.getChild(node_name).getClass() == BpelCompositeReceiveActivity.class )
			
				bpelCompositeActivity.connect(((BpelAndJoin)bpelCompositeActivity.getChild(last_node)).getControlOutputPort(),((BpelAssignActivity)bpelCompositeActivity.getChild(node_name)).getControlInputPort());
			else if(bpelCompositeActivity.getChild(node_name).getClass() == BpelScopeActivity.class)
				bpelCompositeActivity.connect(((BpelAndJoin)bpelCompositeActivity.getChild(last_node)).getControlOutputPort(),((BpelScopeActivity)bpelCompositeActivity.getChild(node_name)).getEndEvent().getControlInputPort());
		}
		else if (bpelCompositeActivity.getChild(last_node).getClass() == BpelAndSplit.class )
		{
			if(bpelCompositeActivity.getChild(node_name).getClass() == BpelAssignActivity.class 
					|| bpelCompositeActivity.getChild(node_name).getClass() == BpelInvokeActivity.class 
					|| bpelCompositeActivity.getChild(node_name).getClass() == BpelCompositeReceiveActivity.class )
			
				bpelCompositeActivity.connect(((BpelAndSplit)bpelCompositeActivity.getChild(last_node)).getNewControlOutputPort(),((BpelAssignActivity)bpelCompositeActivity.getChild(node_name)).getControlInputPort());
			else if(bpelCompositeActivity.getChild(node_name).getClass() == BpelScopeActivity.class)
				bpelCompositeActivity.connect(((BpelAndSplit)bpelCompositeActivity.getChild(last_node)).getNewControlOutputPort(),((BpelScopeActivity)bpelCompositeActivity.getChild(node_name)).getControlInputPort());
		}
	}
	
	public void serialize()
	{
		//just for testing
		//bpelCompositeActivity.connect(initial_receive, bpelCompositeActivity.getChild("Assign0"));
		//bpelCompositeActivity.connect(bpelCompositeActivity.getChild("Callback0"), bpelCompositeActivity.getEndEvent());
		///////////////////////////////////
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
		
		File bpel_file = new File(folder_Path+"/"+wf_name+"/"+wf_name+".bpel");
		BpelSerializer bpelSerializer = new BpelSerializer();
        bpelSerializer.serialize(process, bpel_file);
	}
	
	public void deploy(String url)
	{
		//Copy AdditiveSplitting wsdl and broker wsdl files to directory
		
		File additive_splittingWSDL = new File(SOCConfiguration.WSDLS_PATH+"/AdditiveSplittingArtifacts.wsdl");
		String filename = additive_splittingWSDL.getName();
		
		File additive_copy = new File(folder_Path+"/"+wf_name+"/"+filename);
		
		File brokerWSDL = new File(SOCConfiguration.WSDLS_PATH+"/BrokerServices.wsdl");
		String brokerfilename = brokerWSDL.getName();
		
		File broker_copy = new File(folder_Path+"/"+wf_name+"/"+brokerfilename);
		
		File cloudWSDL = new File(SOCConfiguration.WSDLS_PATH+"/CloudServices.wsdl");
		String cloudfilename = cloudWSDL.getName();
		
		File cloud_copy = new File(folder_Path+"/"+wf_name+"/"+cloudfilename);
		
		try {
			FileUtils.copyFile(additive_splittingWSDL, additive_copy);
			FileUtils.copyFile(brokerWSDL, broker_copy);
			FileUtils.copyFile(cloudWSDL, cloud_copy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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


	public BpelProcess getProcess() {
		return process;
	}


	public void setProcess(BpelProcess process) {
		this.process = process;
	}


	public String getBroker_services_url() {
		return broker_services_url;
	}


	public void setBroker_services_url(String broker_services_url) {
		this.broker_services_url = broker_services_url;//+"/BrokerServices/services/MatServ?wsdl";
	}


	public String getQueryLanguage() {
		return queryLanguage;
	}


	public void setQueryLanguage(String queryLanguage) {
		this.queryLanguage = queryLanguage;
	}


	public String getWSDL_SCHEMA() {
		return WSDL_SCHEMA;
	}


	public void setWSDL_SCHEMA(String wSDL_SCHEMA) {
		WSDL_SCHEMA = wSDL_SCHEMA;
	}


	public BpelCorrelationSet getJOB_CS() {
		return JOB_CS;
	}


	public void setJOB_CS(BpelCorrelationSet jOB_CS) {
		JOB_CS = jOB_CS;
	}


	public BpelCompositeActivity getBpelCompositeActivity() {
		return bpelCompositeActivity;
	}


	public void setBpelCompositeActivity(BpelCompositeActivity bpelCompositeActivity) {
		this.bpelCompositeActivity = bpelCompositeActivity;
	}


	public BpelCompositeReceiveActivity getInitial_receive() {
		return initial_receive;
	}


	public void setInitial_receive(BpelCompositeReceiveActivity initial_receive) {
		this.initial_receive = initial_receive;
	}


	public String getLast_node() {
		return last_node;
	}


	public void setLast_node(String last_node) {
		this.last_node = last_node;
	}


	public BpelScope getScope() {
		return scope;
	}


	public void setScope(BpelScope scope) {
		this.scope = scope;
	}


	public ArrayList<BpelVariable> getVariables() {
		return variables;
	}


	public void setVariables(ArrayList<BpelVariable> variables) {
		this.variables = variables;
	}


	public BpelPartnerLink getClient_PL() {
		return client_PL;
	}


	public void setClient_PL(BpelPartnerLink client_PL) {
		this.client_PL = client_PL;
	}


	public BpelPartnerLink getAdditiveSplitting_PL() {
		return AdditiveSplitting_PL;
	}


	public void setAdditiveSplitting_PL(BpelPartnerLink additiveSplitting_PL) {
		AdditiveSplitting_PL = additiveSplitting_PL;
	}


	public BpelPartnerLink getBroker_PL() {
		return Broker_PL;
	}


	public void setBroker_PL(BpelPartnerLink broker_PL) {
		Broker_PL = broker_PL;
	}
}

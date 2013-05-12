import java.io.ByteArrayInputStream;
import java.io.File;

import javax.naming.PartialResultException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.apache.xerces.dom.CDATASectionImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.impl.xs.opti.NodeImpl;
import org.unify_framework.abstract_syntax.CompositeActivity;

import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.impl.ExpressionImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.instances.bpel.BpelAndJoin;
import org.unify_framework.instances.bpel.BpelAndSplit;
import org.unify_framework.instances.bpel.BpelAssignActivity;
import org.unify_framework.instances.bpel.BpelCompositeActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCopy;
import org.unify_framework.instances.bpel.BpelCopyExpressionToExpressionActivity;
import org.unify_framework.instances.bpel.BpelCopyExpressionToVariableActivity;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelFrom;
import org.unify_framework.instances.bpel.BpelFromExpression;
import org.unify_framework.instances.bpel.BpelFromVariable;
import org.unify_framework.instances.bpel.BpelImport;
import org.unify_framework.instances.bpel.BpelInvokeActivity;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScope;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelToVariable;
import org.unify_framework.instances.bpel.BpelVariable;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.BpelVariableType;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.instances.bpel.visitors.ElementVisitor;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class BPELGenerator {
                                                            
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BpelProcess process = new BpelProcess("WF_Process", "http://matrix.bpelprocess");
		//process.addNamespaceDeclaration("bpel", "http://docs.oasis-open.org/wsbpel/2.0/process/executable");
		process.addNamespaceDeclaration("ns", "http://www.example.org/MatServ24/");
		process.addNamespaceDeclaration("ns1", "http://www.example.org/MatServ25/");
		process.addNamespaceDeclaration("ns2", "http://www.example.org/MatServ26/");
		process.addNamespaceDeclaration("ns3", "http://www.example.org/MatServ33/");
		process.addNamespaceDeclaration("ns4", "http://www.example.org/MatServ27/");
 	    process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
		process.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		process.addNamespaceDeclaration("ode", "http://www.apache.org/ode/type/extension");
		
		String queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0";
		process.setExitOnStandardFault("yes");
		process.setSuppressJoinFailure("yes");
		    
	   //process.setQueryLanguage(queryLanguage);
	   //process.setExpressionLanguage(queryLanguage);
	    		   
	    	    
	    	   try {
	              //create resource
	            File file = new File("WF_Process.bpel");
	            
	            BpelImport imp  = new BpelImport("http://matrix.bpelprocess", "WF_ProcessArtifacts.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            BpelImport imp2  = new BpelImport("http://www.example.org/MatServ24/", "MatServ24.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            BpelImport imp3  = new BpelImport("http://www.example.org/MatServ25/", "MatServ25.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            BpelImport imp4  = new BpelImport("http://www.example.org/MatServ26/", "MatServ26.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            BpelImport imp5  = new BpelImport("http://www.example.org/MatServ33/", "MatServ33.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            BpelImport imp6  = new BpelImport("http://www.example.org/MatServ27/", "MatServ27.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            
		    	    
	            BpelPartnerLink PL = new BpelPartnerLink("client", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  PL1 = new BpelPartnerLink("A1B1_PL", "tns:Mahout_PL24", null, "ServiceProvider24"); 
	            BpelPartnerLink  PL2 = new BpelPartnerLink("A2B2_PL", "tns:Mahout_PL25", null, "ServiceProvider25");
	            BpelPartnerLink  PL3 = new BpelPartnerLink("A1B2_PL", "tns:Mahout_PL26", null, "ServiceProvider26");
	            BpelPartnerLink  PL4 = new BpelPartnerLink("A2B1_PL", "tns:Mahout_PL33", null, "ServiceProvider33");
	            BpelPartnerLink  PL5 = new BpelPartnerLink("Add_PL", "tns:Mahout_PL27", null, "ServiceProvider27");
	            
	            BpelPartnerLink  CB_PL1 = new BpelPartnerLink("mult1_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL2 = new BpelPartnerLink("mult2_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL3 = new BpelPartnerLink("mult3_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL4 = new BpelPartnerLink("mult4_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL5 = new BpelPartnerLink("add_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            //BpelPartnerLink  CB_PL6 = new BpelPartnerLink("copy1_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            
	              
	            process.addPartnerLink(PL);process.addPartnerLink(PL1);process.addPartnerLink(PL2);
	            process.addPartnerLink(PL3);process.addPartnerLink(PL4);process.addPartnerLink(PL5);
	            process.addPartnerLink(CB_PL1);process.addPartnerLink(CB_PL2);
	            process.addPartnerLink(CB_PL3);process.addPartnerLink(CB_PL4);
	            process.addPartnerLink(CB_PL5);//process.addPartnerLink(CB_PL6);
	            process.addImport(imp);
	            process.addImport(imp2);
	            process.addImport(imp3);
	            process.addImport(imp4);
	            process.addImport(imp5);
	            process.addImport(imp6);
	            
	            BpelCorrelationSet JOB_CS = new BpelCorrelationSet("JOB_CS", "tns:jobid_CS");
	            process.addCorrelationSet(JOB_CS);
	            BpelCompositeActivity bpelCompositeActivity= (BpelCompositeActivity) process ;
	            
	            //BpelScopeActivity scope_activ = new BpelScopeActivity("main");
	    		BpelScope scope = bpelCompositeActivity.getScope();
	    		
	    		BpelVariable variable = new BpelVariableMessageType("input", "tns:WF_ProcessRequestMessage");
	    		scope.addVariable(variable);
	    		
	    		BpelVariable variable2 = new BpelVariableMessageType("output", "tns:WF_ProcessResponseMessage");
	    		scope.addVariable(variable2);
	    		
	    		variable = new BpelVariableMessageType("A1B1_PLRequest", "ns:computeRequest");
	    		scope.addVariable(variable);

	    		variable = new BpelVariableMessageType("A2B2_PLRequest", "ns1:computeRequest");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("A1B2_PLRequest", "ns2:computeRequest");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("A2B1_PLRequest", "ns3:computeRequest");
	    		scope.addVariable(variable);
	    		
	    		variable = new BpelVariableMessageType("C1C2_PLRequest", "ns4:computeRequest");
	    		scope.addVariable(variable);
	    		
	    		variable = new BpelVariableMessageType("mult1_PLRequest", "tns:multResult1Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("mult2_PLRequest", "tns:multResult2Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("mult3_PLRequest", "tns:multResult3Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("mult4_PLRequest", "tns:multResult4Request");
	    		scope.addVariable(variable);
	    		
	    	/*	variable = new BpelVariableMessageType("copy1_PLRequest", "tns:cpCallback1Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("copy2_PLRequest", "tns:cpCallback2Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("copy3_PLRequest", "tns:cpCallback3Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("copy4_PLRequest", "tns:cpCallback4Request");
	    		scope.addVariable(variable);
	    		*/    		
	    		variable = new BpelVariableMessageType("add_PLRequest", "tns:addResultRequest");
	    		scope.addVariable(variable);
	    		
	    		variable = new BpelVariableType("JOB", "xsd:int");
	    		scope.addVariable(variable);
	    		
	            /*
	            BpelReceiveActivity receive = new BpelReceiveActivity("ReceiveInput");
	            receive.setOperation("initiate");
	            receive.setPartnerLink("client");
	            receive.setPortType("tns:WF_Process");
	            receive.setVariable("input");
	            receive.setCreateInstance("yes");
	            */
	    	
	    		BpelCompositeReceiveActivity receive = new BpelCompositeReceiveActivity("receiveInput");
	    		receive.setOperation("process");
	            receive.setPartnerLink("client");
	            receive.setPortType("tns:WF_Process");
	            receive.setVariable("input");
	            receive.setCreateInstance("yes");
	            BpelCorrelation corr = new BpelCorrelation("yes", "JOB_CS");
	            receive.addCorrelation(corr);
	          
	            BpelAssignActivity assign = new BpelAssignActivity("Assign");
	            assign.setValidate("no");
/*
	            BpelCopyExpressionToExpressionActivity copy = new BpelCopyExpressionToExpressionActivity(null);
	            
	            XpathExpressionImpl source= new XpathExpressionImpl("<![CDATA[concat(\"Hello, \",$input.payload/tns:input, \"!\")]]>");
	            XpathExpressionImpl target= new XpathExpressionImpl("<![CDATA[$output.payload/tns:payload]]>");
	            copy.setSourceExpression(source);
	            copy.setTargetExpression(target);
	*/            
	            
	            BpelCopy copy = new BpelCopy();
	
	            XpathExpressionImpl expression= new XpathExpressionImpl("<![CDATA[concat(\"Hello, \",$input.payload/tns:input, \"!\")]]>");
	            //Node express=  new org.w3c.dom.Node() expression.getCopy();
	    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.newDocument();
				org.w3c.dom.Element literalElement= db.parse(new ByteArrayInputStream(new String("<bpel:literal><tns:WF_ProcessResponse xmlns:tns=\"http://matrix.bpelprocess\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
						  "<tns:result>tns:result</tns:result>"+
						  "<tns:instanceID>tns:instanceID</tns:instanceID>"+
						  "</tns:WF_ProcessResponse></bpel:literal>").getBytes())).getDocumentElement();
				
				BpelFromExpression from = new BpelFromExpression(literalElement);
			    copy.setFrom(from);
	            BpelToVariable to = new BpelToVariable("output");
	            to.setPart("payload");
	            //to.setQuery("tns:payload");
	            copy.setTo(to);
	            assign.addCopy(copy);
	        
	            BpelFromVariable from2 = new BpelFromVariable("input");
	            from2.setPart("payload");
	            //document.createCDATASection("<![CDATA[tns:input]]>");		
	            from2.setQuery("tns:input");
	            from2.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            BpelCopy copy2 = new BpelCopy();
	            copy2.setFrom(from2);
	            BpelToVariable to2 = new BpelToVariable("JOB");
	            copy2.setTo(to2);
	            assign.addCopy(copy2);
	            
	            
				
	            BpelCopy copy3 = new BpelCopy();
	            
	           
	            CDATASection cdata = document.createCDATASection("string($ode:pid)");
	            BpelFromExpression from3 = new BpelFromExpression(cdata.cloneNode(true));
	            
			    copy3.setFrom(from3);
	            BpelToVariable to3 = new BpelToVariable("output");
	            to3.setPart("payload");
	            to3.setQuery("tns:instanceID");
	            to3.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy3.setTo(to3);
	            assign.addCopy(copy3);
	        
	            CDATASection cdata2 = document.createCDATASection("concat($input.payload/tns:matA, $input.payload/tns:matB)");
	            BpelFromExpression from4 = new BpelFromExpression(cdata2.cloneNode(true));
	            BpelCopy copy4 = new BpelCopy();
			    copy4.setFrom(from4);
	            BpelToVariable to4 = new BpelToVariable("output");
	            to4.setPart("payload");
	            
	            to4.setQuery("tns:result");
	            to4.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy4.setTo(to4);
	            assign.addCopy(copy4);
	        

	            BpelReplyActivity reply = new BpelReplyActivity("replyOutput");
	            reply.setOperation("process");
	            reply.setPartnerLink("client");
	            reply.setVariable("output");
	            reply.setPortType("tns:WF_Process");
	          
	            BpelAndSplit split = new BpelAndSplit("Flow_split");
	            BpelAndJoin join = new BpelAndJoin("Flow_join");
	            
	         
	            split.setCorrespondingAndJoin(join);
	            join.setCorrespondingAndSplit(split);
	          
	            BpelAssignActivity assignA1B1 = new BpelAssignActivity("Assign1");
	            assignA1B1.setValidate("no");
	            BpelCopy copy_1 = new BpelCopy();
	           
	            String literalText ="<bpel:literal><tns:compute "
+                                "xmlns:tns=\"http://www.example.org/MatServ24/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
 +                               "<operation>multiply</operation>"
+								"<op_id>1</op_id>"
+                                "<job_id>0</job_id>"
+                                "<matA_ID>A1</matA_ID>"
+                                "<matB_ID>B1</matB_ID>"
+                                "<callback>multResult1</callback>"
+                            "</tns:compute></bpel:literal>"
                       ;
	            
	        	document = db.newDocument();
	        	literalElement= db.parse(new ByteArrayInputStream(new String(literalText).getBytes())).getDocumentElement();
				
	        	BpelFromExpression from_1 = new BpelFromExpression(literalElement);
				copy_1.setFrom(from_1);
				
				
				BpelToVariable to_1 = new BpelToVariable("A1B1_PLRequest");
				to_1.setPart("parameters");
	            //to_1.setQuery("tns:payload");
	            copy_1.setTo(to_1);
	            assignA1B1.addCopy(copy_1);

	            
				BpelCopy copy_2 = new BpelCopy();
				BpelFromVariable from_2 = new BpelFromVariable("JOB");
				copy_2.setFrom(from_2);
				BpelToVariable to_2 = new BpelToVariable("A1B1_PLRequest");
				
				to_2.setPart("parameters");
				CDATASection cdata_2_1 = document.createCDATASection("job_id");
				//to_2.setQuery("job_id");
				to_2.setQueryNode(cdata_2_1.cloneNode(true));
				to_2.addNamespaceDeclaration("queryLanguage", queryLanguage);
				copy_2.setTo(to_2);
				assignA1B1.addCopy(copy_2);
	        
				
				BpelCopy copy_3 = new  BpelCopy();
				CDATASection cdata_3 = document.createCDATASection("concat($input.payload/tns:matA, \"_1\")");
	            BpelFromExpression from_3 = new BpelFromExpression(cdata_3.cloneNode(true));
	            
			    copy_3.setFrom(from_3);
	            BpelToVariable to_3 = new BpelToVariable("A1B1_PLRequest");
	            to_3.setPart("parameters");
	            CDATASection cdata_3_1 = document.createCDATASection("matA_ID");
	            //to_3.setQuery("matA_ID");
	            to_3.setQueryNode(cdata_3_1.cloneNode(true));
	            to_3.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_3.setTo(to_3);
	            assignA1B1.addCopy(copy_3);
	        
	            BpelCopy copy_4 = new  BpelCopy();
				CDATASection cdata_4 = document.createCDATASection("concat($input.payload/tns:matB, \"_1\")");
	            BpelFromExpression from_4 = new BpelFromExpression(cdata_4.cloneNode(true));
	            copy_4.setFrom(from_4);
	            BpelToVariable to_4 = new BpelToVariable("A1B1_PLRequest");
	            to_4.setPart("parameters");
	            CDATASection cdata_4_1 = document.createCDATASection("matB_ID");
	            //to_4.setQuery("matB_ID");
	            to_4.setQueryNode(cdata_4_1.cloneNode(true));
	            to_4.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_4.setTo(to_4);
	            assignA1B1.addCopy(copy_4);
				
				     
	            BpelInvokeActivity A1B1 = new BpelInvokeActivity("Mult_A1B1");
	            A1B1.setOperation("compute");
	            A1B1.setPartnerLink("A1B1_PL");
	            A1B1.setPortType("ns:MatServ");
	            A1B1.setInputVariable("A1B1_PLRequest");
	            
	            BpelCompositeReceiveActivity CB_A1_B1= new BpelCompositeReceiveActivity("CB_A1_B1");
	          //  CB_A1_B1.setCreateInstance("no");
	            CB_A1_B1.setOperation("multResult1");
	            CB_A1_B1.setPartnerLink("mult1_PL");
	            CB_A1_B1.setPortType("tns:WF_Process");
	            CB_A1_B1.setVariable("mult1_PLRequest");
	            BpelCorrelation corr2 = new BpelCorrelation("no", "JOB_CS");
	            CB_A1_B1.addCorrelation(corr2);
	          

	            BpelAssignActivity assignA2B2 = new BpelAssignActivity("Assign2");
	            assignA2B2.setValidate("no");
	            BpelCopy copy_11 = new BpelCopy();
	           
	            String literalText2 ="<bpel:literal><tns:compute "
+                                "xmlns:tns=\"http://www.example.org/MatServ25/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
 +                               "<operation>multiply</operation>"
+								"<op_id>2</op_id>"
+                                "<job_id>0</job_id>"
+                                "<matA_ID>A2</matA_ID>"
+                                "<matB_ID>B2</matB_ID>"
+                                "<callback>multResult2</callback>"
+                            "</tns:compute></bpel:literal>"
                       ;
	            
	        	document = db.newDocument();
	        	literalElement= db.parse(new ByteArrayInputStream(new String(literalText2).getBytes())).getDocumentElement();
				
	        	BpelFromExpression from_11 = new BpelFromExpression(literalElement);
				copy_11.setFrom(from_11);
				
				
				BpelToVariable to_11 = new BpelToVariable("A2B2_PLRequest");
				to_11.setPart("parameters");
	            //to_11.setQuery("tns:payload");
	            copy_11.setTo(to_11);
	            assignA2B2.addCopy(copy_11);

	            
				BpelCopy copy_21 = new BpelCopy();
				BpelFromVariable from_21 = new BpelFromVariable("JOB");
				copy_21.setFrom(from_21);
				BpelToVariable to_21 = new BpelToVariable("A2B2_PLRequest");
				to_21.setPart("parameters");
				CDATASection cdata_1_1 = document.createCDATASection("job_id");
				
				to_21.setQueryNode(cdata_1_1.cloneNode(true));
				//to_21.setQuery("job_id");
				to_21.addNamespaceDeclaration("queryLanguage", queryLanguage);
				copy_21.setTo(to_21);
				assignA2B2.addCopy(copy_21);
	        
				
				BpelCopy copy_31 = new  BpelCopy();
				CDATASection cdata_31 = document.createCDATASection("concat($input.payload/tns:matA, \"_2\")");
	            BpelFromExpression from_31 = new BpelFromExpression(cdata_31.cloneNode(true));
	            
			    copy_31.setFrom(from_31);
	            BpelToVariable to_31 = new BpelToVariable("A2B2_PLRequest");
	            to_31.setPart("parameters");
	            CDATASection cdata_1_2 = document.createCDATASection("matA_ID");
				to_31.setQueryNode(cdata_1_2.cloneNode(true));
	            //to_31.setQuery("matA_ID");
	            to_31.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_31.setTo(to_31);
	            assignA2B2.addCopy(copy_31);
	        
	            BpelCopy copy_41 = new  BpelCopy();
				CDATASection cdata_41 = document.createCDATASection("concat($input.payload/tns:matB, \"_2\")");
	            BpelFromExpression from_41 = new BpelFromExpression(cdata_41.cloneNode(true));
	            
			    copy_41.setFrom(from_41);
	            BpelToVariable to_41 = new BpelToVariable("A2B2_PLRequest");
	            to_41.setPart("parameters");
	            //to_41.setQuery("matB_ID");
	            CDATASection cdata_1_3 = document.createCDATASection("matB_ID");
				to_41.setQueryNode(cdata_1_3.cloneNode(true));
	            to_41.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_41.setTo(to_41);
	            assignA2B2.addCopy(copy_41);

	            BpelInvokeActivity A2B2 = new BpelInvokeActivity("Mult_A2B2");
	            A2B2.setOperation("compute");
	            A2B2.setPartnerLink("A2B2_PL");
	            A2B2.setPortType("ns1:MatServ");
	            A2B2.setInputVariable("A2B2_PLRequest");
	            
	            BpelCompositeReceiveActivity CB_A2_B2= new BpelCompositeReceiveActivity("CB_A2_B2");
	            //CB_A2_B2.setCreateInstance("no");
	            CB_A2_B2.setOperation("multResult2");
	            CB_A2_B2.setPartnerLink("mult2_PL");
	            CB_A2_B2.setPortType("tns:WF_Process");
	            CB_A2_B2.setVariable("mult2_PLRequest");
	            BpelCorrelation corr3 = new BpelCorrelation("no", "JOB_CS");
	            CB_A2_B2.addCorrelation(corr3);
	           
	      
	            BpelAssignActivity assignA1B2 = new BpelAssignActivity("Assign3");
	            assignA1B2.setValidate("no");
	            BpelCopy copy_12 = new BpelCopy();
	           
	            String literalText3 ="<bpel:literal><tns:compute "
+                                "xmlns:tns=\"http://www.example.org/MatServ26/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
 +                               "<operation>multiply</operation>"
+								"<op_id>3</op_id>"
+                                "<job_id>0</job_id>"
+                                "<matA_ID>A1</matA_ID>"
+                                "<matB_ID>B2</matB_ID>"
+                                "<callback>multResult3</callback>"
+                            "</tns:compute></bpel:literal>"
                       ;
	            
	        	document = db.newDocument();
	        	literalElement= db.parse(new ByteArrayInputStream(new String(literalText3).getBytes())).getDocumentElement();
				
	        	BpelFromExpression from_12 = new BpelFromExpression(literalElement);
				copy_12.setFrom(from_12);
				
				
				BpelToVariable to_12 = new BpelToVariable("A1B2_PLRequest");
				to_12.setPart("parameters");
	            //to_12.setQuery("tns:payload");
	            copy_12.setTo(to_12);
	            assignA1B2.addCopy(copy_12);

	            
				BpelCopy copy_22 = new BpelCopy();
				BpelFromVariable from_22 = new BpelFromVariable("JOB");
				copy_22.setFrom(from_22);
				BpelToVariable to_22 = new BpelToVariable("A1B2_PLRequest");
				to_22.setPart("parameters");
				//to_22.setQuery("job_id");
				CDATASection cdata_2_2 = document.createCDATASection("job_id");
				to_22.setQueryNode(cdata_2_2.cloneNode(true));
				to_22.addNamespaceDeclaration("queryLanguage", queryLanguage);
				copy_22.setTo(to_22);
				assignA1B2.addCopy(copy_22);
	        
				
				BpelCopy copy_32 = new  BpelCopy();
				CDATASection cdata_32 = document.createCDATASection("concat($input.payload/tns:matA, \"_1\")");
	            BpelFromExpression from_32 = new BpelFromExpression(cdata_32.cloneNode(true));
	            
			    copy_32.setFrom(from_32);
	            BpelToVariable to_32 = new BpelToVariable("A1B2_PLRequest");
	            to_32.setPart("parameters");
	            //to_32.setQuery("matA_ID");
	            CDATASection cdata_3_2 = document.createCDATASection("matA_ID");
				to_32.setQueryNode(cdata_3_2.cloneNode(true));
	            to_32.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_32.setTo(to_32);
	            assignA1B2.addCopy(copy_32);
	        
	            BpelCopy copy_42 = new  BpelCopy();
				CDATASection cdata_42 = document.createCDATASection("concat($input.payload/tns:matB, \"_2\")");
	            BpelFromExpression from_42 = new BpelFromExpression(cdata_42.cloneNode(true));
	            
			    copy_42.setFrom(from_42);
	            BpelToVariable to_42 = new BpelToVariable("A1B2_PLRequest");
	            to_42.setPart("parameters");
	            //to_42.setQuery("matB_ID");
	            CDATASection cdata_4_2 = document.createCDATASection("matB_ID");
				to_42.setQueryNode(cdata_4_2.cloneNode(true));
	            to_42.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_42.setTo(to_42);
	            assignA1B2.addCopy(copy_42);


	            BpelInvokeActivity A1B2 = new BpelInvokeActivity("Mult_A1B2");
	            A1B2.setOperation("compute");
	            A1B2.setPartnerLink("A1B2_PL");
	            A1B2.setPortType("ns2:MatServ");
	            A1B2.setInputVariable("A1B2_PLRequest");
	            
	            BpelCompositeReceiveActivity CB_A1_B2= new BpelCompositeReceiveActivity("CB_A1_B2");
	            //CB_A1_B2.setCreateInstance("no");
	            CB_A1_B2.setOperation("multResult3");
	            CB_A1_B2.setPartnerLink("mult3_PL");
	            CB_A1_B2.setPortType("tns:WF_Process");
	            CB_A1_B2.setVariable("mult3_PLRequest");
	            BpelCorrelation corr4 = new BpelCorrelation("no", "JOB_CS");
	            CB_A1_B2.addCorrelation(corr4);
	          
	            
	            
	            BpelAssignActivity assignA2B1 = new BpelAssignActivity("Assign4");
	            assignA2B1.setValidate("no");
	            BpelCopy copy_13 = new BpelCopy();
	           
	            String literalText4 ="<bpel:literal><tns:compute "
+                                "xmlns:tns=\"http://www.example.org/MatServ33/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
 +                               "<operation>multiply</operation>"
+								"<op_id>4</op_id>"
+                                "<job_id>0</job_id>"
+                                "<matA_ID>A2</matA_ID>"
+                                "<matB_ID>B1</matB_ID>"
+                                "<callback>multResult4</callback>"
+                            "</tns:compute></bpel:literal>"
                       ;
	            
	        	document = db.newDocument();
	        	literalElement= db.parse(new ByteArrayInputStream(new String(literalText4).getBytes())).getDocumentElement();
				
	        	BpelFromExpression from_13 = new BpelFromExpression(literalElement);
				copy_13.setFrom(from_13);
				
				
				BpelToVariable to_13 = new BpelToVariable("A2B1_PLRequest");
				to_13.setPart("parameters");
	            //to_13.setQuery("tns:payload");
	            copy_13.setTo(to_13);
	            assignA2B1.addCopy(copy_13);

	            
				BpelCopy copy_23 = new BpelCopy();
				BpelFromVariable from_23 = new BpelFromVariable("JOB");
				copy_23.setFrom(from_23);
				BpelToVariable to_23 = new BpelToVariable("A2B1_PLRequest");
				to_23.setPart("parameters");
				//to_23.setQuery("job_id");
				CDATASection cdata_2_3 = document.createCDATASection("job_id");
				to_23.setQueryNode(cdata_2_3.cloneNode(true));
				to_23.addNamespaceDeclaration("queryLanguage", queryLanguage);
				copy_23.setTo(to_23);
				assignA2B1.addCopy(copy_23);
	        
				
				BpelCopy copy_33 = new  BpelCopy();
				CDATASection cdata_33 = document.createCDATASection("concat($input.payload/tns:matA, \"_2\")");
	            BpelFromExpression from_33 = new BpelFromExpression(cdata_33.cloneNode(true));
	            
			    copy_33.setFrom(from_33);
	            BpelToVariable to_33 = new BpelToVariable("A2B1_PLRequest");
	            to_33.setPart("parameters");
	            //to_33.setQuery("matA_ID");
	            CDATASection cdata_2_4 = document.createCDATASection("matA_ID");
				to_33.setQueryNode(cdata_2_4.cloneNode(true));
	            to_33.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_33.setTo(to_33);
	            assignA2B1.addCopy(copy_33);
	        
	            BpelCopy copy_43 = new  BpelCopy();
				CDATASection cdata_43 = document.createCDATASection("concat($input.payload/tns:matB, \"_1\")");
	            BpelFromExpression from_43 = new BpelFromExpression(cdata_43.cloneNode(true));
	            
			    copy_43.setFrom(from_43);
	            BpelToVariable to_43 = new BpelToVariable("A2B1_PLRequest");
	            to_43.setPart("parameters");
	            //to_43.setQuery("matB_ID");
	            CDATASection cdata_2_5 = document.createCDATASection("matB_ID");
				to_43.setQueryNode(cdata_2_5.cloneNode(true));
	            to_43.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_43.setTo(to_43);
	            assignA2B1.addCopy(copy_43);

	            BpelInvokeActivity A2B1 = new BpelInvokeActivity("Mult_A2B1");
	            A2B1.setOperation("compute");
	            A2B1.setPartnerLink("A2B1_PL");
	            A2B1.setPortType("ns4:MatServ");
	            A2B1.setInputVariable("A2B1_PLRequest");
	            
	            
	            BpelCompositeReceiveActivity CB_A2_B1= new BpelCompositeReceiveActivity("CB_A2_B1");
	            CB_A2_B1.setOperation("multResult4");
	            CB_A2_B1.setPartnerLink("mult4_PL");
	            CB_A2_B1.setPortType("tns:WF_Process");
	            CB_A2_B1.setVariable("mult4_PLRequest");
	            //CB_A2_B1.setCreateInstance("no");
	            BpelCorrelation corr5 = new BpelCorrelation("no", "JOB_CS");
	            CB_A2_B1.addCorrelation(corr5);
	    
	            
	            BpelAssignActivity assignAdd = new BpelAssignActivity("Assign5");
	            assignAdd.setValidate("no");
	            BpelCopy copy_14 = new BpelCopy();
	           
	            String literalText5 ="<bpel:literal><tns:compute "
+                                "xmlns:tns=\"http://www.example.org/MatServ27/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
 +                               "<operation>add</operation>"
+								"<op_id>5</op_id>"
+                                "<job_id>0</job_id>"
+                                "<matA_ID>A</matA_ID>"
+                                "<matB_ID>B</matB_ID>"
+                                "<callback>addResult</callback>"
+                            "</tns:compute></bpel:literal>"
                       ;
	            
	        	document = db.newDocument();
	        	literalElement= db.parse(new ByteArrayInputStream(new String(literalText5).getBytes())).getDocumentElement();
				
	        	BpelFromExpression from_14 = new BpelFromExpression(literalElement);
				copy_14.setFrom(from_14);
				
				
				BpelToVariable to_14 = new BpelToVariable("C1C2_PLRequest");
				to_14.setPart("parameters");
	            //to_14.setQuery("tns:payload");
	            copy_14.setTo(to_14);
	            assignAdd.addCopy(copy_14);

	            
				BpelCopy copy_24 = new BpelCopy();
				BpelFromVariable from_24 = new BpelFromVariable("JOB");
				copy_24.setFrom(from_24);
				BpelToVariable to_24 = new BpelToVariable("C1C2_PLRequest");
				to_24.setPart("parameters");
				//to_24.setQuery("job_id");
				CDATASection cdata_add = document.createCDATASection("job_id");
				to_24.setQueryNode(cdata_add.cloneNode(true));
				to_24.addNamespaceDeclaration("queryLanguage", queryLanguage);
				copy_24.setTo(to_24);
				assignAdd.addCopy(copy_24);
	        
				
				BpelCopy copy_34 = new  BpelCopy();
				CDATASection cdata_34 = document.createCDATASection("$input.payload/tns:matA");
	            BpelFromExpression from_34 = new BpelFromExpression(cdata_34.cloneNode(true));
	            
			    copy_34.setFrom(from_34);
	            BpelToVariable to_34 = new BpelToVariable("C1C2_PLRequest");
	            to_34.setPart("parameters");
	            //to_34.setQuery("matA_ID");
	            CDATASection cdata_add_1 = document.createCDATASection("matA_ID");
				to_34.setQueryNode(cdata_add_1.cloneNode(true));
	            to_34.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_34.setTo(to_34);
	            assignAdd.addCopy(copy_34);
	        
	            BpelCopy copy_44 = new  BpelCopy();
				CDATASection cdata_44 = document.createCDATASection("$input.payload/tns:matB");
	            BpelFromExpression from_44 = new BpelFromExpression(cdata_44.cloneNode(true));
	            
			    copy_44.setFrom(from_44);
	            BpelToVariable to_44 = new BpelToVariable("C1C2_PLRequest");
	            to_44.setPart("parameters");
	            //to_44.setQuery("matB_ID");
	            CDATASection cdata_add_2 = document.createCDATASection("matB_ID");
				to_44.setQueryNode(cdata_add_2.cloneNode(true));
	            to_44.addNamespaceDeclaration("queryLanguage", queryLanguage);
	            copy_44.setTo(to_44);
	            assignAdd.addCopy(copy_44);

	            /*
	             *   
        <bpel:assign name="Assign3" validate="no">
            <bpel:copy>
                <bpel:from>
                    <bpel:literal xml:space="preserve"><tns:compute xmlns:tns="http://www.example.org/MatServ27/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <operation>add</operation>
                            <op_id>3</op_id>
                            <job_id>0</job_id>
                            <matA_ID>C1</matA_ID>
                            <matB_ID>C3</matB_ID>
                            <callback>addResult</callback>
                        </tns:compute></bpel:literal>
                </bpel:from>
                <bpel:to part="parameters" variable="C1C2_PLRequest"/>
            </bpel:copy>
            
            <bpel:copy>
                <bpel:from>
                    <![CDATA[$input.payload/tns:matA]]>
                </bpel:from>
                <bpel:to part="parameters" variable="C1C2_PLRequest">
                    <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[matA_ID]]></bpel:query>
                </bpel:to>
            </bpel:copy>
            <bpel:copy>
                <bpel:from>
                    <![CDATA[$input.payload/tns:matB]]>
                </bpel:from>
                <bpel:to part="parameters" variable="C1C2_PLRequest">
                    <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[matB_ID]]></bpel:query>
                </bpel:to>
            </bpel:copy>
            <bpel:copy>
                <bpel:from variable="JOB"></bpel:from>
                <bpel:to part="parameters" variable="C1C2_PLRequest">
                    <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[job_id]]></bpel:query>
                </bpel:to>
            </bpel:copy>
        </bpel:assign>
    
        
	             */
	            BpelInvokeActivity invokeAdd = new BpelInvokeActivity("Add");
	            invokeAdd.setOperation("compute");
	            invokeAdd.setPartnerLink("Add_PL");
	            invokeAdd.setPortType("ns4:MatServ");
	            invokeAdd.setInputVariable("C1C2_PLRequest");
	            
	            BpelCompositeReceiveActivity add_callback= new BpelCompositeReceiveActivity("Receive2");
	            //add_callback.setCreateInstance("no");
	            add_callback.setOperation("addResult");
	            add_callback.setPartnerLink("add_PL");
	            add_callback.setPortType("tns:WF_Process");
	            add_callback.setVariable("add_PLRequest");

	            BpelCorrelation corr_add= new BpelCorrelation("no", "JOB_CS");
	            add_callback.addCorrelation(corr_add);
	           
	            
	            bpelCompositeActivity.addChild(receive);
	            bpelCompositeActivity.addChild(assign);
	            bpelCompositeActivity.addChild(reply);
	           
	            
	            bpelCompositeActivity.addChild(split);
	            bpelCompositeActivity.addChild(assignA1B1);
	            bpelCompositeActivity.addChild(A1B1);
	            bpelCompositeActivity.addChild(CB_A1_B1);
	            bpelCompositeActivity.addChild(assignA2B2);
	            bpelCompositeActivity.addChild(A2B2);
	            bpelCompositeActivity.addChild(CB_A2_B2);
	            bpelCompositeActivity.addChild(assignA1B2);
	            bpelCompositeActivity.addChild(A1B2);
	            bpelCompositeActivity.addChild(CB_A1_B2);
	            bpelCompositeActivity.addChild(assignA2B1);
	            bpelCompositeActivity.addChild(A2B1);
	            bpelCompositeActivity.addChild(CB_A2_B1);
	            
	            bpelCompositeActivity.addChild(assignAdd);
	            bpelCompositeActivity.addChild(invokeAdd);
	            bpelCompositeActivity.addChild(add_callback);
	            
	            bpelCompositeActivity.addChild(join);
	            
	            bpelCompositeActivity.connect(bpelCompositeActivity.getStartEvent(), receive);
	            bpelCompositeActivity.connect(receive,assign);
	            bpelCompositeActivity.connect(assign, reply);
	            //bpelCompositeActivity.connect(reply, bpelCompositeActivity.getEndEvent());
	            bpelCompositeActivity.connect(reply.getControlOutputPort(), split.getControlInputPort());
	            
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(),assignA1B1.getControlInputPort());
	            bpelCompositeActivity.connect(assignA1B1.getControlOutputPort(), A1B1.getControlInputPort());
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(), assignA2B2.getControlInputPort());
	            bpelCompositeActivity.connect(assignA2B2.getControlOutputPort(),A2B2.getControlInputPort());
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(),  assignA1B2.getControlInputPort());
	            bpelCompositeActivity.connect(assignA1B2.getControlOutputPort(),A1B2.getControlInputPort());
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(),  assignA2B1.getControlInputPort());
	            bpelCompositeActivity.connect(assignA2B1.getControlOutputPort(),A2B1.getControlInputPort());
	            
	            bpelCompositeActivity.connect(A1B1, CB_A1_B1);
	            bpelCompositeActivity.connect(A2B2, CB_A2_B2);
	            bpelCompositeActivity.connect(A1B2, CB_A1_B2);
	            bpelCompositeActivity.connect(A2B1, CB_A2_B1);
	            
	            bpelCompositeActivity.connect(CB_A1_B1.getControlOutputPort(), join.getNewControlInputPort());
	            bpelCompositeActivity.connect(CB_A2_B2.getControlOutputPort(), join.getNewControlInputPort());
	            bpelCompositeActivity.connect(CB_A1_B2.getControlOutputPort(), join.getNewControlInputPort());
	            bpelCompositeActivity.connect(CB_A2_B1.getControlOutputPort(), join.getNewControlInputPort());
	            
	            bpelCompositeActivity.connect(join.getControlOutputPort(),assignAdd.getControlInputPort());
	            bpelCompositeActivity.connect(assignAdd,invokeAdd);
	            bpelCompositeActivity.connect(invokeAdd,add_callback);
	            bpelCompositeActivity.connect(add_callback.getControlOutputPort(), bpelCompositeActivity.getEndEvent().getControlInputPort());
	            //bpelCompositeActivity.getEndEvent().getControlInputPort()
	            //process.addActivity(receive);
	            //process.addActivity(reply);
	            process.setScope(scope);
	          
	            BpelSerializer bpelSerializer = new BpelSerializer();
	            bpelSerializer.serialize(process, file);
	            
	        }

	        catch (Exception e) {
	              e.printStackTrace();
	        }

		
	}

}

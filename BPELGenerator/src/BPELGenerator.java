import java.io.File;

import javax.naming.PartialResultException;


import org.apache.xerces.impl.xs.opti.NodeImpl;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.Node;
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


public class BPELGenerator {
                                                            
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BpelProcess process = new BpelProcess("WF_Process", "http://matrix.bpelprocess");
		process.addNamespaceDeclaration("bpel", "http://docs.oasis-open.org/wsbpel/2.0/process/executable");
		process.addNamespaceDeclaration("ns", "http://www.example.org/MatServ24/");
		process.addNamespaceDeclaration("ns1", "http://www.example.org/MatServ25/");
		process.addNamespaceDeclaration("ns2", "http://www.example.org/MatServ26/");
		process.addNamespaceDeclaration("ns3", "http://www.example.org/MatServ33/");
		process.addNamespaceDeclaration("ns4", "http://www.example.org/MatServ27/");
 	    process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
		process.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		process.addNamespaceDeclaration("ode", "http://www.apache.org/ode/type/extension");
		
		process.setExitOnStandardFault("yes");
		process.setSuppressJoinFailure("yes");
		    
	   
	    		   
	    	    
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
	            BpelPartnerLink  PL1 = new BpelPartnerLink("A1B1_PL", "tns:Mahout_PL", null, "ServiceProvider"); 
	            BpelPartnerLink  PL2 = new BpelPartnerLink("A2B2_PL", "tns:Mahout_PL", null, "ServiceProvider");
	            BpelPartnerLink  PL3 = new BpelPartnerLink("A1B2_PL", "tns:Mahout_PL", null, "ServiceProvider");
	            BpelPartnerLink  PL4 = new BpelPartnerLink("A2B1_PL", "tns:Mahout_PL", null, "ServiceProvider");
	            BpelPartnerLink  PL5 = new BpelPartnerLink("Add_PL", "tns:Mahout_PL", null, "ServiceProvider");
	            
	            BpelPartnerLink  CB_PL1 = new BpelPartnerLink("mult1_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL2 = new BpelPartnerLink("mult2_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL3 = new BpelPartnerLink("mult3_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL4 = new BpelPartnerLink("mult4_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL5 = new BpelPartnerLink("add_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            BpelPartnerLink  CB_PL6 = new BpelPartnerLink("copy1_PL", "tns:WF_Process", "WF_ProcessProvider", null);
	            
	              
	            process.addPartnerLink(PL);process.addPartnerLink(PL1);process.addPartnerLink(PL2);
	            process.addPartnerLink(PL3);process.addPartnerLink(PL4);process.addPartnerLink(PL5);
	            process.addPartnerLink(CB_PL1);process.addPartnerLink(CB_PL2);
	            process.addPartnerLink(CB_PL3);process.addPartnerLink(CB_PL4);
	            process.addPartnerLink(CB_PL5);process.addPartnerLink(CB_PL6);
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

	    		variable = new BpelVariableMessageType("A2B2_PLRequest", "ns:computeRequest");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("A1B2_PLRequest", "ns:computeRequest");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("A2B1_PLRequest", "ns:computeRequest");
	    		scope.addVariable(variable);
	    		
	    		variable = new BpelVariableMessageType("C1C2_PLRequest", "ns:computeRequest");
	    		scope.addVariable(variable);
	    		
	    		variable = new BpelVariableMessageType("mult1_PLRequest", "tns:multResult1Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("mult2_PLRequest", "tns:multResult2Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("mult3_PLRequest", "tns:multResult3Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("mult4_PLRequest", "tns:multResult4Request");
	    		scope.addVariable(variable);
	    		
	    		variable = new BpelVariableMessageType("copy1_PLRequest", "tns:cpCallback1Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("copy2_PLRequest", "tns:cpCallback2Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("copy3_PLRequest", "tns:cpCallback3Request");
	    		scope.addVariable(variable);
	    		variable = new BpelVariableMessageType("copy4_PLRequest", "tns:cpCallback4Request");
	    		scope.addVariable(variable);
	    		    		
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
	    	
	    		BpelCompositeReceiveActivity receive = new BpelCompositeReceiveActivity("ReceiveWithCorr");
	    		receive.setOperation("initiate");
	            receive.setPartnerLink("client");
	            receive.setPortType("tns:WF_Process");
	            receive.setVariable("input");
	            receive.setCreateInstance("yes");
	            BpelCorrelation corr = new BpelCorrelation("yes", "JOB_CS");
	            receive.addCorrelation(corr);
	          
	            BpelAssignActivity assign = new BpelAssignActivity("concatInputMsg");
	            
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
	            BpelFromVariable from = new BpelFromVariable("input");
	            
	            from.setPart("payload");
	            from.setQuery("tns:input");
	            
	            
	            copy.setFrom(from);
	            BpelToVariable to = new BpelToVariable("output");
	            to.setPart("payload");
	            to.setQuery("tns:payload");
	            copy.setTo(to);
	            
	            assign.addCopy(copy);
	            
	            
	            //BpelCopyExpressionToVariableActivity copy_Activ = new BpelCopyExpressionToVariableActivity(null);
	            
	        /*    BpelFromVariable from = new BpelFromVariable("input");
	            
	            from.setPart("payload");
	            from.setQuery("tns:input");
	            */
	            
	            BpelReplyActivity reply = new BpelReplyActivity("ReplyToClient");
	            reply.setOperation("initiate");
	            reply.setPartnerLink("client");
	            reply.setVariable("output");
	            reply.setPortType("tns:WF_Process");
	          
	            BpelAndSplit split = new BpelAndSplit("Flow_split");
	            BpelAndJoin join = new BpelAndJoin("Flow_join");
	            
	         
	            split.setCorrespondingAndJoin(join);
	            join.setCorrespondingAndSplit(split);
	          
	            BpelAssignActivity assignA1B1 = new BpelAssignActivity("Assign1");
	            BpelCopy copy_1 = new BpelCopy();
	            XpathExpressionImpl express= new XpathExpressionImpl("<bpel:literal><tns:compute"
+                                "xmlns:tns=\"http://www.example.org/MatServ24/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
 +                               "<operation>multiply</operation>"
+								"<op_id>1</op_id>"
+                                "<job_id>0</job_id>"
+                                "<matA_ID>A1</matA_ID>"
+                                "<matB_ID>B1</matB_ID>"
+                                "<callback>multResult1</callback>"
+                            "</tns:compute>"
+                        "</bpel:literal>");
	            //BpelFromExpression from1 = new BpelFromExpression(new express);
	            /*
	            <bpel:assign name="Assign1" validate="no">
                <bpel:copy>
                    <bpel:from>
                        <bpel:literal>
                            <tns:compute
                                xmlns:tns="http://www.example.org/MatServ24/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <operation>multiply</operation>
                                <op_id>1</op_id>
                                <job_id>0</job_id>
                                <matA_ID>A1</matA_ID>
                                <matB_ID>B1</matB_ID>
                                <callback>multResult1</callback>
                            </tns:compute>
                        </bpel:literal>
                    </bpel:from>
                    <bpel:to part="parameters" variable="A1B1_PLRequest"/>
                </bpel:copy>
                <bpel:copy>
                    <bpel:from variable="JOB"/>
                    <bpel:to part="parameters" variable="A1B1_PLRequest">
                        <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[job_id]]></bpel:query>
                    </bpel:to>
                </bpel:copy>
                <bpel:copy>
                    <bpel:from>
                        
                    <![CDATA[concat($input.payload/tns:matA, "_1")]]>
                    </bpel:from>
                    <bpel:to part="parameters" variable="A1B1_PLRequest">
                        <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[matA_ID]]></bpel:query>
                    </bpel:to>
                </bpel:copy>
                <bpel:copy>
                    <bpel:from>
                        <![CDATA[concat($input.payload/tns:matB,"_1")]]>
                    </bpel:from>
                    <bpel:to part="parameters" variable="A1B1_PLRequest">
                        <bpel:query queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"><![CDATA[matB_ID]]></bpel:query>
                    </bpel:to>
                </bpel:copy>
            </bpel:assign>
            */       
	            BpelInvokeActivity A1B1 = new BpelInvokeActivity("Mult_A1B1");
	            A1B1.setInputVariable("A1B1_PLRequest");
	            A1B1.setOperation("compute");
	            A1B1.setPartnerLink("A1B1_PL");
	            A1B1.setPortType("ns:MatServ24");
	            
	            BpelInvokeActivity A2B2 = new BpelInvokeActivity("Mult_A2B2");
	            A2B2.setInputVariable("A2B2_PLRequest");
	            A2B2.setOperation("compute");
	            A2B2.setPartnerLink("A2B2_PL");
	            A2B2.setPortType("ns:MatServ25");
	            
	            BpelCompositeReceiveActivity CB_A1_B1= new BpelCompositeReceiveActivity("CB_A1_B1");
	            CB_A1_B1.setOperation("multResult1");
	            CB_A1_B1.setPartnerLink("mult1_PL");
	            CB_A1_B1.setPortType("tns:WF_Process");
	            CB_A1_B1.setVariable("mult1_PLRequest");
	            BpelCorrelation corr2 = new BpelCorrelation("no", "JOB_CS");
	            CB_A1_B1.addCorrelation(corr2);
	          
	            BpelCompositeReceiveActivity CB_A2_B2= new BpelCompositeReceiveActivity("CB_A2_B2");
	            CB_A2_B2.setOperation("multResult2");
	            CB_A2_B2.setPartnerLink("mult2_PL");
	            CB_A2_B2.setPortType("tns:WF_Process");
	            CB_A2_B2.setVariable("mult2_PLRequest");
	            BpelCorrelation corr3 = new BpelCorrelation("no", "JOB_CS");
	            CB_A2_B2.addCorrelation(corr3);
	           
	            
	            BpelInvokeActivity A1B2 = new BpelInvokeActivity("Mult_A1B2");
	            A1B2.setInputVariable("A1B2_PLRequest");
	            A1B2.setOperation("compute");
	            A1B2.setPartnerLink("A1B2_PL");
	            A1B2.setPortType("ns:MatServ26");
	            
	            BpelInvokeActivity A2B1 = new BpelInvokeActivity("Mult_A2B1");
	            A2B1.setInputVariable("A2B1_PLRequest");
	            A2B1.setOperation("compute");
	            A2B1.setPartnerLink("A2B1_PL");
	            A2B1.setPortType("ns:MatServ33");
	            
	            BpelCompositeReceiveActivity CB_A1_B2= new BpelCompositeReceiveActivity("CB_A1_B2");
	            CB_A1_B2.setOperation("multResult3");
	            CB_A1_B2.setPartnerLink("mult3_PL");
	            CB_A1_B2.setPortType("tns:WF_Process");
	            CB_A1_B2.setVariable("mult3_PLRequest");
	            BpelCorrelation corr4 = new BpelCorrelation("no", "JOB_CS");
	            CB_A1_B2.addCorrelation(corr4);
	          
	            BpelCompositeReceiveActivity CB_A2_B1= new BpelCompositeReceiveActivity("CB_A2_B1");
	            CB_A2_B1.setOperation("multResult4");
	            CB_A2_B1.setPartnerLink("mult4_PL");
	            CB_A2_B1.setPortType("tns:WF_Process");
	            CB_A2_B1.setVariable("mult4_PLRequest");
	            BpelCorrelation corr5 = new BpelCorrelation("no", "JOB_CS");
	            CB_A2_B1.addCorrelation(corr5);
	           
	            
	            bpelCompositeActivity.addChild(receive);
	            bpelCompositeActivity.addChild(assign);
	            bpelCompositeActivity.addChild(reply);
	           
	            
	            bpelCompositeActivity.addChild(split);
	            bpelCompositeActivity.addChild(A1B1);
	            bpelCompositeActivity.addChild(A2B2);
	            bpelCompositeActivity.addChild(CB_A1_B1);
	            bpelCompositeActivity.addChild(CB_A2_B2);
	            bpelCompositeActivity.addChild(join);
	            
	            bpelCompositeActivity.connect(bpelCompositeActivity.getStartEvent(), receive);
	            bpelCompositeActivity.connect(receive,assign);
	            bpelCompositeActivity.connect(assign, reply);
	            //bpelCompositeActivity.connect(reply, bpelCompositeActivity.getEndEvent());
	            bpelCompositeActivity.connect(reply.getControlOutputPort(), split.getControlInputPort());
	            
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(), A1B1.getControlInputPort());
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(), A2B2.getControlInputPort());
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(), A1B2.getControlInputPort());
	            bpelCompositeActivity.connect(split.getNewControlOutputPort(), A2B1.getControlInputPort());
	            
	            bpelCompositeActivity.connect(A1B1, CB_A1_B1);
	            bpelCompositeActivity.connect(A2B2, CB_A2_B2);
	            bpelCompositeActivity.connect(A1B2, CB_A1_B2);
	            bpelCompositeActivity.connect(A2B1, CB_A2_B1);
	            
	            bpelCompositeActivity.connect(CB_A1_B1.getControlOutputPort(), join.getNewControlInputPort());
	            bpelCompositeActivity.connect(CB_A2_B2.getControlOutputPort(), join.getNewControlInputPort());
	            bpelCompositeActivity.connect(CB_A1_B2.getControlOutputPort(), join.getNewControlInputPort());
	            bpelCompositeActivity.connect(CB_A2_B1.getControlOutputPort(), join.getNewControlInputPort());
	            
	            bpelCompositeActivity.connect(join.getControlOutputPort(), bpelCompositeActivity.getEndEvent().getControlInputPort());
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

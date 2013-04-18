import java.io.File;

import javax.naming.PartialResultException;


import org.apache.xerces.impl.xs.opti.NodeImpl;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.data_perspective.Expression;
import org.unify_framework.abstract_syntax.data_perspective.impl.ExpressionImpl;
import org.unify_framework.abstract_syntax.data_perspective.impl.XpathExpressionImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
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
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.BpelReceiveActivity;
import org.unify_framework.instances.bpel.BpelReplyActivity;
import org.unify_framework.instances.bpel.BpelScope;
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
		//process.addNamespaceDeclaration("ns", "http://www.example.org/MatServ/");
		process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
		process.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		    
	        try {
	              //create resource
	            File file = new File("WF_Process.bpel");
	            
	            BpelImport imp  = new BpelImport("http://matrix.bpelprocess", "WF_ProcessArtifacts.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            BpelImport imp2  = new BpelImport("http://www.example.org/MatServ/", "WF_ProcessArtifacts.wsdl", "http://schemas.xmlsoap.org/wsdl/");
	            
	            
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
	            
	            BpelCorrelationSet JOB_CS = new BpelCorrelationSet("JOB_CS", "tns:jobid_CS");
	            process.addCorrelationSet(JOB_CS);
	            BpelCompositeActivity bpelCompositeActivity= (BpelCompositeActivity) process ;
	            
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
	          
	            bpelCompositeActivity.addChild(receive);
	            bpelCompositeActivity.addChild(reply);
	            bpelCompositeActivity.connect(bpelCompositeActivity.getStartEvent(), receive);
	            bpelCompositeActivity.connect(receive,assign);
	            bpelCompositeActivity.connect(assign, reply);
	            bpelCompositeActivity.connect(reply, bpelCompositeActivity.getEndEvent());
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

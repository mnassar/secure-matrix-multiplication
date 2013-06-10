package broker.workflow.translate.expression;

import groovy.xml.MarkupBuilder;

import java.io.StringWriter;
import java.util.List;

import javax.xml.namespace.QName;


import com.predic8.schema.ComplexType;
import com.predic8.schema.Schema;
import com.predic8.schema.Sequence;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Import;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.creator.WSDLCreator;
import com.predic8.wsdl.creator.WSDLCreatorContext;

public class WSDLGenerator {

		 
		  public static void main(String[] args) {
		    dumpWSDL(createWSDL());
		  }
		 
		  private static Definitions  createWSDL() {
		    Schema schema = new Schema("http://predic8.com/add/1/");
		     
		    schema.newElement("add").newComplexType().newSequence().newElement("summand", "INT").setMaxOccurs("unbounded");
		    schema.newElement("addResponse").newComplexType().newSequence().newElement("number", "INT");
		    
		    schema.newElement("WF_ProcessRequest","RequestType");
		    
		    ComplexType type = schema.newComplexType("RequestType");
		    type.newAttribute("input", new QName("INT"));
		    Sequence seq = type.newSequence();
		    seq.newElement("matA",new QName("STRING") );
		    seq.newElement("matB", new QName("STRING") );
		    

		    schema.newElement("WF_ProcessResponse").newComplexType().newSequence().newElement("result", "string");
		    schema.getElement("WF_ProcessResponse").newComplexType().newSequence().newElement("instanceID", "string");
		    
		    Definitions wsdl = new Definitions("http://predic8.com/wsdl/AddService/1/", "AddService");
		    wsdl.add(schema);
		    
		    List<Import> import_list = wsdl.getImports();
			
		    Import 	imp = new Import();
		    imp.setLocation("Additive_Splitting.wsdl");
		    imp.setNamespace("http://matrix.bpelprocess");
		    
		    import_list.add(imp);
		     
		    imp = new Import();
		    imp.setLocation("Broker_Services.wsdl");
		    imp.setNamespace("http://services.broker");
		    
		    import_list.add(imp);
		    
		    wsdl.setImports(import_list);
		    
		  
		     
		    PortType pt = wsdl.newPortType("AddPortType");
		    Operation op = pt.newOperation("add");
		     
		    op.newInput("add").newMessage("add").newPart("parameters", "tns:add");
		    op.newOutput("addResponse").newMessage("addResponse").newPart("parameters", "tns:addResponse");
		    return wsdl;
		  }
		 
		  private static void dumpWSDL(Definitions wsdl) {
		    StringWriter writer = new StringWriter();
		    WSDLCreator creator = new WSDLCreator();
		    creator.setBuilder(new MarkupBuilder(writer));
		    wsdl.create(creator, new WSDLCreatorContext());
		    System.out.println(writer);
		  }
		}
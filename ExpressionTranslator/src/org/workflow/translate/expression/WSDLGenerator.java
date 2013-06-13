package org.workflow.translate.expression;

import groovy.xml.MarkupBuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.namespace.QName;

import java.io.FileWriter;


import com.predic8.schema.Annotation;
import com.predic8.schema.Attribute;
import com.predic8.schema.ComplexType;
import com.predic8.schema.Schema;
import com.predic8.schema.Sequence;
import com.predic8.wsdl.AbstractAddress;
import com.predic8.wsdl.Binding;
import com.predic8.wsdl.BindingInput;
import com.predic8.wsdl.BindingOperation;
import com.predic8.wsdl.BindingOutput;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Import;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.creator.WSDLCreator;
import com.predic8.wsdl.creator.WSDLCreatorContext;

public class WSDLGenerator {

	
	Definitions wsdl;
	static Schema schema ;
	List<Import> import_list;
	List<Binding> binding_list;
	List<BindingOperation> binding_operations;
	List<Operation> operations; 
	
	public WSDLGenerator(String namepace, String service_name, String schema_namespace )
	{
		wsdl= new Definitions(namepace, service_name);
		
		schema = new Schema(schema_namespace);
		import_list= wsdl.getImports();
		binding_list = wsdl.getBindings();
		
	}
	
	public void addElement()
	{
		
	}
	public void addComplexType(String name, HashMap<String, String> name_type_pairs)
	{
		ComplexType complex = schema.newElement(name).newComplexType();
		Sequence sequence = complex.newSequence();
		for(String key : name_type_pairs.keySet())
			sequence.newElement(key, name_type_pairs.get(key));
	}
	
	
	public void addPortType(String name)
	{
		  PortType pt = wsdl.newPortType(name);
	}
	public void addRequestResponseOperation(String name, String input, String output, String portType)
	{
		Operation op =  wsdl.getPortType(portType).newOperation(name);
		op.newInput(input).newMessage(input+"Message").newPart("parameters", "tns:"+input);
		op.newOutput(output).newMessage(output+"Message").newPart("parameters", "tns:"+output);
		
	}
	public void addOneWayOperation(String name, String input, String portType)
	{
		Operation op =  wsdl.getPortType(portType).newOperation(name);
		op.newInput(input).newMessage(input+"Request").newPart("parameters", "tns:"+input);
		
	}
	public void addImport(String location, String namespace)
	{
		Import 	imp = new Import();
	    imp.setLocation(location);
	    imp.setNamespace(namespace);
	    
	    import_list.add(imp);
	}
	
	public void addService(String name, String port_name, String address)
	{
		 wsdl.newService(name).newPort(port_name).newSOAP11Address(address);
		
		 /*
		Service service = new Service();
		service.setName(name);
		Port port = new Port();
		port.setName(port_name);
	
		wsdl.getServices().add(service);
		*/
	}
	
	public  void addBinding(String name, String portType, String service, String port)
	{
		int i=0;
		for( i=0; i< wsdl.getServices().size(); i++)
		{
			if(wsdl.getServices().get(i).getName().equals(service))
				break;
		}
		
	    
	    int j=0;
		for( j=0; j< wsdl.getServices().get(i).getPorts().size(); j++)
		{
			if( wsdl.getServices().get(i).getPorts().get(j).getName().equals(port))
				break;
		}
	   
		Binding binding = wsdl.getServices().get(i).getPorts().get(j).newBinding(name);
		binding.setType(wsdl.getPortType(portType));
		binding.newSOAP11Binding();
		//binding_list.add(binding);
	}
	
	public  void addBindingOperation(String name,  String soap_Action, String binding)
	{
		BindingOperation oper = new BindingOperation();
		oper.newSOAP11Operation().setSoapAction(soap_Action);
		oper.setName(name);
			
		int i=0;
		for( i=0; i< binding_list.size(); i++)
		{
			if(binding_list.get(i).getName().equals(binding))
				break;
		}
		if(i!= binding_list.size())
			binding_list.get(i).getOperations().add(oper);
		
		
		oper.setBinding(binding_list.get(i));
		oper.newInput();
		//.newSOAP11Body();
		oper.newOutput();//.newSOAP11Body();
		
	}
	
	public  void write()
	{
	    wsdl.setImports(import_list);
	    wsdl.setBindings(binding_list);
	    wsdl.add(schema);
		StringWriter writer = new StringWriter();
	    WSDLCreator creator = new WSDLCreator();
	    creator.setBuilder(new MarkupBuilder(writer));
	    wsdl.create(creator, new WSDLCreatorContext());
	    System.out.println(writer);	
	}
	
	
	public  void writeToFile(String filePath) throws IOException
	{
		
		
		 wsdl.setImports(import_list);
		    wsdl.setBindings(binding_list);
		    wsdl.add(schema);
		java.io.FileWriter fw = new FileWriter(filePath);
		StringWriter writer = new StringWriter();
	    WSDLCreator creator = new WSDLCreator();
	    creator.setBuilder(new MarkupBuilder(writer));
	    wsdl.create(creator, new WSDLCreatorContext());
	    fw.write(writer.toString());
		fw.close();
	//	System.out.println(schema.getAsString());
		
		
			
		
	    System.out.println(writer);	
	}
	
		 
	public static void main(String[] args) throws WSDLException {
		
		/*
		WSDLFactory factory = WSDLFactory.newInstance();
		Definition def= factory.newDefinition();
		
		Types types = def.createTypes();
		
		types.addExtensibilityElement(new javax.wsdl.extensions.schema. );
		
		for( Object o : def.getTypes().getExtensibilityElements()) {
			if( o instanceof javax.wsdl.extensions.schema.Schema ) {
				org.w3c.dom.Element elt = ((javax.wsdl.extensions.schema.Schema) o).getElement();
				// Navigate in the DOM model of the schema
				// You can use Schema#getImport() to work with imports
			}
		javax.wsdl.extensions.schema.Schema sch = .get();
		
		*/
		
		
		HashMap<String, String> sequence = new HashMap<String, String>();
		sequence.put("input", "int");
		sequence.put("matA", "string");
		sequence.put("matB", "string");
	
		WSDLGenerator generator = new WSDLGenerator("http://matrix.bpelprocess", "WF_Process", "http://schema.matrix.bpelprocess");
		
		generator.addImport("Additive_Splitting.wsdl", "http://matrix.bpelprocess");
        generator.addImport("Broker_Services.wsdl", "http://services.broker");
		generator.addComplexType("WF_ProcessRequest", sequence);
		sequence.clear();
		sequence.put("result" , "string");
		sequence.put("instanceID" , "string");
		generator.addComplexType("WF_ProcessResponse", sequence);
		
		sequence.clear();
		sequence.put("in" , "string");
		sequence.put("op_id" , "int");
		sequence.put("job_id" , "int");
		generator.addComplexType("multResult1", sequence);
		
		sequence.clear();
		sequence.put("out" , "string");
		generator.addComplexType("multResult1Response", sequence);
		
   
		sequence.clear();
		sequence.put("in" , "string");
		sequence.put("op_id" , "int");
		sequence.put("job_id" , "int");
		generator.addComplexType("multResult2", sequence);
		
		sequence.clear();
		sequence.put("out" , "string");
		generator.addComplexType("multResult2Response", sequence);
	
		sequence.clear();
		sequence.put("in" , "string");
		sequence.put("op_id" , "int");
		sequence.put("job_id" , "int");
		generator.addComplexType("multResult3", sequence);
		
		sequence.clear();
		sequence.put("out" , "string");
		generator.addComplexType("multResult3Response", sequence);
	
		sequence.clear();
		sequence.put("in" , "string");
		sequence.put("op_id" , "int");
		sequence.put("job_id" , "int");
		generator.addComplexType("multResult4", sequence);
		
		sequence.clear();
		sequence.put("out" , "string");
		generator.addComplexType("multResult4Response", sequence);
	
		sequence.clear();
		sequence.put("in" , "string");
		sequence.put("op_id" , "int");
		sequence.put("job_id" , "int");
		generator.addComplexType("addResult", sequence);
		
		sequence.clear();
		sequence.put("out" , "string");
		generator.addComplexType("addResultResponse", sequence);
	
		sequence.clear();
		sequence.put("in" , "string");
		sequence.put("op_id" , "int");
		sequence.put("job_id" , "int");
		generator.addComplexType("cpCallback1", sequence);
		generator.addComplexType("cpCallback2", sequence);
		generator.addComplexType("cpCallback3", sequence);
		generator.addComplexType("cpCallback4", sequence);
	
		
		generator.addPortType("WF_ProcessPortType");
		generator.addRequestResponseOperation("process", "WF_ProcessRequest", "WF_ProcessResponse", "WF_ProcessPortType");
		generator.addService("WF_Process", "WF_ProcessPort", "http://10.160.2.27:8080/ode/processes/WF_Process");
		    
		generator.addBinding("WF_ProcessBinding", "WF_ProcessPortType","WF_Process","WF_ProcessPort");
		generator.addBindingOperation("process","http://matrix.bpelprocess/process", "WF_ProcessBinding");
		   
		 //   generator.write();
		    try {
				generator.writeToFile("WF_ProcessWSDL.wsdl");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//    dumpWSDL(createWSDL());
}
		 /*
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
		    
		    wsdl= new Definitions("http://predic8.com/wsdl/AddService/1/", "AddService");     
		    wsdl.add(schema);
		    

		    import_list= wsdl.getImports();
			
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
		 */
		  private static void dumpWSDL(Definitions wsdl) {
		    StringWriter writer = new StringWriter();
		    WSDLCreator creator = new WSDLCreator();
		    creator.setBuilder(new MarkupBuilder(writer));
		    wsdl.create(creator, new WSDLCreatorContext());
		    System.out.println(writer);
		  }
		}
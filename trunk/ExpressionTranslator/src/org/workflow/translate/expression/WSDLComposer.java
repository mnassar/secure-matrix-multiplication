package org.workflow.translate.expression;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;

import org.eclipse.jdt.internal.core.builder.JavaBuilder;
import org.jdom.input.DOMBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.wsdl.BindingImpl;
import com.ibm.wsdl.BindingInputImpl;
import com.ibm.wsdl.BindingOperationImpl;
import com.ibm.wsdl.BindingOutputImpl;
import com.ibm.wsdl.Constants;
import com.ibm.wsdl.DefinitionImpl;
import com.ibm.wsdl.ImportImpl;
import com.ibm.wsdl.InputImpl;
import com.ibm.wsdl.MessageImpl;
import com.ibm.wsdl.OperationImpl;
import com.ibm.wsdl.OutputImpl;
import com.ibm.wsdl.PartImpl;
import com.ibm.wsdl.PortImpl;
import com.ibm.wsdl.PortTypeImpl;
import com.ibm.wsdl.ServiceImpl;
import com.ibm.wsdl.TypesImpl;
import com.ibm.wsdl.extensions.soap.SOAPAddressImpl;
import com.ibm.wsdl.extensions.soap.SOAPBindingImpl;
import com.ibm.wsdl.extensions.soap.SOAPBodyImpl;
import com.ibm.wsdl.extensions.soap.SOAPOperationImpl;
import com.ibm.wsdl.factory.WSDLFactoryImpl;
import com.ibm.wsdl.xml.WSDLWriterImpl;


public class WSDLComposer {

	DefinitionImpl definition;
	Element schema;
	Document doc ;
	TypesImpl types ;
	WSDLWriterImpl writer;
	String targetNamespace;
	Element partnerLinkType;
	
	public WSDLComposer(String namespace, String service_name )
	{
		WSDLFactoryImpl factory = null;
		//Constants constants = new Constants();
		//SOAPConstants soapconstants = new SOAPConstants();

		try {
		factory = (WSDLFactoryImpl) WSDLFactoryImpl.newInstance();
		}
		catch (WSDLException we) {
		System.out.println("wsdl exception " + we.getMessage());
		}

		// get a new definition from the factory
		 definition= (DefinitionImpl) factory.newDefinition();

		// add target namespace and additional namespaces that might be there
		definition.setTargetNamespace(namespace);
		targetNamespace = new String(namespace);
		// specify the service name
		QName qname = new QName(namespace,service_name);
		definition.setQName(qname);
		
		
		writer
		= (com.ibm.wsdl.xml.WSDLWriterImpl)factory.newWSDLWriter();

		
		// create types **********************************************************
		 types = (TypesImpl) definition.createTypes();

		
		// generate a schema in DOM to set into the types object
		javax.xml.parsers.DocumentBuilderFactory domfactory =
		javax.xml.parsers.DocumentBuilderFactory.newInstance();

		javax.xml.parsers.DocumentBuilder builder = null;

		try {
		builder = domfactory.newDocumentBuilder();
		}
		catch (javax.xml.parsers.ParserConfigurationException pce) {
		System.out.println("parser config exception " + pce.getMessage());
		}
		DOMImplementation dImpl = builder.getDOMImplementation();

		// namespace for the schema
		String namespaceURI = "http://www.w3.org/2001/XMLSchema";

		 doc = dImpl.createDocument(namespaceURI, "schema", null);

		schema = doc.getDocumentElement();

		//schema.setPrefix("xsd");
		schema.setAttribute("xmlns", "http://www.w3.org/2001/XMLSchema");
		schema.setAttribute("targetNamespace", namespace);

		
		schema.setAttribute("attributeFormDefault", "unqualified");
		schema.setAttribute("elementFormDefault", "qualified");

	}
	
	public void addNameSpace(String prefix, String namespace)
	{
		definition.addNamespace(prefix, namespace);
	}
	public void addElement(String name, String type)
	{
		Element first = doc.createElement("element");
		first.setAttribute("name", name);
		first.setAttribute("type", type);
		schema.appendChild(first);
	}
	public void addComplexType(String name, HashMap<String, String> name_type_pairs)
	{
		Element sequence = null;

		Element complex = doc.createElement("complexType");
		complex.setAttribute("name", name);

		sequence = doc.createElement("sequence");

		for(String key : name_type_pairs.keySet())
		{
			Element part1 = doc.createElement("element");
			part1.setAttribute("name", key);
			part1.setAttribute("type",  name_type_pairs.get(key));
			sequence.appendChild(part1);
		}
		complex.appendChild(sequence);

		schema.appendChild(complex);
		
	}
	
	public void addMessage( String msg_name, HashMap<String, String> partname_elementtype_pairs)
	{
		// add the messages*******************************************************
		MessageImpl message1 = (MessageImpl)definition.createMessage();

		QName messageqname= new QName(targetNamespace, msg_name);
		message1.setQName(messageqname);

		for(String key : partname_elementtype_pairs.keySet())
		{
			PartImpl partma = (PartImpl)definition.createPart();
			partma.setName(key);
			partma.setElementName(new QName(targetNamespace, partname_elementtype_pairs.get(key)));
			message1.addPart(partma);
		}
		message1.setUndefined(false);
		definition.addMessage(message1);
	}
	
	public void addPartnerLinkType(String name, String rolename, String portType)
	{
		// generate a schema in DOM to set into the types object
		javax.xml.parsers.DocumentBuilderFactory domfactory =
		javax.xml.parsers.DocumentBuilderFactory.newInstance();

		javax.xml.parsers.DocumentBuilder builder = null;

		try {
		builder = domfactory.newDocumentBuilder();
		}
		catch (javax.xml.parsers.ParserConfigurationException pce) {
		System.out.println("parser config exception " + pce.getMessage());
		}
		DOMImplementation dImpl = builder.getDOMImplementation();

		// namespace for the schema
		String namespaceURI = "http://docs.oasis-open.org/wsbpel/2.0/plnktype";

		 doc = dImpl.createDocument(namespaceURI, "partnerLinkType", null);

		partnerLinkType = doc.getDocumentElement();

		partnerLinkType.setPrefix("plnk");
		partnerLinkType.setAttribute("name", name);
		
		//partnerLinkType.setTextContent("<plnk:role  name=\""+rolename+" portType=\""+portType+"\" />");
		   
		
		Element role = doc.createElement("plnk:role");
		role.setAttribute("name", rolename);
		role.setAttribute("portType", portType);
		partnerLinkType.appendChild(role);

		

		// show the schema w3c element via conversion JDOM Element and coversion to string
		DOMBuilder jdbuilder = new org.jdom.input.DOMBuilder();
		org.jdom.Element jelement = jdbuilder.build(partnerLinkType);
		//System.out.println("\njelement " + jelement);
		XMLOutputter xmloutput = new org.jdom.output.XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setIndent(" ");
		format.setEncoding("ISO-8859-1");
		format.setLineSeparator("\n");
		
		xmloutput.setFormat(format);
	//	String strelement = xmloutput.outputString(jelement);
		//System.out.println("schema in jdom = \n " + strelement);

		UnknownExtensibilityElement extel = new UnknownExtensibilityElement();
		extel.setElement(partnerLinkType);
		extel.setElementType(new QName(partnerLinkType.getNamespaceURI(),partnerLinkType.getLocalName()));
		
		definition.addExtensibilityElement(extel);
		
	}

	public void addProperty(String name, String type)
	{
		Element property;
		// generate a schema in DOM to set into the types object
		javax.xml.parsers.DocumentBuilderFactory domfactory =
				javax.xml.parsers.DocumentBuilderFactory.newInstance();

		javax.xml.parsers.DocumentBuilder builder = null;

		try {
			builder = domfactory.newDocumentBuilder();
		}
		catch (javax.xml.parsers.ParserConfigurationException pce) {
			System.out.println("parser config exception " + pce.getMessage());
		}
		DOMImplementation dImpl = builder.getDOMImplementation();

		// namespace for the schema
		String namespaceURI = "http://docs.oasis-open.org/wsbpel/2.0/varprop";

		doc = dImpl.createDocument(namespaceURI, "property", null);

		property = doc.getDocumentElement();

		property.setPrefix("vprop");
		property.setAttribute("name", name);
		property.setAttribute("type", type);

		// show the schema w3c element via conversion JDOM Element and coversion to string
		DOMBuilder jdbuilder = new org.jdom.input.DOMBuilder();
		org.jdom.Element jelement = jdbuilder.build(property);
		//System.out.println("\njelement " + jelement);
		XMLOutputter xmloutput = new org.jdom.output.XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setIndent(" ");
		format.setEncoding("ISO-8859-1");
		format.setLineSeparator("\n");

		xmloutput.setFormat(format);
		
		UnknownExtensibilityElement extel = new UnknownExtensibilityElement();
		extel.setElement(property);
		extel.setElementType(new QName(property.getNamespaceURI(),property.getLocalName()));

		definition.addExtensibilityElement(extel);
	}
	
	public void addPropertyAlias(String messageType, String part, String propertyName, String queryString)
	{
		Element propertyAlias;
		// generate a schema in DOM to set into the types object
		javax.xml.parsers.DocumentBuilderFactory domfactory =
				javax.xml.parsers.DocumentBuilderFactory.newInstance();

		javax.xml.parsers.DocumentBuilder builder = null;

		try {
			builder = domfactory.newDocumentBuilder();
		}
		catch (javax.xml.parsers.ParserConfigurationException pce) {
			System.out.println("parser config exception " + pce.getMessage());
		}
		DOMImplementation dImpl = builder.getDOMImplementation();

		// namespace for the schema
		String namespaceURI = "http://docs.oasis-open.org/wsbpel/2.0/varprop";

		doc = dImpl.createDocument(namespaceURI, "propertyAlias", null);

		propertyAlias = doc.getDocumentElement();

		propertyAlias.setPrefix("vprop");
		propertyAlias.setAttribute("messageType","tns:"+messageType);
		propertyAlias.setAttribute("part",part);
		propertyAlias.setAttribute("propertyName","tns:"+propertyName);

		if(queryString!= null)
		{
			Element query = doc.createElement("query");
			query.setTextContent(queryString);
			propertyAlias.appendChild(query);
		}
		// show the schema w3c element via conversion JDOM Element and coversion to string
		DOMBuilder jdbuilder = new org.jdom.input.DOMBuilder();
		org.jdom.Element jelement = jdbuilder.build(propertyAlias);
		//System.out.println("\njelement " + jelement);
		XMLOutputter xmloutput = new org.jdom.output.XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setIndent(" ");
		format.setEncoding("ISO-8859-1");
		format.setLineSeparator("\n");

		xmloutput.setFormat(format);
		
		UnknownExtensibilityElement extel = new UnknownExtensibilityElement();
		extel.setElement(propertyAlias);
		extel.setElementType(new QName(propertyAlias.getNamespaceURI(),propertyAlias.getLocalName()));

		definition.addExtensibilityElement(extel);
	}
	public void addPortType(String name)
	{
		PortTypeImpl porttype = (PortTypeImpl)definition.createPortType();

		porttype.setQName(new QName(targetNamespace, name));
		porttype.setUndefined(false);
		definition.addPortType(porttype);
	}
	
	public void addRequestResponseOperation(String name, String inputMessageName, String outputMessageName, String portType)
	{
		QName messageqname= new QName(targetNamespace, portType);
		
		OperationImpl operation = (OperationImpl)definition.createOperation();
		operation.setName(name);
		operation.setStyle(javax.wsdl.OperationType.REQUEST_RESPONSE);

		InputImpl input = (InputImpl)definition.createInput();
		//input.setName(inputMessageName);
		input.setMessage(definition.getMessage(new QName(targetNamespace, inputMessageName)));
        
		operation.setInput(input);
		
		OutputImpl output = (OutputImpl)definition.createOutput();
		//output.setName(outputMessageName);
		output.setMessage(definition.getMessage(new QName(targetNamespace, outputMessageName)));
	
		operation.setOutput(output);
		operation.setUndefined(false);
		definition.getPortType(messageqname).addOperation(operation);
				
	}
	
	public void addOneWayOperation(String name, String inputMessageName,  String portType)
	{
	QName messageqname= new QName(targetNamespace, portType);
		
		OperationImpl operation = (OperationImpl)definition.createOperation();
		operation.setName(name);
		operation.setStyle(javax.wsdl.OperationType.ONE_WAY);

		InputImpl input = (InputImpl)definition.createInput();
		//input.setName(inputMessageName);
		input.setMessage(definition.getMessage(new QName(targetNamespace, inputMessageName)));

		operation.setInput(input);
		operation.setUndefined(false);
		definition.getPortType(messageqname).addOperation(operation);

	}
	
	public void addImport(String location, String namespace)
	{
		ImportImpl 	imp =  (ImportImpl)definition.createImport();
		imp.setLocationURI(location);
		imp.setNamespaceURI(namespace);
		definition.addImport(imp);
	}
	
	public void addService(String name, String port_name, String binding_name, String address)
	{
		// adding service*********************************************************
		ServiceImpl service = (ServiceImpl)definition.createService();
		service.setQName(new QName(targetNamespace,name));

		PortImpl port = (PortImpl)definition.createPort();

		port.setBinding(definition.getBinding(new QName(targetNamespace,binding_name)));
		port.setName(port_name);

		SOAPAddressImpl soapaddress = new SOAPAddressImpl();
		soapaddress.setLocationURI(address);

		port.addExtensibilityElement(soapaddress);

		service.addPort(port);
		
		definition.addService(service);
	
	}
	
	public  void addBinding(String name, String portType)
	{

		// add the bindings*******************************************************
		BindingImpl binding = (BindingImpl)definition.createBinding();

		binding.setQName(new QName(targetNamespace,name));

		binding.setPortType(definition.getPortType(new QName(targetNamespace, portType)));

		SOAPBindingImpl bindingextension = new SOAPBindingImpl();
		bindingextension.setStyle("document");
		bindingextension.setTransportURI("http://schemas.xmlsoap.org/soap/http");
		binding.addExtensibilityElement(bindingextension);
		binding.setUndefined(false);
		
		definition.addBinding(binding);
		
		// done adding binding

		///////////////////////
		
	}
	
	public  void addBindingOperation(String name,  String soap_Action, String binding)
	{
		BindingOperationImpl bindingoperation= (BindingOperationImpl)definition.createBindingOperation();
		bindingoperation.setName(name);
		QName bindingQname = new QName(targetNamespace, binding);
		
		OperationImpl operation=null;
		for(Object o : definition.getBinding(bindingQname).getPortType().getOperations())
		{
			if(((OperationImpl)o).getName().equals(name))
			{
				operation = (OperationImpl)o;
				break;
			}
			
		}
		
		bindingoperation.setOperation(operation);

	
		SOAPOperationImpl operationextension = new SOAPOperationImpl();
		//operationextension.setStyle("document");
		operationextension.setSoapActionURI(soap_Action);
		///////operationextension.setElementType(new QName(targetNamespace, service_name));
		//operationextension.setRequired(new Boolean(true));
		bindingoperation.addExtensibilityElement(operationextension);
		
		BindingInputImpl bindinginput
		= (BindingInputImpl)definition.createBindingInput();
		bindinginput.setName(operation.getInput().getName());
		SOAPBodyImpl inputextension = new SOAPBodyImpl();
		inputextension.setUse("literal");
		//inputextension.setRequired(new Boolean(true));
		//inputextension.setNamespaceURI(targetNamespace);
		bindinginput.addExtensibilityElement(inputextension);
		//System.out.println("\ninputextension " + inputextension);

		bindingoperation.setBindingInput(bindinginput);

		if(operation.getStyle()==OperationType.REQUEST_RESPONSE)
		{
			BindingOutputImpl bindingoutput
			= (BindingOutputImpl)definition.createBindingOutput();
			bindingoutput.setName(operation.getOutput().getName());
			SOAPBodyImpl outputextension = new SOAPBodyImpl();
			outputextension.setUse("literal");
			//outputextension.setRequired(new Boolean(true));
			//outputextension.setNamespaceURI(targetNamespace);
			bindingoutput.addExtensibilityElement(outputextension);
			//System.out.println("\noutputextension " + outputextension);

			bindingoperation.setBindingOutput(bindingoutput);
		}
		
		definition.getBinding(bindingQname).addBindingOperation(bindingoperation);

		
	}
	
	public  void write(String filePath)
	{

		// show the schema w3c element via conversion JDOM Element and coversion to string
		org.jdom.input.DOMBuilder jdbuilder = new org.jdom.input.DOMBuilder();
		org.jdom.Element jelement = jdbuilder.build(schema);
		//System.out.println("\njelement " + jelement);
		org.jdom.output.XMLOutputter xmloutput = new org.jdom.output.XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setIndent(" ");
		format.setEncoding("ISO-8859-1");
		format.setLineSeparator("\n");
		
		xmloutput.setFormat(format);
	//	String strelement = xmloutput.outputString(jelement);
		//System.out.println("schema in jdom = \n " + strelement);

		UnknownExtensibilityElement extel = new UnknownExtensibilityElement();
		extel.setElement(schema);
		extel.setElementType(new QName(Constants.Q_ELEM_TYPES.getNamespaceURI(),
		schema.getLocalName()));
		
		

		types.addExtensibilityElement(extel);

		// the above is preferrable to this
		//types.setDocumentationElement(schema);
		//System.out.println("schema " + schema);

		// and set the types definiton back to the definition object
		definition.setTypes(types);
	    

		// output to file
		
		File file = new File(filePath);
		FileOutputStream out = null;

		// this has been proven to be correct
		try{
		out = new FileOutputStream(file);
		writer.writeWSDL(definition, out);
		}
		catch(FileNotFoundException fnfe){
		System.out.println("file not found " + fnfe.getMessage());
		}
		catch(IOException ioe){
		System.out.println("file not found " + ioe.getMessage());
		}
		catch(javax.wsdl.WSDLException we){
		System.out.println("wsdl exception " + we.getMessage());
		}
	}
	/*
	
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
	
		*/ 
	public static void main(String[] args) throws WSDLException {
		
		WSDLComposer generator = new WSDLComposer("http://matrix.bpelprocess", "WF_Process");
		generator.addNameSpace("",  "http://schemas.xmlsoap.org/wsdl/" );
		generator.addNameSpace("ns", "http://www.example.org/MatServ24/" );
		generator.addNameSpace("ns1", "http://www.example.org/MatServ25/" );
		generator.addNameSpace("ns2", "http://www.example.org/MatServ26/" );
		generator.addNameSpace("ns3", "http://www.example.org/MatServ33/" );
		generator.addNameSpace("ns4", "http://www.example.org/MatServ27/" );
		generator.addNameSpace("p", "http://www.w3.org/2001/XMLSchema"  );
		generator.addNameSpace("plnk", "http://docs.oasis-open.org/wsbpel/2.0/plnktype" );
		generator.addNameSpace("soap", "http://schemas.xmlsoap.org/wsdl/soap/" );
		generator.addNameSpace("vprop", "http://docs.oasis-open.org/wsbpel/2.0/varprop"  );
		generator.addNameSpace("tns", "http://matrix.bpelprocess" );
	
		
		HashMap<String, String> sequence = new HashMap<String, String>();
		sequence.put("input", "int");
		sequence.put("matA", "string");
		sequence.put("matB", "string");
	
		
		generator.addImport("MatServ24.wsdl", "http://www.example.org/MatServ24/");
		generator.addImport("MatServ25.wsdl", "http://www.example.org/MatServ25/");
		generator.addImport("MatServ26.wsdl", "http://www.example.org/MatServ26/");
		generator.addImport("MatServ27.wsdl", "http://www.example.org/MatServ27/");
		generator.addImport("MatServ33.wsdl", "http://www.example.org/MatServ33/");
	 	
		
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
	
		sequence.clear();
		sequence.put("payload", "WF_ProcessRequest");
		generator.addMessage("WF_ProcessRequestMessage", sequence);
		 
		sequence.clear();
		sequence.put("payload", "WF_ProcessResponse");
		generator.addMessage("WF_ProcessResponseMessage", sequence);
		
		sequence.clear();
		sequence.put("parameters", "multResult1");
		generator.addMessage("multResult1Request", sequence);
	   
		sequence.clear();
		sequence.put("parameters", "multResult2");
		generator.addMessage("multResult2Request", sequence);
	   
		sequence.clear();
		sequence.put("parameters", "multResult3");
		generator.addMessage("multResult3Request", sequence);
		
		sequence.clear();
		sequence.put("parameters", "multResult4");
		generator.addMessage("multResult4Request", sequence);
	   
		sequence.clear();
		sequence.put("parameters", "addResult");
		generator.addMessage("addResultRequest", sequence);
		
		sequence.clear();
		sequence.put("parameters", "cpCallback1");
		generator.addMessage("cpCallback1Request", sequence);
		
		sequence.clear();
		sequence.put("parameters", "cpCallback2");
		generator.addMessage("cpCallback2Request", sequence);

		sequence.clear();
		sequence.put("parameters", "cpCallback3");
		generator.addMessage("cpCallback3Request", sequence);
		
		sequence.clear();
		sequence.put("parameters", "cpCallback4");
		generator.addMessage("cpCallback4Request", sequence);
	    
		generator.addPartnerLinkType("Mahout_PL24", "ServiceProvider24", "ns:MatServ");
		generator.addPartnerLinkType("Mahout_PL25", "ServiceProvider25", "ns1:MatServ");
		generator.addPartnerLinkType("Mahout_PL26", "ServiceProvider26", "ns2:MatServ");
		generator.addPartnerLinkType("Mahout_PL27", "ServiceProvider27", "ns4:MatServ");
		generator.addPartnerLinkType("Mahout_PL33", "ServiceProvider33", "ns3:MatServ");
		
		
		generator.addPartnerLinkType("WF_Process", "WF_ProcessProvider", "tns:WF_Process");
		
		generator.addProperty("jobid_CS", "p:int");
		generator.addPropertyAlias("WF_ProcessRequestMessage", "payload", "jobid_CS", "/tns:input");
		generator.addPropertyAlias("multResult1Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("multResult2Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("multResult3Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("multResult4Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("addResultRequest", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("cpCallback1Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("cpCallback2Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("cpCallback3Request", "parameters", "jobid_CS", "/tns:job_id");
		generator.addPropertyAlias("cpCallback4Request", "parameters", "jobid_CS", "/tns:job_id");
		
		generator.addPortType("WF_Process");
		generator.addRequestResponseOperation("process", "WF_ProcessRequestMessage", "WF_ProcessResponseMessage", "WF_Process");
		generator.addOneWayOperation("multResult1", "multResult1Request", "WF_Process");
		generator.addOneWayOperation("multResult2", "multResult2Request", "WF_Process");
		generator.addOneWayOperation("multResult3", "multResult3Request", "WF_Process");
		generator.addOneWayOperation("multResult4", "multResult4Request", "WF_Process");
		generator.addOneWayOperation("cpCallback1", "cpCallback1Request", "WF_Process");
		generator.addOneWayOperation("cpCallback2", "cpCallback2Request", "WF_Process");
		generator.addOneWayOperation("cpCallback3", "cpCallback3Request", "WF_Process");
		generator.addOneWayOperation("cpCallback4", "cpCallback4Request", "WF_Process");
		generator.addOneWayOperation("addResult", "addResultRequest", "WF_Process");
		
		generator.addBinding("WF_ProcessBinding", "WF_Process");
		generator.addBindingOperation("process", "http://matrix.bpelprocess/process", "WF_ProcessBinding");
		generator.addBindingOperation("multResult1", "http://matrix.bpelprocess/multResult1", "WF_ProcessBinding");
		generator.addBindingOperation("multResult2", "http://matrix.bpelprocess/multResult2", "WF_ProcessBinding");
		generator.addBindingOperation("multResult3", "http://matrix.bpelprocess/multResult3", "WF_ProcessBinding");
		generator.addBindingOperation("multResult4", "http://matrix.bpelprocess/multResult4", "WF_ProcessBinding");
		generator.addBindingOperation("addResult", "http://matrix.bpelprocess/addResult", "WF_ProcessBinding");
		generator.addBindingOperation("cpCallback1", "http://matrix.bpelprocess/cpCallback1", "WF_ProcessBinding");
		generator.addBindingOperation("cpCallback2", "http://matrix.bpelprocess/cpCallback2", "WF_ProcessBinding");
		generator.addBindingOperation("cpCallback3", "http://matrix.bpelprocess/cpCallback3", "WF_ProcessBinding");
		generator.addBindingOperation("cpCallback4", "http://matrix.bpelprocess/cpCallback4", "WF_ProcessBinding");
		
		generator.addService("WF_ProcessService", "WF_ProcessPort", "WF_ProcessBinding", "http://10.160.2.27:8080/ode/processes/WF_Process");
		
		
		generator.write("WF_ProcessArtifacts.wsdl");
		
		
		/*
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
		 
		 */
}

}

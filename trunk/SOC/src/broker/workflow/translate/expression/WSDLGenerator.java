package broker.workflow.translate.expression;

import groovy.xml.MarkupBuilder;

import java.awt.Composite;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.OperationType;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;

import org.jdom.input.DOMBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.abstract_syntax.data_perspective.Variable;
import org.unify_framework.abstract_syntax.impl.ActivityImpl;
import org.unify_framework.abstract_syntax.impl.CompositeActivityImpl;
import org.unify_framework.instances.bpel.BpelCompositeInvokeActivity;
import org.unify_framework.instances.bpel.BpelCompositeReceiveActivity;
import org.unify_framework.instances.bpel.BpelCorrelation;
import org.unify_framework.instances.bpel.BpelCorrelationSet;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.unify_framework.instances.bpel.BpelVariable;
import org.unify_framework.instances.bpel.BpelVariableMessageType;
import org.unify_framework.instances.bpel.parser.BpelParser;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.awt.geom.AreaOp.AddOp;


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
import com.predic8.schema.ComplexType;
import com.predic8.schema.Schema;
import com.predic8.schema.Sequence;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Import;

import com.predic8.wsdl.Operation;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.creator.WSDLCreator;
import com.predic8.wsdl.creator.WSDLCreatorContext;

public class WSDLGenerator  {

	DefinitionImpl definition;
	Element schema;
	Document doc ;
	TypesImpl types ;
	WSDLWriterImpl writer;
	String targetNamespace;
	Element partnerLinkType;
	Workflow workflow;

	public WSDLGenerator(String namespace, String process_name )
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
		QName qname = new QName(namespace,process_name);
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

		//workflow = wf.copy();
	}

	public void initialize(Workflow wf)
	{
		addNameSpace("",  "http://schemas.xmlsoap.org/wsdl/" );
		addNameSpace("ns1", wf.getAdditiveSplitting_namespace() );
		addNameSpace("ns2", wf.getBroker_namespace() );
		addNameSpace("p", "http://www.w3.org/2001/XMLSchema"  );
		addNameSpace("plnk", "http://docs.oasis-open.org/wsbpel/2.0/plnktype" );
		addNameSpace("soap", "http://schemas.xmlsoap.org/wsdl/soap/" );
		addNameSpace("vprop", "http://docs.oasis-open.org/wsbpel/2.0/varprop"  );
		addNameSpace("tns", wf.getProcess().getTargetNamespace() );

		addImport("AdditiveSplittingArtifacts.wsdl", wf.getAdditiveSplitting_namespace());

		addImport("BrokerServices.wsdl", "http://www.brokerservices.org/MatServ/");

		addPortType(wf.getWf_name());
		//addOneWayOperation("start", wf.getWf_name()+"RequestMessage", wf.getWf_name());

		//Add types and variables
		HashMap<String, String> sequence = new HashMap<String, String>();
		sequence.put("jobID", "string");
		sequence.put("expression", "string");
		addComplexType(wf.getWf_name()+"Request", sequence);

		sequence.clear();
		sequence.put("parameters", wf.getWf_name()+"Request");
		addMessage(wf.getWf_name()+"RequestMessage", sequence);

		sequence.clear();
		sequence = new HashMap<String, String>();
		sequence.put("jobID", "string");
		sequence.put("instanceID", "string");
		addComplexType(wf.getWf_name()+"Response", sequence);

		sequence.clear();
		sequence.put("parameters", wf.getWf_name()+"Response");
		addMessage(wf.getWf_name()+"ResponseMessage", sequence);

		//
		for(BpelVariable v: wf.getVariables())
		{
			if(((BpelVariableMessageType)v).getMessageType().startsWith("tns:"))
			{
				sequence.clear();
				sequence.put("job_id", "string");
				sequence.put("sub_jobid", "string");
				sequence.put("result", "string");

				//*********************  check if it is already found don't add it
				////////******************************
				boolean added= false;
				for (int i=0; i< schema.getChildNodes().getLength();i++)
				{
					Element node = (Element)schema.getChildNodes().item(i);
					//	System.out.println(node.getAttribute("name") );
					if(node.getAttribute("name").equals(((BpelVariableMessageType)v).getMessageType().substring(4, ((BpelVariableMessageType)v).getMessageType().length()-7)))
						added =true;
				}
				if(!added)
					addComplexType(((BpelVariableMessageType)v).getMessageType().substring(4, ((BpelVariableMessageType)v).getMessageType().length()-7), sequence);

				sequence.clear();
				sequence.put("parameters",((BpelVariableMessageType)v).getMessageType().substring(4, ((BpelVariableMessageType)v).getMessageType().length()-7));

				addMessage(((BpelVariableMessageType)v).getMessageType().substring(4), sequence);

				//addPropertyAlias(((BpelVariableMessageType)v).getMessageType(), "parameters", "jobid_CS", "/tns:job_id");
			}
		}

		addProperty("jobid_CS", "p:string");
		addPropertyAlias("tns:"+wf.getWf_name()+"RequestMessage", "parameters", "jobid_CS", "/tns:jobID");
		addPropertyAlias("tns:"+wf.getWf_name()+"ResponseMessage", "parameters", "jobid_CS", "/tns:jobID");
		addPropertyAlias("ns1:AdditiveSplittingRequestMessage", "parameters", "jobid_CS", "/ns1:jobID");
		addPropertyAlias("ns1:AdditiveSplittingResponseMessage", "parameters", "jobid_CS", "/ns1:jobID");
		addPropertyAlias("ns2:addRequest", "parameters", "jobid_CS", "/ns2:job_id");
		addPropertyAlias("ns2:addResponse", "parameters", "jobid_CS", "/ns2:job_id");

		for(BpelVariable v: wf.getVariables())
		{
			if(((BpelVariableMessageType)v).getMessageType().startsWith("tns:"))
			{
				addPropertyAlias(((BpelVariableMessageType)v).getMessageType(), "parameters", "jobid_CS", "/tns:job_id");
			}
		}

		//Add property subjob_InvokeCounter and property alias for each invocation message
		for(BpelCorrelationSet c : wf.getProcess().getCorrelationSets())
		{
			String property = c.getName().toLowerCase();
			if(c.getName().equals("JOB_CS")) continue;
			addProperty(property, "p:string");

			if(c.getName().contains("BROKER"))
			{
				addPropertyAlias("ns2:addRequest", "parameters", property, "/ns2:op_id");
				addPropertyAlias("ns2:addResponse", "parameters", property, "/ns2:op_id");

			}
			else
			{
				addPropertyAlias("ns1:AdditiveSplittingRequestMessage", "parameters", property, "/ns1:sub_jobID");
				addPropertyAlias("ns1:AdditiveSplittingResponseMessage", "parameters", property, "/ns1:sub_jobID");

			}


		}

		////////////////////////////////

		addBinding(wf.getWf_name()+"Binding", wf.getWf_name());
		//Add operations and binding operations
		for(Node n : wf.getProcess().getChildren())
		{
			if(n.getClass() == BpelCompositeReceiveActivity.class)
			{
				if(((BpelCompositeReceiveActivity)n).getOperation().equals("start"))
				{
					addTwoWaysOperation(((BpelCompositeReceiveActivity)n).getOperation(), wf.getWf_name()+"RequestMessage",wf.getWf_name()+"ResponseMessage", wf.getWf_name());
					addBindingOperation(((BpelCompositeReceiveActivity)n).getOperation(), wf.getProcess().getTargetNamespace()+"/"+((BpelCompositeReceiveActivity)n).getOperation(), wf.getWf_name()+"Binding");
				}
				else if(((BpelCompositeReceiveActivity)n).getPartnerLink().equals("client"))
				{
					//		List<BpelCorrelation> correlations =((BpelCompositeReceiveActivity)n).getCorrelations();
					//for (BpelCorrelation c : correlations)
					//		if(c.getSet().contains("BROKER"))

					String variable_msg_type = null;
					for (BpelVariable v :wf.getVariables())
						if(v.getName().equals(((BpelCompositeReceiveActivity)n).getVariable()))
						{
							variable_msg_type= new String(((BpelVariableMessageType)v).getMessageType());

						}
					addOneWayOperation(((BpelCompositeReceiveActivity)n).getOperation(), variable_msg_type, wf.getWf_name());
				}


			}
		}


		//Add partnerlinks

		for(BpelPartnerLink pL : wf.getProcess().getPartnerLinks())
		{
			if(pL.getMyRole()!=null)
			{
				if(pL.getPartnerLinkType().contains("AdditiveSplitting"))//Additive Splitting Callback PL
					addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getMyRole(), "ns1:AdditiveSplittingCallback");
				else if(pL.getPartnerLinkType().contains("Broker"))
					addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getMyRole(), "ns2:BrokerCallback");
				else
					addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getMyRole(), "tns:"+wf.getWf_name());

			}
			else
			{	
				Map<String,String> map= wf.getProcess().getNamespaceDeclarations();
				Collection<String> values = map.values();
				Iterator<String> I = values.iterator();
				Iterator<String>  keyIter = map.keySet().iterator();
				String prefix="";
				String portType="";
				//**************************
				//Revise this part back .. I use a workaround .. need a better solution .
				while(I.hasNext())
				{
					String namespace = new String(I.next().toLowerCase());
					if(namespace.contains("additivesplitting") && pL.getPartnerRole().toLowerCase().contains("additivesplitting"))
					{
						prefix = keyIter.next(); 
						portType="AdditiveSplitting";
						break;
					}
					else if(namespace.contains("brokerservices")&& pL.getPartnerRole().toLowerCase().contains("brokerservices"))
					{
						prefix = keyIter.next(); 
						portType="MatServ";
						break;
					}


					keyIter.next();
				}
				/////***************************				
				addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getPartnerRole(), prefix+":"+portType/*pL.getPartnerRole().substring(0,pL.getPartnerRole().length()-8)*/);
			}
		}

		for(Activity scope: wf.getProcess().getActivities())
		{
			if(scope.getClass()== BpelScopeActivity.class)
			{
				for(BpelPartnerLink pL : ((BpelScopeActivity)scope).getPartnerLinks())
				{
					if(pL.getMyRole()!=null)
					{
						if(pL.getPartnerLinkType().contains("AdditiveSplitting"))//Additive Splitting Callback PL
							addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getMyRole(), "ns1:AdditiveSplittingCallback");
						else if(pL.getPartnerLinkType().contains("Broker"))
							addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getMyRole(), "ns2:BrokerCallback");
						else
							addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getMyRole(), "tns:"+wf.getWf_name());

					}
					else
					{	
						Map<String,String> map= wf.getProcess().getNamespaceDeclarations();
						Collection<String> values = map.values();
						Iterator<String> I = values.iterator();
						Iterator<String>  keyIter = map.keySet().iterator();
						String prefix="";
						String portType="";
						//**************************
						//Revise this part back .. I use a workaround .. need a better solution .
						while(I.hasNext())
						{
							String namespace = new String(I.next().toLowerCase());
							if(namespace.contains("additivesplitting") && pL.getPartnerRole().toLowerCase().contains("additivesplitting"))
							{
								prefix = keyIter.next(); 
								portType="AdditiveSplitting";
								break;
							}
							else if(namespace.contains("brokerservices")&& pL.getPartnerRole().toLowerCase().contains("brokerservices"))
							{
								prefix = keyIter.next(); 
								portType="MatServ";
								break;
							}


							keyIter.next();
						}
						/////***************************				
						addPartnerLinkType(pL.getPartnerLinkType().substring(4), pL.getPartnerRole(), prefix+":"+portType/*pL.getPartnerRole().substring(0,pL.getPartnerRole().length()-8)*/);
					}
				}
			}
		}
		//Add service
		addService(wf.getWf_name()+"Service", wf.getWf_name()+"Port", wf.getWf_name()+"Binding", wf.getBroker_services_url()+"/ode/processes/"+wf.getWf_name());


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

		Element type = doc.createElement("element");
		type.setAttribute("name", name);

		Element complex = doc.createElement("complexType");

		sequence = doc.createElement("sequence");

		for(String key : name_type_pairs.keySet())
		{
			Element part1 = doc.createElement("element");
			part1.setAttribute("name", key);
			part1.setAttribute("type",  name_type_pairs.get(key));
			sequence.appendChild(part1);
		}

		complex.appendChild(sequence);
		type.appendChild(complex);
		schema.appendChild(type);

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
		//Check if it is already found
		for(int i=0; i< definition.getExtensibilityElements().size(); i++)
		{
			if(((UnknownExtensibilityElement)definition.getExtensibilityElements().get(i)).getElement().getAttribute("name").equals(name))
			{
				return;
			}
		}


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
		propertyAlias.setAttribute("messageType",messageType);
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

	/*
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
	 */
	public void addOneWayOperation(String name, String inputMessageName,  String portType)
	{


		QName messageqname= new QName(targetNamespace, portType);

		OperationImpl operation = (OperationImpl)definition.createOperation();
		operation.setName(name);
		operation.setStyle(javax.wsdl.OperationType.ONE_WAY);

		InputImpl input = (InputImpl)definition.createInput();
		//input.setName(inputMessageName);
		if(inputMessageName.contains(":")) //external imported message type don't use targetnamespace
		{
			
			MessageImpl external_msg= new MessageImpl();
			external_msg.setQName(new QName( inputMessageName));
			external_msg.setUndefined(false);
			input.setMessage(external_msg);
		}
		else			
			input.setMessage(definition.getMessage(new QName(targetNamespace, inputMessageName)));

		operation.setInput(input);
		operation.setUndefined(false);
		definition.getPortType(messageqname).addOperation(operation);

	}

	public void addTwoWaysOperation(String name, String inputMessageName, String outputMessageName,  String portType)
	{
		QName messageqname= new QName(targetNamespace, portType);

		OperationImpl operation = (OperationImpl)definition.createOperation();
		operation.setName(name);
		operation.setStyle(javax.wsdl.OperationType.REQUEST_RESPONSE);

		InputImpl input = (InputImpl)definition.createInput();
		//input.setName(inputMessageName);
		input.setMessage(definition.getMessage(new QName(targetNamespace, inputMessageName)));

		OutputImpl output = (OutputImpl)definition.createOutput();
		output.setMessage(definition.getMessage(new QName(targetNamespace, outputMessageName)));

		operation.setInput(input);
		operation.setOutput(output);
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
}
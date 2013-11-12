package broker.workflow.translate.expression;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.Node;
import org.unify_framework.instances.bpel.BpelPartnerLink;
import org.unify_framework.instances.bpel.BpelScopeActivity;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.wsdl.DefinitionImpl;
import com.ibm.wsdl.TypesImpl;
import com.ibm.wsdl.xml.WSDLWriterImpl;

public class DeployXML {


	Element deploy;
	Element process;
	Document doc ;

	Workflow workflow;

	public DeployXML(Workflow wkflow )
	{
		workflow = wkflow;

		javax.xml.parsers.DocumentBuilderFactory domfactory =
				javax.xml.parsers.DocumentBuilderFactory.newInstance();

		javax.xml.parsers.DocumentBuilder builder = null;

		try {
			builder = domfactory.newDocumentBuilder();
		}
		catch (javax.xml.parsers.ParserConfigurationException pce) {
			System.out.println("parser config exception " + pce.getMessage());
		}
		//DOMImplementation dImpl = builder.getDOMImplementation();

		// namespace for the schema
		//String namespaceURI = "http://www.apache.org/ode/schemas/dd/2007/03";

		//doc = dImpl.createDocument(namespaceURI, "deploy", null);
		doc = builder.newDocument();

		deploy = doc.createElement("deploy");
	
		deploy.setAttribute("xmlns:AddSplittingNS","http://additivesplitting.bpelprocess");
		deploy.setAttribute("xmlns:BrokerNS", workflow.getBroker_namespace());
		deploy.setAttribute("xmlns:soc."+workflow.getWf_name()+".workflow", "http://soc."+workflow.getWf_name()+".workflow");
		deploy.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");

		process = doc.createElement("process");
	//	Attr attr = doc.createAttribute("name");
	//	attr.setValue("soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name());
	//	process.setAttributeNode(attr);
		
		process.setAttribute("name", "soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name());
		process.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");

		deploy.appendChild(process);
		
		
		Element active = doc.createElement("active");
		active.setTextContent("true");
		active.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		process.appendChild(active);

		Element retired = doc.createElement("retired");
		retired.setTextContent("false");
		retired.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		process.appendChild(retired);

		Element process_events = doc.createElement("process-events");
		process_events.setAttribute("generate", "all");
		process_events.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		
		addScopeEvents(process_events);
		process.appendChild(process_events);

		//deploy.appendChild(process);
	}
	
	public void addScopeEvents(Element process_events)
	{
			
		for(Node activity : workflow.getProcess().getChildren())
		{
			if(activity.getClass()==BpelScopeActivity.class)
			{
				Element scope_events = doc.createElement("scope-events");
				
				scope_events.setAttribute("name", activity.getName());
				scope_events.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
				Element enable_event = doc.createElement("enable-event");
				enable_event.setTextContent("correlation");
				enable_event.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
				scope_events.appendChild(enable_event);
				enable_event = doc.createElement("enable-event");
				enable_event.setTextContent("scopeHandling");
				enable_event.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
				scope_events.appendChild(enable_event);
				enable_event = doc.createElement("enable-event");
				enable_event.setTextContent("instanceLifecycle");
				enable_event.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
				scope_events.appendChild(enable_event);
				enable_event = doc.createElement("enable-event");
				enable_event.setTextContent("activityLifecycle");
				enable_event.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
				scope_events.appendChild(enable_event);
				enable_event = doc.createElement("enable-event");
				enable_event.setTextContent("dataHandling");
				enable_event.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
				scope_events.appendChild(enable_event);
				process_events.appendChild(scope_events);
			
				 
			}
		}

	}

	public void addPartnerLinks()
	{
		addClientPL();
		addServicesPL();
	}

	public void addPartnerLinks(boolean all)
	{

		for(BpelPartnerLink PL: workflow.getProcess().getPartnerLinks())
		{
			addDeployPL(PL);
		}
		
		for(Activity scope : workflow.getProcess().getActivities())
		{
			if(scope.getClass()== BpelScopeActivity.class)
			{
				for(BpelPartnerLink PL : ((BpelScopeActivity)scope).getPartnerLinks() )
				{
					addDeployPL(PL);
				}
			}
			
		}

	}
	public void addDeployPL(BpelPartnerLink PL)
	{
		Element deploy_PL = null;
		Element service = null;
		String service_name=null;
		String port_name = null;

		if(PL.getMyRole()!=null)
		{
			deploy_PL = doc.createElement("provide");
			deploy_PL.setAttribute("partnerLink",PL.getName());
			deploy_PL.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
			service = doc.createElement("service");

			if(PL.getName().contains("AdditiveSplitting"))
			{
				service_name = new String("AddSplittingNS:AdditiveSplittingCallbackService");
				port_name = new String("AdditiveSplittingCallbackPort");
			}
//			else if(PL.getName().contains("Broker"))
//			{
//				service_name = new String("BrokerNS:BrokerCallbackService");
//				port_name = new String("BrokerServicesCallbackPort");
//			}
			else
			{
				service_name = new String("soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name()+"Service");
				port_name = new String(workflow.getWf_name()+"Port");
			}
		}
		else
		{
			deploy_PL = doc.createElement("invoke");
			deploy_PL.setAttribute("partnerLink",PL.getName());
			service = doc.createElement("service");

			deploy_PL.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
			
			if(PL.getName().contains("AdditiveSplitting"))
			{
				service_name = new String("AddSplittingNS:AdditiveSplittingService");
				port_name = new String("AdditiveSplittingPort");
			}
			else if(PL.getName().contains("Broker"))
			{
				service_name = new String("BrokerNS:BrokerService");
				port_name = new String("BrokerServicesPort");
			}
			else
			{
				service_name = new String("soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name()+"Service");
				port_name = new String(workflow.getWf_name()+"Port");
			}	
		}
		service.setAttribute("name",service_name);
		service.setAttribute("port",port_name);
		service.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		
		deploy_PL.appendChild(service);
		
		process.appendChild(deploy_PL);

	}


	private void addClientPL()
	{
		Element provide = doc.createElement("provide");
		/*	provide.setAttribute("partnerLink", "client");
		Element service = doc.createElement("service");
		service.setAttribute("name","soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name()+"Service");
		service.setAttribute("port", workflow.getWf_name()+"Port");
		provide.appendChild(service);

		process.appendChild(provide);
		 */
		for(BpelPartnerLink PL: workflow.getProcess().getPartnerLinks())
		{

			provide = doc.createElement("provide");
			provide.setAttribute("partnerLink",PL.getName());
			Element service = doc.createElement("service");
			String service_name=null;
			String port_name = null;
			if(PL.getName().contains("AdditiveSplitting"))
			{
				service_name = new String("AddSplittingNS:AdditiveSplittingCallbackService");
				port_name = new String("AdditiveSplittingCallbackPort");
			}
			else if(PL.getName().contains("Broker"))
			{
				service_name = new String("BrokerNS:BrokerCallbackService");
				port_name = new String("BrokerServicesCallbackPort");
			}
			else
			{
				service_name = new String("soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name()+"Service");
				port_name = new String(workflow.getWf_name()+"Port");
			}

			service.setAttribute("name",service_name);
			service.setAttribute("port",port_name);
			service.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
			provide.appendChild(service);

			process.appendChild(provide);

		}
		/*
		provide = doc.createElement("provide");
		provide.setAttribute("partnerLink", "CALLBACK_PL");
		service = doc.createElement("service");
		service.setAttribute("name","soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name()+"Service");
		service.setAttribute("port", workflow.getWf_name()+"Port");
		provide.appendChild(service);

		process.appendChild(provide);
		 */
	}

	private void addServicesPL()
	{
		Element invoke = doc.createElement("invoke");
		invoke.setAttribute("partnerLink", "Broker_PL");
		Element service = doc.createElement("service");
		service.setAttribute("name", "BrokerNS:MatServ");
		service.setAttribute("port", "BrokerServicesPort");
		service.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		invoke.appendChild(service);
		
		invoke.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		process.appendChild(invoke);

		invoke = doc.createElement("invoke");
		invoke.setAttribute("partnerLink", "AdditiveSplitting_PL");
		service = doc.createElement("service");
		service.setAttribute("name", "AddSplittingNS:AdditiveSplittingService");
		service.setAttribute("port", "AdditiveSplittingPort");
		service.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		invoke.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		invoke.appendChild(service);

		process.appendChild(invoke);


	}

	public void write()
	{
		
		doc.appendChild(deploy);
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(workflow.getFolder_Path()+"/"+workflow.getWf_name()+"/deploy.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		}catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}

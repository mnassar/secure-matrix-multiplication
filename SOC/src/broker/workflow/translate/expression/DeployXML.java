package broker.workflow.translate.expression;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
		deploy.setAttribute("xmlns", "http://www.apache.org/ode/schemas/dd/2007/03");
		deploy.setAttribute("xmlns:MatServ", workflow.getBroker_namespace());
		deploy.setAttribute("xmlns:soc."+workflow.getWf_name()+".workflow", "http://soc."+workflow.getWf_name()+".workflow");
		deploy.setAttribute("xmlns:AdditiveSplittingService","http://additivesplitting.bpelprocess");
	
		process = doc.createElement("process");
		Attr attr = doc.createAttribute("name");
		attr.setValue("soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name());
		process.setAttributeNode(attr);
		
		deploy.appendChild(process);
		
		Element active = doc.createElement("active");
		active.setTextContent("true");
		process.appendChild(active);
		
		Element retired = doc.createElement("retired");
		retired.setTextContent("false");
		process.appendChild(retired);
		
		Element process_events = doc.createElement("process-events");
		 process_events.setAttribute("generate", "all");
		process.appendChild(process_events);
		
	}
	
	public void addPartnerLinks()
	{
		addClientPL();
		addServicesPL();
	}
	
	private void addClientPL()
	{
		Element provide = doc.createElement("provide");
		provide.setAttribute("partnerLink", "client");
		Element service = doc.createElement("service");
		service.setAttribute("name","soc."+workflow.getWf_name()+".workflow:"+workflow.getWf_name()+"Service");
		service.setAttribute("port", workflow.getWf_name()+"Port");
		provide.appendChild(service);

		process.appendChild(provide);
	}
	
	private void addServicesPL()
	{
		Element invoke = doc.createElement("invoke");
		invoke.setAttribute("partnerLink", "Broker_PL");
		Element service = doc.createElement("service");
		service.setAttribute("name", "MatServ:MatServ");
		service.setAttribute("port", "BrokerServicesPort");
		invoke.appendChild(service);

		process.appendChild(invoke);

		invoke = doc.createElement("invoke");
		invoke.setAttribute("partnerLink", "AdditiveSplitting_PL");
		service = doc.createElement("service");
		service.setAttribute("name", "AdditiveSplittingService:AdditiveSplittingService");
		service.setAttribute("port", "AdditiveSplittingPort");
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

package instance.monitor.service;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import  org.apache.axis2.AxisFault;

import javax.ws.rs.core.UriBuilder ;
import javax.ws.rs.core.Response ;
import javax.ws.rs.core.Response.ResponseBuilder ;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;


import org.apache.axiom.om.*;
import org.apache.axiom.om.util.*;


import org.apache.ode.axis2.service.ServiceClientUtil;
import org.apache.ode.bpel.engine.ProcessAndInstanceManagementImpl;
import org.apache.ode.bpel.pmapi.TInstanceInfo;
import org.apache.ode.utils.Namespaces;
import org.apache.xml.utils.XMLReaderManager;
import org.codehaus.jackson.map.*;

import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;


import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path(value="/instance")
public class MonitorService {

	protected final static ObjectMapper defaultMapper = new ObjectMapper();
	 private ServiceClientUtil _client;

	@POST
	@Path("/getTime")
	@Consumes (MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String getProcessInstanceExecutionTime(String instanceID) {
	  	
		String duration = null;
		_client = new ServiceClientUtil();
		OMElement root = _client.buildMessage("getInstanceInfo", new String[] {"iid"}, new String[] {instanceID});
		OMElement result =null;
        try {
			
        	   result = sendToIM(root);
			   /*String status = result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
		                .getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "status")).getText();
			 
			   DateFormat xsdDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			   
			   String start_time= result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
		                .getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "dt-started")).getText();
			 
			   String end_date = result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
		                .getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "dt-last-active")).getText();
		
			   System.out.println("Started the process with id "+instanceID );
			   Date sDate = xsdDF.parse(start_time);
			   Date eDate= xsdDF.parse(end_date);
			   
			   long diffInMillies = eDate.getTime() - sDate.getTime();
			    long difference = TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
			    
			    duration = String.valueOf(difference);
			    System.out.println("The process with id "+instanceID + " took  "+duration + " minutes with " + status + " status");
			    */
			 return result.toStringWithConsume();
		} catch (AxisFault | XMLStreamException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	return "Not Found";
	}

	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLastInstanceExecutionTime() {
	  	
		String duration = new String("Don't exist");
		_client = new ServiceClientUtil();
		OMElement root = _client.buildMessage("getInstanceInfo", new String[] {"iid"}, new String[] {"26451"});
		OMElement result = null;
		
        try {
			
        	
        
        	   result = sendToIM(root);
        	  /*
        	   XMLStreamReader reader = result.getXMLStreamReader();
        	   XMLInputFactory xmlif = XMLInputFactory.newInstance();
        	   System.out.println("FACTORY: " + xmlif);
        	   xmlif.setEventAllocator(new XMLEventAllocatorImpl());
        	   XMLEventAllocator allocator = xmlif.getEventAllocator();
        	   XMLEventReader xml = xmlif.createXMLEventReader(reader);
        	   int i=0;
        	   while(xml.hasNext()){
        		   System.out.println("i= "+i);
        		   XMLEvent event = (XMLEvent) xml.next();
        		    if(event.getEventType() == XMLStreamReader.START_ELEMENT){
        		        System.out.println(reader.getLocalName() );
        		        if(reader.getLocalName().equals("status"))
        		        {
        		        	StartElement event = reader.get.asStartElement();
        		        	 System.out.println("EVENT: " + event.toString());
        		        }
        		        
        		        if(xml.getLocalName().equals("dt-started"))
        		        {
        		        	StartElement event = allocator.allocate(xml).asStartElement();
        		        	 System.out.println("EVENT: " + event.toString());
        		        }
        		        if(xml.getLocalName().equals("dt-last-active"))
        		        {
        		        	StartElement event = allocator.allocate(xml).asStartElement();
        		        	 System.out.println("EVENT: " + event.toString());
        		        }
//        		       System.out.println("Attribute count : "+ xml.getAttributeCount());
//        		        for (int j=0; i < xml.getAttributeCount(); j++) {
//        		        	String prefix = xml.getAttributePrefix(index);
//        		        	  String namespace = xml.getAttributeNamespace(index);
//        		        	  String localName = xml.getAttributeLocalName(index);
//        		        	  String value = xml.getAttributeValue(index);
//        		        	  System.out.print(" ");
//        		        	  if (namespace != null && !("".equals(namespace)) ) System.out.print("['"+namespace+"']:");
//        		        	  if (prefix != null) System.out.print(prefix+":");
//        		        	  if (localName != null) System.out.print(localName);
//        		        	  System.out.print("='"+value+"'");
//        		        	  System.out.println("j='"+j+"'");
//        		          }
//        		          
        		    }
        		    if(xml.getEventType() == XMLStreamReader.CDATA){
        		    	System.out.println("attribute value : "+ xml.getText());
        		    }
        		    i++;
        		}
	
	
	
	*/
			   String status = result.getAttributeValue(new QName(Namespaces.ODE_PMAPI, "status"));//getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
		                //.getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "status")).getText();
			 
			   DateFormat xsdDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			   
			   String start_time= result.getAttributeValue(new QName(Namespaces.ODE_PMAPI, "dt-started"));//result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
		                //.getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "dt-started")).getText();
			 
			   String end_date = result.getAttributeValue(new QName(Namespaces.ODE_PMAPI, "dt-last-active"));//result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
		               // .getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "dt-last-active")).getText();
		
			   System.out.println("Started the process with id "+26451 + "\n result: "+result.getText()); //" with status "+ status + " \n start time : "+ start_time + " \n last active time :" + end_date );
		/*	   Date sDate = xsdDF.parse(start_time); 
			   Date eDate= xsdDF.parse(end_date);
			   
			   long diffInMillies = eDate.getTime() - sDate.getTime();
			    long difference = TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
			    
			    duration = String.valueOf(difference);
			    System.out.println("The process with id "+25001 + " took  "+duration + " minutes with " + status + " status");
			*/ 
		} catch (AxisFault  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	try {
			return result.toStringWithConsume();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
	  	
	  	return "Not found";
	}
	private OMElement sendToIM(OMElement msg) throws AxisFault {
		System.out.println("Calling ODE service ...." );
		return _client.send(msg, "http://localhost:8080/ode/processes/InstanceManagement/getInstanceInfo");
    }

/*
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String Compute(String op) throws IOException {

	*/	
		
}


package instance.monitor.service;
import java.io.IOException;
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


import org.apache.axiom.om.*;
import org.apache.axiom.om.util.*;


import org.apache.ode.axis2.service.ServiceClientUtil;
import org.apache.ode.utils.Namespaces;
import org.codehaus.jackson.map.*;


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
		
        try {
			
        	   OMElement result = sendToIM(root);
			   String status = result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
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
			 
		} catch (AxisFault | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	return duration;
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


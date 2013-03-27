package instance.monitor.service;

import java.io.IOException;
import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;

import org.xml.sax.SAXException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;



public class MonitorClient {

	 static final String REST_URI = "http://localhost:8080/ProcessMonitorServices/process";

//	   protected final static ObjectMapper defaultMapper = new ObjectMapper();

	 
	    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
	 
	    	
	        ClientConfig config = new DefaultClientConfig();

	   
	        Client client = Client.create(config);
	        WebResource service = client.resource(REST_URI);
	 
	      
	        System.out.println("---------------------------------------------------");

	       
	        GetInstanceTime(service);
	        
			
	 
	    }
	    private static void GetInstanceTime(WebResource service)
	    {
	    	
	    	try {
	    	   		   
	    		String instanceID = "17756";
	    		ClientResponse response = service.path("/instance/getTime").type(MediaType.TEXT_PLAIN).post(ClientResponse.class, instanceID);
	    		
	    		
	    		if (response.getStatus() != 201 ) {
	    		   throw new RuntimeException("Failed : HTTP error code : "
	    			+ response.getStatus()); 
	    		}
	     
	    	    
	    		System.out.println("Duration for the process .... \n" + response.getEntity(String.class));
	    		
	    	  } catch (Exception e) {
	     
	    		e.printStackTrace();
	     
	    	  }
	    	
	    	
	    }
	    
	 
	 
}

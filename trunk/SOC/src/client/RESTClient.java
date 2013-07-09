package client;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import broker.BrokerSOCResource;
import broker.JobType;
import broker.MatrixMeta;
import broker.ResourceMeta;
import broker.SOCJob;
import broker.SOCResource;
import broker.SOCResourceAlias;
import broker.StorageProtocol;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.RequestWriter;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import securerest.MatrixOP;
import sun.net.www.http.HttpClient;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.EntityTag;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpRetryException;
import java.util.ArrayList;

public class RESTClient {

    static final String REST_URI = "http://localhost:8080/SOC/rest";

    static String resourceID;
    static String jobID;
   protected final static ObjectMapper defaultMapper = new ObjectMapper();
//    static { // if you need to change default configuration:
  //    defaultMapper.configure(SerializationConfig.Feature.USE_STATIC_TYPING, true); // faster this way, not default
    //}
 
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
 
    	
        ClientConfig config = new DefaultClientConfig();
     //   config.getClasses().add(JSONRootElementProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI);
 
      
        System.out.println("---------------------------------------------------");
        
        SOCResource resource = new SOCResource(StorageProtocol.ADDITIVE_SPLITTING);
		resource.setUser_token("farida");
		resource.setFile_path("/home/farida/Documents/A1");
		MatrixMeta meta = new MatrixMeta(10,10,"decimal");
		resource.setResource_meta(meta);
        addResource(service, resource);
        System.out.println("Output of get JSON resource object: "+ getResource(service));
        String resourceA = new String(resourceID);
     
        addResource(service, resource);
        String resourceAA = new String(resourceID);
        
        System.out.println("---------------------------------------------------");
        resource = new SOCResource(StorageProtocol.ADDITIVE_SPLITTING);
		resource.setUser_token("farida");
		resource.setFile_path("/home/farida/Documents/B1");
		meta = new MatrixMeta(10,10,"decimal");
		resource.setResource_meta(meta);
        addResource(service, resource);
        System.out.println("Output of get JSON resource object: "+ getResource(service));
        String resourceB = new String(resourceID);
        
        addResource(service, resource);
        String resourceBB = new String(resourceID);
        
        SOCJob job = new SOCJob();
        job.setExpression("A*B+C*D");
        job.setType(JobType.MATRIX_COMPUTATION);
        ArrayList<SOCResourceAlias> aliases = new ArrayList<SOCResourceAlias>();
        SOCResourceAlias alias = new SOCResourceAlias(); alias.setAlias("A"); alias.setResource_id(resourceA);
        aliases.add(alias);
        alias = new SOCResourceAlias(); alias.setAlias("B"); alias.setResource_id(resourceB);
        aliases.add(alias);
        alias = new SOCResourceAlias(); alias.setAlias("C"); alias.setResource_id(resourceAA);
        aliases.add(alias);
        alias = new SOCResourceAlias(); alias.setAlias("D"); alias.setResource_id(resourceBB);
        aliases.add(alias);
        
        job.setAliases(aliases);
        job.setUserToken("farida");
       
        
        addJob(service, job);
  //      System.out.println("Output of get JSON Job object: "+ getJob(service));
     
    }
    
    private static void addResource(WebResource service, SOCResource resource)
    {
    	try {
	   		   
    		System.out.println(service.getURI().toString());
    		
    		String meta_data= defaultMapper.writeValueAsString(resource);
    	   	
    		System.out.println(meta_data);
    
    		ClientResponse response = service.path("/resource/upload").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, meta_data);
    		
    		
    		if (response.getStatus() != 201 ) {
    		   throw new RuntimeException("Failed : HTTP error code : "
    			+ response.getStatus()); 
    		}
    		else
    		{
    	 		String output = response.getEntity(String.class);
    	 		resourceID = new String(output);
    			System.out.println("Matrix ID : " + output); 
    		}
     
    	    
    		System.out.println("JSON Object for matrix upload sent successfully to server .... \n");
    		
    	  } catch (Exception e) {
     
    		e.printStackTrace();
     
    	  }
    	
    	

    }
    
    
    private static String getResource(WebResource service)
    {
    	ClientResponse response = service.path("/resource/"+resourceID).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
    	//System.out.println("---------------- "+response.getStatus());
    	if (response.getStatus() != 200 ) {
 		   throw new RuntimeException("Failed : HTTP error code : "
 			+ response.getStatus()); 
 		}
  
 		String output = response.getEntity(String.class);
  
 		System.out.println("Output from Server .... \n");
 		System.out.println(output);
 		return output;
 	
    }

    private static void addJob(WebResource service, SOCJob job)
    {
    	try {
	   		   
    		System.out.println(service.getURI().toString());
    		
    		String meta_data= defaultMapper.writeValueAsString(job);
    	   	
    		System.out.println(meta_data);
    
    		ClientResponse response = service.path("/job/compute").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, meta_data);
    		
    		if (response.getStatus() != 201 ) {
    		   throw new RuntimeException("Failed : HTTP error code : "
    			+ response.getStatus()); 
    		}
    		else
    		{
    	 		String output = response.getEntity(String.class);
    	 		jobID = new String(output);
    			System.out.println("Job ID : " + output); 
    		}
     
    	    
    		System.out.println("JSON Object for job addition sent successfully to server .... \n");
    		
    	  } catch (Exception e) {
     
    		e.printStackTrace();
     
    	  }
    	
    	

    }
    
    
    private static String getJob(WebResource service)
    {
    	ClientResponse response = service.path("/job/"+jobID).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
    	//System.out.println("---------------- "+response.getStatus());
    	if (response.getStatus() != 200 ) {
 		   throw new RuntimeException("Failed : HTTP error code : "
 			+ response.getStatus()); 
 		}
  
 		String output = response.getEntity(String.class);
  
 		System.out.println("Output from Server .... \n");
 		System.out.println(output);
 		return output;
 	
    }

    /*
    private static String getResponse(WebResource service) {
        return service.path("/hello").accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }
 
    private static String getOutputAsXML(WebResource service) {
        return service.path("/hello").accept(MediaType.TEXT_XML).get(String.class);
    }
    
    private static String getOutputAsString(WebResource service) {
        return service.path("/hello").accept(MediaType.TEXT_PLAIN).get(String.class);
    }
 
    private static String getOutputAsHTML(WebResource service) {
        return service.path("/hello").accept(MediaType.TEXT_HTML).get(String.class);
    }
    
    private static String getOutputMsgAsText(WebResource service) {
        return service.path("/parameter").path("/Farida's Message").accept(MediaType.TEXT_PLAIN).get(String.class);
    }
   */ 
}



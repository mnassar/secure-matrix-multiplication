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
public class SOCClient {

	private String broker_url;
	private WebResource service;
	protected final static ObjectMapper defaultMapper = new ObjectMapper();
	
	SOCClient(String url)
	{
		broker_url= new String(url);
	}
	
	public boolean connect(String REST_URI)
	{  
		ClientConfig config = new DefaultClientConfig();
		//   config.getClasses().add(JSONRootElementProvider.class);
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);
		service = client.resource(REST_URI);
		System.out.println("---------------------------------------------------");

		if(service!=null)
			return true; //connected
		else return false;

	}
	
	public boolean connect(String REST_URI, String user_token){
		
		boolean connected= false;
		//CHECk user authorization on broker
		
		
		return connect(REST_URI);
	}
	public boolean connect()
	{
		return connect(broker_url);
	}
	
	public String addResource(SOCResource resource)
	{
		String resourceID =new String("0");
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
		
		return resourceID;
	}
	
	public String addJob(SOCJob job)
	{
		String jobID = new String("0");
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
		
		return jobID;
	}
}

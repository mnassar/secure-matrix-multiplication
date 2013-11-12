package client;
import soc.client.SOCClient;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.xml.sax.SAXException;

//import broker.BrokerSOCResource;

import broker.JobType;
import broker.MatrixMeta;
import broker.MetadataStoreConnection;
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
import soc.client.SOCClient;
import sun.net.www.http.HttpClient;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.EntityTag;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpRetryException;
import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonTypeInfo;


public class RESTClient {

    static final String REST_URI = "http://10.160.2.27:8080/SOC/rest";
static boolean test_local = true;
    static String resourceID;
    static String jobID;
   protected  static ObjectMapper defaultMapper = new ObjectMapper();
   
    //static { // if you need to change default configuration:
      //defaultMapper.configure(SerializationConfig.Feature.USE_STATIC_TYPING, true); // faster this way, not default
    //}
 
    public static void main(String[] args) throws Exception {
 
    	//defaultMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//, JsonTypeInfo.As.WRAPPER_OBJECT);
    	//defaultMapper.enableDefaultTyping();
    	//defaultMapper.setSerializationConfig(JsonSerialize.Inclusion.NON_NULL);
    	//REST_URL is a string holding the broker url for the rest services of SOC
    	//USER_TOKEN is a string holding the user specific token received upon subscribing to the broker
    	SOCClient soc =null;
    	String userToken=null;
    			if(test_local)
    	{
    		 soc = new SOCClient("http://localhost:8080/SOC/rest");
    	
    	soc.setBroker_ftp_url("localhost");
    	soc.setUsername("farida");
        soc.setPassword("yasmine_123" );
    	 userToken= soc.connect();
        userToken= "hadoop";
    	}
    	else
    	{
	 soc = new SOCClient(REST_URI);
    	soc.setBroker_ftp_url("10.160.2.27");
    	soc.setUsername("hadoop");
        soc.setPassword("broker_1212");
    	 userToken= soc.connect();
        userToken= "hadoop";
    	}
    	 
    	  
        SOCResource resource = new SOCResource(StorageProtocol.ADDITIVE_SPLITTING);
		resource.setUser_token("hadoop");
		resource.setFile_path("/home/farida/Documents/A1");
		MatrixMeta meta = new MatrixMeta(10,10,"INTEGER");
		resource.setResource_meta(meta);
        
		resourceID = soc.addResource(resource);
//      System.out.println("Output of get JSON resource object: "+ getResource(service));
        String resourceA = new String(resourceID);
        
        Thread.sleep(1000);
        resourceID = soc.addResource(resource);
       
        String resourceAA = new String(resourceID);
        
        System.out.println("---------------------------------------------------");
        resource = new SOCResource(StorageProtocol.ADDITIVE_SPLITTING);
		resource.setUser_token(userToken);
		resource.setFile_path("/home/farida/Documents/B1");
		meta = new MatrixMeta(10,10,"INTEGER");
		resource.setResource_meta(meta);
       
		Thread.sleep(1000);
		resourceID = soc.addResource(resource);
        
	//	System.out.println("Output of get JSON resource object: "+ getResource(service));
        String resourceB = new String(resourceID);
        
        Thread.sleep(1000);
        resourceID = soc.addResource(resource);
        
        String resourceBB = new String(resourceID);
        
        SOCJob job = new SOCJob();
        job.setExpression("A*B+C*D");
        job.setType(JobType.MATRIX_COMPUTATION);
        ArrayList<SOCResourceAlias> aliases = new ArrayList<SOCResourceAlias>();
        SOCResourceAlias alias = new SOCResourceAlias(resourceA,"A");
        aliases.add(alias);
        alias = new SOCResourceAlias(resourceB,"B"); 
        aliases.add(alias);
        alias = new SOCResourceAlias(resourceAA,"C");
        aliases.add(alias);
        alias = new SOCResourceAlias(resourceBB,"D");
        aliases.add(alias);
        
        job.setAliases(aliases);
        job.setUserToken(userToken);
       
        Thread.sleep(100000);
        
        jobID= soc.addJob(job);
       
 //       MetadataStoreConnection conn = new MetadataStoreConnection("jdbc:derby://localhost:1527//home/farida/Documents/SOC/db/socdb");
   //     String job_desc= conn.getSOCJob(jobID);
        
     //   System.out.println("Description of the job is "+ job_desc);
        
        /*
       
        String jobID= "2210";
        
        
        String status = soc.getJobStatus(jobID);
        System.out.println("Status of the job is "+ status);
        
        String run_time = soc.getJobRunTime(jobID);
        System.out.println("Total runtime of the job is "+ run_time);
        //System.out.println("Submission date of the job is "+ status.getJobSubmissionDate());
        //System.out.println("Completion date of the job is "+ status.getJobCompletionDate());
        
        
        String result = soc.getJobResult(jobID);
        System.out.println("Result ID of the job is "+ result);
        String download_directory ="/home/farida/Downloads";
        
        boolean download_started = soc.getResource(result, download_directory);
        if(download_started)
        	System.out.println("File started to be downloaded!");
     */
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



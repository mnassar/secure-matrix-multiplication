
import java.io.IOException;
import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.EntityTag;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpRetryException;

public class HelloClientXML { 

    static final String REST_URI = "http://localhost:8080/SecureOutsourcing/rest";

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
        System.out.println("Output of get XML object: "+ getXML(service));
       
       // PostMatrix(service);
        MatrixOP mainmatrixOP = new MatrixOP("multiply", "/home/farida/Documents/A", "/home/farida/Documents/B","localhost:8080/HelloClient/callBack1");
        StartOperation(service, mainmatrixOP); 
        
        
    	MatrixMeta metaA = new MatrixMeta("A1", "/home/farida/Documents/A1", 10,10);		   
		MatrixMeta metaB = new MatrixMeta("B1", "/home/farida/Documents/B1", 10,10);
		MatrixOP multmatrixOP = new MatrixOP("multiply", metaA.getPath(), metaB.getPath(),"localhost:8080/HelloClient/callBack1");
       
        SendXMLOperation(service, multmatrixOP);
       
        MatrixOP copymatrixOP = new MatrixOP("copy", "/home/farida/Documents/A1Boutput" , "/home/mo_usr/Matrices/C1","localhost:8080/HelloClient/callBack1");
       
        SendXMLOperation(service, copymatrixOP);
       
        metaA = new MatrixMeta("A2", "/home/farida/Documents/A2", 10,10);		   
		metaB = new MatrixMeta("B2", "/home/farida/Documents/B2", 10,10);
		MatrixOP multmatrixOP2 = new MatrixOP("multiply", metaA.getPath(), metaB.getPath(),"localhost:8080/HelloClient/callBack2");
	
		System.out.println(multmatrixOP2.toString());
		SendXMLOperation(service, multmatrixOP2);
		
		copymatrixOP = new MatrixOP("copy", "/home/farida/Documents/A2Boutput" , "/home/mo_usr/Matrices/C2","localhost:8080/HelloClient/callBack3");
	    
		System.out.println(copymatrixOP.toString());
        SendXMLOperation(service, copymatrixOP);
       
        metaA = new MatrixMeta("C1", "/home/farida/Documents/C1", 10,10);		   
		metaB = new MatrixMeta("C2", "/home/farida/Documents/C2", 10,10);
		
        MatrixOP addmatrixOP = new MatrixOP("add", metaA.getPath(), metaB.getPath(),"localhost:8080/HelloClient/callBack4");
		
		SendXMLOperation(service, addmatrixOP);

    }
   
    
    private static String getXML(WebResource service)
    {
    	String response = service.path("/matrix/get").accept(MediaType.APPLICATION_XML).get(String.class);
    	//System.out.println("---------------- "+response.getStatus());
    	
 		//System.out.println("Output from Server .... \n");
 		System.out.println(response);
 		return response;
 	
    }
    private static void SendXMLOperation(WebResource service, MatrixOP matrixOP)
    {
    	
    	try {
    
    	
    		System.out.println(service.getURI().toString());
    	//	String mat_operation= defaultMapper.writeValueAsString(matrixOP);
    		String mat_operation= matrixOP.toString();
    	
    		System.out.println(mat_operation);
    
    		String response = service.path("/matrix/postoperation").type(MediaType.APPLICATION_XML).post(String.class, mat_operation);
    
     
    	    
    		System.out.println("XML Object for matrix operation has been sent successfully to server .... \n" +response);
    		
    	  } catch (Exception e) {
     
    		e.printStackTrace();
     
    	  }
    	
    	
    }

    private static void StartOperation(WebResource service, MatrixOP matrixOP)
    {
    	
    	try {
    
    	
    		System.out.println(service.getURI().toString());
    	//	String mat_operation= defaultMapper.writeValueAsString(matrixOP);
    		String mat_operation= matrixOP.toString();
    	
    		System.out.println(mat_operation);
    
    		String response = service.path("/matrix/post").type(MediaType.APPLICATION_XML).post(String.class, mat_operation);
    
     
    	    
    		System.out.println("XML Object for matrix operation has been sent successfully to server .... \n Job ID :" + response);
    		
    	  } catch (Exception e) {
     
    		e.printStackTrace();
     
    	  }

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




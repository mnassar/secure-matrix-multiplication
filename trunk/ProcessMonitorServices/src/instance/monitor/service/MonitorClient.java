package instance.monitor.service;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;



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
	        String result = getOutputAsString(service);
	        System.out.println(result);
	        parseXML(result, "26451");
	      
	        System.out.println("---------------------------------------------------");
	      
	        GetInstanceTime(service);
	        
			
	 
	    }
	    
	    private static void parseXML(String result, String id)
	    {
	    	  DocumentBuilder db;
				try {
					db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					 InputSource is = new InputSource();
					    is.setCharacterStream(new StringReader(result));

					    org.w3c.dom.Document doc = db.parse(is);
						
					    NodeList nodes = doc.getElementsByTagName("instance-info");

					    for (int i = 0; i < nodes.getLength(); i++) {
					      Element element = (Element) nodes.item(i);

					      NodeList name = element.getElementsByTagName("ns:status");
					      Element line = (Element) name.item(0);
					     String status =new String(((CharacterData)line.getFirstChild()).getData()); 
					      
					      NodeList firstPath = element.getElementsByTagName("ns:dt-started");
					      line = (Element) firstPath.item(0);
					      String start_date=new String(((CharacterData)line.getFirstChild()).getData());
					      
					      NodeList secondPath = element.getElementsByTagName("ns:dt-last-active");
					      line = (Element) secondPath.item(0);
					      String end_date=new String(((CharacterData)line.getFirstChild()).getData());
					      DateFormat xsdDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
					      //Date format: 2013-04-01T22:50:38.877-07:00
					      //<axis2ns363:getInstanceInfoResponse xmlns:axis2ns363="http://www.apache.org/ode/pmapi"><instance-info><ns:iid xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">26451</ns:iid><ns:pid xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">{http://matrix.bpelprocess}WF_Process-28</ns:pid><ns:process-name xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/" xmlns:mat="http://matrix.bpelprocess">mat:WF_Process</ns:process-name><ns:root-scope xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/" siid="26501" status="ACTIVE" name="__PROCESS_SCOPE:WF_Process" modelId="76" /><ns:status xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">ACTIVE</ns:status><ns:dt-started xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">2013-04-01T22:50:38.877-07:00</ns:dt-started><ns:dt-last-active xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">2013-04-01T22:50:41.098-07:00</ns:dt-last-active> .....
					      Date sDate = xsdDF.parse(start_date); 
						   Date eDate= xsdDF.parse(end_date);
						   
						   long diffInMillies = eDate.getTime() - sDate.getTime();
						    long difference = TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);
						    
						    String duration = String.valueOf(difference);
						    System.out.println("The process with id "+id + " took  "+duration + " seconds with " + status + " status");
					      
					     
					    }
				} catch (SAXException | IOException | ParserConfigurationException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	    
	    private static String getOutputAsString(WebResource service) {
	        return service.path("/instance/get").accept(MediaType.TEXT_PLAIN).get(String.class);
	    }
	    
	    private static void GetInstanceTime(WebResource service)
	    {
	    	
	    	try {
	    	   		   
	    		String instanceID = "26451";
	    		ClientResponse response = service.path("/instance/getTime").type(MediaType.TEXT_PLAIN).post(ClientResponse.class, instanceID);
	    		
	    		
	    		if (response.getStatus() != 200  ) {
	    		   throw new RuntimeException("Failed : HTTP error code : "
	    			+ response.getStatus()); 
	    		}
	     
	    	    String result = response.getEntity(String.class);
	    		System.out.println("Duration for the process .... \n" + result );
	    		parseXML(result, instanceID);
	    		
	    	  } catch (Exception e) {
	     
	    		e.printStackTrace();
	     
	    	  }
	    	
	    	
	    }
	    
	 
	 
}

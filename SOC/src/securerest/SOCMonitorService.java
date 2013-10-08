package securerest;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import broker.BrokerSOCResource;
import broker.MetadataStoreConnection;
import broker.SOCConfiguration;
import broker.SOCJob;
import broker.SOCJobStatus;

import com.google.gson.Gson;
import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;


import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path(value="/monitorJob")
public class SOCMonitorService {

	protected final static ObjectMapper defaultMapper = new ObjectMapper();
	private ServiceClientUtil _client;

	@POST
	@Path("/getRunTime")
	@Consumes (MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String getProcessInstanceExecutionTime(String jobID) {

		SOCConfiguration conf = new SOCConfiguration();
		MetadataStoreConnection conn;
		SOCJob job = null;
		try {
			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
			String job_json = conn.getSOCJob(jobID);
			
			Gson gson = new Gson();
			job = gson.fromJson(job_json, SOCJob.class);
			
			String instanceID =null; //should be get from the metadataStore

			if(job !=null)
				instanceID = job.getBpel_instanceID();
			else
				return "No job instance with this ID is found!!";

			String duration = null;
			_client = new ServiceClientUtil();
			OMElement root = _client.buildMessage("getInstanceInfo", new String[] {"iid"}, new String[] {instanceID});
			OMElement result =null;
			Date sDate = null;
			Date eDate =null;
			try {

				result = sendToIM(root);
				String[] response = parseXML(result.toStringWithConsume());
				DateFormat xsdDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
				//Date format: 2013-04-01T22:50:38.877-07:00
				//<axis2ns363:getInstanceInfoResponse xmlns:axis2ns363="http://www.apache.org/ode/pmapi"><instance-info><ns:iid xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">26451</ns:iid><ns:pid xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">{http://matrix.bpelprocess}WF_Process-28</ns:pid><ns:process-name xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/" xmlns:mat="http://matrix.bpelprocess">mat:WF_Process</ns:process-name><ns:root-scope xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/" siid="26501" status="ACTIVE" name="__PROCESS_SCOPE:WF_Process" modelId="76" /><ns:status xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">ACTIVE</ns:status><ns:dt-started xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">2013-04-01T22:50:38.877-07:00</ns:dt-started><ns:dt-last-active xmlns:ns="http://www.apache.org/ode/pmapi/types/2006/08/02/">2013-04-01T22:50:41.098-07:00</ns:dt-last-active> .....
				sDate = xsdDF.parse(response[1]); 
				eDate= xsdDF.parse(response[2]);

				job.setJobCompletionDate(eDate);
				job.getStatus().setJobStatus(response[0]);
		//		long diffInMillies = eDate.getTime() - sDate.getTime();
	    //		long difference = TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);

				long diffInMillies = eDate.getTime() - job.getStatus().getJobSubmissionDate().getTime();
				long difference = TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);

				duration = String.valueOf(difference);

				conn.updateSOCJob(job);
				conn.close();
				
				return duration;
			} catch (AxisFault | XMLStreamException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "No job instance with this ID is found!!";
	}


	@POST
	@Path("/getStatus")
	@Consumes (MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String getProcessInstanceStatus(String jobID) {

		SOCConfiguration conf = new SOCConfiguration();
		MetadataStoreConnection conn;
		SOCJob job = null;
		try {
			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
			String job_json = conn.getSOCJob(jobID);
			
			Gson gson = new Gson();
			job = gson.fromJson(job_json, SOCJob.class);
			if(job!=null)
				return job.getStatus().getJobStatus();
			else
				return new String("No job instance with this ID is found!!");
			/*
			String instanceID =null; //should be get from the metadataStore

			if(job !=null)
				instanceID = job.getBpel_instanceID();
			else
				return new String("No job instance with this ID is found!!");

			String duration = null;
			_client = new ServiceClientUtil();
			OMElement root = _client.buildMessage("getInstanceInfo", new String[] {"iid"}, new String[] {instanceID});
			OMElement result =null;
			try {

				result = sendToIM(root);
				String[] response = parseXML(result.toStringWithConsume());

				DateFormat xsdDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
				Date eDate= xsdDF.parse(response[2]);

				job.setJobCompletionDate(eDate);
				job.getStatus().setJobStatus(response[0]);
				
				conn.updateSOCJob(job);
				conn.close();
				
				return response[0]; //Status of the job
				
				
			} catch (AxisFault | XMLStreamException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
  		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/getResult")
	@Consumes (MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String getJobResultID(String jobID) {

		SOCConfiguration conf = new SOCConfiguration();
		MetadataStoreConnection conn;
		SOCJob job = null;
		try {
			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
			String job_json = conn.getSOCJob(jobID);
			
			Gson gson = new Gson();
			job = gson.fromJson(job_json, SOCJob.class);
			
			conn.close();
			
			return job.getStatus().getResultID();
			 
  		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "No job instance with this ID is found!!";
	}

	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLastInstanceExecutionTime() {

		SOCConfiguration conf = new SOCConfiguration();
		String duration = new String("Don't exist");
		_client = new ServiceClientUtil();
		OMElement root = _client.buildMessage("getInstanceInfo", new String[] {"iid"}, new String[] {"26451"});
		OMElement result = null;

		try {

			result = sendToIM(root);
			String status = result.getAttributeValue(new QName(Namespaces.ODE_PMAPI, "status"));//getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
			//.getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "status")).getText();

			DateFormat xsdDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

			String start_time= result.getAttributeValue(new QName(Namespaces.ODE_PMAPI, "dt-started"));//result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
			//.getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "dt-started")).getText();

			String end_date = result.getAttributeValue(new QName(Namespaces.ODE_PMAPI, "dt-last-active"));//result.getFirstElement().getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "instance-info"))
			// .getFirstChildWithName(new QName(Namespaces.ODE_PMAPI, "dt-last-active")).getText();

			

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

	private  String[] parseXML(String result)
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
				
				String[] response = new String[4];
				//response[0] = new String(duration);
				response[0] = new String(status);
				response[1] = new String(start_date);
				response[2] = new String(end_date);
				return  response;

			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private OMElement sendToIM(OMElement msg) throws AxisFault {
		System.out.println("Calling ODE service ...." );
		return _client.send(msg, SOCConfiguration.BROKER_URL+"/ode/processes/InstanceManagement/getInstanceInfo");
		//"// "http://10.160.2.27:8080/ode/processes/InstanceManagement/getInstanceInfo"
	}

	/*
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String Compute(String op) throws IOException {

	 */	

}


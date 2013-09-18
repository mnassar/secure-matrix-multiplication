
package securerest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import javax.ws.rs.core.UriBuilder ;
import javax.ws.rs.core.Response ;
import javax.ws.rs.core.Response.ResponseBuilder ;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import matrix.splitters.AdditiveSplitter;

import org.codehaus.jackson.map.*;
import org.nfunk.jep.ParseException;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.core.header.FormDataContentDisposition;

import Jama.Matrix;

import broker.BrokerSOCResource;
import broker.JobType;
import broker.Location;
import broker.Log;
import broker.MetadataStoreConnection;
import broker.ResourceMeta;
import broker.ResourceMetaAdapter;
import broker.SOCConfiguration;
import broker.SOCJob;
import broker.SOCJobStatus;
import broker.StorageProtocol;
import broker.workflow.translate.expression.ExpressionToWorkflow;
import broker.workflow.translate.expression.ExpressionTranslator;

import java.net.URI;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

@Path(value="/job")
public class SOCJobService {

	protected final static ObjectMapper defaultMapper = new ObjectMapper();
	

@POST
@Path("/compute")
@Consumes(MediaType.APPLICATION_JSON)

public Response compute(SOCJob job)
{
		
		SOCConfiguration conf = new SOCConfiguration();
		
		final Log logfile = new Log(SOCConfiguration.LOG_DIRECTORY);
		//String job_id = UUID.randomUUID().toString();
		Random r= new Random();
		String job_id = new Integer(r.nextInt(10000)).toString();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   //get current date time with Date()
		   java.util.Date date = new java.util.Date();
	
		SOCJob jobOnBroker = new SOCJob(job);
		jobOnBroker.setJob_Id(job_id);
		jobOnBroker.setStatus(new SOCJobStatus(date));
	
		//save data in the metadata store
		MetadataStoreConnection conn;
		try {
			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
			conn.addSOCJob(jobOnBroker);
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		final SOCJob job_to_start = new SOCJob(jobOnBroker);
		//In a thread
		 new Thread(new Runnable() {
   			public void run() {
   	
   				if(job_to_start.getType() == JobType.MATRIX_COMPUTATION)
   				{
   					//parse expression & generate workflow
					ExpressionTranslator translator = new ExpressionTranslator(job_to_start.getJob_Id(), job_to_start.getExpression());
					MetadataStoreConnection conn = null;
					try {
						conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(conn !=null)
					{
						for(int i=0; i<job_to_start.getAliases().size(); i++)
						{
							String alias = job_to_start.getAliases().get(i).getAlias();
							String resourceID = job_to_start.getAliases().get(i).getResource_id();
							String resource = conn.getSOCResource(resourceID);
							Gson gson = new Gson();
							GsonBuilder builder = new GsonBuilder();
						    builder.registerTypeAdapter(ResourceMeta.class   , new ResourceMetaAdapter());
						    gson = builder.create();
							BrokerSOCResource resourceObj = gson.fromJson(resource, BrokerSOCResource.class);
							translator.addExpressionVariable(alias,resourceObj);
						}
						conn.close();
						ExpressionToWorkflow workflow_generator = new ExpressionToWorkflow(translator);
						workflow_generator.initialise();
						try {
							workflow_generator.convert();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//Execute the workflow
						logfile.write("Execution of workflow process starts at : "+ dateFormat.format(new java.util.Date()));
						try {
							SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
							SOAPConnection connection = sfc.createConnection();

							MessageFactory mf = MessageFactory.newInstance();
							SOAPMessage sm = mf.createMessage();

							SOAPHeader sh = sm.getSOAPHeader();
							SOAPBody sb = sm.getSOAPBody();
							sh.detachNode();
							QName bodyName = new QName(workflow_generator.getWorkflowNamespace(), "expjob_"+job_to_start.getJob_Id()+"Request", "tns");
							SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);
							QName qn = new QName("expression");
							
							SOAPElement quotation = bodyElement.addChildElement(qn);

							quotation.addTextNode(job_to_start.getExpression());

							qn = new QName("jobID");
							
							SOAPElement job = bodyElement.addChildElement(qn);

							job.addTextNode(job_to_start.getJob_Id());

						//	System.out.println("\n Soap Request:\n");
							sm.writeTo(System.out);
						
						//	System.out.println();

							URL endpoint = new URL(workflow_generator.getProcessURL());
							SOAPMessage response = connection.call(sm, endpoint);
							System.out.println(response.getContentDescription());
							logfile.write(response.getContentDescription());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						/*
						try {
							String soapXml = "<?xml version=\"1.0\" encoding=\"utf-16\"?>"+
						"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
						+ "<soap:Body><expjob_"+job_to_start.getJob_Id()+"Request> "
						+"<expression>"+ job_to_start.getExpression()+"</expression>"
						+"<jobID>"+job_to_start.getJob_Id()+"</jobID>"
						+"</expjob_"+job_to_start.getJob_Id()+"Request>"  
						+"</soap:Body></soap:Envelope>";

							java.net.URL url = new java.net.URL(workflow_generator.getProcessURL());
							java.net.URLConnection url_conn = url.openConnection();
							// Set the necessary header fields
							url_conn.setRequestProperty("SOAPAction", SOCConfiguration.BROKER_URL+"/ode/processes/expjob_"+job_to_start.getJob_Id());
							url_conn.setDoOutput(true);
							// Send the request
							java.io.OutputStreamWriter wr = new java.io.OutputStreamWriter(url_conn.getOutputStream());
							wr.write(soapXml);
							wr.flush();
							// Read the response
							java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(url_conn.getInputStream()));
							String line;

							while ((line = rd.readLine()) != null) { System.out.println(line);  }
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
*/
						
						
					}
					else // couldn't connect to database
					{
						System.out.println("Operation didn't complete, problem connecting to the data store");
					}
		
   				}
	
   			}
		 }).start();
		//return the job id used by the broker  to compute the expression			
		
			return Response.status(201).entity(jobOnBroker.getJob_Id()).build();
		}





@GET
@Path("/{jobID}") //+job_id
@Produces(MediaType.APPLICATION_JSON)
public SOCJob getJob(@PathParam("jobID") String jobID) {
  	
	SOCConfiguration conf = new SOCConfiguration();
	try {
		MetadataStoreConnection conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
		String job_description= conn.getSOCJob(jobID);
		conn.close();
		SOCJob job ;
		Gson gson = new Gson();
		job = gson.fromJson(job_description, SOCJob.class);

		return job;
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	
}
/*
@DELETE
@Path("/{resource_id}")//+resource_id
public void deleteResource(@PathParam("resourceID") String resourceID)
{
	SOCConfiguration conf = new SOCConfiguration();
	try {
		MetadataStoreConnection conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
		String resource_metadata= conn.getSOCResource(resourceID);
		if(resource_metadata !=null)
		{
			BrokerSOCResource resource ;
			
			Gson gson = new Gson();
			resource = gson.fromJson(resource_metadata, BrokerSOCResource.class);
			
			String fileLocation = SOCConfiguration.BROKER_STORAGE_PATH +"/"+ resource.getResource_id();
		
			File brokerfile = new File(fileLocation);
			if(brokerfile.exists())
			{
				brokerfile.delete();
			}
			else
			{
				//Get splits locations and delete them
			}
				
		}
		else
		{
			System.out.println("Resource "+ resourceID +" already not found!!");
		}
}
*/


@POST
@Path("/job")
@Consumes(MediaType.APPLICATION_JSON) //Job description
@Produces(MediaType.TEXT_PLAIN) 
public Response doOp(MatrixOP op) throws IOException {

	
		System.out.println(op.toString());	  	
		String matrix_job = op.getName();
		String pathA = op.getPathA();
		String pathB = op.getPathB();
		String callBack = op.getCallBack();
		//CALL the corresponding mapreduce job should be here
		if(matrix_job.equals("multiply"))
		//	; //call the matrix mult job on A and B
		{
	//		br.multiply(pathA,pathB, callBack);
		
		}
		else if (matrix_job.equals("add"))
		{
		//	br.add(pathA,pathB, callBack);
		}
		else if (matrix_job.equals("copy"))
		{
	//		br.copy(pathA,pathB, callBack);
		}
		return Response.status(201).entity(op.toString()).build();
	}
  
@GET
@Path("/job/")//+job_id
@Produces(MediaType.APPLICATION_JSON) 
public MatrixOP getJobStatus()
{
	return new MatrixOP();
}

  
} 

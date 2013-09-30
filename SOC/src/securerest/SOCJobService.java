
package securerest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
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
							if(resourceObj.getAvailable())
								translator.addExpressionVariable(alias,resourceObj);
							else
							{
								job_to_start.getStatus().setJobStatus("Failed to start: one or more of the resources are not ready!");
								conn.updateSOCJob(job_to_start);
								conn.close();
								return;
							}
						}
						
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

						org.apache.axis2.client.ServiceClient _serviceClient;
						try {
							_serviceClient = new org.apache.axis2.client.ServiceClient();
							_serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
									workflow_generator.getProcessURL()));
							_serviceClient.getOptions().setUseSeparateListener(false);
							org.apache.axis2.context.MessageContext _messageContext = null;


							org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(ServiceClient.ANON_OUT_IN_OP);
							_operationClient.getOptions().setAction("http://soc.expjob_"+workflow_generator.getJob_ID()+".workflow/start");
							_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



							//addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

							org.apache.axiom.soap.SOAPEnvelope env = null;
							_messageContext = new org.apache.axis2.context.MessageContext();


							SOAPFactory soapFactory = OMAbstractFactory.getSOAP11Factory();


							env = soapFactory.getDefaultEnvelope();

						//	env.setLocalName("expjob_2301Request");
							OMElement omElement = soapFactory.createOMElement(new QName("http://soc.expjob_"+workflow_generator.getJob_ID()+".workflow","expjob_"+workflow_generator.getJob_ID()+"Request","q0"));
							OMElement omElement1 = soapFactory.createOMElement(new QName("http://soc.expjob_"+workflow_generator.getJob_ID()+".workflow","expression","q0"));
							omElement1.setText(job_to_start.getExpression());
							
							OMElement omElement2 = soapFactory.createOMElement(new QName("http://soc.expjob_"+workflow_generator.getJob_ID()+".workflow","jobID","q0"));
							omElement2.setText(job_to_start.getJob_Id());
							
							env.getBody().addChild(omElement);
							env.getBody().getFirstElement().addChild(omElement1);
							env.getBody().getFirstElement().addChild(omElement2);


							//adding SOAP soap_headers
							_serviceClient.addHeadersToEnvelope(env);
							// create message context with that soap envelope

							env.writeTextTo(new PrintWriter(System.out), true);
							_messageContext.setEnvelope(env);

							// add the message contxt to the operation client
							_operationClient.addMessageContext(_messageContext);

							_operationClient.execute(true);


							if (_messageContext.getTransportOut() != null) {
								SOAPEnvelope response = _messageContext.getEnvelope();
								System.out.println(response); 
								_messageContext.getTransportOut().getSender().cleanup(_messageContext);
								job_to_start.getStatus().setJobStatus("Job in progress ....");
								conn.updateSOCJob(job_to_start);
								conn.close();
							}

						} catch (AxisFault e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						conn.close();
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

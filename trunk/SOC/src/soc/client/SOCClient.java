package soc.client;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import org.codehaus.jackson.Version;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.module.SimpleModule;
import org.xml.sax.SAXException;

//import soc.client.broker.BrokerSOCResource;
import broker.JobType;
import broker.MatrixMeta;
import broker.ResourceMeta;
import broker.ResourceMetaAdapter;
//import broker.SOCConfiguration;
import broker.SOCJob;
import broker.SOCJobStatus;
import broker.SOCResource;
import broker.SOCResourceAlias;
import broker.StorageProtocol;

//import client.SFtpDownloadExample;

import broker.BrokerSOCResource;
import broker.MetadataStoreConnection;
import broker.SOCConfiguration;

import com.google.gson.Gson;
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
import org.w3c.dom.CharacterData;


//import securerest.MatrixOP;
import sun.net.www.http.HttpClient;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.EntityTag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpRetryException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import org.codehaus.jackson.annotate.JsonTypeInfo;

public class SOCClient {

	private String broker_url;
	private String broker_ftp_url;
	private String username;
	private String password;
	private String user_token;
	private WebResource service;
	protected final static ObjectMapper defaultMapper = new ObjectMapper();

	public SOCClient(String url)
	{
		broker_url = new String(url);
		//broker_ftp_url = new String("10.160.2.27");
		username = "anonymous";
		password = "331";
	}

	public boolean connect(String REST_URI)
	{  
		ClientConfig config = new DefaultClientConfig();
		//   config.getClasses().add(JSONRootElementProvider.class);
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);
		service = client.resource(REST_URI);
		System.out.println("---------------------------------------------------");
		
		// SimpleModule module =  
			//      new SimpleModule("PolymorphicResourceMetaDeserializerModule",  
			  //        new Version(1, 0, 0, null));  
	   //module.addDeserializer(ResourceMeta.class, new ResourceMetaAdapter());  
		//defaultMapper.registerModule(module);
		//defaultMapper.registerSubtypes(broker.MatrixMeta);
		//defaultMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	//	defaultMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_OBJECT);
		if(service!=null)
		{
			//call authenticate service to set the user_token
			
			
			//////////////////////////////////////////////////
			return true; //connected
		}
		else 
			return false;
	}

	public boolean connect(String REST_URI, String user_token){

		boolean connected= false;
		//CHECk user authorization on broker
		return connect(REST_URI);
	}
	public String connect()
	{
		if (connect(broker_url))
			return user_token;
		else
			return null;
	}

	public String addResource(SOCResource resource)
	{
		String resourceID =new String("0");
		try {

			System.out.println(service.getURI().toString());
			defaultMapper.registerSubtypes(new NamedType(MatrixMeta.class, "matrix"));
			//defaultMapper.getSerializationConfig().constructSpecializedType(ResourceMeta.class, MatrixMeta.class);
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
				System.out.println("JSON Object for matrix upload sent successfully to server .... \n");
				System.out.println("Matrix ID : " + output); 
				FTPClient ftp = new FTPClient();
				ftp.connect(getBroker_ftp_url());
				ftp.login(getUsername(), getPassword());
				ftp.changeDirectory("/home/"+getUsername()+"/Documents/SOC/resources");
				//String dir = ftp.currentDirectory();
				
				//System.out.println(dir);
				//client.changeDirectory(newPath);
				String file_name =null;
				StringTokenizer tokenizer = new StringTokenizer(resource.getFile_path(),"/");
				while(tokenizer.hasMoreTokens())
				{
					file_name = tokenizer.nextToken();
				}
				ftp.upload(new File(resource.getFile_path()));
				ftp.rename(file_name, resourceID);
				ftp.disconnect(true);	
			//	SFtpUploadExample upload = new SFtpUploadExample();
			//	upload.setFile_name(resource.getFile_path());
			//	upload.setRemote_file_name(resourceID);
			//	upload.doUpload(getBroker_ftp_url(), getUsername(), getPassword() );
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceID;
	}

	public String getJobRunTime(String jobID)
	{
		try {

			//String instanceID = "4451";
			SOCJobStatus response = service.path("/job/status/"+jobID).type(MediaType.APPLICATION_JSON).get(SOCJobStatus.class);


			if (response ==null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						); 
			}

			System.out.println("Response Status: "+ response.toString());
			if(response.getJobStatus().contains("failed"))
				return "Job failed";
			else if(response.getJobStatus().contains("progress"))
				return "Job is still in progress ..";
			long diffInMillies = response.getJobCompletionDate().getTime() - response.getJobSubmissionDate().getTime();
			long difference = TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);

			String duration = String.valueOf(difference);

			//String result = response.getEntity(String.class);
			//System.out.println("Duration for the process .... \n" + result );
			//String status= parseXML(result, jobID, 1);
			
				return duration;
			

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;	
		/*
		try {

			//String instanceID = "4451";
			String response = service.path("/monitorJob/getRunTime").type(MediaType.TEXT_PLAIN).post(String.class, jobID);


			if (response != null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						);
			}

		//	String result = response.getEntity(String.class);
			System.out.println("Duration for the process .... \n" + response );
			//String run_time= parseXML(result, jobID, 0);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
		*/	
	}
	
	public String getJobState(String jobID)
	{

		try {

			//String instanceID = "4451";
			String response = service.path("/monitorJob/getStatus").type(MediaType.TEXT_PLAIN).post(String.class, jobID);


			if (response ==null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						); 
			}

			//String result = response.getEntity(String.class);
			//System.out.println("Duration for the process .... \n" + result );
			//String status= parseXML(result, jobID, 1);
			return response;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;	
	}

	public String getJobStatus(String jobID)
	{

		try {

			//String instanceID = "4451";
			SOCJobStatus response = service.path("/job/status/"+jobID).type(MediaType.APPLICATION_JSON).get(SOCJobStatus.class);


			if (response ==null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						); 
			}

			//String result = response.getEntity(String.class);
			//System.out.println("Duration for the process .... \n" + result );
			//String status= parseXML(result, jobID, 1);
			return response.getJobStatus();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;	
	}
	public String getJobResult(String jobID)
	{

		try {

			//String instanceID = "4451";
			SOCJobStatus response = service.path("/job/status/"+jobID).type(MediaType.APPLICATION_JSON).get(SOCJobStatus.class);


			if (response ==null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						); 
			}

			//String result = response.getEntity(String.class);
			//System.out.println("Duration for the process .... \n" + result );
			//String status= parseXML(result, jobID, 1);
			return response.getResultID();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;	
		/*
		try {

			//String instanceID = "4451";
			String response = service.path("/monitorJob/getResult").type(MediaType.TEXT_PLAIN).post(String.class, jobID);


			if (response ==null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						); 
			}

			//String result = response.getEntity(String.class);
			//System.out.println("Duration for the process .... \n" + result );
			//String status= parseXML(result, jobID, 1);
			return response;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
		*/	
	}

	private String parseXML(String result, String id, int info_type) //0: runtime
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
				if(info_type ==1)
					return status;

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
				System.out.println("The process with id "+id + " took  "+diffInMillies + " milliseconds with " + status + " status");
				if(info_type == 0)
					return duration;

			}
		} catch (SAXException | IOException | ParserConfigurationException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean getResource(String resourceID, String download_path)
	{

		try {

			System.out.println(service.getURI().toString());

			//String meta_data= defaultMapper.writeValueAsString(resourceID);

			//	System.out.println(meta_data);

			SOCResource resource = service.path("/resource/"+resourceID).accept(MediaType.APPLICATION_JSON).get(SOCResource.class);


			if (resource == null ) {
				throw new RuntimeException("Failed : No response received from broker");
			}
			else
			{

				System.out.println("Resource Path to download: " + resource.getFile_path()); 
			/*	
			 * SFtpDownloadExample download = new SFtpDownloadExample();
				download.setLocal_file(download_path+"/"+resourceID);
				download.setRemote_file(resource.getFile_path());
				download.doDownload(SOCConfiguration.BROKER_URL, getUsername(), getPassword() );
				*/
				FTPClient ftp = new FTPClient();
				ftp.connect(getBroker_ftp_url());
				ftp.login(getUsername(), getPassword());
				ftp.changeDirectory("/home/"+getUsername()+"/Documents/SOC/resources");
				//String dir = ftp.currentDirectory();
				
				//System.out.println(dir);
				//client.changeDirectory(newPath);
				String file_name =download_path+"/"+resourceID;
				
				ftp.download(resourceID, new File(file_name));
				//ftp.rename(file_name, resourceID);
				ftp.disconnect(true);
				return true;
			}




		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = new String(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new String(password);
	}

	public String getBroker_ftp_url() {
		return broker_ftp_url;
	}

	public void setBroker_ftp_url(String broker_ftp_url) {
		this.broker_ftp_url = new String(broker_ftp_url);
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

	public String getResource(String resourceID)
	{

		try {

			System.out.println(service.getURI().toString());

			//String meta_data= defaultMapper.writeValueAsString(resourceID);

			//	System.out.println(meta_data);

			String resource = service.path("/resource/test/"+resourceID).type(MediaType.TEXT_PLAIN).get(String.class);


			if (resource == null ) {
				throw new RuntimeException("Failed : No response received from broker");
			}
			else
			{

				System.out.println("Resource  " + resource); 
			/*	
			 * SFtpDownloadExample download = new SFtpDownloadExample();
				download.setLocal_file(download_path+"/"+resourceID);
				download.setRemote_file(resource.getFile_path());
				download.doDownload(SOCConfiguration.BROKER_URL, getUsername(), getPassword() );
				*/
			
				return resource;
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return "Hello";
	}

	public String getJobInfo(String jobID)
	{
		try {

			//String instanceID = "4451";
			SOCJob response = service.path("/job/"+jobID).type(MediaType.APPLICATION_JSON).get(SOCJob.class);


			if (response ==null  ) {
				throw new RuntimeException("Failed : No response received from broker!! "
						); 
			}

			Gson gson = new  Gson();
			String json = gson.toJson(response);
						
				return json;
			

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;	
	}

	public static void main(String[] args) throws Exception
	{
	//	SOCClient soc = new SOCClient("http://localhost:8080/SOC/rest");
	//	soc.connect();
		
		  //FTP Test
		FTPClient ftp = new FTPClient();
		ftp.connect("localhost");
		ftp.login("farida", "yasmine_123");
		ftp.changeDirectory("/home/farida/Documents/SOC/resources");
		//String dir = ftp.currentDirectory();
		
		//System.out.println(dir);
		//client.changeDirectory(newPath);
	
		String file_name =null;
		StringTokenizer tokenizer = new StringTokenizer("/home/farida/Documents/A2","/");
		while(tokenizer.hasMoreTokens())
		{
			file_name = tokenizer.nextToken();
		}
		ftp.upload(new File("/home/farida/Documents/A2"));
		ftp.rename(file_name, "uploadedA2");
		ftp.disconnect(true);
		
		
		//String res  = soc.getResource("123"); 
		//System.out.println(res);
		//String job  = soc.getJobInfo("9533"); 
		//System.out.println(job);

//		final SOCConfiguration conf = new SOCConfiguration();
//
//		Random r= new Random();
//		String resource_id = new Integer(r.nextInt(10000)).toString();
//
//		//String resource_id = UUID.randomUUID().toString();
//
//		String uploadedFileLocation = SOCConfiguration.BROKER_STORAGE_PATH +"/"+ resource_id;
//
//		BrokerSOCResource resourceOnBroker = new BrokerSOCResource(new BrokerSOCResource("1234"));
//		resourceOnBroker.setResource_id(resource_id);
//		resourceOnBroker.setFile_path(uploadedFileLocation);
//
		    
	}
}

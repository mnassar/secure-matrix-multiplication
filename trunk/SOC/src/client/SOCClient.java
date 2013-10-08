package client;

import it.sauronsoftware.ftp4j.FTPClient;

import java.io.IOException;
import javax.xml.parsers.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import broker.BrokerSOCResource;
import broker.JobType;
import broker.MatrixMeta;
import broker.ResourceMeta;
import broker.SOCConfiguration;
import broker.SOCJob;
import broker.SOCJobStatus;
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
import org.w3c.dom.CharacterData;


import securerest.MatrixOP;
import sun.net.www.http.HttpClient;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.EntityTag;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpRetryException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
public class SOCClient {

	private String broker_url;
	private String broker_ftp_url;
	private String username;
	private String password;
	private WebResource service;
	protected final static ObjectMapper defaultMapper = new ObjectMapper();

	SOCClient(String url)
	{
		broker_url= new String(url);
		broker_ftp_url = new String("10.160.2.27");
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
				System.out.println("JSON Object for matrix upload sent successfully to server .... \n");
				System.out.println("Matrix ID : " + output); 
				FTPClient ftp = new FTPClient();
				ftp.connect(getBroker_ftp_url());
				ftp.login(getUsername(), getPassword());
				ftp.changeDirectory("/home/hadoop/Documents/SOC/resources");
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
	}
	
	public String getJobStatus(String jobID)
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

	public String getJobResult(String jobID)
	{

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
				SFtpDownloadExample download = new SFtpDownloadExample();
				download.setLocal_file(download_path+"/"+resourceID);
				download.setRemote_file(resource.getFile_path());
				download.doDownload(SOCConfiguration.BROKER_URL, getUsername(), getPassword() );
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
		this.broker_ftp_url = broker_ftp_url;
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

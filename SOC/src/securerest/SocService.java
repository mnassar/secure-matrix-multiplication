
package securerest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import matrix.splitters.AdditiveSplitter;

import org.codehaus.jackson.map.*;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;

import Jama.Matrix;
import broker.Broker;
import broker.BrokerSOCResource;
import broker.Location;
import broker.MetadataStoreConnection;
import broker.SOCConfiguration;
import broker.StorageProtocol;

import java.net.URI;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

@Path(value="/resource")
public class SocService {

	protected final static ObjectMapper defaultMapper = new ObjectMapper();
	protected static Broker br= new Broker();

@POST
@Path("/upload")
@Consumes(MediaType.APPLICATION_JSON)

public Response storeResource(SOCResource resource)
{
		//@FormParam("file") InputStream uploadedInputStream,
		//@FormParam("file") FormDataContentDisposition fileDetail) {
		SOCConfiguration conf = new SOCConfiguration();
		String resource_id = UUID.randomUUID().toString();
		
		String uploadedFileLocation = SOCConfiguration.BROKER_STORAGE_PATH +"/"+ resource_id;

		BrokerSOCResource resourceOnBroker = new BrokerSOCResource(resource);
		resourceOnBroker.setResource_id(resource_id);
		resourceOnBroker.setFile_path(uploadedFileLocation);
		
		
		///////////////// Read Matrix from file and store it to the broker grid ftp server
		// UPLOAD ... will be replaced by the GridFTP or  alternative code to upload the file
		///////////////////////////////////
		InputStream uploadedInputStream;
		try {
			uploadedInputStream = new FileInputStream(new File(resource.getFile_path()));
			saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;

		//do the splitting of file into two files if additive splitting
		//and save the  splits on the cloud
		if(resource.getStorage_protocol() == StorageProtocol.ADDITIVE_SPLITTING)
		{
			AdditiveSplitter splitter = new AdditiveSplitter(resourceOnBroker);
			
			Location loc_split1 = conf.getRandomCloud();
			Location loc_split2 ;
			while((loc_split2= conf.getRandomCloud())!=loc_split1);
			
			//splitter.Split(loc_split1, loc_split2);
			resourceOnBroker.addLocation(loc_split1);
			resourceOnBroker.addLocation(loc_split2);
		}
		
		
		
		//save data in the metadata store
		MetadataStoreConnection conn;
		try {
			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
			conn.addSOCResource(resourceOnBroker);
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		//return the resource id used by the broker  to get the matrix			
		
			return Response.status(201).entity(resourceOnBroker.getResource_id()).build();
		}


// save uploaded file to new location
private void saveToFile(InputStream uploadedInputStream,
	String uploadedFileLocation) {

	try {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(uploadedFileLocation));
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	} catch (IOException e) {

		e.printStackTrace();
	}

}


@GET
@Path("/{resourceID}") //+resource_id
@Produces(MediaType.TEXT_PLAIN)
public String getResource(@PathParam("resourceID") String resourceID ) {
  	
	SOCConfiguration conf = new SOCConfiguration();
	try {
		MetadataStoreConnection conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
		String resource_metadata= conn.getSOCResource(resourceID);
		conn.close();
		BrokerSOCResource resource ;
		Gson gson = new Gson();
		resource = gson.fromJson(resource_metadata, BrokerSOCResource.class);
		
		String fileLocation = SOCConfiguration.BROKER_STORAGE_PATH +"/"+ resource.getResource_id();
		
		File brokerfile = new File(fileLocation);
		if(brokerfile.exists())
		{
			//copy directly to client
		}
		else
		{
			//Get the splits locations and add them to get the original file
		}
		
		String message = resource.getResource_id() + " saved !";
		
		return message;
		
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


  
} 


package securerest;
import java.io.IOException;
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


import javax.ws.rs.core.UriBuilder ;
import javax.ws.rs.core.Response ;
import javax.ws.rs.core.Response.ResponseBuilder ;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.codehaus.jackson.map.*;

import Jama.Matrix;
import broker.Broker;

import java.net.URI;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /matrix
@Path(value="/matrix")
public class MatrixOPService {

	protected final static ObjectMapper defaultMapper = new ObjectMapper();
	protected static Broker br= new Broker();

@GET
@Path("/get")
@Produces(MediaType.APPLICATION_XML)
public String getMatrixOp() {
  	MatrixOP op = new MatrixOP("multiply","A","B","callback");
  	System.out.println("Operation get : " + op.toString());
  	return op.toString();
}


@POST
@Path("/postoperation")
@Consumes(MediaType.APPLICATION_XML)
public void doMatrixOp(String op) throws IOException {

	
		System.out.println("Operation posted: " +op);	  	
		MatrixOP opObj = new MatrixOP(op); 
		String matrix_job = opObj.getName();
		String pathA = opObj.getPathA();
		String pathB = opObj.getPathB();
		String callBack = opObj.getCallBack();
		//CALL the corresponding mapreduce job should be here
		if(matrix_job.equals("multiply"))
		//	; //call the matrix mult job on A and B
		{
			br.multiply(pathA,pathB, callBack);
		
		}
		else if (matrix_job.equals("add"))
		{
			br.add(pathA,pathB, callBack);
		}
		else if (matrix_job.equals("copy"))
		{
			br.copy(pathA,pathB, callBack);
		}
		
	}
  

  
} 

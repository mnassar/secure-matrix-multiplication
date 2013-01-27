package securepckg;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import org.codehaus.jackson.map.ObjectMapper;



@Path("/service")
public class MatrixService {

	//protected final static ObjectMapper defaultMapper = new ObjectMapper();	

@POST
@Path("/post")
@Consumes(MediaType.APPLICATION_XML)
public void postMatrixOP(String m) {

		System.out.println(m.toString());	  	
}

@GET
@Path("/get")
@Produces(MediaType.APPLICATION_XML)
public String getMatrixOp() {
  	String op = new String("<matrixOP><name>multiply</name><pathA>A</pathA><pathB>B</pathB>");
  	return op;
}


	
}

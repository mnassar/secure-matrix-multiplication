package sample.hello.resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import sample.hello.bean.Matrix;
import sample.hello.storage.MatrixStore;


@Path("/matrices")
public class MatricesResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Matrix> getMatrices() {
		List<Matrix> matrices = new ArrayList<Matrix>();
		matrices.addAll( MatrixStore.getStore().values() );
		return matrices;
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = MatrixStore.getStore().size();
		return String.valueOf(count);
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newMatrix(
			@FormParam("id") String id,
			@FormParam("rows") String rows,
			@FormParam("columns") String columns,
			@FormParam("content") String content,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Matrix c = new Matrix(id,rows,columns,content);
		MatrixStore.getStore().put(id, c);
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		//System.out.println(uri);
		Response.created(uri).build();
		servletResponse.sendRedirect("../pages/new_matrix.html");
	}
	
	
	@Path("{matrix}")
	public MatrixResource getMatrix(
			@PathParam("matrix") String  matrix) {
		return new MatrixResource(uriInfo, request, matrix);
	}
	
}

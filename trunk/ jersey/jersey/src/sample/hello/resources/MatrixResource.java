package sample.hello.resources;

import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import sample.hello.bean.Matrix;
import sample.hello.storage.MatrixStore;

import com.sun.jersey.api.NotFoundException;


public class MatrixResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String matrix_id;
	
	public MatrixResource(UriInfo uriInfo, Request request, 
			String matrix_id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.matrix_id = matrix_id;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Matrix getMatrix() {
		Matrix mat = MatrixStore.getStore().get(matrix_id);
		if(mat==null)
			throw new NotFoundException("No such Matrix.");
		return mat;
	}
	//to do: upload a matrix from a local file using post or put
	@PUT
	@Consumes({"application/xml","application/json"})
	public Response putMatrix(JAXBElement<Matrix> jaxbMatrix) {
	//public Response putMatrix(Matrix c) {
		Matrix c =  jaxbMatrix.getValue();
		System.out.println(c);
		return putAndGetResponse(c);
	}
	private Response putAndGetResponse(Matrix c) {
		Response res=null;
		if(MatrixStore.getStore().containsKey(c.getId())) {
			res = Response.noContent().build();
		} else {
			//URI share1_uri = uriInfo.getAbsolutePathBuilder().path(c.getId()+"_share1").build();
			//URI share2_uri = uriInfo.getAbsolutePathBuilder().path(c.getId()+"_share2").build();
			//res =Response.created(share1_uri).build();
			//Response.created(share2_uri).build();
			res=Response.created(uriInfo.getAbsolutePath()).build();
		}
		MatrixStore.getStore().put(c.getId(), c);
		Matrix[] shares =shareMatrix(c);
		MatrixStore.getStore().put(shares[0].getId(), shares[0]);
		MatrixStore.getStore().put(shares[1].getId(), shares[1]);
		//System.out.println(res.getMetadata().values());
		return res;
	}    
	private Matrix[] shareMatrix(Matrix c){
		int[] content = c.getContent();
		int rows= c.getRows();
		int columns = c.getColumns();
		double [][] dcontent = new double [rows][columns];
		//from matrix to jama matrix
		for (int i=0; i<rows; i++){
			for(int j=0;j<columns;j++){
				dcontent[i][j]=(double) content[i*columns+j];
				//System.out.print(dcontent[i][j]+" ");
			}
		//System.out.println();
		}
		Jama.Matrix jmatrix = new Jama.Matrix(dcontent);
		
		Jama.Matrix jshare1= Jama.Matrix.random(rows, columns).times
				(Matrix.random_generator.nextInt(100));
		// transform all the entries to int 
		for (int i=0; i<rows; i++)
			for(int j=0;j<columns;j++)
				jshare1.set(i,j,(double)( (int) jshare1.get(i,j)));
		Jama.Matrix jshare2=jmatrix.minus(jshare1);
		//from jama matrix to matrix
		int[] content_share1=new int[rows*columns];
		int[] content_share2=new int[rows*columns];
		for (int i=0; i<rows; i++)
			for(int j=0;j<columns;j++){
				content_share1[i*columns+j]=(int) jshare1.get(i,j);
				content_share2[i*columns+j]=(int) jshare2.get(i,j);
			}
		
		Matrix share1 = new Matrix(c.getId()+"_share1",rows,columns,content_share1);
		Matrix share2 = new Matrix(c.getId()+"_share2",rows,columns,content_share2);
		System.out.println(Arrays.toString(share1.getContent()));
		System.out.println(Arrays.toString(share2.getContent()));
		return new Matrix[]{share1,share2};
		
	}
}
package broker;

import com.google.gson.Gson;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("matrix")
public class MatrixMeta extends ResourceMeta {

	private int nRows;
	private int nColumns;
	private String dataType;
	
	
	public MatrixMeta(int nRows, int nColumns, String dataType) {
		super("matrix");
		this.nRows = nRows;
		this.nColumns = nColumns;
		this.dataType = new String(dataType);
	}
	public MatrixMeta() {
		super("matrix");
	
	}
	
	public MatrixMeta(ResourceMeta meta) {
		super("matrix");
		this.nRows = ((MatrixMeta)meta).getnRows();
		this.nColumns = ((MatrixMeta)meta).getnColumns();
		this.dataType= ((MatrixMeta)meta).getdataType();
	
	}
	

	public int getnRows() {
		return this.nRows;
	}
	public void setnRows(int nRows) {
		this.nRows = nRows;
	}
	public int getnColumns() {
		return this.nColumns;
	}
	public void setnColumns(int nColumns) {
		this.nColumns = nColumns;
	}
	
	public String getdataType() {
		return this.dataType;
	}
	
	public void setdataType(String dataType) {
	/*	if(type.equals("INTEGER"))
			this.dataType = DataType.INTEGER;
		else if(type.equals("REAL"))
			this.dataType = DataType.REAL;
		else if(type.equals("IMAGINARY"))
			this.dataType = DataType.IMAGINARY;
			*/
		this.dataType = new String(dataType);
	}
	public static void main(String[] args)
	{
		String meta = "{\"nRows\":10,\"nColumns\":10,\"dataType\":\"decimal\",\"type\":\"matrix\"}";

		Gson gson = new  Gson();
		
		MatrixMeta mat_meta = gson.fromJson(meta, MatrixMeta.class);
		System.out.println(mat_meta.getType()+":"+mat_meta.getnRows()+","+mat_meta.getnColumns()+"/"+mat_meta.getdataType());
	}
}

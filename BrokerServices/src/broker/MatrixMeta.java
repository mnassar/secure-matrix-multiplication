package broker;

import com.google.gson.Gson;

public class MatrixMeta extends ResourceMeta {

	public MatrixMeta(int nRows, int nColumns, String dataType) {
		super("matrix");
		this.nRows = nRows;
		this.nColumns = nColumns;
		this.dataType = dataType;
	}
	public MatrixMeta() {
		super("matrix");
	
	}
	
	public MatrixMeta(ResourceMeta meta) {
		super("matrix");
		this.nRows = ((MatrixMeta)meta).getnRows();
		this.nColumns = ((MatrixMeta)meta).getnColumns();
		this.dataType = ((MatrixMeta)meta).getDataType();
	
	}
	private int nRows;
	private int nColumns;
	private String dataType;
	

	public int getnRows() {
		return nRows;
	}
	public void setnRows(int nRows) {
		this.nRows = nRows;
	}
	public int getnColumns() {
		return nColumns;
	}
	public void setnColumns(int nColumns) {
		this.nColumns = nColumns;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public static void main(String[] args)
	{
		String meta = "{\"nRows\":10,\"nColumns\":10,\"dataType\":\"decimal\",\"type\":\"matrix\"}";

		Gson gson = new  Gson();
		
		MatrixMeta mat_meta = gson.fromJson(meta, MatrixMeta.class);
		System.out.println(mat_meta.getType()+":"+mat_meta.getnRows()+","+mat_meta.getnColumns()+"/"+mat_meta.getDataType());
	}
}

package broker;

import com.google.gson.Gson;

public class MatrixMeta extends ResourceMeta {

	public MatrixMeta(int nRows, int nColumns, DataType dataType) {
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
		setDataType(((MatrixMeta)meta).getDataType());
	
	}
	private int nRows;
	private int nColumns;
	private DataType dataType;
	

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
	public String getDataTypeString() {
		return dataType.toString();
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public void setDataType(String type) {
		if(type.equals("INTEGER"))
			this.dataType = DataType.INTEGER;
		else if(type.equals("REAL"))
			this.dataType = DataType.REAL;
		else if(type.equals("IMAGINARY"))
			this.dataType = DataType.IMAGINARY;
	}
	public static void main(String[] args)
	{
		String meta = "{\"nRows\":10,\"nColumns\":10,\"dataType\":\"INTEGER\",\"type\":\"matrix\"}";

		Gson gson = new  Gson();
		
		MatrixMeta mat_meta = gson.fromJson(meta, MatrixMeta.class);
		System.out.println(mat_meta.getType()+":"+mat_meta.getnRows()+","+mat_meta.getnColumns()+"/"+mat_meta.getDataTypeString());
	}
}

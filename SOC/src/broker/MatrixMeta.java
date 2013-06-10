package broker;

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
		
	
}

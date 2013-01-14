

public class MatrixMeta {

	private String id;
	private String path;
	private int nRows;
	private int nCols;
	
	public MatrixMeta()
	{
	
	}
	public MatrixMeta(String id, String p, int r, int c)
	{
		this.id = new String (id);
		path = new String (p);
		nRows = r;
		nCols = c;
	
	}
	
	
	public String getID()
	{
		return id;
	}
	
	public String getPath() 
	{
		return path;
	}
	public int getnRows()
	{
		return nRows;
	}
	public int getnCols()
	{
		return nCols;
	}
	
	@Override
	public String toString() {
		return "Matrix Meta [ID= " + id + ", path of matrix= " + path+ ", nrows= "+nRows+" ,nCols= "+nCols+" ]";
	}


}

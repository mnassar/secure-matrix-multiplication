package broker.workflow.translate.expression;

public class MatrixInfo {

	static public final short  ADDITIVE_SPLITTING=0;
	static public final short  HOMOMORPHIC=1;
	static public final short THREE_SERVER=2;
	
	private String id;
	private String url;
	private int nRows;
	private int nCols;
	private short protocol;
	private String[] actual_urls;
	
	
	public MatrixInfo()
	{
	
	}
	
	public MatrixInfo(MatrixInfo m)
	{
		id = m.getID();
		url = m.getPath();
		nRows = m.getnRows();
		nCols = m.getnCols();
		protocol = m.getProtocol();
		actual_urls = new String[m.getActual_urls().length];
		for(int i=0; i< m.getActual_urls().length; i++)
			actual_urls[i]= new String(m.getActual_urls()[i]);
	}
	
	public MatrixInfo(String id, String p, int r, int c, short proto, String[] act_urls)
	{
		this.id = new String (id);
		url = new String (p);
		nRows = r;
		nCols = c;
		protocol = proto;
		if(protocol== ADDITIVE_SPLITTING)
		{
			actual_urls= new String[4];
			for(int i=0; i<4; i++)
				actual_urls[i]= new String(act_urls[i]);
		}
		else if(protocol== THREE_SERVER)
		{
			actual_urls= new String[3];
			for(int i=0; i<3; i++)
				actual_urls[i]= new String(act_urls[i]);
		}
		/////////////////TODO: for other protocols
	
	}
	
	
	public String getID()
	{
		return id;
	}
	
	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() 
	{
		return url;
	}
	public int getnRows()
	{
		return nRows;
	}
	public int getnCols()
	{
		return nCols;
	}
	
	/**
	 * @return the protocol
	 */
	public short getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(short protocol) {
		this.protocol = protocol;
	}
	/**
	 * @return the actual_urls
	 */
	public String[] getActual_urls() {
		return actual_urls;
	}
	/**
	 * @param actual_urls the actual_urls to set
	 */
	public void setActual_urls(String[] actual_urls) {
		this.actual_urls = actual_urls;
	}
	@Override
	public String toString() {
		return "Matrix Meta [ID= " + id + ", url of matrix= " + url+ ", nrows= "+nRows+" ,nCols= "+nCols+" ]";
	}


}

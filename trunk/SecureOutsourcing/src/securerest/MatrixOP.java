package securerest;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
//Isn't that cool?
public class MatrixOP {

	 private String name;
	 private String pathA;
	 private String pathB;
	 private String callBack;
	
	public MatrixOP()
	{
	
	}
	public MatrixOP(String n, String A, String B, String C)
	{
		name = new String (n);
		pathA = new String(A);
		pathB = new String(B);
		callBack = new String (C);
	}
	
	
	public String getName()
	{
		return name;
	}
	
	public String getPathA()
	{
		return pathA;
	}
	public String getPathB()
	{
		return pathB;
	}
	
	public String getCallBack()
	{
		return callBack;
	}
	
	@Override
	public String toString() {
		return "Matrix Operation [name=" + name + ", path of 1st matrix=" + pathA + ", path of 2nd matrix=" + pathB + ", call back adress= "+callBack+ "]";
	}

}


package org.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

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
	public MatrixOP(String xml)
	{
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			 InputSource is = new InputSource();
			    is.setCharacterStream(new StringReader(xml));

			    org.w3c.dom.Document doc = db.parse(is);
				
			    NodeList nodes = doc.getElementsByTagName("matrixOP");

			    for (int i = 0; i < nodes.getLength(); i++) {
			      Element element = (Element) nodes.item(i);

			      NodeList name = element.getElementsByTagName("name");
			      Element line = (Element) name.item(0);
			      this.name=new String(((CharacterData)line.getFirstChild()).getData()); 
			      
			      NodeList firstPath = element.getElementsByTagName("pathA");
			      line = (Element) firstPath.item(0);
			      this.pathA=new String(((CharacterData)line.getFirstChild()).getData());
			      
			      NodeList secondPath = element.getElementsByTagName("pathB");
			      line = (Element) secondPath.item(0);
			      this.pathB=new String(((CharacterData)line.getFirstChild()).getData());
			      
			      NodeList callBackPath = element.getElementsByTagName("callBack");
			      line = (Element) callBackPath.item(0);
			      this.callBack=new String(((CharacterData)line.getFirstChild()).getData());
			    }
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
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
		return "<matrixOP><name>" + name + "</name><pathA>" + pathA + "</pathA><pathB>" + pathB + "</pathB><callBack>"+callBack+ "</callBack></matrixOP>";
	}

}


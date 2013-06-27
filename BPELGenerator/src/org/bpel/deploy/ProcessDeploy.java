package org.bpel.deploy;

import java.util.List; 
import java.util.Iterator; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.String;  
import java.io.InputStream; 
import org.apache.commons.codec.binary.Base64; 
import org.apache.commons.fileupload.servlet.ServletFileUpload; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory ;
import org.apache.commons.fileupload.* ;
import org.apache.axis2.client.Options ;
import org.apache.axis2.client.ServiceClient; 
import org.apache.axis2.addressing.EndpointReference; 
import org.apache.axiom.om.OMElement ;
import org.apache.axiom.om.OMFactory ;
import org.apache.axiom.om.OMAbstractFactory; 
import org.apache.axiom.om.OMText ;
import javax.xml.stream.XMLStreamReader; 
import javax.xml.stream.XMLInputFactory ;
import org.apache.axiom.om.impl.builder.StAXOMBuilder; 
import java.io.StringReader ;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory; 
import org.apache.commons.httpclient.protocol.Protocol ;
import org.apache.axiom.soap.SOAP11Constants ;
import org.apache.axiom.soap.SOAP12Constants ;
import org.apache.axis2.Constants ;
import org.apache.axis2.description.WSDL2Constants ;
import org.apache.axis2.util.JavaUtils ;
import org.apache.axis2.addressing.AddressingConstants; 
import org.apache.axis2.transport.http.HttpTransportProperties; 
import org.apache.axis2.transport.http.HTTPConstants ;
import org.apache.axis2.AxisFault ;
import org.apache.axis2.context.OperationContext; 
import org.apache.axis2.context.MessageContext ;
import org.apache.axiom.soap.SOAPEnvelope ;
import org.apache.axiom.om.OMNamespace;

public class ProcessDeploy {

	String process_Name;
	public String getProcess_Name() {
		return process_Name;
	}

	public void setProcess_Name(String process_Name) {
		this.process_Name = process_Name;
	}

	public String getDeploymentService_URL() {
		return deploymentService_URL;
	}

	public void setDeploymentService_URL(String deploymentService_URL) {
		this.deploymentService_URL = deploymentService_URL;
	}

	public String getZippedProcess_Path() {
		return zippedProcess_Path;
	}

	public void setZippedProcess_Path(String zippedProcess_Path) {
		this.zippedProcess_Path = zippedProcess_Path;
	}

	public String getBpelprocess_folder() {
		return bpelprocess_folder;
	}

	public void setBpelprocess_folder(String bpelprocess_folder) {
		this.bpelprocess_folder = bpelprocess_folder;
	}

	String deploymentService_URL;
	String zippedProcess_Path;
	String bpelprocess_folder;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Default values
		/*String bpelprocess_folder = "/home/farida/workspace2/WF_Process/bpelContent";
		String process_Name = "My_Process";
		String zipTo_Path= "/home/farida/Documents/ZippedProcess.zip";
		String deploymentService_URL ="http://localhost:8080/ode/processes/DeploymentService";
		*/
		
		String bpelprocess_folder = "/home/farida/Clean_workspace/BPELGenerator/BPEL";
		String process_Name = "WF_Process";
		String zipTo_Path= "/home/farida/Documents/WF_Process.zip";
		String deploymentService_URL ="http://10.160.2.27:8080/ode/processes/DeploymentService";
		
		//Read values from args []
		
		ProcessDeploy deployProc = new ProcessDeploy();
		deployProc.setBpelprocess_folder(bpelprocess_folder);
		deployProc.setProcess_Name(process_Name);
		deployProc.setZippedProcess_Path(zipTo_Path);
		deployProc.setDeploymentService_URL(deploymentService_URL);
		
		FolderZipper appZip = new FolderZipper(bpelprocess_folder,zipTo_Path);
    	appZip.generateFileList(new File(appZip.getSource_Folder()));
    	appZip.zipIt(appZip.getOutput_ZIP_Path());
    
    	deployProc.deploy();
			
	}
	
	public void deploy()
	{
		  if(zippedProcess_Path.endsWith(".zip")){
			  try {
				File zippedFile = new File(zippedProcess_Path);
				  
				  //FileItem item = (FileItem)zippedFile;
				  InputStream is = new FileInputStream(zippedFile);
				  long size = zippedFile.length();
				  byte[] bytes = new byte[(int)size];
				  int offset = 0;
				  int numRead = 0;
				  while (offset < bytes.length
				      && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				      offset += numRead;
				  }
				  if (offset < bytes.length) {
				      System.out.println("Overflow Error Occurred!");
				  }else{
				      if(!Base64.isArrayByteBase64(bytes)){
				          byte[] encodedBytes = Base64.encodeBase64(bytes);
				          String encodedString = new String(encodedBytes);
				          Options opts = new Options();
				          opts.setAction("http://www.apache.org/ode/deployapi/DeploymentPortType/deployRequest");
				          opts.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
				          opts.setProperty(Constants.Configuration.HTTP_METHOD,
				              Constants.Configuration.HTTP_METHOD_POST);
				          
				          opts.setTo(new EndpointReference(deploymentService_URL));

				          OMElement payload = null;
				          OMFactory omFactory = OMAbstractFactory.getOMFactory();
				          OMNamespace ns = omFactory.createOMNamespace("http://www.apache.org/ode/pmapi","p");
				          payload = omFactory.createOMElement("deploy", ns);
				          OMElement name = omFactory.createOMElement("name", ns);
				          OMElement packageCont = omFactory.createOMElement("package", ns);
				          OMElement zipEle = omFactory.createOMElement("zip", ns);
				          if(process_Name != null && encodedString != null){
				              OMText nameText = omFactory.createOMText(name, process_Name);
				              OMText packageText = omFactory.createOMText(zipEle, encodedString);
				              packageCont.addChild(zipEle);
				              payload.addChild(name);
				              payload.addChild(packageCont);

				              
				              try {
				            	//creating service client
				                  ServiceClient sc = new ServiceClient();
				                  sc.setOptions(opts);
				                  
				                  //invoke service
				                  OMElement responseMsg = sc.sendReceive(payload);
				                  String body = responseMsg.toString();
				                  if(body.indexOf("name") > 0){
				                	  System.out.println("Package deployed successfully!");
				                  }else{
				                	  System.out.println("Package deployement failed!");
				                  }
				              } catch (AxisFault axisFault ) {
				                  System.out.println(axisFault.getMessage());
				                  axisFault.printStackTrace();
				              }
				              
				          }else{
				        	  System.out.println("No package Name specified!");
				              //break;
				          }
				      }else{
				    	  System.out.println("TODO: Implement Base64 encoded string support!");
				      }
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

          }else{
        	  System.out.println("Wrong input format. Input file must be zip archive!");
          }

	}

}

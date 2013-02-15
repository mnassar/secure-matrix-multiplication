
/**
 * MatServSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
       package org.example.www.matserv;

import java.rmi.RemoteException;

import javax.xml.soap.SOAPFactory;

import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.ADBException;
import org.apache.mahout.math.hadoop.MatrixMultiplicationJob;
import org.bpelprocess.MatprocessServiceStub;
import org.bpelprocess.MatprocessServiceStub.OnCallback;

    /**
     *  MatServSkeleton java skeleton for the axisService
     */
    public class MatServSkeleton extends org.apache.axis2.client.Stub {
        
    	private static final String EPR = "http://localhost:8080/ode/processes/Matprocess";
    	private static final String namespace="http://bpelprocess.org/Matprocess";
    	  private static int counter = 0;

          private static synchronized java.lang.String getUniqueSuffix(){
              // reset the counter if it is greater than 99999
              if (counter > 99999){
                  counter = 0;
              }
              counter = counter + 1; 
              return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
          }
          private javax.xml.namespace.QName[] opNameArray = null;
          private boolean optimizeContent(javax.xml.namespace.QName opName) {
              

              if (opNameArray == null) {
                  return false;
              }
              for (int i = 0; i < opNameArray.length; i++) {
                  if (opName.equals(opNameArray[i])) {
                      return true;   
                  }
              }
              return false;
          }

            

/* methods to provide back word compatibility */


      
          private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, org.example.www.matserv.Compute param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
          throws org.apache.axis2.AxisFault{

               
                      try{

                              org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                              emptyEnvelope.getBody().addChild(param.getOMElement(org.example.www.matserv.Compute.MY_QNAME,factory));
                              return emptyEnvelope;
                          } catch(org.apache.axis2.databinding.ADBException e){
                              throw org.apache.axis2.AxisFault.makeFault(e);
                          }
                  

          }
  
        /**
         * Auto generated method signature
         * 
                                     * @param onFinish0 
             * @return onFinishResponse1 
         */
        
                 public org.example.www.matserv.OnFinishResponse onFinish
                  (
                  org.example.www.matserv.OnFinish onFinish0
                  )
            {
                //TODO : fill this with the necessary business logic
                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#onFinish");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param compute2 
             * @return  
         */
        
                 public void compute
                  (
                  org.example.www.matserv.Compute compute2
                  )
            {
                //TODO : fill this with the necessary business logic
                	 final org.example.www.matserv.Compute compute_request = compute2;
					 	 
                	 System.out.println("Input" + compute2.getIn());
                	 new Thread(new Runnable() {
              			public void run() {
              			String	[] jobcommand= {"--numRowsA", "10", "--numColsA" , "10","--numRowsB", "10","--numColsB", "10", "--inputPathA", "/home/farida/GlobalFS/OA/part-m-00000","--inputPathB", "/home/farida/GlobalFS/OB1/part-m-00000"};          
 							
                     	 System.out.println(jobcommand.toString());
 							 	 try {
 									MatrixMultiplicationJob.main(jobcommand);
 									//response.setOut("Message Received: " + operation + " , Operation Succeeded!!");
 	                                //return response;
 									
 								} catch (Exception e) {
 									// TODO Auto-generated catch block
 									e.printStackTrace();
 								}
 							 	MatprocessServiceStub ms;
								try {
									System.out.println("CALL BACK STARTED");
									ms = new MatprocessServiceStub();
									
									OnCallback callback = new OnCallback();
	 							 	callback.setInput("callback");
	 							 	ms.onCallback(callback);
	 							 	System.out.println("CALL BACK FINISHED");
	 							 	//ms._getServiceClient().sendRobust(callback.getOMElement(OnCallback.MY_QNAME,  org.apache.axiom.om.OMAbstractFactory.getOMFactory()));

								} catch (AxisFault e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
 							 
 							 	
 							 	 /*
 							 	 try
 							 	 {
 							 		org.apache.axis2.context.MessageContext _messageContext = null;	 		 
 					            	 org.apache.axis2.description.AxisService _service = new org.apache.axis2.description.AxisService("Matprocess" + getUniqueSuffix());
 					            	 org.apache.axis2.client.ServiceClient _serviceClient= 
 					            			 new org.apache.axis2.client.ServiceClient(null,_service);
 					                
 					            	 org.apache.axis2.description.AxisOperation _operation= 
 					            			 new org.apache.axis2.description.OutOnlyAxisOperation();
 					            	 _operation.setName(new javax.xml.namespace.QName(namespace, "onCallback"));
 					        	    _service.addOperation(_operation);
 					        	    
 					                _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
 					                        EPR));
 					                
 					               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operation.getName());
 					              _operationClient.getOptions().setAction("http://bpelprocess.org/Matprocess/onCallback");
 					              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
 		
 		//addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
 						              

 					              // create a message context
 					              _messageContext = new org.apache.axis2.context.MessageContext();

 					              

 					              // create SOAP envelope with that payload
 					              org.apache.axiom.soap.SOAPEnvelope env = null;
 					                    
 					                                                    
 					                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
 					                                                    		compute_request,
 					                                                    optimizeContent(new javax.xml.namespace.QName(namespace,
 					                                                    "onCallback")), new javax.xml.namespace.QName(namespace,
 					                                                    "onCallback"));
 					                                                
 					        //adding SOAP soap_headers
 					         _serviceClient.addHeadersToEnvelope(env);
 					        // set the message context with that soap envelope
 					        _messageContext.setEnvelope(env);

 					        // add the message contxt to the operation client
 					        _operationClient.addMessageContext(_messageContext);
 					        //execute the operation client
 					        _operationClient.execute(true);

 					         
 					              
 					                                   
 					         }catch(org.apache.axis2.AxisFault f){
 					        	 f.printStackTrace();
 					         }
 					         

 							 	 */
              			}
                	 }).start();
                
        }
     
    }
    
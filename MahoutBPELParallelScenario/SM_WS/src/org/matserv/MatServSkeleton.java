
/**
 * MatServSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package org.matserv;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.fileupload.util.Streams;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.web.WebHdfsFileSystem;
import org.apache.mahout.math.hadoop.MatrixMultiplicationJob;
import org.util.MatrixOP;

import sun.management.FileSystemImpl;

import bpelprocess.matrix.ProcessConnection;
import bpelprocess.matrix.ProcessConnection.Result;
import bpelprocess.matrix.WF_ProcessServiceStub;
import bpelprocess.matrix.WF_ProcessServiceStub.AddResult;
import bpelprocess.matrix.WF_ProcessServiceStub.MultResult1;
import bpelprocess.matrix.WF_ProcessServiceStub.MultResult2;
import bpelprocess.matrix.WF_ProcessServiceStub.MultResult3;
import bpelprocess.matrix.WF_ProcessServiceStub.MultResult4;
    /**
     *  MatServSkeleton java skeleton for the axisService
     */
    public class MatServSkeleton implements MatServSkeletonInterface{
        
    	
    	
        /**
         * Auto generated method signature
         * 
                                     * @param compute0 
             * @return  
         */
        
                 public void compute
                  (
                  org.example.www.matserv.Compute compute0
                  )
            {
                //TODO : fill this with the necessary business logic
                	 final org.example.www.matserv.Compute compute_request = compute0;
				 	 
                	 final MatrixOP operation = new MatrixOP(compute0.getOperation(), compute0.getMatA_ID(), compute0.getMatB_ID(), compute0.getCallback());

                	 System.out.println(operation.toString());
                	 new Thread(new Runnable() {
              			public void run() {
              	
              				 Configuration config = new Configuration();

                        	 config.set("fs.default.name","hdfs://127.0.0.1:54310");
                        	 config.set("mapred.job.tracker", "localhost:54311");
                        	 FileSystem dfs = null;
							try {
								dfs = FileSystem.get(config);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
     						String pathA = dfs.getWorkingDirectory()+"/HadoopInput/"+ operation.getPathA();
     						String pathB = dfs.getWorkingDirectory()+"/HadoopInput/"+ operation.getPathB();
     						String outPath = dfs.getWorkingDirectory()+"/HadoopOutput/"+ operation.getPathA()+operation.getPathB();
     						try {
								if(dfs.exists(new Path(outPath)))
								{
									dfs.delete(new Path(outPath), true);
								}
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
     						
                        	 try {
        						 String[] jobcommand= {"--numRowsA", "10", "--numColsA" , "10","--numRowsB", "10","--numColsB", "10", "--inputPathA", pathA,"--inputPathB", pathB, "--outputPath", outPath};          
        						 
        	                	 System.out.println(jobcommand.toString());
        	                	 
        	                	 if(operation.getName().equals("multiply"))
        	                	 {
        	                		 try {
        	                	 			MatrixMultiplicationJob.main(jobcommand);
        	                	 			
        	                	 		} catch (Exception e) {
        	                	 			// TODO Auto-generated catch block
        	                	 			e.printStackTrace();
        	                	 		}
        	                	 }
        	                	 else if(operation.getName().equals("add"))
        	                	 {
        	                		 try {
     	                	 			MatrixMultiplicationJob.main(jobcommand);
     	                	 			
     	                	 		} catch (Exception e) {
     	                	 			// TODO Auto-generated catch block
     	                	 			e.printStackTrace();
     	                	 		} 
        	                	 }
        	                	 else if (operation.getName().equals("copy"))
        	                	 {
        	                		 //COPY Service
        	                		 System.out.println("Copying file .....");
        	                		 FileSystem fs = FileSystem.get(config);
        	                		 
        	                	/*	 Configuration webconf = new Configuration();
        	                		 final String uri = WebHdfsFileSystem.SCHEME + "://" + "127.0.0.1:54310";
        	                		    // Turn on security
        	                		 webconf.set(CommonConfigurationKeys.HADOOP_SECURITY_AUTHENTICATION, "kerberos");
        	                		 WebHdfsFileSystem whdfs = (WebHdfsFileSystem) FileSystem.get(
        	                			        URI.create(uri), webconf);
        	                		 whdfs.setConf(webconf);
        	                		 String src= whdfs.getWorkingDirectory()+"/HadoopInput/"+operation.getPathA();
        	                		 String dest = "/home/farida/Documents/"+ operation.getPathB();
        	                		 whdfs.copyToLocalFile(new Path(src),new Path(dest));
        	                		 whdfs.close();
        	                		 */
        	                		 String dest = "/home/farida/Documents/"+ operation.getPathB();
        	                		 
        	                		 fs.copyToLocalFile(new Path(pathA), new Path(dest));
        	                		 System.out.println("File Copied!! To :" +dest);
        	                	 }
        	                		 
                        	 } catch (IOException e1) {
        						// TODO Auto-generated catch block
        						e1.printStackTrace();
        					}
                        	 
								try {
									System.out.println("CALL BACK STARTED");
									Result callback_res= new Result();
									callback_res.setIn(operation.getPathA()+operation.getPathB());
									callback_res.setJob_id(compute_request.getJob_id());
									callback_res.setOp_id(compute_request.getOp_id());
									callback_res.setCallback_op(compute_request.getCallback());
									Result.setNamespace("http://matrix.bpelprocess");
									callback_res.MY_QNAME = new javax.xml.namespace.QName(
											Result.getNamespace(),
							                callback_res.getCallback_op(),
							                "ns1");
									
					                

									
									ProcessConnection cb = new ProcessConnection(Result.getNamespace(), callback_res.getCallback_op());
									
									cb.sendCallback(callback_res);
									
									/*
									WF_ProcessServiceStub ms= new WF_ProcessServiceStub();
							
									switch (compute_request.getOp_id())
									{
										case 1:
											System.out.println("Multiply 1 CALL BACK");
												MultResult1 result = new MultResult1();
												result.setIn("A1B1");
												result.setJob_id(compute_request.getJob_id());
												result.setOp_id(compute_request.getOp_id());
												ms.multResult1(result);
												break;
										case 2:
											System.out.println("Multiply 2 CALL BACK");
												MultResult2 res2 = new MultResult2();
												res2.setIn("A2B2");
												res2.setJob_id(compute_request.getJob_id());
												res2.setOp_id(compute_request.getOp_id());
												ms.multResult2(res2);
												break;
										case 4:
											System.out.println("Multiply 3 CALL BACK");
												MultResult3 res3 = new MultResult3();
												res3.setIn("A1B2");
												res3.setJob_id(compute_request.getJob_id());
												res3.setOp_id(compute_request.getOp_id());
												ms.multResult3(res3);
												break;
										case 5:
											System.out.println("Multiply 4 CALL BACK");
												MultResult4 res4 = new MultResult4();
												res4.setIn("A2B1");
												res4.setJob_id(compute_request.getJob_id());
												res4.setOp_id(compute_request.getOp_id());
												ms.multResult4(res4);
												break;
										case 3:
											System.out.println("Add CALL BACK");
												AddResult add = new AddResult();
												add.setIn("C1C2");
												add.setJob_id(compute_request.getJob_id());
												System.out.println(compute_request.getJob_id());
												add.setOp_id(compute_request.getOp_id());
												ms.addResult(add);
												break;
									}
									*/
	 							 	System.out.println("CALL BACK FINISHED");
	 							 	//ms._getServiceClient().sendRobust(callback.getOMElement(OnCallback.MY_QNAME,  org.apache.axiom.om.OMAbstractFactory.getOMFactory()));

								} catch (AxisFault e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
 							 
 							 	
 							 	 
              			}
                	 }).start();
        }
     
    }
    
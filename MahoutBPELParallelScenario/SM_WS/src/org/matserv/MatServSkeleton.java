
/**
 * MatServSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package org.matserv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

import org.apache.axis2.AxisFault;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.web.WebHdfsFileSystem;
import org.apache.mahout.math.hadoop.MM_Job;
import org.apache.mahout.math.hadoop.MatrixMultiplicationJob;
import org.apache.mahout.utils.SequenceFileDumper;
import org.hsqldb.Tokenizer;
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
                        	 config.set("mapred.job.tracker", "http://127.0.0.1:54311");
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
        						 String runtime_comm ="--numRowsA  10  --numColsA 10 --numRowsB  10 --numColsB  10  --inputPathA "+ pathA +" --inputPathB "+ pathB + " --outputPath "+ outPath ;
        	                	 System.out.println(jobcommand.toString());
        	                	 
        	                	 if(operation.getName().equals("multiply"))
        	                	 {
        	                		 try {
        	                	//		   MM_Job mm= new MM_Job();
        	                			 //mm.createMatrixMultiplyJobConf(conf, aPath, bPath, outPath, outCardinality)
        	                		//	   mm.start(jobcommand);
        	                	 			//MatrixMultiplicationJob.main(jobcommand);
        	                			 System.out.println("/home/farida/Programs/Mahout/mahout-distribution-0.7/bin/mahout matrixmult " + runtime_comm);
        	                			 Process ls_proc = Runtime.getRuntime().exec("/home/farida/Programs/Mahout/mahout-distribution-0.7/bin/mahout matrixmult " + runtime_comm);
        	                			 int exit_value = ls_proc.waitFor();
        	                			 if(exit_value == 0 )
        	                				 System.out.println("Job has finished!!");
        	                			 ls_proc.destroy();
        	                	 		} catch (Exception e) {
        	                	 			// TODO Auto-generated catch block
        	                	 			e.printStackTrace();
        	                	 		}
        	                	 }
        	                	 else if(operation.getName().equals("add"))
        	                	 {
        	                		 try {
     	                	 			//MatrixMultiplicationJob.main(jobcommand);
     	                	 			BufferedReader br1 = null;
     	                	 			BufferedReader br2 = null;
     	                	 			BufferedReader br3 = null;
     	                	 			BufferedReader br4 = null;
     	                	 			try {
     	                	 	 
     	                	 				String sCurrentLine1, sCurrentLine2, sCurrentLine3, sCurrentLine4;
     	                	 				String f1 = "/home/farida/Documents/"+ operation.getPathA();
     	                	 				String f2 = "/home/farida/Documents/C2";
     	                	 				String f3 = "/home/farida/Documents/"+ operation.getPathB();
     	                	 				String f4 = "/home/farida/Documents/C4";
     	                	 				
     	                	 				String result_File = "/home/farida/Documents/C1C2C3C4";
     	                	 				File file = new File(result_File);
     	                	 				 
     	                	 				// if file doesnt exists, then create it
     	                	 				if (!file.exists()) {
     	                	 					file.createNewFile();
     	                	 				}
     	                	 	 
     	                	 				FileWriter fw = new FileWriter(file.getAbsoluteFile());
     	                	 				BufferedWriter bw = new BufferedWriter(fw);
     	                	 				
     	                	 				br1 = new BufferedReader(new FileReader(f1));
     	                	 				br2 = new BufferedReader(new FileReader(f2));
     	                	 				br3 = new BufferedReader(new FileReader(f3));
     	                	 				br4 = new BufferedReader(new FileReader(f4));
     	                	 				br1.readLine();	br1.readLine();
     	                	 				br2.readLine();	br2.readLine();
     	                	 				br3.readLine();	br3.readLine();
     	                	 				br4.readLine();	br4.readLine();
     	                	 				
     	                	 			        
     	                	 					for(int i=0; i<10; i++)
     	                	 					{
     	                	 						sCurrentLine1 = new String(br1.readLine());
         	                	 					sCurrentLine2 =  new String(br2.readLine());
         	                	 					sCurrentLine3 =  new String(br3.readLine());
         	                	 					sCurrentLine4 =  new String(br4.readLine());
         	                	 					
         	                	 					StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine1, ":");
         	                	 					StringTokenizer tokenzier2 = new StringTokenizer(sCurrentLine2, ":");
         	                	 					StringTokenizer tokenzier3 = new StringTokenizer(sCurrentLine3, ":");
         	                	 					StringTokenizer tokenzier4 = new StringTokenizer(sCurrentLine4, ":");
         	       				
     	                	 						tokenzier1.nextToken(); tokenzier1.nextToken();tokenzier1.nextToken();tokenzier1.nextToken();
     	                	 					    tokenzier2.nextToken(); tokenzier2.nextToken();tokenzier2.nextToken();tokenzier2.nextToken();
     	                	 					    tokenzier3.nextToken(); tokenzier3.nextToken();tokenzier3.nextToken();tokenzier3.nextToken();
     	                	 					    tokenzier4.nextToken(); tokenzier4.nextToken();tokenzier4.nextToken();tokenzier4.nextToken();
     	                	 					
     	                	 					    for ( int j=0; j<10; j++)
     	                	 					    {   String token_string= tokenzier1.nextToken();
     	                	 					    	int comma_indx =  token_string.indexOf(",") -1;
     	                	 					    	comma_indx = comma_indx==-2? token_string.indexOf("}") -1 : comma_indx;
     	                	 					    	Double v1= Double.valueOf(token_string.substring(0, comma_indx));
     	                	 					    	//System.out.println("value of mat elem =" + v1.toString());
     	                	 					    	token_string= tokenzier2.nextToken();
     	                	 					    	comma_indx =  token_string.indexOf(",")-1;
     	                	 					    	comma_indx = comma_indx==-2? token_string.indexOf("}") -1 : comma_indx;
     	                	 					    	Double v2= Double.valueOf(token_string.substring(0, comma_indx));
     	                	 					    	token_string= tokenzier3.nextToken();
     	                	 					    	comma_indx =  token_string.indexOf(",")-1;
     	                	 					    	comma_indx = comma_indx==-2? token_string.indexOf("}") -1 : comma_indx;
     	                	 					    	Double v3= Double.valueOf(token_string.substring(0, comma_indx));
     	                	 					    	token_string= tokenzier4.nextToken();
     	                	 					    	comma_indx =  token_string.indexOf(",")-1;
     	                	 					    	comma_indx = comma_indx==-2? token_string.indexOf("}") -1 : comma_indx;
     	                	 					    	Double v4= Double.valueOf(token_string.substring(0, comma_indx));
     	                	 					    	
     	                	 					    	
     	                	 					    	Double mat_elem = v1 + v2 + v3 + v4;
     	                	 					    	bw.write(mat_elem.toString());
     	                	 					    	if(j<9)
     	                	 					    		bw.write(" , ");
     	        	                	 				
     	                	 					    }
     	                	 					    bw.write("\n");
     	                	 					}
     	                	 					
     	                	 					
     	                	 					bw.close();
     	                	 					//** read sequence files//
     	                	 					/*
     	                	 					 * Configuration config = new Configuration();
								Path path = new Path(PATH_TO_YOUR_FILE);
									SequenceFile.Reader reader = new SequenceFile.Reader(FileSystem.get(config), path, config);
								WritableComparable key = (WritableComparable) reader.getKeyClass().newInstance();
Writable value = (Writable) reader.getValueClass().newInstance();
while (reader.next(key, value))
  // perform some operating
reader.close();
     	                	 					 */
     	                	 				
     	                	 	 
     	                	 			} catch (IOException e) {
     	                	 				e.printStackTrace();
     	                	 			} finally {
     	                	 				try {
     	                	 					if (br1 != null)br1.close();
     	                	 					if (br2 != null)br2.close();
     	                	 					if (br3 != null)br3.close();
     	                	 					if (br4 != null)br4.close();
     	                	 				} catch (IOException ex) {
     	                	 					ex.printStackTrace();
     	                	 				}
     	                	 			}
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
        	                		// fs.createNewFile((new Path(pathA+"/csv")));
        	                		 pathA = dfs.getWorkingDirectory()+"/HadoopOutput/"+ operation.getPathA();
        	                		 
        	     								 
        	                		 String dest = "/home/farida/Documents/"+ operation.getPathB();
        	                		       	                		 
        	                		 String[] args= {"--input", pathA+ "/part-00000","--output",dest };
        	                		 //SequenceFileDumper.main(args);
        	                		 runtime_comm = "/home/farida/Programs/Mahout/mahout-distribution-0.7/bin/mahout seqdumper --input "+ pathA+ "/part-00000  --output "+ dest ;
        	                		 System.out.println( runtime_comm);
    	                			 Process ls_proc = Runtime.getRuntime().exec(runtime_comm);
    	                			 int exit_value = ls_proc.waitFor();
    	                			 if(exit_value == 0 )
    	                				 System.out.println("File Copied!! To :" +dest);
    	                			 ls_proc.destroy();
        	                		 //fs.copyToLocalFile(new Path(pathA+"/csv"), new Path(dest));
        	                		 //FileUtil.copy(fs,new Path(pathA),fs, new Path(pathB),false, config);
        	                		 
        	                	 }
        	                		 
                        	 } catch (IOException e1) {
        						// TODO Auto-generated catch block
        						e1.printStackTrace();
        					} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
    
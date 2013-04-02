
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URI;
import java.rmi.RemoteException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis2.AxisFault;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.web.WebHdfsFileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.mahout.math.hadoop.MM_Job;
import org.apache.mahout.math.hadoop.MatrixMultiplicationJob;
import org.apache.mahout.utils.SequenceFileDumper;
import org.hsqldb.Tokenizer;
import org.util.MatrixOP;

import com.jcraft.jsch.JSch;
import com.wonderant.ssh.Jscp;
import com.wonderant.ssh.SecureContext;

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
              			
              				File server_file = new File("server.txt");
                       	 
       						
       						String server_ip ="10.160.2.27";
							String hdfs_port="54310";
       						String jobtracker_port="54311";
       						
       						if(server_file.exists())
       						{
       							BufferedReader br = null;
       						
							try {
								br = new BufferedReader(new FileReader(server_file));
							} catch (FileNotFoundException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
       						try {
								server_ip = new String(br.readLine());
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								System.out.println("Couldn't read the server ip!!");
								e3.printStackTrace();
							}
       						try {
								hdfs_port = new String(br.readLine());
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								System.out.println("Couldn't read the hdfs port!!");
								e3.printStackTrace();
							}
       						
							try {
								jobtracker_port = new String(br.readLine());
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								System.out.println("Couldn't read the jobtracker port!!");
								e3.printStackTrace();
							}
       						}
                       	 
       					
                        	 config.set("fs.default.name","hdfs://"+server_ip+":"+hdfs_port);
                        	 config.set("mapred.job.tracker", "http://"+server_ip+":"+jobtracker_port);
                        	 FileSystem dfs = null;
							
								try {
									dfs = FileSystem.get(config);
								} catch (IOException e3) {
									// TODO Auto-generated catch block
									e3.printStackTrace();
								}
								final String pathA = "/HadoopInput/"+ operation.getPathA();
	     						final String pathB = "/HadoopInput/"+ operation.getPathB();
	     						final String outPath = operation.getPathA()+operation.getPathB();
	     						try {
									if(dfs.exists(new Path(outPath)))
									{
										dfs.delete(new Path(outPath), true);
									}
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
	     						
////
     						
                        	 try {
        			        	 
        	                	 if(operation.getName().equals("multiply"))
        	                	 {
        	                		 try {
        	                	//		   MM_Job mm= new MM_Job();
        	                			 //mm.createMatrixMultiplyJobConf(conf, aPath, bPath, outPath, outCardinality)
        	                		//	   mm.start(jobcommand);
        	                	 			//MatrixMultiplicationJob.main(jobcommand);
        	                	
        	                			 //System.out.println("Path A: "+ operation.getPathA());
        	                			 UserGroupInformation ugi = UserGroupInformation.createProxyUser("hadoop", UserGroupInformation.getLoginUser());
        	        	                 ugi.doAs(new PrivilegedExceptionAction<Void>() {
        	        	                	               public Void run() throws Exception {
        	        	                	       
        	        	                	       int start = operation.getPathA().indexOf("_")+1;
        	                	            	   Pattern patt = Pattern.compile("\\D|_");
        	                	            	   Matcher match = patt.matcher(operation.getPathA().substring(start));
        	                	            	   boolean found = match.find();
        	                	            	   int end = found==false ? operation.getPathA().length() :  match.end()+1;        	                	            	  
        	                	            	   String mat_size = operation.getPathA().substring(start,end);
        	                	           // 	   System.out.println("Start: "+start);
        	                	            //	   System.out.println("End: "+ end);
        	              						 String[] jobcommand= {"--numRowsA", mat_size, "--numColsA" , mat_size,"--numRowsB", mat_size,"--numColsB", mat_size, "--inputPathA", pathA,"--inputPathB", pathB, "--outputPath", outPath};          
        	              						 String runtime_comm ="--numRowsA "+ mat_size+" --numColsA " + mat_size+" --numRowsB "+ mat_size+ " --numColsB "+ mat_size + " --inputPathA "+ pathA +" --inputPathB "+ pathB + " --outputPath "+ outPath ;
        	              	                	 System.out.println(jobcommand.toString());
        	              	                	 
        	            	                	 System.out.println("/home/hadoop/Mahout/mahout-distribution-0.7/bin/mahout matrixmult " + runtime_comm);    
        	                	            	   Process ls_proc = Runtime.getRuntime().exec("/home/hadoop/Mahout/mahout-distribution-0.7/bin/mahout matrixmult " + runtime_comm);
                	                			 int exit_value = ls_proc.waitFor();
                	                			 if(exit_value == 0 )
                	                				 System.out.println("Job has finished!!");
                	                			 ls_proc.destroy();
                	                			 return null;
                	                             
        	        	                	   }
        	        	                });
               
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
     	                	 	 
     	                	 				String sCurrentLine1=null, sCurrentLine2=null, sCurrentLine3=null, sCurrentLine4= null;
     	                	 				String f1 = "/home/hadoop/Documents/C_1_"+ operation.getPathA()+operation.getPathB();
     	                	 				String f2 = "/home/hadoop/Documents/C_2_"+ operation.getPathA()+operation.getPathB();
     	                	 				String f3 = "/home/hadoop/Documents/C_3_"+ operation.getPathA()+operation.getPathB();
     	                	 				String f4 = "/home/hadoop/Documents/C_4_"+ operation.getPathA()+operation.getPathB();
     	                	 				
     	                	 				String result_File = "/home/hadoop/Documents/"+operation.getPathA()+operation.getPathB();
     	                	 		 	   int start = operation.getPathA().indexOf("_")+1;
	                	            	   Pattern patt = Pattern.compile("\\D|_");
	                	            	   Matcher match = patt.matcher(operation.getPathA().substring(start));
	                	            	   boolean found = match.find();
	                	            	   int end = found==false ? operation.getPathA().length() :  match.end()+1;        	                	            	  
	                	            	   String mat_size = operation.getPathA().substring(start,end);
	                	           
	                	            	   System.out.println("Matrix size: " +mat_size);
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
     	                	 				
     	                	 			        
     	                	 				String key1= "0";
 	                	 					String key2= "0";
 	                	 					String key3= "0";
 	                	 					String key4= "0";
 	                	 					boolean zeros_row1 = false;
 	                	 					boolean zeros_row2 = false;
 	                	 					boolean zeros_row3 = false;
 	                	 					boolean zeros_row4 = false;
     	                	 					for(int i=0; i<Integer.parseInt(mat_size); i++)
     	                	 					{
     	                	 						if(!zeros_row1 ) sCurrentLine1 = new String(br1.readLine());
     	                	 						if(!zeros_row2) sCurrentLine2 =  new String(br2.readLine());
     	                	 						if(!zeros_row3) sCurrentLine3 =  new String(br3.readLine());
     	                	 						if(!zeros_row4) sCurrentLine4 =  new String(br4.readLine());
         	                	 					
         	                	 					StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine1, ":");
         	                	 					StringTokenizer tokenzier2 = new StringTokenizer(sCurrentLine2, ":");
         	                	 					StringTokenizer tokenzier3 = new StringTokenizer(sCurrentLine3, ":");
         	                	 					StringTokenizer tokenzier4 = new StringTokenizer(sCurrentLine4, ":");
         	       				/*
     	                	 						tokenzier1.nextToken(); tokenzier1.nextToken();tokenzier1.nextToken();tokenzier1.nextToken();
     	                	 					    tokenzier2.nextToken(); tokenzier2.nextToken();tokenzier2.nextToken();tokenzier2.nextToken();
     	                	 					    tokenzier3.nextToken(); tokenzier3.nextToken();tokenzier3.nextToken();tokenzier3.nextToken();
     	                	 					    tokenzier4.nextToken(); tokenzier4.nextToken();tokenzier4.nextToken();tokenzier4.nextToken();
     	                	 					
     	                	 					    for ( int j=0; j<Integer.parseInt(mat_size); j++)
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
     	                	 					    */
         	                	 		
         	                	 					
         	                	 					
         	                	 					String key_word=tokenzier1.nextToken(); 
         	                	 					if(key_word.equals("Key")) { key1 = tokenzier1.nextToken().substring(1).trim();tokenzier1.nextToken();}
         	                	 					
         	                	 					key_word=tokenzier2.nextToken(); 
         	                	 					if(key_word.equals("Key")) { key2 = tokenzier2.nextToken().substring(1).trim();tokenzier2.nextToken();}
         	                	 					
         	                	 					key_word=tokenzier3.nextToken(); 
         	                	 					if(key_word.equals("Key")) { key3 = tokenzier3.nextToken().substring(1).trim();tokenzier3.nextToken();}
         	                	 					
         	                	 					key_word=tokenzier4.nextToken(); 
         	                	 					if(key_word.equals("Key")) { key4 = tokenzier4.nextToken().substring(1).trim();tokenzier4.nextToken();}
         	                	 					
         	                	 					HashMap<Integer,Double>  row1 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
         	                	 					HashMap<Integer,Double>  row2 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
         	                	 					HashMap<Integer,Double>  row3 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
         	                	 					HashMap<Integer,Double>  row4 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
         	               				
         	                	 					String indx1 = null; Integer col_ind1=0; 
         	                	 					String indx2 = null; Integer col_ind2=0; 
         	                	 					String indx3 = null; Integer col_ind3=0; 
         	                	 					String indx4 = null; Integer col_ind4=0; 
         	                	 					if (Integer.parseInt(key1)== i)
         	                	 					{
         	                	 						indx1=  tokenzier1.nextToken();
         	                	 						col_ind1= Integer.parseInt(indx1.substring(2).trim());
         	                	 						zeros_row1 = false;
         	                	 					}
         	                	 					else
         	                	 					{
         	                	 						zeros_row1 = true;
         	                	 						for (int j= 0; j< Integer.parseInt(mat_size);j++) row1.put(j,0.0);
         	                	 					}
         	                	 					if (Integer.parseInt(key2)== i)
         	                	 					{
         	                	 						indx2=  tokenzier2.nextToken();
         	                	 						col_ind2= Integer.parseInt(indx2.substring(2).trim());
         	                	 						zeros_row2 = false;
         	                	 					}
         	                	 					else
         	                	 					{
         	                	 						zeros_row2 = true;
         	                	 						for (int j= 0; j< Integer.parseInt(mat_size);j++) row2.put(j,0.0);
         	                	 					}
         	                	 					if (Integer.parseInt(key3)== i)
         	                	 					{
         	                	 						indx3=  tokenzier3.nextToken();
         	                	 						col_ind3= Integer.parseInt(indx3.substring(2).trim());
         	                	 						zeros_row3 = false;
         	                	 						
         	                	 					}   
         	                	 					else
         	                	 					{
         	                	 						zeros_row3 = true;
         	                	 						for (int j= 0; j< Integer.parseInt(mat_size);j++) row3.put(j,0.0);
         	                	 					}
         	                	 					if (Integer.parseInt(key4)== i)
         	                	 					{
         	                	 						indx4=  tokenzier4.nextToken();
         	                	 						col_ind4= Integer.parseInt(indx4.substring(2).trim());
         	                	 						zeros_row4 = false;
         	                	 					}
         	                	 					else
         	                	 					{
         	                	 						zeros_row4 = true;
         	                	 						for (int j= 0; j< Integer.parseInt(mat_size);j++) row4.put(j,0.0);
         	                	 					}
         	               					    	
         	                	 					//System.out.println("In Row :" +i +" Key1: "+key1 + " Key2: "+key2 +" Key3: "+key3 +" Key4: "+key4);
         	               					    
         	               				    	while( tokenzier1.hasMoreTokens())
         	               					    { 
         	               				    		 String token_string1= tokenzier1.nextToken(",");
         	               				    		 String token_string2= tokenzier2.nextToken(",");
         	               				    		 String token_string3= tokenzier3.nextToken(",");
         	               				    		 String token_string4= tokenzier4.nextToken(",");
         	               				    		 
         	               				    		 Double val = new  Double(0.0);
         	               				    		 if(token_string1.contains("}")&& token_string1!=null)
         	               				    		 {
         	               				    			 val= Double.valueOf(token_string1.substring(1, token_string1.length()-1).trim());
         	               				    			if(!zeros_row1)
         	               				    				row1.put(col_ind1,val);
         	               				    			 if(token_string2.contains("}")&& token_string2!=null)
         	               				    			 {
         	               				    				 val= Double.valueOf(token_string2.substring(1, token_string2.length()-1).trim());
         	               				    				if(!zeros_row2)
         	               				    					row2.put(col_ind2,val);
         	               				    			 }
         	               				    			if(token_string3.contains("}")&& token_string3!=null)
        	               				    			 {
         	               				    				 val= Double.valueOf(token_string3.substring(1, token_string3.length()-1).trim());
        	               				    				 if(!zeros_row3)
        	               				    					row3.put(col_ind3,val);
        	               				    			 }
         	               				    			if(token_string4.contains("}")&& token_string4!=null)
         	               				    			{
         	               				    				val= Double.valueOf(token_string4.substring(1, token_string4.length()-1).trim());
         	               				    				if(!zeros_row4)
         	               				    					row4.put(col_ind4,val);
         	               				    			}
         	               				    			 break;
         	               				    		 }
         	               				    		 else
         	               				    		 {
         	               				    			 
         	               				    			if(!zeros_row1)
         	               				    			{
         	               				    				val= Double.valueOf(token_string1.substring(1).trim());
         	               				    				row1.put(col_ind1,val);
         	               				    			}
         	               				    			
         	               				    			if(!zeros_row2)
         	               				    			{
         	               				    				val= Double.valueOf(token_string2.substring(1).trim());
         	               				    				row2.put(col_ind2,val);
         	               				    			}
         	               				    			
         	               				    			if(!zeros_row3)
         	               				    			{
         	               				    				val= Double.valueOf(token_string3.substring(1).trim());
         	               				    				row3.put(col_ind3,val);
         	               				    			}
         	               				    			
         	               				    			if(!zeros_row4) 
         	               				    			{
         	               				    				val= Double.valueOf(token_string4.substring(1).trim());
         	               				    				row4.put(col_ind4,val);
         	               				    			}
         	               				    		 }
         	               					    
         	               					    	 indx1=  tokenzier1.nextToken(":");
         	               					    	 indx2=  tokenzier2.nextToken(":");
         	               					    	 indx3=  tokenzier3.nextToken(":");
         	               					    	 indx4=  tokenzier4.nextToken(":");
         	               							 
         	               					    	 col_ind1= Integer.parseInt(indx1.substring(1).trim());
         	               					    	 col_ind2= Integer.parseInt(indx2.substring(1).trim());
         	               					    	 col_ind3= Integer.parseInt(indx3.substring(1).trim());
         	               					    	 col_ind4= Integer.parseInt(indx4.substring(1).trim());
         	               							 
         	               								 
         	               					    }
         	               	
         	               					for(int j=0; j< Integer.parseInt(mat_size); j++)
         	               					{
         	               						if(row1.get(j)!=null && row2.get(j)!=null && row3.get(j)!=null && row4.get(j)!=null) 
         	               						{
         	               							Double added_val = row1.get(j) + row2.get(j) + row3.get(j) + row4.get(j);
         	               								bw.write(added_val.toString());
         	               						}
         	               						else 
         	               							bw.write("0.0");
         	               						if(j== Integer.parseInt(mat_size)-1 ) {bw.write("\n"); break;}
         	               						bw.write(",");
         	               						}
         	               				    	
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
        	                		// pathA = dfs.getWorkingDirectory()+"/Output/"+ operation.getPathA();
        	                		 String path_A = operation.getPathA();
        	     								 
        	                		 String dest = "/home/hadoop/Documents/"+ operation.getPathB();
        	                		       	                		 
        	                		 String[] args= {"--input", path_A,"--output",dest };
        	                		 
        	                		
        	                		 
        	                		 //SequenceFileDumper.main(args);
        	                		 String runtime_comm = "/home/hadoop/Mahout/mahout-distribution-0.7/bin/mahout seqdumper --input "+ path_A+ "  --output "+ dest ;
        	                		 System.out.println( runtime_comm);
    	                			 Process ls_proc = Runtime.getRuntime().exec(runtime_comm);
    	                			 int exit_value = ls_proc.waitFor();
    	                			 if(exit_value == 0 )
    	                				 System.out.println("File Copied!! To :" +dest);
    	                			 
    	                			 ls_proc.destroy();
    	                			 
    	                			 SecureContext context = new SecureContext("hadoop", "10.160.2.27");
    	                			 // set optional security configurations.
    	                			 context.setTrustAllHosts(true);
    	                			    	                			//context.setPrivateKeyFile(new File("private/key"));

    	                			    	                			// Console requires JDK 1.7
    	                			    	                			// System.out.println("enter password:");
    	                			 context.setPassword("broker_1212");

    	                			 Jscp.exec(context, dest, dest , null);

    	                		/*	 
    	                			 String runtime_comm2 = "scp -r  "+ dest+ " hadoop@10.160.2.27:Documents" ;
    	                			 Process rm_scp_proc = Runtime.getRuntime().exec(runtime_comm2);
    	                			 Reader stdOut = new InputStreamReader(rm_scp_proc.getInputStream (), "US-ASCII");
    	                			 OutputStream stdIn = rm_scp_proc.getOutputStream ();

    	                			    char[] passRequest = new char[128];//Choose it big enough for scp password request and all that goes before it
    	                			    int len = 0;
    	                			    while (true)
    	                			    {
    	                			        len += stdOut.read (passRequest, len, passRequest.length - len);
    	                			        if (new String (passRequest, 0, len).contains ("password:")) break;
    	                			    }

    	                			    System.out.println ("Password requested");
    	                			    stdIn.write ("1212\n".getBytes ("US-ASCII"));
    	                			    stdIn.flush ();
    	                			    exit_value = rm_scp_proc.waitFor();
    	                			    if(exit_value == 0 )
    	                			    	System.out.println("File Copied!! To :" +dest);
    	                			 
    	                			    rm_scp_proc.destroy();
        	                		*/
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
/*
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
                        		 
                        		 String mat_size = operation.getPathA().substring(operation.getPathA().indexOf("_")+1);
        						 String[] jobcommand= {"--numRowsA", mat_size, "--numColsA" , mat_size,"--numRowsB", mat_size,"--numColsB", mat_size, "--inputPathA", pathA,"--inputPathB", pathB, "--outputPath", outPath};          
        						 String runtime_comm ="--numRowsA "+ mat_size+" --numColsA " + mat_size+" --numRowsB "+ mat_size+ " --numColsB "+ mat_size + " --inputPathA "+ pathA +" --inputPathB "+ pathB + " --outputPath "+ outPath ;
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
     	                	 				String f1 = "/home/hadoop/Documents/C_1_"+ operation.getPathA()+operation.getPathB();
     	                	 				String f2 = "/home/hadoop/Documents/C_2_"+ operation.getPathA()+operation.getPathB();
     	                	 				String f3 = "/home/hadoop/Documents/C_3_"+ operation.getPathA()+operation.getPathB();
     	                	 				String f4 = "/home/hadoop/Documents/C_4_"+ operation.getPathA()+operation.getPathB();
     	                	 				
     	                	 				String result_File = "/home/farida/Documents/"+operation.getPathA()+operation.getPathB();
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
//     	                	 					//** read sequence files//
//     	                	 					/*
//     	                	 					 * Configuration config = new Configuration();
//								Path path = new Path(PATH_TO_YOUR_FILE);
//									SequenceFile.Reader reader = new SequenceFile.Reader(FileSystem.get(config), path, config);
//								WritableComparable key = (WritableComparable) reader.getKeyClass().newInstance();
//Writable value = (Writable) reader.getValueClass().newInstance();
//while (reader.next(key, value))
//  // perform some operating
//reader.close();
//     	                	 					 
     	                	 				
     	                	 	 
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
        	                		 
//        	                	/*	 Configuration webconf = new Configuration();
//        	                		 final String uri = WebHdfsFileSystem.SCHEME + "://" + "127.0.0.1:54310";
//        	                		    // Turn on security
//        	                		 webconf.set(CommonConfigurationKeys.HADOOP_SECURITY_AUTHENTICATION, "kerberos");
//        	                		 WebHdfsFileSystem whdfs = (WebHdfsFileSystem) FileSystem.get(
//        	                			        URI.create(uri), webconf);
//        	                		 whdfs.setConf(webconf);
//        	                		 String src= whdfs.getWorkingDirectory()+"/HadoopInput/"+operation.getPathA();
//        	                		 String dest = "/home/farida/Documents/"+ operation.getPathB();
//        	                		 whdfs.copyToLocalFile(new Path(src),new Path(dest));
//        	                		 whdfs.close();
//        	                		 
        	                		// fs.createNewFile((new Path(pathA+"/csv")));
        	                		 pathA = dfs.getWorkingDirectory()+"/HadoopOutput/"+ operation.getPathA();
        	                		 
        	     								 
        	                		 String dest = "/home/farida/Documents/"+ operation.getPathB();
        	                		       	                		 
        	                		 String[] args= {"--input", pathA,"--output",dest };
        	                		 //SequenceFileDumper.main(args);
        	                		 runtime_comm = "/home/farida/Programs/Mahout/mahout-distribution-0.7/bin/mahout seqdumper --input "+ pathA+ "  --output "+ dest ;
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
									
//									/*
//									WF_ProcessServiceStub ms= new WF_ProcessServiceStub();
//							
//									switch (compute_request.getOp_id())
//									{
//										case 1:
//											System.out.println("Multiply 1 CALL BACK");
//												MultResult1 result = new MultResult1();
//												result.setIn("A1B1");
//												result.setJob_id(compute_request.getJob_id());
//												result.setOp_id(compute_request.getOp_id());
//												ms.multResult1(result);
//												break;
//										case 2:
//											System.out.println("Multiply 2 CALL BACK");
//												MultResult2 res2 = new MultResult2();
//												res2.setIn("A2B2");
//												res2.setJob_id(compute_request.getJob_id());
//												res2.setOp_id(compute_request.getOp_id());
//												ms.multResult2(res2);
//												break;
//										case 4:
//											System.out.println("Multiply 3 CALL BACK");
//												MultResult3 res3 = new MultResult3();
//												res3.setIn("A1B2");
//												res3.setJob_id(compute_request.getJob_id());
//												res3.setOp_id(compute_request.getOp_id());
//												ms.multResult3(res3);
//												break;
//										case 5:
//											System.out.println("Multiply 4 CALL BACK");
//												MultResult4 res4 = new MultResult4();
//												res4.setIn("A2B1");
//												res4.setJob_id(compute_request.getJob_id());
//												res4.setOp_id(compute_request.getOp_id());
//												ms.multResult4(res4);
//												break;
//										case 3:
//											System.out.println("Add CALL BACK");
//												AddResult add = new AddResult();
//												add.setIn("C1C2");
//												add.setJob_id(compute_request.getJob_id());
//												System.out.println(compute_request.getJob_id());
//												add.setOp_id(compute_request.getOp_id());
//												ms.addResult(add);
//												break;
//									}
//									
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
     */
    }
    
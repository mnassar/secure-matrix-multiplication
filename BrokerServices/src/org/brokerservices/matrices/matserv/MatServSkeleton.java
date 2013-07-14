
/**
 * MatServSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package org.brokerservices.matrices.matserv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;



import matrix.splitters.AdditiveSplitter;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.time.StopWatch;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.mahout.math.VectorWritable;
import org.brokerservices.www.matserv.Resplit;

import java.rmi.RemoteException;
import java.security.PrivilegedExceptionAction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bpelprocess.additivesplitting.AdditiveSplittingServiceStub;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_0;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_1;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Split_type0;
import broker.BrokerSOCResource;
import broker.Location;
import broker.Log;
import broker.MatrixMeta;
import broker.MetadataStoreConnection;
import broker.ResourceMeta;
import broker.ResourceMetaAdapter;
import broker.SOCConfiguration;
    /**
     *  MatServSkeleton java skeleton for the axisService
     */
    public class MatServSkeleton{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param add 
             * @return  
         */
        
                 public void add
                  (
                  org.brokerservices.www.matserv.Add add
                  )
                 {
                	 final SOCConfiguration soc_conf = new SOCConfiguration();
                	 final Log logfile = new Log(SOCConfiguration.LOG_DIRECTORY);
             		//TODO : fill this with the necessary business logic
             		final org.brokerservices.www.matserv.Add add_request = add;
             		System.out.println(add.getJob_id()+":Add splits \n"+add.getAdd_list()[0].getCloudURL()+":"+add.getAdd_list()[0].getSplitName()+" ,\n "
             				+add.getAdd_list()[1].getCloudURL()+":"+add.getAdd_list()[1].getSplitName()+" ,\n "
             				+add.getAdd_list()[2].getCloudURL()+":"+add.getAdd_list()[2].getSplitName()+" ,\n "
             				+add.getAdd_list()[3].getCloudURL()+":"+add.getAdd_list()[3].getSplitName()+" ,\n ");
             		
             		logfile.write(add.getJob_id()+":Add splits \n"+add.getAdd_list()[0].getCloudURL()+":"+add.getAdd_list()[0].getSplitName()+" ,\n "
             				+add.getAdd_list()[1].getCloudURL()+":"+add.getAdd_list()[1].getSplitName()+" ,\n "
             				+add.getAdd_list()[2].getCloudURL()+":"+add.getAdd_list()[2].getSplitName()+" ,\n "
             				+add.getAdd_list()[3].getCloudURL()+":"+add.getAdd_list()[3].getSplitName()+" ,\n ");
             		
             		final String matA_ID = add.getMatA_ID();
             		final String matB_ID = add.getMatB_ID();
             		final String jobID = add.getJob_id();


             		new Thread(new Runnable() {
             			public void run() {

             				

             				BrokerSOCResource matA =null;
             				BrokerSOCResource matB =null;
             				try {
             					MetadataStoreConnection conn;
             					try {
             						System.out.println(SOCConfiguration.METADATA_STORE_URL);
             						conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
             						Gson gson  = new Gson();
                         			GsonBuilder builder = new GsonBuilder();
            					    builder.registerTypeAdapter(ResourceMeta.class   , new ResourceMetaAdapter());
            					    gson = builder.create();
            					
                         			matA = gson.fromJson(conn.getSOCResource(matA_ID),BrokerSOCResource.class);
                         			matB = gson.fromJson(conn.getSOCResource(matB_ID),BrokerSOCResource.class);
                         			
             						conn.close();
             					} catch (Exception e) {
             						// TODO Auto-generated catch block
             						e.printStackTrace();
             					}


             					final int  nRows = ((MatrixMeta)matA.getResource_meta()).getnRows();
             					final int  nCols = ((MatrixMeta)matA.getResource_meta()).getnColumns();


             					StopWatch stopwatch = new StopWatch();
             					long start_time = System.nanoTime();
             					stopwatch.start();
             					//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
             					//File log_file = new File(SOCConfiguration.LOG_DIRECTORY+"/"+dateFormat.format(new java.util.Date())+".log");
             					//final FileWriter fw2 = new FileWriter(log_file.getAbsoluteFile(),true);

             					try {

             						Configuration config = new Configuration();
             						Location loc1 = new Location(add_request.getAdd_list()[0].getCloudURL());
             						String server_ip1 = loc1.getIP();
             						String hdfs_port= loc1.getPort_no();
             						String jobtracker_port="54311";

             						config.set("fs.default.name","hdfs://"+server_ip1+":"+hdfs_port);
             						config.set("mapred.job.tracker", "http://"+server_ip1+":"+jobtracker_port);
             						UserGroupInformation.setConfiguration(config);
             						final Configuration conf = config;
             						UserGroupInformation ugi = UserGroupInformation.createRemoteUser("farida");// UserGroupInformation.getLoginUser());
             						ugi.doAs(new PrivilegedExceptionAction<Void>() {
             							public Void run() throws Exception {

             								Configuration conf2 = new Configuration();
             								Location loc2 = new Location(add_request.getAdd_list()[1].getCloudURL());
             								String server_ip2 = loc2.getIP();
             								String hdfs_port= loc2.getPort_no();
             								String jobtracker_port="54311";

             								Configuration conf3 = new Configuration();
             								Location loc3 = new Location(add_request.getAdd_list()[2].getCloudURL());
             								String server_ip3 = loc3.getIP();

             								Configuration conf4 = new Configuration();
             								Location loc4 = new Location(add_request.getAdd_list()[3].getCloudURL());
             								String server_ip4 = loc4.getIP();

             								conf2.set("fs.default.name","hdfs://"+server_ip2+":"+hdfs_port);
             								conf2.set("mapred.job.tracker", "http://"+server_ip2+":"+jobtracker_port);

             								conf3.set("fs.default.name","hdfs://"+server_ip3+":"+hdfs_port);
             								conf3.set("mapred.job.tracker", "http://"+server_ip3+":"+jobtracker_port);

             								conf4.set("fs.default.name","hdfs://"+server_ip4+":"+hdfs_port);
             								conf4.set("mapred.job.tracker", "http://"+server_ip4+":"+jobtracker_port);

             								FileSystem dfs = null;
             								FileSystem dfs2 = null;
             								FileSystem dfs3 = null;
             								FileSystem dfs4 = null;
             								try {
             									dfs = FileSystem.get(conf);
             									dfs2 = FileSystem.get(conf2);
             									dfs3 = FileSystem.get(conf3);
             									dfs4 = FileSystem.get(conf4);
             								} catch (IOException e3) {
             									// TODO Auto-generated catch block
             									e3.printStackTrace();
             								}
             								final String pathA = "/"+add_request.getAdd_list()[0].getSplitName()+"/part-m-00000";
             								final String pathB = "/"+add_request.getAdd_list()[1].getSplitName()+"/part-m-00000";
             								final String pathC = "/"+add_request.getAdd_list()[2].getSplitName()+"/part-m-00000";
             								final String pathD = "/"+add_request.getAdd_list()[3].getSplitName()+"/part-m-00000";



             								try {

             									SequenceFile.Reader reader = null;   
             									SequenceFile.Reader reader2 = null;
             									SequenceFile.Reader reader3 = null;
             									SequenceFile.Reader reader4 = null;
             									try {             
             										reader = new SequenceFile.Reader(dfs, new Path(dfs.getHomeDirectory()+pathA), conf);
             										reader2 = new SequenceFile.Reader(dfs2, new Path(dfs2.getHomeDirectory()+pathB), conf2);
             										reader3 = new SequenceFile.Reader(dfs3, new Path(dfs3.getHomeDirectory()+pathC), conf3);
             										reader4 = new SequenceFile.Reader(dfs4, new Path(dfs4.getHomeDirectory()+pathD), conf4);


             										IntWritable key_seq1 = (IntWritable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);    
             										VectorWritable value = (VectorWritable) ReflectionUtils.newInstance(reader.getValueClass(), conf);

             										IntWritable key_seq2 = (IntWritable) ReflectionUtils.newInstance(reader2.getKeyClass(), conf2);    
             										VectorWritable value2 = (VectorWritable) ReflectionUtils.newInstance(reader2.getValueClass(), conf2);

             										IntWritable key_seq3 = (IntWritable) ReflectionUtils.newInstance(reader3.getKeyClass(), conf3);    
             										VectorWritable value3 = (VectorWritable) ReflectionUtils.newInstance(reader3.getValueClass(), conf3);

             										IntWritable key_seq4 = (IntWritable) ReflectionUtils.newInstance(reader4.getKeyClass(), conf4);    
             										VectorWritable value4 = (VectorWritable) ReflectionUtils.newInstance(reader4.getValueClass(), conf4);


             										String result_File = SOCConfiguration.BROKER_STORAGE_PATH+"/"+matA_ID+"_"+matB_ID;
             										logfile.write("Addition of " +dfs.getHomeDirectory()+pathA + " , " +dfs2.getHomeDirectory()+pathB +" , "+dfs3.getHomeDirectory()+ pathC +" , "+dfs4.getHomeDirectory()+pathD+" ........");

             										File file = new File(result_File);

             										// if file doesnt exists, then create it
             										if (!file.exists()) {
             											file.createNewFile();
             										}

             										FileWriter fw = new FileWriter(file.getAbsoluteFile());
             										BufferedWriter bw = new BufferedWriter(fw);

             										String sCurrentLine1=null, sCurrentLine2=null, sCurrentLine3=null, sCurrentLine4= null;

             										while (reader.next(key_seq1, value) && reader2.next(key_seq2, value2) && reader3.next(key_seq3, value3) && reader4.next(key_seq4, value4)) {                
             											//		
             											HashMap<Integer,Double>  result_row = new HashMap<Integer, Double>(nRows);

             											String key1= "0";
             											String key2= "0";
             											String key3= "0";
             											String key4= "0";

             											boolean zeros_row1 = false;
             											boolean zeros_row2 = false;
             											boolean zeros_row3 = false;
             											boolean zeros_row4 = false;

             											//HashMap<Integer,Double>  row1 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
             											//	         	                	 					HashMap<Integer,Double>  row2 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
             											//	         	                	 					HashMap<Integer,Double>  row3 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
             											//	         	                	 					HashMap<Integer,Double>  row4 = new HashMap<Integer, Double>(Integer.parseInt(mat_size));
             											//	         	               				
             											String indx1 = null; Integer col_ind1=0; 
             											String indx2 = null; Integer col_ind2=0; 
             											String indx3 = null; Integer col_ind3=0; 
             											String indx4 = null; Integer col_ind4=0; 

             											//        	     	                	 						
             											sCurrentLine1 =  new String(value.toString());
             											sCurrentLine2 =    new String(value2.toString());
             											sCurrentLine3 =    new String(value3.toString());
             											sCurrentLine4 =    new String(value4.toString());

             											if(sCurrentLine1.equals("{}")) zeros_row1=true;
             											if(sCurrentLine1.equals("{}")) zeros_row2=true;
             											if(sCurrentLine1.equals("{}")) zeros_row3=true;
             											if(sCurrentLine1.equals("{}")) zeros_row4=true;

             											StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine1, ",");
             											StringTokenizer tokenzier2 = new StringTokenizer(sCurrentLine2, ",");
             											StringTokenizer tokenzier3 = new StringTokenizer(sCurrentLine3, ",");
             											StringTokenizer tokenzier4 = new StringTokenizer(sCurrentLine4, ",");
             											//    	         	       				 					

             											int j = nCols-1;

             											while( tokenzier1.hasMoreTokens()| tokenzier2.hasMoreTokens() |tokenzier3.hasMoreTokens() |tokenzier4.hasMoreTokens() )
             											{ 
             												//        	         	               				    		 String token_string1= tokenzier1.nextToken(",");
             												//        	         	               				    		 String token_string2= tokenzier2.nextToken(",");
             												//        	         	               				    		 String token_string3= tokenzier3.nextToken(",");
             												//        	         	               				    		 String token_string4= tokenzier4.nextToken(",");
             												//        	         	               				    	
             												Double val1 = new  Double(0.0), val2= new  Double(0.0), val3= new  Double(0.0), val4 = new  Double(0.0);
             												String token_string1= null;//tokenzier1.nextToken(",");
             												String token_string2= null; //tokenzier2.nextToken(",");
             												String token_string3= null; // tokenzier3.nextToken(",");
             												String token_string4= null; //tokenzier4.nextToken(",");

             												if(!zeros_row1)
             												{
             													token_string1= tokenzier1.nextToken(",");
             													if(token_string1!=null)
             													{
             														int colon_indx = token_string1.indexOf(":");
             														//System.out.println("Token: "+token_string1);
             														//System.out.println("colon_indx: "+colon_indx);
             														String indx = null;
             														if(token_string1.contains("{"))
             															indx = token_string1.substring(1,colon_indx);
             														else
             															indx = token_string1.substring(0,colon_indx);
             														//System.out.println("key_indx: "+indx);
             														if(Integer.parseInt(indx)== j)
             														{
             															if(token_string1.contains("}"))
             																val1= Double.valueOf(token_string1.substring(colon_indx+1, token_string1.length()-1).trim());
             															else
             																val1= Double.valueOf(token_string1.substring(colon_indx+1).trim());
             														}
             													}	
             												}
             												if(!zeros_row2)
             												{
             													token_string2= tokenzier2.nextToken(",");
             													if(token_string2!=null)
             													{
             														int colon_indx = token_string2.indexOf(":");
             														String indx = null;
             														if(token_string2.contains("{"))
             															indx = token_string2.substring(1,colon_indx);
             														else
             															indx = token_string2.substring(0,colon_indx);
             														if(Integer.parseInt(indx)== j)
             														{
             															if(token_string2.contains("}"))
             																val2= Double.valueOf(token_string2.substring(colon_indx+1, token_string2.length()-1).trim());
             															else
             																val2= Double.valueOf(token_string2.substring(colon_indx+1).trim());
             														}
             													}	
             												}

             												if(!zeros_row3)
             												{
             													token_string3= tokenzier3.nextToken(",");
             													if(token_string3!=null)
             													{
             														int colon_indx = token_string3.indexOf(":");
             														String indx = null;
             														if(token_string3.contains("{"))
             															indx = token_string3.substring(1,colon_indx);
             														else
             															indx = token_string3.substring(0,colon_indx);
             														if(Integer.parseInt(indx)== j)
             														{
             															if(token_string3.contains("}"))
             																val3= Double.valueOf(token_string3.substring(colon_indx+1, token_string3.length()-1).trim());
             															else
             																val3= Double.valueOf(token_string3.substring(colon_indx+1).trim());
             														}
             													}	
             												}

             												if(!zeros_row4)
             												{
             													token_string4= tokenzier4.nextToken(",");
             													if(token_string4!=null)
             													{
             														int colon_indx = token_string4.indexOf(":");
             														String indx = null;
             														if(token_string4.contains("{"))
             															indx = token_string4.substring(1,colon_indx);
             														else
             															indx = token_string4.substring(0,colon_indx);
             														if(Integer.parseInt(indx)== j)
             														{
             															if(token_string4.contains("}"))
             																val4= Double.valueOf(token_string4.substring(colon_indx+1, token_string4.length()-1).trim());
             															else
             																val4= Double.valueOf(token_string4.substring(colon_indx+1).trim());
             														}
             													}	
             												}


             												result_row.put(j, val1 + val2 + val3 + val4);
             												//fw2.append("\n col "+j+": " +val1 +val2+val3+val4+"\n");
             												j--; 				

             											}

             											for(int i=0; i< nCols; i++)
             											{
             												bw.write(result_row.get(i).toString());
             												if(i== nCols-1 ) {bw.write("\n"); break;}
             												bw.write(",");
             											}

             										}
             										bw.close();  


             									}
             									catch (Exception e) {           
             										e.printStackTrace();        
             									}
             									finally {            
             										IOUtils.closeStream(reader);      
             									}

             									//LocalFi	leSystem fs = new LocalFileSystem();
             									//System.out.println(dfs.getHomeDirectory());
             									//dfs.copyToLocalFile(new Path("hdfs://10.160.2.24:54310/user/hadoop"+pathA), new Path("file:///home/farida/a100b100.txt"));

             									dfs.close();
             									dfs2.close();
             									dfs3.close();
             									dfs4.close();

             								}
             								catch(Exception e)
             								{
             									e.printStackTrace();
             								}
             								return null;
             							}});


             						stopwatch.stop();
             						System.out.println("Time for addition :"+stopwatch.getTime());
             						logfile.write("\n Time taken for addition in job number: "+ jobID+" with matrix size ("+nRows+" , "+ nCols+")" 
             								+":  " + stopwatch.getTime() + " in nanotime :"+(System.nanoTime()-start_time) +"\n");
             						//fw2.close();




             					} catch (IOException e) {
             						e.printStackTrace();
             					} 


             				} catch (Exception e) {
             					// TODO Auto-generated catch block
             					e.printStackTrace();
             				}

             				try {
             					System.out.println("CALL BACK STARTED");
             					Callback_1 callback = new Callback_1();

             					callback.setJobID(jobID);
             					callback.setOp_ID(add_request.getOp_id());
             					callback.setSub_jobID(jobID);
             					callback.setCloud_url(SOCConfiguration.BROKER_URL);
             					callback.setSplit_name(matA_ID+"_"+matB_ID);


             					AdditiveSplittingServiceStub add_callback = new AdditiveSplittingServiceStub();

             					add_callback.callBack_Operation1(callback);

             					logfile.write("Add callback is done!");
             					
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


             	/**
             	 * Auto generated method signature
             	 * 
             	 * @param resplit 
             	 * @return  
             	 */

             	public void resplit
             	(
             			org.brokerservices.www.matserv.Resplit resplit
             			)
             	{
             		//TODO : fill this with the necessary business logic
             		
             		final Resplit resplit_request= resplit;
             		final String matrixA_ID = resplit_request.getMatA_ID();
             		final String matrixB_ID = resplit_request.getMatB_ID();

             		BrokerSOCResource matA = null;
             		BrokerSOCResource matB = null;
             		SOCConfiguration soc_conf = new SOCConfiguration();
             		final Log logfile = new Log(SOCConfiguration.LOG_DIRECTORY);
             		
             		MetadataStoreConnection conn;
             		try {
             			conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
             			Gson gson  = new Gson();
             			GsonBuilder builder = new GsonBuilder();
					    builder.registerTypeAdapter(ResourceMeta.class   , new ResourceMetaAdapter());
					    gson = builder.create();
					
             			matA = gson.fromJson(conn.getSOCResource(matrixA_ID),BrokerSOCResource.class);
             			matB = gson.fromJson(conn.getSOCResource(matrixB_ID),BrokerSOCResource.class);
             			conn.close();
             		} catch (Exception e) {
             			// TODO Auto-generated catch block
             			e.printStackTrace();
             		}

             		
             		ArrayList<Location> clouds = soc_conf.getClouds();
             		ArrayList<String> splitA_names = new ArrayList<String>();
             		ArrayList<String> splitB_names = new ArrayList<String>();

             		boolean comb1_found=false, comb2_found=false,comb3_found=false,comb4_found=false;

             		for(int i=0; i<clouds.size(); i++)
             		{
             			Configuration conf = new Configuration();
             			Location loc = new Location(clouds.get(i).getUrl());
             			String server_ip = loc.getIP();

             			conf.set("fs.default.name","hdfs://"+server_ip+":"+loc.getPort_no());
             			conf.set("mapred.job.tracker", "http://"+server_ip+":54311");

             			FileSystem dfs = null;

             			try {
             				dfs = FileSystem.get(conf);
             				String matA_filenamepart =matrixA_ID+"_"+((MatrixMeta)matA.getResource_meta()).getnRows()+"_"+((MatrixMeta)matA.getResource_meta()).getnColumns()+"_";
             				String matB_filenamepart =matrixB_ID+"_"+((MatrixMeta)matB.getResource_meta()).getnRows()+"_"+((MatrixMeta)matB.getResource_meta()).getnColumns()+"_";

             				if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"1"))
             						&& dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"1")))
             				{
             					comb1_found= true;
             					splitA_names.add(matA_filenamepart+"1");
             					splitB_names.add(matB_filenamepart+"1");
             				}
             				else if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"2"))
             						&& dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"2")))
             				{
             					comb2_found= true;
             					splitA_names.add(matA_filenamepart+"2");
             					splitB_names.add(matB_filenamepart+"2");
             				}
             				else if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"1"))
             						&& dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"2")))
             				{
             					comb3_found= true;
             					splitA_names.add(matA_filenamepart+"1");
             					splitB_names.add(matB_filenamepart+"2");
             				}
             				else if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"2"))
             						&& dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"1")))
             				{
             					comb4_found= true;
             					splitA_names.add(matA_filenamepart+"2");
             					splitB_names.add(matB_filenamepart+"1");
             				}
             			}
             			catch (Exception e)
             			{
             				e.printStackTrace();
             			}
             		}



             		if(!(comb1_found && comb2_found && comb3_found && comb4_found))  ///NO resplitting needed
             		{

             			splitA_names.clear();
             			splitB_names.clear();

             			for(int i=0; i<clouds.size(); i++)
             			{
             				Configuration conf = new Configuration();
             				Location loc = new Location(clouds.get(i).getUrl());
             				String server_ip = loc.getIP();

             				conf.set("fs.default.name","hdfs://"+server_ip+":"+loc.getPort_no());
             				conf.set("mapred.job.tracker", "http://"+server_ip+":54311");

             				FileSystem dfs = null;

             				try {
             					dfs = FileSystem.get(conf);
             					String matA_filenamepart =matrixA_ID+"_"+((MatrixMeta)matA.getResource_meta()).getnRows()+"_"+((MatrixMeta)matA.getResource_meta()).getnColumns()+"_";
             					String matB_filenamepart =matrixB_ID+"_"+((MatrixMeta)matB.getResource_meta()).getnRows()+"_"+((MatrixMeta)matB.getResource_meta()).getnColumns()+"_";

             					if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"1")))
             					{
             						dfs.delete(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"1"),true);
             					}
             					if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"2")))
             					{
             						dfs.delete(new Path(dfs.getWorkingDirectory()+"/"+matA_filenamepart+"2"),true);
             					}
             					if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"1")))
             					{
             						dfs.delete(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"1"),true);
             					}
             					if(dfs.exists(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"2")))
             					{
             						dfs.delete(new Path(dfs.getWorkingDirectory()+"/"+matB_filenamepart+"2"),true);
             					}

             				}
             				catch(Exception e)
             				{
             					e.printStackTrace();
             				}
             			}
             			try {

             				AdditiveSplitter splitter = new AdditiveSplitter(matA);
             				splitter.Split(clouds.get(0),clouds.get(2), clouds.get(1), clouds.get(3));
             				
             				matA.setLocations(clouds);
             				String matA_filenamepart= matrixA_ID+"_"+((MatrixMeta)matA.getResource_meta()).getnRows()+"_"+((MatrixMeta)matA.getResource_meta()).getnColumns()+"_";
             				splitA_names.add(matA_filenamepart+"1");
             				splitA_names.add(matA_filenamepart+"2");
             				splitA_names.add(matA_filenamepart+"1");
             				splitA_names.add(matA_filenamepart+"2");

             				splitter = new AdditiveSplitter(matB);
             				splitter.Split(clouds.get(0),clouds.get(3), clouds.get(1) , clouds.get(2));
             				matB.setLocations(clouds);

             				String matB_filenamepart= matrixB_ID+"_"+((MatrixMeta)matB.getResource_meta()).getnRows()+"_"+((MatrixMeta)matB.getResource_meta()).getnColumns()+"_";
             				splitB_names.add(matB_filenamepart+"1");
             				splitB_names.add(matB_filenamepart+"2");
             				splitB_names.add(matB_filenamepart+"2");
             				splitB_names.add(matB_filenamepart+"1");

             				conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
             				conn.updateSOCResource(matA);
             				conn.updateSOCResource(matB);

             				conn.close();
             			} catch (Exception e) {
             				// TODO Auto-generated catch block
             				e.printStackTrace();
             			}

             		}

             		logfile.write("Resplitting check is done!");
             		//form the callback with the stored urls & split names found

             		Callback_0 resplit_callback = new Callback_0();
             		resplit_callback.setSub_jobID(resplit_request.getJob_id());
             		resplit_callback.setOp_ID(resplit_request.getOp_id());
             		Split_type0[] splits = new Split_type0[4];

             		for(int j=0; j<4; j++)
             		{
             			splits[j].setCloud_url(clouds.get(j).getUrl());
             			splits[j].setSplitA_name(splitA_names.get(j));
             			splits[j].setSplitB_name(splitB_names.get(j));
             		}
             		resplit_callback.setSplit(splits);
             		AdditiveSplittingServiceStub resplitting_callback;
             		try {
             			resplitting_callback = new AdditiveSplittingServiceStub();
             			resplitting_callback.resplit_callBack(resplit_callback);
             		} catch (AxisFault e) {
             			// TODO Auto-generated catch block
             			e.printStackTrace();
             		} catch (RemoteException e) {
             			// TODO Auto-generated catch block
             			e.printStackTrace();
             		}
             		logfile.write("Resplitting callback is done!");
             		/*
             		 * <sequence>
                                     <element name="jobID" type="string" />
                                     <element name="sub_jobID" type="string" />
                                     <element name="op_ID" type="string" />
                                     <element name="split" minOccurs="1" maxOccurs="4">
                                     <complexType>
                                     	<sequence>
                                     	<element name="cloud_url" type="string"/>
                                     	<element name="splitA_name" type="string"/>
                                     	<element name="splitB_name" type="string"/>
                                     	</sequence>
                                     	</complexType>
                                     </element>
                                 </sequence>
             		 */
             	}
 
    }
    
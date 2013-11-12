
/**
 * MatServSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package org.cloudservices.matserv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.Severity;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.time.StopWatch;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.cloudservices.www.matserv.Multiply;

import bpelprocess.additivesplitting.AdditiveSplittingServiceStub;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_1;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_2;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_3;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_4;
import bpelprocess.additivesplitting.AdditiveSplittingServiceStub.Callback_5;
    /**
     *  MatServSkeleton java skeleton for the axisService
     */
    public class MatServSkeleton implements MatServSkeletonInterface{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param multiply0 
             * @return  
         */
        
                 public void multiply
                  (
                  org.cloudservices.www.matserv.Multiply multiply
                  )
                 {
             		final Multiply mult_request = multiply;


             		final String splitA = multiply.getMatA_ID();//of the form 2434_10_10_1
             		final String splitB = multiply.getMatB_ID();

             		new Thread(new Runnable() {
             			public void run() {

             				
             				String MAHOUT_HOME= System.getenv("MAHOUT_HOME");
             				File soc_conf = new File("/etc/soc/cloud.conf");
             				
             				String LOG_DIRECTORY = new String();
             				String CLOUD_USER = new String();
             				String CLOUD_IP = new String();
             				
             				if(MAHOUT_HOME ==null)
             				{
             					BufferedReader br;
             					try
             					{
             						br = new BufferedReader(new FileReader(soc_conf));
             						String sCurrentLine;
             						
             						while ((sCurrentLine = br.readLine()) != null) {
             							StringTokenizer tokenizer = new StringTokenizer(sCurrentLine);
             							String variable = tokenizer.nextToken("="); variable = variable.trim();
             							String path = tokenizer.nextToken(); path  = path.trim();
             						
             							if(variable.equals("MAHOUT_HOME")) MAHOUT_HOME = new String(path.substring(1,path.length()-1));
             							if(variable.equals("LOG_DIRECTORY")) LOG_DIRECTORY = new String(path.substring(1,path.length()-1));
             							if(variable.equals("CLOUD_USER")) CLOUD_USER = new String(path.substring(1,path.length()-1));
             							if(variable.equals("CLOUD_IP")) CLOUD_IP = new String(path.substring(1,path.length()-1));
             						}
             					}
             					catch(Exception e)
             					{
             						e.printStackTrace();
             					}
             				}
             				Log logfile = new Log(LOG_DIRECTORY);
             				logfile.write("Received job for multiplication of "+mult_request.getMatA_ID() + " and "+ mult_request.getMatB_ID());
             				Configuration config = new Configuration();
             				InetAddress ip =null;//= InetAddress.getByName("127.0.0.1") ;//= InetAddress.getLocalHost();
             				/*
             				try {

             					ip = InetAddress.getLocalHost();
             					System.out.println("Current IP address : " + ip.getHostAddress());

             				} catch (UnknownHostException e) {

             					e.printStackTrace();

             				}
             				*/
             				String server_ip =CLOUD_IP;
             				/*
             				Enumeration<NetworkInterface> en;
								try {
									en = NetworkInterface.getNetworkInterfaces();
									while(en.hasMoreElements()){
	                				    NetworkInterface ni=(NetworkInterface) en.nextElement();
	                				    Enumeration<InetAddress> ee = ni.getInetAddresses();
	                				    while(ee.hasMoreElements()) {
	                				        InetAddress ia= (InetAddress) ee.nextElement();
	                				        server_ip = ia.getHostAddress();
	                				        ip = ia;
	                				        System.out.println(ia.getHostAddress());
	                				       System.out.println(ia.getHostName());
	                				    }
	                				}
								} catch (SocketException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
             			
             				
             				if(ip !=null) 
             					server_ip=ip.getHostAddress();//"127.0.0.1";
             				else 
								
             				if (server_ip==null)	server_ip="127.0.0.1";
             				*/
             				String hdfs_port="54310";
             				String jobtracker_port="54311";

             				config.set("fs.default.name","hdfs://"+server_ip+":"+hdfs_port);
             				config.set("mapred.job.tracker", "http://"+server_ip+":"+jobtracker_port);
             				logfile.write("server_ip:"+server_ip);
             				FileSystem dfs = null;

             				try {
             					dfs = FileSystem.get(config);
             				} catch (IOException e3) {
             					// TODO Auto-generated catch block
             					e3.printStackTrace();
             				}
             				final String pathA = splitA;
             				final String pathB = splitB;
             				String matA_ID= splitA.substring(0, splitA.indexOf("_"));
             				String matB_ID= splitB.substring(0, splitB.indexOf("_"));

             				final String outPath = matA_ID+"_"+matB_ID+"_"+mult_request.getOp_id();
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
             					int start = splitA.indexOf("_")+1;
             					Pattern patt = Pattern.compile("\\D|_");
             					Matcher match = patt.matcher(splitA.substring(start));
             					boolean found = match.find();
             					int end = found == false ? splitA.length() : match.end();        	                	            	  
             					String mat_size = splitA.substring(start,start+end-1);
             					
             					// 	   System.out.println("Start: "+start);
             					//	   System.out.println("End: "+ end);
             					String[] jobcommand= {"--numRowsA", mat_size, "--numColsA" , mat_size,"--numRowsB", mat_size,"--numColsB", mat_size, "--inputPathA", pathA,"--inputPathB", pathB, "--outputPath", outPath};          
             					String runtime_comm ="--numRowsA "+ mat_size+" --numColsA " + mat_size+" --numRowsB "+ mat_size+ " --numColsB "+ mat_size + " --inputPathA "+ pathA +" --inputPathB "+ pathB + " --outputPath "+ outPath ;
             					System.out.println(jobcommand.toString());

             					System.out.println(MAHOUT_HOME+"/bin/mahout matrixmult " + runtime_comm);    
             					StopWatch stopwatch = new StopWatch();
            					long start_time = System.nanoTime();
            					stopwatch.start();
    
            					logfile.write("JOB "+mult_request.getJob_id()+"/"+mult_request.getOp_id() +" : "+server_ip +" :  "+MAHOUT_HOME+"/bin/mahout matrixmult " + runtime_comm);
            					Process ls_proc = Runtime.getRuntime().exec(MAHOUT_HOME+"/bin/mahout matrixmult " + runtime_comm);
             					
            					
            					logfile.write("Started job "+mult_request.getJob_id()+ " task for multiplication of "+mult_request.getMatA_ID() + " and "+ mult_request.getMatB_ID());
             					int exit_value = ls_proc.waitFor();
             					stopwatch.stop();
             					if(exit_value == 0 )
             					{
             						System.out.println("Job has finished!!");
             						logfile.write("Job "+mult_request.getJob_id()+ " finished with total runtime = " + stopwatch.getTime());
             					}
             					else
             					{
             						System.out.println("Job has failed!!");
             						logfile.write("Job "+mult_request.getJob_id()+ " has failed!!" );
             					}	
             					ls_proc.destroy();


             				} catch (Exception e) {
             					// TODO Auto-generated catch block
             					e.printStackTrace();
             				}

/*
             				try {
             					System.out.println("CALL BACK STARTED");
             					Callback_1 callback_res= new Callback_1();
             					
             					callback_res.setSub_jobID(mult_request.getJob_id());
             					callback_res.setOp_ID(mult_request.getOp_id());
             					callback_res.setCloud_url(ip.getHostAddress());
             					callback_res.setSplit_name(outPath);
             					callback_res.setJobID(mult_request.getJob_id());

             					AdditiveSplittingServiceStub bpelProcess = new AdditiveSplittingServiceStub();

             					bpelProcess.callBack_Operation1(callback_res);

             					System.out.println("CALL BACK FINISHED");
             					logfile.write("Call back sent to the calling process of job "+mult_request.getJob_id()+ " !!");

             				} catch (AxisFault e) {
             					// TODO Auto-generated catch block
             					e.printStackTrace();
             				} catch (RemoteException e) {
             					// TODO Auto-generated catch block
             					e.printStackTrace();
             				}
*/
             				
             				try {
            					
            					System.out.println("CALL BACK STARTED");
            					if(mult_request.getOp_id().equals("1"))
            					{
            						Callback_1 callback_res= new Callback_1();

            						callback_res.setSub_jobID(mult_request.getJob_id());
            						callback_res.setOp_ID(mult_request.getOp_id());
            						callback_res.setCloud_url(CLOUD_IP);
            						callback_res.setSplit_name(outPath);
            						callback_res.setJobID(mult_request.getJob_id());

            						AdditiveSplittingServiceStub bpelProcess = new AdditiveSplittingServiceStub();

            						bpelProcess.callBack_Operation1(callback_res);

            					}
            					else if(mult_request.getOp_id().equals("2"))
            					{
            						Callback_2 callback_res= new Callback_2();

            						callback_res.setSub_jobID(mult_request.getJob_id());
            						callback_res.setOp_ID(mult_request.getOp_id());
            						callback_res.setCloud_url(CLOUD_IP);
            						callback_res.setSplit_name(outPath);
            						callback_res.setJobID(mult_request.getJob_id());

            						AdditiveSplittingServiceStub bpelProcess = new AdditiveSplittingServiceStub();

            						bpelProcess.callBack_Operation2(callback_res);

            					}
            					else if(mult_request.getOp_id().equals("3"))
            					{
            						Callback_3 callback_res= new Callback_3();

            						callback_res.setSub_jobID(mult_request.getJob_id());
            						callback_res.setOp_ID(mult_request.getOp_id());
            						callback_res.setCloud_url(CLOUD_IP);
            						callback_res.setSplit_name(outPath);
            						callback_res.setJobID(mult_request.getJob_id());

            						AdditiveSplittingServiceStub bpelProcess = new AdditiveSplittingServiceStub();

            						bpelProcess.callBack_Operation3(callback_res);

            					}
            					else if(mult_request.getOp_id().equals("4"))
            					{
            						Callback_4 callback_res= new Callback_4();

            						callback_res.setSub_jobID(mult_request.getJob_id());
            						callback_res.setOp_ID(mult_request.getOp_id());
            						callback_res.setCloud_url(CLOUD_IP);
            						callback_res.setSplit_name(outPath);
            						callback_res.setJobID(mult_request.getJob_id());

            						AdditiveSplittingServiceStub bpelProcess = new AdditiveSplittingServiceStub();

            						bpelProcess.callBack_Operation4(callback_res);

            					}

            					else if(mult_request.getOp_id().equals("5"))
            					{
            						Callback_5 callback_res= new Callback_5();

            						callback_res.setSub_jobID(mult_request.getJob_id());
            						callback_res.setOp_ID(mult_request.getOp_id());
            						callback_res.setCloud_url(CLOUD_IP);
            						callback_res.setSplit_name(outPath);
            						callback_res.setJobID(mult_request.getJob_id());

            						AdditiveSplittingServiceStub bpelProcess = new AdditiveSplittingServiceStub();

            						bpelProcess.callBack_Operation5(callback_res);

            					}
            					System.out.println("CALL BACK FINISHED");
            					logfile.write("Call back sent to the calling process of job "+mult_request.getJob_id()+" for the callback operation : Callback_Operation"+mult_request.getOp_id() +" !!");

            				} catch (AxisFault e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            					logfile.write("Error in sending callback to the calling process of job "+mult_request.getJob_id()+" for the callback operation : Callback_Operation"+mult_request.getOp_id() +" !!");
            				} catch (RemoteException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
             				
             				
             			}
             		}).start();

             	}
     
    }
    
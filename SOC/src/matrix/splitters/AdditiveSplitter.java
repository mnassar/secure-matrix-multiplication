package matrix.splitters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.math.hadoop.SequenceFileWriterJob;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import securerest.MatrixOP;

import broker.BrokerSOCResource;
import broker.Location;
import broker.Log;
import broker.MatrixMeta;
import broker.MetadataStoreConnection;
import broker.ResourceMeta;
import broker.ResourceMetaAdapter;
import broker.SOCConfiguration;

public class AdditiveSplitter {

		private String orig_mat;
		private BrokerSOCResource matrix;
		private String[] splits;
		private int n;
		
		public static  int MIN =-10000 ;
		public static int MAX= 10000;
		
		public AdditiveSplitter(String csv_orig_mat)
		{
			orig_mat = new String(csv_orig_mat);
			n= 2; 								// n=2 splits only to split to more do re-splitting for the results
			splits = new String[n];
			SOCConfiguration conf = new SOCConfiguration();
		}
		
		public AdditiveSplitter(BrokerSOCResource mat)
		{
			matrix = mat;
			orig_mat = new String(mat.getFile_path());
			SOCConfiguration conf = new SOCConfiguration();
			
			splits = new String[2];
		}

		public void Split(Location loc_split1, Location loc_split2) 
		{
			SOCConfiguration socconf = new SOCConfiguration();
			splits[0] = new String(SOCConfiguration.BROKER_STORAGE_PATH+"/"+matrix.getResource_id()+"_"+((MatrixMeta)matrix.getResource_meta()).getnRows()+"_"+((MatrixMeta)matrix.getResource_meta()).getnColumns()+"_1");
			splits[1] = new String(SOCConfiguration.BROKER_STORAGE_PATH+"/"+matrix.getResource_id()+"_"+((MatrixMeta)matrix.getResource_meta()).getnRows()+"_"+((MatrixMeta)matrix.getResource_meta()).getnColumns()+"_2");
			
			
			try {
				
				File file1 = new File(splits[0]);
				File file2 = new File(splits[1]);
					// if file doesnt exists, then create it
				if (!file1.exists()) {
					file1.createNewFile();
				}
				if (!file2.exists()) {
					file2.createNewFile();
				}
				FileWriter fw = new FileWriter(file1.getAbsoluteFile());
				 PrintWriter pw = new PrintWriter(fw);
				
				 FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
				 PrintWriter pw2 = new PrintWriter(fw2);
				
				
				 BufferedReader br1 = new BufferedReader(new FileReader(orig_mat));
				 
				 Random rand = new Random(System.nanoTime());
				 
				 
				 for (int i=0; i< ((MatrixMeta)matrix.getResource_meta()).getnRows(); i++)
				 {
					 String sCurrentLine = new String(br1.readLine());
					 StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine, ",");
					 
					 for (int j=0; j< ((MatrixMeta)matrix.getResource_meta()).getnColumns(); j++)
					 {
						 int random_value = rand.nextInt(MAX - MIN+1)+ MIN;
						 int orig_mat_val = Integer.parseInt(tokenzier1.nextToken().trim());
						 int second_split_val = orig_mat_val - random_value;
						 pw.print(random_value);
						 pw2.print(second_split_val);
						 if(j== ((MatrixMeta)matrix.getResource_meta()).getnColumns()-1){ pw.println(); pw2.println(); break;}
						 pw.print(","); pw2.print(",");
					 
					 }
				 }
			        //Flush the output to the file
			        pw.flush();
			        pw2.flush();
			        
			        //Close the Print Writer
			        pw.close();
			        pw2.close();
			        
			        //Close the File Writer
			        fw.close();
			        fw2.close();
			        
			        br1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SequenceFileWriterJob writer = new SequenceFileWriterJob();
			try {
				//Write their sequence file format to the cloud
				writer.writeToCloud(splits[0], loc_split1.getUrl());
				writer.writeToCloud(splits[1], loc_split2.getUrl());
				
				//Delete split copies on broker
				File split1 = new File(splits[0]);
				File split2 = new File(splits[1]);
				if(split1.delete()) 
					System.out.println("File " +split1.getAbsolutePath() +" deleted!");
				else
					System.out.println("File " +split1.getAbsolutePath() +" can not be found!");
				if(split2.delete()) 
					System.out.println("File " +split2.getAbsolutePath() +" deleted!");
				else
					System.out.println("File " +split2.getAbsolutePath() +" can not be found!");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}

		
		public void Combine(final Location loc_split1, final Location loc_split2) 
		{
			SOCConfiguration socconf = new SOCConfiguration();
			
			try {
				Configuration config = new Configuration();
				//Location loc1 = new Location(add_request.getAdd_list()[0].getCloudURL());
				String server_ip1 = loc_split1.getIP();//loc1.getIP();
				System.out.println("server1:" +server_ip1);
				//String hdfs_port= loc1.getPort_no();
				String hdfs_port= "54310";
				String jobtracker_port="54311";

				config.set("fs.default.name","hdfs://"+server_ip1+":"+hdfs_port);
				config.set("mapred.job.tracker", "http://"+server_ip1+":"+jobtracker_port);
				UserGroupInformation.setConfiguration(config);
				final Configuration conf = config;
				UserGroupInformation ugi = UserGroupInformation.createRemoteUser(SOCConfiguration.CLOUD_USER);// UserGroupInformation.getLoginUser());
				ugi.doAs(new PrivilegedExceptionAction<Void>() {
					public Void run() throws Exception {
						
						Configuration conf2 = new Configuration();
						
						String server_ip2 = loc_split2.getIP();
						System.out.println("server2:" +server_ip2);
						//	String hdfs_port= loc2.getPort_no();
						String hdfs_port= "54310";
						String jobtracker_port="54311";

					

						conf2.set("fs.default.name","hdfs://"+server_ip2+":"+hdfs_port);
						conf2.set("mapred.job.tracker", "http://"+server_ip2+":"+jobtracker_port);

					
						FileSystem dfs = null;
						FileSystem dfs2 = null;
						try {
							dfs = FileSystem.get(conf);
							dfs2 = FileSystem.get(conf2);
						} catch (IOException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						String pathA = "/"+matrix.getResource_id()+"_"+((MatrixMeta)matrix.getResource_meta()).getnRows()+"_"+((MatrixMeta)matrix.getResource_meta()).getnColumns()+"_1"+"/part-00000";
						if(!dfs.exists(new Path(dfs.getHomeDirectory()+pathA)))
							pathA = "/"+matrix.getResource_id()+"_"+((MatrixMeta)matrix.getResource_meta()).getnRows()+"_"+((MatrixMeta)matrix.getResource_meta()).getnColumns()+"_1"+"/part-m-00000";
						String pathB = "/"+matrix.getResource_id()+"_"+((MatrixMeta)matrix.getResource_meta()).getnRows()+"_"+((MatrixMeta)matrix.getResource_meta()).getnColumns()+"_2"+"/part-00000";
						if(!dfs2.exists(new Path(dfs2.getHomeDirectory()+pathB)))
							pathB = "/"+matrix.getResource_id()+"_"+((MatrixMeta)matrix.getResource_meta()).getnRows()+"_"+((MatrixMeta)matrix.getResource_meta()).getnColumns()+"_2"+"/part-m-00000";

						try {

							SequenceFile.Reader reader = null;   
							SequenceFile.Reader reader2 = null;
							try {             
								reader = new SequenceFile.Reader(dfs, new Path(dfs.getHomeDirectory()+pathA), conf);
								reader2 = new SequenceFile.Reader(dfs2, new Path(dfs2.getHomeDirectory()+pathB), conf2);
						
								IntWritable key_seq1 = (IntWritable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);    
								VectorWritable value = (VectorWritable) ReflectionUtils.newInstance(reader.getValueClass(), conf);

								IntWritable key_seq2 = (IntWritable) ReflectionUtils.newInstance(reader2.getKeyClass(), conf2);    
								VectorWritable value2 = (VectorWritable) ReflectionUtils.newInstance(reader2.getValueClass(), conf2);

						

								File combined_file = new File(SOCConfiguration.BROKER_STORAGE_PATH+"/"+matrix.getResource_id());
								Log logfile = new Log(SOCConfiguration.LOG_DIRECTORY);
								logfile.write("Addition of " +dfs.getHomeDirectory()+pathA + " , " +dfs2.getHomeDirectory()+pathB +" is being done to combine the splits on the broker..... ");

								
								// if file doesnt exists, then create it
								if (!combined_file.exists()) {
									combined_file.createNewFile();
								}

								FileWriter fw = new FileWriter(combined_file.getAbsoluteFile());
								BufferedWriter bw = new BufferedWriter(fw);

								String sCurrentLine1=null, sCurrentLine2=null, sCurrentLine3=null, sCurrentLine4= null;

								while (reader.next(key_seq1, value) && reader2.next(key_seq2, value2) ) {                
									
									HashMap<Integer,Double>  result_row = new HashMap<Integer, Double>(((MatrixMeta)matrix.getResource_meta()).getnRows());

									String key1= "0";
									String key2= "0";
									
									boolean zeros_row1 = false;
									boolean zeros_row2 = false;
									
									String indx1 = null; Integer col_ind1=0; 
									String indx2 = null; Integer col_ind2=0; 
									
									sCurrentLine1 =  new String(value.toString());
									sCurrentLine2 =    new String(value2.toString());
								
									if(sCurrentLine1.equals("{}")) zeros_row1=true;
									if(sCurrentLine2.equals("{}")) zeros_row2=true;
									
									StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine1, ",");
									StringTokenizer tokenzier2 = new StringTokenizer(sCurrentLine2, ",");
									
									StringTokenizer format_tokenizer= new StringTokenizer(sCurrentLine1, ":");
									int j = ((MatrixMeta)matrix.getResource_meta()).getnColumns()-1;
									boolean descending= true;
									if(format_tokenizer.hasMoreTokens() && format_tokenizer.nextToken().substring(1).equals("0"))
									{
										j=0;
										descending=false;
									}
									while( tokenzier1.hasMoreTokens()| tokenzier2.hasMoreTokens() )
									{ 
										Double val1 = new  Double(0.0), val2= new  Double(0.0), val3= new  Double(0.0), val4 = new  Double(0.0);
										String token_string1= null;
										String token_string2= null; 
										String token_string3= null; 
										String token_string4= null; 

										if(!zeros_row1)
										{
											token_string1= tokenzier1.nextToken(",");
											if(token_string1!=null)
											{
												int colon_indx = token_string1.indexOf(":");
												String indx = null;
												if(token_string1.contains("{"))
													indx = token_string1.substring(1,colon_indx);
												else
													indx = token_string1.substring(0,colon_indx);
											
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

										
										result_row.put(j, val1 + val2 );
										//fw2.append("\n col "+j+": " +val1 +val2+val3+val4+"\n");
										if(descending) 
											j--;
										else
											j++;

									}

									for(int i=0; i< ((MatrixMeta)matrix.getResource_meta()).getnColumns(); i++)
									{
										bw.write(result_row.get(i).toString());
										if(i== ((MatrixMeta)matrix.getResource_meta()).getnColumns()-1 ) {bw.write("\n"); break;}
										bw.write(",");
									}

								}
								bw.close();  

								matrix.setAvailable(true);
								MetadataStoreConnection conn;
								try {

									conn = new MetadataStoreConnection(SOCConfiguration.METADATA_STORE_URL);
									Gson gson  = new Gson();
									GsonBuilder builder = new GsonBuilder();
									builder.registerTypeAdapter(ResourceMeta.class   , new ResourceMetaAdapter());
									gson = builder.create();

									conn.updateSOCResource(matrix);
									conn.close();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							catch (Exception e) {           
								e.printStackTrace();        
							}
							finally {            
								IOUtils.closeStream(reader);      
							}
							dfs.close();
							dfs2.close();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return null;
					}});




			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	

		public static void main(String[] args)
		{
			String json= "{\"resource_id\":\"95d5d956-3bfb-4f79-a602-8f85672486ad\",\"locations\":[{\"url\":\"hdfs://localhost:54310\"},{\"url\":\"hdfs://localhost:54310\"}],\"storage_protocol\":\"ADDITIVE_SPLITTING\",\"user_token\":\"farida\",\"file_path\":\"/home/farida/Documents/SOC/resources/95d5d956-3bfb-4f79-a602-8f85672486ad\",\"resource_meta\":{\"nRows\":10,\"nColumns\":10,\"dataType\":\"decimal\",\"type\":\"matrix\"}}";
			Gson gson = new Gson();
			ArrayList<Location> locs = new ArrayList<Location>();
			locs.add(new Location("hdfs://localhost:54310"));
			locs.add(new Location("hdfs://localhost:54310"));
			SOCConfiguration conf = new SOCConfiguration();
			//BrokerSOCResource mat = gson.fromJson(json, BrokerSOCResource.class);
			BrokerSOCResource mat = new BrokerSOCResource("A", locs, new MatrixMeta(10,10,"INTEGER"));;
			mat.setFile_path(SOCConfiguration.BROKER_STORAGE_PATH+"/"+mat.getResource_id());
			System.out.println(mat.getResource_meta().getType());
			System.out.println(new MatrixMeta(mat.getResource_meta()).getnColumns());
			AdditiveSplitter splitter = new AdditiveSplitter(mat);
			splitter.Split(locs.get(0),locs.get(1));
			//splitter.Combine(new Location("hdfs://localhost:54310"), new Location("hdfs://localhost:54310"));
		}
		
}

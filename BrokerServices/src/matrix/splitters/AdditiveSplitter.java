package matrix.splitters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.mahout.math.hadoop.SequenceFileWriterJob;


import broker.BrokerSOCResource;
import broker.Location;
import broker.MatrixMeta;
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
			n= 2; // n=2 splits only to split to more do re-splitting for the results
			splits = new String[n];
		}
		
		public AdditiveSplitter(BrokerSOCResource mat)
		{
			matrix = new BrokerSOCResource(mat);
			orig_mat = new String(mat.getFile_path());
			SOCConfiguration conf = new SOCConfiguration();
			
			splits = new String[2];
		}

		public void Split(Location loc_split1, Location loc_split2) 
		{
			
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
		
		
}

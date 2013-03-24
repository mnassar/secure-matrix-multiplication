package matrix.convert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.hadoop.mapred.SequenceFileAsTextInputFormat;
import org.apache.mahout.utils.SequenceFileDumper;

import matrix.meta.Configuration;
import matrix.meta.MatrixMeta;

public class SequenceToCSV {

	MatrixMeta meta;
	private String seq_file;
	private String csv_file;
	
	public SequenceToCSV(MatrixMeta mm, Configuration conf) {
		// TODO Auto-generated constructor stub
		meta = new MatrixMeta(mm.getID(), mm.getPath(), mm.getnRows(), mm.getnCols());
		csv_file = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+meta.getID()+".csv");
		seq_file = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+meta.getID());
	}
	
	
	
	public void convert()
	{
		BufferedReader br1 = null;
		try {
	 
				String sCurrentLine;
				
				//SequenceFileDumper seqText = new SequenceFileDumper();
				String[] args= {"--input", seq_file+"/part-m-00000", "--output" , seq_file+".txt"};
				SequenceFileDumper.main(args );
				File file = new File(csv_file);
				 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				br1 = new BufferedReader(new FileReader(seq_file+".txt"));
				//Read the first two lines fo key  and value class types
				br1.readLine();	br1.readLine();
					for(int i=0; i<meta.getnRows(); i++)
					{
						sCurrentLine = new String(br1.readLine());
	 					 
						
	 					StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine, ":");
	 					
						tokenzier1.nextToken(); tokenzier1.nextToken();tokenzier1.nextToken();
					    HashMap<Integer,Double>  row = new HashMap<Integer, Double>(meta.getnCols());
					    
					    String indx=  tokenzier1.nextToken();
					    Integer col_ind= Integer.parseInt(indx.substring(2).trim());
					    System.out.println("col_ind:"+col_ind);
					    
					    	
					   
					    
				    	while( tokenzier1.hasMoreTokens())
					    { 
				    		 String token_string= tokenzier1.nextToken(",");
				    		 Double val = new  Double(0.0);
				    		 if(token_string.contains("}"))
				    		 {
				    			 val= Double.valueOf(token_string.substring(1, token_string.length()-1).trim());
				    			 row.put(col_ind,val);
				    			 break;
				    		 }
				    		 else
				    			 val= Double.valueOf(token_string.substring(1).trim());
					    	 row.put(col_ind,val);
					    
					    	 indx=  tokenzier1.nextToken(":");
							 col_ind= Integer.parseInt(indx.substring(1).trim());
							 System.out.println("col_ind:"+col_ind);
								 
					    }
				    	
				    	Print(row, bw);
					    
					}
					bw.close();
	
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (br1 != null)
						br1.close();
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		
	}
	
	private void Print(HashMap<Integer,Double> vec, BufferedWriter bw) throws IOException
	{
		for(int i=0; i< meta.getnCols(); i++)
		{
			if(vec.get(i)!=null) 
				bw.write(vec.get(i).toString());
			else
				bw.write("0.0");
			if(i== meta.getnCols()-1 ) {bw.write("\n"); break;}
			bw.write(",");
		}
		
	}
	
}

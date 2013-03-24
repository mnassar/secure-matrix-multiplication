package matrix.split;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;



import csv.matrix.generate.CSVGenerator;

import matrix.meta.MatrixMeta;

public class MatrixSplitter {

	private String orig_mat;
	private MatrixMeta mat_meta;
	private String[] splits;
	private int n;
	
	public MatrixSplitter(String csv_orig_mat)
	{
		orig_mat = new String(csv_orig_mat);
		n= 2; // n=2 splits only to split to more do re-splitting for the results
		splits = new String[n];
	}
	
	public MatrixSplitter(MatrixMeta orig_mat_meta)
	{
		mat_meta = new MatrixMeta(orig_mat_meta.getID(), orig_mat_meta.getPath(), orig_mat_meta.getnRows(), orig_mat_meta.getnCols());
		orig_mat = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+orig_mat_meta.getID()+".csv");
		n= 2; // n=2 splits only to split to more do re-splitting for the results
		splits = new String[n];
	}
	public String[] Split() //currently deals with n=2 splits only to split to more do re-splitting for the results
	{
		
		splits[0] = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID()+"_1.csv");
		splits[1] = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID()+"_2.csv");
		
		
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
			 
			 
			 for (int i=0; i< mat_meta.getnRows(); i++)
			 {
				 String sCurrentLine = new String(br1.readLine());
				 StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine, ",");
				 
				 for (int j=0; j< mat_meta.getnCols(); j++)
				 {
					 //int random_value= MIN + (int)(Math.random() * ((MAX - MIN) + 1));
					 int random_value = rand.nextInt(CSVGenerator.MAX-CSVGenerator.MIN+1)+CSVGenerator.MIN;
					 int orig_mat_val = Integer.parseInt(tokenzier1.nextToken());
					 int second_split_val = orig_mat_val - random_value;
					 pw.print(random_value);
					 pw2.print(second_split_val);
					 if(j== mat_meta.getnCols()-1){ pw.println(); pw2.println(); break;}
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


		return splits;
	}
	
	
	public MatrixMeta[] Split(MatrixMeta orig_mat_meta) //currently deals with n=2 splits only to split to more do re-splitting for the results
	{
		mat_meta = new MatrixMeta(orig_mat_meta.getID(), orig_mat_meta.getPath(), orig_mat_meta.getnRows(), orig_mat_meta.getnCols());
		orig_mat = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+orig_mat_meta.getID()+".csv");
		n= 2; // n=2 splits only to split to more do re-splitting for the results
		splits = new String[n];
		
		for(int i=0; i<n; i++)
		{
			splits[i] = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID()+"_"+i+".csv");
		}
		
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
			 
			 
			 for (int i=0; i< mat_meta.getnRows(); i++)
			 {
				 String sCurrentLine = new String(br1.readLine());
				 StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine, ",");
				 
				 for (int j=0; j< mat_meta.getnCols(); j++)
				 {
					 //int random_value= MIN + (int)(Math.random() * ((MAX - MIN) + 1));
					 int random_value = rand.nextInt(CSVGenerator.MAX-CSVGenerator.MIN+1)+CSVGenerator.MIN;
					 int orig_mat_val = Integer.parseInt(tokenzier1.nextToken());
					 int second_split_val = orig_mat_val - random_value;
					 pw.print(random_value);
					 pw2.print(second_split_val);
					 if(j== mat_meta.getnCols()-1){ pw.println(); pw2.println(); break;}
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

		MatrixMeta[] splits_meta = new MatrixMeta[n];
		splits_meta[0] = new MatrixMeta(mat_meta.getID()+"_1", matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID()+"_1.csv", mat_meta.getnRows(), mat_meta.getnCols());
		splits_meta[1] = new MatrixMeta(mat_meta.getID()+"_2", matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID()+"_2.csv", mat_meta.getnRows(), mat_meta.getnCols());
		
		return splits_meta;
	}
}

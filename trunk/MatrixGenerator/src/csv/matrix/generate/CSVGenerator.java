package csv.matrix.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import matrix.meta.Configuration;
import matrix.meta.MatrixMeta;

public class CSVGenerator {
	
	private MatrixMeta mat_meta;
	public static  int MIN =-10000 ;
	public static int MAX= 10000;
	
	public CSVGenerator(MatrixMeta mm, Configuration conf)
	{
		mat_meta = new MatrixMeta(mm.getID(), mm.getPath(), mm.getnRows(), mm.getnCols());
	}
	
	public String generateRandom()
	{
		String pathsaved= Configuration.MATRIX_DIR+"/"+mat_meta.getID()+".csv";
		
		try {
			File file = new File(pathsaved);
				 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			 PrintWriter pw = new PrintWriter(fw);
			 
			 //Generate matrix and Write to file 
			 Random rand = new Random(System.nanoTime());
			 
			 
			 for (int i=0; i< mat_meta.getnRows(); i++)
				 for (int j=0; j< mat_meta.getnCols(); j++)
				 {
					 //int random_value= MIN + (int)(Math.random() * ((MAX - MIN) + 1));
					 int random_value = rand.nextInt(MAX-MIN+1)+MIN;
					 pw.print(random_value);
					 if(j== mat_meta.getnCols()-1){ pw.println(); break;}
					 pw.print(",");
					 
					 
				 }
			
		        //Flush the output to the file
		        pw.flush();
		       
		        //Close the Print Writer
		        pw.close();
		       
		        //Close the File Writer
		        fw.close();        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return pathsaved;
	}

}

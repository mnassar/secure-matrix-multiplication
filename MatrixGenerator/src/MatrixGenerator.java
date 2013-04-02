import java.io.IOException;

import sequence.matrix.generate.SequenceFileGenerator;
import csv.matrix.generate.CSVGenerator;
import matrix.convert.CSVToSequence;
import matrix.convert.SequenceToCSV;
import matrix.meta.Configuration;
import matrix.meta.MatrixMeta;
import matrix.split.MatrixSplitter;


public class MatrixGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		Configuration conf =new Configuration();
		MatrixMeta meta =null;
		int nsize = 5000;
		 if(args.length != 0)
		 {
			 meta = new MatrixMeta(args[0],null,Integer.parseInt(args[1]),Integer.parseInt(args[2]));
			 nsize = Integer.parseInt(args[3]);
			 Configuration.MATRIX_DIR = args[4];		 
		 }
		 else
		 {
			System.out.println("If you want different size or name use: java MatrixGenerator ID nRows nCols nsize mat_directory");
			meta = new MatrixMeta("A_"+nsize, null, nsize, nsize);
		 }
		 
		CSVGenerator.MIN =-100;
		CSVGenerator.MAX = 100;
		CSVGenerator gen = new CSVGenerator(meta, conf);
		String filesaved= gen.generateRandom();
		System.out.println(filesaved);
		
		
		meta = new MatrixMeta("B_"+nsize, null, nsize, nsize);
		
		gen = new CSVGenerator(meta, conf);
		filesaved= gen.generateRandom();
		System.out.println(filesaved);
		/*
		MatrixSplitter splitter = new MatrixSplitter(meta);
		
		String[] splits  = splitter.Split();
		System.out.println(splits[0]);
		System.out.println(splits[1]);
		*/
		
		//SequenceFileGenerator seq = new SequenceFileGenerator(meta, conf);
		//seq.generateRandom();
		
		//CSVToSequence csvtoSeq = new CSVToSequence(meta, conf);
		//csvtoSeq.convert();
		
		//meta = new MatrixMeta("B", null, 100, 100);
		//SequenceToCSV seqToCSV = new SequenceToCSV(meta, conf);
		//seqToCSV.convert();
		
		
	}

}

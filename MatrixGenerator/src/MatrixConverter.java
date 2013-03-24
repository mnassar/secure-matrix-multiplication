import matrix.convert.CSVToSequence;
import matrix.meta.Configuration;
import matrix.meta.MatrixMeta;
import matrix.split.MatrixSplitter;
import csv.matrix.generate.CSVGenerator;


public class MatrixConverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Configuration conf =new Configuration();
		MatrixMeta meta =null;
		int nsize = 10000;
		 if(args.length != 0)
		    meta = new MatrixMeta(args[0],null,Integer.parseInt(args[1]),Integer.parseInt(args[2]));
			
		else
		{
			System.out.println("If you want custom size or name use: java MatrixConverter ID path nRows nCols");
			
			meta = new MatrixMeta("A_"+nsize, null, nsize, nsize);
		}
		 
		
		
		
		//SequenceFileGenerator seq = new SequenceFileGenerator(meta, conf);
		//seq.generateRandom();
		
		 
		 CSVToSequence csvtoSeq = new CSVToSequence(meta, conf);
		 csvtoSeq.convert();
		 
		 meta = new MatrixMeta("A_"+nsize+"_1", null, nsize, nsize);
		 csvtoSeq = new CSVToSequence(meta, conf);
		 csvtoSeq.convert();
		 
		 
		 meta = new MatrixMeta("A_"+nsize+"_2", null, nsize, nsize);
		 csvtoSeq = new CSVToSequence(meta, conf);
		 csvtoSeq.convert();
		
		 
		 meta = new MatrixMeta("B_"+nsize, null, nsize, nsize);
		 csvtoSeq = new CSVToSequence(meta, conf);
		 csvtoSeq.convert();
		
		 meta = new MatrixMeta("B_"+nsize+"_1", null, nsize, nsize);
		 csvtoSeq = new CSVToSequence(meta, conf);
		 csvtoSeq.convert();
		 
		 meta = new MatrixMeta("B_"+nsize+"_2", null, nsize, nsize);
		 csvtoSeq = new CSVToSequence(meta, conf);
		 csvtoSeq.convert();
		
	}


}

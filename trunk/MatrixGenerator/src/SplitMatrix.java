import matrix.meta.Configuration;
import matrix.meta.MatrixMeta;
import matrix.split.MatrixSplitter;
import csv.matrix.generate.CSVGenerator;


public class SplitMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Configuration conf =new Configuration();
		MatrixMeta meta =null;
		int nsize= 10000;
		 if(args.length != 0)
		    meta = new MatrixMeta(args[0],args[1],Integer.parseInt(args[2]),Integer.parseInt(args[3]));
			
		else
		{
			System.out.println(" If you want different size or name use: java SplitMatrix ID nRows nCols");
			meta = new MatrixMeta("A_10000", null, nsize, nsize);
		}
		 
		
		
		MatrixSplitter splitter = new MatrixSplitter(meta);
		
		String[] splits  = splitter.Split();
		System.out.println(splits[0]);
		System.out.println(splits[1]);
		
		
		meta = new MatrixMeta("B_10000", null, nsize, nsize);
		splitter = new MatrixSplitter(meta);
	
		splits  = splitter.Split();
		System.out.println(splits[0]);
		System.out.println(splits[1]);
		
		
	}

}

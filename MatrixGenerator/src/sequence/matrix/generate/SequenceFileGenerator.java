package sequence.matrix.generate;
/////////////////////////////////////////////////////////////////
//Qatar University
//QU_ NPRP09-622-1-090
//Author: Farida Sabry , Mar 2013
/////////////////////////////////////////////////////////////////

import java.io.IOException;
import matrix.meta.MatrixMeta;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public  class  SequenceFileGenerator {


	private static MatrixMeta mat_meta;
	public static  int MIN =-10000 ;
	public static int MAX= 10000;
	private static String csv_file;
	private static String seq_file;
	
	
	public SequenceFileGenerator(MatrixMeta mm, matrix.meta.Configuration conf)
	{
		mat_meta = new MatrixMeta(mm.getID(), mm.getPath(), mm.getnRows(), mm.getnCols());
		csv_file = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID()+".csv");
		seq_file = new String(matrix.meta.Configuration.MATRIX_DIR+"/"+mat_meta.getID());
	}
	
	public void generateRandom()
	{
		try {
			SequenceMapper.main();
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
	
	
public static class SequenceMapper  extends
Mapper<LongWritable, Text, IntWritable, VectorWritable> {			
	
@Override
protected void map(LongWritable key, Text value,
Mapper<LongWritable, Text, IntWritable, VectorWritable>.Context context)
throws IOException, InterruptedException {

//assuming that your line contains key and value separated by \t
String[] split = value.toString().split(",");
Vector matrixRow = new RandomAccessSparseVector(Integer.MAX_VALUE, 100);
int i =0;
for (String mat_elem: split)
matrixRow.setQuick(i++, Double.valueOf(mat_elem));
context.write(new IntWritable(Integer.valueOf(key.toString())), new VectorWritable(matrixRow));
}
/**
* @param args
*/
public static void main() throws IOException,
InterruptedException, ClassNotFoundException {

Configuration conf = new Configuration();
Job job = new Job(conf);
job.setJobName("SequenceFileGenerator");
job.setJarByClass(SequenceMapper.class);

job.setMapperClass(SequenceMapper.class);
job.setReducerClass(Reducer.class);


job.setNumReduceTasks(0);

job.setOutputKeyClass(IntWritable.class);
job.setOutputValueClass(VectorWritable.class);

job.setOutputFormatClass(SequenceFileOutputFormat.class);
job.setInputFormatClass(TextInputFormat.class);

TextInputFormat.addInputPath(job, new Path(csv_file));
Configuration config = new Configuration();



FileSystem dfs = FileSystem.get(config);
try {
	if(dfs.exists(new Path(seq_file)))
	{
		dfs.delete(new Path(seq_file), true);
	}
} catch (IOException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
//Path p = new Path(dfs.getWorkingDirectory()+"/HaddopOutput/A");
//dfs.mkdirs(p);
//SequenceFileOutputFormat.setOutputPath(job, new Path("/home/farida/GlobalFS/OB1"));
SequenceFileOutputFormat.setOutputPath(job, new Path(seq_file));

//submit and wait for completion
job.waitForCompletion(true);
}
}
}


package broker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import Jama.Matrix;

public class MatrixReader {

	private String filePath;
	private Matrix readMatrix;
	private int rows;
	private int cols;
	public MatrixReader(int x, int y)
	{
		rows = x;
		cols = y;
	}
	
	public Matrix readfromCSV(String path) throws IOException
	{
		filePath = new String(path);
		BufferedReader CSVFile = new BufferedReader(new FileReader(filePath));

		String dataRow = CSVFile.readLine(); // Read first line.
		// The while checks to see if the data is null. If 
		// 	it is, we've hit the end of the file. If not, 
		// process the data.
		int i=0, j=0;
		
		double[][] mat_data = new double[rows][cols];
		while (dataRow != null){
			String[] dataArray = dataRow.split(", ");
		
			for (String item:dataArray) { 
					mat_data[i][j++] = Double.parseDouble(item);
				}
			
			dataRow = CSVFile.readLine(); // Read next line of data.
			i++; j=0;
			
		}
		// 	Close the file once all data has been read.
		CSVFile.close();
		readMatrix = new Matrix(mat_data);
		return readMatrix;
	}
	
}

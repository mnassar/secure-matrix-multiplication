package broker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import Jama.Matrix;

public class MatrixWriter {

	private String filePath;
	private Matrix matrixToWrite;
	private int rows;
	private int cols;
	public MatrixWriter(int x, int y, Matrix m)
	{
		rows = x;
		cols = y;
		matrixToWrite = m;
	}
	
	public void writeToCSV(String path) throws IOException
	{
		filePath = new String(path);
		BufferedWriter CSVFile = new BufferedWriter(new FileWriter(filePath));

		double[][] mat_data = new double[rows][cols];
		mat_data = matrixToWrite.getArray();
		
		for(int i=0; i< rows; i++)
		{
			for(int j=0; j< cols; j++)
		
				{
					CSVFile.append(mat_data[i][j]+", ");
				}
			CSVFile.newLine();
		}
		CSVFile.flush();
		CSVFile.close();
		
	}
	
}

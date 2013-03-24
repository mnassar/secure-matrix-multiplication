package matrix.meta;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Configuration {

	public static String META_DIR;
	public static String MATRIX_DIR;
	
	public Configuration()
	{
		try {
			BufferedReader buffer = new BufferedReader(new FileReader("conf.txt"));
			String sCurrentLine = new String(buffer.readLine());
			StringTokenizer tokenzier1 = new StringTokenizer(sCurrentLine, "=");
			String var = tokenzier1.nextToken();
			String val= tokenzier1.nextToken();
			Assign(var,val);
			sCurrentLine = new String(buffer.readLine());
			tokenzier1 = new StringTokenizer(sCurrentLine, "=");
			var = tokenzier1.nextToken();
			val= tokenzier1.nextToken();
			Assign(var,val);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot open configuration file");
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void Assign(String var, String val)
	{
		if(var.trim().equals("META_DIR"))
			META_DIR = new String(val.trim());
		else if (var.trim().equals("MATRIX_DIR"))
			MATRIX_DIR = new String(val.trim());
	}
}

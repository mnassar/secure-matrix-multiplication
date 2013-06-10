package broker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class SOCConfiguration {

	public static String  ODE_PATH;
	public static String  WORKFLOWS_PATH;
	public static String  BROKER_STORAGE_PATH;
	public static String  ADDITIVE_SPLITTING_PROCESS;
	public static String  BROKER_URL;
	public static String  METADATA_STORE_URL;
	private ArrayList<Location> clouds;
	
	public SOCConfiguration() {
		// TODO Auto-generated constructor stub
		File conf = new File("/etc/soc/soc.conf");
		BufferedReader br = null;
		clouds = new ArrayList<Location>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(conf));
 
			while ((sCurrentLine = br.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(sCurrentLine);
				String variable = tokenizer.nextToken("="); variable = variable.trim();
				String path = tokenizer.nextToken(); path  = path.trim();
			
				if(variable.equals("ODE_PATH")) ODE_PATH = new String(path.substring(1,path.length()-1));
				if(variable.equals("WORKFLOWS_PATH")) WORKFLOWS_PATH= new String(path.substring(1,path.length()-1));
				if(variable.equals("ADDITIVE_SPLITTING_PROCESS"))  ADDITIVE_SPLITTING_PROCESS= new String(path.substring(1,path.length()-1));
				if(variable.equals("BROKER_URL"))  BROKER_URL = new String(path.substring(1,path.length()-1));
				if(variable.equals("BROKER_STORAGE_PATH"))  BROKER_STORAGE_PATH = new String(path.substring(1,path.length()-1));
				if(variable.equals("METADATA_STORE_URL"))  METADATA_STORE_URL = new String(path.substring(1,path.length()-1));
				if(variable.contains("CLOUD"))  clouds.add(new Location(new String(path.substring(1,path.length()-1))))  ;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		
	}

	public ArrayList<Location> getClouds() {
		return clouds;
	}

	public void setClouds(ArrayList<Location> clouds) {
		this.clouds = clouds;
	}
	
	public Location getCloud(int i)
	{
		return clouds.get(i);
	}
	
	public Location getRandomCloud()
	{
		Random r = new Random();
		int index = r.nextInt(clouds.size());
		return clouds.get(index);
	}
}

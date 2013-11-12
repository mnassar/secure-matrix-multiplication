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
	public static String  WSDLS_PATH;
	public static String  ADDITIVE_SPLITTING_PROCESS;
	public static String  BROKER_URL;
	public static String  METADATA_STORE_URL;
	public static String  LOG_DIRECTORY;
	public static String  CLOUD_USER;
	private ArrayList<Location> clouds;
	
	public static String configuration_file = "/etc/soc/soc.conf";
	
	public SOCConfiguration(String conf_file_path) {
		// TODO Auto-generated constructor stub
		File conf = new File(conf_file_path);
		BufferedReader br = null;
		this.clouds = new ArrayList<Location>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(conf));
 
			while ((sCurrentLine = br.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(sCurrentLine);
				System.out.println(sCurrentLine);
				String variable = tokenizer.nextToken("="); variable = variable.trim();
				String path = tokenizer.nextToken(); path  = path.trim();
			
				if(variable.equals("ODE_PATH")) ODE_PATH = new String(path.substring(1,path.length()-1));
				else if(variable.equals("WORKFLOWS_PATH")) WORKFLOWS_PATH= new String(path.substring(1,path.length()-1));
				else if(variable.equals("ADDITIVE_SPLITTING_PROCESS"))  ADDITIVE_SPLITTING_PROCESS= new String(path.substring(1,path.length()-1));
				else if(variable.equals("BROKER_URL"))  BROKER_URL = new String(path.substring(1,path.length()-1));
				else if(variable.equals("BROKER_STORAGE_PATH"))  BROKER_STORAGE_PATH = new String(path.substring(1,path.length()-1));
				else if(variable.equals("WSDLS_PATH"))  WSDLS_PATH = new String(path.substring(1,path.length()-1));
				else if(variable.equals("METADATA_STORE_URL"))  METADATA_STORE_URL = new String(path.substring(1,path.length()-1));
				else if(variable.equals("LOG_DIRECTORY"))  LOG_DIRECTORY = new String(path.substring(1,path.length()-1));
				else if(variable.equals("CLOUD_USER"))   CLOUD_USER = new String(path.substring(1,path.length()-1));
				else if(variable.contains("CLOUD"))  this.clouds.add(new Location(new String(path.substring(1,path.length()-1))))  ;
				
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

	public SOCConfiguration()
	{
		this(configuration_file);
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
	
	public static void main(String[] args)
	{
		SOCConfiguration conf = new SOCConfiguration();
		System.out.println(conf.getCloud(0).getUrl());
		System.out.println(SOCConfiguration.LOG_DIRECTORY);
		System.out.println(SOCConfiguration.CLOUD_USER);
	}
	
}


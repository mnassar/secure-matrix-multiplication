package org.cloudservices.matserv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Log {

	private String log_file;
	static public File log;
	
	public Log(String log_folder)
	{
		log = new File(log_folder+"/"+  (new SimpleDateFormat("yyyy_MM_dd")).format(new Date())+".log");
		if(!log.exists())
			try {
				log.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void write (String string_to_write)
	{
		FileWriter fw;
		try {
		
			fw = new FileWriter(log.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\n"+(new SimpleDateFormat("hh:mm:ss")).format(new Date())+" at Cloud Services: "+string_to_write+"\n");
			bw.close();
			fw.close();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		Log log = new Log("/home/hadoop/Documents/SOC/logs");
		log.write("Hello");
		
	}
}

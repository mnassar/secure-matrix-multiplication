import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class WSDLConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String wsdl_server1= "/home/farida/"+"MatServ24.wsdl";
		String server1_url="http://localhost:8080/SM_WS/services/MatServ";
		
		 
		try {
			
			 if(args!=null && args.length ==2)
			 {
				 	wsdl_server1 = new String(args[0]);
				 	server1_url = new String(args[1]);
				 	
			 }
	            // Open the file that is the first
	            // command line parameter
	            FileInputStream fstream = new FileInputStream(wsdl_server1);
	            
	            // Get the object of DataInputStream
	            DataInputStream in = new DataInputStream(fstream);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            String strLine;
	            StringBuilder fileContent = new StringBuilder();
	            //Read File Line By Line
	            while ((strLine = br.readLine()) != null) {
	                // Print the content on the console
	                
	             
	                
	                if (strLine.contains("<soap:address location=")) {
	                   ///To UPDATE HERE
	                	System.out.println(strLine);
	                        String newLine = strLine.replaceFirst("=\\S+/>", "=\""+server1_url+"\"/>");
	                        fileContent.append(newLine);
	                        fileContent.append("\n");
	                    } else {
	                        // update content as it is
	                        fileContent.append(strLine);
	                        fileContent.append("\n");
	                    
	                }
	            }
	            //Close the input stream
	            in.close();
	            // Now fileContent will have updated content , which you can override into file
	            FileWriter fstreamWrite = new FileWriter(wsdl_server1);
	            BufferedWriter out = new BufferedWriter(fstreamWrite);
	            out.write(fileContent.toString());
	            out.close();
	            
	        } catch (Exception e) {//Catch exception if any
	            System.err.println("Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	}

}

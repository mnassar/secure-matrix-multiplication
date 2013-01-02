import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.stringtemplate.v4.*;
public class Example2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		STGroup group = new STGroupDir("templates/",'$','$');
		ST st = group.getInstanceOf("Workflow1");
	
		
		String name="hello";
		String url = "http://www.rest.com/{id}";
		st.add("name", name);
		st.add("url", url);
		String result = st.render(); 
		System.out.println(result);
		try {
			FileWriter fstream = new FileWriter("C:\\Users\\NASSAR\\taverna_workflows\\" + name + ".t2flow");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(result);
			out.close();
		}
		catch (IOException e){
			System.err.println("Error: " + e.getMessage());
		}

	}

}

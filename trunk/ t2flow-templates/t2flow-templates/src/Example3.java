import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.stringtemplate.v4.*;

public class Example3 {
	public static void main(String[] args) {
		// pre-prepared inputs 
		String first_workflow_file="workflows/addition.t2flow";
		String sec_workflow_file="workflows/multiplication.t2flow";
		String output_file="workflows/expression-recomposed.t2flow";
		// should be extract by an XML Parser 
		String first_workflow_name="Workflow3"; 
		String sec_workflow_name="Workflow55"; 
		String first_workflow_output_port="result"; 
		String sec_workflow_input_port="A";
		String first_dataflow_ref="531313a7-0835-4c87-9fd9-0548f762778b"; 
		String sec_dataflow_ref="58a72d9e-d226-49b8-9a04-00eba395dbd8";
		String dataflow1=""; // to be extracted from the first t2flow  
		String dataflow2=""; // to be extracted from the sec t2flow 
	
		try {
			BufferedReader br1 = 
					new BufferedReader(new FileReader(first_workflow_file));
			
			System.out.println("reading the addition workflow\n" + br1.readLine());
			System.out.println();
			String strLine;
			while (true)   {
				strLine = br1.readLine();
				if (strLine.endsWith("</workflow>"))
					break;
				dataflow1+=strLine+"\n";
			}
			BufferedReader br2 = 
					new BufferedReader(new FileReader(sec_workflow_file));
			System.out.println("reading the multiplication workflow\n" + br2.readLine());
			System.out.println();
			while (!(strLine = br2.readLine()).endsWith("</workflow>"))   {		
				dataflow2+=strLine+"\n";
			}
			br1.close();
			br2.close();
			dataflow1=dataflow1.replace( "role=\"top\"",  "role=\"nested\"");
			dataflow2=dataflow2.replace( "role=\"top\"",  "role=\"nested\"");
			
			// create the template and add the arguments
			STGroup group = new STGroupDir("templates/",'$','$');
			ST st = group.getInstanceOf("expression");
			st.add("dataflow1", dataflow1);
			st.add("dataflow2", dataflow2);
			st.add("first_workflow_name", first_workflow_name);
			st.add("sec_workflow_name", sec_workflow_name);
			st.add("first_workflow_output_port", first_workflow_output_port);
			st.add("sec_workflow_input_port", sec_workflow_input_port);
			st.add("first_dataflow_ref",first_dataflow_ref);
			st.add("sec_dataflow_ref",sec_dataflow_ref);
			String result = st.render(); 
			System.out.println("writing the nested workflow:");
			System.out.println();
			System.out.println(result);
			FileWriter fstream = new FileWriter(output_file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(result);
			out.close();
		}
		catch (IOException e){
			System.err.println("Error: " + e.getMessage());
		}
	}
}

package org.workflow.translate.expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.nfunk.jep.ParseException;
import org.xml.sax.SAXException;



public class ExpressionTranslator {
	
	private HashMap<String, MatrixMeta> variables_map;
	private String job_ID;
	private String expression;
	
	private String additivesplitting_wf;
	
	
	
	public ExpressionTranslator(String expression)
	{
		UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        System.out.println(":UUID:"+randomUUIDString);
		setJob_ID("job_"+ randomUUIDString);
		setExpression(expression);
		variables_map = new HashMap<String, MatrixMeta>();
	}
	
	public ExpressionTranslator(ExpressionTranslator trans)
	{
		setJob_ID(trans.getJob_ID());
		setExpression(trans.getExpression());
		variables_map = new HashMap<String, MatrixMeta>();
		variables_map = trans.getVariables_map();
	}
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public HashMap<String, MatrixMeta> getVariables_map() {
		return variables_map;
	}

	public void setVariables_map(HashMap<String, MatrixMeta> variables_map) {
		this.variables_map = variables_map;
	}

	public void translate()
	{
		ExpressionToWorkflow expWF = new ExpressionToWorkflow(this);
		expWF.initialise();
		try {
			expWF.convert();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main()
	{
		String expression = "A*B + C*D";
		ExpressionTranslator trans = new ExpressionTranslator(expression);
		//**********************
		//This data should be read from the metadata store
		String key ="A";
		String[] storage_url = new String[4];
		storage_url[0] = new String("http://server1/matrix/A1");
		storage_url[1] = new String("http://server2/matrix/A2");
		storage_url[2] = new String("http://server3/matrix/A1");
		storage_url[3] = new String("http://server4/matrix/A2");
		
		MatrixMeta A = new MatrixMeta(key, "http://soc.org/matrix/A", 100, 100, (short)0, storage_url);
		trans.addExpressionVariable(key, A);
		
		key ="B";
		storage_url = new String[4];
		storage_url[0] = new String("http://server1/matrix/B1");
		storage_url[1] = new String("http://server2/matrix/B2");
		storage_url[2] = new String("http://server3/matrix/B1");
		storage_url[3] = new String("http://server4/matrix/B2");
		
		MatrixMeta B = new MatrixMeta(key, "http://soc.org/matrix/B", 100, 100, (short)0, storage_url);
		trans.addExpressionVariable(key, B);
		
		key ="C";
		storage_url = new String[4];
		storage_url[0] = new String("http://server1/matrix/C1");
		storage_url[1] = new String("http://server2/matrix/C2");
		storage_url[2] = new String("http://server3/matrix/C1");
		storage_url[3] = new String("http://server4/matrix/C2");
		
		MatrixMeta C = new MatrixMeta(key, "http://soc.org/matrix/C", 100, 100, (short)0, storage_url);
		trans.addExpressionVariable(key, C);
		
		key ="D";
		storage_url = new String[4];
		storage_url[0] = new String("http://server1/matrix/D1");
		storage_url[1] = new String("http://server2/matrix/D2");
		storage_url[2] = new String("http://server3/matrix/D1");
		storage_url[3] = new String("http://server4/matrix/D2");
		
		MatrixMeta D = new MatrixMeta(key, "http://soc.org/matrix/D", 100, 100, (short)0, storage_url);
		trans.addExpressionVariable(key, D);
		
		
		
		trans.translate();
	}
	/**
	 * @return the job_ID
	 */
	public String getJob_ID() {
		return job_ID;
	}
	/**
	 * @param job_ID the job_ID to set
	 */
	public void setJob_ID(String job_ID) {
		this.job_ID = job_ID;
	}
	
	
	public MatrixMeta getExpressionVariable(String key)
	{
		return new MatrixMeta(variables_map.get(key));
	}

	public void addExpressionVariable(String key, MatrixMeta m)
	{
		variables_map.put(key, m);
	}

}

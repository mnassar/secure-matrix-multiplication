package broker.workflow.translate.expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.nfunk.jep.ParseException;
import org.xml.sax.SAXException;

import broker.BrokerSOCResource;



public class ExpressionTranslator {
	
	private HashMap<String, BrokerSOCResource> variables_map;
	private String job_ID;
	private String expression;
	
	private String additivesplitting_wf;
	
	
	public ExpressionTranslator(String job_id, String expression)
	{

		setJob_ID("job_"+ job_id);
		setExpression(expression);
		variables_map = new HashMap<String, BrokerSOCResource>();
	}
	
	public ExpressionTranslator(String expression)
	{
		UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        System.out.println(":UUID:"+randomUUIDString);
		setJob_ID("job_"+ randomUUIDString);
		setExpression(expression);
		variables_map = new HashMap<String, BrokerSOCResource>();
	}
	
	public ExpressionTranslator(ExpressionTranslator trans)
	{
		setJob_ID(trans.getJob_ID());
		setExpression(trans.getExpression());
		variables_map = new HashMap<String, BrokerSOCResource>();
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
	public HashMap<String, BrokerSOCResource> getVariables_map() {
		return variables_map;
	}

	public void setVariables_map(HashMap<String, BrokerSOCResource> variables_map) {
		this.variables_map = variables_map;
	}

	public void translate() throws ParserConfigurationException, SAXException, IOException
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
		
		//add variables from data store
		
		////////////////////////////////////
		
		try {
			trans.translate();
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	public BrokerSOCResource getExpressionVariable(String key)
	{
		return new BrokerSOCResource(variables_map.get(key));
	}

	public void addExpressionVariable(String key, BrokerSOCResource m)
	{
		variables_map.put(key, m);
	}

}

package soc.client.broker;

import java.util.ArrayList;
import java.util.Date;


public class SOCJob {

	private String expression;
	private ArrayList<SOCResourceAlias> aliases;
	private String userToken;
	private SOCJobStatus status;
	private JobType type;
	private String bpel_instanceID;
	
	private String job_Id;

	/**
	 * @return the status
	 */
	public SOCJobStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SOCJobStatus status) {
		this.status = status;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public ArrayList<SOCResourceAlias> getAliases() {
		return aliases;
	}

	public void setAliases(ArrayList<SOCResourceAlias> aliases) {
		this.aliases = aliases;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getJob_Id() {
		return job_Id;
	}

	public void setJob_Id(String job_Id) {
		this.job_Id = job_Id;
	}
	
	public void setSubmissionDate(Date date)
	{
		this.status.setJobSubmissionDate(date);
	}
	
	public void setJobCompletionDate(Date date)
	{
		this.status.setJobCompletionDate(date);
	}
	
	public void setJobStatus(String jobStatus)
	{
		this.status.setJobStatus(jobStatus);
	}

	public SOCJob(String expression, ArrayList<SOCResourceAlias> aliases) {
		super();
		this.expression = expression;
		this.aliases = aliases;
	}
	
	public SOCJob() {
		super();
		
	}
	
	public SOCJob(SOCJob j) {
		super();

		expression= new String(j.getExpression());
		aliases = j.getAliases();
		userToken = new String(j.getUserToken());
		status = j.getStatus();
		type = j.getType();
		job_Id= j.getJob_Id();

	}

	/**
	 * @return the type
	 */
	public JobType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(JobType type) {
		this.type = type;
	}

	/**
	 * @return the bpel_instanceID
	 */
	public String getBpel_instanceID() {
		return bpel_instanceID;
	}

	/**
	 * @param bpel_instanceID the bpel_instanceID to set
	 */
	public void setBpel_instanceID(String bpel_instanceID) {
		this.bpel_instanceID = bpel_instanceID;
	}
}

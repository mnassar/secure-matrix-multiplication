package broker;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;



import com.google.gson.Gson;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)

public class SOCJobStatus {

	private String jobStatus; // completed, running , started
	private Date jobCompletionDate; //  1/1/2012 13:00,
	private Date jobSubmissionDate;  // 1/1/2012 12:00,
	
	private String resultID; // http://broker/resource/result123

	public SOCJobStatus()
	{
		
	}
	
	public SOCJobStatus(String status, Date submission)
	{
		super();
		jobStatus = new String(status);
		jobSubmissionDate = submission;
	}

	public SOCJobStatus(SOCJobStatus status)
	{
		super();
		jobStatus = new String(status.getJobStatus());
		jobSubmissionDate = status.getJobSubmissionDate();
		jobCompletionDate= status.getJobCompletionDate();
		resultID = status.getResultID();
	}
	/**
	 * @return the jobSubmissionDate
	 */
	public Date getJobSubmissionDate() {
		return jobSubmissionDate;
	}

	/**
	 * @param jobSubmissionDate the jobSubmissionDate to set
	 */
	public void setJobSubmissionDate(Date jobSubmissionDate) {
		this.jobSubmissionDate = jobSubmissionDate;
	}

	/**
	 * @return the jobCompletionDate
	 */
	public Date getJobCompletionDate() {
		return jobCompletionDate;
	}

	/**
	 * @param jobCompletionDate the jobCompletionDate to set
	 */
	public void setJobCompletionDate(Date jobCompletionDate) {
		this.jobCompletionDate = jobCompletionDate;
	}

	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = new String(jobStatus);
	}

	public SOCJobStatus(Date jobSubmissionDate) {
		super();
		this.jobSubmissionDate = jobSubmissionDate;
	}

	public SOCJobStatus(String jobStatus) {
		super();
		this.jobStatus= new String(jobStatus);
	}
	
	public String getResultID() {
		return resultID;
	}

	public void setResultID(String result_id) {
		this.resultID = new String(result_id);
	}
	
	public String toString()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
		
	}
}

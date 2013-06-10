package securerest;

import java.util.Date;

public class SOCJobStatus {

	private String jobStatus; // completed, running , started
	private Date jobCompletionDate; //  1/1/2012 13:00,
	private Date jobSubmissionDate;  // 1/1/2012 12:00,
	
	private String resultUrl; // http://broker/resource/result123

	/**
	 * @return the resultUrl
	 */
	public String getResultUrl() {
		return resultUrl;
	}

	/**
	 * @param resultUrl the resultUrl to set
	 */
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
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
		this.jobStatus = jobStatus;
	}

	public SOCJobStatus(Date jobSubmissionDate) {
		super();
		this.jobSubmissionDate = jobSubmissionDate;
	}
}

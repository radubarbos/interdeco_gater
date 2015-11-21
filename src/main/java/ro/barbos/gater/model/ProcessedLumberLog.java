package ro.barbos.gater.model;

import java.util.Date;

public class ProcessedLumberLog {
	
	private Date processedDate;
	private User user;
	private LumberLog lumberLog;
	
	
	/**
	 * @return the processedDate
	 */
	public Date getProcessedDate() {
		return processedDate;
	}
	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the lumberLog
	 */
	public LumberLog getLumberLog() {
		return lumberLog;
	}
	/**
	 * @param lumberLog the lumberLog to set
	 */
	public void setLumberLog(LumberLog lumberLog) {
		this.lumberLog = lumberLog;
	}
	
	

}

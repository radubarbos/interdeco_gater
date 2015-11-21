package ro.barbos.gater.model;

import java.util.Date;

public class LumberLogStockEntry {

	private User user;
	private Date entryDate;
	private LumberLog lumberLog;
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
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}
	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
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

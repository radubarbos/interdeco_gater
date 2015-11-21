package ro.barbos.gater.dto;

import java.util.Date;

public class ProcessedLumberLogFilterDTO {

	private Date startDate;
	private Date endDate;
	private boolean includeEndDate = true;
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the includeEndDate
	 */
	public boolean isIncludeEndDate() {
		return includeEndDate;
	}
	/**
	 * @param includeEndDate the includeEndDate to set
	 */
	public void setIncludeEndDate(boolean includeEndDate) {
		this.includeEndDate = includeEndDate;
	}
	
	
	
}

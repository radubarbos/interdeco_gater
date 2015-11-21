package ro.barbos.gater.dto;

public class LumberStackInfoDTO {

	private LumberStackDTO stack;
	private Double totalVolume;
	private Long totalLumberLogs;
	/**
	 * @return the stack
	 */
	public LumberStackDTO getStack() {
		return stack;
	}
	/**
	 * @param stack the stack to set
	 */
	public void setStack(LumberStackDTO stack) {
		this.stack = stack;
	}
	/**
	 * @return the totalVolume
	 */
	public Double getTotalVolume() {
		return totalVolume;
	}
	/**
	 * @param totalVolume the totalVolume to set
	 */
	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}
	/**
	 * @return the totalLumberLogs
	 */
	public Long getTotalLumberLogs() {
		return totalLumberLogs;
	}
	/**
	 * @param totalLumberLogs the totalLumberLogs to set
	 */
	public void setTotalLumberLogs(Long totalLumberLogs) {
		this.totalLumberLogs = totalLumberLogs;
	}
	
	
	
	
}

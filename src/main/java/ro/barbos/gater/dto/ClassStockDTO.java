package ro.barbos.gater.dto;

public class ClassStockDTO {
	
	private Long id;
	private String name;
	private Double totalVolume;
	private Long totalLumberLogs;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

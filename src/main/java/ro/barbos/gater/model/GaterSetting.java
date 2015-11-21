package ro.barbos.gater.model;

import ro.barbos.gater.data.METRIC;

public class GaterSetting {

	private Long Id;
	private String name;
	private Double value;
	private METRIC metric;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
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
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	/**
	 * @return the metric
	 */
	public METRIC getMetric() {
		return metric;
	}
	/**
	 * @param metric the metric to set
	 */
	public void setMetric(METRIC metric) {
		this.metric = metric;
	}
	
	
	
}

package ro.barbos.gater.model;

import java.io.Serializable;

import ro.barbos.gater.data.METRIC;

public class LumberStack implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Double minimum;
	private Double maximum;
	private METRIC metric;
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
	 * @return the minimum
	 */
	public Double getMinimum() {
		return minimum;
	}
	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}
	/**
	 * @return the maximum
	 */
	public Double getMaximum() {
		return maximum;
	}
	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(Double maximum) {
		this.maximum = maximum;
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

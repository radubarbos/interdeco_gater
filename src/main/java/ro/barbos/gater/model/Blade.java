package ro.barbos.gater.model;

import ro.barbos.gater.data.METRIC;

public class Blade {

	private Long Id;
	private String name;
	private Double thick;
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
	 * @return the thick
	 */
	public Double getThick() {
		return thick;
	}
	/**
	 * @param thick the thick to set
	 */
	public void setThick(Double thick) {
		this.thick = thick;
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

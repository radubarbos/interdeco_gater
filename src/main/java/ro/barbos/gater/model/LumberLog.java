package ro.barbos.gater.model;

import java.io.Serializable;
import java.util.List;

import ro.barbos.gater.data.METRIC;

public class LumberLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private IDPlate plate;
	private Double smallRadius;
	private List<Double> mediumRadius;
	private Double bigRadius;
	private Double length;
	private Long realLength;
	private Double volume;
	private Double realVolume;
	private METRIC metric;
	private Long lumberClass;
	private Long lumberType;
	private LumberStack stack;
	private Long cutPlanId;
	
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
	 * @return the plate
	 */
	public IDPlate getPlate() {
		return plate;
	}
	/**
	 * @param plate the plate to set
	 */
	public void setPlate(IDPlate plate) {
		this.plate = plate;
	}
	/**
	 * @return the smallRadius
	 */
	public Double getSmallRadius() {
		return smallRadius;
	}
	/**
	 * @param smallRadius the smallRadius to set
	 */
	public void setSmallRadius(Double smallRadius) {
		this.smallRadius = smallRadius;
	}
	/**
	 * @return the mediumRadius
	 */
	public List<Double> getMediumRadius() {
		return mediumRadius;
	}
	/**
	 * @param mediumRadius the mediumRadius to set
	 */
	public void setMediumRadius(List<Double> mediumRadius) {
		this.mediumRadius = mediumRadius;
	}
	/**
	 * @return the bigRadius
	 */
	public Double getBigRadius() {
		return bigRadius;
	}
	/**
	 * @param bigRadius the bigRadius to set
	 */
	public void setBigRadius(Double bigRadius) {
		this.bigRadius = bigRadius;
	}
	/**
	 * @return the length
	 */
	public Double getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Double length) {
		this.length = length;
	}
	
	/**
	 * @return the volume
	 */
	public Double getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Double volume) {
		this.volume = volume;
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
	/**
	 * @return the lumberClass
	 */
	public Long getLumberClass() {
		return lumberClass;
	}
	/**
	 * @param lumberClass the lumberClass to set
	 */
	public void setLumberClass(Long lumberClass) {
		this.lumberClass = lumberClass;
	}
	/**
	 * @return the lumberType
	 */
	public Long getLumberType() {
		return lumberType;
	}
	/**
	 * @param lumberType the lumberType to set
	 */
	public void setLumberType(Long lumberType) {
		this.lumberType = lumberType;
	}
	/**
	 * @return the stack
	 */
	public LumberStack getStack() {
		return stack;
	}
	/**
	 * @param stack the stack to set
	 */
	public void setStack(LumberStack stack) {
		this.stack = stack;
	}
	/**
	 * @return the cutPlanId
	 */
	public Long getCutPlanId() {
		return cutPlanId;
	}
	/**
	 * @param cutPlanId the cutPlanId to set
	 */
	public void setCutPlanId(Long cutPlanId) {
		this.cutPlanId = cutPlanId;
	}
	/**
	 * @return the realLength
	 */
	public Long getRealLength() {
		return realLength;
	}
	/**
	 * @param realLength the realLength to set
	 */
	public void setRealLength(Long realLength) {
		this.realLength = realLength;
	}
	/**
	 * @return the realVolume
	 */
	public Double getRealVolume() {
		return realVolume;
	}
	/**
	 * @param realVolume the realVolume to set
	 */
	public void setRealVolume(Double realVolume) {
		this.realVolume = realVolume;
	}
	
	

}

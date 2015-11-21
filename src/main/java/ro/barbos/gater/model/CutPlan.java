package ro.barbos.gater.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.dto.ProductCutTargetDTO;

public class CutPlan implements Serializable, GeneralEntity {

	private Integer Id;
	private String name;
	private String description;
	private Date date;
	private Integer status = 1;
	private Double completed;
	List<ProductCutTargetDTO> cutDataInfo;
	List<CutPlanSenquence> cutDiagrams;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the completed
	 */
	public Double getCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Double completed) {
		this.completed = completed;
	}
	/**
	 * @return the cutDataInfo
	 */
	public List<ProductCutTargetDTO> getCutDataInfo() {
		return cutDataInfo;
	}
	/**
	 * @param cutDataInfo the cutDataInfo to set
	 */
	public void setCutDataInfo(List<ProductCutTargetDTO> cutDataInfo) {
		this.cutDataInfo = cutDataInfo;
	}
	/**
	 * @return the cutDiagrams
	 */
	public List<CutPlanSenquence> getCutDiagrams() {
		return cutDiagrams;
	}
	/**
	 * @param cutDiagrams the cutDiagrams to set
	 */
	public void setCutDiagrams(List<CutPlanSenquence> cutDiagrams) {
		this.cutDiagrams = cutDiagrams;
	}
	
	
	
}

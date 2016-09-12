package ro.barbos.gater.model;

import ro.barbos.gater.data.METRIC;

import java.io.Serializable;
import java.util.List;

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
    private int status;
    private Long supplierId;
    private Long transportCertifiateId;
    private Integer marginPercent;
    private Double marginVolume;
    private Double marginRealVolume;
    private Long transportEntryId;
    private Long costPerUnit;
    private Double cost;
    private Long marginCostPerUnit;
    private Double marginCost;


    public Double getMarginRealVolume() {
        return marginRealVolume;
    }

    public void setMarginRealVolume(Double marginRealVolume) {
        this.marginRealVolume = marginRealVolume;
    }

    public Double getMarginVolume() {
        return marginVolume;
    }

    public void setMarginVolume(Double marginVolume) {
        this.marginVolume = marginVolume;
    }

    public Integer getMarginPercent() {
        return marginPercent;
    }

    public void setMarginPercent(Integer marginPercent) {
        this.marginPercent = marginPercent;
    }

    public Long getTransportCertifiateId() {
        return transportCertifiateId;
    }

    public void setTransportCertifiateId(Long transportCertifiateId) {
        this.transportCertifiateId = transportCertifiateId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public Long getTransportEntryId() {
        return transportEntryId;
    }

    public void setTransportEntryId(Long transportEntryId) {
        this.transportEntryId = transportEntryId;
    }

    public Long getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(Long costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getMarginCost() {
        return marginCost;
    }

    public void setMarginCost(Double marginCost) {
        this.marginCost = marginCost;
    }

    public Long getMarginCostPerUnit() {
        return marginCostPerUnit;
    }

    public void setMarginCostPerUnit(Long marginCostPerUnit) {
        this.marginCostPerUnit = marginCostPerUnit;
    }
}

package ro.barbos.gui.tablemodel;

import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.data.MetricTools;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class StockRecord {

	private LumberLog lumberLog;
	private String stackLabel;
	private String label;
	private String smallRadius;
	private String mediumRadius;
	private String bigRadius;
	private String volum;
	private String length;
	private String type;
	private String lumberClass;
	private String userFullName;
	private String dateLabel;
	private String cutPlanName;
    private Double costPerUnit;

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd,M yyyy HH:mm:ss", ConfigLocalManager.locale);
	
	public StockRecord(LumberLog lumberLog) {
		updateLumberLog(lumberLog);
	}
	
	public  StockRecord(LumberLogStockEntry stockEntry) {
		this(stockEntry.getLumberLog());
		userFullName = stockEntry.getUser().getName();
		dateLabel = dateFormatter.format(stockEntry.getEntryDate());
	}
	
	public void updateLumberLog(LumberLog lumberLog) {
        this.lumberLog = lumberLog;
		
        numberFormatter.setMaximumFractionDigits(2);
		stackLabel = lumberLog.getStack().getName();
		label = lumberLog.getPlate().getLabel();
		smallRadius = lumberLog.getSmallRadius() + " mm";
		mediumRadius = lumberLog.getMediumRadius() + " mm";
		bigRadius = lumberLog.getBigRadius() + " mm";
		volum = MetricFormatter.formatVolume(MetricTools.toMeterCubs(lumberLog.getVolume(), METRIC.MILIMETER));
		length = lumberLog.getLength() + " mm";
		type = GUIUtil.types[lumberLog.getLumberType().intValue()-1];
		lumberClass = GUIUtil.lumberClass[lumberLog.getLumberClass().intValue()-1];
		cutPlanName = lumberLog.getCutPlanId() != null && lumberLog.getCutPlanId() != 0 ? "Da": "Nu";
        costPerUnit = new Double(lumberLog.getCostPerUnit() / 100);
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

	/**
	 * @return the stackLabel
	 */
	public String getStackLabel() {
		return stackLabel;
	}

	/**
	 * @param stackLabel the stackLabel to set
	 */
	public void setStackLabel(String stackLabel) {
		this.stackLabel = stackLabel;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the smallRadius
	 */
	public String getSmallRadius() {
		return smallRadius;
	}

	/**
	 * @param smallRadius the smallRadius to set
	 */
	public void setSmallRadius(String smallRadius) {
		this.smallRadius = smallRadius;
	}

	/**
	 * @return the mediumRadius
	 */
	public String getMediumRadius() {
		return mediumRadius;
	}

	/**
	 * @param mediumRadius the mediumRadius to set
	 */
	public void setMediumRadius(String mediumRadius) {
		this.mediumRadius = mediumRadius;
	}

	/**
	 * @return the bigRadius
	 */
	public String getBigRadius() {
		return bigRadius;
	}

	/**
	 * @param bigRadius the bigRadius to set
	 */
	public void setBigRadius(String bigRadius) {
		this.bigRadius = bigRadius;
	}

	/**
	 * @return the volum
	 */
	public String getVolum() {
		return volum;
	}

	/**
	 * @param volum the volum to set
	 */
	public void setVolum(String volum) {
		this.volum = volum;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the lumberClass
	 */
	public String getLumberClass() {
		return lumberClass;
	}

	/**
	 * @param lumberClass the lumberClass to set
	 */
	public void setLumberClass(String lumberClass) {
		this.lumberClass = lumberClass;
	}

	/**
	 * @return the userFullName
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * @param userFullName the userFullName to set
	 */
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	/**
	 * @return the dateLabel
	 */
	public String getDateLabel() {
		return dateLabel;
	}

	/**
	 * @param dateLabel the dateLabel to set
	 */
	public void setDateLabel(String dateLabel) {
		this.dateLabel = dateLabel;
	}

	/**
	 * @return the cutPlanName
	 */
	public String getCutPlanName() {
		return cutPlanName;
	}

	/**
	 * @param cutPlanName the cutPlanName to set
	 */
	public void setCutPlanName(String cutPlanName) {
		this.cutPlanName = cutPlanName;
	}

    public Double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(Double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }
}

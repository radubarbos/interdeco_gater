package ro.barbos.gui.tablemodel;

import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.data.MetricTools;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;
import java.util.Locale;

public class ReceiveRecord {

	private String stackLabel;
	private String label;
	private String smallRadius;
	//private String mediumRadius;
	private String bigRadius;
	private String volum;
	private String length;
	private String type;
	private String lumberClass;
	
	
	private NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));
	
	public ReceiveRecord(LumberLog lumberLog, LumberStack stack) {
		
		numberFormatter.setMaximumFractionDigits(2);
		
		stackLabel = stack.getName();
		label = lumberLog.getPlate().getLabel();
		smallRadius = lumberLog.getSmallRadius() + " mm";
		//mediumRadius = lumberLog.getMediumRadius() + " mm";
		bigRadius = lumberLog.getBigRadius() + " mm";
		volum = MetricFormatter.formatVolume(MetricTools.toMeterCubs(lumberLog.getVolume(), METRIC.MILIMETER));
		length = lumberLog.getLength() + " mm";
		type = GUIUtil.types[lumberLog.getLumberType().intValue()-1];
		lumberClass = GUIUtil.lumberClass[lumberLog.getLumberClass().intValue()-1];
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
	 *//*
	public String getMediumRadius() {
		return mediumRadius;
	}
	*//**
	 * @param mediumRadius the mediumRadius to set
	 *//*
	public void setMediumRadius(String mediumRadius) {
		this.mediumRadius = mediumRadius;
	}*/
	/**
	 * @return the bbigRadius
	 */
	public String getBigRadius() {
		return bigRadius;
	}
	/**
	 * @param bigRadius the bbigRadius to set
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
	
	
	
}

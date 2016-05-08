package ro.barbos.gui.tablemodel;

import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.data.MetricTools;
import ro.barbos.gater.model.ProcessedLumberLog;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProcessedLumberLogRecord {
	
	private SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
	private NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));

	private ProcessedLumberLog processedLumberLog;
	
	private String processedDate;
	private String smallRadius;
	private String bigRadius;
	private String volum;
	private String length;
	private String type;
	private String lumberClass;
	private String user;
	private String plateLabel;
	
	public ProcessedLumberLogRecord(ProcessedLumberLog processedLumberLog) {
		this.processedLumberLog = processedLumberLog;
		this.processedDate = format.format(processedLumberLog.getProcessedDate());
		smallRadius = processedLumberLog.getLumberLog().getSmallRadius() + " mm";
		bigRadius = processedLumberLog.getLumberLog().getBigRadius() + " mm";
		volum = MetricFormatter.formatVolume(MetricTools.toMeterCubs(processedLumberLog.getLumberLog().getVolume(), METRIC.MILIMETER));
		length = processedLumberLog.getLumberLog().getLength() + " mm";
		type = GUIUtil.types[processedLumberLog.getLumberLog().getLumberType().intValue()-1];
		lumberClass = GUIUtil.lumberClass[processedLumberLog.getLumberLog().getLumberClass().intValue()-1];
		user = processedLumberLog.getUser().getName();
		if(processedLumberLog.getLumberLog().getPlate() != null) {
			plateLabel  = processedLumberLog.getLumberLog().getPlate().getLabel();
		}
	}

	/**
	 * @return the processedLumberLog
	 */
	public ProcessedLumberLog getProcessedLumberLog() {
		return processedLumberLog;
	}

	/**
	 * @param processedLumberLog the processedLumberLog to set
	 */
	public void setProcessedLumberLog(ProcessedLumberLog processedLumberLog) {
		this.processedLumberLog = processedLumberLog;
	}

	/**
	 * @return the processedDate
	 */
	public String getProcessedDate() {
		return processedDate;
	}

	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(String processedDate) {
		this.processedDate = processedDate;
	}

	/**
	 * @return the format
	 */
	public SimpleDateFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	/**
	 * @return the numberFormatter
	 */
	public NumberFormat getNumberFormatter() {
		return numberFormatter;
	}

	/**
	 * @param numberFormatter the numberFormatter to set
	 */
	public void setNumberFormatter(NumberFormat numberFormatter) {
		this.numberFormatter = numberFormatter;
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
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the plateLabel
	 */
	public String getPlateLabel() {
		return plateLabel;
	}

	/**
	 * @param plateLabel the plateLabel to set
	 */
	public void setPlateLabel(String plateLabel) {
		this.plateLabel = plateLabel;
	}
	
	
	
	
	
}

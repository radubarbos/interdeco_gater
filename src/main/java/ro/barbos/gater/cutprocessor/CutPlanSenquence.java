package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;

public class CutPlanSenquence {

	private LumberLog lumberLog;
	private CutDiagram cutDiagram;
	private double percentage;
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
	 * @return the cutDiagram
	 */
	public CutDiagram getCutDiagram() {
		return cutDiagram;
	}
	/**
	 * @param cutDiagram the cutDiagram to set
	 */
	public void setCutDiagram(CutDiagram cutDiagram) {
		this.cutDiagram = cutDiagram;
	}
	/**
	 * @return the percentage
	 */
	public double getPercentage() {
		return percentage;
	}
	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
}

package ro.barbos.gater.cutprocessor;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;

public class LumberLogCutContext {

	private LumberLog lumberLog;
	private double radius;
	private double insideSquareSide;
	private double halfSquare;
	
	public LumberLogCutContext(LumberLog lumberLog, double edgeTolerance) {
		this.lumberLog = lumberLog;
		radius = lumberLog.getSmallRadius()/2 - edgeTolerance;
		insideSquareSide = radius * Math.pow(2, 0.5);
		halfSquare = insideSquareSide/2;
	}
	
	public void setCutInfo(CutDiagram diagram) {
		diagram.cutInfo.smallEndArea = Math.PI * radius * radius;
		diagram.cutInfo.lumberLogVolume = lumberLog.getVolume();
		diagram.debugSquare = insideSquareSide;
	}
	
	public CutContext createCutContext(int horizontalCut, double yPosition) {
		CutContext cutContext = new CutContext();
		cutContext.lumberLogRadius = radius;
		cutContext.yPosition = yPosition;
		cutContext.lengthLimit = lumberLog.getRealLength();
		cutContext.currentCut = horizontalCut;
		cutContext.innerSquare = insideSquareSide;
		return cutContext;
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
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the insideSquareSide
	 */
	public double getInsideSquareSide() {
		return insideSquareSide;
	}

	/**
	 * @param insideSquareSide the insideSquareSide to set
	 */
	public void setInsideSquareSide(double insideSquareSide) {
		this.insideSquareSide = insideSquareSide;
	}

	/**
	 * @return the halfSquare
	 */
	public double getHalfSquare() {
		return halfSquare;
	}

	/**
	 * @param halfSquare the halfSquare to set
	 */
	public void setHalfSquare(double halfSquare) {
		this.halfSquare = halfSquare;
	}
	
	public double getLumberLogDiameter() {
		return lumberLog.getSmallRadius();
	}
}

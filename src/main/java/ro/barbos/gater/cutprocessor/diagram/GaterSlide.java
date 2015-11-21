package ro.barbos.gater.cutprocessor.diagram;

import java.awt.Color;

import ro.barbos.gater.cutprocessor.CutPhase;

public class GaterSlide implements GaterOperation {

	public double x = 0;
	public double y = 0;
	public double height = 0;
	public double rightLimit = 10000000D;
	public int pieces = 0;
	public double pieceWidth = 0;
	public Color color;
	public CutPhase phase;
	
	public boolean rotate() {
		return false;
	}
}

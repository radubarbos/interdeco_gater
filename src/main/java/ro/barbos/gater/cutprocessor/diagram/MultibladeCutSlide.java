package ro.barbos.gater.cutprocessor.diagram;

import java.awt.Color;
import java.io.Serializable;

import ro.barbos.gater.cutprocessor.CutPhase;

public class MultibladeCutSlide implements Serializable {

	public double x = 0;
	public double y = 0;
	public int pieces = 0;
	public double width = 0;
	public double height = 0;
	public double pieceWidth = 0; //actualy is the thick
	public Color color;
	public CutPhase phase;
}

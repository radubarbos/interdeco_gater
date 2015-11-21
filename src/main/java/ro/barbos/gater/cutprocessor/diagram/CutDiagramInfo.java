package ro.barbos.gater.cutprocessor.diagram;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CutDiagramInfo implements Serializable {

	public double smallEndArea = 0d;
	public double lumberLogVolume = 0d;
	public double usedCutArea = 0d;
	public double cutLayoutEfficency = 0;
	public double cutVolumeEfficency = 0;
	public double cutVolume = 0;
	public Map<String, Integer> cutPieces = new HashMap<>();
	public Map<String, Color> colors = new HashMap<>();
}

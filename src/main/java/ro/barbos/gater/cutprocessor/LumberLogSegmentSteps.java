package ro.barbos.gater.cutprocessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LumberLogSegmentSteps implements Serializable{

	public List<Double> cuts = new ArrayList<>();
	
	public void addCut(Double cut) {
		cuts.add(cut);
	}
	
	public void setCuts(List<Double> cuts) {
		this.cuts = cuts;
	}
	
}

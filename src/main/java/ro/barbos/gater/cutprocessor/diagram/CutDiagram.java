package ro.barbos.gater.cutprocessor.diagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ro.barbos.gater.cutprocessor.LumberLogCutSteps;
import ro.barbos.gater.model.GeneralEntity;

public class CutDiagram implements Serializable, GeneralEntity {

	public List<GaterOperation> gaterCutFlow = new ArrayList<>();
	public List<MultibladeCutSlide> multiBladeSlides = new ArrayList<>();
	public CutDiagramInfo cutInfo = new CutDiagramInfo();
	public LumberLogCutSteps steps = new LumberLogCutSteps();
	
	public Double debugSquare = null;
}

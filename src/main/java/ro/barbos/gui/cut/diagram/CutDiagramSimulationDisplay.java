package ro.barbos.gui.cut.diagram;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CutDiagramSimulationDisplay extends JInternalFrame {
	
	private boolean ignoreEmptySegments = true;

	public CutDiagramSimulationDisplay(LumberLog lumberLog, CutDiagram diagram) {
		setTitle("Pasi de taiere pentru busteanul " + lumberLog.getPlate().getLabel());
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setSize(500, 500);
		Container panel = getContentPane();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(new JLabel("Taiere bustean " + lumberLog.getPlate().getLabel()));
		
		boolean left = diagram.steps.getLeft() != null && (ignoreEmptySegments && diagram.steps.getLeft().cuts.size()>1);
		boolean bottom = diagram.steps.getBottom() != null && (ignoreEmptySegments && diagram.steps.getBottom().cuts.size()>0);
		boolean right = diagram.steps.getRight() != null && (ignoreEmptySegments && diagram.steps.getRight().cuts.size()>1);
		boolean top = diagram.steps.getTop() != null && (ignoreEmptySegments && diagram.steps.getTop().cuts.size()>0);
		boolean multiBlade = diagram.steps.getMultiBlade() != null;
		
		int step = 1;
		if(left) {
			List<Double> segmentSteps = diagram.steps.getLeft().cuts;
			for(Double cut: segmentSteps) {
				add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
				step++;
			}
			
		}
		if(left && right) {
			add(new JLabel("Pasul "  + step +  " : Rotiti 180 grade: "));
			step++;
		}
		else if(left && bottom) {
			add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
			step++;
		}
		
		if(right) {
			List<Double> segmentSteps = diagram.steps.getRight().cuts;
			for(Double cut: segmentSteps) {
				add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
				step++;
			}
		}
		if(right && (bottom || top)) {
			add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
			step++;
		}
		if(bottom) {
			List<Double> segmentSteps = diagram.steps.getBottom().cuts;
			for(Double cut: segmentSteps) {
				add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
				step++;
			}
			add(new JLabel("Pasul "  + step +  " : Rotiti 180 grade: "));
			step++;
		}
		//show top segment
		if(top) {
			List<Double> segmentSteps = diagram.steps.getTop().cuts;
			for(Double cut: segmentSteps) {
				add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
				step++;
			}
		}
		if(multiBlade) {
			List<Double> segmentSteps = diagram.steps.getMultiBlade().cuts;
			double endMultiBlade = segmentSteps.size();
			if(bottom) endMultiBlade--;
			for(int index =0; index < endMultiBlade; index++) {
				add(new JLabel("Pasul "  + step +  " : Taiati la: " + segmentSteps.get(index)));
				step++;
			}
		}
		
		
		add(Box.createVerticalGlue());
		
		GUIUtil.container.add(this, 100000);
	}
	
	
}

/*int step = 1;
if(left) {
	List<Double> segmentSteps = diagram.steps.getLeft().cuts;
	for(Double cut: segmentSteps) {
		add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
		step++;
	}
	add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
	step++;
}
if(bottom) {
	List<Double> segmentSteps = diagram.steps.getBottom().cuts;
	for(Double cut: segmentSteps) {
		add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
		step++;
	}
	add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
	step++;
}
if(left && !bottom) {
	add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
	step++;
}
if(right) {
	List<Double> segmentSteps = diagram.steps.getRight().cuts;
	for(Double cut: segmentSteps) {
		add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
		step++;
	}
	add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
	step++;
}
if(!right && (left || bottom)) {
	add(new JLabel("Pasul "  + step +  " : Rotiti dreapta 90 grade: "));
	step++;
}
//show top segment
if(top) {
	List<Double> segmentSteps = diagram.steps.getTop().cuts;
	for(Double cut: segmentSteps) {
		add(new JLabel("Pasul "  + step +  " : Taiati la: " + cut));
		step++;
	}
}*/

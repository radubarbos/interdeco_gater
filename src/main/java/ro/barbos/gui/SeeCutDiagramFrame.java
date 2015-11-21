package ro.barbos.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;

public class SeeCutDiagramFrame extends JInternalFrame {

	public SeeCutDiagramFrame(LumberLog lumberLog, CutDiagram diagram) {
		super();	
		setTitle("Diagrama taiere pentru busteanul " + lumberLog.getPlate().getLabel());
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		CutDiagramPaintPanel painter = new CutDiagramPaintPanel(lumberLog, diagram);
		
		add(painter, BorderLayout.CENTER);
		
		setLocation(0, 0);
		setSize(painter.getPreferredSize().width+100, painter.getPreferredSize().height+100);
		
		GUIUtil.container.add(this, 100000);
		setVisible(true);
	}
}

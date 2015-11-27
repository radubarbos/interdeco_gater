package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SeeCutDiagramFrame extends JInternalFrame {

	public SeeCutDiagramFrame(LumberLog lumberLog, CutDiagram diagram, ActionListener actionListener) {
		super();	
		setTitle("Diagrama taiere pentru busteanul " + lumberLog.getPlate().getLabel());
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		CutDiagramPaintPanel painter = new CutDiagramPaintPanel(lumberLog, diagram);
		
		add(painter, BorderLayout.CENTER);

        if(actionListener != null) {
            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            JButton btn = new JButton("Ataseaza la plan");
            btn.setActionCommand("ADDTOPLAN");
            btn.addActionListener(actionListener);
            southPanel.add(btn);
            add(southPanel, BorderLayout.SOUTH);
        }
		
		setLocation(0, 0);
		setSize(painter.getPreferredSize().width+100, painter.getPreferredSize().height+100);
		
		GUIUtil.container.add(this, 100000);
		setVisible(true);
	}
}

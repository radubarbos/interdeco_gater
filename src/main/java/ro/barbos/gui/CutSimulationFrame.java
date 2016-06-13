package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.CuterProcessor;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.cut.diagram.CutDiagramPaintPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.List;

public class CutSimulationFrame extends GeneralFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private CutSimulationPanel simulation;
	private JPanel painterDiagrams;
	
	private CutPlanSettingsPanel settingsPanel = new CutPlanSettingsPanel();
	
public CutSimulationFrame() {
		
        super();	
		setTitle("Simulare taiere");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		 /*JButton edit = new JButton(new ImageIcon(
					GUITools.getImage("resources/edit24.png")));*/
		JButton cutLumber = new JButton("Taie bustean");
		cutLumber.setToolTipText("Simulare taiere");
		cutLumber.setActionCommand("CUT");
		cutLumber.addActionListener(this);
		cutLumber.setFocusPainted(false);
		cutLumber.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(cutLumber);
		JButton print = new JButton("Printeaza");
		print.setToolTipText("Printeaza");
		print.setActionCommand("PRINT");
		print.addActionListener(this);
		print.setFocusPainted(false);
		print.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		toolbar.add(print);
		
		painterDiagrams = new JPanel();
		painterDiagrams.setLayout(new BoxLayout(painterDiagrams, BoxLayout.Y_AXIS));
		
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(painterDiagrams), BorderLayout.CENTER);
	}

public void actionPerformed(ActionEvent eve) {
	String command = eve.getActionCommand();
	if(command.equals("CUT")) {
		JButton buttonOk = new JButton("Simulator");
		buttonOk.setActionCommand("SAVE_ADD_LOG");
		buttonOk.addActionListener(this);
		JButton buttonCancel = new JButton("Anuleaza");
		buttonCancel.setActionCommand("CANCEL_ADD_LOG");
		buttonCancel.addActionListener(this);
		simulation = new CutSimulationPanel();
		JOptionPane.showOptionDialog(GUIUtil.container, simulation, "Simulare taiere", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new JButton[]{buttonOk, buttonCancel}, buttonCancel);
		
	}
	else if(command.equals("CANCEL_ADD_LOG")) {
		Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
		if(w!=null) w.dispose();
	}
	else if(command.equals("SAVE_ADD_LOG")) {
		if(!simulation.validateData()) {
			return;
		}
		LumberLog lumberLog = simulation.getLumberLog();
		List<Product> products = simulation.getProducts();
		List<Integer> pieces = simulation.getPieces();
		Window w = SwingUtilities.getWindowAncestor((JButton)eve.getSource());
		if(w!=null) w.dispose();
        boolean valid = products.isEmpty();
        for(Product product: products) {
            if(product.getLength() <= lumberLog.getLength().longValue()) {
                valid = true;
            }
        }
        if(!valid) {
            JOptionPane.showMessageDialog(GUIUtil.container, "Nu se poate taia busteanul cu aceste produse.\n Sunt mai lungi ca busteanul.");
            return;
        }
		List<CutDiagram> diagrams = new CuterProcessor().cutLumberLog(lumberLog, products, pieces);
		painterDiagrams.removeAll();
		for(CutDiagram diagram: diagrams) {
			CutDiagramPaintPanel painter = new CutDiagramPaintPanel(lumberLog, diagram);
			painterDiagrams.add(painter);
		}
		painterDiagrams.repaint();
		painterDiagrams.revalidate();
	}
	else if(command.equals("PRINT")) {
		int diagrams = painterDiagrams.getComponentCount();
		if(diagrams == 0) {
			JOptionPane.showMessageDialog(GUIUtil.container, "Nu exista nici o diagrama de printat");
		}
		else {
			JOptionPane.showConfirmDialog(GUIUtil.container, settingsPanel, "Alegeti diagrama de printat", -1);
			if(settingsPanel.getCutStrategySettings().size() == 0) {
				JOptionPane.showMessageDialog(GUIUtil.container, "Nu sa selectat nici o diagrama de printat");	
				return;
			}
			List<Integer> indexes = settingsPanel.getCutStrategyIndexes();
			PrinterJob job = PrinterJob.getPrinterJob();
			if(job.printDialog())
			{
				try
				{
					for(Integer index: indexes) {
						job.setPrintable((Printable)painterDiagrams.getComponent(index));
					}
					job.print();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
}
	
	@Override
	public String getFrameCode() {
		return GUIUtil.CUT_SIMULATION_KEY;
	}

	@Override
	public ImageIcon getFrameIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getIconifiedIcon() {
		Image image = GUITools.getImage("/ro/barbos/gui/resources/product32.png");
		return new ImageIcon(image);
	}
	
}

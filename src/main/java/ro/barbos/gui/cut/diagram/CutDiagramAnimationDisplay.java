package ro.barbos.gui.cut.diagram;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.diagram.GaterCutStep;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gui.GUITools;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CutDiagramAnimationDisplay extends JInternalFrame implements ActionListener, InternalFrameListener {

	private CutDiagram diagram;
	private LumberLog lumberLog;
	private CutDiagramAnimationPanel display;
	private JPanel showStepsPanel;

	
	public CutDiagramAnimationDisplay(CutDiagram diagram, LumberLog lumberLog) {
		
		this.diagram = diagram;
		this.lumberLog = lumberLog;
		
		setTitle("Animatie");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setLocation(0, 0);
		setSize(GUIUtil.container.getWidth(), GUIUtil.container.getHeight());
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		JButton but = new JButton(new ImageIcon(
				GUITools.getImage("resources/play32.png")));
		but.setFocusPainted(false);
		but.setContentAreaFilled(false);
		but.setBorder(null);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Start");
		but.setActionCommand("PLAY");
		but.addActionListener(this);
		toolbar.add(but);
		but = new JButton(new ImageIcon(
				GUITools.getImage("resources/pause32.png")));
		but.setFocusPainted(false);
		but.setContentAreaFilled(false);
		but.setBorder(null);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Pauza");
		but.setActionCommand("PAUSE");
		but.addActionListener(this);
		toolbar.add(but);
		but = new JButton(new ImageIcon(
				GUITools.getImage("resources/nextplay32.png")));
		but.setFocusPainted(false);
		but.setContentAreaFilled(false);
		but.setBorder(null);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Urmatorul");
		but.setActionCommand("NEXT");
		but.addActionListener(this);
		toolbar.add(but);
		
		display = new CutDiagramAnimationPanel(lumberLog, diagram,  this);
		this.addInternalFrameListener(this);
		
		showStepsPanel = new JPanel(new GridLayout(diagram.steps.getStepSequence().size(), 1));
		
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(display, BorderLayout.CENTER);
		getContentPane().add(showStepsPanel, BorderLayout.EAST);
		GUIUtil.container.add(this);
		play();
	}
	
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("PLAY")) {
			play();
		}
		else if(command.equals("PAUSE")) {
			pause();
		}
	}
	
	public void displayGaterStep(GaterCutStep step, int index) {
		if(index == -1) {
			showStepsPanel.removeAll();
		}
		else {
			if(showStepsPanel.getComponentCount() > 0) {
				resetPreviousStepDisplay();
			}
			JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JLabel label = new JLabel();
			if(!step.isRotate) {
				label.setText("Pasul " +(index+1)+". Taiat la " + step.value);
			}
			else {
				label.setText("Pasul " +(index+1)+".Rotire " + step.value + " grade");
			}
			label.setForeground(Color.red);
			Font font = label.getFont();
			font = font.deriveFont(Font.ITALIC);
			label.setFont(font);
			row.add(label);
			showStepsPanel.add(row);
		}
		showStepsPanel.revalidate();
		showStepsPanel.repaint();
	}
	
	private void resetPreviousStepDisplay() {
	 JLabel label = (JLabel)((JPanel)showStepsPanel.getComponent(showStepsPanel.getComponentCount() - 1)).getComponent(0);
	 label.setForeground(Color.black);
	 Font font = label.getFont();
		font = font.deriveFont(Font.PLAIN);
		label.setFont(font);
	}
	
	private void play() {
		display.play();
	}
	
	private void pause() {
		display.pause();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameActivated(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosed(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		display.pause();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosing(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		display.pause();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeactivated(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeiconified(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameIconified(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameOpened(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}

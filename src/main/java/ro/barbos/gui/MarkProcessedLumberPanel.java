package ro.barbos.gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ro.barbos.gater.model.LumberLog;

public class MarkProcessedLumberPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private LumberLog lumberLog;
	private JTextArea messageArea;

	public MarkProcessedLumberPanel(LumberLog lumberLog) {
		this.lumberLog = lumberLog;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Placuta:", 100));
		panel.add(new JLabel(lumberLog.getPlate().getLabel()));
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Stiva:", 100));
		panel.add(new JLabel(lumberLog.getStack().getName()));
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Mesaj:", 100));
		messageArea = new JTextArea(5, 20);
		messageArea.setWrapStyleWord(true);
		panel.add(new JScrollPane(messageArea));
		add(panel);
	}
	
	public LumberLog getLumberLog() {
		return lumberLog;
	}
	
	public String getMessage() {
		return messageArea.getText();
	}
	
}

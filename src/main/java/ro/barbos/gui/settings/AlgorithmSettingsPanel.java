package ro.barbos.gui.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.dao.BladeDAO;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.Blade;
import ro.barbos.gui.GUIUtil;

public class AlgorithmSettingsPanel extends JPanel implements ActionListener {

	JCheckBox lengthOptimization;
	
	public AlgorithmSettingsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setMaximumSize(new Dimension(10000,30));
		panel.add(createLabel("Optimizare lungime:"));
		lengthOptimization = new JCheckBox();
		lengthOptimization.setSelected(CutterSettings.DO_LENGTH_OPTIMIZATION);
		panel.add(lengthOptimization);
		add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton save = new JButton("Save");
		save.setActionCommand("SAVE");
		save.addActionListener(this);
		panel.add(save);
		add(panel);
		
		add(Box.createVerticalGlue());
		add(Box.createVerticalStrut(20));
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
		add(Box.createVerticalGlue());
	}
	
	private JLabel createLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setPreferredSize(new Dimension(200, label.getPreferredSize().height));
		return label;
	}
	
	@Override
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("SAVE")) {
			int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa salvati", "Confirmati salvare", JOptionPane.YES_NO_OPTION);
            if(ras == JOptionPane.YES_OPTION) {
				List<Blade> updated = new ArrayList<>();//.updateBlades(newBlades);
				if(updated == null) {
					JOptionPane.showMessageDialog(GUIUtil.container, "Salvarea a esuat", "Nu se poate salva", JOptionPane.ERROR_MESSAGE);
				}
				else {
					CutterSettings.DO_LENGTH_OPTIMIZATION = lengthOptimization.isSelected();
				}
			}
		}
	}
}

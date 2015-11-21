package ro.barbos.gui.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.dao.BladeDAO;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.Blade;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUIUtil;

public class BladeSettingsPanel extends JPanel implements ActionListener {
	
	private List<Blade> blades;

	public BladeSettingsPanel(List<Blade> blades) {
		this.blades = blades;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(Blade blade: blades) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setMaximumSize(new Dimension(10000,30));
			panel.add(GUIFactory.createLabel(blade.getName()+":", 200));
			panel.add(GUIFactory.createDecimalNumberInput(blade.getThick(), 0D, 9999D, 80));
			JComboBox mCombo = new JComboBox<String>(GUIUtil.metric);
			mCombo.setSelectedIndex(blade.getMetric().ordinal());
			mCombo.setPreferredSize(new Dimension(50, mCombo.getPreferredSize().height));
			panel.add(mCombo);
			add(panel);
		}	
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
	
	@Override
	public void actionPerformed(ActionEvent eve) {
		String command = eve.getActionCommand();
		if(command.equals("SAVE")) {
			boolean valid = true;
			List<Blade> newBlades = new ArrayList<>();
			for(int index = 0; index < blades.size(); index++) {
				JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
				JFormattedTextField value = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(1);
				JComboBox<String> metric = (JComboBox<String>)((JPanel)getComponent(index)).getComponent(2);
				label.setForeground(Color.black);
				if(value.getValue() == null) {
					valid = false;
					label.setForeground(Color.red);
				}
				Blade blade = new Blade();
				blade.setId(blades.get(index).getId());
				blade.setThick((Double)value.getValue());
				blade.setMetric(METRIC.getById(metric.getSelectedIndex()+1));
				newBlades.add(blade);
			}
			if(!valid) {
				return;
			}
			int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa salvati", "Confirmati salvare", JOptionPane.YES_NO_OPTION);
            if(ras == JOptionPane.YES_OPTION) {
				List<Blade> updated = BladeDAO.updateBlades(newBlades);
				if(updated == null) {
					JOptionPane.showMessageDialog(GUIUtil.container, "Salvarea a esuat", "Nu se poate salva", JOptionPane.ERROR_MESSAGE);
				}
				else {
					CutterSettings.GATER_THICK = updated.get(0).getThick().doubleValue();
					CutterSettings.MULTIBLADE = updated.get(1).getThick().doubleValue();
				}
			}
		}
	}
}

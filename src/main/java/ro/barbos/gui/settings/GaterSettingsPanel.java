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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.dao.GaterSettingDAO;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gui.GUIFactory;
import ro.barbos.gui.GUIUtil;

public class GaterSettingsPanel extends JPanel implements ActionListener {

	private List<GaterSetting> settings;
	
	public GaterSettingsPanel(List<GaterSetting> settings) {
		this.settings = settings;
		
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for(GaterSetting setting: settings) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setMaximumSize(new Dimension(10000,30));
			panel.add(GUIFactory.createLabel(setting.getName()+":", 200));
			panel.add(GUIFactory.createDecimalNumberInput(setting.getValue(), 0D, 9999D, 80));
			JComboBox mCombo = new JComboBox<String>(GUIUtil.metric);
			mCombo.setSelectedIndex(setting.getMetric().ordinal());
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
			List<GaterSetting> newSettings = new ArrayList<>();
			for(int index = 0; index < settings.size(); index++) {
				JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
				JFormattedTextField value = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(1);
				JComboBox<String> metric = (JComboBox<String>)((JPanel)getComponent(index)).getComponent(2);
				label.setForeground(Color.black);
				if(value.getValue() == null) {
					valid = false;
					label.setForeground(Color.red);
				}
				GaterSetting setting = new GaterSetting();
				setting.setId(settings.get(index).getId());
				setting.setName(label.getText().substring(0, label.getText().length()-1));
				setting.setValue((Double)value.getValue());
				setting.setMetric(METRIC.getById(metric.getSelectedIndex()+1));
				newSettings.add(setting);
			}
			if(!valid) {
				return;
			}
			int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa salvati", "Confirmati salvare", JOptionPane.YES_NO_OPTION);
            if(ras == JOptionPane.YES_OPTION) {
				List<GaterSetting> updated = GaterSettingDAO.updateSettings(newSettings);
				if(updated == null) {
					JOptionPane.showMessageDialog(GUIUtil.container, "Salvarea a esuat", "Nu se poate salva", JOptionPane.ERROR_MESSAGE);
				}
				else {
					for(GaterSetting setting: updated) {
						if(setting.getName().startsWith("Su")) {
							CutterSettings.TOP_START = setting.getValue().doubleValue();
						}
						else if(setting.getName().startsWith("Jo")) {
							CutterSettings.BOTTOM_END = setting.getValue().doubleValue();
						}
						else if(setting.getName().startsWith("Li")) {
							CutterSettings.BOTTOM_OFFSET = setting.getValue().doubleValue();
						}
						else if(setting.getName().startsWith("Mul")) {
							CutterSettings.MULTIBLADE_TOLERANCE = setting.getValue().doubleValue();
						}
						else if(setting.getName().startsWith("Tol")) {
							CutterSettings.EDGE_TOLERANCE = setting.getValue().doubleValue();
						}
					}
				}
			}
		}
	}

}

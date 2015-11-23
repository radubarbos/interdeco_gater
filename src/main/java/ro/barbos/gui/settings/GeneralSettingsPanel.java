package ro.barbos.gui.settings;

import ro.barbos.gater.dao.GaterSettingDAO;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gater.stock.StockSettings;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 11/23/2015.
 */
public class GeneralSettingsPanel extends JPanel implements ActionListener {

    JCheckBox lumberMeasureMiddle;

    public GeneralSettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setMaximumSize(new Dimension(10000,30));
        panel.add(createLabel("Masurare mijloc:"));
        lumberMeasureMiddle = new JCheckBox();
        lumberMeasureMiddle.setSelected(StockSettings.MEASURE_MIDDLE_ONCE);
        panel.add(lumberMeasureMiddle);
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
                List<GaterSetting> gaterSettings = GaterSettingDAO.getSettings();
                List<GaterSetting> updatedSetting = new ArrayList<GaterSetting>();
                for(GaterSetting setting: gaterSettings) {
                    if(setting.getName().equals("MEASURE_MIDDLE_ONCE")) {
                        setting.setValue(lumberMeasureMiddle.isSelected() ? 1d : 0d);
                        updatedSetting.add(setting);
                    }
                }
                List<GaterSetting> updated = GaterSettingDAO.updateSettings(updatedSetting);
                if(updated == null) {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Salvarea a esuat", "Nu se poate salva", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    StockSettings.MEASURE_MIDDLE_ONCE = lumberMeasureMiddle.isSelected();
                }
            }
        }
    }
}

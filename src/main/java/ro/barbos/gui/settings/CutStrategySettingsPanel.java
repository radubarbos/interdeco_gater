package ro.barbos.gui.settings;

import ro.barbos.gater.cutprocessor.strategy.CutStrategyType;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gui.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by radu on 3/15/2016.
 */
public class CutStrategySettingsPanel extends JPanel implements ActionListener {

    JCheckBox simpleVerticalCutStrategyChk;
    JCheckBox simple2VerticalCutStrategyChk;
    JCheckBox oneRotateCutStrategyChk;
    JCheckBox bestMultibladeMatchCutStrategyChk;
    JCheckBox noMultibladeCutStrategyChk;
    JCheckBox oneRotate180CutStrategyChk;

    public CutStrategySettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        simpleVerticalCutStrategyChk = new JCheckBox("Strategie verticala simpla", CutStrategyType.SIMPLE_VERTICAL.isEnabled());
        add(simpleVerticalCutStrategyChk);
        simple2VerticalCutStrategyChk = new JCheckBox("Strategie verticala simpla2", CutStrategyType.BEST_MATCH_VERTICAL.isEnabled());
        add(simple2VerticalCutStrategyChk);
        oneRotateCutStrategyChk = new JCheckBox("Strategie cu o rotire", CutStrategyType.ROTATE_ONE.isEnabled());
        add(oneRotateCutStrategyChk);
        bestMultibladeMatchCutStrategyChk = new JCheckBox("Strategie cu multi analiza, 3 rotiri", CutStrategyType.BEST_MULTIBLADE_MATCH.isEnabled());
        add(bestMultibladeMatchCutStrategyChk);
        noMultibladeCutStrategyChk = new JCheckBox("Strategie fara prisme", CutStrategyType.NO_MULTI_BLADE.isEnabled());
        add(noMultibladeCutStrategyChk);
        oneRotate180CutStrategyChk = new JCheckBox("O singura rotire la 180", CutStrategyType.ROTATE_ONE_180.isEnabled());
        add(oneRotate180CutStrategyChk);

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
            int ras = JOptionPane.showConfirmDialog(GUIUtil.container, "Doriti sa salvati", "Confirmati salvare", JOptionPane.YES_NO_OPTION);
            if(ras == JOptionPane.YES_OPTION) {
                setStrategiesFlag();
                java.util.List<GaterSetting> newSettings = new ArrayList<>();
                GaterSetting set1 = new GaterSetting();
                boolean updated = true;
                if(!updated) {
                    JOptionPane.showMessageDialog(GUIUtil.container, "Salvarea a esuat", "Nu se poate salva", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //
                }
            }
        }
    }

    private void setStrategiesFlag() {
        Map<String, Boolean> cutStrategies = new HashMap<String, Boolean>();
        if(simpleVerticalCutStrategyChk.isSelected()) {
            cutStrategies.put(CutStrategyType.SIMPLE_VERTICAL.name(), true);
        }
        CutStrategyType.SIMPLE_VERTICAL.setEnabled(simpleVerticalCutStrategyChk.isSelected());
        if(simple2VerticalCutStrategyChk.isSelected()) {
            cutStrategies.put(CutStrategyType.BEST_MATCH_VERTICAL.name(), true);
        }
        CutStrategyType.BEST_MATCH_VERTICAL.setEnabled(simple2VerticalCutStrategyChk.isSelected());
        if(oneRotateCutStrategyChk.isSelected()) {
            cutStrategies.put(CutStrategyType.ROTATE_ONE.name(), true);
        }
        CutStrategyType.ROTATE_ONE.setEnabled(oneRotateCutStrategyChk.isSelected());
        if(bestMultibladeMatchCutStrategyChk.isSelected()) {
            cutStrategies.put(CutStrategyType.BEST_MULTIBLADE_MATCH.name(), true);
        }
        CutStrategyType.BEST_MULTIBLADE_MATCH.setEnabled(bestMultibladeMatchCutStrategyChk.isSelected());
        if(noMultibladeCutStrategyChk.isSelected()) {
            cutStrategies.put(CutStrategyType.NO_MULTI_BLADE.name(), true);
        }
        CutStrategyType.NO_MULTI_BLADE.setEnabled(noMultibladeCutStrategyChk.isSelected());
        if(oneRotate180CutStrategyChk.isSelected()) {
            cutStrategies.put(CutStrategyType.ROTATE_ONE_180.name(), true);
        }
        CutStrategyType.ROTATE_ONE_180.setEnabled(oneRotate180CutStrategyChk.isSelected());
    }
}

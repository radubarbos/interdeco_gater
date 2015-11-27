package ro.barbos.gui;

import ro.barbos.gater.cutprocessor.strategy.CutStrategyType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutPlanSettingsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JCheckBox allChk;
	JCheckBox simpleVerticalCutStrategyChk;
	JCheckBox simple2VerticalCutStrategyChk;
	JCheckBox oneRotateCutStrategyChk;
	JCheckBox bestMultibladeMatchCutStrategyChk;
	JCheckBox noMultibladeCutStrategyChk;
	
	public CutPlanSettingsPanel() {
		this(false);
	}
	
	public CutPlanSettingsPanel(boolean showAllOption) {
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
	}
	
	public Map<String, Boolean> getCutStrategySettings() {
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
		return cutStrategies;
	}
	
	public List<Integer> getCutStrategyIndexes() {
		List<Integer> cutStrategiesIndexes = new ArrayList<>();
		if(simpleVerticalCutStrategyChk.isSelected()) {
			cutStrategiesIndexes.add(0);
		}
		if(simple2VerticalCutStrategyChk.isSelected()) {
			cutStrategiesIndexes.add(1);
		}
		if(oneRotateCutStrategyChk.isSelected()) {
			cutStrategiesIndexes.add(2);
		}
		if(bestMultibladeMatchCutStrategyChk.isSelected()) {
			cutStrategiesIndexes.add(3);
		}
		return cutStrategiesIndexes;
	}
}

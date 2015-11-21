package ro.barbos.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import ro.barbos.gater.cutprocessor.strategy.CutStrategyType;

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
		simpleVerticalCutStrategyChk = new JCheckBox("Strategie verticala simpla", true);
		add(simpleVerticalCutStrategyChk);
		simple2VerticalCutStrategyChk = new JCheckBox("Strategie verticala simpla2", true);
		add(simple2VerticalCutStrategyChk);
		oneRotateCutStrategyChk = new JCheckBox("Strategie cu o rotire", true);
		add(oneRotateCutStrategyChk);
		bestMultibladeMatchCutStrategyChk = new JCheckBox("Strategie cu multi analiza, 3 rotiri", true);
		add(bestMultibladeMatchCutStrategyChk);
		noMultibladeCutStrategyChk = new JCheckBox("Strategie fara prisme");
		add(noMultibladeCutStrategyChk);
	}
	
	public Map<String, Boolean> getCutStrategySettings() {
		Map<String, Boolean> cutStrategies = new HashMap<String, Boolean>();
		if(simpleVerticalCutStrategyChk.isSelected()) {
			cutStrategies.put(CutStrategyType.SIMPLE_VERTICAL.name(), true);
		}
		if(simple2VerticalCutStrategyChk.isSelected()) {
			cutStrategies.put(CutStrategyType.BEST_MATCH_VERTICAL.name(), true);
		}
		if(oneRotateCutStrategyChk.isSelected()) {
			cutStrategies.put(CutStrategyType.ROTATE_ONE.name(), true);
		}
		if(bestMultibladeMatchCutStrategyChk.isSelected()) {
			cutStrategies.put(CutStrategyType.BEST_MULTIBLADE_MATCH.name(), true);
		}
		if(noMultibladeCutStrategyChk.isSelected()) {
			cutStrategies.put(CutStrategyType.NO_MULTI_BLADE.name(), true);
		}
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

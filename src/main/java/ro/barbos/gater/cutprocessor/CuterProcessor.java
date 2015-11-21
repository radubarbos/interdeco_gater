package ro.barbos.gater.cutprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.strategy.AbstractCutStrategy;
import ro.barbos.gater.cutprocessor.strategy.BestMatchVerticalCutStrategy;
import ro.barbos.gater.cutprocessor.strategy.BestMultiBladeCutStrategy;
import ro.barbos.gater.cutprocessor.strategy.CutStrategy;
import ro.barbos.gater.cutprocessor.strategy.CutStrategyType;
import ro.barbos.gater.cutprocessor.strategy.NoMultiBladeCutStrategy;
import ro.barbos.gater.cutprocessor.strategy.RotateOneCutStrategy;
import ro.barbos.gater.cutprocessor.strategy.SimpleVerticalCutStrategy;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public class CuterProcessor {
	
	private Map<String, Boolean> cutStrategies;
	
	public List<CutDiagram> cutLumberLog(LumberLog lumberLog, List<Product> products, List<Integer> targetPieces) {
		List<CutDiagram> diagrams = new ArrayList<>();
		for(CutStrategy strategy: getCutStrategies()) {
			diagrams.add(strategy.cutLumberLog(lumberLog, products, targetPieces));
		}
		return diagrams;
	}
	
	private List<CutStrategy> getCutStrategies() {
		List<CutStrategy> strategies = new ArrayList<>();
		if(cutStrategies == null || cutStrategies.containsKey(CutStrategyType.SIMPLE_VERTICAL.name())) {
			AbstractCutStrategy cutStrategy = new SimpleVerticalCutStrategy();
			setCutStrategyLengthOptimization(cutStrategy);
			strategies.add(cutStrategy);
		}
		if(cutStrategies == null || cutStrategies.containsKey(CutStrategyType.BEST_MATCH_VERTICAL.name())) {
			AbstractCutStrategy cutStrategy = new BestMatchVerticalCutStrategy();
			setCutStrategyLengthOptimization(cutStrategy);
			strategies.add(cutStrategy);
		}
		if(cutStrategies == null || cutStrategies.containsKey(CutStrategyType.ROTATE_ONE.name())) {
			AbstractCutStrategy cutStrategy = new RotateOneCutStrategy();
			setCutStrategyLengthOptimization(cutStrategy);
			strategies.add(cutStrategy);
		}
		if(cutStrategies == null || cutStrategies.containsKey(CutStrategyType.BEST_MULTIBLADE_MATCH.name())) {
			AbstractCutStrategy cutStrategy = new BestMultiBladeCutStrategy();
			setCutStrategyLengthOptimization(cutStrategy);
			strategies.add(cutStrategy);
		}
		if(cutStrategies == null || cutStrategies.containsKey(CutStrategyType.NO_MULTI_BLADE.name())) {
			AbstractCutStrategy cutStrategy = new NoMultiBladeCutStrategy();
			setCutStrategyLengthOptimization(cutStrategy);
			strategies.add(cutStrategy);
		}
		return strategies;
	}
	
	public CutDiagram getBestCut(LumberLog lumberLog, List<Product> products, List<Integer> targetPieces, Map<String, Boolean> cutStrategies) {
		this.cutStrategies = cutStrategies;
		CutDiagram bestCut = null;
		List<CutDiagram> cutDiagrams  = cutLumberLog(lumberLog, products, targetPieces);
		if(cutDiagrams != null) {
			for(CutDiagram diagram: cutDiagrams) {
				if(bestCut == null) {
					bestCut = diagram;
				}
				else if(CutterSettings.DO_LENGTH_OPTIMIZATION) {
					if(bestCut.cutInfo.cutVolume < diagram.cutInfo.cutVolume) {
						bestCut = diagram;
					}
				}
				else if(bestCut.cutInfo.cutLayoutEfficency < diagram.cutInfo.cutLayoutEfficency) {
					bestCut = diagram;
				}
			}
		}
		return bestCut;
	}
	
	private void setCutStrategyLengthOptimization(AbstractCutStrategy cutStrategy) {
		LengthOptimization lengthOptimization = new LengthOptimization();
		lengthOptimization.doOptimization = CutterSettings.DO_LENGTH_OPTIMIZATION;
		cutStrategy.setLengthOptimization(lengthOptimization);
	}
}

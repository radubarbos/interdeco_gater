package ro.barbos.gater.cutprocessor.strategy;

import java.util.List;

import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public interface CutStrategy {

	public CutDiagram cutLumberLog(LumberLog lumberLog, List<Product> products, List<Integer> targetPieces);
	
	public int getStrategyCode();
}

package ro.barbos.gater.cutprocessor.strategy;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import ro.barbos.gater.cutprocessor.CutContext;
import ro.barbos.gater.cutprocessor.CutPhase;
import ro.barbos.gater.cutprocessor.CuterManager;
import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.cutprocessor.LumberLogSegmentSteps;
import ro.barbos.gater.cutprocessor.ProductsCutData;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo;
import ro.barbos.gater.cutprocessor.diagram.GaterSlide;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public class NoMultiBladeCutStrategy extends AbstractCutStrategy {
	
	/**
	 * Y axis slide algorithm
	 * 
	 *  - it goes on the y axis down and on each slide it checks the best productivity product
	 *  - it uses the inner square as multi blade area
	 * 
	 * @param lumberLog
	 * @param products
	 * @return
	 */
	public CutDiagram cutLumberLog(LumberLog lumberLog, List<Product> products, List<Integer> targetPieces) {
		CutDiagram diagram = new CutDiagram();
		diagram.cutInfo = new CutDiagramInfo();
		Map<String, Color> colors = getColorMap(products);
		diagram.cutInfo.colors = colors;
		ProductsCutData cuttingState = CuterManager.analize(products, targetPieces);
		if(cuttingState.getMetaData().size() == 0) {
			return diagram;
		}
		LumberLogSegmentSteps segmentSteps = new LumberLogSegmentSteps();
		double radius = lumberLog.getSmallRadius()/2 - CutterSettings.EDGE_TOLERANCE;
		diagram.cutInfo.smallEndArea = Math.PI * radius * radius;
		diagram.cutInfo.lumberLogVolume = lumberLog.getVolume();
		double insideSquareSide = radius * Math.pow(2, 0.5);
		diagram.debugSquare = insideSquareSide;
		double currentYPosition = -radius;
		currentYPosition += CutterSettings.GATER_THICK;
		while(currentYPosition < radius) {
			int gaterCut = computeGaterCut(radius, currentYPosition);
			CutContext cutContext = new CutContext();
			cutContext.lumberLogRadius = radius;
			cutContext.yPosition = currentYPosition;
			cutContext.lengthLimit = lumberLog.getRealLength();
			cutContext.currentCut = gaterCut;
			cutContext.innerSquare = insideSquareSide;
			
			Product productToCut = cuttingState.getBestMatchSmallestProduct(cutContext);
			if(productToCut != null) {
				int nextGuterCut = computeGaterCut(radius, currentYPosition + productToCut.getThick());
				int smallestCut = Math.min(gaterCut, nextGuterCut);
				if(smallestCut > productToCut.getWidth()) {
					if(segmentSteps.cuts.size() == 0) {
						double cutPos = lumberLog.getSmallRadius()/2 + (currentYPosition-CutterSettings.GATER_THICK);
						segmentSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), cutPos));
					}
					GaterSlide cutSlide = new GaterSlide();
					cutSlide.phase = CutPhase.FIRST;
					cutSlide.y = currentYPosition;
					cutSlide.height = productToCut.getThick();
					cutSlide.pieceWidth = productToCut.getWidth();
					diagram.gaterCutFlow.add(cutSlide);
					int pieces = (int)(smallestCut / productToCut.getWidth());
					cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
					currentYPosition += productToCut.getThick();
					cutSlide.pieces = pieces;
					double tempWidth = cutSlide.pieces * productToCut.getWidth();
					tempWidth += (pieces-1) * CutterSettings.MULTIBLADE;
					cutSlide.x = -tempWidth/2;
					cutSlide.color = colors.get(productToCut.getName());
					segmentSteps.addCut(productToCut.getThick().doubleValue());
				}
			}
			currentYPosition += CutterSettings.GATER_THICK;
		}
		diagram.steps.setTop(segmentSteps);
		CuterManager.setCutLayoutData(lumberLog, diagram.cutInfo, cuttingState);
		diagram.steps.compileSteps();
		return diagram;
	}

	/* (non-Javadoc)
	 * @see ro.barbos.gater.cutprocessor.strategy.CutStrategy#getStrategyCode()
	 */
	@Override
	public int getStrategyCode() {
		return 4;
	}
	
	
	
}

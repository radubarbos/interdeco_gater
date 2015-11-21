package ro.barbos.gater.cutprocessor.strategy;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ro.barbos.gater.cutprocessor.CutContext;
import ro.barbos.gater.cutprocessor.CutPhase;
import ro.barbos.gater.cutprocessor.CuterManager;
import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.cutprocessor.LumberLogCutContext;
import ro.barbos.gater.cutprocessor.LumberLogSegmentSteps;
import ro.barbos.gater.cutprocessor.ProductsCutData;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo;
import ro.barbos.gater.cutprocessor.diagram.GaterSlide;
import ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public class BestMatchVerticalCutStrategy extends AbstractCutStrategy {
	
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
		cuttingState.setLengthOptimization(lengthOptimization);
		LumberLogCutContext lumberContext = new LumberLogCutContext(lumberLog, CutterSettings.EDGE_TOLERANCE);
		lumberContext.setCutInfo(diagram);
		
		LumberLogSegmentSteps segmentSteps = new LumberLogSegmentSteps();
		double currentYPosition = -lumberContext.getRadius();
		currentYPosition += CutterSettings.GATER_THICK;
		boolean noMoreMultiBladeMatch = false;
		boolean storedTop = false;
		boolean storedMultiblade = false;
		boolean hasMultiblade = false;
		
		double lastMultibladeCut = 0D;
		double lastBottomCut = 0D;
		double startBottomYCut = 0;
		
		while(currentYPosition < lumberContext.getRadius()) {
			Product productToCut = null;
			int gaterCut = computeGaterCut(lumberContext.getRadius(), currentYPosition);
			CutContext cutContext = lumberContext.createCutContext(gaterCut, currentYPosition);
			if(cutInTopSegment(currentYPosition, -lumberContext.getHalfSquare())) {
				//not in multi blade mode
				productToCut = cuttingState.getBestMatchSmallestProduct(cutContext);
				if(productToCut != null && productToCut.getWidth() < gaterCut) {
					if(segmentSteps.cuts.size() == 0) {
						double cutPos = lumberLog.getSmallRadius()/2 + (currentYPosition-CutterSettings.GATER_THICK);
						segmentSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), cutPos));
					}
					GaterSlide cutSlide = new GaterSlide();
					cutSlide.phase = CutPhase.SECOND;
					cutSlide.y = currentYPosition;
					cutSlide.height = productToCut.getThick();
					cutSlide.pieceWidth = productToCut.getWidth();
					diagram.gaterCutFlow.add(cutSlide);
					int pieces = getMultibladePieces(gaterCut, productToCut.getWidth());
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
			else if(currentYPosition < lumberContext.getHalfSquare() && !noMoreMultiBladeMatch) {
				if(storedTop == false) {
					storedTop = true;
					if(segmentSteps.cuts.size()>0) {
						diagram.steps.setTop(segmentSteps);
						segmentSteps = new LumberLogSegmentSteps();
					}
				}
				cutContext.analizeBestMatch = true;
				cutContext.widthLimit = lumberContext.getHalfSquare() - currentYPosition;
				productToCut = cuttingState.getBestMultibladeProduct(cutContext);
				if(productToCut != null) {
					hasMultiblade = true;
					//slide for multilama
					GaterSlide cutSlide = new GaterSlide();
					cutSlide.y = currentYPosition;
					cutSlide.height = productToCut.getWidth();
					diagram.gaterCutFlow.add(cutSlide);
					int nextGuterCut = computeGaterCut(lumberContext.getRadius(), currentYPosition + productToCut.getWidth());
					int smallestCut = Math.min(gaterCut, nextGuterCut);
					int pieces = getMultibladePieces(smallestCut, productToCut.getThick());
					cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
					MultibladeCutSlide cut = createMultiBladeSlide(smallestCut, currentYPosition, pieces, productToCut);
					cut.phase = CutPhase.SECOND;
					diagram.multiBladeSlides.add(cut);
					currentYPosition += productToCut.getWidth();
					cut.color = colors.get(productToCut.getName());
					lastMultibladeCut = lumberLog.getSmallRadius()/2 + currentYPosition;
					segmentSteps.cuts.add(productToCut.getWidth().doubleValue());
				}
				else {
					//reuse the last cut position if no more multiblade are detected
					currentYPosition -= CutterSettings.GATER_THICK;
					noMoreMultiBladeMatch = true;
				}
			}
			else {
				if(hasMultiblade && !storedMultiblade && segmentSteps.cuts.size()>0) {
					storedMultiblade = true;
					diagram.steps.setMultiBlade(segmentSteps);
					segmentSteps = new LumberLogSegmentSteps();
					currentYPosition += CutterSettings.MULTIBLADE_TOLERANCE;
				}
				productToCut = cuttingState.getBestMatchSmallestProduct(cutContext);
				if(productToCut != null) {
					int nextGuterCut = computeGaterCut(lumberContext.getRadius(), currentYPosition + productToCut.getThick());
					int smallestCut = Math.min(gaterCut, nextGuterCut);
					if(smallestCut > productToCut.getWidth()) {
						if(segmentSteps.cuts.size() == 0) {
							startBottomYCut = lumberLog.getSmallRadius()/2 - currentYPosition + CutterSettings.GATER_THICK;
						}
						GaterSlide cutSlide = new GaterSlide();
						cutSlide.phase = CutPhase.FIRST;
						cutSlide.y = currentYPosition;
						cutSlide.height = productToCut.getThick();
						cutSlide.pieceWidth = productToCut.getWidth();
						diagram.gaterCutFlow.add(cutSlide);
						int pieces = getMultibladePieces(smallestCut, productToCut.getWidth());
						cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
						currentYPosition += productToCut.getThick();
						cutSlide.pieces = pieces;
						double tempWidth = cutSlide.pieces * productToCut.getWidth();
						tempWidth += (pieces-1) * CutterSettings.MULTIBLADE;
						cutSlide.x = -tempWidth/2;
						cutSlide.color = colors.get(productToCut.getName());
						lastBottomCut = lumberLog.getSmallRadius()/2 - currentYPosition - CutterSettings.GATER_THICK;
						segmentSteps.addCut(productToCut.getThick().doubleValue());
					}
				}
			}
			currentYPosition += CutterSettings.GATER_THICK;
		}
		if(segmentSteps.cuts.size()>0) {
			segmentSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), lastBottomCut));
			Collections.reverse(segmentSteps.cuts);
			diagram.steps.setBottom(segmentSteps);
			segmentSteps = new LumberLogSegmentSteps();
		}
		else {
			startBottomYCut = lumberLog.getSmallRadius() - lastMultibladeCut;
			segmentSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), startBottomYCut - CutterSettings.GATER_THICK));
			diagram.steps.setBottom(segmentSteps);
		}
		if(diagram.steps.getTop() != null) {
		   diagram.steps.getTop().cuts.set(0, diagram.steps.getTop().cuts.get(0) - startBottomYCut);
		}
		CuterManager.setCutLayoutData(lumberLog, diagram.cutInfo, cuttingState);
		diagram.steps.compileSteps();
		return diagram;
	}

	/* (non-Javadoc)
	 * @see ro.barbos.gater.cutprocessor.strategy.CutStrategy#getStrategyCode()
	 */
	@Override
	public int getStrategyCode() {
		return 2;
	}
	
	
	
}

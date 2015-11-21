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
import ro.barbos.gater.cutprocessor.ProductsCutData;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo;
import ro.barbos.gater.cutprocessor.diagram.GaterSlide;
import ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public class SimpleVerticalCutStrategy extends AbstractCutStrategy {

	/**
	 * Y axis slide algorithm
	 * 
	 *  - it goes on the y axis down
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
		
		double currentYPosition = -lumberContext.getRadius();
		currentYPosition += CutterSettings.GATER_THICK;
		boolean noMoreMultiBladeMatch = false;
		boolean storedMultiblade = false;
		boolean hasMultiblade = false;
		
		double lastMultibladeCut = 0D;
		double lastBottomCut = 0D;
		double startBottomYCut = 0;
		
		while(currentYPosition < lumberContext.getRadius()) {
			Product productToCut = null;
			int gaterCut = computeGaterCut(lumberContext.getRadius(), currentYPosition);
			if(currentYPosition < -lumberContext.getHalfSquare()) {
				//not in multi blade mode
				productToCut = cuttingState.getCurrentSmallestProduct(lumberLog.getRealLength());
				if(productToCut != null && productToCut.getWidth() < gaterCut) {
					if(topCutSteps.cuts.size() == 0) {
						double cutPos = lumberLog.getSmallRadius()/2 + (currentYPosition-CutterSettings.GATER_THICK);
						topCutSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), cutPos));
					}
					GaterSlide cutSlide = createGaterSlide(productToCut);
					cutSlide.phase = CutPhase.SECOND;
					cutSlide.y = currentYPosition;
					diagram.gaterCutFlow.add(cutSlide);
					int pieces = getMultibladePieces(gaterCut, productToCut.getWidth());
					cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
					cutSlide.pieces = pieces;
					setProductXInGaterSlide(cutSlide, productToCut.getWidth());
					currentYPosition += productToCut.getThick();
					cutSlide.color = colors.get(productToCut.getName());
					topCutSteps.addCut(productToCut.getThick().doubleValue());
				}
			}
			else if(currentYPosition < lumberContext.getHalfSquare() && !noMoreMultiBladeMatch) {
				CutContext cutContext = new CutContext();
				cutContext.analizeBestMatch = true;
				cutContext.widthLimit = lumberContext.getHalfSquare() - currentYPosition;
				cutContext.lumberLogRadius = lumberContext.getRadius();
				cutContext.yPosition = currentYPosition;
				cutContext.lengthLimit = lumberLog.getRealLength();
				productToCut = cuttingState.getMultibladeProduct(cutContext);
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
					multibladeCutSteps.cuts.add(productToCut.getWidth().doubleValue());
				}
				else {
					currentYPosition -= CutterSettings.GATER_THICK;
					noMoreMultiBladeMatch = true;
				}
			}
			else {
				if(hasMultiblade && !storedMultiblade) {
					storedMultiblade = true;
					currentYPosition += CutterSettings.MULTIBLADE_TOLERANCE;
				}
				productToCut = cuttingState.getCurrentSmallestProduct(lumberLog.getRealLength());
				if(productToCut != null) {
					int nextGuterCut = computeGaterCut(lumberContext.getRadius(), currentYPosition + productToCut.getThick());
					int smallestCut = Math.min(gaterCut, nextGuterCut);
					if(smallestCut > productToCut.getWidth()) {
						if(bottomCutSteps.cuts.size() == 0) {
							startBottomYCut = lumberLog.getSmallRadius()/2 - currentYPosition + CutterSettings.GATER_THICK;
						}
						GaterSlide cutSlide = createGaterSlide(productToCut);
						cutSlide.phase = CutPhase.FIRST;
						cutSlide.y = currentYPosition;
						diagram.gaterCutFlow.add(cutSlide);
						int pieces = getMultibladePieces(smallestCut, productToCut.getWidth());
						cuttingState.addCutPieces(productToCut, pieces, lumberLog.getPlate());
						cutSlide.pieces = pieces;
						setProductXInGaterSlide(cutSlide, productToCut.getWidth());
						currentYPosition += productToCut.getThick();
						cutSlide.color = colors.get(productToCut.getName());
						lastBottomCut = lumberLog.getSmallRadius()/2 - currentYPosition - CutterSettings.GATER_THICK;
						bottomCutSteps.addCut(productToCut.getThick().doubleValue());
					}
				}
			}
			currentYPosition += CutterSettings.GATER_THICK;
		}
		if(bottomCutSteps.cuts.size()>0) {
			bottomCutSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), lastBottomCut));
			Collections.reverse(bottomCutSteps.cuts);
		}
		else {
			startBottomYCut = lumberLog.getSmallRadius() - lastMultibladeCut;
			bottomCutSteps.addCut(CuterManager.computeStartFromTop(lumberLog.getSmallRadius(), startBottomYCut - CutterSettings.GATER_THICK));
		}
		setCutSteps(diagram);
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
		return 1;
	}
	
	
	
}

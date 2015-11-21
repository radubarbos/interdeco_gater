package ro.barbos.gater.cutprocessor;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public class ProductsCutData {

	private Map<Product, ProductCutMetaData> metaData = new LinkedHashMap<Product, ProductCutMetaData>();
	private List<Product> widthData = new ArrayList<>(); //products in order of width 
	
	private Map<String, Map<String, Integer>> debugData = new HashMap<>();
	
	private double multiBladeCoverageAllowedMin = 0.50D;
	
	private LengthOptimization lengthOptimization;
	
	private boolean stopOnProductTargetLimit = true;
	
	/**
	 * @return the metaData
	 */
	public Map<Product, ProductCutMetaData> getMetaData() {
		return metaData;
	}
	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(Map<Product, ProductCutMetaData> metaData) {
		this.metaData = metaData;
	}
	/**
	 * @return the widthData
	 */
	public List<Product> getWidthData() {
		return widthData;
	}
	/**
	 * @param widthData the widthData to set
	 */
	public void setWidthData(List<Product> widthData) {
		this.widthData = widthData;
	}
	
	
	/**
	 * Gets the next most important product that is small
	 * @return
	 */
	public Product getCurrentSmallestProduct(double lengthLimit) {
		Product closesLenghtMatch = null;
		for(int index =0; index < widthData.size(); index++) {
			Product product = widthData.get(index);
			if(!isProductLengthValid(lengthLimit, product)) {
				continue;
			}
			if(isProductLengthAccepted(lengthLimit, product)) {
				return product;
			}
			else {
				closesLenghtMatch = updateClosesLengthMatch(closesLenghtMatch, product);
			}
		}
		return closesLenghtMatch;
	}
	
	//TODO used a lot, verify
	public Product getBestMatchSmallestProduct(CutContext cutContext) {
		double coverage = 0;
		Product best = null;
		Product closesLenghtMatch = null;
		for(int index =0; index < widthData.size(); index++) {
			Product product = widthData.get(index);
			if(!isProductLengthValid(cutContext.lengthLimit, product)) {
				continue;
			}
			
			double y = cutContext.yPosition + cutContext.cutDirection * product.getThick();
			Line2D.Double cut = new Line2D.Double(-cutContext.lumberLogRadius - 10, y, cutContext.lumberLogRadius + 10, y);
			int belowCut = CuterManager.computeCutLength(cutContext, cut);
			int smallestcut = Math.min(belowCut, cutContext.currentCut);
			int matchPieces = (int)(smallestcut/ (product.getWidth() + CutterSettings.MULTIBLADE));
			if(matchPieces == 0) {
				continue;
			}
			closesLenghtMatch = updateClosesLengthMatch(closesLenghtMatch, product);
			double piecesWidth = matchPieces * (product.getWidth() + CutterSettings.MULTIBLADE);
			if(smallestcut - piecesWidth > product.getWidth()) {
				//last one can be cut
				matchPieces++;
			}
			double usedMaterial = matchPieces * product.getWidth();
			if(usedMaterial > coverage) {
				coverage = usedMaterial;
				best = product;
			}
		}
		return chooseBestProduct(best, closesLenghtMatch);
	}
	
	public void addCutPieces(Product product, long pieces, IDPlate idPlate) {
		ProductCutMetaData meta = metaData.get(product);
		meta.piecesCut += pieces;
		Map<String, Integer> debug = debugData.get(idPlate.getLabel());
		if(debug == null) {
			debug = new HashMap<>();
			debugData.put(idPlate.getLabel(), debug);
		}
		Integer counter = debug.get(product.getName());
		if(counter == null) counter = 0;
		counter += (int)pieces;
		debug.put(product.getName(), counter);
	}
	
	public Product getMultibladeProduct(CutContext cutContext) {
		Product product = null;
		Iterator<Map.Entry<Product, ProductCutMetaData>> iterator = metaData.entrySet().iterator();
		if(product == null && cutContext.analizeBestMatch) {
			iterator = metaData.entrySet().iterator();
			Product bestMatch = null;
			Product closesLenghtMatch = null;
			double productivity = 0;
			while (iterator.hasNext()) {
				Map.Entry<Product, ProductCutMetaData> entry = iterator.next();
				ProductCutMetaData meta = entry.getValue();
				if(!isProductLengthValid(cutContext.lengthLimit, entry.getKey())) {
					continue;
				}
				double currentProductCompletition = meta.piecesCut/(double)meta.piecesTarget;
				if(currentProductCompletition > 0.90) {
					continue;
				}
				if(!isProductLengthAccepted(cutContext.lengthLimit, entry.getKey())){
					closesLenghtMatch = updateClosesLengthMatch(closesLenghtMatch, entry.getKey());
				}
				double width = entry.getKey().getWidth();
				double y = cutContext.yPosition + width;
				Line2D.Double cut = new Line2D.Double(-cutContext.lumberLogRadius - 10, y, cutContext.lumberLogRadius + 10, y);
				int belowCut = CuterManager.computeCutLength(cutContext.lumberLogRadius, cut);
				int matchPieces = (int)(belowCut/ (entry.getKey().getThick() + CutterSettings.MULTIBLADE));
				double tempProductiv = matchPieces * entry.getKey().getThick();
				if(tempProductiv > 0.7) {
					if(bestMatch == null || tempProductiv > productivity) {
						bestMatch = entry.getKey();
						productivity = tempProductiv;
					}
				}
			}
			if(bestMatch != null) {
				if(closesLenghtMatch != null && closesLenghtMatch.getLength() > bestMatch.getLength()) {
					return closesLenghtMatch;
				}
				return bestMatch;
			}
		}
		return product;
	}
	
	//TODO used a lot, verify test
	public Product getBestMultibladeProduct(CutContext cutContext) {
		Product product = null;
		int bestMatchPieces = 0;
		Product closesLenghtMatch = null;
		int bestLengthMatchPieces = 0;
		double coverage = 0D;
		Iterator<Map.Entry<Product, ProductCutMetaData>> iterator = metaData.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Product, ProductCutMetaData> entry = iterator.next();
			ProductCutMetaData meta = entry.getValue();
			Product currentProduct = entry.getKey();
			if(meta.piecesCut >= meta.piecesTarget) {
				continue;
			}
			if(!isProductLengthValid(cutContext.lengthLimit, currentProduct)) {
				continue; // product too long for current log
			}
			if(currentProduct.getWidth()/cutContext.widthLimit > 1.5) {
				continue;
			}
			
			double width =currentProduct.getWidth();
			double y = cutContext.yPosition + width;
			Line2D.Double cut = new Line2D.Double(-cutContext.lumberLogRadius - 10, y, cutContext.lumberLogRadius + 10, y);
			int belowCut = CuterManager.computeCutLength(cutContext, cut);
			int smallestcut = Math.min(belowCut, cutContext.currentCut);
			int matchPieces = (int)(smallestcut/ (currentProduct.getThick() + CutterSettings.MULTIBLADE));
			double piecesWidth = matchPieces * (currentProduct.getThick() + CutterSettings.MULTIBLADE);
			if(smallestcut - piecesWidth > currentProduct.getThick()) {
				//last one can be cut
				matchPieces++;
			}
			double materialUse = matchPieces * currentProduct.getThick();
			if((materialUse/cutContext.innerSquare) > multiBladeCoverageAllowedMin) {
				Product closesLenghtMatch2 = updateClosesLengthMatch(closesLenghtMatch, currentProduct);
				if(closesLenghtMatch2 != closesLenghtMatch) {
					closesLenghtMatch = closesLenghtMatch2;
					bestLengthMatchPieces = matchPieces;
				}
			}
			if(materialUse > coverage && (materialUse/cutContext.innerSquare) > multiBladeCoverageAllowedMin) {
				coverage = materialUse;
				product = currentProduct;
				bestMatchPieces = matchPieces;
			}
		}
		return chooseBestProduct(product, closesLenghtMatch, bestMatchPieces, bestLengthMatchPieces);
	}
		
	public Map<String, Integer> getDebugInfo(LumberLog lumberLog) {
		return debugData.get(lumberLog.getPlate().getLabel());
	}
	/**
	 * @param lengthOptimization the lengthOptimization to set
	 */
	public void setLengthOptimization(LengthOptimization lengthOptimization) {
		this.lengthOptimization = lengthOptimization;
	}
	
	private boolean isProductLengthValid(double lumberLogLength, Product product) {
		return product.getLength() <= lumberLogLength;
	}
	
	private boolean isProductLengthAccepted(double lumberLogLength, Product product) {
		if(lengthOptimization != null && lengthOptimization.doOptimization) {
			double lengthLoss = lumberLogLength - product.getLength();
			if(lengthLoss <= lengthOptimization.maxLenghtLoss) {
				return true;
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	private Product updateClosesLengthMatch(Product currentClosestMatch, Product product) {
		if(lengthOptimization != null && lengthOptimization.doOptimization && lengthOptimization.forceTheClosest) {
			if(currentClosestMatch == null || product.getLength() > currentClosestMatch.getLength()) {
				return product;
			}
			else {
				return currentClosestMatch;
			}
		}
		return null;
	}
	
	private Product chooseBestProduct(Product bestLayout, Product bestLength) {
		return chooseBestProduct(bestLayout, bestLength, 1, 1);
	}
	
	private Product chooseBestProduct(Product bestLayout, Product bestLength, int bestLayoutPieces, int bestLengthPieces) {
		if(bestLayout == null) {
			return bestLength;
		}
		if(bestLength == null) {
			return bestLayout;
		}
		if(bestLayout == bestLength) {
			return bestLength;
		}
		//if(bestLayout.getLength() < bestLength.getLength()) return bestLength; temp test
		double layoutVolume = bestLayout.getLength() * bestLayout.getThick() * bestLayout.getWidth() * bestLayoutPieces;
		double lengthVolume = bestLength.getLength() * bestLength.getThick() * bestLength.getWidth() * bestLengthPieces;
		if(layoutVolume > lengthVolume) {
			return bestLayout;
		}
		return bestLength;
	}
	
}

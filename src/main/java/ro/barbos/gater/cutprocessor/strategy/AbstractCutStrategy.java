package ro.barbos.gater.cutprocessor.strategy;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.barbos.gater.cutprocessor.CuterManager;
import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gater.cutprocessor.LengthOptimization;
import ro.barbos.gater.cutprocessor.LumberLogSegmentSteps;
import ro.barbos.gater.cutprocessor.diagram.CutDiagram;
import ro.barbos.gater.cutprocessor.diagram.GaterSlide;
import ro.barbos.gater.cutprocessor.diagram.MultibladeCutSlide;
import ro.barbos.gater.model.Product;

public abstract class AbstractCutStrategy implements CutStrategy {

	public static final Color[] colors = new Color[]  {Color.red, Color.green, Color.black, Color.orange};
	
	protected LumberLogSegmentSteps topCutSteps = new LumberLogSegmentSteps();
	protected LumberLogSegmentSteps multibladeCutSteps = new LumberLogSegmentSteps();
	protected LumberLogSegmentSteps bottomCutSteps = new LumberLogSegmentSteps();
	
	protected LengthOptimization lengthOptimization = new LengthOptimization();
	
	protected int computeGaterCut(double radius, double y) {
		Line2D.Double cut = new Line2D.Double(-radius - 10, y, radius + 10, y);
		return CuterManager.computeCutLength(radius, cut);
	}
	
	protected boolean cutInTopSegment(double yPosition, double innerSquareLimit) {
		if(innerSquareLimit - 4 > yPosition) {
			return true;
		}
		return false;
	}
	
	protected int computeGaterCut(double radius, double y, Double rightLimit) {
		Line2D.Double cut = new Line2D.Double(-radius - 10, y, radius + 10, y);
		int cutLength =  CuterManager.computeCutLength(radius, cut);
		if(rightLimit != null) {
			double delta = cutLength/2 - rightLimit;
			if(delta >0) cutLength -= (int)delta;
		}
		return cutLength;
	}
	
	protected Map<String, Color> getColorMap(List<Product> products) {
		Map<String, Color> color = new HashMap<String, Color>();
		for(int index = 0; index < products.size(); index++) {
			color.put(products.get(index).getName(), colors[index % colors.length]);
		}
		return color;
	}
	
	protected int getMultibladePieces(double gaterCut, double productWidth) {
		int pieces = (int)(gaterCut / (productWidth + CutterSettings.MULTIBLADE));
		double piecesWidth = pieces * (productWidth + CutterSettings.MULTIBLADE);
		if(gaterCut - piecesWidth > productWidth) {
			//last one can be cut
			pieces++;
		}
		return pieces;
	}
	
	protected GaterSlide createGaterSlide(Product productToCut) {
		GaterSlide cutSlide = new GaterSlide();
		cutSlide.height = productToCut.getThick();
		cutSlide.pieceWidth = productToCut.getWidth();
		
		return cutSlide;
	}
	
	protected MultibladeCutSlide createMultiBladeSlide(double gaterCut, double currentYPosition, int pieces, Product productToCut) {
		MultibladeCutSlide cut = new MultibladeCutSlide();
		cut.x = -gaterCut/2;
		cut.y = currentYPosition;
		cut.pieces = pieces;
		cut.height = productToCut.getWidth();
		cut.width = 0;//not used
		cut.pieceWidth = productToCut.getThick();
		return cut;
	}
	
	protected void setProductXInGaterSlide(GaterSlide gaterCut, double pieceWidth) {
		double tempWidth = gaterCut.pieces * pieceWidth;
		tempWidth += (gaterCut.pieces-1) * CutterSettings.MULTIBLADE;
		gaterCut.x = -tempWidth/2;
	}
	
	protected void setCutSteps(CutDiagram diagram) {
		if(topCutSteps.cuts.size() > 0) {
			diagram.steps.setTop(topCutSteps);
		}
		if(multibladeCutSteps.cuts.size() > 0) {
			diagram.steps.setMultiBlade(multibladeCutSteps);
		}
		if(bottomCutSteps.cuts.size() > 0) {
			diagram.steps.setBottom(bottomCutSteps);
		}
	}

	/**
	 * @param lengthOptimization the lengthOptimization to set
	 */
	public void setLengthOptimization(LengthOptimization lengthOptimization) {
		this.lengthOptimization = lengthOptimization;
	}
	
	
}

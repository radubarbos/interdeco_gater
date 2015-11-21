package ro.barbos.gater.cutprocessor;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ro.barbos.gater.cutprocessor.diagram.CutDiagramInfo;
import ro.barbos.gater.model.GaterSetting;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.Product;

public class CuterManager {
	
	public static ProductsCutData analize(List<Product> products, List<Integer> targetPieces) {
		ProductsCutData data = new ProductsCutData();
		long totalPieces = 0;
		for(Integer pieces: targetPieces) {
			totalPieces += pieces.intValue();
		}
		Map<Product, ProductCutMetaData> metaData = new LinkedHashMap<Product, ProductCutMetaData>();
		Map<String, Product> map = new HashMap<String, Product>();
		for(int index = products.size()-1; index >= 0; index--) {
			Product product = products.get(index);
			Integer productPiecesTarget = targetPieces.get(index);
			if(productPiecesTarget.intValue() == 0) {
				products.remove(index);
				continue;
			}
			if (!map.containsKey(product.getName())) {
				map.put(product.getName(), product);
				ProductCutMetaData meta = new ProductCutMetaData();
				meta.piecesTarget = productPiecesTarget;
				meta.percentage = meta.piecesTarget/(double)totalPieces;
				meta.priority = CutPriority.NORMAL;
				metaData.put(product, meta);
				meta.width = product.getWidth();
				meta.thick = product.getThick();
				meta.length = product.getLength();
				meta.volume = meta.width * meta.thick * meta.length;
			} else {
				product = map.get(product.getName());
				ProductCutMetaData meta = metaData.get(product);
				meta.piecesTarget += productPiecesTarget;
				meta.percentage = meta.piecesTarget/(double)totalPieces;
			}
		}
		data.setMetaData(metaData);
		data.setWidthData(orderByWidth(products));
		return data;
	}
	
	private static List<Product> orderByWidth(List<Product> products) {
	     Collections.sort(products, new WidthComparator());
	     return products;
	}
	
	public static boolean insidePoint(double radius, Point point) {
		int x = Math.abs(point.x);
		int y = Math.abs(point.y);
		if(x > radius || y > radius) {
			return false;
		}
		
		return true;
	}
	
	public static List<Point2D.Double> cutIntersection(Double radius, Line2D.Double cut) {
		List<Point2D.Double> intersection = new ArrayList<>();
		//http://mathworld.wolfram.com/Circle-LineIntersection.html
		double dx = cut.x2 - cut.x1;
		double dy = cut.y2 - cut.y1;
		double dr = Math.pow(dx*dx + dy*dy, 0.5);
		double D = cut.x1 * cut.y2 - cut.x2 * cut.y1;
		double discriminant = radius*radius *dr*dr - D *D;
		double sgn = dy < 0 ? -1: 1;
		if(discriminant > 0) {
			Point2D.Double p1 = new Point2D.Double();
			p1.x = (D*dy - sgn * dx * Math.pow(discriminant, 0.5))/(dr * dr);
			p1.y = (-D * dx - Math.abs(dy) *  Math.pow(discriminant, 0.5))/(dr * dr);
			intersection.add(p1);
			Point2D.Double p2 = new Point2D.Double();
			p2.x = (D*dy + sgn * dx * Math.pow(discriminant, 0.5))/(dr * dr);
			p2.y = (-D * dx + Math.abs(dy) *  Math.pow(discriminant, 0.5))/(dr * dr);
			intersection.add(p2);
		}
		return intersection;
	}
	
	public static int computeCutLength(Double radius, Line2D.Double cut) {
		int cutLength = -1;
		List<Point2D.Double> intersection = CuterManager.cutIntersection(radius, cut);
		if(intersection.size() > 0) {
			cutLength = (int)Math.abs(intersection.get(1).x - intersection.get(0).x);
		}
		return cutLength;
	}
	
	public static int computeCutLength(CutContext cutContext, Line2D.Double cut) {
		int cutLength = CuterManager.computeCutLength(cutContext.lumberLogRadius, cut);
		if(cutContext.rightEdge != null) {
			double delta = cutLength/2 - cutContext.rightEdge;
			if(delta >0) cutLength -= (int)delta;
		}
		return cutLength;
	}
	
	public static double computeStartFromTop(double verticalGap, double distance) {
		double bottomStart = CutterSettings.BOTTOM_END - CutterSettings.BOTTOM_OFFSET;
		double lumberTopStart = bottomStart + verticalGap;
		return lumberTopStart - distance;
	}
	
	public static double computeGapFromTop(double verticalGap, double distance) {
		double bottomStart = CutterSettings.BOTTOM_END - CutterSettings.BOTTOM_OFFSET;
		double lumberTopStart = bottomStart + verticalGap;
		return lumberTopStart - distance;
	}
	
	public static void setCutLayoutData(LumberLog lumberLog, CutDiagramInfo info, ProductsCutData cutState) {
		Map<Product, ProductCutMetaData> metaData = cutState.getMetaData();
		Iterator<Map.Entry<Product, ProductCutMetaData>> iterator = metaData.entrySet().iterator();
		double areaOfCutProduct = 0D;
		double volumeOfCutProduct = 0D;
		while (iterator.hasNext()) {
			Map.Entry<Product, ProductCutMetaData> entry = iterator.next();
			ProductCutMetaData meta = entry.getValue();
			double areaCut = meta.piecesCut * meta.width * meta.thick;
			areaOfCutProduct += areaCut;
			volumeOfCutProduct += (areaCut * meta.length);
		}
		info.usedCutArea = areaOfCutProduct;
		info.cutLayoutEfficency = areaOfCutProduct/info.smallEndArea * 100;
		info.cutPieces = cutState.getDebugInfo(lumberLog);
		info.cutVolume = volumeOfCutProduct;
		info.cutVolumeEfficency = (volumeOfCutProduct/lumberLog.getVolume()) * 100;
	}
}

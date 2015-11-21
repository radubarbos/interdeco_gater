package ro.barbos.gater.data;

import java.util.List;

import ro.barbos.gater.model.LumberLog;

/**
 * http://www.mateonline.net/geometrie.htm
 * @author radu
 *
 */
public class LumberLogUtil {
	//TODO method name
	public static void calculateVolume(LumberLog lumberLog) {
		double minimum = lumberLog.getSmallRadius() /2;
		double maximum = lumberLog.getBigRadius() /2;
		
		double volume = 0;
		long lengthProcessed = 0;
		long meter = 1000L;
		List<Double> middleRadius = lumberLog.getMediumRadius();
		if(middleRadius != null && !middleRadius.isEmpty()) {
			for(int index =0; index < middleRadius.size(); index++) {
				double middleRadiusDiameter = middleRadius.get(index)/2;
				volume += calculateVolume(minimum, middleRadiusDiameter, meter);
				lengthProcessed+= meter;
				minimum = middleRadiusDiameter;
			}
		}
		volume+= calculateVolume(minimum, maximum, lumberLog.getLength() - lengthProcessed);
		lumberLog.setVolume(volume);
	}
	
	public static Long trimLength(LumberLog lumberLog) {
		Long trimLength = null;
		Double lumberLength = lumberLog.getLength();
		if(lumberLog.getLength() == null) {
			return null;
		}
		if(lumberLength>=4000 && lumberLength<5000) {
			trimLength = new Long(4000);
		}
		if(lumberLength>=3000 && lumberLength<4000) {
			trimLength = new Long(3000);
		}
		if(lumberLength>=2600 && lumberLength<3000) {
			trimLength = new Long(2600);
		}
		return trimLength;
	}
	
	public static void trimAndSetLength(LumberLog lumberLog) {
		Long newLength = LumberLogUtil.trimLength(lumberLog);
		if(newLength != null) {
			lumberLog.setLength((double)newLength);
		}
	}
	
	private static double calculateVolume(double smallRadius, double bigRadius, double length) {
		double firstPart = Math.pow(smallRadius, 2) + Math.pow(bigRadius, 2) + smallRadius * bigRadius;
		firstPart = firstPart * (Math.PI * length / 3);
		return firstPart;
	}

}

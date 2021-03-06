package ro.barbos.gater.data;

import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;
import ro.barbos.gater.stock.StockSettings;

import java.util.List;

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
		if(middleRadius != null && !middleRadius.isEmpty() && middleRadius.size()>1) {
			for(int index =0; index < middleRadius.size(); index++) {
				double middleRadiusDiameter = middleRadius.get(index)/2;
				volume += calculateVolume(minimum, middleRadiusDiameter, meter);
				lengthProcessed+= meter;
				minimum = middleRadiusDiameter;
			}
		} else if(middleRadius != null && !middleRadius.isEmpty() && StockSettings.MEASURE_MIDDLE_ONCE) {
            long middle = lumberLog.getLength().longValue()/2;
            double middleRadiusDiameter = middleRadius.get(0)/2;
            volume += calculateVolume(minimum, middleRadiusDiameter, meter);
            lengthProcessed+= middle;
            minimum = middleRadiusDiameter;
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

    public static LumberStack findLumberStack(LumberLog lumberLog, List<LumberStack> stacks) {
        LumberStack stack = null;
        for(LumberStack entry: stacks) {
            if(lumberLog.getSmallRadius() >= entry.getMinimum() && lumberLog.getSmallRadius() <= entry.getMaximum()) {
                stack = entry;
            }
        }
        return stack;
    }

}

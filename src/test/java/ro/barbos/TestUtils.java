package ro.barbos;

import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.model.LumberLog;

/**
 * Created by radu on 9/4/2016.
 */
public class TestUtils {

    public static LumberLog createLumberLog(Double smallDiameter, Double bigDiameter, Double length) {
        return TestUtils.createLumberLog(smallDiameter, bigDiameter, length, 1, 1, 7);
    }

    public static LumberLog createLumberLog(Double smallDiameter, Double bigDiameter, Double length, int species, int qaClass, int marginPercent) {
        LumberLog lumberLog = new LumberLog();
        lumberLog.setSmallRadius(smallDiameter);
        lumberLog.setBigRadius(bigDiameter);
        lumberLog.setLength(length);
        lumberLog.setMetric(METRIC.MILIMETER);
        LumberLogUtil.calculateVolume(lumberLog);
        lumberLog.setLumberType((long) species);
        lumberLog.setLumberClass((long) qaClass);
        lumberLog.setMarginPercent(marginPercent);
        LumberLogUtil.calculateVolume(lumberLog);
        lumberLog.setRealLength(lumberLog.getLength().longValue());
        lumberLog.setRealVolume(lumberLog.getVolume());
        LumberLogUtil.trimAndSetLength(lumberLog);
        LumberLogUtil.calculateVolume(lumberLog);
        lumberLog.setMarginRealVolume(lumberLog.getRealVolume() * (lumberLog.getMarginPercent() / 100D));
        lumberLog.setMarginVolume(lumberLog.getVolume() * (lumberLog.getMarginPercent() / 100D));
        return lumberLog;
    }
}

package ro.barbos.gater.data;

/**
 * Created by radu on 5/7/2016.
 */
public class MetricTools {

    public static final Long MILIMETER_VOLUME_SCALE = 1000000000L;

    public static Double toMeterCubs(Double volume) {
        return MetricTools.toMeterCubs(volume, METRIC.MILIMETER);
    }

    public static Double toMeterCubs(Double volume, METRIC metric) {
       if(metric == METRIC.MILIMETER) {
           return volume/MILIMETER_VOLUME_SCALE;
       }
        return 0D;
    }
}

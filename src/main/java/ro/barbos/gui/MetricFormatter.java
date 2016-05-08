package ro.barbos.gui;

import java.text.NumberFormat;

/**
 * Created by radu on 5/7/2016.
 */
public class MetricFormatter {

    public static NumberFormat numberFormatter = null;

    public static void init(){
        numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
        numberFormatter.setMaximumFractionDigits(6);
    }

    public static String formatVolume(Double volume) {
        return numberFormatter.format(volume) + " m. cub";
    }

    public static String format(Double value) {
        return numberFormatter.format(value);
    }

}

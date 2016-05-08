package ro.barbos.gui;

import ro.barbos.gater.model.User;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class ConfigLocalManager {

    public static SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
    public static NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));

    static {
        numberFormatter.setMaximumFractionDigits(2);
        numberFormatter.setMinimumFractionDigits(2);
    }

	public static User currentUser =null;
	public static Locale locale = null;

    public static Boolean showVolumeSum = true;
	
}

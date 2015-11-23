package ro.barbos.gui;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ro.barbos.gater.model.User;


public class ConfigLocalManager {

    public static SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
    public static NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));

    static {
        numberFormatter.setMaximumFractionDigits(2);
        numberFormatter.setMinimumFractionDigits(2);
    }

	public static User currentUser =null;
	public static Locale locale = null;
	
}

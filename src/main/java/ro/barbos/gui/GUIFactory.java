package ro.barbos.gui;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import ro.barbos.gui.exswing.JDateTimePicker;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Properties;

public class GUIFactory {
	
	public static Properties i18nDefaults;
	
	static {
		 i18nDefaults = new Properties();
		 i18nDefaults.put("messages.today", "Azi");
		 i18nDefaults.put("messages.nextMonth", "Luna urm.");
		 i18nDefaults.put("messages.previousMonth", "Luna trecuta");
		 i18nDefaults.put("messages.nextYear", "Anul viitor");
		 i18nDefaults.put("messages.previousYear", "Anul trecut");
		 i18nDefaults.put("messages.clear", "Sterge");
	}

	public static JLabel createLabel(String text, int width) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setPreferredSize(new Dimension(width, label.getPreferredSize().height));
		return label;
	}
	
	public static JComponent createDatePicker() {
		JDatePicker datePicker = JDateComponentFactory.createJDatePicker();
		datePicker.setI18nStrings(i18nDefaults);
		datePicker.setShowYearButtons(true); 
		datePicker.getModel().setSelected(true);
		return (JComponent)datePicker;
	}

    public static JPanel createDateTimePicker() {
        return new JDateTimePicker();
    }

    public static JPanel createFieldPanel(JLabel label, JComponent component) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		panel.add(component);
		return panel;
	}
	
	public static JFormattedTextField createDecimalNumberInput(Double value, Double min, Double max, int width) {
		NumberFormat nFormat = NumberFormat.getNumberInstance(ConfigLocalManager.locale);
		//nFormat.setMinimumFractionDigits(2);
		NumberFormatter formater = new NumberFormatter(nFormat);
		formater.setValueClass(Double.class);
		//formater.setAllowsInvalid(false);
		formater.setMinimum(0D);
		formater.setMaximum(9999D);
		JFormattedTextField field = new JFormattedTextField(formater);
		field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		if(value != null) {
			field.setValue(value);
		}
		return field;
	}
	
	public static JFormattedTextField createNumberInput(Long value, Long min, Long max, int width) {
		NumberFormatter formater = new NumberFormatter(NumberFormat.getIntegerInstance(ConfigLocalManager.locale));
		formater.setValueClass(Long.class);
		formater.setMinimum(min);
		formater.setMaximum(max);
		JFormattedTextField field = new JFormattedTextField(formater);
		field.setPreferredSize(new Dimension(width, field.getPreferredSize().height));
		if(value != null) {
			field.setValue(value);
		}
		return field;
	}
}

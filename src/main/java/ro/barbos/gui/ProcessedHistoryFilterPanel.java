package ro.barbos.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.JDatePicker;

public class ProcessedHistoryFilterPanel extends JPanel {

	public ProcessedHistoryFilterPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JComponent startDate = GUIFactory.createDatePicker();
		add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Din data de:", 90), startDate));
		JComponent endDate = GUIFactory.createDatePicker();
		add(GUIFactory.createFieldPanel(GUIFactory.createLabel("Pana in:", 90), endDate));
	}
	
	public boolean isFilterValid() {
		boolean valid = true;
		JLabel label = (JLabel)((JPanel)getComponent(0)).getComponent(0);
		JDatePicker start = (JDatePicker)((JPanel)getComponent(0)).getComponent(1);
		if(start.getModel().getValue() == null) {
			label.setForeground(Color.red);
			valid = false;
		}
		else {
			label.setForeground(Color.black);
		}
		
		JLabel label1 = (JLabel)((JPanel)getComponent(1)).getComponent(0);
		JDatePicker end = (JDatePicker)((JPanel)getComponent(1)).getComponent(1);
		if(end.getModel().getValue() == null) {
			label1.setForeground(Color.red);
			valid = false;
		}
		else {
			label1.setForeground(Color.black);
		}
		
		if(valid) {
			Calendar startDate = (Calendar)start.getModel().getValue();
			Calendar endDate = (Calendar)end.getModel().getValue();
			if(startDate.after(endDate)) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	public List<Date> getDateRange() {
		List<Date> dateRange = new ArrayList<>();
		JDatePicker start = (JDatePicker)((JPanel)getComponent(0)).getComponent(1);
		JDatePicker end = (JDatePicker)((JPanel)getComponent(1)).getComponent(1);
		dateRange.add(((Calendar)start.getModel().getValue()).getTime());
		dateRange.add(((Calendar)end.getModel().getValue()).getTime());
		return dateRange;
	}
}

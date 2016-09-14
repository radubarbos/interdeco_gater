package ro.barbos.gui.exswing;

import net.sourceforge.jdatepicker.JDatePicker;
import ro.barbos.gui.GUIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Created by radu on 9/14/2016.
 */
public class JDateTimePicker extends JPanel {
    JDatePicker datePicker;
    JComboBox<Integer> hours;
    JComboBox<Integer> minutes;
    JComboBox<Integer> seconds;

    public JDateTimePicker() {
        datePicker = (JDatePicker) GUIFactory.createDatePicker();
        Vector<Integer> hoursModel = new Vector<>(24);
        for (int h = 0; h < 24; h++) {
            hoursModel.add(h);
        }
        hours = new JComboBox<>(hoursModel);
        Vector<Integer> minutesModel = new Vector<>(64);
        for (int h = 0; h < 60; h++) {
            minutesModel.add(h);
        }
        minutes = new JComboBox<>(minutesModel);
        Vector<Integer> secondsModel = new Vector<>(64);
        for (int h = 0; h < 60; h++) {
            secondsModel.add(h);
        }
        seconds = new JComboBox<>(secondsModel);

        setLayout(new BorderLayout());
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 5));
        timePanel.add(GUIFactory.createLabel("Ora:", 30));
        timePanel.add(hours);
        JLabel sep = GUIFactory.createLabel(" :", 10);
        sep.setHorizontalTextPosition(SwingConstants.CENTER);
        timePanel.add(sep);
        timePanel.add(minutes);
        timePanel.add(GUIFactory.createLabel(" :", 10));
        timePanel.add(seconds);

        add((JComponent) datePicker, BorderLayout.NORTH);
        add(timePanel, BorderLayout.SOUTH);
    }

    public Date getDateTime() {
        Calendar date = ((Calendar) ((JDatePicker) datePicker).getModel().getValue());
        if (date != null) {
            date.set(Calendar.HOUR_OF_DAY, (Integer) hours.getSelectedItem());
            date.set(Calendar.MINUTE, (Integer) minutes.getSelectedItem());
            date.set(Calendar.SECOND, (Integer) seconds.getSelectedItem());
            return date.getTime();
        }
        return null;
    }
}

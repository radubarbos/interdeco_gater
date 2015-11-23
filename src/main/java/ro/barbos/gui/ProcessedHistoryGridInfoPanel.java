package ro.barbos.gui;

import ro.barbos.gater.gui.grid.DefaultGrid;
import ro.barbos.gater.gui.grid.DefaultGridInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by radu on 11/22/2015.
 */
public class ProcessedHistoryGridInfoPanel extends DefaultGridInfoPanel {

    private SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
    private NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));
    private JLabel info = null;

    public ProcessedHistoryGridInfoPanel(DefaultGrid grid) {
        super(grid);

        numberFormatter.setMaximumFractionDigits(2);
        numberFormatter.setMinimumFractionDigits(2);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 2));
        totalPanel.setMinimumSize(new Dimension(250, 30));
        info = new JLabel("Busteni: 0 Total m. cubi: 0");
        totalPanel.add(info);
        add(info, 0);
    }

    public void setInfo(Double volume, int lumbersCount) {
       info.setText("Busteni: "+lumbersCount+" Total m. cubi: " + numberFormatter.format(volume));
    }
}

package ro.barbos.gui.tablemodel.stock;

import ro.barbos.gater.dto.LengthStockDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;

import java.text.NumberFormat;

/**
 * Created by radu on 9/5/2016.
 */
public class LengthStockModel extends DefaultDataTableModel<LengthStockDTO> {

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    public LengthStockModel() {
        columns = new String[]{"Lungime", "Nr. busteni", "Volum"};
        numberFormatter.setMaximumFractionDigits(5);
    }

    @Override
    public Object getValueAt(int row, int col) {
        LengthStockDTO record = records.get(row);
        if (col == 0) {
            return numberFormatter.format(record.getLength()) + " mm";
        } else if (col == 1) {
            return numberFormatter.format(record.getTotalLumberLogs());
        } else if (col == 2) {
            return numberFormatter.format(record.getTotalVolume());
        }
        return null;
    }
}

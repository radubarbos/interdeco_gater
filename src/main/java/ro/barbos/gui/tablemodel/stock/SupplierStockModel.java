package ro.barbos.gui.tablemodel.stock;

import ro.barbos.gater.dto.SupplierStockDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;

import java.text.NumberFormat;

/**
 * Created by radu on 9/5/2016.
 */
public class SupplierStockModel extends DefaultDataTableModel<SupplierStockDTO> {

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    public SupplierStockModel() {
        columns = new String[]{"Supplier", "Nr. busteni", "Volum"};
        numberFormatter.setMaximumFractionDigits(5);
    }

    @Override
    public Object getValueAt(int row, int col) {
        SupplierStockDTO record = records.get(row);
        if (col == 0) {
            return record.getName();
        } else if (col == 1) {
            return numberFormatter.format(record.getTotalLumberLogs());
        } else if (col == 2) {
            return numberFormatter.format(record.getTotalVolume());
        }
        return null;
    }
}

package ro.barbos.gui.tablemodel.stock;

import ro.barbos.gater.model.LumberLogTransportCertificate;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by radu on 8/17/2016.
 */
public class LumberLogTransportCertificateModel extends DefaultDataTableModel<LumberLogTransportCertificate> {

    SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");

    private Map<Long, Supplier> suppliers = new HashMap();

    public LumberLogTransportCertificateModel() {
        columns = new String[]{"Nr", "Data emiterii", "Cod", "Data cod", "Locatie incarcare", "Locatie descarcare", "Nr masina", "Furnizor"};
    }


    @Override
    public Object getValueAt(int row, int col) {
        LumberLogTransportCertificate record = records.get(row);
        if (col == 0) {
            return row + 1;
        } else if (col == 1) {
            return format.format(record.getEntryDate());
        } else if (col == 2) {
            return record.getCode();
        } else if (col == 3) {
            return format.format(record.getCodeCreationTime());
        } else if (col == 4) {
            return record.getLoadPlace();
        } else if (col == 5) {
            return record.getUnloadPlace();
        } else if (col == 6) {
            return record.getTransportPlate();
        } else if (col == 7) {
            return suppliers.get(record.getSupplierId());
        }
        return null;
    }


    public Map<Long, Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Map<Long, Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}

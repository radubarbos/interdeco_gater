package ro.barbos.gui.tablemodel.stock;

import ro.barbos.gater.model.LumberLogTransportCertificate;
import ro.barbos.gater.model.LumberLogTransportEntry;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.tablemodel.DefaultDataTableModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by radu on 8/23/2016.
 */
public class LumberLogTransportEntryModel extends DefaultDataTableModel<LumberLogTransportEntry> {

    private SimpleDateFormat format = new SimpleDateFormat("dd, MM yyyy");
    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    private Map<Long, Supplier> suppliers = new HashMap();
    private Map<Long, LumberLogTransportCertificate> certificates = new HashMap();

    public LumberLogTransportEntryModel() {
        columns = new String[]{"Nr", "Data", "Total busteni", "Volum", "Volum coaja", "Furnizor", "Aviz", "Cost"};
        numberFormatter.setMaximumFractionDigits(5);
    }


    @Override
    public Object getValueAt(int row, int col) {
        LumberLogTransportEntry record = records.get(row);
        numberFormatter.setMaximumFractionDigits(5);
        if (col == 0) {
            return row + 1;
        } else if (col == 1) {
            return format.format(record.getEntryDate());
        } else if (col == 2) {
            return record.getLumberLogCount();
        } else if (col == 3) {
            return numberFormatter.format(record.getVolume());
        } else if (col == 4) {
            return numberFormatter.format(record.getMarginVolume());
        } else if (col == 5) {
            Supplier supp = suppliers.get(record.getSupplierId());
            return supp != null ? supp.getTitle() : "";
        } else if (col == 6) {
            LumberLogTransportCertificate certificate = certificates.get(record.getCertificateId());
            return certificate != null ? certificate.getCode() : "";
        } else if (col == 7) {
            numberFormatter.setMaximumFractionDigits(2);
            return numberFormatter.format(record.getCost());
        }
        return null;
    }

    public void setSuppliers(Map<Long, Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public void setCertificates(Map<Long, LumberLogTransportCertificate> certificates) {
        this.certificates = certificates;
    }
}

package ro.barbos.gui.tablemodel.supplier;

import ro.barbos.gater.model.Person;
import ro.barbos.gater.model.Supplier;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.tablemodel.GeneralAbstractTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 8/2/2016.
 */
public class SupplierModel extends GeneralAbstractTableModel {

    String[] columns = {"Nr", "Data adaugari", "Nr. registru", "CUI", "Titlu", "Adresa", "Judet", "Contact", "Contact telefon", "contact email"};

    private List<Supplier> records = new ArrayList<>();

    /* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Supplier record = records.get(row);
        Person person = record.getContactPerson();
        if(col == 0) {
            return row+1;
        }
        else if(col == 1) {
            return ConfigLocalManager.format.format(record.getEntryDate());
        } else if(col == 2) {
            return record.getRegisterNo();
        } else if(col == 3) {
            return record.getRegisterId();
        } else if (col == 4) {
            return record.getTitle();
        } else if (col == 5) {
            return record.getAddress();
        } else if (col == 6) {
            return record.getAreaCode();
        } else if (col == 7) {
            return "" + (person.getFirstName() != null ? person.getFirstName() : "") + " " + (person.getLastName() != null ? person.getLastName() : "");
        } else if (col == 8) {
            return person.getPhone();
        } else if (col == 9) {
            return person.getEmail();
        }
        return null;
    }

    public void setData(List<Supplier> suppliers) {
        if(suppliers != null) {
            records.clear();
            records.addAll(suppliers);
            refreshOnDataChange();
        }
    }

    public void addRecord(Supplier supplier) {
        records.add(supplier);
        refreshOnDataChange();
    }

    public void deleteRecord(int row) {
        records.remove(row);
        refreshOnDataChange();
    }

    public Supplier getRecord(int row) {
        if(row >= 0 || row < records.size()) {
            return records.get(row);
        }
        return null;
    }
}

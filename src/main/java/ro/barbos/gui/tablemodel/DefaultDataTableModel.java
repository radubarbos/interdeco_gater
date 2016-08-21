package ro.barbos.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 8/17/2016.
 */
public class DefaultDataTableModel<T> extends GeneralAbstractTableModel {

    protected List<T> records = new ArrayList<>();

    protected String[] columns = new String[]{};

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

    public void setData(List<T> newRecords) {
        if (newRecords != null) {
            records.clear();
            records.addAll(newRecords);
            refreshOnDataChange();
        }
    }

    public void addRecord(T record) {
        records.add(record);
        refreshOnDataChange();
    }

    public void deleteRecord(int row) {
        records.remove(row);
        refreshOnDataChange();
    }

    public T getRecord(int row) {
        if (row >= 0 || row < records.size()) {
            return records.get(row);
        }
        return null;
    }
}

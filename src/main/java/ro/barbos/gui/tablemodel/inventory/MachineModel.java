package ro.barbos.gui.tablemodel.inventory;

import ro.barbos.gater.model.Machine;
import ro.barbos.gui.tablemodel.GeneralAbstractTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 8/2/2016.
 */
public class MachineModel extends GeneralAbstractTableModel {

    String[] columns = {"Nr","Nume"};

    private List<Machine> records = new ArrayList<Machine>();

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
        Machine record = records.get(row);
        if(col == 0) {
            return row+1;
        }
        else if(col == 1) {
            return record.getLabel();
        }
        return null;
    }

    public void setMachines(List<Machine> machines) {
        if(machines != null) {
            records.clear();
            records.addAll(machines);
            refreshOnDataChange();
        }
    }

    public void addMachine(Machine product) {
        records.add(product);
        refreshOnDataChange();
    }

    public void deleteMachine(int row) {
        records.remove(row);
        refreshOnDataChange();
    }

    public Machine getMachine(int row) {
        if(row >= 0 || row < records.size()) {
            return records.get(row);
        }
        return null;
    }
}

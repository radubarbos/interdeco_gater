package ro.barbos.gui.tablemodel;

import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberStack;

import java.util.ArrayList;
import java.util.List;

public class ReceiveModel extends GeneralAbstractTableModel {
	
    String[] columns = {"Nr","Stiva","Placa","Diametru mic","Diametru mare","Volum","Lungime","Tip","Clasa"};
	
	private List<ReceiveRecord> data = new ArrayList<ReceiveRecord>();
	private List<LumberLog> lumberLogs = new ArrayList<LumberLog>();

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
		// TODO Auto-generated method stub
		return data.size();
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		return super.getColumnClass(col);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		ReceiveRecord record = data.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getStackLabel();
		} 
		else if(col == 2) {
			return record.getLabel();
		}
		else if(col == 3) {
			return record.getSmallRadius();
		} 
		/*else if(col == 4) {
			return record.getMediumRadius();
		} */
		else if(col == 4) {
			return record.getBigRadius();
		} 
		else if(col == 5) {
			return record.getVolum();
		} 
		else if(col == 6) {
			return record.getLength();
		} 
		else if(col == 7) {
			return record.getType();
		} 
		else if(col == 8) {
			return record.getLumberClass();
		} 
		return null;
	}
	
	public void addLumberLog(LumberLog lumberLog, LumberStack lumberStack) {
		data.add(new ReceiveRecord(lumberLog, lumberStack));
		lumberLogs.add(lumberLog);
		refreshOnDataChange();
	}

    public void addLumberLogs(List<LumberLog> lumberLogs) {
        for (LumberLog lumberLog : lumberLogs) {
            data.add(new ReceiveRecord(lumberLog, lumberLog.getStack()));
            this.lumberLogs.add(lumberLog);
        }
        refreshOnDataChange();
    }

    public void removeLumberLog(int index) {
		data.remove(index);
		lumberLogs.remove(index);
		refreshOnDataChange();
	}
	
	public LumberLog getLumberLog(int row) {
		return lumberLogs.get(row);
	}

    public List<LumberLog> getLumberLogs() {
        return lumberLogs;
    }

}

package ro.barbos.gui.tablemodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ro.barbos.gater.model.CutPlan;
import ro.barbos.gui.ConfigLocalManager;

public class CutPlanHistoryModel extends GeneralAbstractTableModel{

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd, MM yyyy");
	private NumberFormat completeFormat = NumberFormat.getPercentInstance(ConfigLocalManager.locale);
	
	String[] columns = {"Nr","Nume","Data","Activ","Complect"};
	
	private List<CutPlan> records = new ArrayList<>();
	
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
		return records.size();
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
		CutPlan record = records.get(row);
		if(col == 0 ) {
			return row + 1;
		}
		else if(col ==1) {
			return record.getName();
		}
		else if(col == 2) {
			return dateFormat.format(record.getDate());
		} 
		else if(col == 3) {
			return record.getStatus() == 0 ? "Activ": "Inactiv";
		} 
		else if(col == 4) {
			return completeFormat.format(record.getCompleted());
		}  
		return null;
	}
	
	public void setRecords(List<CutPlan> plans) {
		records.clear();
		if(plans != null) {
			records = plans;
		}
		refreshOnDataChange();
	}
	
	public CutPlan getRecord(int row) {
		return records.get(row);
	}
}

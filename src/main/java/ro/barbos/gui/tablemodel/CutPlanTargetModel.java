package ro.barbos.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class CutPlanTargetModel extends AbstractTableModel {

    String[] columns = {"Nr","Produs","Volum metri cubi", "Bucati"};
	
	public int currentPage =0;
	public int count =0;
	public int pages =0;
	public int pageCount=100;
	
	private List<CutPlanTargetRecord> records = new ArrayList<CutPlanTargetRecord>(); 
	
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
		CutPlanTargetRecord record = records.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getProduct().getName();
		} 
		else if(col == 2) {
			return record.getTargetMCub();
		}
		else if(col == 3) {
			return record.getPieces();
		}
		return null;
	}
	
	private void refreshOnDataChange() {
		fireTableDataChanged();
		count = records.size();
		pages = (records.size()-1)/pageCount +1;
		if(currentPage>=pages) currentPage = pages-1;
	}
	
	public void setRecords(List<CutPlanTargetRecord> records) {
		this.records = records;
		refreshOnDataChange();
	}
}

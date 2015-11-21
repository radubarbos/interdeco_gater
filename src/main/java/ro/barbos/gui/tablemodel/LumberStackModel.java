package ro.barbos.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.LumberStack;

public class LumberStackModel extends GeneralAbstractTableModel {
	
String[] columns = {"Nr", "Name", "Minimum", "Maximum"};
	
	
	private List<LumberStack> records = new ArrayList<LumberStack>();
	
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
		LumberStack record = records.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getName();
		} 
		else if(col == 2) {
			return record.getMinimum() + " mm";
		}
		else if(col == 3) {
			return record.getMaximum() + " mm";
		}
		return null;
	}
	
	public void setLumberStacks(List<LumberStack> stacks) {
		if(stacks != null) {
			records.clear();
			records.addAll(stacks);
			refreshOnDataChange();
		}
	}
	
	public void addLumberStack(LumberStack stack) {
		records.add(stack);
		refreshOnDataChange();
	}
	
	public void removeLumberStack(int row) {
		records.remove(row);
		refreshOnDataChange();
	}
	
	public LumberStack getLumberStack(int row) {
		return records.get(row);
	}

}

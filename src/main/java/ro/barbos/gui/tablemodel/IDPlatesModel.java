package ro.barbos.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ro.barbos.gater.model.IDPlate;

public class IDPlatesModel extends GeneralAbstractTableModel {
	
	String[] columns = {"Nr","Placa","Stare"};
	
	
	private List<IDPlate> records = new ArrayList<IDPlate>(); 
	
	public IDPlatesModel() {
		memoryPagination = true;
	}
	
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
		return records.size() < pageCount ? records.size() : pageCount;
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
		row = currentPage * pageCount + row;
		if(row>=records.size()) {
			return null;
		}
		IDPlate record = records.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getLabel();
		} 
		else if(col == 2) {
			Boolean status = record.getStatus();
			if(status != null && status) return "Ocupat";
			return "Liber";
		}
		return null;
	}
	
	public void setIdPlates(List<IDPlate> plates) {
		if(plates != null) {
			records.clear();
			records.addAll(plates);
			count = plates.size();
			refreshOnDataChange();
		}
	}
	
	public void addIdPlate(IDPlate idPlate) {
		records.add(idPlate);
		count++;
		refreshOnDataChange();
	}
	
	public void removeIdPlate(int row) {
		int index = row + getDataOffset();
		records.remove(index);
		count--;
		refreshOnDataChange();
	}
	
	public IDPlate getIDPlate(int row) {
		int index = row + getDataOffset();
		if(index >= records.size()) {
			return null;
		}
		return records.get(index);
	}

}

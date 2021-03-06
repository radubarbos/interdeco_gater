package ro.barbos.gui.tablemodel;

import ro.barbos.gater.dto.ClassStockDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ClassStockModel extends GeneralAbstractTableModel {
	
    String[] columns = {"Clasa","Nr. busteni","Volum",};
	
	private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
	
	private List<ClassStockDTO> records = new ArrayList<ClassStockDTO>(); 
	
	public ClassStockModel() {
		numberFormatter.setMaximumFractionDigits(2);
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
		ClassStockDTO record = records.get(row);
		if(col == 0 ) {
			return record.getName();
		}
		else if(col == 1) {
			return record.getTotalLumberLogs();
		} 
		else if(col == 2) {
			return MetricFormatter.formatVolume(record.getTotalVolume());
		} 
		return null;
	}
	
	public void setLumberClassInfo(List<ClassStockDTO> classInfo) {
		records.clear();
		records.addAll(classInfo);
		refreshOnDataChange();
	}
	
	public ClassStockDTO getClassInfo(int row) {
		return records.get(row);
	}
	
	public void removeLumberLog(int index) {
		records.remove(index);
		refreshOnDataChange();
	}

}

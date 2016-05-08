package ro.barbos.gui.tablemodel;

import ro.barbos.gater.dto.TypeStockDTO;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TypeStockModel extends GeneralAbstractTableModel {
	
    String[] columns = {"Tip bustean","Nr. busteni","Volum",};
	
	private NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ro"));
	
	private List<TypeStockDTO> records = new ArrayList<TypeStockDTO>(); 
	
	public TypeStockModel() {
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
		TypeStockDTO record = records.get(row);
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
	
	public void setLumberTypeInfo(List<TypeStockDTO> typesInfo) {
		records.clear();
		records.addAll(typesInfo);
		refreshOnDataChange();
	}
	
	public TypeStockDTO getTypeInfo(int row) {
		return records.get(row);
	}
	
	public void removeLumberLog(int index) {
		records.remove(index);
		refreshOnDataChange();
	}

}

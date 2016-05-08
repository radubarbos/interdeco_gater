package ro.barbos.gui.tablemodel;

import ro.barbos.gater.dto.LumberStackInfoDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StackStockModel extends GeneralAbstractTableModel {
	
    String[] columns = {"Stiva","Minimum","Maximum","Nr. busteni","Volum",};
	
	private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
	
	private List<LumberStackInfoDTO> records = new ArrayList<LumberStackInfoDTO>(); 
	
	public StackStockModel() {
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
		LumberStackInfoDTO record = records.get(row);
		if(col == 0 ) {
			return record.getStack().getName();
		}
		else if(col ==1) {
			return record.getStack().getMinimum() + " mm";
		}
		else if(col == 2) {
			return record.getStack().getMaximum() + " mm";
		} 
		else if(col == 3) {
			return record.getTotalLumberLogs();
		} 
		else if(col == 4) {
			return MetricFormatter.formatVolume(record.getTotalVolume());
		} 
		return null;
	}
	
	public void setLumberStacksInfo(List<LumberStackInfoDTO> lumberStacks) {
		records.clear();
		records.addAll(lumberStacks);
		refreshOnDataChange();
	}
	
	public LumberStackInfoDTO getLumberStackInfoDTO(int row) {
		return records.get(row);
	}
	
	public void removeLumberLog(int index) {
		records.remove(index);
		refreshOnDataChange();
	}

}

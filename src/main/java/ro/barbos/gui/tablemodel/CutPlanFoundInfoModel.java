package ro.barbos.gui.tablemodel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.dto.ProductCutTargetDTO;
import ro.barbos.gui.ConfigLocalManager;

public class CutPlanFoundInfoModel extends AbstractTableModel {

    String[] columns = {"Produs", "Tinta bucati", "Bucati taiate", "Acoperire"};
	
	public int currentPage =0;
	public int count =0;
	public int pages =0;
	public int pageCount=100;
	
    private List<ProductCutTargetDTO> records = new ArrayList<ProductCutTargetDTO>(); 
	private NumberFormat formater = NumberFormat.getPercentInstance(ConfigLocalManager.locale);
    
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
		ProductCutTargetDTO record = records.get(row);
		if(col == 0) {
			return record.getProduct();
		}
		else if(col == 1) {
			return record.getTargetPieces();
		} 
		else if(col == 2) {
			return record.getCutPieces();
		}
		else if(col == 3) {
		  return formater.format((double)record.getCutPieces()/record.getTargetPieces());
		}
		return null;
	}
	
	private void refreshOnDataChange() {
		fireTableDataChanged();
		count = records.size();
		pages = (records.size()-1)/pageCount +1;
		if(currentPage>=pages) currentPage = pages-1;
	}
	
	public void setRecords(List<ProductCutTargetDTO> records) {
		this.records = records;
		refreshOnDataChange();
	}
}

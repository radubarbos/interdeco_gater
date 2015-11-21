package ro.barbos.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

import ro.barbos.gater.dao.StockDAO;
import ro.barbos.gater.data.DataSearchPagination;
import ro.barbos.gater.data.DataSearchResult;
import ro.barbos.gater.model.ProcessedLumberLog;

public class ProcessedHistoryModel  extends GeneralAbstractTableModel {

	private static final long serialVersionUID = 1L;

String[] columns = {"Data","Diametru mic","Diametru mare","Lungime","Volum","Tip","Clasa", "Operator", "Placuta"};
	
	private List<ProcessedLumberLogRecord> records = new ArrayList<ProcessedLumberLogRecord>(); 
	
	public ProcessedHistoryModel() {
		pageCount = 50;
		dbPagination = true;
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
		ProcessedLumberLogRecord record = records.get(row);
		if(col == 0) {
			return record.getProcessedDate();
		}
		else if(col == 1) {
			return record.getSmallRadius();
		}
		else if(col == 2) {
			return record.getBigRadius();
		}
		else if(col == 3) {
			return record.getLength();
		}
		else if(col == 4) {
			return record.getVolum();
		}
		else if(col == 5) {
			return record.getType();
		}
		else if(col == 6) {
			return record.getLumberClass();
		}
		else if(col == 7) {
			return record.getUser();
		}
		else if(col == 8) {
			return record.getPlateLabel();
		}
		return null;
	}
	
	public void setHistoryData(DataSearchResult<ProcessedLumberLog> processedData) {
		records.clear();
		for(ProcessedLumberLog lumberLog: processedData.getData()) {
			records.add(new ProcessedLumberLogRecord(lumberLog));
		}
		if(processedData.getTotal() != null) {
			count = processedData.getTotal().intValue();
		}
		refreshOnDataChange();
	}
	
	protected boolean extractDbPage() {
		DataSearchPagination pagination = new DataSearchPagination(currentPage, pageCount);
		DataSearchResult<ProcessedLumberLog> lumbersProcessed = StockDAO.getProcessedHistory(null, pagination);
		if(lumbersProcessed == null) {
			return false;
		}
		setHistoryData(lumbersProcessed);
		return true;
	}
	
	protected boolean extractNextDbPage() {
		return extractDbPage();
	}
	
	protected boolean extractPreviousDbPage() {
		return extractDbPage();
	}

}

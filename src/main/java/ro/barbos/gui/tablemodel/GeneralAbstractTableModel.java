package ro.barbos.gui.tablemodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import ro.barbos.gater.gui.grid.GridModelEvent;
import ro.barbos.gater.gui.grid.GridModelListener;
import ro.barbos.gui.GUIUtil;

import com.csvreader.CsvWriter;

public class GeneralAbstractTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	public int currentPage = 0;
	public int count = 0;
	public int pages = 0;
	public int pageCount = 100;

	private Map<GridModelListener, Object> gridModelListeners = new HashMap<>();
	private boolean extracting = false;
	protected boolean dbPagination = false;
	protected boolean memoryPagination = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void refreshOnDataChange() {
		fireTableDataChanged();
		if(!dbPagination && !memoryPagination) {
		  count = getRowCount();
		}
		pages = (count - 1) / pageCount + 1;
		if (currentPage >= pages)
			currentPage = pages - 1;
		fireGridModelNotification();
	}

	public void nextPage() {
		if (currentPage >= pages)
			return;
		currentPage++;
		if(!dbPagination) {
			fireTableDataChanged();
		}
		else if(dbPagination) {
			boolean status = extractNextDbPage();
			if(!status) {
				return;
			}
		}
		fireGridModelNotification();
	}

	public void prevPage() {
		if (currentPage == 0)
			return;
		currentPage--;
		if(!dbPagination) {
			fireTableDataChanged();
		}
		else {
			boolean status = extractPreviousDbPage();
			if(!status) {
				return;
			}
		}
		fireGridModelNotification();
	}
	
	/**
	 * @return the dbPagination
	 */
	public boolean isDbPagination() {
		return dbPagination;
	}

	/**
	 * @param dbPagination the dbPagination to set
	 */
	public void setDbPagination(boolean dbPagination) {
		this.dbPagination = dbPagination;
	}

	protected synchronized boolean isExtracting() {
		return extracting;
	}

	protected synchronized void setExtracting(boolean extracting) {
		this.extracting = extracting;
	}

	protected synchronized boolean setAndGetExtracting() {
		if (extracting)
			return extracting;
		extracting = true;
		return false;
	}

	public void toCsv(File file) {
		int rows = getRowCount();
		int cols = getColumnCount();
		CsvWriter csvWriter = null;
		try {
			if (file.getName().endsWith(".csv") == false) {
				file = new File(file.getParentFile(), file.getName() + ".csv");
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			csvWriter = new CsvWriter(writer, ',');
			List<String> record = new ArrayList<>();
			for (int col = 0; col < cols; col++) {
				record.add(getColumnName(col));
			}
			csvWriter.writeRecord(record.toArray(new String[0]));
			for (int row = 0; row < rows; row++) {
				record = new ArrayList<>();
				for (int col = 0; col < cols; col++) {
					record.add(getValueAt(row, col).toString());
				}
				csvWriter.writeRecord(record.toArray(new String[0]));
			}
			JOptionPane.showMessageDialog(GUIUtil.container, "Tabelul a fost salvat in fisierul ("
					+ file.getName() + ")", "Saved", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
		} finally {
			if (csvWriter != null) {
				try {
					csvWriter.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void addGridModelListener(GridModelListener listener) {
		gridModelListeners.put(listener, true);
	}
	
	public void removeGridModelListener(GridModelListener listener) {
		gridModelListeners.remove(listener);
	}
	
	private void fireGridModelNotification() {
		GridModelEvent event = new GridModelEvent();
		event.model = this;
		Iterator<Map.Entry<GridModelListener, Object>> it = gridModelListeners.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<GridModelListener, Object> entry = it.next();
			entry.getKey().gridDataChanged(event);
		}
	}
	
	protected boolean extractNextDbPage() {
		return false;
	}
	
	protected boolean extractPreviousDbPage() {
		return false;
	}
	
	public int getDataOffset() {
		return currentPage * pageCount;
	}
}

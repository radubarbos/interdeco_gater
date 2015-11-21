package ro.barbos.gui.tablemodel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import ro.barbos.gater.cutprocessor.CutPlanSenquence;
import ro.barbos.gater.cutprocessor.CutterSettings;
import ro.barbos.gui.ConfigLocalManager;

public class CutPlanFoundModel extends AbstractTableModel {

    String[] columns = {"Nr","Placuta bustean", "Bucati", "Volum produs", "Eficenta"};
	
	public int currentPage =0;
	public int count =0;
	public int pages =0;
	public int pageCount=100;
	
	private NumberFormat formatter = NumberFormat.getNumberInstance(ConfigLocalManager.locale);
	
    private List<CutPlanSenquence> records = new ArrayList<CutPlanSenquence>(); 
	
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
		CutPlanSenquence record = records.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getLumberLog().getPlate().getLabel();
		} 
		else if(col == 2) {
			StringBuilder sb = new StringBuilder("<html>");
			Map<String, Integer> lumberLogPieces = record.getCutDiagram().cutInfo.cutPieces;
			Iterator<Map.Entry<String, Integer>> ite = lumberLogPieces.entrySet().iterator();
			while(ite.hasNext()) {
				Map.Entry<String, Integer> entry = ite.next();
				String productName = entry.getKey();
				Integer piecesFromLumber = entry.getValue();
				sb.append(productName).append(": <b>").append(piecesFromLumber).append("</b><br>");
			}
			sb.append("</html>");
			return sb.toString();
		}
		else if(col == 3) {
			return formatter.format(record.getCutDiagram().cutInfo.cutVolume/1000000000L) + "m. cub";
		}
		else if(col == 4) {
			return formatter.format(CutterSettings.DO_LENGTH_OPTIMIZATION ? record.getCutDiagram().cutInfo.cutVolumeEfficency : record.getCutDiagram().cutInfo.cutLayoutEfficency) + " %";
		}
		return null;
	}
	
	private void refreshOnDataChange() {
		fireTableDataChanged();
		count = records.size();
		pages = (records.size()-1)/pageCount +1;
		if(currentPage>=pages) currentPage = pages-1;
	}
	
	public void setRecords(List<CutPlanSenquence> records) {
		this.records = records;
		refreshOnDataChange();
	}
}

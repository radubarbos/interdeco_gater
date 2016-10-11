package ro.barbos.gui.tablemodel;

import ro.barbos.gater.dto.LumberStackInfoDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;

public class StackStockModel extends DefaultDataTableModel<LumberStackInfoDTO> {

	private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
	
	public StackStockModel() {
        columns = new String[]{"Stiva", "Minimum", "Maximum", "Nr. busteni", "Volum"};
        numberFormatter.setMaximumFractionDigits(2);
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

}

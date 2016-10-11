package ro.barbos.gui.tablemodel;

import ro.barbos.gater.dto.TypeStockDTO;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.MetricFormatter;

import java.text.NumberFormat;

public class TypeStockModel extends DefaultDataTableModel<TypeStockDTO> {

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    public TypeStockModel() {
        columns = new String[]{"Tip bustean", "Nr. busteni", "Volum"};
        numberFormatter.setMaximumFractionDigits(2);
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

}

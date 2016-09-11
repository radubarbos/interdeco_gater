package ro.barbos.gui.tablemodel;

import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.data.MetricTools;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogStockEntry;
import ro.barbos.gui.ConfigLocalManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FullStockModel extends StockModel {

    String[] columns = {"Stiva", "Placa", "Diametru mic", "Diametru mare", "Volum", "Lungime", "Tip", "Clasa", "Operator", "Data", "Adaugat plan", "Cost unitate m.cub"};

    public int currentPage =0;
	public int count =0;
	public int pages =0;
	public int pageCount=100;

    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);

    private List<StockRecord> records = new ArrayList<StockRecord>();
	double volume = 0D;
	
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
        numberFormatter.setMaximumFractionDigits(2);
        StockRecord record = records.get(row);
		if(col == 0 ) {
			return record.getStackLabel();
		}
		else if(col ==1) {
			return record.getLabel();
		}
		else if(col == 2) {
			return record.getSmallRadius();
		} 
		else if(col == 3) {
			return record.getBigRadius();
		} 
		else if(col == 4) {
			return record.getVolum();
		} 
		else if(col == 5) {
			return record.getLength();
		} 
		else if(col == 6) {
			return record.getType();
		} 
		else if(col == 7) {
			return record.getLumberClass();
		} 
		else if(col == 8) {
			return record.getUserFullName();
		}
		else if(col == 9) {
			return record.getDateLabel();
		} 
		else if(col == 10) {
			return record.getCutPlanName();
		} else if (col == 11) {
            return numberFormatter.format(record.getCostPerUnit());
        }
        return null;
	}
	
	public Double setLumberLogs(List<LumberLogStockEntry> lumberLogs) {
		if(lumberLogs != null) {
			for(LumberLogStockEntry lumberLogEntry: lumberLogs) {
				LumberLog lumberLog = lumberLogEntry.getLumberLog();
				records.add(new StockRecord(lumberLogEntry));
				volume += MetricTools.toMeterCubs(lumberLog.getVolume(), METRIC.MILIMETER);
			}
		}
		refreshOnDataChange();
		return volume;
	}
	
	public List<Double> setLumberLogsData(List<LumberLogStockEntry> lumberLogs) {
		List<Double> data = new ArrayList<>();
		double unuseable = 0;
		if(lumberLogs != null) {
			for(LumberLogStockEntry lumberLogEntry: lumberLogs) {
				LumberLog lumberLog = lumberLogEntry.getLumberLog();
				records.add(new StockRecord(lumberLogEntry));
				double lumberVolume = MetricTools.toMeterCubs(lumberLog.getVolume(), METRIC.MILIMETER);
				volume += lumberVolume;
				if(lumberLog.getCutPlanId() == 0) {
					unuseable += lumberVolume;
				}
			}
		}
		refreshOnDataChange();
		data.add(volume);
		data.add(unuseable);
		return data;
	}
	
	public Double updateLumberLog(LumberLog lumberLog, int index) {
		LumberLog oldLumberLog = records.get(index).getLumberLog();
		//records.set(index, new StockRecord(lumberLog));
		records.get(index).updateLumberLog(lumberLog);
		volume -= MetricTools.toMeterCubs(oldLumberLog.getVolume(), METRIC.MILIMETER);
		volume += MetricTools.toMeterCubs(lumberLog.getVolume(), METRIC.MILIMETER);
		refreshOnDataChange();
		return volume;
	}
	
	public LumberLog getLumberLog(int row) {
		return records.get(row).getLumberLog();
	}
	
	public void removeLumberLog(int index) {
		records.remove(index);
		refreshOnDataChange();
	}
	
	

}

package ro.barbos.gui.tablemodel;

import ro.barbos.gater.cutprocessor.cutplan.CutPlanStackResult;
import ro.barbos.gui.MetricFormatter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by radu on 5/16/2016.
 */
public class CutPlanStackStatisticsModel extends AbstractTableModel {

    String[] columns = {"Nr","Stiva","Busteni", "Eficenta medie"};

    public int currentPage =0;
    public int count =0;
    public int pages =0;
    public int pageCount=100;

    private List<CutPlanStackResult> records = new ArrayList<CutPlanStackResult>();

    private boolean showEfficence = true;

    public CutPlanStackStatisticsModel(boolean showEfficence) {
        this.showEfficence = showEfficence;
        if(!showEfficence) {
           columns = Arrays.copyOf(columns, columns.length-1);
        }
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
        CutPlanStackResult record = records.get(row);
        if(col == 0) {
            return row+1;
        }
        else if(col == 1) {
            return record.getStack();
        }
        else if(col == 2) {
            return record.getCount();
        }
        else if(col == 3) {
            return MetricFormatter.formatPercent(record.getAvgEfficency() / 100);
        }
        return null;
    }

    private void refreshOnDataChange() {
        fireTableDataChanged();
        count = records.size();
        pages = (records.size()-1)/pageCount +1;
        if(currentPage>=pages) currentPage = pages-1;
    }

    public void setRecords(List<CutPlanStackResult> records) {
        this.records = records;
        refreshOnDataChange();
    }

    public boolean isShowEfficence() {
        return showEfficence;
    }

    public void setShowEfficence(boolean showEfficence) {
        this.showEfficence = showEfficence;
    }
}

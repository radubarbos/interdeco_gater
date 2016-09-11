package ro.barbos.gui.tablemodel.stock;

import ro.barbos.gater.model.LumberLogTransportEntryCostMatrix;
import ro.barbos.gater.model.LumberLogTransportEntryCostMatrixKey;
import ro.barbos.gater.model.LumberLogTransportEntryCostMatrixValue;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.stock.ConfigLumberTransportEntryFrame;
import ro.barbos.gui.tablemodel.GeneralAbstractTableModel;

import java.text.NumberFormat;
import java.util.*;

/**
 * Created by radu on 8/24/2016.
 */
public class LumberLogTransportCostModel extends GeneralAbstractTableModel {

    private LumberLogTransportEntryCostMatrix matrix;

    private List<String> columns = new ArrayList<>();
    private List<String> rows = new ArrayList<>();
    private Map<Integer, List<Integer>> colKeys = new HashMap<>();
    private List<Integer> rowKeys = new ArrayList<>();
    private NumberFormat numberFormatter = NumberFormat.getInstance(ConfigLocalManager.locale);
    private ConfigLumberTransportEntryFrame parent;

    public LumberLogTransportCostModel(LumberLogTransportEntryCostMatrix matrix, ConfigLumberTransportEntryFrame parent) {
        this.matrix = matrix;
        this.parent = parent;
        initGridSetup();
        numberFormatter.setMaximumFractionDigits(2);
    }

    private void initGridSetup() {
        List<Integer> qualityClasses = new ArrayList<>();
        qualityClasses.addAll(matrix.getQualityClasses());
        List<Integer> types = new ArrayList<>();
        types.addAll(matrix.getTypes());
        Map<String, Boolean> colExitingMap = new HashMap<>();
        for (Map.Entry<LumberLogTransportEntryCostMatrixKey, LumberLogTransportEntryCostMatrixValue> cell : matrix.getCellData().entrySet()) {
            LumberLogTransportEntryCostMatrixKey key = cell.getKey();
            colExitingMap.put(key.getType() + "-" + key.getQualityClass(), true);
        }
        for (Integer lumberType : types) {
            for (Integer qualityClass : qualityClasses) {
                String checkName = lumberType + "-" + qualityClass;
                if (colExitingMap.containsKey(checkName)) {
                    String colName = GUIUtil.types[lumberType - 1] + " - " + GUIUtil.lumberClass[qualityClass - 1];
                    List<Integer> colKey = Arrays.asList(lumberType, qualityClass);
                    colKeys.put(columns.size(), colKey);
                    columns.add(colName);
                }

            }
        }
        rowKeys.addAll(matrix.getLengths());
        for (Integer length : matrix.getLengths()) {
            rows.add("L." + (length) + " mm");
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
	 */
    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column);
    }

    /* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
    @Override
    public int getRowCount() {
        return matrix.getLengths().size();
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
        LumberLogTransportEntryCostMatrixKey dataKey = computeDataKey(row, col);
        if (dataKey != null) {
            LumberLogTransportEntryCostMatrixValue value = matrix.getCellData().get(dataKey);
            if (value != null) {
                return numberFormatter.format(value.getRealCost());
            }
        }
        return null;
    }

    /* (non-Javadoc)
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (!(value instanceof Double)) {
            return;
        }
        Double val = (Double) value * 100;
        long cellCost = val.longValue();
        if (cellCost < 0) {
            return;
        }
        LumberLogTransportEntryCostMatrixKey dataKey = computeDataKey(row, col);
        if (dataKey != null) {
            LumberLogTransportEntryCostMatrixValue valueCell = matrix.getCellData().get(dataKey);
            if (valueCell != null) {
                valueCell.setCost(cellCost);
                matrix.calculateTotalCost();
                parent.costChanged(matrix.getMediumCost());
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    private LumberLogTransportEntryCostMatrixKey computeDataKey(int row, int col) {
        if (row >= rowKeys.size() || col >= colKeys.size()) {
            return null;
        }
        int length = rowKeys.get(row);
        List<Integer> colKey = colKeys.get(col);
        return new LumberLogTransportEntryCostMatrixKey((long) length, colKey.get(1), colKey.get(0));
    }

    public List<String> getRows() {
        return rows;
    }
}

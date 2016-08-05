package ro.barbos.gui.tablemodel.production;

import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.dto.production.ProductionProductPalletDTO;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.ConfigLocalManager;
import ro.barbos.gui.GUIUtil;
import ro.barbos.gui.tablemodel.GeneralAbstractTableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radu on 8/2/2016.
 */
public class ProductPalletModel extends GeneralAbstractTableModel {

    String[] columns = {"Nr", "Data", "Product", "Cantitate", "Tip", "Packet"};

    private List<ProductionProductPalletDTO> records = new ArrayList<>();

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
        return records.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        ProductionProductPalletDTO record = records.get(row);
        if(col == 0) {
            return row+1;
        }
        else if(col == 1) {
            return ConfigLocalManager.format.format(record.getEntryDate());
        }
        else if(col == 2) {
            return record.getProduct().getName();
        }
        else if(col == 3) {
            return record.getQuantity();
        }
        else if(col == 4) {
            return GUIUtil.types[record.getLumberType().intValue()-1];
        }
        else if(col == 5) {
            return record.getPackageNo();
        }
        return null;
    }

    public void setData(List<ProductionProductPalletDTO> data) {
        if(data != null) {
            Map<Long, Product> productCache = new HashMap<>();
            for(ProductionProductPalletDTO palletDTO: data) {
              Product product = productCache.get(palletDTO.getProduct().getId());
              if(product == null) {
                  product = ProductDAO.getProduct(palletDTO.getProduct().getId());
                  productCache.put(product.getId(), product);
              }
                palletDTO.getProduct().setName(product.getName());
            }
            records.clear();
            records.addAll(data);
            refreshOnDataChange();
        }
    }

    public void addRecord(ProductionProductPalletDTO productPalletDTO) {
        records.add(productPalletDTO);
        refreshOnDataChange();
    }

    public void deleteRecord(int row) {
        records.remove(row);
        refreshOnDataChange();
    }

    public ProductionProductPalletDTO getRecord(int row) {
        if(row >= 0 || row < records.size()) {
            return records.get(row);
        }
        return null;
    }
}

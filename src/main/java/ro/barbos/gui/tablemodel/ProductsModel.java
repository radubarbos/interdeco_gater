package ro.barbos.gui.tablemodel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.Product;
import ro.barbos.gui.ConfigLocalManager;

public class ProductsModel extends GeneralAbstractTableModel {

    String[] columns = {"Nr","Nume","Lungime", "Latime", "Grosime"};
	
	
	private List<Product> records = new ArrayList<Product>(); 
	
	NumberFormat formater = NumberFormat.getNumberInstance(ConfigLocalManager.locale);
	
	public ProductsModel() {
		formater.setMaximumFractionDigits(1);
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
		Product record = records.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getName();
		} 
		else if(col == 2) {
			return formater.format(record.getLength()/1000D) + " m";
		}
		else if(col == 3) {
			return record.getWidth() + " mm";
		}
		else if(col == 4) {
			return record.getThick() + " mm";
		}
		return null;
	}
	
	public void setProducts(List<Product> products) {
		if(products != null) {
			records.clear();
			records.addAll(products);
			refreshOnDataChange();
		}
	}
	
	public void addProduct(Product product) {
		records.add(product);
		refreshOnDataChange();
	}
	
	public void deleteProduct(int row) {
		records.remove(row);
		refreshOnDataChange();
	}
	
	public Product getProduct(int row) {
		if(row >= 0 || row < records.size()) {
		   return records.get(row);
		}
		return null;
	}
}

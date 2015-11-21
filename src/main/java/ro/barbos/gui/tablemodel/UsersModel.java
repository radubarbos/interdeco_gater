package ro.barbos.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;

import ro.barbos.gater.model.IDPlate;
import ro.barbos.gater.model.User;

public class UsersModel extends GeneralAbstractTableModel {
	
	String[] columns = {"Nr", "Nume", "Nume utilizator", "Nivel"};
	
	private List<User> records = new ArrayList<User>(); 

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
		User record = records.get(row);
		if(col == 0) {
			return row+1;
		}
		else if(col == 1) {
			return record.getName();
		} 
		else if(col == 2) {
			return record.getUserName();
		}
		else if(col == 3) {
			Integer right = record.getRights().getRightsLevel();
			if(right != null) {
				if(right.intValue() == 0) {
					return "Administrator";
				}
				else if(right.intValue() == 1) {
					return "Operator stock";
				}
				else if(right.intValue() == 2) {
					return "Operator gater";
				}
			}
		}
		return null;
	}
	
	public void setRecords(List<User> users) {
		if(users != null) {
			records.clear();
			records.addAll(users);
			refreshOnDataChange();
		}
	}
}

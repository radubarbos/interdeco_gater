package ro.barbos.gui.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import ro.barbos.gui.tablemodel.IDPlatesModel;

public class IDPlateRenderer extends GeneralTableRenderer {
	
	private IDPlatesModel model;
	
	public IDPlateRenderer(IDPlatesModel model) {
		this.model = model;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component compSuper =super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		JLabel label =(JLabel)compSuper;
		
		int colIndex = table.getColumnModel().getColumn(column).getModelIndex();
		label.setHorizontalAlignment(SwingConstants.LEADING);
		if(colIndex == 2 && !isSelected)
		{
			if(value != null && value.toString().equals("Ocupat")) {
				label.setBackground(Color.yellow);
			}
		}
		return label;
	}

}

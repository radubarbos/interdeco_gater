package ro.barbos.gui.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import ro.barbos.gui.tablemodel.ReceiveModel;

public class ReceiveTableRenderer extends DefaultTableCellRenderer {

	private ReceiveModel model =null;
	private Color oddColor = new Color(255,247,229);
	
	public ReceiveTableRenderer(ReceiveModel model)
	{
		super();
		this.model = model;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component compSuper =super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		JLabel label =(JLabel)compSuper;
		if(!isSelected)
		{
			if(row%2==1) label.setBackground(oddColor);
			else label.setBackground(Color.white);
		}
		int colIndex = table.getColumnModel().getColumn(column).getModelIndex();
		label.setHorizontalAlignment(SwingConstants.LEADING);
		if(colIndex >= 3 || colIndex <= 6)
		{
			label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return label;
	}
	
}

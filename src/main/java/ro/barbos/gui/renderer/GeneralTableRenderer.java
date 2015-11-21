package ro.barbos.gui.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class GeneralTableRenderer extends DefaultTableCellRenderer{

	private Color oddColor = new Color(255,247,229);
	
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
		
		return label;
	}

	
	
}

package ro.barbos.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import ro.barbos.gater.dao.ProductDAO;
import ro.barbos.gater.model.Product;

public class CutPlanTargetPanel extends JPanel implements ActionListener {

	private List<Product> products = ProductDAO.getProducts();
	private int productIndex = 0;
	
	public CutPlanTargetPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createNewEntry());
	}
	
	private JPanel createNewEntry() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(GUIFactory.createLabel("Product"+(++productIndex)+":", 80));
		JComboBox<Product> product1 = new JComboBox<Product>(products.toArray(new Product[0]));
		panel.add(product1);
		panel.add(GUIFactory.createLabel("M.cub", 40));
		panel.add(GUIFactory.createDecimalNumberInput(0D, 0D, 10000D, 40));
		panel.add(createRemove());
		return panel;
	}
	
	private JButton createRemove() {
		JButton but = new JButton(new ImageIcon(
				GUITools.getImage("resources/delete24.png")));
		but.setFocusPainted(false);
		but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		but.setToolTipText("Sterge placuta");
		but.setActionCommand("DELETE_ROW");
		but.setContentAreaFilled(false);
		but.setBorder(null);
		but.addActionListener(this);
		return but;
	}
	
	public void addNewProduct() {
		add(createNewEntry());
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("DELETE_ROW")) {
			JButton but = (JButton)e.getSource();
			Container parent = but.getParent();
			remove(parent);
			revalidate();
			relabel();
			repaint();
		}
	}
	
	private void relabel() {
		productIndex = getComponentCount();
		for(int index =0; index< productIndex; index++) {
			JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
			label.setText("Product"+(index+1)+":");
		}
		
	}
	
	public boolean validateData() {
		boolean valid = true;
		for(int index =0; index< this.getComponentCount(); index++) {
			JLabel label = (JLabel)((JPanel)getComponent(index)).getComponent(0);
			JFormattedTextField quantity = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(3);
			if(quantity.getValue() == null || (Double)quantity.getValue() == 0) {
				label.setForeground(Color.red);
				valid = false;
			}
			else {
				label.setForeground(Color.black);
			}
		}
		if(this.getComponentCount() == 0) valid = false;
		return valid;
	}
	
	public List<List<Object>> getData() {
		List<List<Object>> target = new ArrayList<>();
		for(int index =0; index< productIndex; index++) {
			JComboBox<Product> product = (JComboBox<Product>)((JPanel)getComponent(index)).getComponent(1);
			JFormattedTextField quantity = (JFormattedTextField)((JPanel)getComponent(index)).getComponent(3);
			List<Object> data = new ArrayList<>();
			data.add(product.getSelectedItem());
			data.add(quantity.getValue());
			target.add(data);
		}
		
		return target;
	}
}
